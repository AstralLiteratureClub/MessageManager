package bet.astral.messenger.v2;

import bet.astral.messenger.v2.component.ComponentBase;
import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.component.ComponentTypeRegistry;
import bet.astral.messenger.v2.component.ParsedComponentPart;
import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v2.info.MessageInfo;
import bet.astral.messenger.v2.info.MessageInfoBuilder;
import bet.astral.messenger.v2.info.MultiMessageInfo;
import bet.astral.messenger.v2.info.MultiMessageInfoBuilder;
import bet.astral.messenger.v2.locale.LanguageTable;
import bet.astral.messenger.v2.locale.source.LanguageSource;
import bet.astral.messenger.v2.permission.Permission;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.GlobalPlaceholderManager;
import bet.astral.messenger.v2.placeholder.hooks.PlaceholderHookManager;
import bet.astral.messenger.v2.receiver.Receiver;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.messenger.v2.translation.TranslationKeyRegistry;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.*;
import java.util.function.Function;

public abstract class AbstractMessenger implements Messenger {
	private final Map<Locale, LanguageTable> languages = new HashMap<>();
	private final Set<Function<Object, Receiver>> receiverConverterSet = new HashSet<>();
	private final TranslationKeyRegistry translationKeyRegistry;
	private PlaceholderHookManager placeholderHookManager = PlaceholderHookManager.getGlobal();
	private GlobalPlaceholderManager placeholderLoader = null;
	private final Random random;
	private final Logger logger;
	@Setter
	private Locale locale;
	@Getter
	@Setter
	private boolean useReceiverLocale;
	@Setter
	private boolean sendASync = false;
	public AbstractMessenger(Logger logger) {
		this(logger, Randomly.RANDOM);
	}
	public AbstractMessenger(Logger logger, Random random) {
		this.logger = logger;
		this.random = random;
		translationKeyRegistry = TranslationKeyRegistry.create();
		registerReceiverConverter(object->object instanceof Receiver receiver ? receiver : null);
	}

	@Override
	public void setDefaultLocale(@NotNull LanguageSource defaultLocale) {
		this.locale = defaultLocale.getLocale();
		languages.put(getLocale(), LanguageTable.of(defaultLocale));
	}

	@Override
	public @Nullable Component parseComponent(@NotNull MessageInfo messageInfo, @NotNull ComponentType componentType) {
		return Messenger.super.parseComponent(messageInfo, componentType);
	}

	@Override
	public @Nullable Component parseComponent(@NotNull MessageInfo messageInfo, @NotNull ComponentType componentType, @NotNull Receiver receiver, boolean useReceiverLocale) {
		return null;
	}

	@Override
	public @Nullable Component parseComponent(@NotNull TranslationKey translationKey, @NotNull Locale locale, @NotNull ComponentType componentType, @NotNull Placeholder... placeholders) {
		return Messenger.super.parseComponent(translationKey, locale, componentType, placeholders);
	}

	@Override
	public @Nullable ComponentBase getBaseComponent(@NotNull TranslationKey key, @NotNull Locale locale) {
		return getBaseComponent(key, locale, false);
	}

	@Override
	public @Nullable ComponentBase getBaseComponent(@NotNull TranslationKey key, @NotNull Locale locale, boolean tryFallBack) {
		LanguageTable languageTable = getLanguageTable(locale);
		if (languageTable == null){
			languageTable = getLanguageTable();
		}
		return languageTable.getComponentFallBack(key);
	}

	@Override
	public @NotNull MessageInfoBuilder createMessage(@NotNull TranslationKey translation) {
		return new MessageInfoBuilder(translation).withLocale(this.getLocale()).useReceiverDelay(tryToUseReceiverLocale());
	}

	@Override
	public void send(@NotNull MessageInfo... messageInformation) throws ClassCastException {
		MultiMessageInfoBuilder multiMessageInfoBuilder = new MultiMessageInfoBuilder();
		for (MessageInfo messageInfo : messageInformation){
			multiMessageInfoBuilder.and(messageInfo);
		}
		send(multiMessageInfoBuilder.create());
	}

	@Override
	public void send(@NotNull MessageInfoBuilder... messageInformation) throws ClassCastException {
		MultiMessageInfoBuilder multiMessageInfoBuilder = new MultiMessageInfoBuilder();
		for (MessageInfoBuilder builder : messageInformation){
			multiMessageInfoBuilder.and(builder);
		}
		send(multiMessageInfoBuilder.create());
	}

	@Override
	public void send(@NotNull MultiMessageInfo... multiMessageInformation) throws ClassCastException {
		for (MultiMessageInfo info : multiMessageInformation){
			for (MessageInfo messageInfo : info.getMessages()){
				for (ComponentType componentType : getComponentTypeRegistry().getRegisteredComponentTypes()){
					for (Object receiverObj : messageInfo.getReceivers()) {
						Receiver receiver = convertReceiver(receiverObj);
						if (receiver == null){
							continue;
						}
						ParsedComponentPart part = parseComponentPart(messageInfo, componentType, receiver, isUseReceiverLocale());
						if (part == null){
							continue;
						}
						componentType.forward(receiver, part);
					}
				}
			}
		}
	}

	@Override
	public @NotNull TranslationKeyRegistry getTranslationKeyRegistry() {
		return translationKeyRegistry;
	}

	@Override
	public @Nullable LanguageTable getLanguageTable(@NotNull Locale locale) {
		return languages.get(locale);
	}

	@Override
	public @NotNull LanguageTable getLanguageTable() {
		return languages.get(getLocale());
	}

	@Override
	public @NotNull ComponentTypeRegistry getComponentTypeRegistry() {
		return Messenger.super.getComponentTypeRegistry();
	}

	@Override
	public void registerLanguageTable(@NotNull Locale locale, @NotNull LanguageTable table) {
		languages.put(locale, table);
	}

	@Override
	public @NotNull Locale getLocale() {
		return locale;
	}

	@Override
	public void registerReceiverConverter(Function<Object, Receiver> converter) {
		this.receiverConverterSet.add(converter);
	}

	@Override
	public void loadTranslations(@NotNull List<TranslationKey> translationKeys) {
		for (Locale locale : languages.keySet()){
			loadTranslations(locale, translationKeys);
		}
	}

	@Override
	public void loadTranslations(@NotNull TranslationKey[] translationKeys) {
		loadTranslations(List.of(translationKeys));
	}

	@Override
	public void loadTranslations(@NotNull Locale locale, @NotNull List<TranslationKey> translationKeys) {
		LanguageTable table = languages.get(locale);
		if (table == null){
			return;
		}
		table.getLanguageSource().loadAllComponents(translationKeys)
				.thenAccept((map)-> {
					map.values().stream().filter(Objects::nonNull).forEach(component->table.addComponentBase(component.getTranslationKey(), component));
				});
	}

	@Override
	public void loadTranslations(@NotNull Locale locale, @NotNull TranslationKey[] translationKeys) {
		loadTranslations(locale, List.of(translationKeys));
	}

	@Override
	public Component parsePlaceholders(@NotNull Receiver receiver, @NotNull Component component, @NotNull PlaceholderHookManager hookManager) {
		return component;
	}

	@Override
	public Component parsePlaceholders(@NotNull Receiver receiver, @NotNull Component component, @NotNull Collection<? extends Placeholder> placeholders) {
		return component;
	}

	@Override
	public boolean tryToUseReceiverLocale() {
		return useReceiverLocale;
	}

	@Override
	public void setPlaceholderHookManager(@NotNull PlaceholderHookManager hookManager) {
		this.placeholderHookManager = hookManager;
	}

	@Override
	public @NotNull PlaceholderHookManager getPlaceholderHookManager() {
		return this.placeholderHookManager;
	}

	@Override
	public void setPlaceholderLoader(@NotNull GlobalPlaceholderManager loader) {
		this.placeholderLoader = loader;
	}

	@Override
	public @NotNull GlobalPlaceholderManager getPlaceholderLoader() {
		return placeholderLoader;
	}

	@Override
	public void broadcast(@Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
		this.message(broadcast(), permission, translation, placeholderList, placeholders);
	}

	@Override
	public void broadcast(@Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
		this.message(broadcast(), permission, delay, translation, placeholderList, placeholders);
	}

	@Override
	public void broadcast(@Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		this.message(broadcast(), permission, translation, placeholders);
	}

	@Override
	public void broadcast(@Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		this.message(broadcast(), permission, delay, translation, placeholders);
	}

	@Override
	public void broadcast(@Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		this.message(broadcast(), permission, translation, placeholderList);
	}

	@Override
	public void broadcast(@Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		this.message(broadcast(), permission, delay, translation, placeholderList);
	}

	@Override
	public void broadcast(@Nullable Permission<Receiver> permission, @NotNull TranslationKey translation) {
		this.message(broadcast(), permission, translation);
	}

	@Override
	public void broadcast(@Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation) {
		this.message(broadcast(), permission, delay, translation);
	}

	@Override
	public void broadcast(@NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
		this.message(broadcast(), translation, placeholderList, placeholders);
	}

	@Override
	public void broadcast(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
		this.message(broadcast(), delay, translation, placeholderList, placeholders);
	}

	@Override
	public void broadcast(@NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		this.message(broadcast(), translation, placeholders);
	}

	@Override
	public void broadcast(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		this.message(broadcast(), delay, translation, placeholders);
	}

	@Override
	public void broadcast(@NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		this.message(broadcast(), translation, placeholderList);
	}

	@Override
	public void broadcast(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		this.message(broadcast(), delay, translation, placeholderList);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(Delay.NONE)
				.useReceiverDelay(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(delay)
				.useReceiverDelay(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.useReceiverDelay(tryToUseReceiverLocale())
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(delay)
				.useReceiverDelay(tryToUseReceiverLocale())
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.useReceiverDelay(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(delay)
				.useReceiverDelay(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.useReceiverDelay(tryToUseReceiverLocale())
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(delay)
				.useReceiverDelay(tryToUseReceiverLocale())
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.useReceiverDelay(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(delay)
				.useReceiverDelay(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.useReceiverDelay(tryToUseReceiverLocale())
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(delay)
				.useReceiverDelay(tryToUseReceiverLocale())
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		createMessage(translation)
				.withReceivers(receiver)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.useReceiverDelay(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		createMessage(translation)
				.withReceivers(receiver)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(delay)
				.useReceiverDelay(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.send(this);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, translation, placeholderList, placeholders);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, Placeholder... placeholders) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, delay, translation, placeholderList, placeholders);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, translation, placeholders);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, delay, translation, placeholders);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, translation, placeholderList);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, delay, translation, placeholderList);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @NotNull TranslationKey translation) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, translation);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission<Receiver> permission, @Nullable Delay delay, @NotNull TranslationKey translation) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, delay, translation);
	}

	@Override
	public void message(@NotNull Object receiver, @NotNull TranslationKey translation, Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, translation, placeholderList, placeholders);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, Placeholder... placeholders) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, delay, translation, placeholderList, placeholders);
	}

	@Override
	public void message(@NotNull Object receiver, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, translation, placeholders);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, delay, translation, placeholders);
	}

	@Override
	public void message(@NotNull Object receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, translation, placeholderList);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, delay, translation, placeholderList);
	}

	@Override
	public Receiver convertReceiver(@NotNull Object object) throws ClassCastException{
		for (Function<Object, Receiver> converter : receiverConverterSet){
			Receiver receiver = converter.apply(object);
			if (receiver != null){
				return receiver;
			}
		}
		throw new ClassCastException("Couldn't find a class converter for class: "+ object.getClass().getName());
	}

	@Override
	public Logger getLogger(){
		return logger;
	}

	@Override
	public Random getRandom() {
		return random;
	}
}

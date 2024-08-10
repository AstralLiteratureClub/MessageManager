package bet.astral.messenger.v2;

import bet.astral.messenger.v2.component.ComponentBase;
import bet.astral.messenger.v2.component.ComponentPart;
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
import bet.astral.messenger.v2.placeholder.hooks.PlaceholderHookManager;
import bet.astral.messenger.v2.placeholder.manager.PlaceholderManager;
import bet.astral.messenger.v2.placeholder.values.TranslationPlaceholderValue;
import bet.astral.messenger.v2.receiver.ForwardingReceiver;
import bet.astral.messenger.v2.receiver.Receiver;
import bet.astral.messenger.v2.task.IScheduler;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.messenger.v2.translation.TranslationKeyRegistry;
import bet.astral.more4j.tuples.Pair;
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
	private PlaceholderManager placeholderManager = PlaceholderManager.create();
	private Component prefix;
	private final Random random;
	private final Logger logger;
	@Setter
	private Locale locale;
	@Getter
	@Setter
	private boolean useReceiverLocale;
	@Setter
	private boolean sendASync = false;
	@Getter
	private boolean prefixDisabled = false;
	@Getter
	private boolean prefixDisabledForNextParse;
	private boolean sendTranslationKey = true;
	public AbstractMessenger(Logger logger) {
		this(logger, Randomly.RANDOM);
	}
	public AbstractMessenger(Logger logger, Random random) {
		this.logger = logger;
		this.random = random;
		translationKeyRegistry = TranslationKeyRegistry.create();
	}

	@Override
	public void setDefaultLocale(@NotNull LanguageSource defaultLocale) {
		this.locale = defaultLocale.getLocale();
		languages.put(getLocale(), LanguageTable.of(defaultLocale));
	}

	public boolean isASync() {
		return sendASync;
	}

	public AbstractMessenger setASync(boolean sendASync) {
		this.sendASync = sendASync;
		return this;
	}

	@NotNull
	public IScheduler getAsync(){
		//noinspection DataFlowIssue
		return DefaultScheduler.ASYNC_SCHEDULER;
	}



	@Override
	public @Nullable Component parseComponent(@NotNull MessageInfo messageInfo, @NotNull ComponentType componentType, @NotNull Receiver receiver) {
		Locale locale = useReceiverLocale ? receiver.getLocale() : messageInfo.getLocale();
		if (locale==null){
			locale = this.locale;
		}
		LanguageTable table = getLanguageTable(locale);
		if (table==null){
			locale = this.locale;
			table = getLanguageTable(locale);
			if (table==null){
				return null;
			}
		}
		Pair<ComponentBase, LanguageTable> pair = getBaseComponentAndTable(messageInfo.getTranslationKey(), locale, true);
		LanguageTable languageTable = pair.getSecond();
		ComponentBase base = pair.getFirst();
		if (base == null || base.isDisabled()){
			return null;
		}
		if (base.getParts()==null){
			return null;
		}
		ComponentPart part = base.getParts().get(componentType);
		if (part==null){
			return null;
		}
		Component component = part.getTextComponent();
		Map<String, Placeholder> placeholderMap = new HashMap<>(getPlaceholderManager().getGlobalPlaceholders());
		placeholderMap.putAll(languageTable.getPlaceholderManager().getGlobalPlaceholders());
		placeholderMap.putAll(messageInfo.getPlaceholders());
		if (base.getPlaceholders()!=null) {
			placeholderMap.putAll(base.getPlaceholders());
		}
		for (Map.Entry<String, Placeholder> entry : placeholderMap.entrySet()){
			Placeholder placeholder = entry.getValue();
			Component value = placeholder.getValue();
			if (placeholder.getValue() instanceof TranslationPlaceholderValue translationPlaceholderValue){
				value = parseComponent(translationPlaceholderValue.getTranslationKey(), locale, translationPlaceholderValue.getValueComponentType(), placeholderMap.values().toArray(Placeholder[]::new));
			}

			final Component finalValue = value;
			component = component.replaceText(b->b.match("%(?i)"+entry.getKey()+"%").replacement(finalValue));
		}

		if (!this.prefixDisabledForNextParse && this.getPrefix() != null && !this.prefixDisabled) {
			component = Component.empty().append(getPrefix()).append(component);
		}

		prefixDisabledForNextParse = false;
		return component;
	}

	private Map<String, Placeholder> toMap(@NotNull Collection<Placeholder> placeholders){
		Map<String, Placeholder> placeholderMap = new HashMap<>();
		for (Placeholder placeholder : placeholders){
			placeholderMap.put(placeholder.getKey(), placeholder);
		}
		return placeholderMap;
	}

	@Override
	public @Nullable ComponentBase getBaseComponent(@NotNull TranslationKey key, @NotNull Locale locale) {
		return getBaseComponent(key, locale, true);
	}

	@Override
	public @Nullable ComponentBase getBaseComponent(@NotNull TranslationKey key, @NotNull Locale locale, boolean tryFallBack) {
		LanguageTable languageTable = getLanguageTable(locale);
		if (languageTable == null){
			languageTable = getLanguageTable();
		}
		return languageTable.getComponentFallBack(key);
	}

	protected @NotNull Pair<ComponentBase, LanguageTable> getBaseComponentAndTable(TranslationKey translationKey, Locale locale, boolean tryFallBack){
		LanguageTable languageTable = getLanguageTable(locale);
		if (languageTable == null){
			languageTable = getLanguageTable();
		}
		return Pair.immutable(languageTable.getComponentFallBack(translationKey), languageTable);
	}

	@Override
	public @NotNull MessageInfoBuilder createMessage(@NotNull TranslationKey translation) {
		return new MessageInfoBuilder(translation).withLocale(this.getLocale()).useReceiverLocale(tryToUseReceiverLocale());
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
		for (MultiMessageInfo info : multiMessageInformation) {
			for (MessageInfo messageInfo : info.getMessages()) {
				if (messageInfo.getReceivers().isEmpty()) {
					continue;
				}
				Map<Object, Boolean> empty = new HashMap<>();
				Map<Object, Locale> locales = new HashMap<>();
				for (ComponentType componentType : getComponentTypeRegistry().getRegisteredComponentTypes()) {
					for (Object receiverObj : messageInfo.getReceivers()) {
						Receiver receiver = convertReceiver(receiverObj);
						if (receiver instanceof ForwardingReceiver forwardingReceiver){
							for (Receiver receiver1 : forwardingReceiver) {
								send(empty, locales, receiver1, messageInfo, componentType);
							}
						}
						if (receiver == null) {
							continue;
						}
						send(empty, locales, receiver, messageInfo, componentType);
					}
				}
			}
		}
	}

	private void send(Map<Object, Boolean> empty, Map<Object, Locale> locales, Receiver receiver, MessageInfo messageInfo, ComponentType componentType){
		if (empty.get(receiver) != null && empty.get(receiver)) {
			return;
		}
		Locale locale = locales.get(receiver);
		if (locale == null) {
			if (messageInfo.tryToUseReceiverLocale()) {
				locale = receiver.getLocale();
			} else if (isUseReceiverLocale()) {
				locale = receiver.getLocale();
			} else {
				locale = getLocale();
			}
			locales.put(receiver, locale);
		}
		ComponentBase componentBase = getBaseComponent(messageInfo.getTranslationKey(), locale);
		if (componentBase == null || componentBase.isDisabled()) {
			empty.put(receiver, true);
			return;
		}
		IScheduler scheduler = receiver.getScheduler();
		if (sendASync) {
			scheduler = getAsync();
		}
		Delay delay = messageInfo.getDelay();
		if (componentBase.getParts() == null || componentBase.getParts().isEmpty()) {
			empty.put(receiver, true);
			if (shouldSendTranslationKey()) {
				if (delay.delay() > 0) {
					scheduler.runLater(t -> receiver.sendMessage(Component.translatable(messageInfo.getTranslationKey())), delay);
				} else {
					scheduler.run(t -> receiver.sendMessage(Component.translatable(messageInfo.getTranslationKey())));
				}
			}
			return;
		}

		if (delay.delay() > 0) {
			scheduler.runLater(t -> {
				ParsedComponentPart part = parseComponentPart(messageInfo, componentType, receiver);
				if (part == null) {
					return;
				}
				componentType.forward(receiver, part);
			}, delay);
		} else {
			scheduler.run(t -> {
				ParsedComponentPart part = parseComponentPart(messageInfo, componentType, receiver);
				if (part == null) {
					return;
				}
				componentType.forward(receiver, part);
			});
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
	public void setPlaceholderManager(@NotNull PlaceholderManager manager) {
		this.placeholderManager = manager;
	}

	@Override
	public @NotNull PlaceholderManager getPlaceholderManager() {
		return placeholderManager;
	}

	@Override
	public void broadcast(@Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
		this.message(broadcast(), permission, translation, placeholderList, placeholders);
	}

	@Override
	public void broadcast(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
		this.message(broadcast(), permission, delay, translation, placeholderList, placeholders);
	}

	@Override
	public void broadcast(@Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		this.message(broadcast(), permission, translation, placeholders);
	}

	@Override
	public void broadcast(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		this.message(broadcast(), permission, delay, translation, placeholders);
	}

	@Override
	public void broadcast(@Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		this.message(broadcast(), permission, translation, placeholderList);
	}

	@Override
	public void broadcast(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		this.message(broadcast(), permission, delay, translation, placeholderList);
	}

	@Override
	public void broadcast(@Nullable Permission permission, @NotNull TranslationKey translation) {
		this.message(broadcast(), permission, translation);
	}

	@Override
	public void broadcast(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation) {
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
	public void message(@NotNull Receiver receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(Delay.NONE)
				.useReceiverLocale(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(delay)
				.useReceiverLocale(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.useReceiverLocale(tryToUseReceiverLocale())
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(delay)
				.useReceiverLocale(tryToUseReceiverLocale())
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.useReceiverLocale(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(delay)
				.useReceiverLocale(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission permission, @NotNull TranslationKey translation) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.useReceiverLocale(tryToUseReceiverLocale())
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation) {
		createMessage(translation)
				.withReceivers(receiver)
				.withPermission(permission)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(delay)
				.useReceiverLocale(tryToUseReceiverLocale())
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.useReceiverLocale(tryToUseReceiverLocale())
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
				.useReceiverLocale(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.useReceiverLocale(tryToUseReceiverLocale())
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
		createMessage(translation)
				.withReceivers(receiver)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(delay)
				.useReceiverLocale(tryToUseReceiverLocale())
				.addPlaceholders(placeholders)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		createMessage(translation)
				.withReceivers(receiver)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.useReceiverLocale(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.send(this);
	}

	@Override
	public void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
		createMessage(translation)
				.withReceivers(receiver)
				.withLocale(useReceiverLocale ? receiver.isLocaleSupported() ? receiver.getLocale() : getLocale() : getLocale())
				.withDelay(delay)
				.useReceiverLocale(tryToUseReceiverLocale())
				.addPlaceholders(placeholderList)
				.send(this);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, translation, placeholderList, placeholders);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, Placeholder... placeholders) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, delay, translation, placeholderList, placeholders);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, translation, placeholders);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, delay, translation, placeholders);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, translation, placeholderList);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, delay, translation, placeholderList);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission permission, @NotNull TranslationKey translation) throws ClassCastException {
		Receiver converted = convertReceiver(receiver);
		if (converted == null){
			return;
		}
		message(converted, permission, translation);
	}

	@Override
	public void message(@NotNull Object receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation) throws ClassCastException {
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

	@Nullable
	@Override
	public Component getPrefix() {
		return prefix;
	}

	@Override
	public void setPrefix(Component prefix) {
		this.prefix = prefix;
	}

	@Override
	public Messenger disablePrefixForNextParse() {
		this.prefixDisabledForNextParse = true;
		return this;
	}
	@Override
	public Messenger enablePrefixForNextParse() {
		this.prefixDisabledForNextParse = false;
		return this;
	}


	@Override
	public Messenger enablePrefix() {
		this.prefixDisabled = false;
		return this;
	}

	@Override
	public Messenger disablePrefix() {
		this.prefixDisabled = true;
		return this;
	}

	@Override
	public boolean shouldSendTranslationKey() {
		return sendTranslationKey;
	}

	@Override
	public void setSendTranslationKey(boolean value) {
		this.sendTranslationKey = value;
	}
}

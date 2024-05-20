package bet.astral.messenger.v2;

import bet.astral.messenger.v2.component.ComponentBase;
import bet.astral.messenger.v2.component.ComponentLoader;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.PlaceholderLoader;
import bet.astral.messenger.v2.placeholder.hooks.PlaceholderHookManager;
import bet.astral.messenger.v2.receiver.Receiver;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.platform.permission.Permission;
import bet.astral.platform.scheduler.delay.Delay;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.*;
import java.util.function.Function;

public abstract class AbstractMessenger implements Messenger {
	private final Map<TranslationKey, ComponentBase> components = new HashMap<>();
	private final Set<Function<Object, Receiver>> receiverConverterSet = new HashSet<>();
	private PlaceholderHookManager placeholderHookManager = PlaceholderHookManager.getGlobal();
	private PlaceholderLoader placeholderLoader = null;
	private Set<ComponentLoader> componentLoaderMap = new HashSet<>();
	private final Random random;
	private final Logger logger;
	@Getter
	@Setter
	private Locale locale;
	@Getter
	@Setter
	private boolean useReceiverLocale;
	@Setter
	private boolean sendASync = false;
	public AbstractMessenger(Logger logger) {
		this.logger = logger;
		this.random = Randomly.RANDOM;
		registerReceiverConverter(object ->object instanceof Receiver receiver ? receiver : null);
	}
	public AbstractMessenger(Logger logger, Random random) {
		this.logger = logger;
		this.random = random;
		registerReceiverConverter(object->object instanceof Receiver receiver ? receiver : null);
	}

	@Override
	public void registerReceiverConverter(java.util.function.Function<Object, Receiver> converter) {
		this.receiverConverterSet.add(converter);
	}

	@Override
	public boolean sendMessagesASync() {
		return sendASync;
	}

	abstract Component parsePlaceholders(@NotNull Receiver receiver, @NotNull PlaceholderHookManager hookManager);
	abstract Component parsePlaceholders(@NotNull Receiver receiver, @NotNull Collection<? extends Placeholder> placeholders);

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
	public void setPlaceholderLoader(@NotNull PlaceholderLoader loader) {
		this.placeholderLoader = loader;
	}

	@Override
	public @NotNull PlaceholderLoader getPlaceholderLoader() {
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

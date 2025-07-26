package bet.astral.messenger.v2.info;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v2.permission.Permission;
import bet.astral.messenger.v2.permission.Permissionable;
import bet.astral.messenger.v2.permission.PredicatePermission;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.collection.PlaceholderCollection;
import bet.astral.messenger.v2.translation.TranslationKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Simple builder to create new instances of {@link MessageInfo}
 */
public class MessageInfoBuilder {
	private final @NotNull TranslationKey translation;
	private @NotNull Permission permission;
	private @Nullable Locale locale;
	private boolean tryToUseReceiverLocale;
	private @NotNull Delay delay;
	private final @NotNull Collection<Object> receivers;
	private final @NotNull List<Placeholder> placeholders;
	private boolean hidePrefix;

	public MessageInfoBuilder(@NotNull TranslationKey translation) {
		this.translation = translation;
		receivers = new HashSet<>();
		locale = null;
		tryToUseReceiverLocale = true;
		permission = Permission.empty();
		delay = Delay.NONE;
		this.placeholders = new LinkedList<>();
	}

	public MessageInfoBuilder withPermission(@Nullable String permission){
		if (permission == null || permission.isBlank() || permission.isEmpty()){
			this.permission = Permission.empty();
			return this;
		}
		this.permission = Permission.of(permission);
		return this;
	}
	public MessageInfoBuilder withPermission(@Nullable Permission permission) {
		if (permission == null){
			this.permission = Permission.empty();
			return this;
		}
		this.permission = permission;
		return this;
	}
	public MessageInfoBuilder withPermission(@Nullable Predicate<Permissionable> predicate){
		if (predicate == null){
			this.permission = Permission.empty();
			return this;
		}
		this.permission = PredicatePermission.of(predicate);
		return this;
	}

	public MessageInfoBuilder withLocale(@NotNull Locale locale) {
		this.locale = locale;
		return this;
	}

	public MessageInfoBuilder useReceiverLocale(boolean tryToUseReceiverLocale){
		this.tryToUseReceiverLocale = tryToUseReceiverLocale;
		return this;
	}

	public MessageInfoBuilder withPlaceholder(@NotNull Placeholder placeholder){
		this.placeholders.add(placeholder);
		return this;

	}

	public MessageInfoBuilder withPlaceholders(@NotNull Placeholder... placeholders){
		this.placeholders.addAll(List.of(placeholders));
		return this;
	}

	public MessageInfoBuilder withPlaceholders(@NotNull Iterable<? extends Placeholder> placeholders){
		for (Placeholder placeholder : placeholders) {
			this.placeholders.add(placeholder);
		}
		return this;
	}

	public MessageInfoBuilder withPlaceholders(@NotNull PlaceholderCollection placeholders){
		this.placeholders.addAll(placeholders.toList());
		return this;
	}

	public MessageInfoBuilder withDelay(@Nullable Delay delay) {
		if (delay == null){
			this.delay = Delay.NONE;
			return this;
		}
		this.delay = delay;
		return this;
	}

	public MessageInfoBuilder withReceiver(@NotNull Object receiver){
		this.receivers.add(receiver);
		return this;
	}

	public MessageInfoBuilder withReceivers(@NotNull Object... receivers){
		this.receivers.addAll(Arrays.stream(receivers).toList());
		return this;
	}

	public MessageInfoBuilder withReceivers(@NotNull List<Object> receivers){
		this.receivers.addAll(receivers);
		return this;
	}

	/**
	 * Marks the prefix to be hidden, when the message is parsed and sent
	 * @return this
	 */
	public MessageInfoBuilder hidePrefix(){
		this.hidePrefix = true;
		return this;
	}

	/**
	 * Builds a new instance of {@link MessageInfo}
	 * @return new instance
	 */
	public MessageInfo build() {
		return new MessageInfoImpl(translation, locale, delay, tryToUseReceiverLocale, receivers, permission, PlaceholderCollection.map(placeholders), hidePrefix);
	}
	/**
	 * @deprecated use {@link #build()}
	 * @return new instance of message info
	 */
	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "2.5.0")
	public MessageInfo create() {
		return build();
	}

	public MessageInfoBuilder send(@NotNull Messenger messenger){
		messenger.send(this.build());
		return this;
	}
	@NotNull
	public MultiMessageInfoBuilder and(@NotNull MessageInfo messageInfo){
		return new MultiMessageInfoBuilder().and(this.create()).and(messageInfo);
	}
	@NotNull
	public MultiMessageInfoBuilder and(@NotNull MessageInfoBuilder messageInfoBuilder){
		return new MultiMessageInfoBuilder().and(this.create()).and(messageInfoBuilder.create());
	}
	@NotNull
	public MultiMessageInfoBuilder and(@NotNull TranslationKey translation, @NotNull Consumer<MessageInfoBuilder> messageInfoBuilder){
		MessageInfoBuilder builder = new MessageInfoBuilder(translation);
		messageInfoBuilder.accept(builder);
		return new MultiMessageInfoBuilder().and(this.create()).and(builder.create());
	}
}
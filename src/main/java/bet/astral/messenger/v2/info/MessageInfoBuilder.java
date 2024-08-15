package bet.astral.messenger.v2.info;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v2.permission.Permission;
import bet.astral.messenger.v2.permission.Permissionable;
import bet.astral.messenger.v2.permission.PredicatePermission;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.placeholder.collection.PlaceholderCollection;
import bet.astral.messenger.v2.placeholder.collection.PlaceholderList;
import bet.astral.messenger.v2.translation.TranslationKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MessageInfoBuilder {
	private final @NotNull TranslationKey translation;
	private @NotNull Permission permission;
	private @Nullable Locale locale;
	private boolean tryToUseReceiverLocale;
	private @NotNull Delay delay;
	private final @NotNull Collection<Object> receivers;
	private final @NotNull List<Placeholder> placeholders;

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

	public MessageInfoBuilder addPlaceholder(@NotNull Placeholder placeholder){
		this.placeholders.add(placeholder);
		return this;
	}

	public MessageInfoBuilder addPlaceholders(@NotNull Placeholder... placeholders){
		this.placeholders.addAll(List.of(placeholders));
		return this;
	}
	public MessageInfoBuilder addPlaceholders(@NotNull Collection<? extends Placeholder> placeholders){
		this.placeholders.addAll(placeholders);
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

	public MessageInfoBuilder clearReceivers(){
		this.receivers.clear();
		return this;
	}

	public MessageInfo create() {
		return new MessageInfoImpl(translation, locale, delay, tryToUseReceiverLocale, receivers, permission, PlaceholderCollection.map(placeholders));
	}

	public MessageInfoBuilder send(@NotNull Messenger messenger){
		messenger.send(this.create());
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
package bet.astral.messenger.v2;

import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v2.permission.Permission;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.receiver.Receiver;
import bet.astral.messenger.v2.translation.TranslationKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface MessageSender {
	void broadcast(@Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void broadcast(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void broadcast(@Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void broadcast(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void broadcast(@Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void broadcast(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void broadcast(@Nullable Permission permission, @NotNull TranslationKey translation);
	void broadcast(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation);
	void broadcast(@NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void broadcast(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void broadcast(@NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void broadcast(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void broadcast(@NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void broadcast(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void message(@NotNull Receiver receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void message(@NotNull Receiver receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void message(@NotNull Receiver receiver, @Nullable Permission permission, @NotNull TranslationKey translation);
	void message(@NotNull Receiver receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation);
	void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders);
	void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList);
	void message(@NotNull Object receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission permission, @NotNull TranslationKey translation) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation) throws ClassCastException;
	void message(@NotNull Object receiver, @NotNull TranslationKey translation, Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException;
	void message(@NotNull Object receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException;
	void message(@NotNull Object receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException;
}

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
	interface Packed  {
		Messenger getMessenger();
		default void broadcast(@Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
			getMessenger().broadcast(permission, translation, placeholderList, placeholders);
		}
		default void broadcast(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
			getMessenger().broadcast(permission, delay, translation, placeholderList, placeholders);
		}
		default void broadcast(@Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
			getMessenger().broadcast(permission, translation, placeholders);
		}
		default void broadcast(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
			getMessenger().broadcast(permission, delay, translation, placeholders);
		}
		default void broadcast(@Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
			getMessenger().broadcast(permission, translation, placeholderList);
		}
		default void broadcast(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
			getMessenger().broadcast(permission, delay, translation, placeholderList);
		}
		default void broadcast(@Nullable Permission permission, @NotNull TranslationKey translation) {
			getMessenger().broadcast(permission, translation);
		}
		default void broadcast(@Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation) {
			getMessenger().broadcast(permission, delay, translation);
		}
		default void broadcast(@NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
			getMessenger().broadcast(translation, placeholderList, placeholders);
		}
		default void broadcast(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
			getMessenger().broadcast(delay, translation, placeholderList, placeholders);
		}
		default void broadcast(@NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
			getMessenger().broadcast(translation, placeholders);
		}
		default void broadcast(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
			getMessenger().broadcast(delay, translation, placeholders);
		}
		default void broadcast(@NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
			getMessenger().broadcast(translation, placeholderList);
		}
		default void broadcast(@Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
			getMessenger().broadcast(delay, translation, placeholderList);
		}
		default void message(@NotNull Receiver receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
			getMessenger().message(receiver, permission, translation, placeholderList, placeholders);
		}
		default void message(@NotNull Receiver receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
			getMessenger().message(receiver, permission, delay, translation, placeholderList, placeholders);
		}
		default void message(@NotNull Receiver receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
			getMessenger().message(receiver, permission, translation, placeholders);
		}
		default void message(@NotNull Receiver receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
			getMessenger().message(receiver, permission, delay, translation, placeholders);
		}
		default void message(@NotNull Receiver receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
			getMessenger().message(receiver, permission, translation, placeholderList);
		}
		default void message(@NotNull Receiver receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
			getMessenger().message(receiver, permission, delay, translation, placeholderList);
		}
		default void message(@NotNull Receiver receiver, @Nullable Permission permission, @NotNull TranslationKey translation) {
			getMessenger().message(receiver, permission, translation);
		}
		default void message(@NotNull Receiver receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation) {
			getMessenger().message(receiver, permission, delay, translation);
		}
		default void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
			getMessenger().message(receiver, translation, placeholderList, placeholders);
		}
		default void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) {
			getMessenger().message(receiver, delay, translation, placeholderList, placeholders);
		}
		default void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
			getMessenger().message(receiver, translation, placeholders);
		}
		default void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) {
			getMessenger().message(receiver, delay, translation, placeholders);
		}
		default void message(@NotNull Receiver receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
			getMessenger().message(receiver, translation, placeholderList);
		}
		default void message(@NotNull Receiver receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) {
			getMessenger().message(receiver, delay, translation, placeholderList);
		}
		default void message(@NotNull Object receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) throws ClassCastException {
			getMessenger().message(receiver, permission, translation, placeholderList, placeholders);
		}
		default void message(@NotNull Object receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, Placeholder... placeholders) throws ClassCastException {
			getMessenger().message(receiver, permission, delay, translation, placeholderList, placeholders);
		}
		default void message(@NotNull Object receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException {
			getMessenger().message(receiver, permission, translation, placeholders);
		}
		default void message(@NotNull Object receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException {
			getMessenger().message(receiver, permission, delay, translation, placeholders);
		}
		default void message(@NotNull Object receiver, @Nullable Permission permission, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException {
			getMessenger().message(receiver, permission, translation, placeholderList);
		}
		default void message(@NotNull Object receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException {
			getMessenger().message(receiver, permission, delay, translation, placeholderList);
		}
		default void message(@NotNull Object receiver, @Nullable Permission permission, @NotNull TranslationKey translation) throws ClassCastException {
			getMessenger().message(receiver, permission, translation);
		}
		default void message(@NotNull Object receiver, @Nullable Permission permission, @Nullable Delay delay, @NotNull TranslationKey translation) throws ClassCastException {
			getMessenger().message(receiver, permission, delay, translation);
		}
		default void message(@NotNull Object receiver, @NotNull TranslationKey translation, Collection<? extends Placeholder> placeholderList, @NotNull Placeholder... placeholders) throws ClassCastException {
			getMessenger().message(receiver, translation, placeholderList, placeholders);
		}
		default void message(@NotNull Object receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList, Placeholder... placeholders) throws ClassCastException {
			getMessenger().message(receiver, delay, translation, placeholderList, placeholders);
		}
		default void message(@NotNull Object receiver, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException {
			getMessenger().message(receiver, translation, placeholders);
		}
		default void message(@NotNull Object receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Placeholder... placeholders) throws ClassCastException {
			getMessenger().message(receiver, delay, translation, placeholders);
		}
		default void message(@NotNull Object receiver, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException {
			getMessenger().message(receiver, translation, placeholderList);
		}
		default void message(@NotNull Object receiver, @Nullable Delay delay, @NotNull TranslationKey translation, @NotNull Collection<? extends Placeholder> placeholderList) throws ClassCastException {
			getMessenger().message(receiver, delay, translation, placeholderList);
		}
	}
}

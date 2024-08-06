package bet.astral.messenger.v2.cloud;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.cloud.placeholders.VariablePlaceholderCollections;
import bet.astral.messenger.v2.cloud.translation.CaptionTranslationKey;
import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.receiver.Receiver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionProvider;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.minecraft.extras.caption.ComponentCaptionFormatter;

import java.util.List;
import java.util.Locale;

/**
 * Extends caption provider and caption formatter
 * @param <C>
 */
public interface CaptionMessenger<C> extends Messenger, ComponentCaptionFormatter<C>, CaptionProvider<C> {

	@Override
	default @NonNull Component formatCaption(@NonNull Caption captionKey, @NonNull C recipient, @NonNull String caption, @NonNull CaptionVariable @NonNull ... variables) {
		Receiver receiver = convertReceiver(recipient);
		if (receiver == null){
			return Component.text(caption);
		}

		Component component = parseComponent(
				createMessage(CaptionTranslationKey.of(captionKey))
						.withReceiver(receiver)
						.useReceiverLocale(tryToUseReceiverLocale())
						.withLocale(getLocale())
						.addPlaceholders(VariablePlaceholderCollections.toList(variables))
						.create(),
				ComponentType.CHAT,
				receiver);
		if (component == null){
			return Component.text(caption);
		}
		return component;
	}

	@Override
	@NonNull
	default Component formatCaption(@NonNull Caption captionKey, @NonNull C recipient, @NonNull String caption, @NonNull List<@NonNull CaptionVariable> variables) {
		Receiver receiver = convertReceiver(recipient);
		if (receiver == null){
			return Component.text(caption);
		}
		Locale locale = getLocale();
		if (tryToUseReceiverLocale() && receiver.isLocaleSupported()){
			locale = receiver.getLocale();
		}

		Component component = parseComponent(
				createMessage(CaptionTranslationKey.of(captionKey))
						.withReceiver(receiver)
						.useReceiverLocale(tryToUseReceiverLocale())
						.withLocale(locale)
						.addPlaceholders(VariablePlaceholderCollections.toList(variables))
						.create(),
				ComponentType.CHAT,
				receiver
				);
		if (component == null){
			return Component.text(caption);
		}
		return component;
	}

	@Override
	default @Nullable String provide(@NonNull Caption caption, @NonNull C recipient) {
		Receiver receiver = convertReceiver(recipient);
		if (receiver == null) {
			return null;
		}

		CaptionTranslationKey captionTranslationKey = CaptionTranslationKey.of(caption);
		Component component = parseComponent(createMessage(captionTranslationKey).withLocale(receiver.getLocale()).create(), ComponentType.CHAT, receiver);
		if (component == null){
			return null;
		}
		PlainTextComponentSerializer plainTextComponentSerializer = PlainTextComponentSerializer.plainText();
		return plainTextComponentSerializer.serialize(component);
	}
}

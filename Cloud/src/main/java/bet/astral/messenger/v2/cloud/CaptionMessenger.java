package bet.astral.messenger.v2.cloud;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.cloud.placeholders.VariablePlaceholderCollections;
import bet.astral.messenger.v2.cloud.translation.CaptionTranslationKey;
import bet.astral.messenger.v2.cloud.translation.TranslationCaptionWithPlaceholders;
import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.placeholder.PlaceholderList;
import bet.astral.messenger.v2.receiver.Receiver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.caption.Caption;
import org.incendo.cloud.caption.CaptionProvider;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.minecraft.extras.caption.ComponentCaptionFormatter;
import org.incendo.cloud.translations.LocaleExtractor;
import org.incendo.cloud.translations.TranslationBundle;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

/**
 * Extends caption provider and caption formatter
 * @param <C>
 */
public interface CaptionMessenger<C> extends Messenger, ComponentCaptionFormatter<C>, CaptionProvider<C> {
	/**
	 * Returns the locale extractor made for {@link C}'s locale
	 * @return extractor
	 */
	@NotNull
	LocaleExtractor<C> getLocaleExtractor();

	@Override
	default @NonNull Component formatCaption(@NonNull Caption captionKey, @NonNull C recipient, @NonNull String caption, @NonNull CaptionVariable @NonNull ... variables) {
		Receiver receiver = null;
		try {
			receiver = convertReceiver(recipient);
		} catch(ClassCastException e){
			e.printStackTrace(System.out);
			return Component.text(caption);
		}
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

		CaptionTranslationKey translationKey = captionKey instanceof CaptionTranslationKey captionTranslationKey ? captionTranslationKey : CaptionTranslationKey.of(caption);

		Component component = parseComponent(
				createMessage(translationKey)
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
		PlaceholderList placeholders = null;
		if (captionTranslationKey instanceof TranslationCaptionWithPlaceholders captionWithPlaceholders){
			placeholders = captionWithPlaceholders.getPlaceholders();
		}
		if (placeholders==null){
			placeholders = new PlaceholderList();
		}

		Component component = parseComponent(createMessage(captionTranslationKey).withLocale(receiver.getLocale()).addPlaceholders(placeholders).create(), ComponentType.CHAT, receiver);
		if (component == null){
			return null;
		}
		PlainTextComponentSerializer plainTextComponentSerializer = PlainTextComponentSerializer.plainText();
		return plainTextComponentSerializer.serialize(component);
	}

	default void registerTo(@NotNull CommandManager<C> commandManager){
		commandManager.captionRegistry().registerProvider(this);

		LocaleExtractor<C> extractor = getLocaleExtractor();
		TranslationBundle<C> bundle = TranslationBundle.core(extractor);
		commandManager.captionRegistry().registerProvider(bundle);
	}
}

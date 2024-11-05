package bet.astral.messenger.v3.cloud.translation;

import bet.astral.messenger.v2.placeholder.collection.PlaceholderList;

public class TranslationCaptionWithPlaceholdersImpl extends CaptionTranslationImpl implements TranslationCaptionWithPlaceholders{
	private final PlaceholderList placeholders;
	public TranslationCaptionWithPlaceholdersImpl(String key, PlaceholderList placeholders) {
		super(key);
		this.placeholders = placeholders;
	}


	@Override
	public PlaceholderList getPlaceholders() {
		return placeholders;
	}
}

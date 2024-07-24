package bet.astral.messenger.v2.info;

import bet.astral.messenger.v2.annotations.Immutable;
import bet.astral.messenger.v2.delay.Delay;
import bet.astral.messenger.v2.permission.Permission;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.translation.TranslationKey;
import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

@Immutable
final class MessageInfoImpl implements MessageInfo {
	@NotNull
	private final TranslationKey translation;
	@Nullable
	private final Locale locale;
	@NotNull
	private final Delay delay;
	private final boolean tryToUseReceiverLocale;
	@NotNull
	private final Collection<Object> receivers;
	@NotNull
	private final Permission permission;
	@NotNull
	private final Map<String, Placeholder> placeholders;

	public MessageInfoImpl(@NotNull TranslationKey translation, @Nullable Locale locale, @NotNull Delay delay, boolean tryToUseReceiverLocale, @NotNull Collection<Object> receivers, @NotNull Permission permission, @NotNull Map<String, Placeholder> placeholders) {
		this.translation = translation;
		this.locale = locale;
		this.delay = delay;
		this.tryToUseReceiverLocale = tryToUseReceiverLocale;
		this.receivers = receivers;
		this.permission = permission;
		this.placeholders = placeholders;
	}

	@Override
	public @NotNull TranslationKey getTranslationKey() {
		return translation;
	}

	@Override
	public @NotNull Map<String, Placeholder> getPlaceholders() {
		return ImmutableMap.copyOf(placeholders);
	}

	@Override
	public @NotNull Permission getPermission() {
		return permission;
	}

	@Override
	public @Nullable Locale getLocale() {
		return locale;
	}

	@Override
	public boolean tryToUseReceiverLocale() {
		return tryToUseReceiverLocale;
	}

	@Override
	public @NotNull Delay getDelay() {
		return delay;
	}

	@Override
	public @NotNull Collection<Object> getReceivers() {
		return receivers;
	}
}

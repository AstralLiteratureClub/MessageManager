package bet.astral.messenger.v2.source.source.gson;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.component.ComponentBase;
import bet.astral.messenger.v2.component.ComponentBaseBuilder;
import bet.astral.messenger.v2.component.ComponentPart;
import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.source.source.AbstractFileLanguageSource;
import bet.astral.messenger.v2.translation.TranslationKey;
import bet.astral.messenger.v2.utils.Reloadable;
import com.google.gson.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.util.Ticks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class GsonLanguageSource extends AbstractFileLanguageSource implements Reloadable {
	private final List<ComponentType> types;
	private final ComponentSerializer<Component, Component, String> componentSerializer;
	private final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	private JsonObject source;
	public GsonLanguageSource(@NotNull Messenger messenger, @NotNull Locale locale, @NotNull File file, @NotNull ComponentSerializer<Component, Component, String> componentSerializer) {
		super(messenger, locale, file);
		try {
			FileReader reader = new FileReader(file);
			source = gson.fromJson(reader, JsonObject.class);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		this.componentSerializer = componentSerializer;
		types = new ArrayList<>();
		types.add(ComponentType.CHAT);
		types.add(ComponentType.ACTION_BAR);
		types.add(ComponentType.TITLE);
		types.add(ComponentType.SUBTITLE);
		types.add(ComponentType.PLAYER_LIST_FOOTER);
		types.add(ComponentType.PLAYER_LIST_HEADER);
	}

	@Override
	public @NotNull CompletableFuture<@Nullable ComponentBase> loadComponent(@NotNull TranslationKey translationKey) {
		return CompletableFuture.supplyAsync(() -> load(translationKey));
	}

	@Override
	public @NotNull CompletableFuture<@NotNull Map<@NotNull TranslationKey, @Nullable ComponentBase>> loadAllComponents(@NotNull TranslationKey... translationKeys) {
		return loadAllComponents(Arrays.stream(translationKeys).collect(Collectors.toList()));
	}

	@Override
	public @NotNull CompletableFuture<@NotNull Map<@NotNull TranslationKey, @Nullable ComponentBase>> loadAllComponents(@NotNull Collection<? extends TranslationKey> translationKeys) {
		return CompletableFuture.supplyAsync(()->{
			Map<TranslationKey, ComponentBase> componentBaseMap = new HashMap<>();
			for (TranslationKey translationKey : translationKeys) {
				componentBaseMap.put(translationKey, load(translationKey));
			}
			return componentBaseMap;
		});
	}


	private @Nullable ComponentBase load(@NotNull TranslationKey translationKey){
		JsonElement element = source.get(translationKey.getKey());
		if (element == null) {
			return null;
		}
		ComponentBaseBuilder builder = new ComponentBaseBuilder(translationKey);
		if (element.isJsonObject()) {
			JsonObject root = element.getAsJsonObject();
			for (ComponentType type : types) {
				for (String name : type.getNameAndAliases()) {
					ComponentPart part;
					JsonElement typeElement = root.get(name);
					if (typeElement == null) {
						continue;
					}
					if (typeElement.isJsonObject()) {
						JsonObject typeObject = typeElement.getAsJsonObject();
						JsonElement valueElement = typeObject.get("value");
						if (valueElement == null) {
							continue;
						}
						Component value = null;
						if (valueElement.isJsonArray()) {
							for (JsonElement arrayPart : valueElement.getAsJsonArray()) {
								if (value == null) {
									value = componentSerializer.deserialize(arrayPart.getAsString());
								} else {
									value = value.append(componentSerializer.deserialize(arrayPart.getAsString()));
								}
							}
						} else {
							value = componentSerializer.deserialize(valueElement.getAsString());
						}
						if (value == null){
							continue;
						}

						if (type == ComponentType.SUBTITLE || type == ComponentType.TITLE) {
							Duration in = typeObject.get("in") != null ? Ticks.duration(typeObject.get("in").getAsInt()) : Ticks.duration(10);
							Duration stay = typeObject.get("stay") != null ? Ticks.duration(typeObject.get("stay").getAsInt()) : Ticks.duration(70);
							Duration out = typeObject.get("out") != null ? Ticks.duration(typeObject.get("out").getAsInt()) : Ticks.duration(20);
							part = ComponentPart.of(value, in, stay, out);
						} else {
							part = ComponentPart.of(value);
						}
					} else if (typeElement.isJsonArray()) {
						Component value = null;
						for (JsonElement arrayPart : typeElement.getAsJsonArray()) {
							if (value == null) {
								value = componentSerializer.deserialize(arrayPart.getAsString());
							} else {
								value = value.append(componentSerializer.deserialize(arrayPart.getAsString()));
							}
						}
						if (value==null){
							continue;
						}
						part = ComponentPart.of(value);
					} else {
						part = ComponentPart.of(componentSerializer.deserialize(typeElement.getAsString()));
					}
					builder.setComponentPart(type, part);
				}
			}
		} else {
			builder.setComponentPart(ComponentType.CHAT, ComponentPart.of(componentSerializer.deserialize(element.getAsString())));
		}
		return builder.build();
	}

	@Override
	public void reload() {
		try {
			source = staticReload(getFile(), gson);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CompletableFuture<Void> reloadASync() {
		AtomicReference<GsonLanguageSource> reference = new AtomicReference<>(this);
		return CompletableFuture.supplyAsync(()->{
			try {
				reference.get().source = staticReload(reference.get().getFile(), reference.get().gson);
			} catch (IOException e) {
				getMessenger().getLogger().error("Caught an error while trying to complete async file reload.", e);
			}
			return null;
		});
	}

	static JsonObject staticReload(File file, Gson gson) throws IOException {
		FileReader reader = new FileReader(file);
		JsonObject object = gson.fromJson(reader, JsonObject.class);
		reader.close();
		return object;
	}
}

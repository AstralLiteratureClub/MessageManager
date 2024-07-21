package bet.astral.messenger.v2.locale.source.gson;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.component.ComponentBase;
import bet.astral.messenger.v2.component.ComponentBaseBuilder;
import bet.astral.messenger.v2.component.ComponentPart;
import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.locale.source.AbstractFileLanguageSource;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.translation.TranslationKey;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.kyori.adventure.util.Ticks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class GsonLanguageSource extends AbstractFileLanguageSource {
	private final List<ComponentType> types;
	private final ComponentSerializer<Component, Component, String> componentSerializer;
	private final Gson gson = new Gson();
	private final JsonObject source;
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
				ComponentPart part = null;
				JsonElement elem = root.get(type.getName());
				if (elem.isJsonObject()){
					JsonObject object = elem.getAsJsonObject();
					JsonElement value = object.get("value");
					Component valueComp;
					if (value.isJsonArray()){
						Component component = null;
						JsonArray array = value.getAsJsonArray();
						for (JsonElement jsonElement : array){
							if (component == null){
								component = componentSerializer.deserialize(gson.toJson(jsonElement));
							} else {
								component = component.appendNewline().append(componentSerializer.deserialize(gson.toJson(jsonElement)));
							}
						}
						valueComp = component;
					} else {
						valueComp = componentSerializer.deserialize(gson.toJson(value));
					}
					if (type==ComponentType.SUBTITLE||type==ComponentType.TITLE){
						Duration in = object.get("in") != null ? Ticks.duration(object.get("in").getAsInt()) : Ticks.duration(10);
						Duration stay = object.get("stay") != null ? Ticks.duration(object.get("in").getAsInt()) : Ticks.duration(70);
						Duration out = object.get("out") != null ? Ticks.duration(object.get("in").getAsInt()) : Ticks.duration(20);
						part = ComponentPart.of(valueComp, in, stay, out);
					} else {
						part = ComponentPart.of(valueComp);
					}
					builder.setComponentPart(type, part);
				} else {
					builder.setComponentPart(type, ComponentPart.of(componentSerializer.deserialize(gson.toJson(elem))));
				}
			}
			if (root.get("placeholders") != null && root.get("placeholders").isJsonArray()){
				for (JsonElement placeholder : root.get("placeholders").getAsJsonArray()){
					if (placeholder.isJsonObject()){
						JsonObject object = placeholder.getAsJsonObject();
						String key = object.get("name").getAsString();
						String parser = "gson";
						if (object.get("parser") != null){
							parser = object.get("parser").getAsString();
						}
						String type = "default";
						if (object.get("type") != null) {
							type = object.get("type").getAsString();
						}
						if (!type.equalsIgnoreCase("random")&&!type.equalsIgnoreCase("default")){
							type="default";
						}
						List<ComponentLike> valuePlural = new ArrayList<>();
						if (parser.equalsIgnoreCase("gson")||parser.equalsIgnoreCase("json")){
							JsonElement value = object.get("value");
							if (value==null){
								continue;
							}
							if (value.isJsonArray()){
								for (JsonElement valueElem : value.getAsJsonArray()){
									valuePlural.add(componentSerializer.deserialize(gson.toJson(valueElem)));
								}
							} else {
								valuePlural.add(componentSerializer.deserialize(gson.toJson(value)));
							}
						} else if (parser.equalsIgnoreCase("minimessage")){
							JsonElement value = object.get("value");
							if (value==null){
								continue;
							}
							if (value.isJsonArray()){
								for (JsonElement valueElem : value.getAsJsonArray()){
									valuePlural.add(MiniMessage.miniMessage().deserialize(gson.toJson(valueElem)));
								}
							} else {
								valuePlural.add(MiniMessage.miniMessage().deserialize(gson.toJson(value)));
							}
						}
						if (valuePlural.isEmpty()){
							continue;
						}
						if (type.equalsIgnoreCase("default")){
							Component value = null;
							for (ComponentLike valueSingle : valuePlural){
								if (value == null){
									value = valueSingle.asComponent();
								} else {
									value = value.appendNewline().append(valueSingle);
								}
							}
							builder.addPlaceholder(Placeholder.of(key, value));
						} else {
							builder.addPlaceholder(Placeholder.random(key, valuePlural));
						}
					}
				}
			}
		} else {
			builder.setComponentPart(ComponentType.CHAT, ComponentPart.of(componentSerializer.deserialize(gson.toJson(element))));
		}
		return builder.build();
	}
}

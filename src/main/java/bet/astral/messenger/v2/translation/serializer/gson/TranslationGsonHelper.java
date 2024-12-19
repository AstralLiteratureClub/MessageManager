package bet.astral.messenger.v2.translation.serializer.gson;

import bet.astral.messenger.v2.component.ComponentPart;
import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.component.TitleComponentPart;
import bet.astral.messenger.v2.translation.Translation;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class TranslationGsonHelper {
	public static @NotNull JsonObject getDefaults(@NotNull Class<?> clazz, @NotNull ComponentSerializer<Component, Component, String> serializer, @NotNull Gson gson){
		JsonObject fullObject = new JsonObject();
		Field[] fields = clazz.getFields();
		for (Field field : fields){
			if (field.canAccess(null)){
				Object object = null;
				try {
					object = field.get(null);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				if (object instanceof Translation translation){
					Translation.Message message = translation.getMessage();
					JsonElement element = null;
					// TODO fix this so it saves messages properly and allows other messages than CHAT being stored only
					if (translation.getMessage().useObject()) {
						JsonObject current = new JsonObject();
						for (Map.Entry<ComponentType, ComponentPart> entry : message.getComponentParts().entrySet()) {
							ComponentType type = entry.getKey();
							ComponentPart part = entry.getValue();
							boolean hidePrefix = translation.getMessage().getDisablePrefix().get(type);

							if (part instanceof TitleComponentPart title) {
								JsonObject titleObj = new JsonObject();
								titleObj.addProperty("in", title.getFadeIn().toMillis());
								titleObj.addProperty("stay", title.getStay().toMillis());
								titleObj.addProperty("out", title.getFadeOut().toMillis());
								titleObj.addProperty("value", serializer.serialize(part.getTextComponent()));
								if (hidePrefix){
									titleObj.addProperty("hidePrefix", hidePrefix);
								}
								current.add(type.getName(), titleObj);
							} else {
								if (hidePrefix){
									JsonObject obj = new JsonObject();
									obj.addProperty("value", serializer.serialize(part.getTextComponent()));
									obj.addProperty("hidePrefix", hidePrefix);
									current.add(type.getName(), obj);
								} else {
									current.addProperty(type.getName(), serializer.serialize(part.getTextComponent()));
								}
							}
						}
						element = current;
					} else {
						element = gson.toJsonTree(serializer.serialize(((ComponentPart) (List.of(message.getComponentParts().values().toArray()).get(0))).getTextComponent()));
					}
					fullObject.add(translation.getKey(), element);
				}
			}
		}
		return fullObject;
	}
}

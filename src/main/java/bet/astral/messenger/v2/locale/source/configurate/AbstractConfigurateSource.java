package bet.astral.messenger.v2.locale.source.configurate;

import bet.astral.messenger.v2.Messenger;
import bet.astral.messenger.v2.component.ComponentBase;
import bet.astral.messenger.v2.component.ComponentBaseBuilder;
import bet.astral.messenger.v2.component.ComponentType;
import bet.astral.messenger.v2.component.ComponentTypeRegistry;
import bet.astral.messenger.v2.locale.source.AbstractFileLanguageSource;
import bet.astral.messenger.v2.locale.source.FileLanguageSource;
import bet.astral.messenger.v2.locale.source.components.BasicComponent;
import bet.astral.messenger.v2.locale.source.components.TitleComponent;
import bet.astral.messenger.v2.placeholder.Placeholder;
import bet.astral.messenger.v2.translation.TranslationKey;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.util.Ticks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractConfigurateSource extends AbstractFileLanguageSource implements FileLanguageSource {
	protected final ConfigurationLoader<?> configurationLoader;
	protected final CommentedConfigurationNode root;
	protected AbstractConfigurateSource(@NotNull Messenger messenger, @NotNull Locale locale, File file, ConfigurationLoader<?> configurationLoader) throws ConfigurateException {
		super(messenger, locale, file);
		this.configurationLoader = configurationLoader;
		this.root = (CommentedConfigurationNode) configurationLoader.load();
	}

	@Override
	public @NotNull CompletableFuture<Placeholder> loadPlaceholder(@NotNull String key) {
		return null;
	}

	@Override
	public @NotNull CompletableFuture<@NotNull Map<@NotNull String, @NotNull Placeholder>> loadAllPlaceholders(@NotNull String key) {
		return null;
	}

	@Override
	public @NotNull CompletableFuture<@Nullable ComponentBase> loadComponent(@NotNull TranslationKey translationKey) {
		return CompletableFuture.supplyAsync(()-> load(translationKey));
	}

	@Override
	public @NotNull CompletableFuture<@NotNull Map<@NotNull TranslationKey, @Nullable ComponentBase>> loadAllComponents(@NotNull TranslationKey... translationKeys) {
		return CompletableFuture.supplyAsync(()->{
			Map<TranslationKey, ComponentBase> componentBaseMap = new HashMap<>();
			for (TranslationKey key : translationKeys){
				ComponentBase componentBase = load(key);
				componentBaseMap.put(key, componentBase);
			}
			return componentBaseMap;
		});	}

	@Override
	public @NotNull CompletableFuture<@NotNull Map<@NotNull TranslationKey, @Nullable ComponentBase>> loadAllComponents(@NotNull Collection<? extends TranslationKey> translationKeys) {
		return CompletableFuture.supplyAsync(()->{
			Map<TranslationKey, ComponentBase> componentBaseMap = new HashMap<>();
			for (TranslationKey key : translationKeys){
				ComponentBase componentBase = load(key);
				componentBaseMap.put(key, componentBase);
			}
			return componentBaseMap;
		});
	}

	private ComponentBase load(@NotNull TranslationKey translationKey){
		final String key = translationKey.getKey();
		final ComponentBaseBuilder componentBase = new ComponentBaseBuilder(translationKey);
		final ConfigurationNode messageNode = root.node((Object[]) key.split("\\."));
		final List<String> nodes = new ArrayList<>(Arrays.stream(key.split("\\.")).toList());
		try {
			if (messageNode.getString() != null){
				String val = messageNode.getString();
				BasicComponent basicComponent = new BasicComponent(val);
				componentBase.setComponentPart(ComponentType.CHAT,
						ComponentType.CHAT.getLoader().load(basicComponent));
				return componentBase.build();
			} else if (messageNode.isList()) {
				List<String> values = messageNode.getList(String.class);
				if (values == null){
					return null;
				}
				StringBuilder builder = new StringBuilder();
				AtomicBoolean isEmpty = new AtomicBoolean(true);
				values.forEach(value->{
					if (!isEmpty.get()){
						builder.append("\n");
					} else {
						isEmpty.set(false);
					}
					builder.append(value);
				});
				BasicComponent basicComponent = new BasicComponent(builder.toString());
				componentBase.setComponentPart(ComponentType.CHAT,
						ComponentType.CHAT.getLoader().load(basicComponent));
				return componentBase.build();
			} else if (messageNode.isMap()){
				ComponentTypeRegistry componentTypeRegistry = getMessenger().getComponentTypeRegistry();
				for (ComponentType componentType : componentTypeRegistry.getRegisteredComponentTypes()){
					List<String> cloneList = new ArrayList<>(nodes);
					cloneList.add(componentType.getName());
					ConfigurationNode node = messageNode.node(cloneList);
					if (node == null){
						for (String alias : componentType.getAliases()){
							List<String> cloneList2 = new ArrayList<>(cloneList);
							cloneList2.add(alias);
							node = root.node(cloneList2);
							if (node == null){
								continue;
							}
							cloneList = cloneList2;
							break;
						}
					}
					if (node == null){
						continue;
					}
					if (componentType.equals(ComponentType.SUBTITLE) || componentType.equals(ComponentType.TITLE)) {
						if (node.isMap()){
							String component = node.node("value").getString();
							List<String> cloneList3 = new ArrayList<>(cloneList);
							cloneList3.add("fade-in");
							ConfigurationNode inNode = root.node(cloneList3);
							cloneList3.remove(cloneList3.size()-1);
							cloneList3.add("stay");
							ConfigurationNode stayNode = root.node(cloneList3);
							cloneList3.remove(cloneList3.size()-1);
							cloneList3.add("fade-out");
							ConfigurationNode outNode = root.node(cloneList3);
							cloneList3.remove(cloneList3.size()-1);

							Title.Times times = Title.DEFAULT_TIMES;
							TitleComponent.Duration inDur = handleTimespan(inNode);
							if (inDur == null) {
								new TitleComponent.Duration(times.fadeIn().toMillis(), TimeUnit.MILLISECONDS);
							}
							TitleComponent.Duration stayDur = handleTimespan(stayNode);
							if (stayDur == null){
								stayDur = new TitleComponent.Duration(times.stay().toMillis(), TimeUnit.MILLISECONDS);
							}
							TitleComponent.Duration outDur = handleTimespan(outNode);
							if (outDur == null) {
								outDur = new TitleComponent.Duration(times.fadeOut().toMillis(), TimeUnit.MILLISECONDS);
							}

							componentBase.setComponentPart(
									componentType,
									componentType
											.getLoader()
											.load(
													new TitleComponent(
															component,
															inDur,
															stayDur,
															outDur
													)
											)
							);
							continue;
						}
					}
					if (node.isList()){
						List<String> stringList = node.getList(String.class);
						if (stringList == null){
							continue;
						}
						StringBuilder builder = new StringBuilder();
						for (String val : stringList) {
							if (!builder.isEmpty()) {
								builder.append("\n");
							}
							builder.append(val);
						}
						componentBase
								.setComponentPart(
										componentType,
										componentType.getLoader().load(
												new BasicComponent(builder.toString())
										)
								);
					} else {
						String value = node.getString();
						if (value == null){
							continue;
						}
						componentBase
								.setComponentPart(
										componentType,
										componentType.getLoader().load(
												new BasicComponent(value)
										)
								);
					}
				}
			} else {
				return null;
			}
		} catch (SerializationException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	public TitleComponent.Duration handleTimespan(ConfigurationNode node){
		if (node != null && node.isMap()){
			ConfigurationNode unit = node.node("timeunit");
			String unitStr = null;
			if (unit != null && unit.getString() != null){
				unitStr = unit.getString();
			}
			ConfigurationNode timeSpan = node.node("time-span");
			if (timeSpan != null && timeSpan.getInt(-1)>0){
				TimeUnit timeUnit = TimeUnit.valueOf(unitStr);
				int timeSpanLong = timeSpan.getInt();
				return new TitleComponent.Duration(timeSpanLong, timeUnit);
			}
		} else if (node  != null && node.getInt(-1)>0){
			return new TitleComponent.Duration(Ticks.duration(node.getInt()).toMillis(), TimeUnit.MILLISECONDS);
		}
		return null;
	}
}

package bet.astral.messenger.v2.paper.receiver;

import bet.astral.messenger.v2.paper.scheduler.EntityScheduler;
import bet.astral.messenger.v2.permission.Permission;
import bet.astral.messenger.v2.receiver.Receiver;
import bet.astral.messenger.v2.task.IScheduler;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.UUID;

public class PlayerReceiver implements Receiver {
	private final UUID uniqueId;
	private final EntityScheduler scheduler;

	public PlayerReceiver(Player player) {
		this.uniqueId = player.getUniqueId();
		this.scheduler = new EntityScheduler(player);
	}
	@Override
	public @NotNull IScheduler getScheduler() {
		return scheduler;
	}

	@Override
	public @NotNull Locale getLocale() {
		return Bukkit.getPlayer(uniqueId).locale();
	}

	public @Nullable Player getPlayer(){
		return Bukkit.getPlayer(uniqueId);
	}

	public @NotNull UUID getUniqueId(){
		return uniqueId;
	}

	@Override
	public boolean hasPermission(@NotNull Permission permission) {
		return permission.test(this);
	}

	@Override
	public boolean hasPermission(@NotNull String s) {
		return Bukkit.getPlayer(uniqueId).hasPermission(s);
	}

	@SuppressWarnings("UnstableApiUsage")
	public void sendMessage(final @NotNull Identity source, final @NotNull Component message, final @NotNull MessageType type) {
		getPlayer().sendMessage(source, message, type);
	}

	@Override
	public void deleteMessage(SignedMessage.Signature signature) {
		getPlayer().deleteMessage(signature);
	}

	@Override
	public void sendActionBar(final @NotNull Component message) {
		getPlayer().sendActionBar(message);
	}
	@Override
	public void sendPlayerListHeaderAndFooter(final @NotNull Component header, final @NotNull Component footer) {
		getPlayer().sendPlayerListHeaderAndFooter(header, footer);
	}

	@Override
	public <T> void sendTitlePart(@NotNull TitlePart<T> part, @NotNull T value) {
		getPlayer().sendTitlePart(part, value);
	}

	@Override
	public void clearTitle() {
		getPlayer().clearTitle();
	}

	@Override
	public void resetTitle() {
		getPlayer().resetTitle();
	}

	@Override
	public void showBossBar(@NotNull BossBar bar) {
		getPlayer().showBossBar(bar);
	}

	@Override
	public void hideBossBar(@NotNull BossBar bar) {
		getPlayer().hideBossBar(bar);
	}

	@Override
	public void playSound(@NotNull Sound sound) {
		getPlayer().playSound(sound);
	}

	@Override
	public void playSound(@NotNull Sound sound, double x, double y, double z) {
		getPlayer().playSound(sound, x, y, z);
	}

	@Override
	public void playSound(@NotNull Sound sound, Sound.Emitter emitter) {
		getPlayer().playSound(sound, emitter);
	}

	@Override
	public void stopSound(@NotNull SoundStop stop) {
		getPlayer().stopSound(stop);
	}

	@Override
	public void openBook(@NotNull Book book) {
		getPlayer().openBook(book);
	}

	@Override
	public void sendResourcePacks(@NotNull ResourcePackRequest request) {
		getPlayer().sendResourcePacks(request);
	}

	@Override
	public void removeResourcePacks(@NotNull UUID id, @NotNull UUID @NotNull ... others) {
		getPlayer().removeResourcePacks(id, others);
	}

	@Override
	public void clearResourcePacks() {
		getPlayer().clearResourcePacks();
	}
}














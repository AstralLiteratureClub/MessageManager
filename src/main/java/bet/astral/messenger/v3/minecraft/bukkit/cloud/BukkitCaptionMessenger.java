package bet.astral.messenger.v3.minecraft.bukkit.cloud;

import bet.astral.messenger.v3.minecraft.bukkit.BukkitMessenger;
import bet.astral.messenger.v3.cloud.CaptionMessenger;
import bet.astral.messenger.v3.cloud.CloudMessenger;
import org.slf4j.Logger;

import java.util.Random;

public abstract class BukkitCaptionMessenger<C> extends BukkitMessenger implements CaptionMessenger<C>, CloudMessenger {
	public BukkitCaptionMessenger(Logger logger, Random random) {
		super(logger, random);
	}

	public BukkitCaptionMessenger(org.slf4j.Logger logger) {
		super(logger);
	}
}

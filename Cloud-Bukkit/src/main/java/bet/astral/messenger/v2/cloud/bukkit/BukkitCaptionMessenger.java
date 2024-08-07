package bet.astral.messenger.v2.cloud.bukkit;

import bet.astral.messenger.v2.bukkit.BukkitMessenger;
import bet.astral.messenger.v2.cloud.CaptionMessenger;
import bet.astral.messenger.v2.cloud.CloudMessenger;
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

package bet.astral.messenger.v3.minecraft.paper.cloud;

import bet.astral.messenger.v3.cloud.CaptionMessenger;
import bet.astral.messenger.v3.cloud.CloudMessenger;
import bet.astral.messenger.v3.minecraft.paper.PaperMessenger;
import org.slf4j.Logger;

import java.util.Random;

public abstract class PaperCaptionMessenger<C> extends PaperMessenger implements CaptionMessenger<C>, CloudMessenger {
	public PaperCaptionMessenger(Logger logger) {
		super(logger);
	}

	public PaperCaptionMessenger(Logger logger, Random random) {
		super(logger, random);
	}
}

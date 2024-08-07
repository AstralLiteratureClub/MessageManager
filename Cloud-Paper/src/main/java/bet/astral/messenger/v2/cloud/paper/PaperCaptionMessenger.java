package bet.astral.messenger.v2.cloud.paper;

import bet.astral.messenger.v2.cloud.CaptionMessenger;
import bet.astral.messenger.v2.cloud.CloudMessenger;
import bet.astral.messenger.v2.paper.PaperMessenger;
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

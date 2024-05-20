package bet.astral.messenger.v2;

import java.util.Random;

/**
 * Allows easier random methods and returns random
 */
public interface Randomly {
	/**
	 * Default random used by every Randomly implementation
	 */
	Random RANDOM = new Random(System.currentTimeMillis()*System.nanoTime()*132);

	/**
	 * Returns the random used for the implementation
	 * @return random
	 */
	default Random getRandom() {
		return RANDOM;
	}

	/**
	 * Loops given amount of times randoms and after loops are done, returns the random.
	 * @param times times to execute random nextDouble
	 * @return random
	 */
	default Random getRandomBeforeSkip(int times){
		for (int i = 0; i < times; i++){
			getRandom().nextDouble();
		}
		return getRandom();
	}
}

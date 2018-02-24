package mining;

import java.io.IOException;

public class MiningManager {
	private static boolean currentlyMining;

	private MiningManager() throws IOException {
	}

	public static boolean isCurrentlyMining() {
		return currentlyMining;
	}

	public static void mine() {
		if (!isCurrentlyMining()) {
			currentlyMining = true;
		} else {
			return;
		}
	}

	public static void stopMining() {
		if (isCurrentlyMining()) {
			currentlyMining = false;
		} else {
			return;
		}
	}

}

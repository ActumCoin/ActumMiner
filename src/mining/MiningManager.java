package mining;

import multichain.command.MultiChainCommand;
import multichain.command.MultichainException;

public class MiningManager {
	private static boolean currentlyMining;
	private static MultiChainCommand mCommand;
	private static MiningManager instance;

	private MiningManager(MultiChainCommand m) {
		mCommand = m;
	}

	public static boolean isCurrentlyMining() {
		return currentlyMining;
	}

	public static void toggleMining() throws MultichainException {
		if (currentlyMining) {
			currentlyMining = false;
			// pause mining
			mCommand.getMiningCommand().pauseMining();
		} else {
			currentlyMining = true;
			// resume mining
			mCommand.getMiningCommand().resumeMining();
		}
	}
	
	public static void createInstance(MultiChainCommand m) {
		instance = new MiningManager(m);
	}

	public MiningManager getInstance() {
		return instance;
	}

}

package mining;

import java.util.concurrent.TimeUnit;

import multichain.command.MultiChainCommand;
import multichain.command.MultichainException;

public class MiningManager {
	private static boolean currentlyMining;
	private static MultiChainCommand mCommand;
	private static MiningManager instance;
	private static Thread keepMining = new Thread() {
		public void run() {
			if (currentlyMining) {
				try {
					mCommand.getMiningCommand().resumeMining();
					TimeUnit.SECONDS.sleep(1);
				} catch (MultichainException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	private MiningManager(MultiChainCommand m) {
		mCommand = m;
		try {
			mCommand.getMiningCommand().pauseMining();
		} catch (MultichainException e) {
			e.printStackTrace();
		}
	}

	public static boolean isCurrentlyMining() {
		return currentlyMining;
	}

	public static void toggleMining() throws MultichainException {
		if (currentlyMining) {
			currentlyMining = false;
			try {
				keepMining.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// pause mining
			mCommand.getMiningCommand().pauseMining();
		} else {
			currentlyMining = true;
			// resume mining
			mCommand.getMiningCommand().resumeMining();
			// start thread to keep it mining
			keepMining.start();
		}
	}

	public static void createInstance(MultiChainCommand m) {
		instance = new MiningManager(m);
	}

	public MiningManager getInstance() {
		return instance;
	}

}

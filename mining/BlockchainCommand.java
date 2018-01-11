package mining;

import java.io.IOException;

public class BlockchainCommand {

	private Runtime rt = Runtime.getRuntime();

	public void e(String to, String command) {
		String finalCommand = "";
		switch (to) {
		case "d":
			finalCommand = "multichaind ";
			break;
		case "u":
			finalCommand = "multichain-util ";
			break;
		case "c":
			finalCommand = "multichain-cli ";
			break;
		case "o":
			finalCommand = "multichain-cold ";
			break;
		default:
			// same as d
			finalCommand = "multichaind ";
		}
		finalCommand += command;
		try {
			Process p = rt.exec(finalCommand);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package mining;

import java.io.IOException;
import java.net.*;

public class MiningManager {
	private static boolean currentlyMining;
	private URL url;
	private URLConnection con;
	private HttpURLConnection http;

	private MiningManager() throws IOException {
		// setup connection to controlling server
		url = new URL("didnt.buy.domain/yet.so?this=is-placeholder");
		con = url.openConnection();
		http = (HttpURLConnection) con;
		http.setRequestMethod("POST");
		http.setDoOutput(true);
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

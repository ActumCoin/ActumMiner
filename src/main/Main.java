package main;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import gui.GUI;
import util.FourWords;
import util.Preferences;

public class Main {
	
	public static void main(String[] args) {
		// init
		new Preferences();
		new GUI();
		SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest256();

		String[] IDSet = Preferences.getIDSet();
		if (IDSet[0] == "" || IDSet[1] == "") {
			if (IDSet[0] == "") {
				byte[] d = digestSHA3.digest(ByteBuffer.allocate(4).putInt(ThreadLocalRandom.current().nextInt(0, 2147483647)).array());
				IDSet[0] = Hex.toHexString(d);
			}
			if (IDSet[1] == "") {
				String s = FourWords.generate();
				IDSet[1] = s;
			}
			Preferences.setIDSet(IDSet);
		}
		
		System.out.println(IDSet[0] + "\n" + IDSet[1]);

	}
	
}

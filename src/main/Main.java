package main;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import gui.GUI;
import util.Preferences;

public class Main {
	
	public static void main(String[] args) {
		// init
		Preferences p = new Preferences();
		
		System.out.println(p.address);
		
		// just hashing "Hello world" right now...
		String input = "Hello world";
		SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
		byte[] digest = digestSHA3.digest(input.getBytes());

		System.out.println("SHA3-512 = " + Hex.toHexString(digest));

	}
	
}

package main;

import java.util.List;

import gui.GUI;
import mining.BalanceManager;
import mining.ForwardSmartContract;
import mining.MiningManager;
import multichain.command.MultiChainCommand;
import multichain.command.MultichainException;
import util.Preferences;

public class Main {

	public static void main(String[] args) throws InterruptedException, MultichainException {
		new Preferences();
		// connect to blockchain
		String ip = "localhost";
		String port = "7440";
		String user = "multichainrpc";
		String pass = "password";
		MultiChainCommand m = new MultiChainCommand(ip, port, user, pass);

		// get address
		List<String> addresses = null;
		try {
			addresses = m.getAddressCommand().getAddresses();
		} catch (MultichainException e) {
			e.printStackTrace();
		}

		String address = null;

		for (String a : addresses) {
			address = a;
		}

		MiningManager.createInstance(m);
		BalanceManager.createInstance(m, address);

		new GUI();
		System.out.println(Preferences.getAddress());
		if (!Preferences.getAddress().isEmpty()) {
			ForwardSmartContract.setInstance(address, Preferences.getAddress());
			ForwardSmartContract.getInstance().thread.start();
		}

	}

}

package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.*;

import mining.ForwardSmartContract;
import mining.MiningManager;
import multichain.command.MultichainException;
import util.Preferences;

public class GUI extends JFrame {
	private JFrame f;
	
	public GUI() {
		// init
		setUIFont(new javax.swing.plaf.FontUIResource("Arial", Font.PLAIN, 26));
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// logo
		JLabel logo = new JLabel(new ImageIcon("res/logo.png"));
		logo.setBounds(274, 0, 171, 119);// gah! not quite centered, should be right another 0.5 pixels

		// mine button
		JButton mineButton = new JButton("Start Mining");
		mineButton.setBounds(260, 139, 210, 40);
		mineButton.setBackground(Color.WHITE);
		mineButton.setEnabled(Preferences.getAddress() != null);

		// info
		JLabel info = new JLabel("ActumMiner v1.0.0");
		info.setBounds(480, 10, 210, 40);
		info.setFont(new javax.swing.plaf.FontUIResource("Arial", Font.PLAIN, 22));

		// address button
		JButton addressButton = new JButton("Set Address");
		addressButton.setBounds(10, 10, 210, 40);
		addressButton.setBackground(Color.WHITE);

		// display current address
		JLabel currentAddress = new JLabel(Preferences.getAddress());
		currentAddress.setBounds(10, 10, 210, 100);
		currentAddress.setFont(new javax.swing.plaf.FontUIResource("1234", Font.ITALIC, 14));

		// log
		LiveLog log = new LiveLog("Log", new String[7], 20, 220, 660, 276,
				new String[] { "Ready to mine...", "Currently mining..." }, 0);

		// button listeners
		mineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// toggle mining
				if (MiningManager.isCurrentlyMining()) {
					try {
						MiningManager.toggleMining();
					} catch (MultichainException e1) {
						e1.printStackTrace();
					}
					mineButton.setText("Start Mining");
					log.log("Stopped mining", 0);
				} else {
					try {
						MiningManager.toggleMining();
					} catch (MultichainException e1) {
						e1.printStackTrace();
					}
					mineButton.setText("Stop Mining");
					log.log("Started mining", 1);
				}
			}
		});

		addressButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = (String) JOptionPane.showInputDialog(f, "Paste your wallet address",
						"Paste your wallet address", JOptionPane.PLAIN_MESSAGE, null, null, "");

				if ((s != null) && (s.length() > 0)) {
					Preferences.setAddress(s);
					currentAddress.setText(s);
					
					// mine button enabled
					mineButton.setEnabled(Preferences.getAddress() != null);
					
					// log
					log.log("Address updated", -1);
					
					// restart forward smart contract
					ForwardSmartContract.getInstance().thread.interrupt();
					ForwardSmartContract.setInstance(ForwardSmartContract.getInstance().getTxAddress(), Preferences.getAddress());
				}
			}
		});

		// window close listener
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (MiningManager.isCurrentlyMining()) {
					int n = JOptionPane.showConfirmDialog(f,
							"You are currently mining, would you like to stop and exit?", "Currently mining",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (n == JOptionPane.YES_OPTION) {
						try {
							MiningManager.toggleMining();
							ForwardSmartContract.getInstance().thread.interrupt();
						} catch (MultichainException e1) {
							e1.printStackTrace();
						}
						dispose();
					}
				} else {
					dispose();
				}
			}
		});

		add(logo);
		add(mineButton);
		add(info);
		add(addressButton);
		add(currentAddress);
		add(log.getLabel());

		// icon
		try {
			setIconImage(ImageIO.read(new File("res/logo.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		setTitle("ActumMiner");
		setSize(720, 576);
		setLayout(null);
		setResizable(false);
		setVisible(true);
	}

	public static void setUIFont(javax.swing.plaf.FontUIResource f) {
		Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put(key, f);
		}
		UIManager.put("ToolTip.font", new javax.swing.plaf.FontUIResource(f.getFontName(), Font.ITALIC, 14));
	}

}

package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.*;
import java.util.Enumeration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import main.Main;
import mining.MiningManager;
import util.Preferences;

public class GUI extends JFrame {
	private JFrame f;
	private boolean isPreferences = false;

	public GUI() {
		// init
		setUIFont(new javax.swing.plaf.FontUIResource("1234", Font.PLAIN, 26));
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// logo
		JLabel logo = new JLabel(new ImageIcon("C:\\xampp\\htdocs\\ActumCoin\\logo.png"));
		logo.setBounds(274, 0, 171, 119);// gah! not quite centered, should be right another 0.5 pixels

		// mine button
		JButton mineButton = new JButton("Start Mining");
		mineButton.setBounds(260, 139, 200, 40);
		mineButton.setBackground(Color.WHITE);

		// preferences button
		JButton preferencesButton = new JButton("Preferences");
		preferencesButton.setBounds(480, 10, 210, 40);
		preferencesButton.setBackground(Color.WHITE);

		// preferences stuff
		CheckBox linkCheckBox = new CheckBox("Link with wallet", Preferences.isLink());
		linkCheckBox.setBounds(480, 60, 200, 30);
		linkCheckBox.setVisible(false);
		linkCheckBox.setToolTipText("This allows ActumMiner to automatically sync with your ActumWallet, if it's on this PC.");
		CheckBox publicStatsCheckBox = new CheckBox("Public stats", Preferences.isPublicStats());
		publicStatsCheckBox.setBounds(480, 100, 200, 30);
		publicStatsCheckBox.setVisible(false);
		publicStatsCheckBox.setToolTipText("This allows anyone to view your stats on actumcrypto.org/mine-stats.");
		
		// address button
		JButton addressButton = new JButton("Set Address");
		addressButton.setBounds(10, 10, 210, 40);
		addressButton.setBackground(Color.WHITE);

		// display current address
		JLabel currentAddress = new JLabel(Preferences.getAddress());
		currentAddress.setBounds(10, 10, 210, 100);
		currentAddress.setFont(new javax.swing.plaf.FontUIResource("1234", Font.ITALIC, 14));

		// button listeners
		mineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// toggle mining
				if (MiningManager.isCurrentlyMining()) {
					mineButton.setText("Start Mining");
					MiningManager.stopMining();
				} else {
					mineButton.setText("Stop Mining");
					MiningManager.mine();
				}
			}
		});

		preferencesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isPreferences = !isPreferences;
				linkCheckBox.setVisible(isPreferences);
				publicStatsCheckBox.setVisible(isPreferences);
				if (isPreferences) {
					// if already closed
					preferencesButton.setText("Save");
				} else {
					// if already open\
					Preferences.setLink(linkCheckBox.isChecked());
					Preferences.setPublicStats(publicStatsCheckBox.isChecked());
					preferencesButton.setText("Preferences");
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
				}
			}
		});

		// window close listener
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (MiningManager.isCurrentlyMining() ) {
					int n = JOptionPane.showConfirmDialog(f,
							"You are currently mining, would you like to stop and exit?", "Currently mining",
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (n == JOptionPane.YES_OPTION) {
						MiningManager.stopMining();
						dispose();
					}
				} else {
					dispose();
				}
			}
		});

		add(logo);
		add(mineButton);
		add(preferencesButton);
		add(linkCheckBox);
		add(publicStatsCheckBox);
		add(addressButton);
		add(currentAddress);

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
		UIManager.put("ToolTip.font", new javax.swing.plaf.FontUIResource("1234", Font.ITALIC, 14));
	}

}

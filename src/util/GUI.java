package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.*;

import mining.MiningManager;

public class GUI extends JFrame {
	JFrame f;

	public GUI() {
		setUIFont(new javax.swing.plaf.FontUIResource("1234", Font.PLAIN, 26));

		// logo
		JLabel logo = new JLabel(new ImageIcon("C:\\xampp\\htdocs\\ActumCoin\\logo.png"));
		logo.setBounds(274, 10, 171, 119);// gah! not quite centered, should be right another 0.5 pixels

		// mine button
		JButton mineButton = new JButton("Start Mining");
		mineButton.setBounds(260, 139, 200, 40);
		mineButton.setBackground(Color.WHITE);

		// preferences button
		JButton preferencesButton = new JButton("Preferences");
		preferencesButton.setBounds(480, 10, 210, 40);
		preferencesButton.setBackground(Color.WHITE);

		// address button
		JButton addressButton = new JButton("Set Address");
		addressButton.setBounds(10, 10, 210, 40);
		addressButton.setBackground(Color.WHITE);

		// display current address
		JLabel currentAddress = new JLabel(Preferences.getAddress());
		currentAddress.setBounds(10, 10, 100, 100);
		currentAddress.setFont(new javax.swing.plaf.FontUIResource("1234", Font.ITALIC, 14));

		// listeners
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

		addressButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = (String) JOptionPane.showInputDialog(f, "Set your wallet address", JOptionPane.PLAIN_MESSAGE);

				if ((s != null) && (s.length() > 0)) {
					Preferences.setAddress(s);
					currentAddress.setText(s);
				}
			}
		});

		add(logo);
		add(mineButton);
		add(preferencesButton);
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
	}

}

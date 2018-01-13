package util;

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

		JLabel logo = new JLabel(new ImageIcon("C:\\xampp\\htdocs\\ActumCoin\\logo.png"));
		logo.setBounds(274, 10, 171, 119);// gah! not quite centered, should be right another 0.5 pixels

		JButton b = new JButton("Start Mining");
		b.setBounds(260, 139, 200, 40);

		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// toggle mining
				if (MiningManager.isCurrentlyMining()) {
					b.setText("Start Mining");
					MiningManager.stopMining();
				} else {
					b.setText("Stop Mining");
					MiningManager.mine();
				}
			}
		});

		add(logo);
		add(b);

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

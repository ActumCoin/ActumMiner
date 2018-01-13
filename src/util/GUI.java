package util;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.*;

public class GUI extends JFrame {
	JFrame f;

	public GUI() {
		setUIFont(new javax.swing.plaf.FontUIResource("1234", Font.PLAIN, 16));

		JLabel logo = new JLabel(new ImageIcon("logo.png"));
		logo.setBounds(0, 0, 171, 119);

		JButton b = new JButton("foo");
		b.setBounds(130, 100, 80, 20);

		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// nothing yet
			}
		});

		add(logo);
		add(b);

		setSize(720, 576);
		setLayout(null);
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

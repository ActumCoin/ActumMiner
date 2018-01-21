package gui;

import java.awt.Color;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class LiveLog {

	private String[] log;
	private JLabel label;

	public LiveLog(String name, String[] l, int x, int y, int w, int h) {
		log = l;
		
		String logOut = "<html>";

		log = new String[l.length];
		
		for (int i = 0; i < l.length - 1; i++) {
			log[i] = l[i + 1];
		}
		
		for (int i = 0; i < l.length; i++) {
			if (log[i] != null) {
				logOut += log[i] + "<br>";
			} else {
				logOut += "<br>";
			}
		}
		
		logOut += "</html>";
		
		label = new JLabel(logOut);
		label.setBounds(x, y, w, h);
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), name);
		border.setTitleJustification(TitledBorder.CENTER);
		label.setBorder(border);
	}

	public void log(String add) {
		String logOut = "<html>";
		String[] oldLog = log;

		log = new String[oldLog.length];
		
		for (int i = 0; i < oldLog.length - 1; i++) {
			log[i] = oldLog[i + 1];
		}
		
		log[oldLog.length - 1] = add;
		
		for (int i = 0; i < oldLog.length; i++) {
			if (log[i] != null) {
				logOut += log[i] + "<br>";
			} else {
				logOut += "<br>";
			}
		}
		
		logOut += "</html>";

		label.setText(logOut);
	}

	public JLabel getLabel() {
		return label;
	}

}

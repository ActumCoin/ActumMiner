package gui;

import java.awt.Color;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class LiveLog {
	private String[] log;
	private String[] statusSet;
	private JLabel label;
	private int status;
	private DateFormat df;

	public LiveLog(String name, String[] l, int x, int y, int w, int h, String[] sS, int s) {
		log = l;
		statusSet = sS;
		status = s;
		
		df = DateFormat.getTimeInstance();
		
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
		
		logOut += "<hr>" + statusSet[status] + "</html>";
		
		label = new JLabel(logOut);
		label.setBounds(x, y, w, h);
		TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), name);
		border.setTitleJustification(TitledBorder.CENTER);
		label.setBorder(border);
	}

	public void log(String add, int s) {
		Calendar cal = Calendar.getInstance();
		Timestamp time = new Timestamp(cal.getTimeInMillis());
		
		add = df.format(new Date(time.getTime())) + " " + add;
		
		String logOut = "<html>";
		String[] oldLog = log;
		status = (s > -1 ? s : status);

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
		
		logOut += "<hr>" + statusSet[status] + "</html>";

		label.setText(logOut);
	}

	public JLabel getLabel() {
		return label;
	}

	public int getStatus() {
		return status;
	}

}

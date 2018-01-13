package util;

import javax.swing.*;

public class GUI extends JFrame {
	JFrame f;
	
	public GUI() {
		JButton b = new JButton("foo");
		b.setBounds(130,100,50,20);
	          
		add(b);
	          
		setSize(400,500); 
		setLayout(null);
		setVisible(true);
	}
	
}

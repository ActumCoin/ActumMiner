package util;

public class Preferences extends XMLReaderWriter {
	
	public Preferences() {
		readXML("preferences.xml");
		this.address = getAddress();
	}

	public static String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
		writeXML("preferences.xml");
	}
	
}

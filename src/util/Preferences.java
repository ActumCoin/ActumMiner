package util;

public class Preferences extends XMLReaderWriter {
	private static final String ADDRESS = "preferences.xml";
	
	public Preferences() {
		readXML(ADDRESS);
	}

	public static String getAddress() {
		return address;
	}
	
	public static void setAddress(String a) {
		address = a;
		writeXML(ADDRESS);
	}
	
}

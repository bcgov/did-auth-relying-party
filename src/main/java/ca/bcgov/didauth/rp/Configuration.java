package ca.bcgov.didauth.rp;

public class Configuration {

	public static final int DEFAULT_PORT = 8080;

	static int port() {

		String port = System.getenv("port");
		if (port == null) return DEFAULT_PORT;

		return Integer.parseInt(port);
	}
}

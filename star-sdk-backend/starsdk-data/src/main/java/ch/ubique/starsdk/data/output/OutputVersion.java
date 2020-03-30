package ch.ubique.starsdk.data.output;

/**
 * Version enumeration for the output-handler.
 * <ul>
 * <li>V1: First and base version of the application meteov2</li>
 * </ul>
 *
 * @author alig
 */
public enum OutputVersion {

	V1("v1");

	private String version;

	private OutputVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}
}

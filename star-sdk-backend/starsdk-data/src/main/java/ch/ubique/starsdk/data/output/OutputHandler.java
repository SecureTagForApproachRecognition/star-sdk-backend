package ch.ubique.starsdk.data.output;

/**
 * Listener interface for outputting data. The handlers are attached to the
 * importer and are called as soon as the importer is done.
 *
 * @author alig
 */
public interface OutputHandler {

	public void output(Object object, String destination);

	/**
	 * Old standard output. Outputs for {@link OutputVersion#V1}
	 *
	 * @param object
	 * @param destination
	 * @param metaData
	 */
	public void output(Object object, String destination,
					   OutputMetaData metaData);

	public void output(Object object, String destination,
					   OutputMetaData metaData, OutputVersion... version);
}

package ch.ubique.starsdk.data.output;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Outputhandler that generates a JSON file for any Object and puts it on a
 * destination on the local file system of in case a {@link File} is given, the
 * file is copied.
 * <p>
 * TODO: Is this really a good idea to have this outputer handler dealing with
 * objects to json and moving files? <br/>
 * <b>Note:</b> Versions not supported.
 *
 * @author alig
 */
public class LocalFSOutputHandler extends JsonOutputHandler {

	final static Logger logger = LoggerFactory.getLogger(LocalFSOutputHandler.class);

	private String baseDirectory;

	public LocalFSOutputHandler() {
	}

	public LocalFSOutputHandler(String baseDirectory) {
		this.setBaseDirectory(baseDirectory);
	}

	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
		// make sure it exists, if not, try to create.
		File dir = new File(baseDirectory);
		if (!dir.exists()) {
			boolean created = dir.mkdirs();
			if (!created) {
				throw new IllegalStateException("Base directory does not exist!");
			}
		}
	}

	@Override
	public void output(Object object, String destination) {
		assert destination != null;
		File destinationFile = new File(baseDirectory + File.separator + destination);
		if (destination.contains("/")) {
			destinationFile.getParentFile().mkdirs();
		}
		if (object instanceof File) {
			File file = (File) object;
			try {
				FileUtils.copyFile(file, destinationFile);
			} catch (IOException ioe) {
				logger.error("Exception copying file to destination: ", ioe);
			}
		} else if (object instanceof byte[]) {
			try {
				FileUtils.writeByteArrayToFile(destinationFile, (byte[]) object);
			} catch (IOException ioe) {
				logger.error("Exception writing to file: ", ioe);
			}
		} else {
			try {
				objectMapper.writeValue(destinationFile, object);
			} catch (IOException ioe) {
				logger.error("Exception writing JSON file: ", ioe);
			}
		}
	}

	@Override
	public void output(Object object, String destination, OutputMetaData metaData, OutputVersion... version) {
		output(object, destination, metaData);
	}

	@Override
	public void output(Object object, String destination, OutputMetaData metaData) {
		output(object, destination);
	}
}

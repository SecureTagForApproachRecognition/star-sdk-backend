package ch.ubique.starsdk.data.output;

import ch.ubique.starsdk.data.cache.CacheHeaderUtility;
import ch.ubique.starsdk.data.util.FileUtility;

import com.microsoft.azure.storage.blob.BlobProperties;
import com.microsoft.azure.storage.blob.CloudBlobDirectory;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class AzureBlobOutputHandler extends JsonOutputHandler {

	private static final Logger logger = LoggerFactory.getLogger(AzureBlobOutputHandler.class);
	private final CloudBlobDirectory cloudBlobDirectory;
	private DateFormat httpDateFormat;

	public AzureBlobOutputHandler(CloudBlobDirectory cloudBlobDirectory) {
		this.cloudBlobDirectory = cloudBlobDirectory;
		DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		this.httpDateFormat = df;
	}

	@Override
	public void output(Object object, String destination) {
		output(object, destination, new OutputMetaData());
	}

	@Override
	public void output(Object object, String destination, OutputMetaData metaData) {
		output(object, destination, metaData, OutputVersion.V1);
	}

	@Override
	public void output(Object object, String destination, OutputMetaData metaData, OutputVersion... versions) {
		for (OutputVersion version : versions) {
			String versionedDestination = version.getVersion() + "/" + destination;
			if (!(object instanceof File)) {
				internalOutput(object, versionedDestination, metaData, true);
			} else {
				internalOutput(object, versionedDestination, metaData, false);
			}
		}
	}

	private void internalOutput(Object object, String destination, OutputMetaData metaData, boolean gzip) {
		InputStream input = null;
		if (object instanceof File) {
			try {
				input = new FileInputStream((File) object);
			} catch (FileNotFoundException e) {
				logger.error("could not read file", e);
			}
		} else if (object instanceof Path) {
			try {
				Path path = (Path) object;
				input = new FileInputStream(path.toFile());
			} catch (FileNotFoundException e) {
				logger.error("could not read file", e);
			}
		} else if (object instanceof byte[]) {
			try {
				byte[] data = (byte[]) object;
				if (gzip) {
					data = FileUtility.gZip(data);
				}
				input = new ByteArrayInputStream(data);

			} catch (Exception ace) {
				logger.error("Exception uploading file to Amazon S3: ", ace);
			}
		} else {
			try {
				byte[] data = objectMapper.writeValueAsBytes(object);
				if (gzip) {
					data = FileUtility.gZip(data);
				}

				input = new ByteArrayInputStream(data);
			} catch (Exception e) {
				logger.error("Exception uploading JSON file to Amazon S3", e);
			}

		}
		try {
			CloudBlockBlob blob = cloudBlobDirectory.getBlockBlobReference(destination);
			blob.upload(input, input.available());
			BlobProperties blobProperties = blob.getProperties();
			blobProperties.setContentType(metaData.getContentType());
			if (gzip) {
				blobProperties.setContentEncoding("gzip");
			}
			blob.uploadProperties();
			HashMap<String, String> azureMetaData = createAzureMetaData(metaData);
			if (!azureMetaData.isEmpty()) {
				blob.setMetadata(createAzureMetaData(metaData));
				blob.uploadMetadata();
			}
		} catch (Exception e) {
			logger.error("blob storage upload failed", e);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				logger.warn("failed to close input stream", e);
			}

		}
	}

	/**
	 * Creates the azure meta data object based on the given ouput meta object.
	 *
	 * @param metaData
	 * @return
	 */
	private HashMap<String, String> createAzureMetaData(OutputMetaData metaData) {
		HashMap<String, String> azureMetaData = new HashMap<String, String>();
		if (metaData.getNextRefresh() != null) {
			putMetaData(azureMetaData, CacheHeaderUtility.NEXT_REFRESH_SHORT,
					getHTTPDateString(metaData.getNextRefresh()));
		}
		if (metaData.getBestBefore() != null) {
			putMetaData(azureMetaData, CacheHeaderUtility.BEST_BEFORE_SHORT,
					getHTTPDateString(metaData.getBestBefore()));
		}
		if (metaData.getCache() != null) {
			putMetaData(azureMetaData, CacheHeaderUtility.CACHE_SHORT, metaData.getCache());
		}
		if (metaData.getBackoff() != null) {
			putMetaData(azureMetaData, CacheHeaderUtility.REFRESH_BACKOFF_SHORT, metaData.getBackoff().toString());
		}
		if (metaData.getMinimumApiVersion() != null) {
			putMetaData(azureMetaData, CacheHeaderUtility.MINIMUM_API_VERSION_SHORT,
					metaData.getMinimumApiVersion().toString());
		} else {
			putMetaData(azureMetaData, CacheHeaderUtility.MINIMUM_API_VERSION_SHORT,
					CacheHeaderUtility.DEFAULT_API_VERSION);
		}
		return azureMetaData;
	}

	private void putMetaData(HashMap<String, String> metaData, String key, String value) {
		try {
			metaData.put(key, value);
		} catch (Exception e) {
			logger.warn("Could not add metadata: ", e);
		}
	}

	private synchronized String getHTTPDateString(Date date) {
		return httpDateFormat.format(date);
	}
}

package ch.ubique.starsdk.data.output;

import java.util.Date;

/**
 * Utility class to work with {@link OutputMetaData} objects.
 *
 * @author alig
 */
public class OutputMetaDataUtility {

	public static final String JSON_CONTENT = "application/json";
	public static final String JPEG_CONTENT = "animation/jpeg";
	public static final String PNG_CONTENT = "animation/png";
	public static final String ZIP_CONTENT = "application/octet-stream";
	public static final String SQLITE_CONTENT = "application/x-sqlite3";
	public static final String HTML_CONTENT = "text/html";
	public static final String SVG_CONTENT = "animation/svg+xml";
	public static final String TEXT_CONTENT = "text/plain";

	public static final String GZIP_ENCODING = "gzip";
	public static final String JSON_ENDING = ".json";

	/**
	 * Constructs a meta data object for the output handler.
	 *
	 * @param contentType
	 * @param importDate
	 * @param millisBestBefore millisecond to expiry date. -1 means not set
	 * @param millisRefresh    millisecond to expiry date. -1 means not set
	 * @return
	 */
	public static OutputMetaData constructMetaData(String contentType, Date importDate, long millisBestBefore,
												   long millisRefresh) {
		OutputMetaData metaData = new OutputMetaData();
		metaData.setContentType(contentType);
		if (millisBestBefore > -1) {
			metaData.setBestBefore(addMilis(importDate, millisBestBefore));
		}
		if (millisRefresh > -1) {
			metaData.setNextRefresh(addMilis(importDate, millisRefresh));
		}
		return metaData;
	}

	/**
	 * Constructs a meta data object for the output handler.
	 *
	 * @param contentType
	 * @param importDate
	 * @param millisBestBefore millisecond to expiry date. -1 means not set
	 * @param millisRefresh    millisecond to expiry date. -1 means not set
	 * @param backoff          in seconds.
	 * @return
	 */
	public static OutputMetaData constructMetaData(String contentType, Date importDate, long millisBestBefore,
												   long millisRefresh, long backoff) {
		OutputMetaData metaData = new OutputMetaData();
		metaData.setContentType(contentType);
		if (millisBestBefore > -1) {
			metaData.setBestBefore(addMilis(importDate, millisBestBefore));
		}
		if (millisRefresh > -1) {
			metaData.setNextRefresh(addMilis(importDate, millisRefresh));
		}
		metaData.setBackoff(backoff);
		return metaData;
	}

	/**
	 * Constructs a meta data object for the output handler.
	 *
	 * @param contentType
	 * @param importDate
	 * @param millisRefresh millisecond to expiry date. -1 means never
	 * @param cache         cache control string to set
	 * @return
	 */
	public static OutputMetaData constructMetaData(String contentType, Date importDate, long millisRefresh,
												   String cache,
												   long backoff) {
		OutputMetaData metaData = new OutputMetaData();
		metaData.setContentType(contentType);
		metaData.setNextRefresh(addMilis(importDate, millisRefresh));
		metaData.setCache(cache);
		metaData.setBackoff(backoff);
		return metaData;
	}

	/**
	 * Constructs a meta data object for the output handler.
	 *
	 * @param contentType
	 * @param importDate
	 * @param millisRefresh     millisecond to expiry date. -1 means never
	 * @param cache             cache control string to set
	 * @param minimumApiVersion minimum api version
	 * @return
	 */
	public static OutputMetaData constructMetaData(String contentType, Date importDate, long millisRefresh,
												   String cache,
												   long backoff, long minimumApiVersion) {
		OutputMetaData metaData = new OutputMetaData();
		metaData.setContentType(contentType);
		metaData.setNextRefresh(addMilis(importDate, millisRefresh));
		metaData.setCache(cache);
		metaData.setBackoff(backoff);
		metaData.setMinimumApiVersion(minimumApiVersion);
		return metaData;
	}

	private static Date addMilis(Date date, long millisToAdd) {
		return new Date(date.getTime() + millisToAdd);
	}

}

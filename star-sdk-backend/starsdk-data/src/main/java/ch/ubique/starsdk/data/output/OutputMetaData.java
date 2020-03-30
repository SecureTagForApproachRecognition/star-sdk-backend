package ch.ubique.starsdk.data.output;

import java.util.Date;

/**
 * Object used by the output handler holding meta informations about the object
 * to output. Not all output handler support metadata.
 *
 * @author alig
 */
public class OutputMetaData {

	private String contentType;
	private String contentEncoding;

	// private Date expiry;
	private Date bestBefore;
	private Date nextRefresh;
	private String cache;
	private Long backoff;
	private Long minimumApiVersion;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getBestBefore() {
		return bestBefore;
	}

	public void setBestBefore(Date bestBefore) {
		this.bestBefore = bestBefore;
	}

	public Date getNextRefresh() {
		return nextRefresh;
	}

	public void setNextRefresh(Date nextRefresh) {
		this.nextRefresh = nextRefresh;
	}

	public String getCache() {
		return cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	public Long getBackoff() {
		return backoff;
	}

	public void setBackoff(Long backoff) {
		this.backoff = backoff;
	}

	/**
	 * @return the minimumApiVersion
	 */
	public Long getMinimumApiVersion() {
		return minimumApiVersion;
	}

	/**
	 * @param minimumApiVersion the minimumApiVersion to set
	 */
	public void setMinimumApiVersion(Long minimumApiVersion) {
		this.minimumApiVersion = minimumApiVersion;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

}

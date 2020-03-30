package ch.ubique.starsdk.data.cache;

/**
 * Holds all static informations needed for the cache headers and offers some
 * helper methods.
 *
 * @author haller
 */
public class CacheHeaderUtility {

	public static final String CACHE = "x-ms-metacache";
	public static final String NEXT_REFRESH = "x-ms-meta-nextrefresh";
	public static final String BEST_BEFORE = "x-ms-meta-bestbefore";
	public static final String REFRESH_BACKOFF = "x-ms-meta-backoff";
	public static final String MINIMUM_API_VERSION = "x-ms-meta-minimumapiversion";

	public static final String CACHE_SHORT = "cache";
	public static final String NEXT_REFRESH_SHORT = "nextrefresh";
	public static final String BEST_BEFORE_SHORT = "bestbefore";
	public static final String REFRESH_BACKOFF_SHORT = "backoff";
	public static final String MINIMUM_API_VERSION_SHORT = "minimumapiversion";

	public static final String DEFAULT_API_VERSION = "1";

	/**
	 * Exposed json
	 */
	 public static final long EXPOSED_EXPIRES = 14 * 24 * 60 * 60 * 1000L;
	 public static final long EXPOSED_NEXT_REFRESH = 1 * 60 * 1000L;
}

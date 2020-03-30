package ch.ubique.starsdk.data.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class FileUtility {
	/**
	 * Compress a byte array using gzip.
	 *
	 * @param input
	 * @return the compressed data
	 * @throws IOException
	 */
	public static byte[] gZip(byte[] input) throws IOException {
		ByteArrayOutputStream compressedOut = new ByteArrayOutputStream();
		GZIPOutputStream gzipOut = new GZIPOutputStream(compressedOut);
		gzipOut.write(input, 0, input.length);
		gzipOut.finish();
		gzipOut.close();
		return compressedOut.toByteArray();
	}
}

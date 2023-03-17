/* 
 * Bitcoin cryptography library
 * Copyright (c) Project Nayuki
 * 
 * https://www.nayuki.io/page/bitcoin-cryptography-library
 * https://github.com/nayuki/Bitcoin-Cryptography-Library
 */

package world.ntdi.planium.manger.encoding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;


/**
 * Converts between an array of bytes and a Base58Check string. Not instantiable.
 * Modified by Ntdi
 */
public final class Base58Check {
	
	/*---- Static functions ----*/
	
	// Adds the checksum and converts to Base58Check. Note that the caller needs to prepend the version byte(s).
	public static String bytesToBase58(byte[] data) {
		return rawBytesToBase58(addCheckHash(data));
	}
	
	
	// Directly converts to Base58Check without adding a checksum.
	static String rawBytesToBase58(byte[] data) {
		// Convert to base-58 string
		StringBuilder sb = new StringBuilder();
		BigInteger num = new BigInteger(1, data);
		while (num.signum() != 0) {
			BigInteger[] quotrem = num.divideAndRemainder(ALPHABET_SIZE);
			sb.append(ALPHABET.charAt(quotrem[1].intValue()));
			num = quotrem[0];
		}
		
		// Add '1' characters for leading 0-value bytes
		for (int i = 0; i < data.length && data[i] == 0; i++)
			sb.append(ALPHABET.charAt(0));
		return sb.reverse().toString();
	}
	
	
	// Returns a new byte array by concatenating the given array with its checksum.
	static byte[] addCheckHash(byte[] data) {
		try {
			byte[] hash = Arrays.copyOf(Sha256.getDoubleHash(data).toBytes(), 4);
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			buf.write(data);
			buf.write(hash);
			return buf.toByteArray();
		} catch (IOException e) {
			throw new AssertionError(e);
		}
	}
	
	/*---- Class constants ----*/
	
	public static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";  // Everything except 0OIl
	private static final BigInteger ALPHABET_SIZE = BigInteger.valueOf(ALPHABET.length());
	
	
	
	/*---- Miscellaneous ----*/
	
	private Base58Check() {}  // Not instantiable
	
}
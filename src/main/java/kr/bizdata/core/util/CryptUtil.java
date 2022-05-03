package kr.bizdata.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class CryptUtil {
	
	/**
	 * SHA 암호화 (단방향)
	 * 
	 * @param str	문자열
	 * @return
	 */
	public static String encodeSHA(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(str.getBytes());
			
			byte[] encrypted = md.digest();
			
			return Hex.encodeHexString(encrypted); // encoded
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * SHA-256 암호화 (단방향)
	 * 
	 * @param str	문자열
	 * @return
	 */
	public static String encodeSHA256(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes());
			
			byte[] encrypted = md.digest();
			
			return Hex.encodeHexString(encrypted); // encoded
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * SHA-512 암호화 (단방향)
	 * 
	 * @param str	문자열
	 * @return
	 */
	public static String encodeSHA512(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(str.getBytes());
			
			byte[] encrypted = md.digest();
			
			return Hex.encodeHexString(encrypted); // encoded
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
}

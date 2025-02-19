package br.com.directpurchase.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class PasswordUtil {

	public static String encryptPassword(String contrasena) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		byte[] pass = md.digest(contrasena.getBytes());
		return Base64.encodeBase64String(pass);
	}

}

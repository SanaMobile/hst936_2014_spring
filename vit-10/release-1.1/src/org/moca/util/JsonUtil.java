package org.moca.util;


/**
 * Class create for decrypt/encrypt Json text
 * author vit team 10
 */
public class JsonUtil {
	
	/**
	 * Decrypt json text
	 */
	public static String DecryptJson(String text)
	{
		return CryptoUtil.CreateOne().decrypt(text);
	}
	
	/**
	 * Encrypt json text
	 */
	public static String EncryptJson(String text)
	{
		return CryptoUtil.CreateOne().encrypt(text);
	}
}

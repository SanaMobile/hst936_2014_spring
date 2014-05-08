package org.moca.util;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.moca.Constants;

import android.util.Log;

/**
 * Crypto class using DESede algorithms. 
 * author vit team 10
 */
public class CryptoUtil {	
	
		private static final String TAG = CryptoUtil.class.getSimpleName();
		
		private static final String UNICODE_FORMAT = "UTF8";
	    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	    private KeySpec myKeySpec;
	    private SecretKeyFactory mySecretKeyFactory;
	    private Cipher cipher;
	    byte[] keyAsBytes;
	    private String myEncryptionKey;
	    private String myEncryptionScheme;
	    private SecretKey key;
	    private static CryptoUtil _singleton;
	 
	    public CryptoUtil() throws Exception
	    {
	        myEncryptionKey = Constants.DEFAULT_SEEDS;
	        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
	        keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
	        myKeySpec = new DESedeKeySpec(keyAsBytes);
	        mySecretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
	        cipher = Cipher.getInstance(myEncryptionScheme);
	        key = mySecretKeyFactory.generateSecret(myKeySpec);
	    }
	    
	    /**
		 * Singelton pattern.
		 */
	    public static CryptoUtil CreateOne()
		  {
	    	if(_singleton == null)
	    	{
	    		try {
					_singleton = new CryptoUtil();
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    	
	    	return _singleton;
			  
		  }
	 
	    /**
	     * Method To Encrypt The String
	     */
	    public String encrypt(String clearText) {
	        String encryptedString = null;
	        try {
	            cipher.init(Cipher.ENCRYPT_MODE, key);
	            byte[] plainText = clearText.getBytes(UNICODE_FORMAT);
	            byte[] encryptedText = cipher.doFinal(plainText);
	            Base64 base64encoder = new Base64();
	            encryptedString = new String(base64encoder.encode(encryptedText), UNICODE_FORMAT);
	        } catch (Exception e) {
	        	Log.e(TAG, "Exception encrypt the string: "
						+ e.toString());				
				e.printStackTrace();
	        }
	        return encryptedString;
	    }
	    
	    /**
	     * Method To Decrypt An Ecrypted String
	     */
	    public String decrypt(String encryptedString) {
	        String decryptedText=Constants.EMPTY_STRING;
	        try {
	            cipher.init(Cipher.DECRYPT_MODE, key);
	            Base64 base64decoder = new Base64();
	            byte[] encryptedText = base64decoder.decode(encryptedString.getBytes(UNICODE_FORMAT));
	            byte[] plainText = cipher.doFinal(encryptedText);
	            decryptedText= bytes2String(plainText);
	        } catch (Exception e) {
	        	Log.e(TAG, "Exception decrypt the string: "
						+ e.toString());				
				e.printStackTrace();

	        }
	        return decryptedText;
	    }
	    
	    /**
	     * Returns String From An Array Of Bytes
	     */
	    private static String bytes2String(byte[] bytes) {
	        StringBuffer stringBuffer = new StringBuffer();
	        for (int i = 0; i < bytes.length; i++) {
	            stringBuffer.append((char) bytes[i]);
	        }
	        return stringBuffer.toString();
	    }	
}

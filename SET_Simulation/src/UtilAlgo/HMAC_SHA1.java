package UtilAlgo;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

public class HMAC_SHA1 {

	public static String HashData(String data)
	{
		return DigestUtils.sha1Hex(data);
	}
	
	public static String GenereCodeHMAC() throws NoSuchAlgorithmException, InvalidKeyException{
		KeyGenerator generateur = KeyGenerator.getInstance("HmacSHA1");
		SecretKey cle = generateur.generateKey();
	    
		SecretKeySpec keySpec = new SecretKeySpec(
				cle.getEncoded(), "HmacSHA1");

		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(keySpec);
		
		cle = generateur.generateKey();
		byte[] hmacCode = mac.doFinal(cle.getEncoded());

		return Base64.getEncoder().encodeToString(hmacCode);
	}
	
	public static boolean ValidationHMAC(String data,String codeHMAC)
	{
		
		if(DigestUtils.sha1Hex(data).equals(codeHMAC))
			return true;
		
		return false;
	}
}

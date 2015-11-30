package UtilAlgo;

import org.apache.commons.*;
import java.util.Base64;
//import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.concurrent.ExecutionException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class DES {
	
	static Cipher desCipher;
	
	public DES () throws NoSuchAlgorithmException, NoSuchPaddingException
	{
		desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	}
	
	public static SecretKey generateSecretKey()
	{
		KeyGenerator keyGen = null;
		try{
		keyGen = KeyGenerator.getInstance("DES");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return keyGen.generateKey();
	}
	
	public static byte[] EncryptData(byte[] data,SecretKey secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException
	{
		desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		
		desCipher.init(Cipher.ENCRYPT_MODE,secretKey,new IvParameterSpec(new byte[8]));

		return desCipher.doFinal(data); 
	}
		
	public static byte[] DecryptData(byte[] data, SecretKey _clePrivee) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException
		{
		desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		
		desCipher.init(Cipher.DECRYPT_MODE,_clePrivee,new IvParameterSpec(new byte[8]));
		
		return desCipher.doFinal(data);
		}
		
	
	

	
	
}

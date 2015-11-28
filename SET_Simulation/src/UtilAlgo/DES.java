package UtilAlgo;

import org.apache.commons.*;
import org.apache.commons.codec.binary.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class DES {


	Cipher desCipher;
	
	public SecretKey generateSecretKey() throws NoSuchAlgorithmException
	{
		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		return keyGen.generateKey();
	}
	
	public String encryptData(String data,SecretKey secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] byteCipherText;

		desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		desCipher.init(Cipher.ENCRYPT_MODE,secretKey);
		
		byte[] byteDataToEncrypt = data.getBytes();
		byteCipherText = desCipher.doFinal(byteDataToEncrypt); 

		return org.apache.commons.codec.binary.Base64.encodeBase64(byteCipherText).toString();
		}
		
		public String Decrypt(String data, SecretKey secretKey) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
		{
		desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			
		desCipher.init(Cipher.DECRYPT_MODE,secretKey);
		byte[] byteDecryptedText = desCipher.doFinal(data.getBytes());
		return new String(byteDecryptedText);
		
		}
		
	
	

	
	
}

package UtilAlgo;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class RSA {
	 
		public RSA(){}
	
	    public static KeyPair getKeyPair() {
	       KeyPair keypair = null;
	    	try {
	    		keypair =  KeyPairGenerator.getInstance("RSA").generateKeyPair();
	        } catch (NoSuchAlgorithmException e) {
	            System.err.println("Algorithm not supported! " + e.getMessage() + "!");
	        }
	    	return keypair;

	    }
	 
	    public static byte[] EncryptPublicKey(byte[] data,PublicKey clePublique) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
	        final Cipher cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE, clePublique);
	        return cipher.doFinal(data);
	    }
	    
	    public static byte[] DecryptPrivateKey(byte[] data,PrivateKey clePrive) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	    {
	    	 final Cipher cipher = Cipher.getInstance("RSA");
	         cipher.init(Cipher.DECRYPT_MODE, clePrive);
	         return cipher.doFinal(data);
	    }
	
}

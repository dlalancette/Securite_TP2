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
	 
	    public static String EncryptPublicKey(String data,PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
	        final Cipher cipher = Cipher.getInstance("RSA");
	        // ENCRYPT using the PUBLIC key
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	         byte[] encryptedBytes = cipher.doFinal(data.getBytes());
	         return new String(Base64.getEncoder().encode(encryptedBytes));
	    }
	    
	    public static String DecryptPrivateKey(String data,PrivateKey Privatekey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	    {
	    	 final Cipher cipher = Cipher.getInstance("RSA");
	         // DECRYPT using the PRIVATE key
	         cipher.init(Cipher.DECRYPT_MODE, Privatekey);
	         byte[] ciphertextBytes = Base64.getDecoder().decode(data.getBytes());
	         byte[] decryptedBytes = cipher.doFinal(ciphertextBytes);
	         return new String(decryptedBytes);
	            
	      
	    }
	
}

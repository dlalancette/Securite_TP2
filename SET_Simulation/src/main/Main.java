package main;

import java.io.Console;
import java.security.KeyPair;

import UtilAlgo.RSA;

public class Main {

	public static void main(String[] args) {
		
		String data = "données";	
		String donneeEncrypteClePublic;
		String donneeDesencrypteClePrive;
		
			
		KeyPair keypair = RSA.getKeyPair();
		try{
			donneeEncrypteClePublic = RSA.EncryptPublicKey(data, keypair.getPublic());
			donneeDesencrypteClePrive = RSA.DecryptPrivateKey(donneeEncrypteClePublic, keypair.getPrivate());
		
			
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

}

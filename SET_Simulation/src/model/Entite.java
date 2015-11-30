package model;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import UtilAlgo.DES;
import UtilAlgo.RSA;

public class Entite {

	protected UUID _id;
	protected PrivateKey _clePrivee;
	protected SecretKey _cleSecrete;
	protected PublicKey _clePublique;
	protected Certificat _certificat;
	
	public UUID getId(){
		return _id;
	}
	
	public PublicKey getClePublique(){
		return _clePublique;
	}
	
	public void setCertificat(Certificat certificat){
		_certificat = certificat;
	}
	
	public Certificat getCertificat(){
		return _certificat;
	}
	
	//Création d'un entité (Client, banque, marchand)
	protected Entite(){
		KeyPair keypair = RSA.getKeyPair();
		_id = java.util.UUID.randomUUID();
		_clePrivee = keypair.getPrivate();
		_clePublique = keypair.getPublic();
		_cleSecrete = DES.generateSecretKey();
	}
	
	//Encryption des données avec la clé publique de l'entite et l'algo RSA
	public byte[] EncrypterDonnees(byte[] donneesClaires) 
			throws InvalidKeyException, NoSuchAlgorithmException, 
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		return RSA.EncryptPublicKey(donneesClaires, _clePublique);
	}
	
	//Décryption des données avec la clé publique de l'entité et l'algo RSA
	public byte[] DecrypterDonnees(byte[] donneesCryptees) 
			throws InvalidKeyException, NoSuchAlgorithmException, 
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		return RSA.DecryptPrivateKey(donneesCryptees, _clePrivee);
	}
}

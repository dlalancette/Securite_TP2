package model;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import UtilAlgo.RSA;

public class Commande implements Serializable  {

	private byte[] _encryptelstArticle;	
	private byte[] _encryptePaiement;


	public Commande(byte[] encrypteLstArticle,byte[] encrypteCompte)
	{
		_encryptelstArticle = encrypteLstArticle;
		_encryptePaiement = encrypteCompte;
		
	}
	
	public byte[] getEncrypteLstArticle()
	{
		return _encryptelstArticle;
	}
	
	public byte[] getEncryptePaiement()
	{
		return _encryptePaiement;
	}	
}

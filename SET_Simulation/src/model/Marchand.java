package model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import UtilAlgo.DES;
import UtilAlgo.RSA;
import UtilAlgo.UtilSerialisation;

public class Marchand extends Entite {

	private Compte _compte; 
	private Map<String,PublicKey> _mapPublicKey;
	private Map<String,SecretKey> _mapSecretKey;
	
	public Marchand() throws NoSuchAlgorithmException{
		super();
		
		_compte = new Compte();
		_mapPublicKey = new HashMap<>();
		_mapSecretKey = new HashMap<>();
	}
	
	public byte[] getCommande(Commande commande) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, ClassNotFoundException, IOException
	{
		byte[] decrypteCommande = DES.DecryptData(commande.getEncrypteLstArticle(), super._cleSecrete);
		
		Articles lstArticle = (Articles) UtilSerialisation.deserialize(decrypteCommande);
		
		return commande.getEncryptePaiement();
	}
	
	public void ReceptionClePublic(String name,PublicKey publicKey)
	{
		_mapPublicKey.put(name, publicKey);
	}
	
	public void ReceptionCleSecreteBanque(String name,byte[] encrypteSecretKey) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] decryptekey = RSA.DecryptPrivateKey(encrypteSecretKey, _clePrivee);
		SecretKey sk = new SecretKeySpec(decryptekey,0,decryptekey.length,"DES");
		_mapSecretKey.put(name,sk );
	}
	
	public byte[] EnvoieCleSecreteMarchant() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		return RSA.EncryptPublicKey(_cleSecrete.getEncoded(),_mapPublicKey.get(Client.class.getSimpleName()));
	}
	
	public byte[] EnvoiCleSecreteBanqueDuMarchant() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		return RSA.EncryptPublicKey(_mapSecretKey.get(Banque.class.getSimpleName()).getEncoded(),_mapPublicKey.get(Client.class.getSimpleName()));
	}

}

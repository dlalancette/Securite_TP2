package model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Hashtable;
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

public class Client extends Entite {
	
	private Compte _compte; 
	private Map<String,SecretKey> _mapSecretKey;
	private Map<String,PublicKey> _mapPublicKey;
	
	public Client(){
		super();
		_mapSecretKey = new Hashtable();
		_compte = new Compte();
	}
	
	public Commande EnvoiCommande(Articles articles) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IOException
	{
		int montantAPayer = 0;
		
		for(Article article : articles.getList())
		{
			montantAPayer += article.getPrix();
		}
		
		Paiement paiement = new Paiement( montantAPayer, _compte);
		
		byte[] encrypteLstArticle = DES.EncryptData(UtilSerialisation.serialize(articles), _mapSecretKey.get(Marchand.class.getSimpleName()));
		byte[] encrypteCompte = DES.EncryptData(UtilSerialisation.serialize(paiement), _mapSecretKey.get(Banque.class.getSimpleName()));
				
		return new Commande(encrypteLstArticle,encrypteCompte)	;	
	}
	
	//Suposons que seulement la banque va utiliser le compte
	public Compte getCompte()
	{
		return _compte;
	}
	//Suposons que seulement la banque va utiliser le compte
	public void UpdateCompte(Compte compte)
	{
		_compte = compte;
	}
	
	public void ReceptionCleSecret(String name,byte[] encrypteSecretKey) throws InvalidKeyException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException
	{
		byte[] decryptekey = RSA.DecryptPrivateKey(encrypteSecretKey, _clePrivee);
		SecretKey sk = new SecretKeySpec(decryptekey,0,decryptekey.length,"DES");
		_mapSecretKey.put(name,sk );
	}
	
	public void ReceptionClePublic(String name,PublicKey publicKey)
	{
		_mapPublicKey.put(name, publicKey);
	}
	
}

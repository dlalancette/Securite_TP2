package model;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import UtilAlgo.DES;
import UtilAlgo.RSA;
import UtilAlgo.UtilSerialisation;

public class Banque extends Entite {
	
	Map<UUID,Compte> _comptesClient; 
	private Map<String,PublicKey> _mapPublicKey;
	
	public Banque(){	
		super();
		_comptesClient = new HashMap<>();
		_mapPublicKey = new HashMap<>();
	}
	
	public boolean getCommandeFromMarchand(byte[] encryptePaiement) throws ClassNotFoundException, IOException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
	{
		byte[] decryptePaiement = DES.DecryptData(encryptePaiement, super._cleSecrete);
		
		Paiement paiement = (Paiement) UtilSerialisation.deserialize(decryptePaiement);
		
		Compte compte = _comptesClient.get(paiement.getCompteClient().getID());
		//Debit du compte
		if(paiement.getCompteClient().getSolde() >= paiement.getMontantDebitClient())
		{
		compte = _comptesClient.get(paiement.getCompteClient().getID());
		compte.soustraireCompte(paiement.getMontantDebitClient());
		_comptesClient.replace(compte.getID(), compte);
		return true;
		}
		
		return false;
		
	}
	
	public void ReceptionClePublic(String name,PublicKey publicKey)
	{
		_mapPublicKey.put(name, publicKey);
	}
	
	public byte[] EnvoieCleSecreteMarchant() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		return RSA.EncryptPublicKey(_cleSecrete.getEncoded(),_mapPublicKey.get(Marchand.class.getSimpleName()));
	}
	
	public void AjoutCompteClient(Compte compte)
	{
		_comptesClient.put(compte.getID(), compte);
	}
	
	public Compte getUpdateCompte(UUID idCompte)
	{
		return _comptesClient.get(idCompte);
	}
}

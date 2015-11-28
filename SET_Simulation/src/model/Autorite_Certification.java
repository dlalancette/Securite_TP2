package model;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.UUID;

public class Autorite_Certification {
	
	String _nomAutorite;
	HashMap<UUID, Certificat> _mapCertificats;
	
	public Autorite_Certification(){
		_nomAutorite = "Autorite";
		_mapCertificats = new HashMap<UUID, Certificat>();
	}
	
	//Permet de d�livrer un cetificat pour une banque
	public Certificat delivrerCertificatBanque(UUID objetID, PublicKey clePublique)
			throws InvalidKeyException, NoSuchAlgorithmException{
		HashMap<String, PublicKey> mapCles = new HashMap<String, PublicKey>();
		mapCles.put("Banque", clePublique);
		return delivrerCertificat(objetID, mapCles);
	}
	
	
	//Permet de d�livrer un certificat pour un client
	public Certificat delivrerCertificatClient(UUID objetID, PublicKey clePublique)
			throws InvalidKeyException, NoSuchAlgorithmException{
		HashMap<String, PublicKey> mapCles = new HashMap<String, PublicKey>();
		mapCles.put("Client", clePublique);
		return delivrerCertificat(objetID, mapCles);
	}
	
	
	//Permet de d�livrer un certificat pour un marchand
	public Certificat delivrerCertificatMarchand(UUID objetID, PublicKey clePubliqueBanque, PublicKey clePubliqueMarchand)
			throws InvalidKeyException, NoSuchAlgorithmException{
		HashMap<String, PublicKey> mapCles = new HashMap<String, PublicKey>();
		mapCles.put("Banque", clePubliqueBanque);
		mapCles.put("Marchand", clePubliqueMarchand);
		return delivrerCertificat(objetID, mapCles);
	}
	
	public boolean estCertificatValide(UUID objetID, Certificat certificat){
		//On recherche le certificat contenu dans un dictionnaire de l'agence
		Certificat certificatOriginale = _mapCertificats.get(objetID);
		
		//Si les signatures num�riques du certificat re�u et de l'orignale sont identiques
		//Alors on retounre Vrai, sinon on retourne Faux
		return certificat.getSignatureNumerique() == 
			   certificatOriginale.getSignatureNumerique() ? true : false;
	}
	
	private Certificat delivrerCertificat(UUID objetID, HashMap<String, PublicKey> mapCle) 
			throws InvalidKeyException, NoSuchAlgorithmException{
		Certificat cert = new Certificat(_nomAutorite, mapCle); //On g�n�re le certificat � partir de la cl�
		
		//On conserve un record du certificat dans un dictionnaire
		//La cl� est un UUID afin d'identifier de mani�re unique l'entit�/objet � qui apartient le certificat 
		_mapCertificats.put(objetID, cert);
				
		return cert;
	}
}

package model;

import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import UtilAlgo.HMAC_SHA1;;

public class Certificat {

	private String _nomAgentCertification;
	private String _signatureNumerique;
	private Map<String, PublicKey> _mapClesPubliques;
	private String _dateExpiration;
	
	public String getAgentCertification(){
		return _nomAgentCertification;
	}
	
	public String getSignatureNumerique(){
		return _signatureNumerique;
	}
	
	public PublicKey getClePublique(String entiteCles){
		return _mapClesPubliques.get(entiteCles);
	}
	
	public String getDateExpiration(){
		return _dateExpiration;
	}
	
	//Génération d'un certificat lors de l'appel du constructeur
	public Certificat(String nomAgent, Map<String, PublicKey> mapClesPubliques) throws InvalidKeyException, NoSuchAlgorithmException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Date du jour.
		c.add(Calendar.DATE, 1826); // On ajout 5 ans pour simuler une date d'expiration
		
		_nomAgentCertification = nomAgent; //Nom de l'agence de certification
		_signatureNumerique = HMAC_SHA1.GenereCodeHMAC(); //Génération d'un code HMAC pour la signature numérique
		_mapClesPubliques = mapClesPubliques; //Contient toutes les clés publiques du certificat
		_dateExpiration =  sdf.format(c.getTime()); //On définit la date d'Expiration
	}
	
}

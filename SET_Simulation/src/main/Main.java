package main;

import java.io.Console;
import java.security.KeyPair;

import UtilAlgo.RSA;
import model.*;

public class Main {

	public static void main(String[] args) {
		Banque banque;
		Client client;
		Marchand marchand;
		Autorite_Certification autorite;
		
		try{
			banque = new Banque();
			client = new Client();
			marchand = new Marchand();
			autorite = new Autorite_Certification();
			
			banque.setCertificat(autorite.delivrerCertificatBanque(
					banque.getId(), banque.getClePublique()));
			
			marchand.setCertificat(autorite.delivrerCertificatMarchand(
					marchand.getId(), marchand.getClePublique(), banque.getClePublique()));
			
			client.setCertificat(autorite.delivrerCertificatBanque(
					client.getId(), client.getClePublique()));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		System.exit(0);
	}

}

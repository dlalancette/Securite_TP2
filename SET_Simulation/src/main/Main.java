package main;

import java.io.Console;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import UtilAlgo.DES;
import UtilAlgo.HMAC_SHA1;
import UtilAlgo.RSA;
import model.*;

public class Main {

	public static void main(String[] args) {
		Banque banque;
		Client client;
		Marchand marchand;
		Autorite_Certification autorite;

		try {
			// Initialisation des tiers
			banque = new Banque();
			client = new Client();
			marchand = new Marchand();
			autorite = new Autorite_Certification();

			/*
			 * Pour le bien de la simulation on supose que le client fait une
			 * mise a jour a la fin de la journée pour avoir le solde de son
			 * compte
			 */
			banque.AjoutCompteClient(client.getCompte());

			Article art1 = new Article("Lampe", 1);
			Article art2 = new Article("Telephone", 50);
			// Initialisation du client
			Compte compte = client.getCompte();

			compte.depotCompte(150);

			client.UpdateCompte(compte);
			// Création d'articles
			Articles articles = new Articles();

			articles.AjoutArticles(art1);
			articles.AjoutArticles(art2);

			// Délivrance des certificat par l'autorité
			banque.setCertificat(autorite.delivrerCertificatBanque(banque.getId(), banque.getClePublique()));

			marchand.setCertificat(autorite.delivrerCertificatMarchand(marchand.getId(), marchand.getClePublique(),
					banque.getClePublique()));

			client.setCertificat(autorite.delivrerCertificatClient(client.getId(), client.getClePublique()));

			// Échange de clé et validation :
			if (autorite.estCertificatValide(marchand.getId(), marchand.getCertificat()) && autorite.estCertificatValide(client.getId(), client.getCertificat())) {
				//Échange de clés
				banque.ReceptionClePublic(Marchand.class.getSimpleName(), marchand.getClePublique());
				//Envoi de la clé secrete au marchant préalablement encrypté avec la clé public du marchant (RSA)
				byte[] encrypteCleSecreteBanque =  banque.EnvoieCleSecreteMarchant();
				
				marchand.ReceptionCleSecreteBanque(Banque.class.getSimpleName(), encrypteCleSecreteBanque);
				marchand.ReceptionClePublic(Client.class.getSimpleName(), client.getClePublique());
				
				//Envoie des clés secret de la banque et du marchant encrypté avant la clé public du client(RSA)
				byte[] encrypteCleSecreteBanqueDuMarchant = marchand.EnvoiCleSecreteBanqueDuMarchant();
				byte[] encrypteCleSecreteMarchant = marchand.EnvoieCleSecreteMarchant();
				//Reception des clé secrete par le marchant encrpyté avec la clé privé du client(RSA);
				client.ReceptionCleSecret(Marchand.class.getSimpleName(), encrypteCleSecreteMarchant);
				client.ReceptionCleSecret(Banque.class.getSimpleName(), encrypteCleSecreteBanqueDuMarchant);
				
				// Reception d'une commande encrypté(DES)
				Commande commande = client.EnvoiCommande(articles);
				//Le marchant prend seulement sa liste d'article et remet le reste a la banque qui sont crypté avec sa clé secrete.
				byte[] encryptePaiement = marchand.getCommande(commande);

				boolean encrypteconfirmationMarchant = banque.getCommandeFromMarchand(encryptePaiement);

				if (encrypteconfirmationMarchant)
					System.out.println("Transaction réussie");
				else
					System.out.println("Transaction échoué");
			}

			

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Transaction impossible");
		}

		System.exit(0);
	}
}

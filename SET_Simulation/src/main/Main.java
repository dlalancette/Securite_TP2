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
			 * mise a jour a la fin de la journ�e pour avoir le solde de son
			 * compte
			 */
			banque.AjoutCompteClient(client.getCompte());

			Article art1 = new Article("Lampe", 1);
			Article art2 = new Article("Telephone", 50);
			// Initialisation du client
			Compte compte = client.getCompte();

			compte.depotCompte(150);

			client.UpdateCompte(compte);
			// Cr�ation d'articles
			Articles articles = new Articles();

			articles.AjoutArticles(art1);
			articles.AjoutArticles(art2);

			// D�livrance des certificat par l'autorit�
			banque.setCertificat(autorite.delivrerCertificatBanque(banque.getId(), banque.getClePublique()));

			marchand.setCertificat(autorite.delivrerCertificatMarchand(marchand.getId(), marchand.getClePublique(),
					banque.getClePublique()));

			client.setCertificat(autorite.delivrerCertificatClient(client.getId(), client.getClePublique()));

			// �change de cl� et validation :
			if (autorite.estCertificatValide(marchand.getId(), marchand.getCertificat()) && autorite.estCertificatValide(client.getId(), client.getCertificat())) {
				//�change de cl�s
				banque.ReceptionClePublic(Marchand.class.getSimpleName(), marchand.getClePublique());
				//Envoi de la cl� secrete au marchant pr�alablement encrypt� avec la cl� public du marchant (RSA)
				byte[] encrypteCleSecreteBanque =  banque.EnvoieCleSecreteMarchant();
				
				marchand.ReceptionCleSecreteBanque(Banque.class.getSimpleName(), encrypteCleSecreteBanque);
				marchand.ReceptionClePublic(Client.class.getSimpleName(), client.getClePublique());
				
				//Envoie des cl�s secret de la banque et du marchant encrypt� avant la cl� public du client(RSA)
				byte[] encrypteCleSecreteBanqueDuMarchant = marchand.EnvoiCleSecreteBanqueDuMarchant();
				byte[] encrypteCleSecreteMarchant = marchand.EnvoieCleSecreteMarchant();
				//Reception des cl� secrete par le marchant encrpyt� avec la cl� priv� du client(RSA);
				client.ReceptionCleSecret(Marchand.class.getSimpleName(), encrypteCleSecreteMarchant);
				client.ReceptionCleSecret(Banque.class.getSimpleName(), encrypteCleSecreteBanqueDuMarchant);
				
				// Reception d'une commande encrypt�(DES)
				Commande commande = client.EnvoiCommande(articles);
				//Le marchant prend seulement sa liste d'article et remet le reste a la banque qui sont crypt� avec sa cl� secrete.
				byte[] encryptePaiement = marchand.getCommande(commande);

				boolean encrypteconfirmationMarchant = banque.getCommandeFromMarchand(encryptePaiement);

				if (encrypteconfirmationMarchant)
					System.out.println("Transaction r�ussie");
				else
					System.out.println("Transaction �chou�");
			}

			

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Transaction impossible");
		}

		System.exit(0);
	}
}

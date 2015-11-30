package model;

import java.io.Serializable;

public class Paiement implements Serializable {
	
	private int _montantdebitclient;
	
	private Compte _compteclient;
	
	public Paiement(int montantdebitclient,Compte compteclient)
	{
		_montantdebitclient = montantdebitclient;
		
		_compteclient = compteclient;
	}
	
	public int getMontantDebitClient()
	{
		return _montantdebitclient;
	}
	
	public Compte getCompteClient()
	{
		return _compteclient;
	}
}

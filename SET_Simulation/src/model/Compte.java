package model;

import java.io.Serializable;
import java.util.UUID;

public class Compte implements Serializable {
	
	private UUID _idCompte;
	private int _solde;
	
	public Compte ()
	{
		_idCompte = UUID.randomUUID();
	}
	
	public UUID getID()
	{
		return _idCompte;
	}
	
	public int getSolde()
	{
		return _solde;
	}
	
	public boolean soustraireCompte(int montant)
	{
		if((_solde - montant) >0)
			{
			_solde = _solde - montant;
			return true;
			}
		return false;
	}
	
	public boolean depotCompte(int montant)
	{
		if(montant > 0)
			{
			_solde = _solde + montant;
			return true;
			}
		return false;
	}
	
	
}

package model;

import java.io.Serializable;

public class Article implements Serializable {

	private String _nom; 
	private int    _prix;
	private String _description;
	
	public Article (String nom,int prix)
	{
		_nom = nom;
		_prix = prix;
	}
	
	public void AjoutDescription(String description)
	{
		_description = description;
	}
	
	public int getPrix()
	{
		return _prix;
	}
	
}

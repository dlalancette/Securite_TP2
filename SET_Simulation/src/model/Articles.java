package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Articles implements Serializable {
	
	private ArrayList<Article> lstArticle;
	
	public Articles()
	{
		lstArticle = new ArrayList<Article>();
	}
	
	public void AjoutArticles(Article art)
	{
		lstArticle.add(art);
	}
	
	public ArrayList<Article>  getList()
	{
		return lstArticle;
	}
}

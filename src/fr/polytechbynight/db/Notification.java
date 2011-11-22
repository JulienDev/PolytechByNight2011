package fr.polytechbynight.db;

import java.util.Date;

public class Notification {

	public int id;
	public String titre;
	public String contenu;
	public Date date;
	
	public Notification(int id, String titre, String contenu, Date date) {
		super();
		this.id = id;
		this.titre = titre;
		this.contenu = contenu;
		this.date = date;
	}

	public Notification(String titre, String contenu, Date date) {
		super();
		this.titre = titre;
		this.contenu = contenu;
		this.date = date;
	}
}
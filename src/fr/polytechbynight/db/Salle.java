package fr.polytechbynight.db;

public class Salle {

	public int id;
	public String nom;
	public String description;
	public String image;
	public int type;
	public String etage;

	public Salle(int id, String nom, String description, String image,
			int type, String etage) {
		super();
		this.id = id;
		this.nom = nom;
		this.description = description;
		this.image = image;
		this.type = type;
		this.etage = etage;
	}

	public Salle() {
		super();
	}	
}
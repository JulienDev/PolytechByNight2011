/*
 * This file is part of Vieilles Charrues 2010.
 *
 * Vieilles Charrues 2010 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Vieilles Charrues 2010 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Vieilles Charrues 2010.  If not, see <http://www.gnu.org/licenses/>.
 */

package fr.polytechbynight.db;

import java.io.Serializable;
import java.util.Date;


/**
 * Classe représentant un concert
 * 
 * @author Julien VERMET / Arzhel YOUNSI
 * @version 1.0
 */
public class Animation implements Serializable{
	public int id = 0;
	public Date heureDebut = new Date(0);
	public Date heureFin = new Date(0);
	public int idSalle = 0;
	public int idTypeAnimation = 0;
	public String nom = "";
	public String description = "";
	public String genre = "";
	public String image = "";
	public String thumbnail = "";
	public String site = "";
	public String facebook = "";
	public String myspace = "";
	public String youtube = "";
	public String twitter = "";
	public String soundcloud = "";
	public String googleplus = "";
	public String deezer = "";	
	
	public Animation() {
		super();
	}

	public Animation(int id, Date heureDebut, Date heureFin, int idSalle,
			int idTypeAnimation, String nom, String description, String genre,
			String image, String thumbnail, String site, String facebook,
			String myspace, String youtube, String twitter, String soundcloud,
			String googleplus, String deezer) {
		super();
		this.id = id;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.idSalle = idSalle;
		this.idTypeAnimation = idTypeAnimation;
		this.nom = nom;
		this.description = description;
		this.genre = genre;
		this.image = image;
		this.thumbnail = thumbnail;
		this.site = site;
		this.facebook = facebook;
		this.myspace = myspace;
		this.youtube = youtube;
		this.twitter = twitter;
		this.soundcloud = soundcloud;
		this.googleplus = googleplus;
		this.deezer = deezer;
	}

	public Animation(int id, Date heureDebut, Date heureFin, int idSalle,
			int idTypeAnimation, String nom, String description) {
		super();
		this.id = id;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.idSalle = idSalle;
		this.idTypeAnimation = idTypeAnimation;
		this.nom = nom;
		this.description = description;
	}
}
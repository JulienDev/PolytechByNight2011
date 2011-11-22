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

package fr.polytechbynight.programme;

/**
 * Classe représentant une case de la liste
 * 
 * @author Julien VERMET / Arzhel YOUNSI
 * @version 1.0
 */
public class CaseListe{
	public int concertId;
	public String titre;
	public String details;
	public int type = 0; // 0 concert, 1 separateur, 3 vide
	public boolean afficherEtoile = true;
	public int idArtiste;

	/**
	 * Constructeur
	 * @param titre Titre de la case
	 */
	public CaseListe(String titre) {
		this.titre = titre;
		this.type = 1;
	}
	
	   public CaseListe() {
	        this.type = 3;
	        this.titre = "";

	    }

	/**
	 * Constructeur
	 * @param idArtiste ID de l'artiste
	 * @param concertId ID du concert
	 * @param nomArtiste Nom de l'artiste
	 * @param details Détails/description
	 * @param afficherEtoile Afficher ou non l'étoile des favoris 
	 */
	public CaseListe(int idArtiste,int concertId, String nomArtiste, String details, boolean afficherEtoile) {
		this.concertId = concertId;
		this.titre = nomArtiste;
		this.details = details;   
		this.idArtiste = idArtiste;
		this.afficherEtoile = afficherEtoile;
	}

	/**
	 * Test si la case est un séparateur
	 * @return boolean Renvoi 1 si la case est un séparateur
	 */
	public int type() {
		return type;
	}


}

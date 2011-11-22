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

package fr.polytechbynight.parseurs;

import java.io.File;
import java.util.Date;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.util.Log;
import fr.polytechbynight.db.Animation;
import fr.polytechbynight.db.DB;
import fr.polytechbynight.db.Salle;
import fr.polytechbynight.outils.Outils;

/**
 * Classe gérant le parseur de programmation/artistes
 * 
 * @author Julien VERMET / Arzhel YOUNSI
 * @version 1.0
 */
public class RSSProgHandler extends DefaultHandler {

	//Liste et initialisation de toutes les balises XML présentes dans les flux.
	private boolean inSalle = false;
	private boolean inAnimation = false;

	private boolean inSalleDescription = false;
	private boolean inAnimationId = false; 
	private boolean inAnimationHeureDebut = false;
	private boolean inAnimationHeureFin = false;
	private boolean inAnimationTypeId = false;
	private boolean inAnimationNom = false;
	private boolean inAnimationDescription = false;
	private boolean inAnimationGenre = false;
	private boolean inAnimationImage = false;
	private boolean inAnimationThumbnail = false;
	private boolean inAnimationSite = false;
	private boolean inAnimationFacebook = false;
	private boolean inAnimationMyspace = false;
	private boolean inAnimationYoutube = false;
	private boolean inAnimationTwitter = false;
	private boolean inAnimationSoundcloud = false;
	private boolean inAnimationGoogleplus = false;
	private boolean inAnimationDeezer = false;

	private int salleId;

	private Animation animation;
	private Salle salle;

	private static final String LOGTAG = "SAX-Prog";

	private DB db;

	/**
	 * Méthode appelée à l'ouverture du flux XML
	 */
	@Override
	public void startDocument() throws SAXException {
		animation = new Animation();
		salle = new Salle();
		Log.d("Debut", "Parsing");
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();

		Log.d("Fin", "Parsing");
	}

	/**
	 * Méthode appelée lorsque le parseur rencontre une balise ouvrante
	 */
	@Override
	public void startElement(String uri, String name, String qName, Attributes atts) {
		if (name.trim().equals("salle")) { 
			inSalle = true;

			salleId = Integer.parseInt(atts.getValue("id"));

			salle.id = salleId;
			salle.nom = atts.getValue("nom");
			//String description = atts.getValue("description");
			salle.image = atts.getValue("image");
			salle.type = Integer.parseInt(atts.getValue("type"));
			salle.etage = atts.getValue("etage");
		}
		else if (name.trim().equals("animation")) inAnimation = true;
		else if (name.trim().equals("descriptionSalle")) inSalleDescription = true;
		else if (name.trim().equals("id")) inAnimationId = true;
		else if (name.trim().equals("heureDebut")) inAnimationHeureDebut = true;
		else if (name.trim().equals("heureFin")) inAnimationHeureFin = true;
		else if (name.trim().equals("idTypeAnimation")) inAnimationTypeId = true;
		else if (name.trim().equals("nom")) inAnimationNom = true;
		else if (name.trim().equals("description")) inAnimationDescription = true;
		else if (name.trim().equals("genre")) inAnimationGenre = true;
		else if (name.trim().equals("image")) inAnimationImage = true;
		else if (name.trim().equals("thumbnail")) inAnimationThumbnail = true;
		else if (name.trim().equals("site")) inAnimationSite = true;
		else if (name.trim().equals("facebook")) inAnimationFacebook = true;
		else if (name.trim().equals("myspace")) inAnimationMyspace = true;
		else if (name.trim().equals("youtube")) inAnimationYoutube = true;
		else if (name.trim().equals("twitter")) inAnimationTwitter = true;
		else if (name.trim().equals("soundcloud")) inAnimationSoundcloud = true;
		else if (name.trim().equals("googleplus")) inAnimationGoogleplus = true;
	}


	/**
	 * Méthode appelée lorsque le parseur rencontre une balise fermante
	 */
	@Override
	public void endElement(String uri, String name, String qName) throws SAXException {

		if (name.trim().equals("salle")) 
		{
			inSalle = false;

			db.insertSalle(salle);

			salle.id = 0;
			salle.etage = "";
			salle.description="";
			salle.image="";
			salle.nom="";
			salle.type=0;
		}
		else if (name.trim().equals("animation")) 
		{
			inAnimation = false;

			Log.d("artiste", "a:" + animation.nom);

			animation.idSalle = salleId;
			db.insertAnimation(animation);

			animation.id = 0;
			animation.heureDebut = new Date(0);  
			animation.heureFin = new Date(0);
			animation.idTypeAnimation = 0;
			animation.nom = "";
			animation.description = "";
			animation.genre = "";
			animation.image = "";
			animation.thumbnail = "";
			animation.site = "";
			animation.facebook = "";
			animation.myspace = "";
			animation.youtube = "";
			animation.twitter = "";
			animation.soundcloud = "";
			animation.googleplus = "";
		}
		else if (name.trim().equals("animation")) inAnimation = false;

		else if (name.trim().equals("descriptionSalle")) inSalleDescription = false;

		else if (name.trim().equals("id")) inAnimationId = false;
		else if (name.trim().equals("heureDebut")) inAnimationHeureDebut = false;
		else if (name.trim().equals("heureFin")) inAnimationHeureFin = false;
		else if (name.trim().equals("idTypeAnimation")) inAnimationTypeId = false;
		else if (name.trim().equals("nom")) inAnimationNom = false;
		else if (name.trim().equals("description")) inAnimationDescription = false;
		else if (name.trim().equals("genre")) inAnimationGenre = false;
		else if (name.trim().equals("image")) inAnimationImage = false;
		else if (name.trim().equals("thumbnail")) inAnimationThumbnail = false;
		else if (name.trim().equals("site")) inAnimationSite = false;
		else if (name.trim().equals("facebook")) inAnimationFacebook = false;
		else if (name.trim().equals("myspace")) inAnimationMyspace = false;
		else if (name.trim().equals("youtube")) inAnimationYoutube = false;
		else if (name.trim().equals("twitter")) inAnimationTwitter = false;
		else if (name.trim().equals("soundcloud")) inAnimationSoundcloud = false;
		else if (name.trim().equals("googleplus")) inAnimationGoogleplus = false;
	}

	/**
	 * Méthode appelée lorsque le parseur rencontre du contenu entre balises
	 */
	@Override
	public void characters(char ch[], int start, int length) {

		String chars = new String(ch,start,length);
		if (inSalle) {

			if (inSalleDescription) salle.description += chars;

			if (inAnimation) {
				if (inAnimationId) animation.id = Integer.parseInt(chars);
				else if	 (inAnimationHeureDebut) animation.heureDebut.setTime(Outils.stringToLong(chars));
				else if (inAnimationHeureFin) animation.heureFin.setTime(Outils.stringToLong(chars));
				else if (inAnimationTypeId) animation.idTypeAnimation = Integer.parseInt(chars);
				else if (inAnimationNom) animation.nom += chars;
				else if (inAnimationDescription) animation.description += chars;
				else if (inAnimationGenre) animation.genre += chars;
				else if (inAnimationImage) animation.image += chars;
				else if (inAnimationThumbnail) animation.thumbnail += chars;
				else if (inAnimationSite) animation.site += chars;
				else if (inAnimationMyspace) animation.myspace += chars;
				else if (inAnimationYoutube) animation.youtube += chars;
				else if (inAnimationTwitter) animation.twitter += chars;
				else if (inAnimationSoundcloud) animation.soundcloud += chars;
				else if (inAnimationGoogleplus) animation.googleplus += chars;
			}
		}
	}

	/**
	 * Méthode appelée pour démarrer le parseur
	 * @param ctx Contexte dans lequel est lancé le parseur
	 * @param fichier fichier à parser
	 */
	public void updateArticles(Context ctx, File fichier) {
		try {
			db = new DB(ctx);

			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(this);
			xr.parse(new InputSource(fichier.toURL().openStream()));
		} catch (Exception e) {
			Log.e(LOGTAG, e.toString());
		}
	}
}
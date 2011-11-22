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

package fr.polytechbynight.outils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import android.util.Log;

/**
 * Classe contenant uniquement des methodes statiques, pricipallement pour convertion
 * 
 * @author Julien VERMET / Arzhel YOUNSI
 * @version 1.0
 */
public class Outils {

	public static final String PATH = "/data/data/fr.asso.pdn/";
	private static final String LOGTAG = "Outils";

	public static final int FORMAT_JOURH = 1;
	public static final int FORMAT_HSEULE = 0;
	public static final int FORMAT_JOURDH = 2;
	public static final int FORMAT_JOURD = 3;

	/**
	 * Change le format des horaires
	 * @param debut     Heure de debut au format date
	 * @param fin       Heure du fin au format date
	 * @param format    Format de sorti souhaité
	 * @return String   La date au format
	 */
	public static String horairesDebutFin(Date debut, Date fin, int format)
	{
		if(debut.equals(fin))
			return "";

		DateFormat df1 = null;
		DateFormat df2 = null;
		DateFormat df3 = null;
		if (format == FORMAT_HSEULE) {       // Format 12:30 - 15:45
			df1 = new SimpleDateFormat("HH:mm");
			return df1.format(debut) + " - " + df1.format(fin);
		}
		else if (format == FORMAT_JOURH) {   // Format Vendredi 12:30 - 15:45
			df1 = new SimpleDateFormat("HH:mm");
			df2 = new SimpleDateFormat("EEEE HH:mm");
			return df2.format(debut) + "-" + df1.format(fin);
		}   

		else if (format == FORMAT_JOURDH) { // Format Vendredi 23 Mai, 12:30 -- pour les news
			df3 = new SimpleDateFormat("EEEE d MMMMM, HH:mm");
			return df3.format(debut) + "";      
		}
		else if (format == FORMAT_JOURD) { // Format Vendredi 23 Mai, 12:30 -- pour les news
			df3 = new SimpleDateFormat("EEEE d");
			return df3.format(debut) + "";      
		}
		return "";
	}
	
	/**
	 * Change le format des horaires
	 * @param debut     Heure de debut au format date
	 * @param fin       Heure du fin au format date
	 * @param format    Format de sorti souhaité
	 * @return String   La date au format
	 */
	public static String timestampToHour(String timestamp)
	{
		Date dd = new Date(Long.parseLong(timestamp));
		
		DateFormat df3 = new SimpleDateFormat("HH:mm");
		return df3.format(dd) + "";
	}

	/**
	 * Cr�e un timestamp en fonction de l'heure et du jour du concert.
	 * @param Heure    Heure en string
	 * @return long     date/heure en long
	 */
	public static long stringToLong(String heure){

		Log.d("heure", "h:" + heure);

		SimpleDateFormat sdfDateConcert = new SimpleDateFormat("yyyy-MM-dd hh':'mm" );       

		String date = "2011-11-15 ";
		heure = heure.substring(0, heure.length()-3);
				
		StringTokenizer st = new StringTokenizer(heure, ":");
		int h = Integer.parseInt( st.nextToken() );
		if ( h >=0 && h <= 6)
		{
			date = "2011-11-16 ";
		}

		date += heure;
		try {
			return sdfDateConcert.parse(date).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * Boucle servant a télécharger toutes les images des artistes pour une utilisation hors ligne.
	 * A utiliser uniquement dans la version de devellopement, surtout pas dans les releases. 
	 * @param ctx     Contexte d'execution
	 */
	/*public static void TelechargeImagesArtistes(Context ctx){
		DB programmeDB = new DB(ctx);

		List<Artiste> artistes = programmeDB.getArtiste();
		for (Artiste artiste : artistes) {
			if (artiste.image != null)
			{
				if(!(new File(PATH+"images/imageArtiste" + artiste.idArtiste + ".jpg").exists())){
					try {
						TelechargeFichier.DownloadFromUrl(new URL(artiste.image), "imageArtiste" + artiste.idArtiste + ".jpg");
					}catch (IOException e) {
						Log.e(LOGTAG,"Impossible de télecharger l'image");
					}
				}
			}
		}
	}*/
}

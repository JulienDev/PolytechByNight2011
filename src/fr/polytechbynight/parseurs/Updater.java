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
import java.io.IOException;
import java.net.URL;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import fr.polytechbynight.db.DB;

/**
 * Classe gérant le Thread utilisé pour mettre à jour les flux
 * 
 * @author Julien VERMET / Arzhel YOUNSI
 * @version 1.0
 */
public class Updater implements Runnable{

	// Liste des URL utilisées par l'application
	private static final String DONNEES_URL = "http://www.polytechbynight.fr/site/android/prog.xml";
	private static final String PATH = "/data/data/fr.polytechbynight/";

	private DB dbSQLite;
	private final static Object LOCK = new Object();

	private final Handler handler;
	private final Context context;
	private final int typeMaj;

	/**
	 * Constructeur
	 * @param handler   Handler qui vas traiter les message envoyé par la classe
	 * @param context   Context qui execute le thread
	 * @param typeMaj   Type de la MAJ demandée (1 : News, 2 : programmation) 
	 */
	public Updater(Handler handler, Context context, int typeMaj) {

		this.handler = handler;
		this.context = context;
		this.typeMaj = typeMaj;
	}

	/**
	 * Méthode utilisée pour envoyer un message au handler
	 * @param type Type de message à envoyer (1 : Début, 2 : Terminé, 3 : Erreur)
	 */
	private void envoiMessage(int type) {
		final Message message = Message.obtain();
		message.what = type;
		handler.sendMessage(message);
	}

	@Override
	public void run() {
		dbSQLite = new DB(this.context);
		synchronized (LOCK) { //Empèche l'éxécution d'une 2nd instance du thread.
			try {
					envoiMessage(1);
					TelechargeFichier.DownloadFromUrl(new URL(DONNEES_URL), "prog.xml");
					dbSQLite.deleteTableSalles();
					dbSQLite.deleteTableAnimations();
					RSSProgHandler rh = new RSSProgHandler();
					rh.updateArticles(this.context, new File(PATH,"prog.xml"));
					envoiMessage(2);
			}catch (IOException e) { // Si impossible de télécharger un des flux (problème réseau)
				envoiMessage(3);
			}
		}
	}
}

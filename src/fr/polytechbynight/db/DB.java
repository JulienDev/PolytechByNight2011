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

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Classe gérant la base de donnée SQLite
 * 
 * @author Julien VERMET / Arzhel YOUNSI
 * @version 1.0
 */
public class DB {

	private static final String TABLE_NOTIFICATIONS = "notifications";
	private static final String TABLE_ANIMATIONS = "animations";
	private static final String TABLE_SALLES = "salles";

	private static final String CREER_TABLE_NOTIFICATIONS = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NOTIFICATIONS
			+ "(id INTEGER primary key autoincrement, titre VARCHAR, contenu VARCHAR, date INTEGER);";

	private static final String CREER_TABLE_ANIMATIONS = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_ANIMATIONS
			+ "(id INTEGER primary key autoincrement, heure_debut INTEGER, heure_fin INTEGER, idSalle INTEGER, idTypeAnimation INTEGER,"
			+ " nom VARCHAR, description TEXT, genre VARCHAR, image VARCHAR, thumbnail VARCHAR, site VARCHAR, facebook VARCHAR, myspace VARCHAR, youtube VARCHAR, twitter VARCHAR, soundcloud VARCHAR, googleplus VARCHAR, deezer VARCHAR, favoris BOOL);";

	private static final String CREER_TABLE_SALLES = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_SALLES
			+ "(id INTEGER primary key autoincrement, image VARCHAR, importance INTEGER, type INTEGER, description VARCHAR, nom VARCHAR, etage VARCHAR);";

	public static final String DATABASE_NAME = "database.db";
	private static final String LOGTAG = "db";
	private SQLiteDatabase db;

	private Context ctx;

	/**
	 * Constructeur de la classe DB
	 * 
	 * @param ctx
	 *            Context de l'activité qui a créé l'objet DB
	 */
	public DB(Context ctx) {
		this.ctx = ctx;

		try {
			open();
			db.execSQL(CREER_TABLE_NOTIFICATIONS);
			db.execSQL(CREER_TABLE_ANIMATIONS);
			db.execSQL(CREER_TABLE_SALLES);
			close();
		} catch (SQLException e) {
			Log.e(LOGTAG, e.toString());
		}

	}

	/**
	 * Ouvre la base SQLite ou la crée si elle n'existait pas
	 */
	public void open() {
		db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);
	}

	/**
	 * Ferme la base SQLite
	 */
	public void close() {
		db.close();
	}

	/**
	 * Insère une salle dans la base SQLite
	 */
	public boolean insertSalle(Salle salle) {
		ContentValues values = new ContentValues();
		values.put("id", salle.id);
		values.put("nom", salle.nom);
		values.put("description", salle.description);
		values.put("image", salle.image);
		values.put("type", salle.type);
		values.put("etage", salle.etage);

		open();
		boolean result = db.insert(TABLE_SALLES, null, values) > 0;
		close();
		return result;
	}

	/**
	 * Insère une notification dans la base SQLite
	 */
	public boolean insertNotification(Notification notification) {
		ContentValues values = new ContentValues();
		// values.put("id", notification.id);
		values.put("titre", notification.titre);
		values.put("contenu", notification.contenu);
		values.put("date", notification.date.getTime());

		open();
		boolean result = db.insert(TABLE_NOTIFICATIONS, null, values) > 0;
		close();
		return result;
	}

	/**
	 * Insère une animation dans la base SQLite
	 */
	public boolean insertAnimation(Animation animation) {
		ContentValues values = new ContentValues();
		values.put("id", animation.id);
		values.put("heure_debut", animation.heureDebut.getTime());
		values.put("heure_fin", animation.heureFin.getTime());
		values.put("idSalle", animation.idSalle);
		values.put("idTypeAnimation", animation.idTypeAnimation);
		values.put("nom", animation.nom);
		values.put("description", animation.description);
		values.put("genre", animation.genre);
		values.put("image", animation.image);
		values.put("thumbnail", animation.thumbnail);
		values.put("site", animation.site);
		values.put("facebook", animation.facebook);
		values.put("myspace", animation.myspace);
		values.put("youtube", animation.youtube);
		values.put("twitter", animation.twitter);
		values.put("soundcloud", animation.soundcloud);
		values.put("googleplus", animation.googleplus);
		values.put("deezer", animation.deezer);
		open();
		boolean result = db.insert(TABLE_ANIMATIONS, null, values) > 0;
		close();
		return result;
	}

	/**
	 * Efface les entrées de la table Salles
	 * 
	 * @return vrai(true) si l'insertion est correcte ou faux(false) si une
	 *         erreur s'est produite
	 */
	public boolean deleteTableSalles() {
		open();
		boolean result = db.delete(TABLE_SALLES, null, null) > 0;
		close();
		return result;
	}

	/**
	 * Efface les entrées de la table Notifications
	 * 
	 * @return vrai(true) si l'insertion est correcte ou faux(false) si une
	 *         erreur s'est produite
	 */
	public boolean deleteTableNotifications() {
		open();
		boolean result = db.delete(TABLE_NOTIFICATIONS, null, null) > 0;
		close();
		return result;
	}

	/**
	 * Efface les entrées de la table Animations
	 * 
	 * @return vrai(true) si l'insertion est correcte ou faux(false) si une
	 *         erreur s'est produite
	 */
	public boolean deleteTableAnimations() {
		open();
		boolean result = db.delete(TABLE_ANIMATIONS, null, null) > 0;
		close();
		return result;
	}

	public Salle getSalle(int idSalle) {
		open();

		Cursor c = db.query(TABLE_SALLES, new String[] { "nom", "description",
				"image", "type", "etage" }, "id=" + idSalle,
				null, null, null, null);
		c.moveToFirst();

		int id = idSalle;
		String nom = c.getString(0);
		String description = c.getString(1);
		String image = c.getString(2);
		int type = c.getInt(3);
		String etage = c.getString(4);

		Salle salle = new Salle(id, nom, description, image, type,
				etage);

		c.close();
		close();

		return salle;
	}

	public Animation getAnimation(int idAnimation) {
		open();

		Cursor c = db.query(TABLE_ANIMATIONS, new String[] { "idSalle",
				"heure_debut", "heure_fin", "idTypeAnimation", "nom",
				"description", "genre", "image", "thumbnail", "site",
				"facebook", "myspace", "youtube", "twitter", "soundcloud",
				"googleplus", "deezer", "myspace" }, "id=" + idAnimation, null,
				null, null, null);

		c.moveToFirst();

		int id = idAnimation;
		int idSalle = c.getInt(0);
		Date heureDebut = new Date(c.getLong(1));
		Date heureFin = new Date(c.getLong(2));

		int idTypeAnimation = c.getInt(3);
		String nom = c.getString(4);
		String description = c.getString(5);

		String genre = c.getString(7);
		String image = c.getString(8);
		String thumbnail = c.getString(9);
		String site = c.getString(10);
		String facebook = c.getString(11);
		String myspace = c.getString(12);
		String youtube = c.getString(13);
		String twitter = c.getString(14);
		String soundcloud = c.getString(15);
		String googleplus = c.getString(16);
		String deezer = c.getString(17);

		Animation animation = new Animation(id, heureDebut, heureFin, idSalle,
				idTypeAnimation, nom, description, genre, image, thumbnail,
				site, facebook, myspace, youtube, twitter, soundcloud,
				googleplus, deezer);
		c.close();
		close();

		return animation;
	}

	/**
	 * Récupère les animations d'une salle dans la base SQLite
	 * 
	 * @param scene
	 *            Salle pour laquelle on veut récupérer les animations
	 * @return Une liste d'animations
	 */
	public ArrayList<Notification> getNotifications() {
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		try {

			open();
			Cursor c = db.query(TABLE_NOTIFICATIONS, new String[] { "id",
					"titre", "contenu", "date"}, null, null, null,
					null, "date DESC");

			int numRows = c.getCount();

			c.moveToFirst();
			for (int i = 0; i < numRows; ++i) {

				int id = c.getInt(0);
				String titre = c.getString(1);
				String contenu = c.getString(2);
				Date date = new Date(c.getLong(3));

				Notification notification = new Notification(id, titre,
						contenu, date);

				notifications.add(notification);
				c.moveToNext();
			}
			c.close();
			close();
		} catch (Exception e) {
			Log.e(LOGTAG, e.toString());
		}
		return notifications;
	}

	/**
	 * Récupère les animations d'une salle dans la base SQLite
	 * 
	 * @param scene
	 *            Salle pour laquelle on veut récupérer les animations
	 * @return Une liste d'animations
	 */
	public ArrayList<Animation> getAnimationsSalle(int idSalle) {
		ArrayList<Animation> animations = new ArrayList<Animation>();
		try {

//			Log.d("idSalle", "idSalle:" + idSalle);
			open();

			Cursor c = db.query(TABLE_ANIMATIONS, new String[] { "id",
					"heure_debut", "heure_fin", "idTypeAnimation", "nom",
					"description", "genre", "image", "thumbnail", "site",
					"facebook", "myspace", "youtube", "twitter", "soundcloud",
					"googleplus", "deezer", "myspace" }, "idSalle=" + idSalle, null,
					null, null, null);

			int numRows = c.getCount();

//			Log.d("numRows", "numRows:" + numRows);

			c.moveToFirst();
			for (int i = 0; i < numRows; ++i) {

				int id = c.getInt(0);
				Date heureDebut = new Date(c.getLong(1));
				Date heureFin = new Date(c.getLong(2));

				int idTypeAnimation = c.getInt(3);
				String nom = c.getString(4);
				String description = c.getString(5);

				String genre = c.getString(6);
				String image = c.getString(7);
				String thumbnail = c.getString(8);
				String site = c.getString(9);
				String facebook = c.getString(10);
				String myspace = c.getString(11);
				String youtube = c.getString(12);
				String twitter = c.getString(13);
				String soundcloud = c.getString(14);
				String googleplus = c.getString(15);
				String deezer = c.getString(16);

				Animation animation = new Animation(id, heureDebut, heureFin, idSalle,
						idTypeAnimation, nom, description, genre, image, thumbnail,
						site, facebook, myspace, youtube, twitter, soundcloud,
						googleplus, deezer);

				animations.add(animation);
				c.moveToNext();
			}
			c.close();
			close();
		} catch (Exception e) {
			Log.e(LOGTAG, e.toString());
		}
		return animations;
	}

	/**
	 * Récupère les animations d'une salle dans la base SQLite
	 * 
	 * @param scene
	 *            Salle pour laquelle on veut récupérer les animations
	 * @return Une liste d'animations
	 */
	public ArrayList<Salle> getSalles() {
		ArrayList<Salle> salles = new ArrayList<Salle>();
		try {
			open();
			Cursor c = db.query(TABLE_SALLES, new String[] { "id", "nom",
					"description", "image", "type", "etage" },
					null, null, null, null, null);
			int numRows = c.getCount();
			c.moveToFirst();
			for (int i = 0; i < numRows; ++i) {

				int id = c.getInt(0);
				String nom = c.getString(1);
				String description = c.getString(2);
				String image = c.getString(3);
				int type = c.getInt(4);
				String etage = c.getString(5);

				Salle salle = new Salle(id, nom, description, image,
						type, etage);

				salles.add(salle);
				c.moveToNext();
			}
			c.close();
			close();
		} catch (Exception e) {
			Log.e(LOGTAG, e.toString());
		}
		return salles;
	}

	/**
	 * Récupère l'heure min du premier concert d'un jour
	 * 
	 * @return Un timestamp
	 */
	public long getHeureDebutMin() {
		long min = 0;
		try {
			open();
			Cursor c = db.query(TABLE_ANIMATIONS,
					new String[] { "heure_debut" }, "", null, null, null,
					"heure_debut LIMIT 1");
			c.moveToFirst();
			Log.d("minbdd", "min:" + c.getLong(0));
			min = c.getLong(0);
			c.close();
			close();
		} catch (Exception e) {
			Log.e(LOGTAG, e.toString());
		}
		return min;
	}

	/**
	 * Récupère l'heure min de la premiere animation
	 * 
	 * @return Un timestamp
	 */
	public long getHeureFinMax() {
		long max = 0;
		try {
			open();
			Cursor c = db.query(TABLE_ANIMATIONS, new String[] { "heure_fin" },
					null, null, null, null, "heure_fin DESC LIMIT 1");
			c.moveToFirst();
			max = c.getLong(0);
			c.close();
			close();
		} catch (Exception e) {
			Log.e(LOGTAG, e.toString());
		}
		return max;
	}
}
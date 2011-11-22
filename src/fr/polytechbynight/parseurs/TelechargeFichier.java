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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

/**
 * Classe gérant le télechargement de fichiers
 * 
 * @author Julien VERMET / Arzhel YOUNSI
 * @version 1.0
 */
public class TelechargeFichier{
	private static int tailleDistant = 0;
	private static int tailleLocal = 0;

	private static final String PATH = "/data/data/fr.polytechbynight/";

	/**
	 * Méthode statique gérant le télechargement de fichiers
	 * @param url Adresse du fichier
	 * @param fichierDest Nom du ficher en local
	 */
	public static void DownloadFromUrl(URL url, String fichierDest) throws IOException{ 
		File file;
		if (fichierDest.endsWith(".jpg"))
			file = new File(PATH + "images/",fichierDest);
		else
			file = new File(PATH,fichierDest);
		file.getParentFile().mkdirs();
		URLConnection ucon = url.openConnection();

		try {
			tailleDistant = ucon.getHeaderFieldInt("Content-Length", 0); //Récupére le header HTTP Content-Length
			tailleLocal =  (int) file.length();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Compare les tailles des fichiers
		if((tailleDistant == tailleLocal) && (tailleLocal != 0 )) return;

		InputStream is = ucon.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		ByteArrayBuffer baf = new ByteArrayBuffer(50);
		int current = 0;
		while ((current = bis.read()) != -1) {
			baf.append((byte) current);
		}
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(baf.toByteArray());
		fos.close();
	}

}

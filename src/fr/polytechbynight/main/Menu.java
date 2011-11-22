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

package fr.polytechbynight.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import fr.polytechbynight.R;
import fr.polytechbynight.divers.Infos;
import fr.polytechbynight.divers.Notifications;
import fr.polytechbynight.divers.Sponsors;
import fr.polytechbynight.programme.Programme;
import fr.polytechbynight.programme.Salles;

/**
 * Classe gérant l'activité affichant le menu principal (sous forme d'icones)
 * 
 * @author Julien VERMET / Arzhel YOUNSI
 * @version 1.0
 */
public class Menu extends Activity implements OnItemClickListener {

	private Integer[] menuIcones = { R.drawable.presentation,
			R.drawable.notifications, R.drawable.sponsors, R.drawable.salles,
			R.drawable.prog, R.drawable.teaser, R.drawable.info };

	/**
	 * Crée le menu à la création de l'activité
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		// XXX : A executer 1 fois puis commenter
		// A utiliser uniquement avant distribution
		// Outils.TelechargeImagesArtistes(this);

		// Lance la création de toutes les alarmes programmés.
		sendBroadcast(new Intent("fr.polytechbynight.action.tous_favoris"));

		GridView gvMenu = (GridView) findViewById(R.id.gvMenu);
		gvMenu.setAdapter(new ImageAdapter(this, menuIcones));
		gvMenu.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
		Intent i = null;

		switch (arg2) {
		case 0: // Présentation
			i = new Intent(Menu.this, Presentation.class);
			break;
		case 1: // Notifications
			i = new Intent(Menu.this, Notifications.class);
			break;
		case 2: // Sponsors
			i = new Intent(Menu.this, Sponsors.class);
			break;
		case 3: // Salles
			i = new Intent(Menu.this, Salles.class);
			break;
		case 4: // Prog.
			i = new Intent(Menu.this, Programme.class);
			break;
		case 5: // Teaser
//			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=eDesknU7_mk")));
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=qcmqjCqA3mA")));
			break;
		case 6: // Info
			i = new Intent(Menu.this, Infos.class);
			break;
		}
		if (i != null)
			startActivity(i);	
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 0:
			return createAboutDialog();
		default:
			return null;
		}
	}

	/**
	 * Crée une fenetre de dialogue
	 * 
	 * @return Dialog La fenetre de dialogue crée
	 */
	private Dialog createAboutDialog() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);

		TextView tvAbout = new TextView(this);
		tvAbout.setPadding(5, 0, 0, 0);
		// TODO : Mettre dans le fichier string
		tvAbout.setText(Html
				.fromHtml("<center>"
						+ "Projet réalisé par :<br/>"
						+ "Julien Vermet <a href=\"mailto:ju.vermet@gmail.com\">ju.vermet@gmail.com</a><br/>"
						+ "étudiant à Polytech'Nantes<br/><br/>"
						+ "Graphismes réalisés par :<br/>"
						+ "Florian Hamon<br/>" + "</center>"));
		tvAbout.setMovementMethod(LinkMovementMethod.getInstance());
		tvAbout.setTextColor(Color.WHITE);

		builder.setTitle(getString(R.string.menuAPropos));
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setView(tvAbout);
		builder.setPositiveButton(getString(android.R.string.ok), null);
		builder.setCancelable(true);
		return builder.create();
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		menu.add(0, 1000, 0, getString(R.string.menuAPropos)).setIcon(
				android.R.drawable.ic_menu_info_details);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 1000:
			showDialog(0);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
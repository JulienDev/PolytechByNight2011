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

package fr.polytechbynight.divers;

import java.util.ArrayList;

import android.app.ListActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import fr.polytechbynight.R;
import fr.polytechbynight.db.DB;
import fr.polytechbynight.db.Notification;

/**
 * Classe gerant la liste des favois
 * 
 * @author Julien VERMET / Arzhel YOUNSI
 * @version 1.0
 */
public class Notifications extends ListActivity {

	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.liste);
		
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText("Notifications");

		ImageView ivHomeButton = (ImageView) findViewById(R.id.ivHomeButton);
		ivHomeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		lv = getListView();
		
		DB db = new DB(this);
		ArrayList<Notification> notifications = db.getNotifications();
		
		if (notifications.size() == 0)
		{
			TextView tvInfo = (TextView) findViewById(R.id.tvInfo);
			tvInfo.setText("Aucune notification recue pour le moment");
			tvInfo.setVisibility(View.VISIBLE);
			
			lv.setVisibility(View.GONE);
		}
		
		lv.setAdapter(new ListeAdapteur(this, notifications));
	}
}

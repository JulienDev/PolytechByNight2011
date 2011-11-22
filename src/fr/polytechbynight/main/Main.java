package fr.polytechbynight.main;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import fr.polytechbynight.R;
import fr.polytechbynight.db.DB;

/**
 * Classe gérant le SplashScreen
 * 
 * @author Julien VERMET / Arzhel YOUNSI
 * @version 1.0
 */
public class Main extends Activity {
	private static final int sStopFlash = 0;
	private static final long sSplashTime = 1000;
	private static final int dbVersionLast = 1;

	/**
	 * Envoie un message au handler une fois le temps écoulé
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);


		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		int dbversion = prefs.getInt("dbversion", 0);
		
		if (dbversion < dbVersionLast)
		{
			
			Log.d("majDB", "majDB");
			
//			if(!(new File("/data/data/fr.polytechbynight/databases/"+ "database.db").exists()))
//			{
				try {
					new File("/data/data/fr.polytechbynight/databases/").mkdirs();
					InputStream inputStream = getResources().getAssets().open(DB.DATABASE_NAME);
					OutputStream outputStream = new FileOutputStream("/data/data/fr.polytechbynight/databases/database.db");
					byte[] buffer = new byte[1024];
					int length;
					while ((length = inputStream.read(buffer))>0){
						outputStream.write(buffer, 0, length);
					}
					outputStream.flush();
					outputStream.close();
					inputStream.close();
					
					SharedPreferences.Editor editor = prefs.edit();
					editor.putInt("dbversion", 1);
					editor.commit();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
//			}
			
		}


		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
		registrationIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0)); // boilerplate
		registrationIntent.putExtra("sender", "polytechbynight@gmail.com");
		startService(registrationIntent);

		Message msg = new Message();
		msg.what = sStopFlash;
		splashHandler.sendMessageDelayed(msg, sSplashTime);
	}

	/**
	 * Redirige vers le menu une fois le message reçu
	 */
	private Handler splashHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case sStopFlash:
				//Le temps est écoulé, on démarre un intent vers le menu
				Intent intent = new Intent(Main.this, Menu.class);
				startActivity(intent);
				//On termine le splashscreen pour ne pas le voir réapparaitre avec un retour
				finish();
				break;
			}
			super.handleMessage(msg);
		}
	};
}
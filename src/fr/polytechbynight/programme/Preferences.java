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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import fr.polytechbynight.R;

/**
 * Classe gérant les préférences
 * 
 * @author Julien VERMET / Arzhel YOUNSI
 * @version 1.0
 */
public class Preferences extends PreferenceActivity  implements
OnPreferenceChangeListener {

	private Preference cbAlarme;
	private Preference cbVibrer;
	private Preference etDuree;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		cbAlarme = findPreference("cbAlarme");
		cbVibrer = findPreference("cbVibrer");
		etDuree = findPreference("etDuree");
		cbAlarme.setOnPreferenceChangeListener(this);
		cbVibrer.setOnPreferenceChangeListener(this);
		etDuree.setOnPreferenceChangeListener(this);

	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {

		//Défini les préférences à utiliser
		SharedPreferences prefs = getSharedPreferences("fr.asso.vieillescharrues", Context.MODE_PRIVATE);
		Editor edit = prefs.edit();

		if (preference.getKey().equals("cbAlarme")) {
			cbVibrer.setEnabled((Boolean) newValue);
		}
		if (preference.getKey().equals("etDuree")) {
			String value=(String)newValue;
			if(value!=null && value.length()==0)return false;
			edit.putInt("etDuree", Integer.parseInt(value));

			sendBroadcast(new Intent("fr.asso.vieillescharrues.action.tous_favoris"));

		}
		if(preference.getKey().equals("cbAlarme") || preference.getKey().equals("cbVibrer")){
			edit.putBoolean(preference.getKey(), (Boolean)newValue);
		}
		edit.commit();
		return true;
	}
}

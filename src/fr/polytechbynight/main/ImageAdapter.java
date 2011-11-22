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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import fr.polytechbynight.R;

/**
 * Classe gérant la création du menu principal sous forme d'icones
 * 
 * @author Julien VERMET / Arzhel YOUNSI
 * @version 1.0
 */
public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private Integer[] menuIcones;
	private LayoutInflater inflater;

	/**
	 * Constructeur de la classe ImageAdapter
	 * @param c 	Context de l'activité qui a créé l'objet ImageAdapter
	 * @param MENU_ICONES	Icones du menu
	 * @param MENU_ICONES_TITRES	Titres correspondant aux icones
	 */

	public ImageAdapter(Context ctx, Integer[] MENU_ICONES) {
		this.mContext = ctx;
		this.menuIcones = MENU_ICONES;
		this.inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return menuIcones.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	/*
	 Crée une view pour chaque choix du menu
	 Une view contient une icone ainsi que son titre
	 */
	public View getView(int position, View convertView, ViewGroup parent) {


		ImageView iv = new ImageView(mContext);
		iv.setImageResource(menuIcones[position]);
		return iv; 
	}
} 
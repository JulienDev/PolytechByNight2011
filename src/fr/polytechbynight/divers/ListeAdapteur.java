package fr.polytechbynight.divers;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fr.polytechbynight.R;
import fr.polytechbynight.db.Notification;

/**
 * CategoriesListAdapter is the class who create categories list views 
 * 
 * @author Julien VERMET
 * @version 1.0
 */
public class ListeAdapteur extends BaseAdapter {

	ArrayList<Notification> notifications;
	Context ctx;

	public ListeAdapteur(Context ctx, ArrayList<Notification> notifications) {
		super();
		this.ctx = ctx;
		this.notifications = notifications;
	}

	public int getCount() {
		return this.notifications.size();
	}

	public Object getItem(int arg0) {
		return this.notifications.get(arg0);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {

		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.element_liste, null);
		}
		TextView tvTitre = (TextView) v.findViewById(R.id.tvTitre);		
		tvTitre.setText(notifications.get(position).titre);

		WebView wvContenu = (WebView) v.findViewById(R.id.wvContenu);		
		wvContenu.setBackgroundColor(Color.TRANSPARENT);
		wvContenu.loadDataWithBaseURL(null, "<div style='text-align: justify;'><font color='#ffffff'>" +notifications.get(position).contenu + "</font></div>", null, "utf-8", null);

		Locale locale = Locale.getDefault().FRENCH;
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);

		TextView tvDate = (TextView) v.findViewById(R.id.tvDate);
		tvDate.setText(dateFormat.format(notifications.get(position).date));

		v.setId( notifications.get(position).id );

		return v;
	}
}
package fr.polytechbynight.divers;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import fr.polytechbynight.R;
import fr.polytechbynight.main.ImageAdapter;

/**
 * Classe gerant la liste des favois
 * 
 * @author Julien VERMET / Arzhel YOUNSI
 * @version 1.0
 */
public class Sponsors extends ListActivity {

	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sponsors);
		
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText("Sponsors");

		ImageView ivHomeButton = (ImageView) findViewById(R.id.ivHomeButton);
		ivHomeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		lv = getListView();
		
//		DB db = new DB(this);
//		ArrayList<Notification> notifications = db.getNotifications();
//		
//		if (notifications.size() == 0)
//		{
//			TextView tvInfo = (TextView) findViewById(R.id.tvInfo);
//			tvInfo.setText("Aucun sponsor a afficher");
//			tvInfo.setVisibility(View.VISIBLE);
//			
//			lv.setVisibility(View.GONE);
//		}
//		
//		lv.setAdapter(new ListeAdapteur(this, notifications));
		
		String mPath = "sponsors/logos";
        lv.setAdapter(new SponsorsAdapteur(this, mPath));
//		lv.setDividerHeight(15);
		
	}
}

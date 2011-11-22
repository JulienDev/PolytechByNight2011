package fr.polytechbynight.programme;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import fr.polytechbynight.R;
import fr.polytechbynight.db.Animation;
import fr.polytechbynight.db.DB;
import fr.polytechbynight.db.Salle;
import fr.polytechbynight.outils.MyHorizontalScrollView;
import fr.polytechbynight.outils.MyVerticalScrollView;
import fr.polytechbynight.parseurs.Updater;

public class Programme extends Activity {

	static boolean sChargement = false;
	private ArrayList<Animation> animations;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.frise_programme);

		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText("Prog.");

		ImageView ivHomeButton = (ImageView) findViewById(R.id.ivHomeButton);
		ivHomeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		DB db = new DB(this);

		MyHorizontalScrollView mhsvHeures = (MyHorizontalScrollView) findViewById(R.id.mhsvHeures);
		MyHorizontalScrollView mhsvProg = (MyHorizontalScrollView) findViewById(R.id.mhsvProg);
		mhsvHeures.mhsvLink = mhsvProg;
		mhsvProg.mhsvLink = mhsvHeures;

		MyVerticalScrollView mvsvSalles = (MyVerticalScrollView) findViewById(R.id.mvsvSalles);
		MyVerticalScrollView mvsvProg = (MyVerticalScrollView) findViewById(R.id.mvsvProg);
		mvsvSalles.mvsvLink = mvsvProg;
		mvsvProg.mvsvLink = mvsvSalles;

		// TOP (Heures)

		long min = db.getHeureDebutMin();
		long max = db.getHeureFinMax();

		DateFormat dfHeure = new SimpleDateFormat("HH'h'mm");
		DateFormat dfMinute = new SimpleDateFormat("mm");

//		Log.d("min", "min:" + min + " soit:" + dfHeure.format(min));
//		Log.d("max", "max:" + max + " soit:" + dfHeure.format(max));

		long heure = min;
		String chercheure = "";
		while ( !chercheure.equals("59") && !chercheure.equals("29"))
		{
//			Log.d("heure", "heure" + heure);
			chercheure = dfMinute.format(heure);
			heure += 60000;
//			Log.d("chercheure", "chercheure:" + chercheure);
//			Log.d("chercheure2", "chercheure2:" + dfHeure.format(heure));
		}

		RelativeLayout rlHeure = (RelativeLayout) findViewById(R.id.rlHeures);
		for (long i= heure; i <= max; i+=1800000)
		{
			String h = ""+ dfHeure.format(i);

			TextView tvHeure = new TextView(this);
			tvHeure.setText(h);
			tvHeure.setTextColor(Color.WHITE);
			tvHeure.setTextSize(18);

			long largeur = ((i - min) / 15000);

			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(pixelsToDip( (int) largeur) , 0, 0, 0);
			rlHeure.addView(tvHeure, layoutParams);
		}


		//TextView tvHeures = (TextView) findViewById(R.id.tvHeures);
		String serie = "";
		for (int i = 0; i < 100; i++)
		{
			serie += i + " - ";
		}
		//tvHeures.setText(serie);

		//LEFT (Salles)
		LinearLayout ll2 = (LinearLayout) findViewById(R.id.ll2);

		ArrayList<Salle> salles = db.getSalles();
		for (int i = 0; i < salles.size(); i++)
		{
			if ((db.getAnimationsSalle(salles.get(i).id)).size() != 0)
			{
				TextView tv = new TextView(this);
				tv.setText(Html.fromHtml( "<b>" + salles.get(i).nom + "</b>" ));
				tv.setTextSize(19);
				tv.setPadding(0, pixelsToDip(5), 0, 0);
				tv.setSingleLine();
				ll2.addView(tv);

				TextView tv2 = new TextView(this);
				String type = "";
				if (salles.get(i).type == 0)
					type = "Bateau";
				else
					type = "Escale";

				tv2.setText(Html.fromHtml( "<i>" + type + "-" + salles.get(i).etage+ "</i>" ));
				tv2.setTextSize(12);
				tv2.setPadding(0, 0, 0, pixelsToDip(5));
				ll2.addView(tv2);
			}
		}

		//RIGHT (Prog)

		LinearLayout llProg = (LinearLayout) findViewById(R.id.llProg);

		for (int j=0; j<salles.size(); j++)
		{

			RelativeLayout rlScene = new RelativeLayout(this);

			animations = db.getAnimationsSalle(salles.get(j).id);

			for (final Animation animation : animations) {

				int offsetX = (int) (( animation.heureDebut.getTime() - min) / 15000);
				int duree = (int) (animation.heureFin.getTime() - animation.heureDebut.getTime()) / 15500;

				String artiste = animation.nom;

				LinearLayout llAnimation = new LinearLayout(this);
				llAnimation.setOrientation(LinearLayout.VERTICAL);
				llAnimation.setBackgroundResource(R.drawable.bg_cadre_frise);

				TextView tvAnimationNom = new TextView(this);
				tvAnimationNom.setText( Html.fromHtml("<b>" + artiste + "</b>"));
				tvAnimationNom.setTextColor(Color.parseColor("#223342"));
				tvAnimationNom.setTextSize(20);
				tvAnimationNom.setPadding(pixelsToDip(10), 0, 0, 0);
				tvAnimationNom.setSingleLine();

				TextView tvAnimationGenre = new TextView(this);
				tvAnimationGenre.setText( Html.fromHtml("<i>"+ animation.genre +"</i>"));
				tvAnimationGenre.setTextColor(Color.parseColor("#223342"));
				tvAnimationGenre.setTextSize(12);
				tvAnimationGenre.setPadding(pixelsToDip(10), 0, 0, 0);

				llAnimation.addView(tvAnimationNom);
				llAnimation.addView(tvAnimationGenre);

				llAnimation.setId(animation.id);
				llAnimation.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						lancerAnimation(animation);
					}
				});
				
				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(pixelsToDip(duree), pixelsToDip(48));
				layoutParams.setMargins(pixelsToDip(offsetX), pixelsToDip(5), 0, 0);    
				rlScene.addView(llAnimation, layoutParams);	

			}
			llProg.addView(rlScene);
		}      
	}
	
	private int pixelsToDip(int pixels)
	{
		float d = getResources().getDisplayMetrics().density;
		return (int) (pixels * d);
	}

	public void lancerAnimation(Animation animation)
	{
		Intent i = new Intent(this, UneAnimation.class);
		i.putExtra("animation", animation);
		startActivityForResult(i, 0);
	}

	/**
	 * Lance le thread mettant � jour la base de donn�e
	 */
	protected void majClicked() {
		final Thread t = new Thread(new Updater(handler, getApplicationContext(),2));
		t.start();
	}

	/**
	 * Handler gerant les messages en provenance du thread
	 */
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			if (message == null)
				return;
			switch (message.what) {
			case 1:
				Toast.makeText(getApplicationContext(), getString(R.string.miseAJour), Toast.LENGTH_SHORT).show();
				sChargement = true;
				break;
			case 2:
				Toast.makeText(getApplicationContext(), getString(R.string.miseAJourTermine), Toast.LENGTH_SHORT).show();
				sChargement = false;
				break;
			case 3:
				Toast.makeText(getApplicationContext(), getString(R.string.miseAJourErreur), Toast.LENGTH_SHORT).show();
				sChargement = false;
				break;

			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1000, 0, getString(R.string.actualiser)).setIcon(android.R.drawable.ic_menu_rotate);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == 1000) 
		{
			majClicked();
		}

		return super.onOptionsItemSelected(item);
	}

}
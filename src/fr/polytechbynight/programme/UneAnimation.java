package fr.polytechbynight.programme;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import fr.polytechbynight.R;
import fr.polytechbynight.db.Animation;
import fr.polytechbynight.db.DB;
import fr.polytechbynight.db.Salle;
import fr.polytechbynight.outils.Outils;
import fr.polytechbynight.parseurs.TelechargeFichier;

public class UneAnimation extends Activity {

	private Animation animation;
	LinearLayout llLiens;
	public static final String PATH = "/data/data/fr.polytechbynight/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.une_animation);

		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText("Programmation");

		ImageView ivHomeButton = (ImageView) findViewById(R.id.ivHomeButton);
		ivHomeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		if (savedInstanceState != null) {
			animation = (Animation) savedInstanceState.getSerializable("animation");
		} else {
			Bundle extras = getIntent().getExtras();
			animation = (Animation) extras.getSerializable("animation");
		}
		
	    Bitmap imageB = null;
	    boolean existeEnAsset = false;
		
		if (!animation.image.equals(""))
        {            
            
            try {
                imageB = BitmapFactory.decodeStream(getResources().getAssets().open("artistes/" + animation.image ));
                existeEnAsset  = true;
            } catch (IOException e) {
                Log.e("Image","n'existe pas en asset");
            }

            
            if(!(new File(PATH+"images/" + animation.image ).exists()) && existeEnAsset == false)
            {
                try {
                    TelechargeFichier.DownloadFromUrl(new URL("http://polytechbynight.fr/site/images/artistes/"+animation.image), animation.image);
                }catch (IOException e) {
                    Log.e("Image","Impossible de télecharger l'image");
                }
            }
            try {
                if(existeEnAsset == false)
                    imageB = BitmapFactory.decodeFile(PATH+"images/" + animation.image ); 
                Matrix matrix = new Matrix();
                float scale = ((float) 200) / imageB.getHeight();
                matrix.postScale(scale, scale); 
                BitmapDrawable newImage = new BitmapDrawable(Bitmap.createBitmap(imageB, 0, 0, imageB.getWidth(), imageB.getHeight(), matrix, true)); 
                ImageView imgView = (ImageView)findViewById(R.id.ivArtiste);
                imgView.setImageDrawable(newImage);
                imgView.setScaleType(ScaleType.CENTER);

            } catch (Exception e) {
                Log.e("Image","Impossible d'afficher l'image");
            }
        }

		TextView tvPresentationUn = (TextView) findViewById(R.id.tvPresentationUn);
		tvPresentationUn.setText( Html.fromHtml(animation.nom));
		tvPresentationUn.setSingleLine();

		Locale locale = Locale.getDefault().FRENCH;
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH'h'mm");

		DB db = new DB(this);
		Salle salle = db.getSalle(animation.idSalle);

		TextView tvPresentationDeux = (TextView) findViewById(R.id.tvPresentationDeux);
		tvPresentationDeux.setText(salle.nom + " (" + dateFormat.format(animation.heureDebut) + ")");

		TextView tvPresentationTrois = (TextView) findViewById(R.id.tvPresentationTrois);
		tvPresentationTrois.setText( Html.fromHtml(animation.genre) );

		WebView wvPresentation = (WebView) findViewById(R.id.wvPresentation);
		wvPresentation.setBackgroundColor(Color.TRANSPARENT);
		wvPresentation.loadDataWithBaseURL(null, "<div style='text-align: justify;'><font color='#ffffff'>" + animation.description + "</font></div>", null, "UTF-8", null);

		llLiens = (LinearLayout) findViewById(R.id.llLiens);

		if (!animation.soundcloud.equals(""))
			ajouterLien(R.drawable.soundcloud, animation.soundcloud);

		if (!animation.facebook.equals(""))
			ajouterLien(R.drawable.facebook, animation.facebook);

		if (!animation.twitter.equals(""))
			ajouterLien(R.drawable.twitter, animation.twitter);

		if (!animation.myspace.equals(""))
			ajouterLien(R.drawable.myspace, animation.myspace);

		if (!animation.youtube.equals(""))
			ajouterLien(R.drawable.youtube, animation.youtube);

		if (!animation.site.equals(""))
			ajouterLien(R.drawable.www, animation.site);

		if (!animation.googleplus.equals(""))
			ajouterLien(R.drawable.googleplus, animation.googleplus);

		if (!animation.deezer.equals(""))
			ajouterLien(R.drawable.deezer, animation.deezer);
	}

	private void ajouterLien(int res, final String link)
	{
		ImageView iv = new ImageView(this);
		iv.setImageResource(res);
		iv.setPadding(5, 0, 0, 0);
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Log.d("You", "y:" + link);
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=oJsejsRprgI")));


				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
			}
		});
		llLiens.addView(iv);
		llLiens.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putSerializable("animation", animation);

		super.onSaveInstanceState(savedInstanceState);
	}
}
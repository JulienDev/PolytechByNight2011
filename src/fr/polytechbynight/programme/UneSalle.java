package fr.polytechbynight.programme;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import fr.polytechbynight.R;
import fr.polytechbynight.db.DB;
import fr.polytechbynight.db.Salle;
import fr.polytechbynight.parseurs.TelechargeFichier;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class UneSalle extends Activity {

	private int idSalle;
	public static final String PATH = "/data/data/fr.polytechbynight/";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.presentation_generique);
		
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText("Salle");

		ImageView ivHomeButton = (ImageView) findViewById(R.id.ivHomeButton);
		ivHomeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		if (savedInstanceState != null) {
			idSalle = savedInstanceState.getInt("idSalle");
		} else {
			Bundle extras = getIntent().getExtras();
			idSalle = extras.getInt("idSalle");
		}
		
		DB db = new DB(this);
		
		Salle salle = db.getSalle(idSalle);
		
	    Bitmap imageB = null;
	    boolean existeEnAsset = false;
		
		if (!salle.image.equals(""))
        {            
            
            try {
            	Log.d("image", "i:" + salle.image);
                imageB = BitmapFactory.decodeStream(getResources().getAssets().open("salles/" + salle.image ));
                existeEnAsset  = true;
            } catch (IOException e) {
                Log.e("Image","n'existe pas en asset");
            }

            
            if(!(new File(PATH+"images/" + salle.image ).exists()) && existeEnAsset == false)
            {
                try {
                    TelechargeFichier.DownloadFromUrl(new URL("http://polytechbynight.fr/site/images/salles/"+salle.image), salle.image);
                }catch (IOException e) {
                    Log.e("Image","Impossible de télecharger l'image");
                }
            }
            
            try {
                if(existeEnAsset == false)
                    imageB = BitmapFactory.decodeFile(PATH+"images/" + salle.image ); 
               
                Matrix matrix = new Matrix();
                BitmapDrawable newImage = new BitmapDrawable(Bitmap.createBitmap(imageB, 0, 0, imageB.getWidth(), imageB.getHeight(), matrix, true)); 
                ImageView imgView = (ImageView)findViewById(R.id.ivPresentation);
                imgView.setImageDrawable(newImage);
                imgView.setScaleType(ScaleType.FIT_XY);

            } catch (Exception e) {
                Log.e("Image","Impossible d'afficher l'image : " + e);
            }
        }
		
		TextView tvPresentationUn = (TextView) findViewById(R.id.tvPresentationUn);
		tvPresentationUn.setText(salle.nom);
		tvPresentationUn.setSingleLine(true);
		
		String type = "";
		if ( salle.type == 1 )
			type = "Escale";
		else
			type = "Bateau";
		
		TextView tvPresentationDeux = (TextView) findViewById(R.id.tvPresentationDeux);
		tvPresentationDeux.setText(type);
		
		TextView tvPresentationTrois = (TextView) findViewById(R.id.tvPresentationTrois);
		tvPresentationTrois.setText(salle.etage);
		
		WebView wvPresentation = (WebView) findViewById(R.id.wvPresentation);
		wvPresentation.setBackgroundColor(Color.TRANSPARENT);
		wvPresentation.loadDataWithBaseURL(null, "<div style='text-align: justify;'><font color='#ffffff'>" + salle.description + "</font></div>", null, "UTF-8", null);

	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt("idSalle", idSalle);

		super.onSaveInstanceState(savedInstanceState);
	}
}

package fr.polytechbynight.programme;

import fr.polytechbynight.R;
import fr.polytechbynight.main.Menu;
import fr.polytechbynight.main.Presentation;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Salles extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.salles);
		
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText("Salles");

		ImageView ivHomeButton = (ImageView) findViewById(R.id.ivHomeButton);
		ivHomeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		

		ImageView ivSalleAbysses = (ImageView) findViewById(R.id.ivSalleAbysses);
		ivSalleAbysses.setId(2);
		ivSalleAbysses.setOnClickListener(this);
		
		ImageView ivSalleMachines = (ImageView) findViewById(R.id.ivSalleMachines);
		ivSalleMachines.setId(3);
		ivSalleMachines.setOnClickListener(this);
		
		ImageView ivSalleDiscocotier = (ImageView) findViewById(R.id.ivSalleMiami);
		ivSalleDiscocotier.setId(4);
		ivSalleDiscocotier.setOnClickListener(this);
		
		ImageView ivSalleCuba = (ImageView) findViewById(R.id.ivSalleCuba);
		ivSalleCuba.setId(5);
		ivSalleCuba.setOnClickListener(this);
		
		ImageView ivSalleCabineCapitaine = (ImageView) findViewById(R.id.ivSalleCabineCapitaine);
		ivSalleCabineCapitaine.setId(6);
		ivSalleCabineCapitaine.setOnClickListener(this);
		
		ImageView ivSalleCalePirates = (ImageView) findViewById(R.id.ivSalleCalePirates);
		ivSalleCalePirates.setId(7);
		ivSalleCalePirates.setOnClickListener(this);
		
		ImageView ivSalleCasino = (ImageView) findViewById(R.id.ivSalleCasino);
		ivSalleCasino.setId(8);
		ivSalleCasino.setOnClickListener(this);
		
		ImageView ivSalleSuitePresidentielle = (ImageView) findViewById(R.id.ivSalleSuitePresidentielle);
		ivSalleSuitePresidentielle.setId(9);
		ivSalleSuitePresidentielle.setOnClickListener(this);
		
		ImageView ivSallePont = (ImageView) findViewById(R.id.ivSallePont);
		ivSallePont.setId(1);
		ivSallePont.setOnClickListener(this);
		
		ImageView ivSalleStPetersbourg = (ImageView) findViewById(R.id.ivSalleStPetersbourg);
		ivSalleStPetersbourg.setId(10);
		ivSalleStPetersbourg.setOnClickListener(this);
		
		ImageView ivSalleStTropez = (ImageView) findViewById(R.id.ivSalleStTropez);
		ivSalleStTropez.setId(11);
		ivSalleStTropez.setOnClickListener(this);
			
		ImageView ivSalleJapon = (ImageView) findViewById(R.id.ivSalleJapon);
		ivSalleJapon.setId(12);
		ivSalleJapon.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int idSalle = v.getId();
		
		Log.d("idSalle", "idSalle:" + idSalle);
		
		Intent i = new Intent(Salles.this, UneSalle.class);
		i.putExtra("idSalle", idSalle);
		startActivity(i);
	}
}

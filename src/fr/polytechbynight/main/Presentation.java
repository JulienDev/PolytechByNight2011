package fr.polytechbynight.main;

import fr.polytechbynight.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class Presentation extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.presentation);
		
		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText("Présentation");

		ImageView ivHomeButton = (ImageView) findViewById(R.id.ivHomeButton);
		ivHomeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		WebView wvPresentation = (WebView) findViewById(R.id.wvPresentation);
		wvPresentation.setBackgroundColor(Color.TRANSPARENT);
		wvPresentation.loadDataWithBaseURL(null, "<div style='text-align: justify;'><font color='#ffffff'>" + getString(R.string.presentation) + "</font></div>", null, "UTF-8", null);
		
	}
}

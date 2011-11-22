package fr.polytechbynight.divers;


import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import fr.polytechbynight.R;

public class Infos extends MapActivity {

	MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.infos);

		TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
		tvTitle.setText("Infos");

		ImageView ivHomeButton = (ImageView) findViewById(R.id.ivHomeButton);
		ivHomeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		WebView wvInfos = (WebView) findViewById(R.id.wvInfos);
		wvInfos.loadUrl("file:///android_asset/infos/infos.htm");

		mapView = (MapView) findViewById(R.id.mvInfos);

		String coordinates[] = {"47.28227", "-1.51511"};
		double lat = Double.parseDouble(coordinates[0]);
		double lng = Double.parseDouble(coordinates[1]);

		GeoPoint p = new GeoPoint(
				(int) (lat * 1E6), 
				(int) (lng * 1E6));

		MapController mc = mapView.getController();

		mc.animateTo(p);
		mc.setZoom(12); 

		MapOverlay mapOverlay = new MapOverlay();
		List<Overlay> listOfOverlays = mapView.getOverlays();
        listOfOverlays.clear();
        listOfOverlays.add(mapOverlay);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	class MapOverlay extends com.google.android.maps.Overlay
	{       
		@Override
		public boolean onTouchEvent(MotionEvent e, MapView mapView) 
		{   
			if (e.getAction() == MotionEvent.ACTION_UP) {                
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
						Uri.parse("http://maps.google.com/maps?daddr=47.28227,-1.51511"));
				startActivity(intent);
			}                            
			return false;
		}        
	}

}

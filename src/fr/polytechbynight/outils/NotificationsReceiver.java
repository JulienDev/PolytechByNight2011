package fr.polytechbynight.outils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import fr.polytechbynight.R;
import fr.polytechbynight.db.DB;
import fr.polytechbynight.main.Menu;

public class NotificationsReceiver extends C2DMBroadcastReceiver {

	@Override
	protected void onError(Context context, String error) {
		// traitement de l'erreur
	}

	@Override
	protected void onRegistration(Context context, String registrationId) {
		Log.e("C2DM", "Registration ID arrived: Fantastic!!!");
		Log.e("C2DM", registrationId);

		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);  

		Log.e("C2DM", ">>>>device unique id " + telephonyManager.getDeviceId());  

		// send to server  
		BufferedReader in = null;  
		try {  
			HttpClient client = new DefaultHttpClient();  
			HttpGet request = new HttpGet();  
			try {  
				request.setURI(new URI("http://www.polytechbynight.fr/site/android/tokens_receiver.php?deviceid="+URLEncoder.encode(telephonyManager.getDeviceId())+"&devicetoken="+URLEncoder.encode(registrationId).toString()));  
			} catch (URISyntaxException e) {  
				e.printStackTrace();
			}  
			HttpResponse response = client.execute(request);  
			in = new BufferedReader  
			(new InputStreamReader(response.getEntity().getContent()));  
			StringBuffer sb = new StringBuffer("");  
			String line = "";  
			String NL = System.getProperty("line.separator");  
			while ((line = in.readLine()) != null) {  
				sb.append(line + NL);  
			}  
			in.close();  
			String page = sb.toString();  
			System.out.println(page);  
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {  
			if (in != null) {  
				try {  
					in.close();  
				} catch (IOException e) {  
					e.printStackTrace();  
				}  
			}  
		}
	}

	@Override
	protected void onUnregistration(Context context) {
		// traitement du désabonnement
	}

	@Override
	protected void onMessageReceived(Context context, Intent intent) {

		Log.d("Message", "Reçu");
		
		String donnees = intent.getStringExtra("message"); // data.message contient le texte de la notification
		int iconId = R.drawable.icon;
				
		StringTokenizer st = new StringTokenizer(donnees, "||");
		String titre = st.nextToken();
		String message = st.nextToken();
		Date date = new Date(System.currentTimeMillis());
		
		fr.polytechbynight.db.Notification notif= new fr.polytechbynight.db.Notification(titre, message, date);

		
		DB db = new DB(context);
		db.insertNotification(notif);

		// création de la notification :
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(iconId, message, System.currentTimeMillis()); 

		// création de l'activité à démarrer lors du clic :
		Intent notifIntent = new Intent(context.getApplicationContext(), Menu.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notifIntent, 0);

		// affichage de la notification dans le menu déroulant :
		notification.setLatestEventInfo(context, titre, message, contentIntent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL; // la notification disparaitra une fois cliquée

		// lancement de la notification :
		notificationManager.notify(1, notification);
	}

}
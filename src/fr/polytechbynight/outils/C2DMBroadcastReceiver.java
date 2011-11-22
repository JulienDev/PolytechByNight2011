package fr.polytechbynight.outils;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class C2DMBroadcastReceiver extends BroadcastReceiver {

	protected abstract void onError(Context context, String error);
	protected abstract void onRegistration(Context context, String registrationId);
	protected abstract void onUnregistration(Context context);
	protected abstract void onMessageReceived(Context context, Intent intent);

	@Override
	// méthode appelée lors de l'événement broadcast (les infos sont contenues dans l'intent)
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")) {
			handleRegistration(context, intent);
		} else if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
			onMessageReceived(context, intent);
		}
	}

	private void handleRegistration(Context context, Intent intent) {
		String error = intent.getStringExtra("error");
		String unregistration = intent.getStringExtra("unregistered");
		String registration = intent.getStringExtra("registration_id");

		if (error != null) {
			onError(context, error);
		} else if (unregistration != null) {
			onUnregistration(context);
		} else if (registration != null) {
			onRegistration(context, registration);
		}
	}
}
package in.lguruprasad.buzznotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

public class MyAlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Vibrator v = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(500);
		MyNotificationManager.setRepeatingAlarm(context, MyPreferencesManager.getNotificationInterval(context), true);
	}
}

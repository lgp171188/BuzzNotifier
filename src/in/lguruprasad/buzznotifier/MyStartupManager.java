package in.lguruprasad.buzznotifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyStartupManager extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if (wasCleanShutdownDone(context)) {
			setAlarmForExistingNotification(context);
		}
		else {
			clearExistingNotificationInterval(context);
		}
	}
	private void clearExistingNotificationInterval(Context context) {
		MyPreferencesManager.deleteNotificationInterval(context);
		MyPreferencesManager.setCleanShutdownNotDone(context);
	}
	private boolean wasCleanShutdownDone(Context context) {
		return MyPreferencesManager.getCleanShutdownDone(context);
	}
	private void setAlarmForExistingNotification(Context context) {
		int interval = MyPreferencesManager.getNotificationInterval(context);
		if (interval != MyNotificationManager.ALARM_NOT_SET) {
			CharSequence text = context.getResources().getString(R.string.startup_toast_message);
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			MyNotificationManager.setRepeatingAlarm(context, interval);
		}
	}
}
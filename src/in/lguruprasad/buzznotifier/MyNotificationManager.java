package in.lguruprasad.buzznotifier;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class MyNotificationManager {
	private MyNotificationManager() {}
	
	public static final int ALARM_NOT_SET = -1;
	
	private static AlarmManager getAlarmManager(Context context) {
		return (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	}
	private static PendingIntent getIntervalAlarmIntent(Context context) {
		return PendingIntent.getBroadcast(context, 0, new Intent(context, MyAlarmReceiver.class), 0);
	}
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private static void setRepeatingAlarmKitkatAndAbove(Context context, int interval) {
		getAlarmManager(context).setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + (interval*1000), getIntervalAlarmIntent(context));
	}
	private static void setRepeatingAlarmDeprecated(Context context, int interval) {
		getAlarmManager(context).setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),interval*1000, getIntervalAlarmIntent(context));
	}
	public static void setRepeatingAlarm(Context context, int interval) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
			setRepeatingAlarmKitkatAndAbove(context, interval);
		else
			setRepeatingAlarmDeprecated(context, interval);
	}
	public static void setRepeatingAlarm(Context context, int interval, boolean skip_deprecated)
	{
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT)
			setRepeatingAlarmKitkatAndAbove(context, interval);
		else
			if (skip_deprecated == false)
				setRepeatingAlarmDeprecated(context, interval);
	}
	public static void removeRepeatingAlarm(Context context) {
		getAlarmManager(context).cancel(getIntervalAlarmIntent(context));
	}
}

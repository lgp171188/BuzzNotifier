package in.lguruprasad.buzznotifier;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyPreferencesManager {

    private MyPreferencesManager() {}

    private static SharedPreferences getSharedPreferences(Context context) {
    	SharedPreferences db = PreferenceManager.getDefaultSharedPreferences(context);
    	return db;
    }

    public static int getNotificationInterval(Context context) {
        return getSharedPreferences(context).getInt(context.getResources().getString(R.string.notification_interval_key), -1);
    }
    
    public static void setNotificationInterval(Context context, int value) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(context.getResources().getString(R.string.notification_interval_key), value);
        editor.commit();
    }
    public static void deleteNotificationInterval(Context context) {
    	final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
    	editor.remove(context.getResources().getString(R.string.notification_interval_key));
    	editor.commit();
    }
    public static boolean getCleanShutdownDone(Context context) {
    	return getSharedPreferences(context).getBoolean(context.getResources().getString(R.string.clean_shutdown_done_key), false);
    }
    
    public static void setCleanShutdownDone(Context context) {
    	final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(context.getResources().getString(R.string.clean_shutdown_done_key), true);
        editor.commit();
    }
    public static void setCleanShutdownNotDone(Context context) {
    	final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(context.getResources().getString(R.string.clean_shutdown_done_key), false);
        editor.commit();
    }
}
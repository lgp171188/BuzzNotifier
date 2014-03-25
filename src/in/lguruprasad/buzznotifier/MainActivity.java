package in.lguruprasad.buzznotifier;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		MyPreferencesManager.setCleanShutdownNotDone(getApplicationContext());
	}
	
	@Override
	protected void onStop() {
		MyPreferencesManager.setCleanShutdownDone(getApplicationContext());
		super.onStop();
	}
	public boolean onCreateOptionsMenu(Menu menu) {
        
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId()) {
	    case R.id.about:
	        Intent intent = new Intent(this, AboutActivity.class);
	        this.startActivity(intent);
	        break;
	    default:
	        return super.onOptionsItemSelected(item);
	    }

	    return true;
	}
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			Button start_stop_button = (Button)rootView.findViewById(R.id.start_stop_button);
			Context context = getActivity().getApplicationContext();
			int interval = MyPreferencesManager.getNotificationInterval(context);
			if (interval != MyNotificationManager.ALARM_NOT_SET)
			{
				EditText enter_interval_input = (EditText)rootView.findViewById(R.id.enter_interval_input);
				enter_interval_input.setText(Integer.toString(interval));
				enter_interval_input.setEnabled(false);
				start_stop_button.setText(getString(R.string.stop_notification_text));
			}
						
			start_stop_button.setOnClickListener(new Button.OnClickListener(){
				@Override
				public void onClick(View v){
					Button current_view = (Button)v;
					String current_text = (String)current_view.getText();
					String new_text = current_text;
					String start_string = getString(R.string.start_notification_text);
					String stop_string = getString(R.string.stop_notification_text);
					if (current_text == start_string) {
						RelativeLayout parent = (RelativeLayout)v.getParent();
						EditText enter_interval_input = (EditText) parent.findViewById(R.id.enter_interval_input);
						
						String enter_interval_value = enter_interval_input.getText().toString();
						if (enter_interval_value.length() == 0)
						{
							show_invalid_input_alert();

						}
						else {
							int notification_interval = Integer.parseInt(enter_interval_input.getText().toString());
							if (notification_interval == 0)
								show_invalid_input_alert();
							else {
								enter_interval_input.setEnabled(false);
								new_text = stop_string;
								set_repeating_alarm(notification_interval);
							}
						}
					}
					else {
						RelativeLayout parent = (RelativeLayout)v.getParent();
						EditText enter_interval_input = (EditText) parent.findViewById(R.id.enter_interval_input);
						enter_interval_input.setEnabled(true);
						new_text = start_string;
						remove_repeating_alarm();
				        
					}
					current_view.setText(new_text);
				}
				void show_invalid_input_alert() {
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setMessage(getString(R.string.invalid_input_msg));
					builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) { }
					       });
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				void set_repeating_alarm(int interval) {
					Context context = getActivity().getApplicationContext();
					MyNotificationManager.setRepeatingAlarm(context, interval);
					MyPreferencesManager.setNotificationInterval(context, interval);
				}
				void remove_repeating_alarm() {
					Context context = getActivity().getApplicationContext();
					MyNotificationManager.removeRepeatingAlarm(context);
					MyPreferencesManager.deleteNotificationInterval(context);
				}
			});
			return rootView;
		}
	}
}

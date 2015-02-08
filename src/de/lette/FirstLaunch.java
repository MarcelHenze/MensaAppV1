package de.lette;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FirstLaunch extends Activity implements OnClickListener {
	
	private static final String FIRST_LAUNCH = "first_launch";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_launch);
		
		Button button = (Button) findViewById(R.id.okay);
		button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putBoolean(FIRST_LAUNCH, false);
	    edit.commit();
		finish();
	}
}

package de.lette;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FirstLaunch extends Activity {

	private static final String FIRST_LAUNCH = "first_launch";

	/**
	 * Sucht den Button aus der xml und fügt ihm einen OnClickListener hinzu,
	 * der in den SharedPreferences den FIRST_LAUNCH auf false stellt, speichert
	 * und das Fenster schließt.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_launch);
		
//		TextView tv = (TextView) findViewById(R.id.readme);
//		tv.setMovementMethod(new ScrollingMovementMethod());
		Button button = (Button) findViewById(R.id.okay);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(FirstLaunch.this);
				SharedPreferences.Editor edit = prefs.edit();
				edit.putBoolean(FIRST_LAUNCH, false);
				edit.commit();
				finish();
			}
		});

	}

	/**
	 * Verhindert, dass man durch drücken des Back Buttons in die App gelangt.
	 */
	@Override
	public void onBackPressed() {
	}
}
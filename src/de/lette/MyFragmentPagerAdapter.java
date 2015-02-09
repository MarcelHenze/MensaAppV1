package de.lette;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	private String tabTitles[] = new String[] { "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag" };
	final int PAGE_COUNT = tabTitles.length;
	@SuppressWarnings("unused")
	private Context context;

	public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public int getCount() {
		return PAGE_COUNT;
	}

	@Override
	public Fragment getItem(int position) {
		Log.i("DATESTUFF", "position" +position);
		return PageFragment.newInstance(position + 1);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// Generate title based on item position
		return tabTitles[position];
	}
}
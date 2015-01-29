package de.lette;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import de.lette.mensaplan.ClientData;
import de.lette.mensaplan.Speise;
import de.lette.mensaplan.SpeiseArt;
import de.lette.mensaplan.Termin;

@SuppressWarnings("deprecation")
public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle, mTitle;
//	private static ArrayList<Speise> testSpeisen = new ArrayList<Speise>();

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;

	private String[] wochen, wochentage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		wochen = getResources().getStringArray(R.array.wochen);
		wochentage = getResources().getStringArray(R.array.wochentage);

//		testSpeisen.add(new Speise(0, "Hamburger", SpeiseArt.VOLLKOST, false,
//				"Fleisch", 9000, 9000, 9000, 9000));
//		testSpeisen.add(new Speise(1, "Caesar Salat", SpeiseArt.VORSPEISE,
//				false, "Salat", 100, 100, 100, 100));
//		testSpeisen.add(new Speise(2, "Suppe", SpeiseArt.VORSPEISE, false,
//				"Suppe", 200, 200, 200, 200));
//		testSpeisen.add(new Speise(3, "Staak", SpeiseArt.VOLLKOST, false,
//				"Fleisch", 9000, 9000, 9000, 9000));
//		testSpeisen.add(new Speise(4, "Bio Burger", SpeiseArt.VOLLKOST, true,
//				"Bio Fleisch", 0, 0, 0, 0));
//		testSpeisen.add(new Speise(5, "Eis im Eimer", SpeiseArt.DESSERT, false,
//				"Fleisch", 9000, 9000, 9000, 9000));
		
		mTitle = mDrawerTitle = getTitle();

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// oepns
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Add items to the ListView
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, wochen));
		// Set the OnItemClickListener so something happens when a
		// user clicks on an item.
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerList.setItemChecked(0, true);

		// Enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			return wochentage.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return wochentage[0];
			case 1:
				return wochentage[1];
			case 2:
				return wochentage[2];
			case 3:
				return wochentage[3];
			case 4:
				return wochentage[4];
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.speisenliste, container,
					false);

			TableLayout vorspeisen = (TableLayout) rootView
					.findViewById(R.id.vorspeisen);
			TableLayout hauptspeisen = (TableLayout) rootView
					.findViewById(R.id.hauptspeisen);
			TableLayout nachspeisen = (TableLayout) rootView
					.findViewById(R.id.nachspeisen);

			try {
				ClientData data = ConnectionHandler.getClientData();
				
				for (Termin t : data.getTermine()) {
					Speise speise = data.getSpeisen(t, data.getSpeisen());
//					Log.d("MensaPlan", speise.getName() +" am " +t.getDatum());
					TableRow tr = new TableRow(getActivity());
					TableRow.LayoutParams lp = new TableRow.LayoutParams(
							TableRow.LayoutParams.WRAP_CONTENT);
					tr.setLayoutParams(lp);
					ImageView iv = new ImageView(getActivity());
					TextView tv = new TextView(getActivity());
					tv.setText(speise.getName());
					tr.addView(iv);
					tr.addView(tv);
					if(speise.getArt() == SpeiseArt.VORSPEISE){
						iv.setImageResource(R.drawable.vorspeise);
						vorspeisen.addView(tr);
					}else if(speise.getArt() == SpeiseArt.VOLLKOST){
						iv.setImageResource(R.drawable.hauptspeise);
						hauptspeisen.addView(tr);
					} else if (speise.getArt() == SpeiseArt.DESSERT) {
						iv.setImageResource(R.drawable.nachspeise);
						nachspeisen.addView(tr);
					}
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			
			
//			for (Speise speise : testSpeisen) {
//				TableRow tr = new TableRow(getActivity());
//				TableRow.LayoutParams lp = new TableRow.LayoutParams(
//						TableRow.LayoutParams.WRAP_CONTENT);
//				tr.setLayoutParams(lp);
//
//				ImageView iv = new ImageView(getActivity());
//				TextView tv = new TextView(getActivity());
//				if (speise.getArt() == SpeiseArt.VORSPEISE) {
//					iv.setImageResource(R.drawable.vorspeise);
//					tv.setText("VorspeisenText");
//					tr.addView(iv);
//					tr.addView(tv);
//					vorspeisen.addView(tr);
//				} else if (speise.getArt() == SpeiseArt.VOLLKOST) {
//					iv.setImageResource(R.drawable.hauptspeise);
//					tv.setText("HauptspeisenText");
//					tr.addView(iv);
//					tr.addView(tv);
//					hauptspeisen.addView(tr);
//				} else if (speise.getArt() == SpeiseArt.DESSERT) {
//					iv.setImageResource(R.drawable.nachspeise);
//					tv.setText("NachspeisenText");
//					tr.addView(iv);
//					tr.addView(tv);
//					nachspeisen.addView(tr);
//				}
//			}
			return rootView;
		}
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {

		@Override
		public void onItemClick(
				@SuppressWarnings("rawtypes") AdapterView parent, View view,
				int position, long id) {

			// Highlight the selected item, update the title, and close the
			// drawer
			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);

			String text = "Wähle " + wochen[position] + " aus.";
			Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}
}

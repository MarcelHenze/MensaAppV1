package de.lette;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.ClientProtocolException;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVGParser;

import de.lette.mensaplan.server.ClientData;
import de.lette.mensaplan.server.Speise;
import de.lette.mensaplan.server.SpeiseArt;
import de.lette.mensaplan.server.Termin;

// In this case, the fragment displays simple text based on the page
public class PageFragment extends Fragment {
	public static final String ARG_PAGE = "ARG_PAGE";
	private int mPage;

	public static PageFragment newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		PageFragment fragment = new PageFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPage = getArguments().getInt(ARG_PAGE);
	}

	/**
	 * Fügt die Speisen hinzu und zeigt sie an..
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_page, container, false);
		try {
			ClientData data = ConnectionHandler.getClientData();
			Drawable vorspeise = SVGParser.getSVGFromResource(getResources(), R.raw.vorspeise).createPictureDrawable();
			Drawable hauptspeise = SVGParser.getSVGFromResource(getResources(), R.raw.hauptspeise).createPictureDrawable();
			Drawable nachspeise = SVGParser.getSVGFromResource(getResources(), R.raw.nachspeise).createPictureDrawable();
			LinearLayout vorspeisen = (LinearLayout) view.findViewById(R.id.vorspeisen);
			LinearLayout hauptspeisen = (LinearLayout) view.findViewById(R.id.hauptspeisen);
			LinearLayout nachspeisen = (LinearLayout) view.findViewById(R.id.nachspeisen);

			for(Entry<Date, Map<Speise, Termin>> entry : data.getSpeisenForDate().entrySet()) {
				// Date Stuff
				Calendar c = Calendar.getInstance(Locale.getDefault());
				c.setTime(entry.getKey());
				// int year = c.get(Calendar.YEAR);
				// int mounth = c.get(Calendar.MONTH);
//				int week = c.get(Calendar.WEEK_OF_MONTH);
				int day = c.get(Calendar.DAY_OF_WEEK);

				 if(day != mPage + 1) continue;

				// Vorspeisen
				for(Speise speise : data.getSpeisen(SpeiseArt.VORSPEISE, entry.getValue().keySet())) {
					View newView = inflater.inflate(R.layout.fragment_page_entry, container, false);
					ImageView iv = (ImageView) newView.findViewById(R.id.fragment_page_entry_imageView);
					iv.setImageDrawable(vorspeise);
					iv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
					TextView tv = (TextView) newView.findViewById(R.id.fragment_page_entry_textView);
					tv.setText(speise.getName() + ", " + speise.getKcal() + " kcal, " + speise.getEiweiß() + " Eiweiße, " + speise.getFett()
							+ " Fette, " + speise.getKohlenhydrate() + " Kohlenhydrate.\r\n");
					tv.append("Beachte: " + speise.getBeachte() + "\r\n");
					tv.append("Preis: " + entry.getValue().get(speise).getPreis() + "€");
					vorspeisen.addView(newView);
				}
				// Vollkost
				for(Speise speise : data.getSpeisen(SpeiseArt.VOLLKOST, entry.getValue().keySet())) {
					View newView = inflater.inflate(R.layout.fragment_page_entry, container, false);
					ImageView iv = (ImageView) newView.findViewById(R.id.fragment_page_entry_imageView);
					iv.setImageDrawable(hauptspeise);
					iv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
					TextView tv = (TextView) newView.findViewById(R.id.fragment_page_entry_textView);
					tv.setText(speise.getName() + ", " + speise.getKcal() + " kcal, " + speise.getEiweiß() + " Eiweiße, " + speise.getFett()
							+ " Fette, " + speise.getKohlenhydrate() + " Kohlenhydrate.\r\n");
					tv.append("Beachte: " + speise.getBeachte() + "\r\n");
					tv.append("Preis: " + entry.getValue().get(speise).getPreis() + "€");
					hauptspeisen.addView(newView);
				}
				// Dessert
				for(Speise speise : data.getSpeisen(SpeiseArt.DESSERT, entry.getValue().keySet())) {
					View newView = inflater.inflate(R.layout.fragment_page_entry, container, false);
					ImageView iv = (ImageView) newView.findViewById(R.id.fragment_page_entry_imageView);
					iv.setImageDrawable(nachspeise);
					iv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
					TextView tv = (TextView) newView.findViewById(R.id.fragment_page_entry_textView);
					tv.setText(speise.getName() + ", " + speise.getKcal() + " kcal, " + speise.getEiweiß() + " Eiweiße, " + speise.getFett()
							+ " Fette, " + speise.getKohlenhydrate() + " Kohlenhydrate.\r\n");
					tv.append("Beachte: " + speise.getBeachte() + "\r\n");
					tv.append("Preis: " + entry.getValue().get(speise).getPreis() + "€");
					nachspeisen.addView(newView);
				}
			}
		} catch(ClientProtocolException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(URISyntaxException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	public int getPageNumber() {
		return mPage;
	}
}
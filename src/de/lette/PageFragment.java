package de.lette;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import de.lette.mensaplan.server.ClientData;
import de.lette.mensaplan.server.Speise;
import de.lette.mensaplan.server.SpeiseArt;
import de.lette.mensaplan.server.Termin;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_page, container, false);
		TableLayout vorspeisen = (TableLayout) view.findViewById(R.id.vorspeisen);
		TableLayout hauptspeisen = (TableLayout) view.findViewById(R.id.hauptspeisen);
		TableLayout nachspeisen = (TableLayout) view.findViewById(R.id.nachspeisen);
		try {
			ClientData data = ConnectionHandler.getClientData();
			for(Termin t : data.getTermine()) {
				Speise speise = data.getSpeisen(t, data.getSpeisen());
				// Log.d("MensaPlan", speise.getName() +" am "
				// +t.getDatum());
				TableRow tr = new TableRow(getActivity());
				TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
				tr.setLayoutParams(lp);
				ImageView iv = new ImageView(getActivity());
				iv.setX(10);
				iv.setPadding(0, 0, 25, 0);
				TextView tv = new TextView(getActivity());
				tv.setTextColor(getResources().getColor(R.color.abc_primary_text_material_light));
				tv.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
				tv.setText(speise.getName() + ", " + speise.getKcal() + " kcal, " + speise.getEiweiß() + " Eiweiße, " + speise.getFett() + " Fette, "
						+ speise.getKohlenhydrate() + " Kohlenhydrate.\r\n");
				tv.append("Beachte: " + speise.getBeachte() + "\r\n");
				tv.append("Preis: " + t.getPreis() + "€");
				tr.addView(iv);
				tr.addView(tv);
				if(speise.getArt() == SpeiseArt.VORSPEISE) {
					iv.setImageResource(R.drawable.vorspeise);
					vorspeisen.addView(tr);
				} else if(speise.getArt() == SpeiseArt.VOLLKOST) {
					iv.setImageResource(R.drawable.hauptspeise);
					hauptspeisen.addView(tr);
				} else if(speise.getArt() == SpeiseArt.DESSERT) {
					iv.setImageResource(R.drawable.nachspeise);
					nachspeisen.addView(tr);
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
}
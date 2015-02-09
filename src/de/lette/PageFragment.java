package de.lette;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
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
	 * Fügt die Speisen hinzu und zeigt sie an.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_page, container, false);
		try {
			TableLayout vorspeisen = (TableLayout) view.findViewById(R.id.vorspeisen);
			TableLayout hauptspeisen = (TableLayout) view.findViewById(R.id.hauptspeisen);
			TableLayout nachspeisen = (TableLayout) view.findViewById(R.id.nachspeisen);
			ClientData data = ConnectionHandler.getClientData();
			
			SVG vorspeiseSVG = SVGParser.getSVGFromResource(getResources(), R.raw.vorspeise);
//			SVG hauptspeiseSVG = SVGParser.getSVGFromResource(getResources(), R.raw.hauptspeise);
//			SVG nachspeiseSVG = SVGParser.getSVGFromResource(getResources(), R.raw.nachspeise);
            Drawable vorspeise = vorspeiseSVG.createPictureDrawable();
//            Drawable hauptspeise = hauptspeiseSVG.createPictureDrawable();
//            Drawable nachspeise = nachspeiseSVG.createPictureDrawable();
            
            
			for(Termin t : data.getTermine()) {
				Speise speise = data.getSpeisen(t, data.getSpeisen());
				// Log.d("MensaPlan", speise.getName() +" am "
				// +t.getDatum());
	            
				TableRow tr = new TableRow(getActivity());
				TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
				tr.setLayoutParams(lp);
				
				ImageView icon = new ImageView(getActivity());
				icon.setPadding(0, 0, 25, 0);
				icon.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
				
				TextView tv = new TextView(getActivity());
				tv.setTextColor(getResources().getColor(R.color.abc_primary_text_material_light));
				tv.setTextSize(15f);
				tv.setPadding(0, 17, 0, 0);
				tv.setLayoutParams(new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
				tv.setText(speise.getName() + ", " + speise.getKcal() + " kcal, " + speise.getEiweiß() + " Eiweiße, " + speise.getFett() + " Fette, "
						+ speise.getKohlenhydrate() + " Kohlenhydrate.\r\n");
				tv.append("Beachte: " + speise.getBeachte() + "\r\n");
				tv.append("Preis: " + t.getPreis() + "€");
				tr.addView(icon);
				tr.addView(tv);
				if(speise.getArt() == SpeiseArt.VORSPEISE) {
					icon.setImageDrawable(vorspeise);
					vorspeisen.addView(tr);
				} else if(speise.getArt() == SpeiseArt.VOLLKOST) {
//					icon.setImageDrawable(hauptspeise);
					hauptspeisen.addView(tr);
				} else if(speise.getArt() == SpeiseArt.DESSERT) {
//					icon.setImageDrawable(nachspeise);
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
	
	public int getPageNumber() {
		return mPage;
	}
}
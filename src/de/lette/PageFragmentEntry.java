package de.lette;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PageFragmentEntry extends Fragment {
	private Drawable image;
	
	public PageFragmentEntry(Drawable image) {
		this.image = image;
	}
	
	public static PageFragmentEntry newInstance(Drawable image) {
		PageFragmentEntry fragment = new PageFragmentEntry(image);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_page_entry, container, false);
		return view;
	}
}

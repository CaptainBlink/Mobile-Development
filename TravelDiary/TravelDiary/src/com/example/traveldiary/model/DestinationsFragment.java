package com.example.traveldiary.model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class DestinationsFragment extends BaseFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.destinations_fragment_view,
				container, false);
		WebView webView = (WebView) view.findViewById(R.id.webViewDestinations);
		webView.loadUrl("http://www.besttimetogo.com/");

		return view;

	}
}

package com.example.traveldiary.model;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MapActivity extends Activity {
	String address;
	private static WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		webView = (WebView) findViewById(R.id.webView1);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			address = bundle.getString("address");
			Log.d(address, address);
			SetUpWebView();

			webView.loadDataWithBaseURL("", loadHtmlAndJavaScript(),
					"text/html", "UTF-8", "");

		}
	}

	private void SetUpWebView() {

		webView.setWebViewClient(new WebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_map, menu);
		return true;
	}

	private String loadHtmlAndJavaScript() {
		
		return "<!DOCTYPE html>"
				+ "<html>"
				+ "  <head>"
				+ "  <title>Map</title>"
				+ "<meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">"
				+ "<meta charset=\"utf-8\">"
				+ "<style>"
				+ "html, body, #map-canvas {"
				+ "        margin: 10;"
				+ "        padding: 10;"
				+ "        height: 100%;"
				+ "      }"
				+ "</style>"
				+ "<script src=\"https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false\"></script>"
				+ "<script>" + "var GoogleMap;" + "function initialize() {"
				+ "	var GeoDecoder = new google.maps.Geocoder();"
				+ "	GeoDecoder.geocode({'address' : '"
				+ address
				+ "'}, function(results, status){"
				+ "var mapOptions = {"
				+ "  zoom: 15,"
				+ "  center: new google.maps.LatLng(results[0].geometry.location.lat(), results[0].geometry.location.lng()),"
				+ "  mapTypeId: google.maps.MapTypeId.ROADMAP"
				+ "};"
				+ "var GoogleMap = new google.maps.Map(document.getElementById('map-canvas'),"
				+ "    mapOptions);"
				+ "var Latlng = new google.maps.LatLng(results[0].geometry.location.lat(), results[0].geometry.location.lng());"
				+ "var marker = new google.maps.Marker({"
				+ "    position: Latlng,"
				+ "    map: GoogleMap,"
				+ "    title:\""
				+ address
				+ "\""
				+ "});"
				+ "});"
				+ "}"
				+ "google.maps.event.addDomListener(window, 'load', initialize);"
				+ "    </script>"
				+ "  </head>"
				+ "  <body>"
				+ "    <div id=\"map-canvas\"></div>" + "  </body>" + "</html>";

	}
}

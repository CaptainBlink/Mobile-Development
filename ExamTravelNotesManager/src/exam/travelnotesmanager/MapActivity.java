package exam.travelnotesmanager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MapActivity extends Activity {
	private String address;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		webView = (WebView) findViewById(R.id.webView1);
		
		Bundle b = getIntent().getExtras();
		if (b != null) {
			address = b.getString("address");
			webView.setWebViewClient(new WebViewClient()); 
			webView.getSettings().setJavaScriptEnabled(true);
			 webView.getSettings().setBuiltInZoomControls(true);
			 webView.getSettings().setSupportZoom(true);
			webView.loadDataWithBaseURL("", getHtml(), "text/html", "UTF-8", "");
			//webView.loadUrl("https://developers.google.com/maps/documentation/javascript/examples/map-simple");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
	
	private String getHtml() {
		return "<!DOCTYPE html>"
		+"<html>"
		+"  <head>"
		  +"  <title>Simple Map</title>"
		    +"<meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">"
		    		+"<meta charset=\"utf-8\">"
		    		+"<style>"
		      +"html, body, #map-canvas {"
		+"        margin: 0;"
		+"        padding: 0;"
		+"        height: 100%;"
		+"      }"
		    +"</style>"
		    +"<script src=\"https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false\"></script>"
		    	+"<script>"
		+"var map;"
		+"function initialize() {"
		+"	var mygc = new google.maps.Geocoder();"
		+"	mygc.geocode({'address' : '" + address + "'}, function(results, status){"
			  +"var mapOptions = {"
			  +"  zoom: 12,"
			  +"  center: new google.maps.LatLng(results[0].geometry.location.lat(), results[0].geometry.location.lng()),"
			  +"  mapTypeId: google.maps.MapTypeId.ROADMAP"
			  +"};"
			  +"map = new google.maps.Map(document.getElementById('map-canvas'),"
			  +"    mapOptions);"
			  +"var myLatlng = new google.maps.LatLng(results[0].geometry.location.lat(), results[0].geometry.location.lng());"
			  +"var marker = new google.maps.Marker({"
			  +"    position: myLatlng,"
			  +"    map: map,"
			  +"    title:\"" + address + "\""
			  +"});"
			  +"});"
			  +"}"
		+"google.maps.event.addDomListener(window, 'load', initialize);"
		+"    </script>"
		+"  </head>"
		+"  <body>"
		+"    <div id=\"map-canvas\"></div>"
		+"  </body>"
		+"</html>";
	}
}

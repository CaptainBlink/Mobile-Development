package com.example.adrotator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	String link;
	DefaultHttpClient httpclient = new DefaultHttpClient();
	File file = new File("assets/text.txt");
	ArrayList<String> linkz = new ArrayList<String>();
	Spanned longText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Button button = (Button) findViewById(R.id.button1);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(getAssets().open("text.txt")));

					// do reading, usually loop until end of file reading
					String mLine = reader.readLine();
					while (mLine != null) {
						// process line
						mLine = reader.readLine();
						linkz.add(mLine);
					}

					reader.close();
					rotate();
				} catch (IOException e) {
					// log the exception
				}
			}

			private void rotate() throws MalformedURLException {
				final TextView textfield = (TextView) findViewById(R.id.textView1);

				final Handler handler = new Handler();
				handler.post(new Runnable() {

					Random r = new Random();
					int k = r.nextInt(linkz.size() - 1);

					public void run() {

						Spanned longText = Html.fromHtml(linkz.get(k)
								.toString());
						textfield.setText(Html
								.fromHtml(linkz.get(k).toString()));

						link = Html.toHtml(longText);
						k++;
						if (k < linkz.size() - 1) {
							// Here `this` refers to the anonymous `Runnable`
							handler.postDelayed(this, 1);
						}
						if (k == linkz.size() - 1) {
							k = r.nextInt(linkz.size() - 1);
							run();
						}

					}
				});
				textfield.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						openURL(link);
					}
				});
			}
		});

	}

	private String openURL(String currentLink) {

		String temp2 = currentLink.substring(currentLink.indexOf("href=") + 6,
				currentLink.lastIndexOf("tid=eaayoata") - 1);

		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(temp2));
		startActivity(browserIntent);
		return temp2;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}

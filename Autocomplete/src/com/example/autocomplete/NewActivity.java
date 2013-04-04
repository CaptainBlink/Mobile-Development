package com.example.autocomplete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class NewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_layout);
		TextView t = (TextView) (findViewById(R.id.textView1));
		t.setText(Singleton.getInstance().name);

		TextView t2 = (TextView) (findViewById(R.id.textView2));
		t2.setText(Singleton.getInstance().password);

		TextView t3 = (TextView) (findViewById(R.id.textView3));
		t3.setText(Singleton.getInstance().date);
		Button btn = (Button) findViewById(R.id.button1);

		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

			

				Intent intent = new Intent(NewActivity.this, MainActivity.class);
				startActivity(intent);
				setContentView(R.layout.activity_main);
			}
		});
	}
}

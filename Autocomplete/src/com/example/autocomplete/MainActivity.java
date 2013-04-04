package com.example.autocomplete;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String[] strings = {"asdas","asdasd","asdas"};
//        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,strings);
//        AutoCompleteTextView comp = (AutoCompleteTextView)findViewById(R.id.editText1);
//        comp.setAdapter(aa);
        ImageButton btn = (ImageButton)findViewById(R.id.imageButton1);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				EditText editName = (EditText) findViewById(R.id.editText3);
				EditText editPassword = (EditText)findViewById(R.id.editText4);
				EditText editDate = (EditText)findViewById(R.id.editText5);
				
				String name = editName.getText().toString();
				String pass = editPassword.getText().toString();
				String date= editDate.getText().toString();
				
				Singleton.getInstance().name=name;
				Singleton.getInstance().password=pass;
				Singleton.getInstance().date=date;
				
				Intent intent = new Intent(MainActivity.this, NewActivity.class);
				startActivity(intent);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}

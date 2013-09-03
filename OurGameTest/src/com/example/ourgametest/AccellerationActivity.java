package com.example.ourgametest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
 
public class AccellerationActivity extends Activity {
	private TextView result;
	private SensorManager sensorManager;
	private Sensor sensor;
	private int x, y, z;
	 ShapeDrawable mDrawable = new ShapeDrawable();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.game);
 
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
		AnimatedView animatedView = new AnimatedView(this);
        setContentView(animatedView);
//		result = (TextView) findViewById(R.id.result);
//		result.setText("No result yet");
	}
 
	private void refreshDisplay() {
		String output ="x is: %f / y is: %f / z is: %f", x, y, z;
	System.out.println(output);
		//	result.setText(output);
	}
 
	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(accelerationListener, sensor,
				SensorManager.SENSOR_DELAY_GAME);
	}
 
	@Override
	protected void onStop() {
		sensorManager.unregisterListener(accelerationListener);
		super.onStop();
	}
 
	private SensorEventListener accelerationListener = new SensorEventListener() {
		@Override
		public void onAccuracyChanged(Sensor sensor, int acc) {
		}
 
		@Override
		public void onSensorChanged(SensorEvent event) {
			x = (int) event.values[0];
			y = (int) event.values[1];
			z = (int) event.values[2];
			refreshDisplay();
		}
 
	};
	public class AnimatedView extends ImageView {

        static final int width = 350;
        static final int height = 250;

        public AnimatedView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub

            mDrawable = new ShapeDrawable(new OvalShape());
            mDrawable.getPaint().setColor(0xffffAC23);
            mDrawable.setBounds(x,y,z,x);

        }

        @Override
        protected void onDraw(Canvas canvas) {

            mDrawable.setBounds(x,y,z,x);
            mDrawable.draw(canvas);
            invalidate();
        }
    }


}
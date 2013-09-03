package com.example.ourgametest;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback,SensorEventListener  {

	public MainThread thread;
	public boolean Pause_game;
	private Background background;
	//private Ship ship;

	private Bonus coin;
	public float ShipSpeed; 
	public int ScreenWidth;
	public int Screenheigt;
	public Game game;
		
	public GamePanel(Context context, Game game,int ScreenWidth,int Screenheigt) {
		super(context);
		  
		getHolder().addCallback(this);
		this.game = game;
		thread = new MainThread(getHolder(),this);
		background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.main_bg), ScreenWidth);
		
		
	//	ship = new Ship(BitmapFactory.decodeResource(getResources(), R.drawable.player),650, ScreenWidth/2, ScreenWidth, Screenheigt);
//		coin = new Bonus(BitmapFactory.decodeResource(getResources(), R.drawable.bonus), -200,-200);
//		ArrayList<Bitmap> animation = new ArrayList<Bitmap>();
//		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom1));
//		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom2));
//		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom3));
//		animation.add(BitmapFactory.decodeResource(getResources(), R.drawable.boom4));
	//	ship.setBoomAnimation(animation);
		
	
		setFocusable(true);
		ShipSpeed = ScreenWidth/2.f;
		this.ScreenWidth = ScreenWidth;
		this.Screenheigt = Screenheigt;
	}
//
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		if (event.getAction()==MotionEvent.ACTION_DOWN){
//			System.out.println("DOWN");
//			
//			 
//		
//		}
//		if (event.getAction()==MotionEvent.ACTION_UP){
//			System.out.println("UP");
//			
//		}
//		
//		return true;
//	}
	
//	void Draw(Canvas canvas){
//		if (!Pause_game)
//			if (canvas!=null){
//				canvas.drawColor(Color.BLACK);
//				background.draw(canvas);
//			//	coin.draw(canvas);
//				ship.draw(canvas);
//				ship.VertSpeed=333;
//				
//			}
//	}
//	
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread.setRunning(true);
		thread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		while (retry) {
				try{
					thread.join();
					retry=false;
				} catch (InterruptedException e){
					
				}
			
		}
		
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

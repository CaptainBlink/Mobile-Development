package com.example.ourgametest;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.primitive.Rectangle;

//public class Ship {
//	public Rectangle sprite;
//	public static Ship instance;
//	Camera mCamera;
//	boolean moveable;
//
//	public static Ship getSharedInstance() {
//		if (instance == null)
//			instance = new Ship();
//		return instance;
//	}
//
//	Ship() {
//		sprite = new Rectangle(0, 0, 70, 30, BaseActivity.getSharedInstance()
//				.getVertexBufferObjectManager());
//
//		mCamera = BaseActivity.getSharedInstance().mCamera;
//		sprite.setPosition(mCamera.getWidth() / 2 - sprite.getWidth() / 2,
//				mCamera.getHeight() - sprite.getHeight() - 10);
//
//		moveable = true;
//		 instance = this;
//	}
//
//	public void moveShip(float accelerometerSpeedX) {
//		if (!moveable)
//			return;
//		// Log.v("Jimvaders",
//		// "Ship moveShip() accelerometerSpeedX = "+accellerometerSpeedX);
//		if (accelerometerSpeedX != 0) {
//
//			int lL = 0;
//			int rL = (int) (mCamera.getWidth() - (int) sprite.getWidth());
//
//			float newX;
//
//			// Calculate New X,Y Coordinates within Limits
//			if (sprite.getX() >= lL)
//				newX = sprite.getX() + accelerometerSpeedX;
//			else
//				newX = lL;
//			if (newX <= rL)
//				newX = sprite.getX() + accelerometerSpeedX;
//			else
//				newX = rL;
//
//			// Double Check That New X,Y Coordinates are within Limits
//			if (newX < lL)
//				newX = lL;
//			else if (newX > rL)
//				newX = rL;
//
//			sprite.setPosition(newX, sprite.getY());
//		}
//	}
//
//	// shoots bullets
//	
//
//	// resets the ship to the middle of the screen
//	public void restart() {
//		moveable = false;
//		Camera mCamera = BaseActivity.getSharedInstance().mCamera;
//		MoveXModifier mod = new MoveXModifier(0.2f, sprite.getX(),
//				mCamera.getWidth() / 2 - sprite.getWidth() / 2) {
//			@Override
//			protected void onModifierFinished(IEntity pItem) {
//				super.onModifierFinished(pItem);
//				moveable = true;
//			}
//		};
//		sprite.registerEntityModifier(mod);
//
//	}
//
//}


import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class Ship {

	private Bitmap bitmap;	
	private int x;			
	private int y,z;			
	private int Speed;
	private int inc;
	private int ScreenWidth;
	private int ScreenHeight;
	public ArrayList<Bitmap> Booms = null;
	boolean death;
	boolean up;
	float VertSpeed;
	
	float animTime=0;
	float totalAnimTime = 1;
	float numFrames;
	
	public Ship(Bitmap decodeResource, int x, int y,int z) {
		this.bitmap = decodeResource;
		this.x = x;
		this.y = y;
		this.z=z;
		Speed=1;
		inc=0;
		death=false;
	//	ScreenWidth =screenWidth;
	//	ScreenHeight =screenheight;
		VertSpeed = 0;
	}
	
	public void setBoomAnimation(ArrayList<Bitmap> animation){
		Booms = new ArrayList<Bitmap>(animation);
		numFrames = Booms.size();
	}
	
	public void draw(Canvas canvas){
		if (!death)
		{
			canvas.drawBitmap(bitmap, x - bitmap.getWidth()/2, y - bitmap.getHeight()/2, null);
		}
		else
		{
			int index = (int) (animTime/totalAnimTime*numFrames);
			if (index<numFrames)
				canvas.drawBitmap(Booms.get(index), x - bitmap.getWidth()/2, y - bitmap.getHeight()/2, null);
		}
		
	}
	
	public void update(float dt){
		if (death){
			animTime += dt;
		}
		else
		{
			VertSpeed+=ScreenHeight/2*dt;
			if (up)
				VertSpeed-=ScreenHeight*dt*2;
			y+=VertSpeed*dt;
			
			if (y - (bitmap.getHeight() / 2)>ScreenWidth)			
				y= 0- (bitmap.getHeight() / 2);
		}
	}
	
	
	
	public boolean bump (Point OTL, Point OTR, Point OBR, Point OBL){
		Point TL = new Point(), TR = new Point(), BL = new Point(), BR = new Point();
		
		ArrayList<Point> PointList = new ArrayList<Point>();
		PointList.add(OTL);
		PointList.add(OTR);
		PointList.add(OBR);
		PointList.add(OBL);
		
		getPoint(TL,TR,BL,BR);
		
		for (int i = 0; i<PointList.size(); i++){
			if (BR.x>=PointList.get(i).x)
				if (TL.x<=PointList.get(i).x)
					if(PointList.get(i).y>=TL.y)
						if(PointList.get(i).y<=BR.y)
				return true;
		}
		PointList.clear();
		PointList.add(TL);
		PointList.add(TR);
		PointList.add(BR);
		PointList.add(BL);
		for (int i = 0; i<PointList.size(); i++){
			if (OBR.x>=PointList.get(i).x)
				if (OTL.x<=PointList.get(i).x)
					if(PointList.get(i).y>=OTL.y)
						if(PointList.get(i).y<=OBR.y)
				return true;
		}
		
		return false;
	}

	private void getPoint(Point TL, Point TR, Point BL, Point BR) {
		TL.x = x-bitmap.getWidth() / 2;
		TL.y = y - bitmap.getHeight() / 2;
		
		TR.x = x+bitmap.getWidth() / 2;
		TR.y = y - bitmap.getHeight() / 2;
		
		BL.x = x-bitmap.getWidth() / 2;
		BL.y = y + bitmap.getHeight() / 2;
		
		BR.x = x+bitmap.getWidth() / 2;
		BR.y = y+bitmap.getHeight() / 2;
		
	}

}

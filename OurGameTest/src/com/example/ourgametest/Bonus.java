package com.example.ourgametest;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class Bonus {
	private Bitmap bitmap;	
	private int x;			
	private int y;			
	

	public Bonus(Bitmap decodeResource, int x, int y) {
		this.bitmap = decodeResource;
		this.x = x;
		this.y = y;
	}
	
	
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	
	public ArrayList<Point> GetArray() {
		Point TL = new Point(), TR = new Point(), BL = new Point(), BR = new Point();
		TL.x = x-bitmap.getWidth() / 2;
		TL.y = y - bitmap.getHeight() / 2;
		
		TR.x = x+bitmap.getWidth() / 2;
		TR.y = y - bitmap.getHeight() / 2;
		
		BL.x = x-bitmap.getWidth() / 2;
		BL.y = y + bitmap.getHeight() / 2;
		
		BR.x = x+bitmap.getWidth() / 2;
		BR.y = y+bitmap.getHeight() / 2;
		
		ArrayList<Point> temp = new ArrayList<Point>();
		temp.add(TL);
		temp.add(TR);
		temp.add(BR);
		temp.add(BL);
		return temp;
	}
	
	public void draw(Canvas canvas){
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);	
	}
	
	

	public void setX(int x) {
		this.x=x;
		
	}

	public void setY(int y) {
	this.y=y;
		
	}
}

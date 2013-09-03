package com.whatToBuy;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

	private int id;
	private String name, unit, shop;
	private double price;

	public Product(int id, String name, String unit, String shop, double price) {

		this.id = id;
		this.name = name;
		this.unit = unit;
		this.shop = shop;
		this.price = price;

	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getShop() {
		return shop;
	}

	public void setShop(String shop) {
		this.shop = shop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public int describeContents() {
		
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(name);
		out.writeString(shop);
		out.writeString(unit);
		out.writeDouble(price);
		out.writeInt(id);

	}

	public Product(Parcel in) {
		readFromParcel(in);

	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Product createFromParcel(Parcel in) {
			return new Product(in);
		}

		public Product[] newArray(int size) {
			return new Product[size];
		}

	};

	public void readFromParcel(Parcel in) {
		name = in.readString();
		shop = in.readString();
		unit = in.readString();
		price = in.readDouble();
		id = in.readInt();

	}

	@Override
	public String toString() {
		
		return name + price + shop + unit + id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

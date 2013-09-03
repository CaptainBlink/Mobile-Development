package com.example.traveldiary.model;

import java.io.Serializable;

import android.database.Cursor;

// Travel Note Object
public class TravelNote  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8614987330600463729L;
	/**
	 * 
	 */
	
	private int id;
	private String title;
	private String address;
	private String description;
	private String date;
		private String again;
	public TravelNote(String title, String address,
			String description, String date,String again) {
		super();
	
		this.title = title;
		this.address = address;
		this.description = description;
		this.date = date;
			this.again = again;
			}

	
	public TravelNote()
	{}
	public TravelNote(Cursor c) {
		super();
		this.id = c.getInt(c.getColumnIndex("_id"));
		this.title = c.getString(c.getColumnIndex("title"));
		this.address = c.getString(c.getColumnIndex("address"));
		this.description = c.getString(c.getColumnIndex("description"));
		this.date = c.getString(c.getColumnIndex("date"));
			this.again = c.getString(c.getColumnIndex("again"));
	}


	public String getAgain() {
		return again;
	}


	public void setAgain(String again) {
		this.again = again;
	}


	public int getId() {
		return id;
	}
	public void setId(int id2) {
		this.id = id2;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "TravelNote [id=" + id + ", title=" + title + ", address="
				+ address + ", description=" + description + ", date=" + date
				+ " , again=" + again + "]";
	}


	

}

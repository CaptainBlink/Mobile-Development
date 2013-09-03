package exam.travelnotesmanager;

import java.sql.Date;

public class Note {
	private int id;
	private String title;
	private String address;
	private String description;
	private Date visitDate;
	private boolean visitAgain;

	public Note(int id, String title, String address, String description,
			Date visitDate, boolean visitAgain) {
		super();
		this.id = id;
		this.title = title;
		this.address = address;
		this.description = description;
		this.visitDate = visitDate;
		this.visitAgain = visitAgain;
	}
	
	public String toString() {
		return title + " " + visitDate.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public boolean isVisitAgain() {
		return visitAgain;
	}

	public void setVisitAgain(boolean visitAgain) {
		this.visitAgain = visitAgain;
	}
}

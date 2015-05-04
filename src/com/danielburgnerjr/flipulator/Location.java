package com.danielburgnerjr.flipulator;

import java.io.Serializable;

public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String strAddress;		// address
	private String strCity;			// city
	private String strState;		// state
	private String strZIPCode;		// ZIP Code
	private int nSquareFootage;		// square footage
	private int nBedrooms;			// number of bedrooms
	private int nBathrooms;			// number of bathrooms

	public String getAddress() {
		return strAddress;
	}

	public void setAddress(String strAdd) {
		this.strAddress = strAdd;
	}

	public String getCity() {
		return strCity;
	}

	public void setCity(String strC) {
		this.strCity = strC;
	}

	public String getState() {
		return strState;
	}

	public void setState(String strS) {
		this.strState = strS;
	}

	public String getZIPCode() {
		return strZIPCode;
	}

	public void setZIPCode(String strZ) {
		this.strZIPCode = strZ;
	}
	
	public int getSquareFootage() {
		return nSquareFootage;
	}

	public void setSquareFootage(int nSF) {
		this.nSquareFootage = nSF;
	}

	public int getBedrooms() {
		return nBedrooms;
	}

	public void setBedrooms(int nBR) {
		this.nBedrooms = nBR;
	}

	public int getBathrooms() {
		return nBathrooms;
	}

	public void setBathrooms(int nBA) {
		this.nBathrooms = nBA;
	}
	
    @Override
    public String toString() {
        return "Location\nAddress: " + strAddress + "\nCity: " + strCity + "\nState: " + strState + "\nZIP Code: " + strZIPCode +
        	   "\nSquare Footage: " + nSquareFootage + "\nBedrooms: " + nBedrooms + "\nBathrooms: " + nBathrooms;
    }  
}

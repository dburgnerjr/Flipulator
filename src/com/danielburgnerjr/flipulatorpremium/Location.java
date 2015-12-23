package com.danielburgnerjr.flipulatorpremium;

import java.io.Serializable;

public class Location implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String strAddress;		// address
	private String strCity;			// city
	private String strState;		// state
	private String strZIPCode;		// ZIP Code
	private int nSquareFootage;		// square footage
	private int nBedrooms;			// number of bedrooms
	private double dBathrooms;		// number of bathrooms

	//**********************************************************************	
	// Constructor function (default)
	//	Inputs:	none
	//	Output:	none
	//**********************************************************************	
	public Location() {
		strAddress = "";
		strCity = "";
		strState = "";
		strZIPCode = "";
		nSquareFootage = 0;
		nBedrooms = 0;
		dBathrooms = 0;
	}

	//**********************************************************************	
	// Constructor function 
	//	Inputs:	strA (String) - representing street address
	//          strC (String) - representing city
	//			strS (String) - representing state
	//          strZ (String) - representing ZIP code
	//			nSF  (int)    - representing square footage
	//          nBR  (int)    - representing bedrooms
	//          dBA  (int)    - representing bathrooms
	//	Output:	none
	//**********************************************************************	
	public Location(String strA, String strC, String strS, String strZ, int nSF, int nBR, double dBA) {
		strAddress = strA;
		strCity = strC;
		strState = strS;
		strZIPCode = strZ;
		nSquareFootage = nSF;
		nBedrooms = nBR;
		dBathrooms = dBA;
	}

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

	public double getBathrooms() {
		return dBathrooms;
	}

	public void setBathrooms(double dBA) {
		this.dBathrooms = dBA;
	}
	
    @Override
    public String toString() {
        return "Location\nAddress: " + strAddress + "\nCity: " + strCity + "\nState: " + strState + "\nZIP Code: " + strZIPCode +
        	   "\nSquare Footage: " + nSquareFootage + "\nBedrooms: " + nBedrooms + "\nBathrooms: " + dBathrooms;
    }  
}

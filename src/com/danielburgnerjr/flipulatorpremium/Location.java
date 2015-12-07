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
	private int nBathrooms;			// number of bathrooms

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
		nBathrooms = 0;
	}

	//**********************************************************************	
	// Constructor function 
	//	Inputs:	strA (String) - representing street address
	//          strC (String) - representing city
	//			strS (String) - representing state
	//          strZ (String) - representing ZIP code
	//			nSF  (int)    - representing square footage
	//          nBR  (int)    - representing bedrooms
	//          nBA  (int)    - representing bathrooms
	//	Output:	none
	//**********************************************************************	
	public Location(String strA, String strC, String strS, String strZ, int nSF, int nBR, int nBA) {
		strAddress = strA;
		strCity = strC;
		strState = strS;
		strZIPCode = strZ;
		nSquareFootage = nSF;
		nBedrooms = nBR;
		nBathrooms = nBA;
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

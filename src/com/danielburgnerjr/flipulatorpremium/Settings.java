package com.danielburgnerjr.flipulatorpremium;

import java.io.Serializable;

/* 
*************************************************************************
* 
* Program Name: Settings.java 
* 
* Description: This program is a class of settings
* 
* Date: October 4, 2015
* 
* Author: Daniel Burgner
* 
*************************************************************************
*/



public class Settings implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int nRehab;		// rehab class
	private int nFinance;	// finance class
	
	//**********************************************************************	
	// Constructor function (default)
	//	Inputs:	none
	//	Output:	none
	//**********************************************************************	
	public Settings() {
		nRehab = 0;
		nFinance = 0;
	}

	//**********************************************************************	
	// Constructor function 
	//	Inputs:	nR (int) - representing rehab class
	//          nF (int) - representing finance class
	//	Output:	none
	//**********************************************************************	
	public Settings(int nR, int nF) {
		nRehab = nR;   	// sets rehab class
		nFinance = nF;	// sets finance class
	}

   //**********************************************************************	
	// toString - displays the object's attributes
	//	Inputs:	none
	//	Output:	String (object representation)
	//**********************************************************************	
	public String toString() {
		String strRehab = "";
		String strFinance = "";
		switch (nRehab) {
			case 0:
					strRehab = "Flat Rate";
					break;
					
			case 1:
					strRehab = "Rehab Type";
					break;
		}

		switch (nFinance) {
			case 0:
					strFinance = "Original";
					break;
					
			case 1:
					strFinance = "Owner Carry";
					break;
					
			case 2:
					strFinance = "Finance Rehab";
					break;

		}
		return "Rehab Class: " + strRehab + " Finance Class:  " + strFinance;
	}
}
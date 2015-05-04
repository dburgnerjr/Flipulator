package com.danielburgnerjr.flipulator;

/* 
*************************************************************************
* 
* Program Name: RehabFlatRate.java 
* 
* Description: This program is a class of rehab model by flat rate
* 
* Date: December 30, 2014
* 
* Author: Daniel Burgner
* 
*************************************************************************
*/


import java.io.Serializable;

public class RehabFlatRate extends Rehab implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	//**********************************************************************	
	// Constructor function (default)
	//	Inputs:	none
	//	Output:	none
	//**********************************************************************	
	public RehabFlatRate() {
	}

	//**********************************************************************	
	// Constructor function 
	//	Inputs:	strT (String) - representing title
	//          dB (double) - representing price
	//	Output:	none
	//**********************************************************************	
	public RehabFlatRate(double dB) {
		super(dB);   	// sets budget
	}

   //**********************************************************************	
	// toString - displays the object's attributes
	//	Inputs:	none
	//	Output:	String (object representation)
	//**********************************************************************	
	public String toString() {
		return "Rehab Flat Rate Budget: $" + String.format("%.2f", dBudget);
	}

}
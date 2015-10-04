package com.danielburgnerjr.flipulatorpremium;

/* 
*************************************************************************
* 
* Program Name: Rehab.java 
* 
* Description: This program is an abstract class of rehab
* 
* Date: December 30, 2014
* 
* Author: Daniel Burgner
* 
*************************************************************************
*/


import java.io.Serializable;

public abstract class Rehab implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected double dBudget;		// budget
	
	//**********************************************************************	
	// Constructor function (default)
	//	Inputs:	none
	//	Output:	none
	//**********************************************************************	
	public Rehab() {
	}

	//**********************************************************************	
	// Constructor function 
	//	Inputs:	strT (String) - representing title
	//          dB (double) - representing price
	//	Output:	none
	//**********************************************************************	
	public Rehab(double dB) {
		dBudget = dB;   	// sets budget
	}

	//**********************************************************************	
	//  getBudget - gets Budget
    //	Inputs:	none
	//	Output:	double (Budget)
	//**********************************************************************	
	public double getBudget() {
		return dBudget;		// returns budget
	}

   //**********************************************************************	
	// toString - displays the object's attributes
	//	Inputs:	none
	//	Output:	String (object representation)
	//**********************************************************************	
	public abstract String toString();

}
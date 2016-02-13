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
	
	protected double dBudget;			// budget
	protected String strBudgetItems;	// list of items that need repair

	
	//**********************************************************************	
	// Constructor function (default)
	//	Inputs:	none
	//	Output:	none
	//**********************************************************************	
	public Rehab() {
	}

	//**********************************************************************	
	// Constructor function 
	//	Inputs:	dB (double) - representing budget
	//          strT (String) - representing budget items
	//	Output:	none
	//**********************************************************************	
	public Rehab(double dB, String strBI) {
		dBudget = dB;   		// sets budget
		strBudgetItems = strBI; // sets budget items
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
	//  getBudgetItems - gets Budget items
    //	Inputs:	none
	//	Output:	String (budget items)
	//**********************************************************************	
	public String getBudgetItems() {
		return strBudgetItems;	// returns budget items
	}

    //**********************************************************************	
	// toString - displays the object's attributes
	//	Inputs:	none
	//	Output:	String (object representation)
	//**********************************************************************	
	public abstract String toString();

}
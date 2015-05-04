package com.danielburgnerjr.flipulator;

/* 
*************************************************************************
* 
* Program Name: RehabType.java 
* 
* Description: This program is a class of rehab model by rehab type
* 
* Date: December 30, 2014
* 
* Author: Daniel Burgner
* 
*************************************************************************
*/


import java.io.Serializable;

public class RehabType extends Rehab implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String strType;		// rehab type
	private int nSqFoot;		// square footage
	
	//**********************************************************************	
	// Constructor function (default)
	//	Inputs:	none
	//	Output:	none
	//**********************************************************************	
	public RehabType() {
	}

	//**********************************************************************	
	// Constructor function 
	//	Inputs:	nSF (int) - representing square footage
	//			strT (String) - representing type
	//	Output:	none
	//**********************************************************************	
	public RehabType(int nSF, String strT) {
		nSqFoot = nSF;	// stores square footage
		strType = strT; // stores rehab type
		
		// determines budget based on type and square footage
		if (strType.equals("Low")) {
			dBudget = (15 * nSqFoot);
		} else if (strType.equals("Medium")) {
			if (nSqFoot < 1500)
				dBudget = (25 * nSqFoot);
			else	
				dBudget = (20 * nSqFoot);
		} else if (strType.equals("High")) {
			dBudget = (30 * nSqFoot);
		} else if (strType.equals("Super-High")) {
			dBudget = (40 * nSqFoot);
		} else {				
			dBudget = (125 * nSqFoot);
		}
		
	}

   //**********************************************************************	
	// toString - displays the object's attributes
	//	Inputs:	none
	//	Output:	String (object representation)
	//**********************************************************************	
	public String toString() {
		return "Rehab Type: " + strType + "\nBudget based on " + nSqFoot + " square feet is: $" + String.format("%.2f", dBudget);
	}

}
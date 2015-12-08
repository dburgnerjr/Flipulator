package com.danielburgnerjr.flipulatorpremium;

import java.io.Serializable;

public class Reserves implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private double dMortgage;		// mortgage
	private double dInsurance;		// insurance
	private double dTaxes;			// taxes
	private double dWater;			// water
	private double dGas;			// gas
	private double dElectric;		// electric
	private double dTotalExpenses;	// total expenses

	//**********************************************************************	
	// Constructor function (default)
	//	Inputs:	none
	//	Output:	none
	//**********************************************************************	
	public Reserves() {
		dMortgage = 0;
		dInsurance = 0;
		dTaxes = 0;
		dWater = 0;
		dGas = 0;
		dElectric = 0;
		dTotalExpenses = 0;
	}

	//**********************************************************************	
	// Constructor function 
	//	Inputs:	dM (double) - representing mortgage
	//          dI (double) - representing insurance
	//			dT (double) - representing taxes
	//          dW (double) - representing water
	//			dG (double) - representing gas
	//			dE (double) - representing electric
	//			dTE (double) - representing total expenses
	//	Output:	none
	//**********************************************************************	
	public Reserves(double dM, double dI, double dT, double dW, double dG, double dE, double dTE) {
		dMortgage = dM;
		dInsurance = dI;
		dTaxes = dT;
		dWater = dW;
		dGas = dG;
		dElectric = dE;
		dTotalExpenses = dTE;
	}

	public double getMortgage() {
		return dMortgage;
	}

	public void setMortgage(double dM) {
		this.dMortgage = dM;
	}

	public double getInsurance() {
		return dInsurance;
	}

	public void setInsurance(double dIn) {
		this.dInsurance = dIn;
	}

	public double getTaxes() {
		return dTaxes;
	}

	public void setTaxes(double dT) {
		this.dTaxes = dT;
	}
	
	public double getWater() {
		return dWater;
	}

	public void setWater(double dW) {
		this.dWater = dW;
	}

	public double getGas() {
		return dGas;
	}

	public void setGas(double dG) {
		this.dGas = dG;
	}

	public double getElectric() {
		return dElectric;
	}

	public void setElectric(double dEl) {
		this.dElectric = dEl;
	}

	public double getTotalExpenses() {
		return dTotalExpenses;
	}

	public void setTotalExpenses() {
		this.dTotalExpenses = this.dMortgage + this.dInsurance + this.dTaxes + this.dWater + this.dGas + this.dElectric;
	}
	
    @Override
    public String toString() {
        return "Reserves (6 mos)\nMortgage: $" + String.format("%.2f", dMortgage) + "\nInsurance: $" + String.format("%.2f", dInsurance) + 
        		"\nTaxes: $" + String.format("%.2f", dTaxes) + "\nWater: $" + String.format("%.2f", dWater) + "\nGas: $" + 
        		String.format("%.2f", dGas) + "\nElectric: $" + String.format("%.2f", dElectric) +  "\nTotal Expenses: $" + 
        		String.format("%.2f", dTotalExpenses);
    }  
}

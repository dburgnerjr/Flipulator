package com.danielburgnerjr.flipulator;

import java.io.Serializable;

public class ClosExpPropMktInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private double dRealEstComm;	// real estate commission
	private double dBuyClosCost;	// buyer's closing costs
	private double dSellClosCost;	// seller's closing costs
	private double dFMVARV;			// FMV/ARV
	private double dComparables;	// comparables
	private double dSellingPrice;	// selling price

	public double getRealEstComm() {
		return dRealEstComm;
	}

	public void setRealEstComm(double dREC) {
		this.dRealEstComm = dREC;
	}

	public double getBuyClosCost() {
		return dBuyClosCost;
	}

	public void setBuyClosCost(double dBCC) {
		this.dBuyClosCost = dBCC;
	}

	public double getSellClosCost() {
		return dSellClosCost;
	}

	public void setSellClosCost(double dSCC) {
		this.dSellClosCost = dSCC;
	}
	
	public double getFMVARV() {
		return dFMVARV;
	}

	public void setFMVARV(double dF) {
		this.dFMVARV = dF;
	}

	public double getComparables() {
		return dComparables;
	}

	public void setComparables(double dC) {
		this.dComparables = dC;
	}

	public double getSellingPrice() {
		return dSellingPrice;
	}

	public void setSellingPrice(double dSP) {
		this.dSellingPrice = dSP;
	}
	
    @Override
    public String toString() {
        return "Closing Expense/Market Info\nReal Estate Comm Perc: " + String.format("%.2f", dRealEstComm) + "%\nBuyer's Closing Cost %: " + 
        		String.format("%.2f", dBuyClosCost) + "%\nSeller's Closing Cost %: " + String.format("%.2f", dSellClosCost) + "%\nFMV/ARV: $" + 
        		String.format("%.2f", dFMVARV) + "\nComparables: $" + String.format("%.2f", dComparables) + "\nSellingPrice: $" + 
        		String.format("%.2f", dSellingPrice);
    }  
}

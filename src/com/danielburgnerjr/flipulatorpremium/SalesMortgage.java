package com.danielburgnerjr.flipulatorpremium;

import java.io.Serializable;

public class SalesMortgage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private double dSalesPrice;		// sales price
	private double dPercentDown;	// percentage down
	private double dOfferBid;		// offer/bid price
	private double dDownPayment;	// down payment
	private double dLoanAmount;		// loan amount
	private double dInterestRate;	// interest rate
	private int nTerm;				// term
	private double dMonthlyPmt;		// monthly payment

	//**********************************************************************	
	// Constructor function (default)
	//	Inputs:	none
	//	Output:	none
	//**********************************************************************	
	public SalesMortgage() {
		dSalesPrice = 0;
		dPercentDown = 0;
		dOfferBid = 0;
		dDownPayment = 0;
		dLoanAmount = 0;
		dInterestRate = 0;
		nTerm = 0;
		dMonthlyPmt = 0;
	}

	//**********************************************************************	
	// Constructor function 
	//	Inputs:	dSP (double) - representing sales price
	//          dPD (double) - representing percent down
	//			dOB (double) - representing offer bid
	//			dDP (double) - representing down payment
	//			dLA (double) - representing loan amount
	//          dIR (double) - representing interest rate
	//			nT  (int)    - representing term
	//			dMP (double) - representing monthly payment
	//	Output:	none
	//**********************************************************************	
	public SalesMortgage(double dSP, double dPD, double dOB, double dDP, double dLA, double dIR, int nT, double dMP) {
		dSalesPrice = dSP;
		dPercentDown = dPD;
		dOfferBid = dOB;
		dDownPayment = dDP;
		dLoanAmount = dLA;
		dInterestRate = dIR;
		nTerm = nT;
		dMonthlyPmt = dMP;
	}

	public double getSalesPrice() {
		return dSalesPrice;
	}

	public void setSalesPrice(double dSP) {
		this.dSalesPrice = dSP;
	}

	public double getPercentDown() {
		return dPercentDown;
	}

	public void setPercentDown(double dPD) {
		this.dPercentDown = dPD;
	}

	public double getOfferBid() {
		return dOfferBid;
	}

	public void setOfferBid(double dOB) {
		this.dOfferBid = dOB;
	}
	
	public double getDownPayment() {
		return dDownPayment;
	}

	public void setDownPayment(double dPD, double dOB) {
		this.dDownPayment = ((dPD/100) * dOB);
	}

	public double getLoanAmount() {
		return dLoanAmount;
	}

	public void setLoanAmount() {
		this.dLoanAmount = (this.dOfferBid - this.dDownPayment);
	}

	public double getInterestRate() {
		return dInterestRate;
	}

	public void setInterestRate(double dIR) {
		this.dInterestRate = dIR;
	}

	public int getTerm() {
		return nTerm;
	}

	public void setTerm(int nT) {
		this.nTerm = nT;
	}

	public double getMonthlyPmt() {
		return dMonthlyPmt;
	}

	public void setMonthlyPmt() {
		this.dMonthlyPmt = (dPercentDown == 100) ? 0 : (this.dLoanAmount*((this.dInterestRate/12)/100));
	}
	
	public void setMonthlyPmt(double dRehab) {
		this.dMonthlyPmt = (dPercentDown == 100) ? 0 : ((this.dLoanAmount+dRehab)*((this.dInterestRate/12)/100));
	}

	@Override
    public String toString() {
        return "Sales/Mortgage Info\nSales Price: $" + String.format("%.2f", dSalesPrice) + "\nPercent Down: " + String.format("%.2f", dPercentDown) + 
        		"%\nOffer Bid: $" + String.format("%.2f", dOfferBid) + "\nDown Payment: $" + String.format("%.2f", dDownPayment) + "\nLoan Amount: $" + 
        		String.format("%.2f", dLoanAmount) + "\nInterest Rate: " + String.format("%.2f", dInterestRate) + "%\nTerm (months): " + nTerm + 
        		"\nMonthly Payment: $" + String.format("%.2f", dMonthlyPmt);
    }  
}

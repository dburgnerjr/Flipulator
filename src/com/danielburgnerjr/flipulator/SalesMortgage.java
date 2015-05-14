package com.danielburgnerjr.flipulator;

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
	
    @Override
    public String toString() {
        return "Sales/Mortgage Info\nSales Price: $" + String.format("%.2f", dSalesPrice) + "\nPercent Down: " + String.format("%.2f", dPercentDown) + 
        		"%\nOffer Bid: $" + String.format("%.2f", dOfferBid) + "\nDown Payment: $" + String.format("%.2f", dDownPayment) + "\nLoan Amount: $" + 
        		String.format("%.2f", dLoanAmount) + "\nInterest Rate: " + String.format("%.2f", dInterestRate) + "%\nTerm (months): " + nTerm + 
        		"\nMonthly Payment: $" + String.format("%.2f", dMonthlyPmt);
    }  
}

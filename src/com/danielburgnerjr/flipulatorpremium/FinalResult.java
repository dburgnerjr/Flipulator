package com.danielburgnerjr.flipulatorpremium;

import java.io.Serializable;

public class FinalResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private double dRECost;			// real estate commission ($)
	private double dBCCost;			// buyer's closing costs ($)
	private double dSCCost;			// seller's closing costs ($)
	private double dTotalCost;		// total costs
	private double dOOPExp;			// out of pocket expenses
	private double dGrossProfit;	// gross profit
	private double dCapGains;		// capital gains
	private double dNetProfit;		// net profit
	private double dROI;			// return on investment
	private double dCashOnCash;		// cash on cash return

	public double getRECost() {
		return dRECost;
	}

	public void setRECost(double dSP, double dCP) {
		this.dRECost = dSP * (dCP/100);
	}

	public double getBCCost() {
		return dBCCost;
	}

	public void setBCCost(double dSP, double dCP) {
		this.dBCCost = dSP * (dCP/100);
	}

	public double getSCCost() {
		return dSCCost;
	}

	public void setSCCost(double dSP, double dCP) {
		this.dSCCost = dSP * (dCP/100);
	}
	
	public double getTotalCost() {
		return dTotalCost;
	}

	public void setTotalCost(double dO, double dRH, double dRS) {
		this.dTotalCost = dO + dRH + dRS + this.dRECost + this.dBCCost;
	}

	public double getOOPExp() {
		return dOOPExp;
	}

	public void setOOPExp(double dDP, double dRS, double dRH) {
		this.dOOPExp = dDP + dRH + dRS + this.dBCCost;
	}

	public double getGrossProfit() {
		return dGrossProfit;
	}

	public void setGrossProfit(double dSP) {
		this.dGrossProfit = dSP - this.dTotalCost;
	}

	public double getCapGains() {
		return dCapGains;
	}

	public void setCapGains() {
		this.dCapGains = (this.dGrossProfit * .3);
	}

	public double getNetProfit() {
		return dNetProfit;
	}

	public void setNetProfit() {
		this.dNetProfit = (this.dGrossProfit - this.dCapGains);
	}

	public double getROI() {
		return dROI;
	}

	public void setROI(double dSP) {
		this.dROI = (this.dNetProfit / dSP) * 100;
	}

	public double getCashOnCash() {
		return dCashOnCash;
	}

	public void setCashOnCash() {
		this.dCashOnCash = (this.dNetProfit / this.dOOPExp) * 100;
	}

    @Override
    public String toString() {
        return "Final Result\nReal Estate Comm: $" + String.format("%.2f", dRECost) + "\nBuyer's Closing Cost: $" + 
        		String.format("%.2f", dBCCost) + "\nSeller's Closing Cost: $" + String.format("%.2f", dSCCost) + "\nTotal Cost: $" + 
        		String.format("%.2f", dTotalCost) + "\nOut Of Pocket Exp: $" + String.format("%.2f", dOOPExp) + "\nGross Profit: $" + 
        		String.format("%.2f", dGrossProfit) + "\nCapital Gains: $" + String.format("%.2f", dCapGains) + "\nNet Profit: $" +
        		String.format("%.2f", dNetProfit) + "\nROI: " + String.format("%.2f", dROI) + "%\nCash on Cash Return: " + 
        		String.format("%.2f", dCashOnCash) + "%";
    }  
}

package com.danielburgnerjr.flipulatorpremium;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
 
public class FinalResultActivity extends Activity {
 
	private Settings setS;
	private Location locL;
	private SalesMortgage smSM;
	private Rehab rR;
	private Reserves rsR;
	private ClosExpPropMktInfo cemC;
	private FinalResult frF;
	private Spinner spnTimeFrame;
	private WritableCellFormat timesBold;
	private WritableCellFormat times;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalresult);

		ArrayAdapter<CharSequence> aradAdapter = ArrayAdapter.createFromResource(
				  this, R.array.time_frame, android.R.layout.simple_spinner_item );
		aradAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

		spnTimeFrame   = (Spinner)findViewById(R.id.spnTimeFrame);
		spnTimeFrame.setAdapter(aradAdapter);
		
		Intent intI = getIntent();

		setS = (Settings) intI.getSerializableExtra("Settings");
		locL = (Location) intI.getSerializableExtra("Location");
		smSM = (SalesMortgage) intI.getSerializableExtra("SalesMortgage");
		rR = (Rehab) intI.getSerializableExtra("Rehab");
		rsR = (Reserves) intI.getSerializableExtra("Reserves");
		cemC = (ClosExpPropMktInfo) intI.getSerializableExtra("ClosExpPropMktInfo");

		TextView tvSettings = (TextView) findViewById(R.id.txtRehabFinance);		
		tvSettings.setText(setS.toString());
		
		TextView tvFRAddress = (TextView) findViewById(R.id.txtLocationAddress);
		TextView tvFRCityStZip = (TextView) findViewById(R.id.txtLocationCityStZip);
		TextView tvSF = (TextView) findViewById(R.id.txtLocationSqFootage);
		TextView tvBedBath = (TextView) findViewById(R.id.txtLocationBedBath);
		
		tvFRAddress.setText("Address:\t\t\t\t\t\t\t " + locL.getAddress());
		tvFRCityStZip.setText("City/State/ZIP Code:\t\t " + locL.getCity() + ", " + locL.getState() + " " + locL.getZIPCode());
		tvSF.setText("Square Footage:\t\t\t\t " + locL.getSquareFootage() + "");
		tvBedBath.setText("BR/BA:\t\t\t\t\t\t\t\t " + locL.getBedrooms() + " BR/" + locL.getBathrooms() + " BA");

		TextView tvSalePrice = (TextView) findViewById(R.id.txtSalePrice);
		TextView tvPercentDown = (TextView) findViewById(R.id.txtPercentDown);
		TextView tvOfferBid = (TextView) findViewById(R.id.txtOfferBid);
		TextView tvRehabBudget = (TextView) findViewById(R.id.txtRehabBudget);
		
		tvSalePrice.setText("Sale Price:\t\t\t\t\t\t\t $" + String.format("%.0f", smSM.getSalesPrice()));
		tvPercentDown.setText("Percent Down %:\t\t\t\t " + String.format("%.0f", smSM.getPercentDown()) + "%");
		tvOfferBid.setText("Offer/Bid Price:\t\t\t\t $" + String.format("%.0f", smSM.getOfferBid()));
		tvRehabBudget.setText("Rehab Budget:\t\t\t\t\t $" + String.format("%.0f", rR.getBudget()));

		TextView tvDownPayment = (TextView) findViewById(R.id.txtDownPayment);
		TextView tvLoanAmount = (TextView) findViewById(R.id.txtLoanAmount);
		TextView tvInterestRate = (TextView) findViewById(R.id.txtInterestRate);
		TextView tvTerm = (TextView) findViewById(R.id.txtTerm);
		TextView tvMonthlyPmt = (TextView) findViewById(R.id.txtMonthlyPmt);
		
		tvDownPayment.setText("Down Payment:\t\t\t\t $" + String.format("%.0f", smSM.getDownPayment()));
		tvLoanAmount.setText("Loan Amount:\t\t\t\t\t $" + String.format("%.0f", smSM.getLoanAmount()));
		tvInterestRate.setText("Interest Rate %:\t\t\t\t " + String.format("%.0f", smSM.getInterestRate()) + "%");
		tvTerm.setText("Term (months):\t\t\t\t " + smSM.getTerm());
		tvMonthlyPmt.setText("Monthly Pmt:\t\t\t\t\t $" + String.format("%.0f", smSM.getMonthlyPmt()));

		frF = new FinalResult();
		frF.setRECost(cemC.getSellingPrice(), cemC.getRealEstComm());
		frF.setBCCost(smSM.getSalesPrice(), cemC.getBuyClosCost());
		frF.setSCCost(smSM.getSalesPrice(), cemC.getSellClosCost());

		TextView tvRealEstComm = (TextView) findViewById(R.id.txtRealEstComm);
		TextView tvRealEstCommPer = (TextView) findViewById(R.id.txtRealEstCommPer);
		TextView tvBuyerClosCost = (TextView) findViewById(R.id.txtBuyerClosCost);
		TextView tvBuyerClosCostPer = (TextView) findViewById(R.id.txtBuyerClosCostPer);
		TextView tvSellerClosCost = (TextView) findViewById(R.id.txtSellerClosCost);
		TextView tvSellerClosCostPer = (TextView) findViewById(R.id.txtSellerClosCostPer);

		tvRealEstComm.setText("Real Estate Comm:\t\t\t $" + String.format("%.0f", frF.getRECost()));
		tvRealEstCommPer.setText("Commission %:\t\t\t\t " + String.format("%.0f", cemC.getRealEstComm()) + "%");
		tvBuyerClosCost.setText("Buyer Clos Cost:\t\t\t\t $" + String.format("%.0f", frF.getBCCost()));
		tvBuyerClosCostPer.setText("Closing Cost %:\t\t\t\t " +String.format("%.0f", cemC.getBuyClosCost()) + "%");
	    tvSellerClosCost.setText("Sell Clos Cost:\t\t\t\t\t $" + String.format("%.0f", frF.getSCCost()));
		tvSellerClosCostPer.setText("Closing Cost %:\t\t\t\t " + String.format("%.0f",  cemC.getSellClosCost()) + "%");

		TextView tvFMVARV = (TextView) findViewById(R.id.txtFMVARV);
		TextView tvComparables = (TextView) findViewById(R.id.txtComparables);
		TextView tvSellingPrice = (TextView) findViewById(R.id.txtSellingPrice);

		tvFMVARV.setText("FMV/ARV:\t\t\t\t\t\t\t $" + String.format("%.0f", cemC.getFMVARV()));
		tvComparables.setText("Comparables:\t\t\t\t\t $" + String.format("%.0f", cemC.getComparables()));
		tvSellingPrice.setText("Selling Price:\t\t\t\t\t $" + String.format("%.0f", cemC.getSellingPrice()));

		spnTimeFrame.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
	        	spnTimeFrame.setSelection(i, true);
	            Object item = adapterView.getItemAtPosition(i);
	            double dTimeFrameFactor = 1.0;
	    		// set time frame factor based on time frame value
	    		if (i == 0) {
	    			dTimeFrameFactor = 1.0;
	    		}
	    		if (i == 1) {
	    			dTimeFrameFactor = 1.5;
	    		}
	    		if (i == 2) {
	    			dTimeFrameFactor = 2.0;
	    		}

	    		TextView tvResMort = (TextView) findViewById(R.id.txtMortPmt);
	    		TextView tvResTaxes = (TextView) findViewById(R.id.txtPropertyTaxes);
	    		TextView tvResIns = (TextView) findViewById(R.id.txtInsurance);
	    		TextView tvResElect = (TextView) findViewById(R.id.txtElectric);
	    		TextView tvResWater = (TextView) findViewById(R.id.txtWater);
	    		TextView tvResGas = (TextView) findViewById(R.id.txtGas);
	    		TextView tvTotRes = (TextView) findViewById(R.id.txtTotalReserves);

	    		// calculates reserves based on time frame factor
	    		tvResMort.setText("Mortgage:\t\t\t\t\t\t\t $" + String.format("%.0f", (rsR.getMortgage() * dTimeFrameFactor)));
	    		tvResTaxes.setText("Property Taxes:\t\t\t\t $" + String.format("%.0f", (rsR.getTaxes() * dTimeFrameFactor)));
	    		tvResIns.setText("Insurance:\t\t\t\t\t\t\t $" + String.format("%.0f", (rsR.getInsurance() * dTimeFrameFactor)));
	    		tvResElect.setText("Electric:\t\t\t\t\t\t\t\t $" +String.format("%.0f", (rsR.getElectric() * dTimeFrameFactor)));
	    		tvResWater.setText("Water:\t\t\t\t\t\t\t\t $" + String.format("%.0f", (rsR.getWater() * dTimeFrameFactor)));
	    		tvResGas.setText("Gas:\t\t\t\t\t\t\t\t\t $" + String.format("%.0f", (rsR.getGas() * dTimeFrameFactor)));
	    		tvTotRes.setText("Total Reserves:\t\t\t\t $" + String.format("%.0f", (rsR.getTotalExpenses() * dTimeFrameFactor)));

	    		frF.setTotalCost(smSM.getOfferBid(), rR.getBudget(), (rsR.getTotalExpenses() * dTimeFrameFactor));
	    		// if finance rehab flag is not selected, set out of pocket expenses as follows
	    		if (setS.getFinance() != 2) {
	    			frF.setOOPExp(smSM.getDownPayment(), (rsR.getTotalExpenses() * dTimeFrameFactor), rR.getBudget());
	    		}
	    		// if finance rehab flag is selected, set out of pocket expenses as follows
	    		if (setS.getFinance() == 2) {
	    			frF.setOOPExp(smSM.getDownPayment(), (rsR.getTotalExpenses() * dTimeFrameFactor), 0.0);
	    		}	    		
	    		frF.setGrossProfit(cemC.getSellingPrice());
	    		frF.setCapGains();
	    		frF.setNetProfit();
	    		if (frF.getGrossProfit() < 30000.0) {
	    			AlertDialog adAlertBox = new AlertDialog.Builder(FinalResultActivity.this)
	    		    .setMessage("Your gross profit is below $30K! Would you like to make changes now?")
	    		    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	    		        // do something when the button is clicked
	    		        public void onClick(DialogInterface arg0, int arg1) {
	    		        	Intent intB = new Intent(FinalResultActivity.this, SettingsActivity.class);
	    		    		intB.putExtra("Settings", setS);
	    		    		intB.putExtra("Location", locL);
	    		    		intB.putExtra("SalesMortgage", smSM);
	    		    		intB.putExtra("Rehab", rR);
	    		    		intB.putExtra("Reserves", rsR);
	    		    		intB.putExtra("ClosExpPropMktInfo", cemC);
	    		        	startActivity(intB);
	    		        	finish();
	    		        }
	    		    })
	    		    .setNegativeButton("No", new DialogInterface.OnClickListener() {
	    		        // do something when the button is clicked
	    		        public void onClick(DialogInterface arg0, int arg1) {
	    		        }
	    		    })
	    		    .show();
	    		}
	    		frF.setROI(cemC.getSellingPrice());
	    		frF.setCashOnCash();

	    		TextView tvTotalCosts = (TextView) findViewById(R.id.txtTotalCosts);
	    		TextView tvOutOfPocket = (TextView) findViewById(R.id.txtOutOfPocketExpenses);

	    		tvTotalCosts.setText("Total Costs:\t\t\t\t\t\t $" + String.format("%.0f", frF.getTotalCost()));
	    		tvOutOfPocket.setText("Out of Pocket Exp:\t\t\t $" + String.format("%.0f", frF.getOOPExp()));

	    		TextView tvBuyerCosts = (TextView) findViewById(R.id.txtBuyerCosts);
	    		TextView tvGrossProfit = (TextView) findViewById(R.id.txtGrossProfit);
	    		TextView tvCapGains = (TextView) findViewById(R.id.txtCapGains);
	    		TextView tvNetProfit = (TextView) findViewById(R.id.txtNetProfit);
	    		TextView tvMoneyOut = (TextView) findViewById(R.id.txtMoneyOut);
	    		TextView tvMoneyIn = (TextView) findViewById(R.id.txtMoneyIn);
	    		TextView tvPercReturn = (TextView) findViewById(R.id.txtPercReturn);
	    		TextView tvCashCashRet = (TextView) findViewById(R.id.txtCashCashRet);

	    		tvBuyerCosts.setText("Buy + Costs:\t\t\t\t\t\t $" + String.format("%.0f", frF.getTotalCost()));
	    	    tvGrossProfit.setText("Gross Profit:\t\t\t\t\t\t $" + String.format("%.0f", frF.getGrossProfit()));
	    		tvCapGains.setText("Capital Gains:\t\t\t\t\t $" + String.format("%.0f",  frF.getCapGains()));
	    		tvNetProfit.setText("Net Profit:\t\t\t\t\t\t\t $" + String.format("%.0f", frF.getNetProfit()));
	    		tvMoneyOut.setText("Money Out:\t\t\t\t\t\t $" + String.format("%.0f", frF.getOOPExp()));
	    		tvMoneyIn.setText("Money In:\t\t\t\t\t\t\t $" + String.format("%.0f", frF.getNetProfit()));
	    		tvPercReturn.setText("% Return:\t\t\t\t\t\t\t " + String.format("%.1f", frF.getROI()) + "%");
	    		tvCashCashRet.setText("Cash on Cash Return:\t " + String.format("%.1f", frF.getCashOnCash()) + "%");
	        }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               // sometimes you need nothing here
            }
		});
   }
 
	// returns to main menu
	public void mainMenu(View view) {
	    Intent intI = new Intent(FinalResultActivity.this, MainActivity.class);
	    startActivity(intI);
	    finish();
	};

	public void prevPage(View view) {
		Intent intI = new Intent(this, ClosExpPropMktInfoActivity.class);
		intI.putExtra("Settings", setS);
		intI.putExtra("Location", locL);
		intI.putExtra("SalesMortgage", smSM);
		intI.putExtra("Rehab", rR);
		intI.putExtra("Reserves", rsR);
		intI.putExtra("ClosExpPropMktInfo", cemC);
		startActivity(intI);
		finish();
	}

	public void nextPage(View view) {
		// email results of calculate to those parties concerned
		String strMessage = "Address:\t\t\t\t\t\t\t   " + locL.getAddress() + "\n";
		strMessage += "City/State/ZIP Code:\t\t " + locL.getCity() + ", " + locL.getState() + " " + locL.getZIPCode() +"\n";
		strMessage += "Square Footage:\t\t\t\t  " + locL.getSquareFootage() + "\n";
		strMessage += "Bedrooms/Bathrooms:\t\t  " + locL.getBedrooms() + " BR " + locL.getBathrooms() + " BA\n";
		strMessage += "Sale Price:\t\t\t\t\t\t $" + String.format("%.0f", smSM.getSalesPrice()) + "\n";
		strMessage += "Percent Down %:\t\t\t\t  " + String.format("%.0f", smSM.getPercentDown()) + "%\n";
		strMessage += "Offer/Bid Price:\t\t\t\t$" + String.format("%.0f", smSM.getOfferBid()) + "\n";
		strMessage += "Rehab Budget:\t\t\t\t\t $" + String.format("%.0f", rR.getBudget()) + "\n";
		strMessage += "Down Payment:\t\t\t\t   $" + String.format("%.0f", smSM.getDownPayment()) + "\n";
		strMessage += "Loan Amount:\t\t\t\t\t  $" + String.format("%.0f", smSM.getLoanAmount()) + "\n";
		strMessage += "Interest Rate %:\t\t\t\t " + String.format("%.0f", smSM.getInterestRate()) + "%\n";
		strMessage += "Term (months):\t\t\t\t   " + smSM.getTerm() + "\n";
		strMessage += "Monthly Pmt:\t\t\t\t\t  $" + String.format("%.0f", smSM.getMonthlyPmt()) + "\n";
		strMessage += "Mortgage:\t\t\t\t\t\t\t $" + String.format("%.0f", rsR.getMortgage()) + "\n";
		strMessage += "Property Taxes:\t\t\t\t $" + String.format("%.0f", rsR.getTaxes()) + "\n";
		strMessage += "Insurance:\t\t\t\t\t\t\t $" + String.format("%.0f", rsR.getInsurance()) + "\n";
		strMessage += "Electric:\t\t\t\t\t\t\t\t $" +String.format("%.0f", rsR.getElectric()) + "\n";
		strMessage += "Water:\t\t\t\t\t\t\t\t $" + String.format("%.0f", rsR.getWater()) + "\n";
		strMessage += "Gas:\t\t\t\t\t\t\t\t\t $" + String.format("%.0f", rsR.getGas()) + "\n";
		strMessage += "Total Reserves:\t\t\t\t $" + String.format("%.0f", rsR.getTotalExpenses()) + "\n";
		strMessage += "Real Estate Comm:\t\t\t $" + String.format("%.0f", frF.getRECost()) + "\n";
		strMessage += "Commission %:\t\t\t\t " + String.format("%.0f", cemC.getRealEstComm()) + "%\n";
		strMessage += "Buyer Clos Cost:\t\t\t\t $" + String.format("%.0f", frF.getBCCost()) + "\n";
		strMessage += "Closing Cost %:\t\t\t\t " +String.format("%.0f", cemC.getBuyClosCost()) + "%\n";
		strMessage += "Sell Clos Cost:\t\t\t\t\t $" + String.format("%.0f", frF.getSCCost()) + "\n";
		strMessage += "Closing Cost %:\t\t\t\t " + String.format("%.0f",  cemC.getSellClosCost()) + "%\n";
		strMessage += "Total Costs:\t\t\t\t\t\t $" + String.format("%.0f", frF.getTotalCost()) + "\n";
		strMessage += "Out of Pocket Exp:\t\t\t $" + String.format("%.0f", frF.getOOPExp()) + "\n";
		strMessage += "FMV/ARV:\t\t\t\t\t\t\t $" + String.format("%.0f", cemC.getFMVARV()) + "\n";
		strMessage += "Comparables:\t\t\t\t\t $" + String.format("%.0f", cemC.getComparables()) + "\n";
		strMessage += "Selling Price:\t\t\t\t\t $" + String.format("%.0f", cemC.getSellingPrice()) + "\n";
		strMessage += "Buy + Costs:\t\t\t\t\t\t $" + String.format("%.0f", frF.getTotalCost()) + "\n";
		strMessage += "Gross Profit:\t\t\t\t\t\t $" + String.format("%.0f", frF.getGrossProfit()) + "\n";
		strMessage += "Capital Gains:\t\t\t\t\t $" + String.format("%.0f",  frF.getCapGains()) + "\n";
		strMessage += "Net Profit:\t\t\t\t\t\t\t $" + String.format("%.0f", frF.getNetProfit()) + "\n";
		strMessage += "Money Out:\t\t\t\t\t\t $" + String.format("%.0f", frF.getOOPExp()) + "\n";
		strMessage += "Money In:\t\t\t\t\t\t\t $" + String.format("%.0f", frF.getNetProfit()) + "\n";
		strMessage += "% Return:\t\t\t\t\t\t\t " + String.format("%.1f", frF.getROI()) + "%\n";
		strMessage += "Cash on Cash Return:\t " + String.format("%.1f", frF.getCashOnCash()) + "%\n";
		Intent intEmailActivity = new Intent(Intent.ACTION_SEND);
		intEmailActivity.putExtra(Intent.EXTRA_EMAIL, new String[]{});
		intEmailActivity.putExtra(Intent.EXTRA_SUBJECT, "Flipulator results for: " + locL.getAddress() + " " + locL.getCity() + ", " + locL.getState() + " " + locL.getZIPCode());
		intEmailActivity.putExtra(Intent.EXTRA_TEXT, strMessage);
		intEmailActivity.setType("plain/text");
   		startActivity(intEmailActivity);
	}

	public void createSpreadsheet(File fDir, String strXLSFile) throws IOException, WriteException {
		File fileXls = new File(fDir, strXLSFile);
	    WorkbookSettings wbSettings = new WorkbookSettings();

	    wbSettings.setLocale(new Locale("en", "EN"));

	    WritableWorkbook workbook = Workbook.createWorkbook(fileXls, wbSettings);
	    workbook.createSheet(locL.getAddress() + " " + locL.getCity() + " " + locL.getState() + " " + locL.getZIPCode(), 0);
	    WritableSheet excelSheet = workbook.getSheet(0);

	    // Lets create a times font
	    WritableFont times18ptHeader = new WritableFont(WritableFont.ARIAL, 18, WritableFont.BOLD);
	    // Define the cell format
	    times = new WritableCellFormat(times18ptHeader);
	    // Lets automatically wrap the cells
	    times.setWrap(true);

	    // create a bold font
	    WritableFont times10ptBold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);
	    timesBold = new WritableCellFormat(times10ptBold);
	    // Lets automatically wrap the cells
	    timesBold.setWrap(true);

	    CellView cv = new CellView();
	    cv.setFormat(times);
	    cv.setFormat(timesBold);
	    cv.setAutosize(true);

	    // Number and writable cell formats
	    NumberFormat nbfDollar = new NumberFormat("$###,##0");
	    WritableCellFormat wcfDollar = new WritableCellFormat(nbfDollar);

	    NumberFormat nbfPercent = new NumberFormat("##%");
	    WritableCellFormat wcfPercent = new WritableCellFormat(nbfPercent);

	    NumberFormat nbfPercentTwoPlaces = new NumberFormat("#0.00%");
	    WritableCellFormat wcfPercentTwoPlaces = new WritableCellFormat(nbfPercentTwoPlaces);

	    // Write a few headers
	    WritableCell wrcOriginal = new Label(0, 0, "Original", times);
	    excelSheet.addCell(wrcOriginal);
	    excelSheet.mergeCells(0, 0, 4, 0);

	    WritableCell wrcOwnerCarry = new Label(7, 0, "Owner Carry Rehab Cost", times);
	    excelSheet.addCell(wrcOwnerCarry);
	    excelSheet.mergeCells(7, 0, 11, 0);

	    WritableCell wrcFinanceRehab = new Label(13, 0, "Finance Rehab Cost", times);
	    excelSheet.addCell(wrcFinanceRehab);
	    excelSheet.mergeCells(13, 0, 17, 0);

	    WritableCell wrcSalesOrig = new Label(0, 6, "I. Sales Information", times);
	    excelSheet.addCell(wrcSalesOrig);
	    excelSheet.mergeCells(0, 6, 1, 6);

	    WritableCell wrcMortOrig = new Label(2, 6, "II. Mortgage Information", times);
	    excelSheet.addCell(wrcMortOrig);
	    excelSheet.mergeCells(2, 6, 4, 6);

	    WritableCell wrcSalesOwnerCarry = new Label(7, 6, "I. Sales Information", times);
	    excelSheet.addCell(wrcSalesOwnerCarry);
	    excelSheet.mergeCells(7, 6, 8, 6);

	    WritableCell wrcMortOwnerCarry = new Label(9, 6, "II. Mortgage Information", times);
	    excelSheet.addCell(wrcMortOwnerCarry);
	    excelSheet.mergeCells(9, 6, 11, 6);

	    WritableCell wrcSalesFinanceRehab = new Label(13, 6, "I. Sales Information", times);
	    excelSheet.addCell(wrcSalesFinanceRehab);
	    excelSheet.mergeCells(13, 6, 14, 6);

	    WritableCell wrcMortFinanceRehab = new Label(15, 6, "II. Mortgage Information", times);
	    excelSheet.addCell(wrcMortFinanceRehab);
	    excelSheet.mergeCells(15, 6, 17, 6);

	    WritableCell wrcReservesOrig = new Label(0, 18, "III. Reserves", times);
	    excelSheet.addCell(wrcReservesOrig);
	    excelSheet.mergeCells(0, 18, 4, 18);

	    WritableCell wrcReservesOC = new Label(7, 18, "III. Reserves", times);
	    excelSheet.addCell(wrcReservesOC);
	    excelSheet.mergeCells(7, 18, 11, 18);

	    WritableCell wrcReservesFinRehab = new Label(13, 18, "III. Reserves", times);
	    excelSheet.addCell(wrcReservesFinRehab);
	    excelSheet.mergeCells(13, 18, 17, 18);

	    WritableCell wrcClosingOrig = new Label(0, 34, "IV. Closing Expenses", times);
	    excelSheet.addCell(wrcClosingOrig);
	    excelSheet.mergeCells(0, 34, 4, 34);

	    WritableCell wrcClosingOC = new Label(7, 34, "IV. Closing Expenses", times);
	    excelSheet.addCell(wrcClosingOC);
	    excelSheet.mergeCells(7, 34, 11, 34);

	    WritableCell wrcClosingFinRehab = new Label(13, 34, "IV. Closing Expenses", times);
	    excelSheet.addCell(wrcClosingFinRehab);
	    excelSheet.mergeCells(13, 34, 17, 34);

	    WritableCell wrcPropMktOrig = new Label(0, 46, "V. Property/Market Information", times);
	    excelSheet.addCell(wrcPropMktOrig);
	    excelSheet.mergeCells(0, 46, 4, 46);

	    WritableCell wrcPropMktOC = new Label(7, 46, "V. Property/Market Information", times);
	    excelSheet.addCell(wrcPropMktOC);
	    excelSheet.mergeCells(7, 46, 11, 46);

	    WritableCell wrcPropMktFinRehab = new Label(13, 46, "V. Property/Market Information", times);
	    excelSheet.addCell(wrcPropMktFinRehab);
	    excelSheet.mergeCells(13, 46, 17, 46);

	    WritableCell wrcROIOrig = new Label(0, 58, "VI. Return on Investment", times);
	    excelSheet.addCell(wrcROIOrig);
	    excelSheet.mergeCells(0, 58, 4, 58);

	    WritableCell wrcROIOC = new Label(7, 58, "VI. Return on Investment", times);
	    excelSheet.addCell(wrcROIOC);
	    excelSheet.mergeCells(7, 58, 11, 58);

	    WritableCell wrcROIFinRehab = new Label(13, 58, "VI. Return on Investment", times);
	    excelSheet.addCell(wrcROIFinRehab);
	    excelSheet.mergeCells(13, 58, 17, 58);

	    // set up column views
	    excelSheet.setColumnView(0, 25);
	    excelSheet.setColumnView(1, 14);
	    excelSheet.setColumnView(2, 17);
	    excelSheet.setColumnView(3, 17);
	    excelSheet.setColumnView(4, 16);
	    excelSheet.setColumnView(5, 3);
	    excelSheet.setColumnView(6, 3);
	    excelSheet.setColumnView(7, 29);
	    excelSheet.setColumnView(8, 14);
	    excelSheet.setColumnView(9, 20);
	    excelSheet.setColumnView(10, 17);
	    excelSheet.setColumnView(11, 16);
	    excelSheet.setColumnView(12, 3);
	    excelSheet.setColumnView(13, 29);
	    excelSheet.setColumnView(14, 14);
	    excelSheet.setColumnView(15, 19);
	    excelSheet.setColumnView(16, 17);
	    excelSheet.setColumnView(17, 14);
	    
	    // set up row views
	    excelSheet.setRowView(0, (int)((1.5d * 14)*20), false);
	    excelSheet.setRowView(6, (int)((1.5d * 14)*20), false);
	    excelSheet.setRowView(18, (int)((1.5d * 14)*20), false);
	    excelSheet.setRowView(34, (int)((1.5d * 14)*20), false);
	    excelSheet.setRowView(46, (int)((1.5d * 14)*20), false);
	    excelSheet.setRowView(58, (int)((1.5d * 14)*20), false);

	    // location info - original
	    Label lblPropAddressOrig;
	    lblPropAddressOrig = new Label(0, 1, "Property Address:", timesBold);
	    excelSheet.addCell(lblPropAddressOrig);
	    excelSheet.mergeCells(1, 1, 4, 1);
	    Label lblStreetAddress = new Label(1, 1, locL.getAddress(), timesBold);
	    excelSheet.addCell(lblStreetAddress);

	    Label lblCityOrig;
	    lblCityOrig = new Label(0, 2, "City:", timesBold);
	    excelSheet.addCell(lblCityOrig);
	    Label lblCity = new Label(1, 2, locL.getCity(), timesBold);
	    excelSheet.addCell(lblCity);

	    Label lblStateOrig;
	    lblStateOrig = new Label(0, 3, "State:", timesBold);
	    excelSheet.addCell(lblStateOrig);
	    Label lblState = new Label(1, 3, locL.getState(), timesBold);
	    excelSheet.addCell(lblState);

	    Label lblZipOrig;
	    lblZipOrig = new Label(0, 4, "ZIP:", timesBold);
	    excelSheet.addCell(lblZipOrig);
	    Label lblStZip = new Label(1, 4, locL.getZIPCode(), timesBold);
	    excelSheet.addCell(lblStZip);

	    Label lblSquareFootageOrig;
	    lblSquareFootageOrig = new Label(2, 2, "Square Footage:", timesBold);
	    excelSheet.addCell(lblSquareFootageOrig);
	    excelSheet.mergeCells(3, 2, 4, 2);
	    Label lblSquareFootage = new Label(3, 2, locL.getSquareFootage() + "", timesBold);
	    excelSheet.addCell(lblSquareFootage);

	    Label lblBROrig;
	    lblBROrig = new Label(2, 3, "BR:", timesBold);
	    excelSheet.addCell(lblBROrig);
	    excelSheet.mergeCells(3, 3, 4, 3);
	    Label lblBR = new Label(3, 3, locL.getBedrooms() + "", timesBold);
	    excelSheet.addCell(lblBR);

	    Label lblBAOrig;
	    lblBAOrig = new Label(2, 4, "BA:", timesBold);
	    excelSheet.addCell(lblBAOrig);
	    excelSheet.mergeCells(3, 4, 4, 4);
	    Label lblBA = new Label(3, 4, locL.getBathrooms() + "", timesBold);
	    excelSheet.addCell(lblBA);

	    // sales info - original
	    Label lblSalesPriceOrig;
	    lblSalesPriceOrig = new Label(0, 8, "Sales Price:", timesBold);
	    excelSheet.addCell(lblSalesPriceOrig);
	    Number nbrSalesPriceO = new Number(1, 8, smSM.getSalesPrice(), wcfDollar);
	    excelSheet.addCell(nbrSalesPriceO);

	    Label lblPercentDownOrig;
	    lblPercentDownOrig = new Label(0, 10, "Percent Down:", timesBold);
	    excelSheet.addCell(lblPercentDownOrig);
	    Number nbrPercentDownO = new Number(1, 10, (smSM.getPercentDown()/100), wcfPercent);
	    excelSheet.addCell(nbrPercentDownO);

	    Label lblOfferBidOrig;
	    lblOfferBidOrig = new Label(0, 12, "Offer/Bid Price:", timesBold);
	    excelSheet.addCell(lblOfferBidOrig);
	    Number nbrOfferBidO = new Number(1, 12, smSM.getOfferBid(), wcfDollar);
	    excelSheet.addCell(nbrOfferBidO);

	    Label lblRehabOrig;
	    lblRehabOrig = new Label(0, 14, "Rehab Budget:", timesBold);
	    excelSheet.addCell(lblRehabOrig);
	    Number nbrRehabO = new Number(1, 14, rR.getBudget(), wcfDollar);
	    excelSheet.addCell(nbrRehabO);

	    // mortgage info - original
	    Label lblDownPaymentOrig;
	    lblDownPaymentOrig = new Label(2, 8, "Down Payment:", timesBold);
	    excelSheet.addCell(lblDownPaymentOrig);
	    StringBuffer buf = new StringBuffer();
	    buf.append("SUM(B11*B9)");
	    Formula forDownPaymentO = new Formula(3, 8, buf.toString(), wcfDollar);
	    excelSheet.addCell(forDownPaymentO);

	    Label lblLoanAmountOrig;
	    lblLoanAmountOrig = new Label(2, 10, "Loan Amount:", timesBold);
	    excelSheet.addCell(lblLoanAmountOrig);
	    buf = new StringBuffer();
	    buf.append("(B13-D9)");
	    Formula forLoanAmountO = new Formula(3, 10, buf.toString(), wcfDollar);
	    excelSheet.addCell(forLoanAmountO);

	    Label lblInterestRateOrig;
	    lblInterestRateOrig = new Label(2, 12, "Interest Rate:", timesBold);
	    excelSheet.addCell(lblInterestRateOrig);
	    Number nbrInterestRateO = new Number(3, 12, (smSM.getInterestRate()/100), wcfPercentTwoPlaces);
	    excelSheet.addCell(nbrInterestRateO);

	    Label lblTermOrig;
	    lblTermOrig = new Label(2, 14, "Term:", timesBold);
	    excelSheet.addCell(lblTermOrig);
	    Number nbrTermO = new Number(3, 14, smSM.getTerm());
	    excelSheet.addCell(nbrTermO);

	    Label lblMonthlyPmtOrig;
	    lblMonthlyPmtOrig = new Label(2, 16, "Monthly Pmt:", timesBold);
	    excelSheet.addCell(lblMonthlyPmtOrig);
	    buf = new StringBuffer();
	    buf.append("SUM(D13*D11/12)");
	    Formula forMonthlyPmtO = new Formula(3, 16, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMonthlyPmtO);

	    // reserves info - original
	    Label lblMonthlyReservesOrig;
	    lblMonthlyReservesOrig = new Label(1, 19, "Monthly", timesBold);
	    excelSheet.addCell(lblMonthlyReservesOrig);

	    Label lblSixMonthReservesOrig;
	    lblSixMonthReservesOrig = new Label(2, 19, "6 Months", timesBold);
	    excelSheet.addCell(lblSixMonthReservesOrig);

	    Label lblNineMonthReservesOrig;
	    lblNineMonthReservesOrig = new Label(3, 19, "9 Months", timesBold);
	    excelSheet.addCell(lblNineMonthReservesOrig);
	    
	    Label lblTwelveMonthReservesOrig;
	    lblTwelveMonthReservesOrig = new Label(4, 19, "12 Months", timesBold);
	    excelSheet.addCell(lblTwelveMonthReservesOrig);

	    Label lblMortgageReservesOrig;
	    lblMortgageReservesOrig = new Label(0, 20, "Mortgage", timesBold);
	    excelSheet.addCell(lblMortgageReservesOrig);

	    buf = new StringBuffer();
	    buf.append("(D17)");
	    Formula forMortgageMonthO = new Formula(1, 20, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMortgageMonthO);

	    buf = new StringBuffer();
	    buf.append("SUM(D17*6)");
	    Formula forMortgageSixMonthO = new Formula(2, 20, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMortgageSixMonthO);

	    buf = new StringBuffer();
	    buf.append("SUM(D17*9)");
	    Formula forMortgageNineMonthO = new Formula(3, 20, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMortgageNineMonthO);

	    buf = new StringBuffer();
	    buf.append("SUM(D13*D11)");
	    Formula forMortgageAnnualO = new Formula(4, 20, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMortgageAnnualO);

	    Label lblTaxesReservesOrig;
	    lblTaxesReservesOrig = new Label(0, 22, "Taxes", timesBold);
	    excelSheet.addCell(lblTaxesReservesOrig);

	    buf = new StringBuffer();
	    buf.append("(C23/6)");
	    Formula forTaxesMonthO = new Formula(1, 22, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTaxesMonthO);

	    Number nbrTaxesO = new Number(2, 22, rsR.getTaxes(), wcfDollar);
	    excelSheet.addCell(nbrTaxesO);

	    buf = new StringBuffer();
	    buf.append("SUM(C23*9/6)");
	    Formula forTaxesNineMonthO = new Formula(3, 22, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTaxesNineMonthO);

	    buf = new StringBuffer();
	    buf.append("SUM(C23*2)");
	    Formula forTaxesAnnualO = new Formula(4, 22, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTaxesAnnualO);

	    Label lblInsuranceReservesOrig;
	    lblInsuranceReservesOrig = new Label(0, 24, "Insurance", timesBold);
	    excelSheet.addCell(lblInsuranceReservesOrig);

	    buf = new StringBuffer();
	    buf.append("(C25/6)");
	    Formula forInsuranceMonthO = new Formula(1, 24, buf.toString(), wcfDollar);
	    excelSheet.addCell(forInsuranceMonthO);

	    Number nbrInsuranceO = new Number(2, 24, rsR.getInsurance(), wcfDollar);
	    excelSheet.addCell(nbrInsuranceO);

	    buf = new StringBuffer();
	    buf.append("SUM(C25*9/6)");
	    Formula forInsuranceNineMonthO = new Formula(3, 24, buf.toString(), wcfDollar);
	    excelSheet.addCell(forInsuranceNineMonthO);

	    buf = new StringBuffer();
	    buf.append("SUM(C25*2)");
	    Formula forInsuranceAnnualO = new Formula(4, 24, buf.toString(), wcfDollar);
	    excelSheet.addCell(forInsuranceAnnualO);

	    Label lblWaterReservesOriginal;
	    lblWaterReservesOriginal = new Label(0, 26, "Water", timesBold);
	    excelSheet.addCell(lblWaterReservesOriginal);

	    buf = new StringBuffer();
	    buf.append("(C27/6)");
	    Formula forWaterMonthO = new Formula(1, 26, buf.toString(), wcfDollar);
	    excelSheet.addCell(forWaterMonthO);

	    Number nbrWaterO = new Number(2, 26, rsR.getWater(), wcfDollar);
	    excelSheet.addCell(nbrWaterO);

	    buf = new StringBuffer();
	    buf.append("SUM(C27*9/6)");
	    Formula forWaterNineMonthO = new Formula(3, 26, buf.toString(), wcfDollar);
	    excelSheet.addCell(forWaterNineMonthO);

	    buf = new StringBuffer();
	    buf.append("SUM(C27*2)");
	    Formula forWaterAnnualO = new Formula(4, 26, buf.toString(), wcfDollar);
	    excelSheet.addCell(forWaterAnnualO);

	    Label lblGasReservesOriginal;
	    lblGasReservesOriginal = new Label(0, 28, "Gas", timesBold);
	    excelSheet.addCell(lblGasReservesOriginal);

	    buf = new StringBuffer();
	    buf.append("(C29/6)");
	    Formula forGasMonthO = new Formula(1, 28, buf.toString(), wcfDollar);
	    excelSheet.addCell(forGasMonthO);

	    Number nbrGasO = new Number(2, 28, rsR.getGas(), wcfDollar);
	    excelSheet.addCell(nbrGasO);

	    buf = new StringBuffer();
	    buf.append("SUM(C29*9/6)");
	    Formula forGasNineMonthO = new Formula(3, 28, buf.toString(), wcfDollar);
	    excelSheet.addCell(forGasNineMonthO);

	    buf = new StringBuffer();
	    buf.append("SUM(C29*2)");
	    Formula forGasAnnualO = new Formula(4, 28, buf.toString(), wcfDollar);
	    excelSheet.addCell(forGasAnnualO);

	    Label lblElectricReservesOriginal;
	    lblElectricReservesOriginal = new Label(0, 30, "Electric", timesBold);
	    excelSheet.addCell(lblElectricReservesOriginal);

	    buf = new StringBuffer();
	    buf.append("(C31/6)");
	    Formula forElectricMonthO = new Formula(1, 30, buf.toString(), wcfDollar);
	    excelSheet.addCell(forElectricMonthO);

	    Number nbrElectricO = new Number(2, 30, rsR.getElectric(), wcfDollar);
	    excelSheet.addCell(nbrElectricO);

	    buf = new StringBuffer();
	    buf.append("SUM(C31*9/6)");
	    Formula forElectricNineMonthO = new Formula(3, 30, buf.toString(), wcfDollar);
	    excelSheet.addCell(forElectricNineMonthO);

	    buf = new StringBuffer();
	    buf.append("SUM(C31*2)");
	    Formula forElectricAnnualO = new Formula(4, 30, buf.toString(), wcfDollar);
	    excelSheet.addCell(forElectricAnnualO);

	    Label lblTotalReservesOriginal;
	    lblTotalReservesOriginal = new Label(0, 32, "Total Expenses", timesBold);
	    excelSheet.addCell(lblTotalReservesOriginal);

	    buf = new StringBuffer();
	    buf.append("SUM(B21:B31)");
	    Formula forTotalMonthO = new Formula(1, 32, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalMonthO);

	    buf = new StringBuffer();
	    buf.append("SUM(C21:C31)");
	    Formula forTotalSixMonthO = new Formula(2, 32, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalSixMonthO);

	    buf = new StringBuffer();
	    buf.append("SUM(D21:D31)");
	    Formula forTotalNineMonthO = new Formula(3, 32, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalNineMonthO);

	    buf = new StringBuffer();
	    buf.append("SUM(E21:E31)");
	    Formula forTotalAnnualO = new Formula(4, 32, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalAnnualO);

	    // closing expenses - original
	    Label lblRealEstateCommissionOriginal;
	    lblRealEstateCommissionOriginal = new Label(0, 36, "Real Estate Commission", timesBold);
	    excelSheet.addCell(lblRealEstateCommissionOriginal);

	    buf = new StringBuffer();
	    buf.append("0");
	    Formula forRealEstateCommissionO = new Formula(1, 36, buf.toString(), wcfDollar);
	    excelSheet.addCell(forRealEstateCommissionO);

	    Label lblCommissionPercentageOriginal;
	    lblCommissionPercentageOriginal = new Label(2, 36, "Commission %", timesBold);
	    excelSheet.addCell(lblCommissionPercentageOriginal);

	    Number nbrCommissionPercentageO = new Number(3, 36, (cemC.getRealEstComm()/100), wcfPercentTwoPlaces);
	    excelSheet.addCell(nbrCommissionPercentageO);

	    Label lblBuyerClosingCostOriginal;
	    lblBuyerClosingCostOriginal = new Label(0, 38, "Buyer Closing Costs", timesBold);
	    excelSheet.addCell(lblBuyerClosingCostOriginal);

	    buf = new StringBuffer();
	    buf.append("0");
	    Formula forBuyerClosingCostO = new Formula(1, 38, buf.toString(), wcfDollar);
	    excelSheet.addCell(forBuyerClosingCostO);

	    Label lblBuyerClosingCostPercentageOriginal;
	    lblBuyerClosingCostPercentageOriginal = new Label(2, 38, "Closing Costs %", timesBold);
	    excelSheet.addCell(lblBuyerClosingCostPercentageOriginal);

	    Number nbrBuyerClosingCostPercentageO = new Number(3, 38, (cemC.getBuyClosCost()/100), wcfPercentTwoPlaces);
	    excelSheet.addCell(nbrBuyerClosingCostPercentageO);

	    Label lblSellerClosingCostOriginal;
	    lblSellerClosingCostOriginal = new Label(0, 39, "Seller Closing Costs", timesBold);
	    excelSheet.addCell(lblSellerClosingCostOriginal);

	    buf = new StringBuffer();
	    buf.append("0");
	    Formula forSellerClosingCostO = new Formula(1, 39, buf.toString(), wcfDollar);
	    excelSheet.addCell(forSellerClosingCostO);

	    Label lblSellerClosingCostPercentageOriginal;
	    lblSellerClosingCostPercentageOriginal = new Label(2, 39, "Closing Costs %", timesBold);
	    excelSheet.addCell(lblSellerClosingCostPercentageOriginal);

	    Number nbrSellerClosingCostPercentageO = new Number(3, 39, (cemC.getSellClosCost()/100), wcfPercentTwoPlaces);
	    excelSheet.addCell(nbrSellerClosingCostPercentageO);

	    Label lblSixMonthClosExpOrig;
	    lblSixMonthClosExpOrig = new Label(2, 41, "6 Months", timesBold);
	    excelSheet.addCell(lblSixMonthClosExpOrig);

	    Label lblNineMonthClosExpOrig;
	    lblNineMonthClosExpOrig = new Label(3, 41, "9 Months", timesBold);
	    excelSheet.addCell(lblNineMonthClosExpOrig);
	    
	    Label lblTwelveMonthClosExpOrig;
	    lblTwelveMonthClosExpOrig = new Label(4, 41, "12 Months", timesBold);
	    excelSheet.addCell(lblTwelveMonthClosExpOrig);

	    Label lblTotalCostsOriginal;
	    lblTotalCostsOriginal = new Label(0, 42, "Total Costs", timesBold);
	    excelSheet.addCell(lblTotalCostsOriginal);

	    buf = new StringBuffer();
	    buf.append("0");
	    Formula forTotalCostsSixMonthO = new Formula(2, 42, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalCostsSixMonthO);

	    buf = new StringBuffer();
	    buf.append("0");
	    Formula forTotalCostsNineMonthO = new Formula(3, 42, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalCostsNineMonthO);

	    buf = new StringBuffer();
	    buf.append("0");
	    Formula forTotalCostsAnnualO = new Formula(4, 42, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalCostsAnnualO);

	    Label lblOutOfPocketOriginal;
	    lblOutOfPocketOriginal = new Label(0, 43, "Out of Pocket Expenses", timesBold);
	    excelSheet.addCell(lblOutOfPocketOriginal);

	    buf = new StringBuffer();
	    buf.append("0");
	    Formula forOutOfPocketSixMonthO = new Formula(2, 43, buf.toString(), wcfDollar);
	    excelSheet.addCell(forOutOfPocketSixMonthO);

	    buf = new StringBuffer();
	    buf.append("0");
	    Formula forOutOfPocketNineMonthO = new Formula(3, 43, buf.toString(), wcfDollar);
	    excelSheet.addCell(forOutOfPocketNineMonthO);

	    buf = new StringBuffer();
	    buf.append("0");
	    Formula forOutOfPocketAnnualO = new Formula(4, 43, buf.toString(), wcfDollar);
	    excelSheet.addCell(forOutOfPocketAnnualO);

	    // location info - owner carry
	    Label lblPropAddressOwnerCarry;
	    lblPropAddressOwnerCarry = new Label(7, 1, "Property Address:", timesBold);
	    excelSheet.addCell(lblPropAddressOwnerCarry);
	    excelSheet.mergeCells(8, 1, 11, 1);
	    Label lblStreetAddressOC = new Label(8, 1, locL.getAddress(), timesBold);
	    excelSheet.addCell(lblStreetAddressOC);

	    Label lblCityOwnerCarry;
	    lblCityOwnerCarry = new Label(7, 2, "City:", timesBold);
	    excelSheet.addCell(lblCityOwnerCarry);
	    Label lblCityOC = new Label(8, 2, locL.getCity(), timesBold);
	    excelSheet.addCell(lblCityOC);

	    Label lblStOwnerCarry;
	    lblStOwnerCarry = new Label(7, 3, "State:", timesBold);
	    excelSheet.addCell(lblStOwnerCarry);
	    Label lblStOC = new Label(8, 3, locL.getState(), timesBold);
	    excelSheet.addCell(lblStOC);

	    Label lblZipOwnerCarry;
	    lblZipOwnerCarry = new Label(7, 4, "ZIP:", timesBold);
	    excelSheet.addCell(lblZipOwnerCarry);
	    Label lblZipOC = new Label(8, 4, locL.getZIPCode(), timesBold);
	    excelSheet.addCell(lblZipOC);

	    Label lblSquareFootageOwnerCarry;
	    lblSquareFootageOwnerCarry = new Label(9, 2, "Square Footage:", timesBold);
	    excelSheet.addCell(lblSquareFootageOwnerCarry);
	    excelSheet.mergeCells(10, 2, 11, 2);
	    Label lblSquareFootageOC = new Label(10, 2, locL.getSquareFootage() + "", timesBold);
	    excelSheet.addCell(lblSquareFootageOC);

	    Label lblBROwnerCarry;
	    lblBROwnerCarry = new Label(9, 3, "BR:", timesBold);
	    excelSheet.addCell(lblBROwnerCarry);
	    excelSheet.mergeCells(10, 3, 11, 3);
	    Label lblBROC = new Label(10, 3, locL.getBedrooms() + "", timesBold);
	    excelSheet.addCell(lblBROC);

	    Label lblBAOwnerCarry;
	    lblBAOwnerCarry = new Label(9, 4, "BA:", timesBold);
	    excelSheet.addCell(lblBAOwnerCarry);
	    excelSheet.mergeCells(10, 4, 11, 4);
	    Label lblBAOC = new Label(10, 4, locL.getBathrooms() + "", timesBold);
	    excelSheet.addCell(lblBAOC);

	    // sales info - owner carry
	    Label lblSalesPriceOwnerCarry;
	    lblSalesPriceOwnerCarry = new Label(7, 8, "Sales Price:", timesBold);
	    excelSheet.addCell(lblSalesPriceOwnerCarry);
	    Number nbrSalesPriceOC = new Number(8, 8, smSM.getSalesPrice(), wcfDollar);
	    excelSheet.addCell(nbrSalesPriceOC);

	    Label lblPercentDownOwnerCarry;
	    lblPercentDownOwnerCarry = new Label(7, 10, "Percent Down:", timesBold);
	    excelSheet.addCell(lblPercentDownOwnerCarry);
	    Number nbrPercentDownOC = new Number(8, 10, (smSM.getPercentDown()/100), wcfPercent);
	    excelSheet.addCell(nbrPercentDownOC);

	    Label lblOfferBidOwnerCarry;
	    lblOfferBidOwnerCarry = new Label(7, 12, "Offer/Bid Price:", timesBold);
	    excelSheet.addCell(lblOfferBidOwnerCarry);
	    Number nbrOfferBidOC = new Number(8, 12, smSM.getOfferBid(), wcfDollar);
	    excelSheet.addCell(nbrOfferBidOC);

	    Label lblRehabOwnerCarry;
	    lblRehabOwnerCarry = new Label(7, 14, "Rehab Budget:", timesBold);
	    excelSheet.addCell(lblRehabOwnerCarry);
	    Number nbrRehabOC = new Number(8, 14, rR.getBudget(), wcfDollar);
	    excelSheet.addCell(nbrRehabOC);

	    // mortgage info - original
	    Label lblDownPaymentOwnerCarry;
	    lblDownPaymentOwnerCarry = new Label(9, 8, "Down Payment:", timesBold);
	    excelSheet.addCell(lblDownPaymentOwnerCarry);
	    buf = new StringBuffer();
	    buf.append("SUM(I11*I9)");
	    Formula forDownPaymentOC = new Formula(10, 8, buf.toString(), wcfDollar);
	    excelSheet.addCell(forDownPaymentOC);

	    Label lblLoanAmountOwnerCarry;
	    lblLoanAmountOwnerCarry = new Label(9, 10, "Loan Amount:", timesBold);
	    excelSheet.addCell(lblLoanAmountOwnerCarry);
	    buf = new StringBuffer();
	    buf.append("(I13-K9)");
	    Formula forLoanAmountOC = new Formula(10, 10, buf.toString(), wcfDollar);
	    excelSheet.addCell(forLoanAmountOC);

	    Label lblInterestRateOwnerCarry;
	    lblInterestRateOwnerCarry = new Label(9, 12, "Interest Rate:", timesBold);
	    excelSheet.addCell(lblInterestRateOwnerCarry);
	    Number nbrInterestRateOC = new Number(10, 12, (smSM.getInterestRate()/100), wcfPercentTwoPlaces);
	    excelSheet.addCell(nbrInterestRateOC);

	    Label lblTermOwnerCarry;
	    lblTermOwnerCarry = new Label(9, 14, "Term:", timesBold);
	    excelSheet.addCell(lblTermOwnerCarry);
	    Number nbrTermOC = new Number(10, 14, smSM.getTerm());
	    excelSheet.addCell(nbrTermOC);

	    Label lblMonthlyPmtOwnerCarry;
	    lblMonthlyPmtOwnerCarry = new Label(9, 16, "Monthly Pmt:", timesBold);
	    excelSheet.addCell(lblMonthlyPmtOwnerCarry);
	    buf = new StringBuffer();
	    buf.append("SUM(K13*K11/12)");
	    Formula forMonthlyPmtOC = new Formula(10, 16, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMonthlyPmtOC);

	    // reserves info - owner carry
	    Label lblMonthlyReservesOwnerCarry;
	    lblMonthlyReservesOwnerCarry = new Label(8, 19, "Monthly", timesBold);
	    excelSheet.addCell(lblMonthlyReservesOwnerCarry);

	    Label lblSixMonthReservesOwnerCarry;
	    lblSixMonthReservesOwnerCarry = new Label(9, 19, "6 Months", timesBold);
	    excelSheet.addCell(lblSixMonthReservesOwnerCarry);

	    Label lblNineMonthReservesOwnerCarry;
	    lblNineMonthReservesOwnerCarry = new Label(10, 19, "9 Months", timesBold);
	    excelSheet.addCell(lblNineMonthReservesOwnerCarry);
	    
	    Label lblTwelveMonthReservesOwnerCarry;
	    lblTwelveMonthReservesOwnerCarry = new Label(11, 19, "12 Months", timesBold);
	    excelSheet.addCell(lblTwelveMonthReservesOwnerCarry);

	    Label lblMortgageReservesOwnerCarry;
	    lblMortgageReservesOwnerCarry = new Label(7, 20, "Mortgage", timesBold);
	    excelSheet.addCell(lblMortgageReservesOwnerCarry);

	    buf = new StringBuffer();
	    buf.append("(K17)");
	    Formula forMortgageMonthOC = new Formula(8, 20, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMortgageMonthOC);

	    buf = new StringBuffer();
	    buf.append("SUM(K17*6)");
	    Formula forMortgageSixMonthOC = new Formula(9, 20, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMortgageSixMonthOC);

	    buf = new StringBuffer();
	    buf.append("SUM(K17*9)");
	    Formula forMortgageNineMonthOC = new Formula(10, 20, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMortgageNineMonthOC);

	    buf = new StringBuffer();
	    buf.append("SUM(K13*K11)");
	    Formula forMortgageAnnualOC = new Formula(11, 20, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMortgageAnnualOC);

	    Label lblTaxesReservesOwnerCarry;
	    lblTaxesReservesOwnerCarry = new Label(7, 22, "Taxes", timesBold);
	    excelSheet.addCell(lblTaxesReservesOwnerCarry);

	    buf = new StringBuffer();
	    buf.append("(J23/6)");
	    Formula forTaxesMonthOC = new Formula(8, 22, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTaxesMonthOC);

	    Number nbrTaxesOC = new Number(9, 22, rsR.getTaxes(), wcfDollar);
	    excelSheet.addCell(nbrTaxesOC);

	    buf = new StringBuffer();
	    buf.append("SUM(J23*9/6)");
	    Formula forTaxesNineMonthOC = new Formula(10, 22, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTaxesNineMonthOC);

	    buf = new StringBuffer();
	    buf.append("SUM(J23*2)");
	    Formula forTaxesAnnualOC = new Formula(11, 22, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTaxesAnnualOC);

	    Label lblInsuranceReservesOwnerCarry;
	    lblInsuranceReservesOwnerCarry = new Label(7, 24, "Insurance", timesBold);
	    excelSheet.addCell(lblInsuranceReservesOwnerCarry);

	    buf = new StringBuffer();
	    buf.append("(J25/6)");
	    Formula forInsuranceMonthOC = new Formula(8, 24, buf.toString(), wcfDollar);
	    excelSheet.addCell(forInsuranceMonthOC);

	    Number nbrInsuranceOC = new Number(9, 24, rsR.getInsurance(), wcfDollar);
	    excelSheet.addCell(nbrInsuranceOC);

	    buf = new StringBuffer();
	    buf.append("SUM(J25*9/6)");
	    Formula forInsuranceNineMonthOC = new Formula(10, 24, buf.toString(), wcfDollar);
	    excelSheet.addCell(forInsuranceNineMonthOC);

	    buf = new StringBuffer();
	    buf.append("SUM(J25*2)");
	    Formula forInsuranceAnnualOC = new Formula(11, 24, buf.toString(), wcfDollar);
	    excelSheet.addCell(forInsuranceAnnualOC);

	    Label lblWaterReservesOwnerCarry;
	    lblWaterReservesOwnerCarry = new Label(7, 26, "Water", timesBold);
	    excelSheet.addCell(lblWaterReservesOwnerCarry);

	    buf = new StringBuffer();
	    buf.append("(J27/6)");
	    Formula forWaterMonthOC = new Formula(8, 26, buf.toString(), wcfDollar);
	    excelSheet.addCell(forWaterMonthOC);

	    Number nbrWaterOC = new Number(9, 26, rsR.getWater(), wcfDollar);
	    excelSheet.addCell(nbrWaterOC);

	    buf = new StringBuffer();
	    buf.append("SUM(J27*9/6)");
	    Formula forWaterNineMonthOC = new Formula(10, 26, buf.toString(), wcfDollar);
	    excelSheet.addCell(forWaterNineMonthOC);

	    buf = new StringBuffer();
	    buf.append("SUM(J27*2)");
	    Formula forWaterAnnualOC = new Formula(11, 26, buf.toString(), wcfDollar);
	    excelSheet.addCell(forWaterAnnualOC);

	    Label lblGasReservesOwnerCarry;
	    lblGasReservesOwnerCarry = new Label(7, 28, "Gas", timesBold);
	    excelSheet.addCell(lblGasReservesOwnerCarry);

	    buf = new StringBuffer();
	    buf.append("(J29/6)");
	    Formula forGasMonthOC = new Formula(8, 28, buf.toString(), wcfDollar);
	    excelSheet.addCell(forGasMonthOC);

	    Number nbrGasOC = new Number(9, 28, rsR.getGas(), wcfDollar);
	    excelSheet.addCell(nbrGasOC);

	    buf = new StringBuffer();
	    buf.append("SUM(J29*9/6)");
	    Formula forGasNineMonthOC = new Formula(10, 28, buf.toString(), wcfDollar);
	    excelSheet.addCell(forGasNineMonthOC);

	    buf = new StringBuffer();
	    buf.append("SUM(J29*2)");
	    Formula forGasAnnualOC = new Formula(11, 28, buf.toString(), wcfDollar);
	    excelSheet.addCell(forGasAnnualOC);

	    Label lblElectricReservesOwnerCarry;
	    lblElectricReservesOwnerCarry = new Label(7, 30, "Electric", timesBold);
	    excelSheet.addCell(lblElectricReservesOwnerCarry);

	    buf = new StringBuffer();
	    buf.append("(J31/6)");
	    Formula forElectricMonthOC = new Formula(8, 30, buf.toString(), wcfDollar);
	    excelSheet.addCell(forElectricMonthOC);

	    Number nbrElectricOC = new Number(9, 30, rsR.getElectric(), wcfDollar);
	    excelSheet.addCell(nbrElectricOC);

	    buf = new StringBuffer();
	    buf.append("SUM(J31*9/6)");
	    Formula forElectricNineMonthOC = new Formula(10, 30, buf.toString(), wcfDollar);
	    excelSheet.addCell(forElectricNineMonthOC);

	    buf = new StringBuffer();
	    buf.append("SUM(J31*2)");
	    Formula forElectricAnnualOC = new Formula(11, 30, buf.toString(), wcfDollar);
	    excelSheet.addCell(forElectricAnnualOC);

	    Label lblTotalReservesOwnerCarry;
	    lblTotalReservesOwnerCarry = new Label(7, 32, "Total Expenses", timesBold);
	    excelSheet.addCell(lblTotalReservesOwnerCarry);

	    buf = new StringBuffer();
	    buf.append("SUM(I21:I31)");
	    Formula forTotalMonthOC = new Formula(8, 32, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalMonthOC);

	    buf = new StringBuffer();
	    buf.append("SUM(J21:J31)");
	    Formula forTotalSixMonthOC = new Formula(9, 32, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalSixMonthOC);

	    buf = new StringBuffer();
	    buf.append("SUM(K21:K31)");
	    Formula forTotalNineMonthOC = new Formula(10, 32, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalNineMonthOC);

	    buf = new StringBuffer();
	    buf.append("SUM(L21:L31)");
	    Formula forTotalAnnualOC = new Formula(11, 32, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalAnnualOC);

	    // location info - finance rehab
	    Label lblPropAddressFinRehab;
	    lblPropAddressFinRehab = new Label(13, 1, "Property Address:", timesBold);
	    excelSheet.addCell(lblPropAddressFinRehab);
	    excelSheet.mergeCells(14, 1, 17, 1);
	    Label lblStreetAddressFR = new Label(14, 1, locL.getAddress(), timesBold);
	    excelSheet.addCell(lblStreetAddressFR);

	    Label lblCityFinRehab;
	    lblCityFinRehab = new Label(13, 2, "City:", timesBold);
	    excelSheet.addCell(lblCityFinRehab);
	    Label lblCityFR = new Label(14, 2, locL.getCity(), timesBold);
	    excelSheet.addCell(lblCityFR);

	    Label lblStFinRehab;
	    lblStFinRehab = new Label(13, 3, "State:", timesBold);
	    excelSheet.addCell(lblStFinRehab);
	    Label lblStFR = new Label(14, 3, locL.getState(), timesBold);
	    excelSheet.addCell(lblStFR);

	    Label lblZipFinRehab;
	    lblZipFinRehab = new Label(13, 4, "ZIP:", timesBold);
	    excelSheet.addCell(lblZipFinRehab);
	    Label lblZipFR = new Label(14, 4, locL.getZIPCode(), timesBold);
	    excelSheet.addCell(lblZipFR);

	    Label lblSquareFootageFinRehab;
	    lblSquareFootageFinRehab = new Label(15, 2, "Square Footage:", timesBold);
	    excelSheet.addCell(lblSquareFootageFinRehab);
	    excelSheet.mergeCells(16, 2, 17, 2);
	    Label lblSquareFootageFR = new Label(16, 2, locL.getSquareFootage() + "", timesBold);
	    excelSheet.addCell(lblSquareFootageFR);

	    Label lblBRFinRehab;
	    lblBRFinRehab = new Label(15, 3, "BR:", timesBold);
	    excelSheet.addCell(lblBRFinRehab);
	    excelSheet.mergeCells(16, 3, 17, 3);
	    Label lblBRFR = new Label(16, 3, locL.getBedrooms() + "", timesBold);
	    excelSheet.addCell(lblBRFR);

	    Label lblBAFinRehab;
	    lblBAFinRehab = new Label(15, 4, "BA:", timesBold);
	    excelSheet.addCell(lblBAFinRehab);
	    excelSheet.mergeCells(16, 4, 17, 4);
	    Label lblBAFR = new Label(16, 4, locL.getBathrooms() + "", timesBold);
	    excelSheet.addCell(lblBAFR);

	    // sales info - finance rehab
	    Label lblSalesPriceFinRehab;
	    lblSalesPriceFinRehab = new Label(13, 8, "Sales Price:", timesBold);
	    excelSheet.addCell(lblSalesPriceFinRehab);
	    Number nbrSalesPriceFR = new Number(14, 8, smSM.getSalesPrice(), wcfDollar);
	    excelSheet.addCell(nbrSalesPriceFR);

	    Label lblPercentDownFinRehab;
	    lblPercentDownFinRehab = new Label(13, 10, "Percent Down:", timesBold);
	    excelSheet.addCell(lblPercentDownFinRehab);
	    Number nbrPercentDownFR = new Number(14, 10, (smSM.getPercentDown()/100), wcfPercent);
	    excelSheet.addCell(nbrPercentDownFR);

	    Label lblOfferBidFinRehab;
	    lblOfferBidFinRehab = new Label(13, 12, "Offer/Bid Price:", timesBold);
	    excelSheet.addCell(lblOfferBidFinRehab);
	    Number nbrOfferBidFR = new Number(14, 12, smSM.getOfferBid(), wcfDollar);
	    excelSheet.addCell(nbrOfferBidFR);

	    Label lblRehabFinRehab;
	    lblRehabFinRehab = new Label(13, 14, "Rehab Budget:", timesBold);
	    excelSheet.addCell(lblRehabFinRehab);
	    Number nbrRehabFR = new Number(14, 14, rR.getBudget(), wcfDollar);
	    excelSheet.addCell(nbrRehabFR);

	    // mortgage info - finance rehab
	    Label lblDownPaymentFinRehab;
	    lblDownPaymentFinRehab = new Label(15, 8, "Down Payment:", timesBold);
	    excelSheet.addCell(lblDownPaymentFinRehab);
	    buf = new StringBuffer();
	    buf.append("SUM(O11*O9)");
	    Formula forDownPaymentFR = new Formula(16, 8, buf.toString(), wcfDollar);
	    excelSheet.addCell(forDownPaymentFR);

	    Label lblLoanAmountFinRehab;
	    lblLoanAmountFinRehab = new Label(15, 10, "Loan Amount:", timesBold);
	    excelSheet.addCell(lblLoanAmountFinRehab);
	    buf = new StringBuffer();
	    buf.append("(O13+O15-Q9)");
	    Formula forLoanAmountFR = new Formula(16, 10, buf.toString(), wcfDollar);
	    excelSheet.addCell(forLoanAmountFR);

	    Label lblInterestRateFinRehab;
	    lblInterestRateFinRehab = new Label(15, 12, "Interest Rate:", timesBold);
	    excelSheet.addCell(lblInterestRateFinRehab);
	    Number nbrInterestRateFR = new Number(16, 12, (smSM.getInterestRate()/100), wcfPercentTwoPlaces);
	    excelSheet.addCell(nbrInterestRateFR);

	    Label lblTermFinRehab;
	    lblTermFinRehab = new Label(15, 14, "Term:", timesBold);
	    excelSheet.addCell(lblTermFinRehab);
	    Number nbrTermFR = new Number(16, 14, smSM.getTerm());
	    excelSheet.addCell(nbrTermFR);

	    Label lblMonthlyPmtFinRehab;
	    lblMonthlyPmtFinRehab = new Label(15, 16, "Monthly Pmt:", timesBold);
	    excelSheet.addCell(lblMonthlyPmtFinRehab);
	    buf = new StringBuffer();
	    buf.append("SUM(Q13*Q11/12)");
	    Formula forMonthlyPmtFR = new Formula(16, 16, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMonthlyPmtFR);

	    // reserves info - finance rehab
	    Label lblMonthlyReservesFinRehab;
	    lblMonthlyReservesFinRehab = new Label(14, 19, "Monthly", timesBold);
	    excelSheet.addCell(lblMonthlyReservesFinRehab);

	    Label lblSixMonthReservesFinRehab;
	    lblSixMonthReservesFinRehab = new Label(15, 19, "6 Months", timesBold);
	    excelSheet.addCell(lblSixMonthReservesFinRehab);

	    Label lblNineMonthReservesFinRehab;
	    lblNineMonthReservesFinRehab = new Label(16, 19, "9 Months", timesBold);
	    excelSheet.addCell(lblNineMonthReservesFinRehab);
	    
	    Label lblTwelveMonthReservesFinRehab;
	    lblTwelveMonthReservesFinRehab = new Label(17, 19, "12 Months", timesBold);
	    excelSheet.addCell(lblTwelveMonthReservesFinRehab);

	    Label lblMortgageReservesFinRehab;
	    lblMortgageReservesFinRehab = new Label(13, 20, "Mortgage", timesBold);
	    excelSheet.addCell(lblMortgageReservesFinRehab);

	    buf = new StringBuffer();
	    buf.append("(Q17)");
	    Formula forMortgageMonthFR = new Formula(14, 20, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMortgageMonthFR);

	    buf = new StringBuffer();
	    buf.append("SUM(Q17*6)");
	    Formula forMortgageSixMonthFR = new Formula(15, 20, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMortgageSixMonthFR);

	    buf = new StringBuffer();
	    buf.append("SUM(Q17*9)");
	    Formula forMortgageNineMonthFR = new Formula(16, 20, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMortgageNineMonthFR);

	    buf = new StringBuffer();
	    buf.append("SUM(Q13*Q11)");
	    Formula forMortgageAnnualFR = new Formula(17, 20, buf.toString(), wcfDollar);
	    excelSheet.addCell(forMortgageAnnualFR);

	    Label lblTaxesReservesFinRehab;
	    lblTaxesReservesFinRehab = new Label(13, 22, "Taxes", timesBold);
	    excelSheet.addCell(lblTaxesReservesFinRehab);

	    buf = new StringBuffer();
	    buf.append("(P23/6)");
	    Formula forTaxesMonthFR = new Formula(14, 22, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTaxesMonthFR);

	    Number nbrTaxesFR = new Number(15, 22, rsR.getTaxes(), wcfDollar);
	    excelSheet.addCell(nbrTaxesFR);

	    buf = new StringBuffer();
	    buf.append("SUM(P23*9/6)");
	    Formula forTaxesNineMonthFR = new Formula(16, 22, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTaxesNineMonthFR);

	    buf = new StringBuffer();
	    buf.append("SUM(P23*2)");
	    Formula forTaxesAnnualFR = new Formula(17, 22, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTaxesAnnualFR);

	    Label lblInsuranceReservesFinRehab;
	    lblInsuranceReservesFinRehab = new Label(13, 24, "Insurance", timesBold);
	    excelSheet.addCell(lblInsuranceReservesFinRehab);

	    buf = new StringBuffer();
	    buf.append("(P25/6)");
	    Formula forInsuranceMonthFR = new Formula(14, 24, buf.toString(), wcfDollar);
	    excelSheet.addCell(forInsuranceMonthFR);

	    Number nbrInsuranceFR = new Number(15, 24, rsR.getInsurance(), wcfDollar);
	    excelSheet.addCell(nbrInsuranceFR);

	    buf = new StringBuffer();
	    buf.append("SUM(P25*9/6)");
	    Formula forInsuranceNineMonthFR = new Formula(16, 24, buf.toString(), wcfDollar);
	    excelSheet.addCell(forInsuranceNineMonthFR);

	    buf = new StringBuffer();
	    buf.append("SUM(P25*2)");
	    Formula forInsuranceAnnualFR = new Formula(17, 24, buf.toString(), wcfDollar);
	    excelSheet.addCell(forInsuranceAnnualFR);

	    Label lblWaterReservesFinRehab;
	    lblWaterReservesFinRehab = new Label(13, 26, "Water", timesBold);
	    excelSheet.addCell(lblWaterReservesFinRehab);

	    buf = new StringBuffer();
	    buf.append("(P27/6)");
	    Formula forWaterMonthFR = new Formula(14, 26, buf.toString(), wcfDollar);
	    excelSheet.addCell(forWaterMonthFR);

	    Number nbrWaterFR = new Number(15, 26, rsR.getWater(), wcfDollar);
	    excelSheet.addCell(nbrWaterFR);

	    buf = new StringBuffer();
	    buf.append("SUM(P27*9/6)");
	    Formula forWaterNineMonthFR = new Formula(16, 26, buf.toString(), wcfDollar);
	    excelSheet.addCell(forWaterNineMonthFR);

	    buf = new StringBuffer();
	    buf.append("SUM(P27*2)");
	    Formula forWaterAnnualFR = new Formula(17, 26, buf.toString(), wcfDollar);
	    excelSheet.addCell(forWaterAnnualFR);

	    Label lblGasReservesFinRehab;
	    lblGasReservesFinRehab = new Label(13, 28, "Gas", timesBold);
	    excelSheet.addCell(lblGasReservesFinRehab);

	    buf = new StringBuffer();
	    buf.append("(P29/6)");
	    Formula forGasMonthFR = new Formula(14, 28, buf.toString(), wcfDollar);
	    excelSheet.addCell(forGasMonthFR);

	    Number nbrGasFR = new Number(15, 28, rsR.getGas(), wcfDollar);
	    excelSheet.addCell(nbrGasFR);

	    buf = new StringBuffer();
	    buf.append("SUM(P29*9/6)");
	    Formula forGasNineMonthFR = new Formula(16, 28, buf.toString(), wcfDollar);
	    excelSheet.addCell(forGasNineMonthFR);

	    buf = new StringBuffer();
	    buf.append("SUM(P29*2)");
	    Formula forGasAnnualFR = new Formula(17, 28, buf.toString(), wcfDollar);
	    excelSheet.addCell(forGasAnnualFR);

	    Label lblElectricReservesFinRehab;
	    lblElectricReservesFinRehab = new Label(13, 30, "Electric", timesBold);
	    excelSheet.addCell(lblElectricReservesFinRehab);

	    buf = new StringBuffer();
	    buf.append("(P31/6)");
	    Formula forElectricMonthFR = new Formula(14, 30, buf.toString(), wcfDollar);
	    excelSheet.addCell(forElectricMonthFR);

	    Number nbrElectricFR = new Number(15, 30, rsR.getElectric(), wcfDollar);
	    excelSheet.addCell(nbrElectricFR);

	    buf = new StringBuffer();
	    buf.append("SUM(P31*9/6)");
	    Formula forElectricNineMonthFR = new Formula(16, 30, buf.toString(), wcfDollar);
	    excelSheet.addCell(forElectricNineMonthFR);

	    buf = new StringBuffer();
	    buf.append("SUM(P31*2)");
	    Formula forElectricAnnualFR = new Formula(17, 30, buf.toString(), wcfDollar);
	    excelSheet.addCell(forElectricAnnualFR);

	    Label lblTotalReservesFinRehab;
	    lblTotalReservesFinRehab = new Label(13, 32, "Total Expenses", timesBold);
	    excelSheet.addCell(lblTotalReservesFinRehab);

	    buf = new StringBuffer();
	    buf.append("SUM(O21:O31)");
	    Formula forTotalMonthFR = new Formula(14, 32, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalMonthFR);

	    buf = new StringBuffer();
	    buf.append("SUM(P21:P31)");
	    Formula forTotalSixMonthFR = new Formula(15, 32, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalSixMonthFR);

	    buf = new StringBuffer();
	    buf.append("SUM(Q21:Q31)");
	    Formula forTotalNineMonthFR = new Formula(16, 32, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalNineMonthFR);

	    buf = new StringBuffer();
	    buf.append("SUM(R21:R31)");
	    Formula forTotalAnnualFR = new Formula(17, 32, buf.toString(), wcfDollar);
	    excelSheet.addCell(forTotalAnnualFR);
	    //createContent(excelSheet);

	    workbook.write();
	    workbook.close();		
	}
	
	public void saveFile(View view) throws FileNotFoundException, IOException, WriteException {
		// saves results to text file
		File myDir = new File(getApplicationContext().getExternalFilesDir(null) + "/FlipulatorPremium");
	    myDir.mkdirs();
	    String strFileName = locL.getAddress() + " " + locL.getCity() + " " + locL.getState() + " " + locL.getZIPCode() + ".txt";
	    String strFileNameXls = locL.getAddress() + " " + locL.getCity() + " " + locL.getState() + " " + locL.getZIPCode() + ".xls";
		File file = new File(myDir, strFileName);
		
		// create Excel spreadsheet
		createSpreadsheet(myDir, strFileNameXls);

		String strMessage = "Rehab Type:" + setS.getRehab() + ":";
		strMessage += "Finance Type:" + setS.getFinance() + ":";
	    strMessage += "Address:" + locL.getAddress() + ":";
		strMessage += "City:" + locL.getCity() + ":";
		strMessage += "State:" + locL.getState() + ":";
		strMessage += "ZIP Code:" + locL.getZIPCode() + ":";
		strMessage += "Square Footage:" + locL.getSquareFootage() + ":";
		strMessage += "Bedrooms:" + locL.getBedrooms() + ":";
		strMessage += "Bathrooms:" + locL.getBathrooms() + ":";
		strMessage += "Sale Price:" + String.format("%.0f", smSM.getSalesPrice()) + ":";
		strMessage += "Percent Down %:" + String.format("%.0f", smSM.getPercentDown()) + ":";
		strMessage += "Offer/Bid Price:" + String.format("%.0f", smSM.getOfferBid()) + ":";
		strMessage += "Rehab Budget:" + String.format("%.0f", rR.getBudget()) + ":";
		strMessage += "Down Payment:" + String.format("%.0f", smSM.getDownPayment()) + ":";
		strMessage += "Loan Amount:" + String.format("%.0f", smSM.getLoanAmount()) + ":";
		strMessage += "Interest Rate %:" + String.format("%.0f", smSM.getInterestRate()) + ":";
		strMessage += "Term (months):" + smSM.getTerm() + ":";
		strMessage += "Monthly Pmt:" + String.format("%.0f", smSM.getMonthlyPmt()) + ":";
		strMessage += "Mortgage:" + String.format("%.0f", rsR.getMortgage()) + ":";
		strMessage += "Property Taxes:" + String.format("%.0f", rsR.getTaxes()) + ":";
		strMessage += "Insurance:" + String.format("%.0f", rsR.getInsurance()) + ":";
		strMessage += "Electric:" +String.format("%.0f", rsR.getElectric()) + ":";
		strMessage += "Water:" + String.format("%.0f", rsR.getWater()) + ":";
		strMessage += "Gas:" + String.format("%.0f", rsR.getGas()) + ":";
		strMessage += "Total Reserves:" + String.format("%.0f", rsR.getTotalExpenses()) + ":";
		strMessage += "Real Estate Comm:" + String.format("%.0f", frF.getRECost()) + ":";
		strMessage += "Commission %:" + String.format("%.0f", cemC.getRealEstComm()) + ":";
		strMessage += "Buyer Clos Cost:" + String.format("%.0f", frF.getBCCost()) + ":";
		strMessage += "Closing Cost %:" +String.format("%.0f", cemC.getBuyClosCost()) + ":";
		strMessage += "Sell Clos Cost:" + String.format("%.0f", frF.getSCCost()) + ":";
		strMessage += "Closing Cost %:" + String.format("%.0f",  cemC.getSellClosCost()) + ":";
		strMessage += "Total Costs:" + String.format("%.0f", frF.getTotalCost()) + ":";
		strMessage += "Out of Pocket Exp:" + String.format("%.0f", frF.getOOPExp()) + ":";
		strMessage += "FMV/ARV:" + String.format("%.0f", cemC.getFMVARV()) + ":";
		strMessage += "Comparables:" + String.format("%.0f", cemC.getComparables()) + ":";
		strMessage += "Selling Price:" + String.format("%.0f", cemC.getSellingPrice()) + ":";
		strMessage += "Buy + Costs:" + String.format("%.0f", frF.getTotalCost()) + ":";
		strMessage += "Gross Profit:" + String.format("%.0f", frF.getGrossProfit()) + ":";
		strMessage += "Capital Gains:" + String.format("%.0f",  frF.getCapGains()) + ":";
		strMessage += "Net Profit:" + String.format("%.0f", frF.getNetProfit()) + ":";
		strMessage += "Money Out:" + String.format("%.0f", frF.getOOPExp()) + ":";
		strMessage += "Money In:" + String.format("%.0f", frF.getNetProfit()) + ":";
		strMessage += "% Return:" + String.format("%.1f", frF.getROI()) + ":";
		strMessage += "Cash on Cash Return:" + String.format("%.1f", frF.getCashOnCash()) + ":";
		
		FileOutputStream stream = new FileOutputStream(file);
		try {
		    stream.write(strMessage.getBytes());
		} finally {
			String strSavedFile = "File saved as: " + strFileName;
			Toast.makeText(getApplicationContext(), strSavedFile, Toast.LENGTH_SHORT).show();
			stream.close();
		}
	}

	 public boolean onKeyDown(int nKeyCode, KeyEvent keEvent) {
		String strBackMessage = "Press Previous to make changes, Save to save info to file, ";
		strBackMessage += "Main Menu to return to main menu or Email Results to email.";
		if (nKeyCode == KeyEvent.KEYCODE_BACK) {
			Toast.makeText(getApplicationContext(), strBackMessage, Toast.LENGTH_SHORT).show();
		    return true;
		}
		return super.onKeyDown(nKeyCode, keEvent);
     }

}

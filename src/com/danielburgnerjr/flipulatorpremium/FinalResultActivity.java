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

import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
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
	private WritableCellFormat timesBoldUnderline;
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

	public void saveFile(View view) throws FileNotFoundException, IOException, WriteException {
		// saves results to text file
		File myDir = new File(getApplicationContext().getExternalFilesDir(null) + "/FlipulatorPremium");
	    myDir.mkdirs();
	    String strFileName = locL.getAddress() + " " + locL.getCity() + " " + locL.getState() + " " + locL.getZIPCode() + ".txt";
	    String strFileNameXls = locL.getAddress() + " " + locL.getCity() + " " + locL.getState() + " " + locL.getZIPCode() + ".xls";
		File file = new File(myDir, strFileName);
		File fileXls = new File(myDir, strFileNameXls);
		
	    WorkbookSettings wbSettings = new WorkbookSettings();

	    wbSettings.setLocale(new Locale("en", "EN"));

	    WritableWorkbook workbook = Workbook.createWorkbook(fileXls, wbSettings);
	    workbook.createSheet(locL.getAddress() + " " + locL.getCity() + " " + locL.getState() + " " + locL.getZIPCode(), 0);
	    WritableSheet excelSheet = workbook.getSheet(0);

	    // Lets create a times font
	    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
	    // Define the cell format
	    times = new WritableCellFormat(times10pt);
	    // Lets automatically wrap the cells
	    times.setWrap(true);

	    // create create a bold font with underlines
	    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
	        UnderlineStyle.SINGLE);
	    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
	    // Lets automatically wrap the cells
	    timesBoldUnderline.setWrap(true);

	    CellView cv = new CellView();
	    cv.setFormat(times);
	    cv.setFormat(timesBoldUnderline);
	    cv.setAutosize(true);

	    // Write a few headers
	    Label label;
	    label = new Label(5, 0, "Original", times);
	    excelSheet.addCell(label);

	    //createContent(excelSheet);

	    workbook.write();
	    workbook.close();

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

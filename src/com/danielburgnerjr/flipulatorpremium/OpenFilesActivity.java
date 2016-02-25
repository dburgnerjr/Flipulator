package com.danielburgnerjr.flipulatorpremium;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.text.NumberFormat;
import java.text.ParseException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class OpenFilesActivity extends Activity {
	
	ListView lvView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.openfiles);

		// get the files and place it in a file array
		File myDir = new File(getApplicationContext().getExternalFilesDir(null) + "/FlipulatorPremium");
		String strPath = myDir.getPath();
		File fFileList = new File(strPath);
		File fFileArray[] = fFileList.listFiles();
		List<String> arrayList = new ArrayList<String>();
		
		// Get ListView object from xml
        lvView = (ListView) findViewById(R.id.filesList);
        
        // Defined Array values to show in ListView
        for (int nI = 0; nI < fFileArray.length; nI++) {
        	arrayList.add(fFileArray[nI].getName().substring(0, fFileArray[nI].getName().length()-4));
        }
        
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Fourth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);


        // Assign adapter to ListView
        lvView.setAdapter(adapter); 
        
        // ListView Item Click Listener
        lvView.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
                
               // ListView Clicked item index
               int itemPosition = position;
               
               // ListView Clicked item value
               String itemValue = (String) lvView.getItemAtPosition(itemPosition);

               // Get file and open it
               //Toast.makeText(getApplicationContext(), itemValue, Toast.LENGTH_SHORT).show();
               Intent newActivity;
               Settings setS = null;
               Location locL = null;
               SalesMortgage smSM = null;
               Rehab rR = null;
               Reserves resR = null;
               ClosExpPropMktInfo cemC = null;
               try {
            	   File fPath = new File(getApplicationContext().getExternalFilesDir(null) + "/FlipulatorPremium");
            	   String strFilePathXLS = fPath.getPath() + "/" + itemValue + ".xls";
	               try {
	            	   Workbook wbExcelFile = Workbook.getWorkbook(new File(strFilePathXLS));
	            	   Sheet shWorkSheet = wbExcelFile.getSheet(0);
	            	   
	            	   // Settings
	            	   Cell celRehabType = shWorkSheet.getCell(1, 64);
	            	   Cell celFinanceType = shWorkSheet.getCell(3, 64);
	            	   
	            	   String strRehabType = celRehabType.getContents();
	            	   String strFinanceType = celFinanceType.getContents();
	            	   setS = new Settings(Integer.parseInt(strRehabType), Integer.parseInt(strFinanceType));
	            	   
	            	   // Location
	            	   Cell celAddress = shWorkSheet.getCell(1, 1);
	            	   Cell celCity = shWorkSheet.getCell(1, 2);
	            	   Cell celState = shWorkSheet.getCell(1, 3);
	            	   Cell celZipCode = shWorkSheet.getCell(1, 4);
	            	   Cell celSqFootage = shWorkSheet.getCell(3, 2);
	            	   Cell celBedrooms = shWorkSheet.getCell(3, 3);
	            	   Cell celBathrooms = shWorkSheet.getCell(3, 4);
	            	   
	            	   String strAddress = celAddress.getContents();
	            	   String strCity = celCity.getContents();
	            	   String strState = celState.getContents();
	            	   String strZipCode = celZipCode.getContents();
	            	   int nSqFootage = Integer.parseInt(celSqFootage.getContents());
	            	   int nBedrooms = Integer.parseInt(celBedrooms.getContents());
	            	   double dBathrooms = Double.parseDouble(celBathrooms.getContents());
	            	   locL = new Location(strAddress, strCity, strState, strZipCode, nSqFootage, nBedrooms, dBathrooms);

	            	   // Sales/Mortgage
	            	   Cell celSalePrice = shWorkSheet.getCell(1, 8);
	            	   Cell celPercentDown = shWorkSheet.getCell(1, 10);
	            	   Cell celOfferBid = shWorkSheet.getCell(1, 12);
	            	   Cell celDownPayment = shWorkSheet.getCell(3, 8);
	            	   Cell celLoanAmount = shWorkSheet.getCell(3, 10);
	            	   Cell celInterestRate = shWorkSheet.getCell(3, 12);
	            	   Cell celTerm = shWorkSheet.getCell(3, 14);
	            	   Cell celMonthlyPmt = shWorkSheet.getCell(3, 16);
	            	   
	            	   Locale us = new Locale("en", "US");
	            	   NumberFormat nbfDollar = NumberFormat.getCurrencyInstance(us);
	            	   NumberFormat nbfPercent = NumberFormat.getPercentInstance(us);
	            	   
	            	   Number numSalePrice = nbfDollar.parse(celSalePrice.getContents());
	            	   double dSalePrice = numSalePrice.doubleValue();
	            	   Number numPercentDown = nbfPercent.parse(celPercentDown.getContents());
	            	   double dPercentDown = (Double)numPercentDown * 100.0;
	            	   Number numOfferBid = nbfDollar.parse(celOfferBid.getContents());
	            	   double dOfferBid = numOfferBid.doubleValue();
	            	   Number numDownPayment = nbfDollar.parse(celDownPayment.getContents());
	            	   double dDownPayment = numDownPayment.doubleValue();
	            	   Number numLoanAmount = nbfDollar.parse(celLoanAmount.getContents());
	            	   double dLoanAmount = numLoanAmount.doubleValue();
	            	   Number numInterestRate = nbfPercent.parse(celInterestRate.getContents());
	            	   double dInterestRate = (Double)numInterestRate * 100.0;
	            	   int nTerm = Integer.parseInt(celTerm.getContents());
	            	   Number numMonthlyPmt = nbfDollar.parse(celMonthlyPmt.getContents());
	            	   double dMonthlyPmt = numMonthlyPmt.doubleValue();
	            	   
	            	   smSM = new SalesMortgage(dSalePrice, dPercentDown, dOfferBid, dDownPayment,
	            			   					dLoanAmount, dInterestRate, nTerm, dMonthlyPmt);
	            	   
	            	   // Rehab
	            	   Cell celRehabBudget = shWorkSheet.getCell(1, 14);
	            	   Cell celBudgetItems = shWorkSheet.getCell(1, 65);
	            	   
	            	   Number numRehabBudget = nbfDollar.parse(celRehabBudget.getContents());
	            	   double dRehabBudget = numRehabBudget.doubleValue();
	            	   String strBudgetItems = celBudgetItems.getContents();
	            	   if (setS.getRehab() == 0) {
	            		   rR = new RehabFlatRate(dRehabBudget, strBudgetItems);
	            	   }
	            	   if (setS.getRehab() == 1) {
		       				int nCostSF = (int)(dRehabBudget/locL.getSquareFootage());
		       				String strType = "";
		    	      	  	switch (nCostSF) {
		    	      	  		case 15:
		    	      	  					strType = "Low";
		    	      	  					break;
		    	      	  		case 20:
		    	      	  		case 25:
		    	      	  					strType = "Medium";
		    		    	  				break;
		    	      	  		case 30:
		    	      	  					strType = "High";
		    		    	  				break;
		    	      	  		case 40:
		    	      	  					strType = "Super-High";
		    		    	  				break;
		    	      	  		case 125:
		    	      	  					strType = "Bulldozer";
		    		    	  				break;
		    	      	    }

	            		   rR = new RehabType(locL.getSquareFootage(), strType, strBudgetItems);
	            	   }

	            	   // Reserves
	            	   Cell celMortgage = shWorkSheet.getCell(2, 20);
	            	   Cell celInsurance = shWorkSheet.getCell(2, 22);
	            	   Cell celTaxes = shWorkSheet.getCell(2, 24);
	            	   Cell celWater = shWorkSheet.getCell(2, 26);
	            	   Cell celGas = shWorkSheet.getCell(2, 28);
	            	   Cell celElectric = shWorkSheet.getCell(2, 30);
	            	   Cell celTotalExpense = shWorkSheet.getCell(2, 32);
	            	   
	            	   Number numMortgage = nbfDollar.parse(celMortgage.getContents());
	            	   double dMortgage = numMortgage.doubleValue();
	            	   Number numInsurance = nbfDollar.parse(celInsurance.getContents());
	            	   double dInsurance = numInsurance.doubleValue();
	            	   Number numTaxes = nbfDollar.parse(celTaxes.getContents());
	            	   double dTaxes = numTaxes.doubleValue();
	            	   Number numWater = nbfDollar.parse(celWater.getContents());
	            	   double dWater = numWater.doubleValue();
	            	   Number numGas = nbfDollar.parse(celGas.getContents());
	            	   double dGas = numGas.doubleValue();
	            	   Number numElectric = nbfDollar.parse(celElectric.getContents());
	            	   double dElectric = numElectric.doubleValue();
	            	   Number numTotalExpense = nbfDollar.parse(celTotalExpense.getContents());
	            	   double dTotalExpense = numTotalExpense.doubleValue();
	            	   
	            	   resR = new Reserves(dMortgage, dInsurance, dTaxes, dWater, dGas, dElectric, dTotalExpense);

	            	   // Closing Expenses/Market Info
	            	   Cell celRealEstComm = shWorkSheet.getCell(3, 36);
	            	   Cell celBuyClosCost = shWorkSheet.getCell(3, 38);
	            	   Cell celSellClosCost = shWorkSheet.getCell(3, 39);
	            	   Cell celFMVARV = shWorkSheet.getCell(2, 47);
	            	   Cell celComparables = shWorkSheet.getCell(2, 48);
	            	   Cell celSellingPrice = shWorkSheet.getCell(2, 49);
	            	   
	            	   Number numRealEstComm = nbfPercent.parse(celRealEstComm.getContents());
	            	   double dRealEstComm = (Double)numRealEstComm * 100.0;
	            	   Number numBuyClosCost = nbfPercent.parse(celBuyClosCost.getContents());
	            	   double dBuyClosCost = (Double)numBuyClosCost * 100.0;
	            	   Number numSellClosCost = nbfPercent.parse(celSellClosCost.getContents());
	            	   double dSellClosCost = (Double)numSellClosCost * 100.0;
	            	   Number numFMVARV = nbfDollar.parse(celFMVARV.getContents());
	            	   double dFMVARV = numFMVARV.doubleValue();
	            	   Number numComparables = nbfDollar.parse(celComparables.getContents());
	            	   double dComparables = numComparables.doubleValue();
	            	   Number numSellingPrice = nbfDollar.parse(celSellingPrice.getContents());
	            	   double dSellingPrice = numSellingPrice.doubleValue();
	            	   
	            	   cemC = new ClosExpPropMktInfo(dRealEstComm, dBuyClosCost, dSellClosCost, 
	            			   						 dFMVARV, dComparables, dSellingPrice);

	               } catch (FileNotFoundException e) {
	            	   Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_SHORT).show();
				   } catch (BiffException e) {
					   Toast.makeText(getApplicationContext(), "Biff Exception", Toast.LENGTH_SHORT).show();
	               } catch (ParseException e) {
	            	   Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
	               } 
               } catch (IOException e) {
            	   Toast.makeText(getApplicationContext(), "IO Exception", Toast.LENGTH_SHORT).show();
               } finally {
            	   newActivity = new Intent(OpenFilesActivity.this, SettingsActivity.class);
            	   newActivity.putExtra("Settings", setS);
            	   newActivity.putExtra("Location", locL);
            	   newActivity.putExtra("SalesMortgage", smSM);
            	   newActivity.putExtra("Rehab", rR);
            	   newActivity.putExtra("Reserves", resR);
            	   newActivity.putExtra("ClosExpPropMktInfo", cemC);
            	   startActivity(newActivity);
            	   finish();
               }               
            }

         }); 

	}

	 public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intI = new Intent(OpenFilesActivity.this, MainActivity.class);
		    startActivity(intI);
		    finish();
		    return true;
		}
		return super.onKeyDown(keyCode, event);
   }
}

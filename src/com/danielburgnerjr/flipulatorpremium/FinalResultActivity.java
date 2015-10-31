package com.danielburgnerjr.flipulatorpremium;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.DialogInterface;
import android.content.Intent;
 
public class FinalResultActivity extends Activity {
 
	private Settings setS;
	private Location locL;
	private SalesMortgage smSM;
	private Rehab rR;
	private Reserves rsR;
	private ClosExpPropMktInfo cemC; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalresult);
        
		Intent intI = getIntent();

		setS = (Settings) intI.getSerializableExtra("Settings");
		locL = (Location) intI.getSerializableExtra("Location");
		smSM = (SalesMortgage) intI.getSerializableExtra("SalesMortgage");
		rR = (Rehab) intI.getSerializableExtra("Rehab");
		rsR = (Reserves) intI.getSerializableExtra("Reserves");
		cemC = (ClosExpPropMktInfo) intI.getSerializableExtra("ClosExpPropMktInfo");
 
		TextView tvFRAddress = (TextView) findViewById(R.id.txtLocationAddress);
		TextView tvFRCityStZip = (TextView) findViewById(R.id.txtLocationCityStZip);
		TextView tvSF = (TextView) findViewById(R.id.txtLocationSqFootage);
		TextView tvBedBath = (TextView) findViewById(R.id.txtLocationBedBath);
		
		tvFRAddress.setText("Address:\t\t\t\t\t\t " + locL.getAddress());
		tvFRCityStZip.setText("City/State/ZIP Code:  " + locL.getCity() + ", " + locL.getState() + " " + locL.getZIPCode());
		tvSF.setText("Square Footage:\t\t\t " + locL.getSquareFootage() + "");
		tvBedBath.setText("BR/BA:\t\t\t\t\t\t\t " + locL.getBedrooms() + " BR/" + locL.getBathrooms() + " BA");


		TextView tvSalePrice = (TextView) findViewById(R.id.txtSalePrice);
		TextView tvPercentDown = (TextView) findViewById(R.id.txtPercentDown);
		TextView tvOfferBid = (TextView) findViewById(R.id.txtOfferBid);
		TextView tvRehabBudget = (TextView) findViewById(R.id.txtRehabBudget);
		
		tvSalePrice.setText("Sale Price:\t\t\t\t\t\t $" + String.format("%.0f", smSM.getSalesPrice()));
		tvPercentDown.setText("Percent Down %:\t\t\t " + String.format("%.0f", smSM.getPercentDown()));
		tvOfferBid.setText("Offer/Bid Price:\t\t\t $" + String.format("%.0f", smSM.getOfferBid()));
		tvRehabBudget.setText("Rehab Budget:\t\t\t\t $" + String.format("%.0f", rR.getBudget()));

		TextView tvDownPayment = (TextView) findViewById(R.id.txtDownPayment);
		TextView tvLoanAmount = (TextView) findViewById(R.id.txtLoanAmount);
		TextView tvInterestRate = (TextView) findViewById(R.id.txtInterestRate);
		TextView tvTerm = (TextView) findViewById(R.id.txtTerm);
		TextView tvMonthlyPmt = (TextView) findViewById(R.id.txtMonthlyPmt);
		
		tvDownPayment.setText("Down Payment:\t\t\t $" + String.format("%.0f", smSM.getDownPayment()));
		tvLoanAmount.setText("Loan Amount:\t\t\t\t $" + String.format("%.0f", smSM.getLoanAmount()));
		tvInterestRate.setText("Interest Rate %:\t\t\t " + String.format("%.0f", smSM.getInterestRate()));
		tvTerm.setText("Term (months):\t\t\t " + smSM.getTerm());
		tvMonthlyPmt.setText("Monthly Pmt:\t\t\t\t $" + String.format("%.0f", smSM.getMonthlyPmt()));

		TextView tvResMort = (TextView) findViewById(R.id.txtMortPmt);
		TextView tvResTaxes = (TextView) findViewById(R.id.txtPropertyTaxes);
		TextView tvResIns = (TextView) findViewById(R.id.txtInsurance);
		TextView tvResElect = (TextView) findViewById(R.id.txtElectric);
		TextView tvResWater = (TextView) findViewById(R.id.txtWater);
		TextView tvResGas = (TextView) findViewById(R.id.txtGas);
		TextView tvTotRes = (TextView) findViewById(R.id.txtTotalReserves);

		tvResMort.setText("Mortgage:\t\t\t\t\t\t $" + String.format("%.0f", rsR.getMortgage()));
		tvResTaxes.setText("Property Taxes:\t\t\t $" + String.format("%.0f", rsR.getTaxes()));
		tvResIns.setText("Insurance:\t\t\t\t\t\t $" + String.format("%.0f", rsR.getInsurance()));
		tvResElect.setText("Electric:\t\t\t\t\t\t\t $" +String.format("%.0f", rsR.getElectric()));
		tvResWater.setText("Water:\t\t\t\t\t\t\t $" + String.format("%.0f", rsR.getWater()));
		tvResGas.setText("Gas:\t\t\t\t\t\t\t\t $" + String.format("%.0f", rsR.getGas()));
		tvTotRes.setText("Total Reserves:\t\t\t $" + String.format("%.0f", rsR.getTotalExpenses()));

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
		String strMessage = "Address:                " + locL.getAddress() + "\n";
		strMessage += "City, State ZIP:        " + locL.getCity() + ", " + locL.getState() + " " + locL.getZIPCode() +"\n";
		strMessage += "Square Footage:         " + locL.getSquareFootage() + "\n";
		strMessage += "Bedrooms/Bathrooms:     " + locL.getBedrooms() + " BR " + locL.getBathrooms() + " BA\n";
/*		strMessage += "After Repair Value:    $" + String.format("%.0f", calC.getFMVARV()) + "\n";
		strMessage += "Sales Price:           $" + String.format("%.0f", calC.getSalesPrice()) + "\n";
		strMessage += "Estimated Budget:      $" + String.format("%.0f", calC.getBudget()) + "\n";
		strMessage += "Closing/Holding Costs: $" + String.format("%.0f", resR.getClosHoldCosts()) + "\n";
		strMessage += "Profit:                $" + String.format("%.0f", resR.getProfit()) + "\n";
		strMessage += "ROI:                    " + String.format("%.1f", resR.getROI()) + "%\n";
		strMessage += "Cash on Cash Return:    " + String.format("%.1f", resR.getCashOnCash()) + "%\n";
*/		Intent intEmailActivity = new Intent(Intent.ACTION_SEND);
		intEmailActivity.putExtra(Intent.EXTRA_EMAIL, new String[]{});
		intEmailActivity.putExtra(Intent.EXTRA_SUBJECT, "Flipulator results for: " + locL.getAddress() + " " + locL.getCity() + ", " + locL.getState() + " " + locL.getZIPCode());
		intEmailActivity.putExtra(Intent.EXTRA_TEXT, strMessage);
		intEmailActivity.setType("plain/text");
   		startActivity(intEmailActivity);
	}
	
	 public boolean onKeyDown(int nKeyCode, KeyEvent keEvent) {
		if (nKeyCode == KeyEvent.KEYCODE_BACK) {
			exitByBackKey();
		    return true;
		}
		return super.onKeyDown(nKeyCode, keEvent);
   }

	 protected void exitByBackKey() {
		AlertDialog adAlertBox = new AlertDialog.Builder(this)
		    .setMessage("Do you want to go back to main menu?")
		    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		        // do something when the button is clicked
		        public void onClick(DialogInterface arg0, int arg1) {
		        	Intent intB = new Intent(FinalResultActivity.this, MainActivity.class);
		        	startActivity(intB);
		        	finish();
		            //close();
		        }
		    })
		    .setNegativeButton("No", new DialogInterface.OnClickListener() {
		        // do something when the button is clicked
		        public void onClick(DialogInterface arg0, int arg1) {
		        }
		    })
		    .show();
	 }

}

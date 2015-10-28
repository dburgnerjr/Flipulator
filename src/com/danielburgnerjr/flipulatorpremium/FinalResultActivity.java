package com.danielburgnerjr.flipulatorpremium;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
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
}

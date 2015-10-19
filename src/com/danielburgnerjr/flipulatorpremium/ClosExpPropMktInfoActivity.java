package com.danielburgnerjr.flipulatorpremium;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class ClosExpPropMktInfoActivity extends Activity {
	
	final Context cntC = this;
	
	private Settings setS;
	private Location locL;
	private SalesMortgage smSM;
	private Rehab rR;
	private Reserves rsR;
	
	private EditText etRealEstComm;		// real estate commission
	private EditText etBuyClosCost;		// buyer's closing costs
	private EditText etSellClosCost;	// seller's closing costs
	private EditText etFMVARV;			// fair mkt value/after repair value
	private EditText etComparables;		// comparables
	private EditText etSellingPrice;	// selling price
	private Button btnHelp;				// help

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.closexppropmktinfo);
		
		Intent intI = getIntent();

		setS = (Settings) intI.getSerializableExtra("Settings");
		locL = (Location) intI.getSerializableExtra("Location");
		smSM = (SalesMortgage) intI.getSerializableExtra("SalesMortgage");
		rR = (Rehab) intI.getSerializableExtra("Rehab");
		rsR = (Reserves) intI.getSerializableExtra("Reserves");
		
		etRealEstComm   = (EditText)findViewById(R.id.txtCommission);
		etBuyClosCost   = (EditText)findViewById(R.id.txtBuyerClosCost);
		etSellClosCost   = (EditText)findViewById(R.id.txtSellClosCost);
		etFMVARV   = (EditText)findViewById(R.id.txtFMVARV);
		etComparables   = (EditText)findViewById(R.id.txtComparables);
		etSellingPrice   = (EditText)findViewById(R.id.txtSellingPrice);
		btnHelp = (Button)findViewById(R.id.txtHelp);
		
		// add button listener
		btnHelp.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cntC);
 
				// set title
				alertDialogBuilder.setTitle("Closing Expenses/Property Market Info Help");
 
				// set dialog message
				alertDialogBuilder.setMessage("Enter the real estate commission percentage, buyer and seller closing " +
											  "cost percentage, fair market value/after repair value, comparables and " +
											  "selling price.")
								  .setCancelable(false)
								  .setNeutralButton("OK", new DialogInterface.OnClickListener() {
									  public void onClick(DialogInterface dialog, int id) {
										  // if this button is clicked, close
										  // current activity
										  dialog.cancel();
									  }
								  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
			}
		});


	}

	public void prevPage(View view) {
		Intent intI = new Intent(this, ReservesActivity.class);
		intI.putExtra("Settings", setS);
		intI.putExtra("Location", locL);
		intI.putExtra("SalesMortgage", smSM);
		intI.putExtra("Rehab", rR);
		intI.putExtra("Reserves", rsR);
		startActivity(intI);
		finish();
	}

	public void nextPage(View view) {
		if (("").equals(etRealEstComm.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Real Estate Commission", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etBuyClosCost.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Buyer's Closing Cost", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etSellClosCost.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Seller's Closing Cost", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etFMVARV.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Fair Market Value or After Repair Value", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etComparables.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Comparables", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etSellingPrice.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Selling Price", Toast.LENGTH_SHORT).show();
		} else {
			Intent intI = new Intent(this, FinalResultActivity.class);
			intI.putExtra("Location", locL);
			intI.putExtra("SalesMortgage", smSM);
			intI.putExtra("Rehab", rR);
			intI.putExtra("Reserves", rsR);
	    
			ClosExpPropMktInfo cemC = new ClosExpPropMktInfo();
			cemC.setRealEstComm(Double.parseDouble(etRealEstComm.getText().toString()));
			cemC.setBuyClosCost(Double.parseDouble(etBuyClosCost.getText().toString()));
			cemC.setSellClosCost(Double.parseDouble(etSellClosCost.getText().toString()));
			cemC.setFMVARV(Double.parseDouble(etFMVARV.getText().toString()));
			cemC.setComparables(Double.parseDouble(etComparables.getText().toString()));
			cemC.setSellingPrice(Double.parseDouble(etSellingPrice.getText().toString()));
	    
			intI.putExtra("ClosExpPropMktInfo", cemC);
			startActivity(intI);
			finish();
		}
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
		        	Intent intB = new Intent(ClosExpPropMktInfoActivity.this, MainActivity.class);
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

package com.danielburgnerjr.flipulatorpremium;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class ClosExpPropMktInfoActivity extends Activity {
	
	final Context cntC = this;
	
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
			Intent intI = new Intent(this, MainActivity.class);
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
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

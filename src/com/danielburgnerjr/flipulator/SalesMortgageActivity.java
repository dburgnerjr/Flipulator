package com.danielburgnerjr.flipulator;

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

public class SalesMortgageActivity extends Activity {
	
	final Context cntC = this;
	
	private Location locL;
	private EditText etSalesPrice;		// sales price
	private EditText etPercentDown;		// percent down
	private EditText etOfferBid;		// offer/bid price
	private EditText etInterestRate;	// interest rate
	private EditText etTerm;			// term
	private Button btnHelp;				// help

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salemortgage);
		
		Intent intI = getIntent();
		
		locL = (Location) intI.getSerializableExtra("Location");
		
		etSalesPrice   = (EditText)findViewById(R.id.txtSalePrice);
		etPercentDown   = (EditText)findViewById(R.id.txtPercentDown);
		etOfferBid   = (EditText)findViewById(R.id.txtOfferBid);
		etInterestRate   = (EditText)findViewById(R.id.txtInterestRate);
		etTerm   = (EditText)findViewById(R.id.txtTerm);
		btnHelp = (Button)findViewById(R.id.txtHelp);
		
		// add button listener
		btnHelp.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cntC);
 
				// set title
				alertDialogBuilder.setTitle("Sales/Mortgage Help");
 
				// set dialog message
				alertDialogBuilder.setMessage("Enter the sales price, percentage down, offer or bid, interest rate " +
											  "and term of mortgage.")
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
		if (("").equals(etSalesPrice.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Sales Price", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etPercentDown.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Percent Down", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etOfferBid.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Offer Bid", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etInterestRate.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Interest Rate", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etTerm.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Term", Toast.LENGTH_SHORT).show();
		} else {
			Intent intI = new Intent(this, MainActivity.class);
			intI.putExtra("Location", locL);
	    
			SalesMortgage smSM = new SalesMortgage();
			smSM.setSalesPrice(Double.parseDouble(etSalesPrice.getText().toString()));
			smSM.setPercentDown(Double.parseDouble(etPercentDown.getText().toString()));
			smSM.setOfferBid(Double.parseDouble(etOfferBid.getText().toString()));
			smSM.setDownPayment(Double.parseDouble(etPercentDown.getText().toString()), Double.parseDouble(etOfferBid.getText().toString()));
	    	smSM.setLoanAmount();
	    	smSM.setInterestRate(Double.parseDouble(etInterestRate.getText().toString()));
	    	smSM.setTerm(Integer.parseInt(etTerm.getText().toString()));
	    	smSM.setMonthlyPmt();
	    
	    	intI.putExtra("SalesMortgage", smSM);

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

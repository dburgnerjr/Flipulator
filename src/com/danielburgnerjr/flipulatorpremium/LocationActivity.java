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

public class LocationActivity extends Activity {
	
	final Context cntC = this;
	private Settings setS;
	
	private EditText etAddress;			// address
	private EditText etCity;			// city
	private EditText etState;			// state
	private EditText etZIPCode;			// ZIP Code
	private EditText etSquareFootage;	// square footage
	private EditText etBedrooms;		// number of bedrooms
	private EditText etBathrooms;		// number of bathrooms
	private Button btnHelp;				// help

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);

		Intent intI = getIntent();
		
		setS = (Settings) intI.getSerializableExtra("Settings");

		etAddress   = (EditText)findViewById(R.id.txtAddress);
		etCity   = (EditText)findViewById(R.id.txtCity);
		etState   = (EditText)findViewById(R.id.txtState);
		etZIPCode   = (EditText)findViewById(R.id.txtZIP_Code);
		etSquareFootage   = (EditText)findViewById(R.id.txtSq_Footage);
		etBedrooms   = (EditText)findViewById(R.id.txtBedrooms);
		etBathrooms   = (EditText)findViewById(R.id.txtBathrooms);
		btnHelp = (Button)findViewById(R.id.txtHelp);
		
		// add button listener
		btnHelp.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cntC);
 
				// set title
				alertDialogBuilder.setTitle("Location Help");
 
				// set dialog message
				alertDialogBuilder.setMessage("Enter the address, city, state, ZIP code and square footage of the property, " +
											  "including the number of bedrooms and bathrooms.")
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
		Intent intI = new Intent(this, MainActivity.class);
		intI.putExtra("Settings", setS);
		startActivity(intI);
		finish();
	}

	public void nextPage(View view) {
		if (("").equals(etAddress.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Address", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etCity.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter City", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etState.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter State", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etZIPCode.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter ZIP Code", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etSquareFootage.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Square Footage", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etBedrooms.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Bedrooms", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etBathrooms.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Bathrooms", Toast.LENGTH_SHORT).show();
		} else {
			Intent intI = new Intent(this, MainActivity.class);
	    
			Location locL = new Location();
			locL.setAddress(etAddress.getText().toString());
			locL.setCity(etCity.getText().toString());
			locL.setState(etState.getText().toString());
			locL.setZIPCode(etZIPCode.getText().toString());
			locL.setSquareFootage(Integer.parseInt(etSquareFootage.getText().toString()));
			locL.setBedrooms(Integer.parseInt(etBedrooms.getText().toString()));
			locL.setBathrooms(Integer.parseInt(etBathrooms.getText().toString()));
	    
			intI.putExtra("Location", locL);
			intI.putExtra("Settings", setS);
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
		        	Intent intB = new Intent(LocationActivity.this, MainActivity.class);
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
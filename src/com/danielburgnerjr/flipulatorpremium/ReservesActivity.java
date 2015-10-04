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

public class ReservesActivity extends Activity {
	
	final Context cntC = this;
	
	private Location locL;
	private SalesMortgage smSM;
	private Rehab rR;
	
	private EditText etInsurance;		// sales price
	private EditText etTaxes;			// property taxes
	private EditText etElectric;		// electricity
	private EditText etGas;				// gas
	private EditText etWater;			// water
	private Button btnHelp;				// help

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reserves);
		
		Intent intI = getIntent();
		
		locL = (Location) intI.getSerializableExtra("Location");
		smSM = (SalesMortgage) intI.getSerializableExtra("SalesMortgage");
		rR = (Rehab) intI.getSerializableExtra("Rehab");

		etInsurance   = (EditText)findViewById(R.id.txtInsurance);
		etTaxes   = (EditText)findViewById(R.id.txtTaxes);
		etElectric   = (EditText)findViewById(R.id.txtElectric);
		etGas   = (EditText)findViewById(R.id.txtGas);
		etWater   = (EditText)findViewById(R.id.txtWater);
		btnHelp = (Button)findViewById(R.id.txtHelp);
		
		// add button listener
		btnHelp.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cntC);
 
				// set title
				alertDialogBuilder.setTitle("Reserves Help");
 
				// set dialog message
				alertDialogBuilder.setMessage("Enter the insurance costs, property taxes, electric, gas and water. " +
											  "Insurance and property taxes (annually) are divided by two while " + 
											  "electric, gas and water (monthly) are multiplied by six.")
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
		if (("").equals(etInsurance.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Insurance For 6 Mos", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etTaxes.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Property Taxes For 6 Mos", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etWater.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Water Bill For 6 Mos", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etGas.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Gas Bill For 6 Mos", Toast.LENGTH_SHORT).show();
		} else if (("").equals(etElectric.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Electric Bill For 6 Mos", Toast.LENGTH_SHORT).show();
		} else {
			Intent intI = new Intent(this, MainActivity.class);
			intI.putExtra("Location", locL);
			intI.putExtra("SalesMortgage", smSM);
			intI.putExtra("Rehab", rR);
	    
			Reserves rsR = new Reserves();
			rsR.setMortgage(smSM.getMonthlyPmt()*6);
			rsR.setInsurance(Double.parseDouble(etInsurance.getText().toString()));
			rsR.setTaxes(Double.parseDouble(etTaxes.getText().toString()));
			rsR.setWater(Double.parseDouble(etWater.getText().toString()));
			rsR.setGas(Double.parseDouble(etGas.getText().toString()));
			rsR.setElectric(Integer.parseInt(etElectric.getText().toString()));
			rsR.setTotalExpenses();
	    
			intI.putExtra("Reserves", rsR);

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

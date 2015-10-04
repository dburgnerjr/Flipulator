package com.danielburgnerjr.flipulatorpremium;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;


public class RehabActivity extends Activity {
	
	private Location locL;
	private SalesMortgage smSM;
	
	final Context cntC = this;
	
	private RadioGroup rgRehab;
	private RadioButton rbRehab;
	private TextView tvRehabFlatRate;
	private EditText etRehabBudget;
	private TextView tvRehabType;
	private Spinner spnRehabType;
	private Button btnHelp;				// help


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rehab);
		
		Intent intI = getIntent();
		
		locL = (Location) intI.getSerializableExtra("Location");
		smSM = (SalesMortgage) intI.getSerializableExtra("SalesMortgage");
		
		ArrayAdapter<CharSequence> aradAdapter = ArrayAdapter.createFromResource(
				  this, R.array.rehab_type, android.R.layout.simple_spinner_item );
		aradAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		
		rgRehab   = (RadioGroup)findViewById(R.id.rdoRehab);
		tvRehabFlatRate   = (TextView)findViewById(R.id.tvRehabBudget);
		etRehabBudget   = (EditText)findViewById(R.id.txtRehabBudget);
		tvRehabType   = (TextView)findViewById(R.id.tvRehabType);
		spnRehabType   = (Spinner)findViewById(R.id.spnRehabType);
		btnHelp = (Button)findViewById(R.id.txtHelp);
		spnRehabType.setAdapter(aradAdapter);

		tvRehabFlatRate.setVisibility(View.GONE);
		etRehabBudget.setVisibility(View.GONE);
		tvRehabType.setVisibility(View.GONE);
		spnRehabType.setVisibility(View.GONE);
		
		rgRehab.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	        @Override
	        public void onCheckedChanged(RadioGroup rgG, int nChecked) {

	          if (nChecked==R.id.rdoRehabNumber) {
	        	  tvRehabFlatRate.setVisibility(View.VISIBLE);
	        	  etRehabBudget.setVisibility(View.VISIBLE);
	        	  tvRehabType.setVisibility(View.INVISIBLE);
	        	  spnRehabType.setVisibility(View.INVISIBLE);
	          } else if (nChecked==R.id.rdoRehabType) {
	        	  tvRehabFlatRate.setVisibility(View.INVISIBLE);
	        	  etRehabBudget.setVisibility(View.INVISIBLE);
	        	  tvRehabType.setVisibility(View.VISIBLE);
	        	  spnRehabType.setVisibility(View.VISIBLE);
	          } else {
	        	  tvRehabFlatRate.setVisibility(View.INVISIBLE);
	        	  etRehabBudget.setVisibility(View.INVISIBLE);
	        	  tvRehabType.setVisibility(View.INVISIBLE);
	        	  spnRehabType.setVisibility(View.INVISIBLE);
	          }
	        }
	    });
		
		// add button listener
		btnHelp.setOnClickListener(new OnClickListener() {
		 
			@Override
			public void onClick(View arg0) {
		 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cntC);
		 
				// set title
				alertDialogBuilder.setTitle("Rehab Help");
		 
				// set dialog message
				alertDialogBuilder.setMessage("Enter an option for rehab, whether it be a number or a rehab type. " +
											  "Rehab types are classified as:  Low ($15/sf, yard work and painting), " +
											  "Medium ($20/sf > 1500 sf or $25/sf < 1500 sf, Low + kitchen and bathrooms," +
											  "High ($30/sf, Medium + new roof), Super-High ($40/sf, complete gut job)," +
											  "Bulldozer ($125/sf, demolition and rebuild).  Detailed rehab is covered later.")
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
		
		int nSelected = rgRehab.getCheckedRadioButtonId();
		rbRehab = (RadioButton)findViewById(nSelected);
		Rehab rR;
		Intent intI = new Intent(this, MainActivity.class);
		
		// determines Rehab object by radio button input
		switch (nSelected) {
		
			case R.id.rdoRehabNumber:
										if (("").equals(etRehabBudget.getText().toString())) {
											Toast.makeText(getApplicationContext(), "Must Enter Rehab Budget", Toast.LENGTH_SHORT).show();
										} else {
											intI.putExtra("Location", locL);
											intI.putExtra("SalesMortgage", smSM);
											double dB = Double.parseDouble(etRehabBudget.getText().toString());
											rR = new RehabFlatRate(dB);
											intI.putExtra("Rehab", rR);
											startActivity(intI);
										}
										break;
										
			case R.id.rdoRehabType:
										intI.putExtra("Location", locL);
										intI.putExtra("SalesMortgage", smSM);
										String strRTSel = spnRehabType.getSelectedItem().toString();
										rR = new RehabType(locL.getSquareFootage(), strRTSel);
										intI.putExtra("Rehab", rR);
										startActivity(intI);
										break;
										
			case R.id.rdoRehabDetailed:
/*
										intI = new Intent(this, RehabDetail.class);
										intI.putExtra("Location", locL);
										intI.putExtra("SalesMortgage", smSM);
										String strRTSel = spnRehabType.getSelectedItem().toString();
										rR = new RehabType(locL.getSquareFootage(), strRTSel);
										intI.putExtra("Rehab", rR);
*/										
										Toast.makeText(getApplicationContext(), "Coming soon.", Toast.LENGTH_LONG).show();
										break;								
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

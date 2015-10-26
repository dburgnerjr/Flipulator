package com.danielburgnerjr.flipulatorpremium;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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


public class SettingsActivity extends Activity {
		
	final Context cntC = this;
	
	private RadioGroup rgRehab;
	private RadioButton rbRehab;
	private RadioGroup rgFinance;
	private RadioButton rbFinance;
	private Button btnHelp;				// help
	private int nRehab;
	private int nFinance;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		Intent intI = getIntent();
				
		rgRehab   = (RadioGroup)findViewById(R.id.rdoRehab);
		rgFinance   = (RadioGroup)findViewById(R.id.rdoFinance);
		btnHelp = (Button)findViewById(R.id.txtHelp);
		
		rgRehab.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	        @Override
	        public void onCheckedChanged(RadioGroup rgG, int nChecked) {

	    		switch (nChecked) {
					case R.id.rdoRehabNumber:
												nRehab = 0;
												break;
							
					case R.id.rdoRehabType:
												nRehab = 1;
												break;
	    		}
	        }
	    });

		rgFinance.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	        @Override
	        public void onCheckedChanged(RadioGroup rgG, int nChecked) {
				switch (nChecked) {
					case R.id.rdoOriginal:
												nFinance = 0;
												break;
							
					case R.id.rdoOwnerCarry:
												nFinance = 1;
												break;
							
					case R.id.rdoFinanceRehab:
												nFinance = 2;
												break;
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
				alertDialogBuilder.setMessage("Select rehab class and finance class. Rehab classes are either flat rate (number) " +
											  "or rehab type (set number per sf).  Finance classes are original (financing cost " +
						                      "of house only), owner carry rehab costs (owner pays for rehab costs) and finance " +
											  "rehab (finance cost of house and rehab budget).")
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
		Intent intI = new Intent(this, MainActivity.class);
		Settings setS = new Settings(nRehab, nFinance);
		intI.putExtra("Settings", setS);	    
		startActivity(intI);
		finish();
	}
}

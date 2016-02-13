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
	
	private Settings setS;
	private Location locL;
	private SalesMortgage smSM;
	private Rehab rR;
	private Reserves resR;
	private ClosExpPropMktInfo cemC;
	
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
		setS = (Settings) intI.getSerializableExtra("Settings");
		Toast.makeText(getApplicationContext(), "Rehab Type: " + setS.getRehab(), Toast.LENGTH_SHORT).show();		
		rgRehab   = (RadioGroup)findViewById(R.id.rdoRehab);
		rgFinance   = (RadioGroup)findViewById(R.id.rdoFinance);
		btnHelp = (Button)findViewById(R.id.txtHelp);

		if (setS != null) {
			int nRehabCheckedId = setS.getRehab();
			int nFinanceCheckedId = setS.getFinance();

			switch (nRehabCheckedId) {
				case 0:
							rbRehab = (RadioButton)findViewById(R.id.rdoRehabNumber);
							rbRehab.setChecked(true);
							break;
							
				case 1:
							rbRehab = (RadioButton)findViewById(R.id.rdoRehabType);
							rbRehab.setChecked(true);
							break;				
			}

			switch (nFinanceCheckedId) {
				case 0:
							rbFinance = (RadioButton)findViewById(R.id.rdoOriginal);
							rbFinance.setChecked(true);
							break;
							
				case 1:
							rbFinance = (RadioButton)findViewById(R.id.rdoOwnerCarry);
							rbFinance.setChecked(true);
							break;

				case 2:
							rbFinance = (RadioButton)findViewById(R.id.rdoFinanceRehab);
							rbFinance.setChecked(true);
							break;				

			}
		}
		
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

		locL = (Location) intI.getSerializableExtra("Location");
		smSM = (SalesMortgage) intI.getSerializableExtra("SalesMortgage");
		rR = (Rehab) intI.getSerializableExtra("Rehab");
		resR = (Reserves) intI.getSerializableExtra("Reserves");
		cemC = (ClosExpPropMktInfo) intI.getSerializableExtra("ClosExpPropMktInfo");
	}

	public void prevPage(View view) {
		Intent intI = new Intent(this, MainActivity.class);
		startActivity(intI);
		finish();
	}

	public void nextPage(View view) {		
		if (rgRehab.getCheckedRadioButtonId() == -1) {
			Toast.makeText(getApplicationContext(), "Must Enter Rehab Type", Toast.LENGTH_SHORT).show();
		} else if (rgFinance.getCheckedRadioButtonId() == -1) {
			Toast.makeText(getApplicationContext(), "Must Enter Finance Model", Toast.LENGTH_SHORT).show();
		} else {
			Intent intI = new Intent(this, LocationActivity.class);
			Toast.makeText(getApplicationContext(), "Rehab Value: " + nRehab, Toast.LENGTH_SHORT).show();		
			Settings setS = new Settings(nRehab, nFinance);
			intI.putExtra("Settings", setS);	    
			if (locL != null) {
				intI.putExtra("Location", locL);
			}
			if (smSM != null) {
				intI.putExtra("SalesMortgage", smSM);
			}
			if (rR != null) {
				intI.putExtra("Rehab", rR);
			}
			if (resR != null) {
				intI.putExtra("Reserves", resR);
			}
			if (cemC != null) {
				intI.putExtra("ClosExpPropMktInfo", cemC);
			}
			startActivity(intI);
			finish();
		}
	}
	
	 public boolean onKeyDown(int nKeyCode, KeyEvent keEvent) {
		String strBackMessage = "Press Previous to return to Main Menu, Next to enter Location ";
		strBackMessage += "or Help for assistance.";
		if (nKeyCode == KeyEvent.KEYCODE_BACK) {
			Toast.makeText(getApplicationContext(), strBackMessage, Toast.LENGTH_SHORT).show();
		    return true;
		}
		return super.onKeyDown(nKeyCode, keEvent);
    }
}

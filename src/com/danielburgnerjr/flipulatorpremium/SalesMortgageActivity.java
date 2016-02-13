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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SalesMortgageActivity extends Activity {
	
	final Context cntC = this;
	
	private Settings setS;
	private Location locL;
	private SalesMortgage smSM;
	private Rehab rR;
	private Reserves resR;
	private ClosExpPropMktInfo cemC;

	private EditText etSalesPrice;		// sales price
	private EditText etPercentDown;		// percent down
	private EditText etOfferBid;		// offer/bid price
	private EditText etInterestRate;	// interest rate
	private EditText etTerm;			// term
	private TextView tvRehabFlatRate;
	private EditText etRehabBudget;
	private EditText etBudgetItems;		// budget items
	private TextView tvRehabType;
	private Spinner spnRehabType;
	private Button btnHelp;				// help

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.salemortgage);
		
		Intent intI = getIntent();
		
		setS = (Settings) intI.getSerializableExtra("Settings");
		locL = (Location) intI.getSerializableExtra("Location");

		etSalesPrice   = (EditText)findViewById(R.id.txtSalePrice);
		etPercentDown   = (EditText)findViewById(R.id.txtPercentDown);
		etOfferBid   = (EditText)findViewById(R.id.txtOfferBid);
		etInterestRate   = (EditText)findViewById(R.id.txtInterestRate);
		etTerm   = (EditText)findViewById(R.id.txtTerm);
		etBudgetItems   = (EditText)findViewById(R.id.txtBudgetItems);
		
		ArrayAdapter<CharSequence> aradAdapter = ArrayAdapter.createFromResource(
				  this, R.array.rehab_type, android.R.layout.simple_spinner_item );
		aradAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
		
		tvRehabFlatRate   = (TextView)findViewById(R.id.tvRehabBudget);
		etRehabBudget   = (EditText)findViewById(R.id.txtRehabBudget);
		tvRehabType   = (TextView)findViewById(R.id.tvRehabType);
		spnRehabType   = (Spinner)findViewById(R.id.spnRehabType);
		btnHelp = (Button)findViewById(R.id.txtHelp);
		spnRehabType.setAdapter(aradAdapter);
		
		// add button listener
		btnHelp.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cntC);
 
				// set title
				alertDialogBuilder.setTitle("Sales/Mortgage Help");
 
				// set dialog message
				alertDialogBuilder.setMessage("Enter the sales price, percentage down, offer or bid, interest rate, " +
											  "term of mortgage, budget items and rehab budget.  Rehab budget can be a flat rate or " + 
											  "a rehab type. Rehab types are classified as:  Low ($15/sf, yard work and " + 
											  "painting), Medium ($20/sf > 1500 sf or $25/sf < 1500 sf, Low + kitchen and " + 
											  "bathrooms, High ($30/sf, Medium + new roof), Super-High ($40/sf, complete " + 
											  "gut job), Bulldozer ($125/sf, demolition and rebuild).")
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

		smSM = (SalesMortgage) intI.getSerializableExtra("SalesMortgage");
		// if SalesMortgage object is null, fields are blank
		if (smSM == null) {
			etSalesPrice.setText("");
			etPercentDown.setText("");
			etOfferBid.setText("");
			etInterestRate.setText("");
			etTerm.setText("");
		} else {
			// set fields to member variables of SalesMortgage object
			etSalesPrice.setText((int)smSM.getSalesPrice() + "");
			etPercentDown.setText((int)smSM.getPercentDown() + "");
			etOfferBid.setText((int)smSM.getOfferBid() + "");
			etInterestRate.setText((int)smSM.getInterestRate() + "");
			etTerm.setText(smSM.getTerm() + "");
		}

		setS = (Settings) intI.getSerializableExtra("Settings");
		// if Settings rehab flag is 0, show rehab budget flat rate
		if (setS.getRehab() == 0) {
      	    tvRehabFlatRate.setVisibility(View.VISIBLE);
      	    etRehabBudget.setVisibility(View.VISIBLE);
      	    tvRehabType.setVisibility(View.INVISIBLE);
      	    spnRehabType.setVisibility(View.INVISIBLE);
		} else {
			// show rehab class
      	    tvRehabFlatRate.setVisibility(View.INVISIBLE);
      	    etRehabBudget.setVisibility(View.INVISIBLE);
      	    tvRehabType.setVisibility(View.VISIBLE);
      	    spnRehabType.setVisibility(View.VISIBLE);
		}
		
		rR = (Rehab) intI.getSerializableExtra("Rehab");
		// if Rehab object is null, fields are blank
		if (rR == null) {
			etBudgetItems.setText("");
			if (setS.getRehab() == 0) {
				etRehabBudget.setText("");
			} else {
				spnRehabType.setSelection(0);
			}
		} else {
			etBudgetItems.setText(rR.getBudgetItems());
			if (setS.getRehab() == 0) {
				etRehabBudget.setText((int)rR.getBudget() + "");
			} else {
				int nCostSF = (int)(rR.getBudget()/locL.getSquareFootage());
	      	  	switch (nCostSF) {
	      	  		case 15:
	      	  					spnRehabType.setSelection(0);
	      	  					break;
	      	  		case 20:
	      	  		case 25:
		    	  			    spnRehabType.setSelection(1);
		    	  				break;
	      	  		case 30:
		    	  				spnRehabType.setSelection(2);
		    	  				break;
	      	  		case 40:
		    	  				spnRehabType.setSelection(3);
		    	  				break;
	      	  		case 125:
		    	  				spnRehabType.setSelection(4);
		    	  				break;
	      	    }
			}
		}
		resR = (Reserves) intI.getSerializableExtra("Reserves");
		cemC = (ClosExpPropMktInfo) intI.getSerializableExtra("ClosExpPropMktInfo");
	}

	public void prevPage(View view) {
		Intent intI = new Intent(this, LocationActivity.class);
		intI.putExtra("Settings", setS);
		intI.putExtra("Location", locL);
		intI.putExtra("SalesMortgage", smSM);
		intI.putExtra("Rehab", rR);
		intI.putExtra("Reserves", resR);
		intI.putExtra("ClosExpPropMktInfo", cemC);
		startActivity(intI);
		finish();
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
		} else if (("").equals(etBudgetItems.getText().toString())) {
			Toast.makeText(getApplicationContext(), "Must Enter Budget Items", Toast.LENGTH_SHORT).show();
		} else {
			Intent intI = new Intent(this, ReservesActivity.class);
			intI.putExtra("Location", locL);
			intI.putExtra("Settings", setS);
			
			SalesMortgage smSM = new SalesMortgage();
			smSM.setSalesPrice(Double.parseDouble(etSalesPrice.getText().toString()));
			smSM.setPercentDown(Double.parseDouble(etPercentDown.getText().toString()));
			smSM.setOfferBid(Double.parseDouble(etOfferBid.getText().toString()));
			smSM.setDownPayment(Double.parseDouble(etPercentDown.getText().toString()), Double.parseDouble(etOfferBid.getText().toString()));
	    	smSM.setLoanAmount();
	    	smSM.setInterestRate(Double.parseDouble(etInterestRate.getText().toString()));
	    	smSM.setTerm(Integer.parseInt(etTerm.getText().toString()));
	    	// if finance rehab flag is not selected, set monthly payment as follows
	    	if (setS.getFinance() != 2) {
		    	smSM.setMonthlyPmt();
		    	intI.putExtra("SalesMortgage", smSM);	    		
	    	}
	    	
	    	Rehab rR;
			String strBI = etBudgetItems.getText().toString();
	    	if (setS.getRehab() == 0) {
				if (("").equals(etRehabBudget.getText().toString())) {
					Toast.makeText(getApplicationContext(), "Must Enter Rehab Budget", Toast.LENGTH_SHORT).show();
				} else {
					double dB = Double.parseDouble(etRehabBudget.getText().toString());
					rR = new RehabFlatRate(dB, strBI);
					// if finance rehab flag is selected, set monthly payment as follows
					if (setS.getFinance() == 2) {
				    	smSM.setMonthlyPmt(rR.getBudget());
				    	intI.putExtra("SalesMortgage", smSM);						
					}
					intI.putExtra("Rehab", rR);
					if (resR != null) {
						intI.putExtra("Reserves", resR);
					}
					if (cemC != null) {
						intI.putExtra("ClosExpPropMktInfo", cemC);
					}
			    	startActivity(intI);
			    	finish();
				}	    		
	    	} else {
				String strRTSel = spnRehabType.getSelectedItem().toString();
				rR = new RehabType(locL.getSquareFootage(), strRTSel, strBI);
				// if finance rehab flag is selected, set monthly payment as follows
				if (setS.getFinance() == 2) {
			    	smSM.setMonthlyPmt(rR.getBudget());
			    	intI.putExtra("SalesMortgage", smSM);						
				}
				intI.putExtra("Rehab", rR);
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
	}
	
	 public boolean onKeyDown(int nKeyCode, KeyEvent keEvent) {
		String strBackMessage = "Press Previous to return to Location, Next to enter Reserves info ";
		strBackMessage += "or Help for assistance.";
		if (nKeyCode == KeyEvent.KEYCODE_BACK) {
			Toast.makeText(getApplicationContext(), strBackMessage, Toast.LENGTH_SHORT).show();
		    return true;
		}
		return super.onKeyDown(nKeyCode, keEvent);
   }
}

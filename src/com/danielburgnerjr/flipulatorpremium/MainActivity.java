package com.danielburgnerjr.flipulatorpremium;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends Activity {
	String strPackName;
	private Settings setS;
/*
	final Context cntC = this;
	
	private Location locL;
	private SalesMortgage smSM;
	private Rehab rR;
	private Reserves rsR;
	private ClosExpPropMktInfo cemC;
*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		strPackName = getApplicationContext().getPackageName();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intI = getIntent();
		setS = (Settings) intI.getSerializableExtra("Settings");
        
		final Button btnAbout = (Button) findViewById(R.id.btnAbout);
		btnAbout.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
			    Intent intA = new Intent(MainActivity.this, AboutFlipulatorPremium.class);
			    startActivity(intA);
			}
		});

		final Button btnCalculate = (Button) findViewById(R.id.btnCalculate);
		btnCalculate.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (setS == null) {
					setS = new Settings(0, 0);	// default is flat rate rehab class and original finance class
				}
				//Toast.makeText(getApplicationContext(), setS.toString(), Toast.LENGTH_SHORT).show();
			    Intent intL = new Intent(MainActivity.this, LocationActivity.class);
			    intL.putExtra("Settings", setS);
			    startActivity(intL);
			    finish();
			}
		});

		final Button btnSettings = (Button) findViewById(R.id.btnSettings);
		btnSettings.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
			    Intent intI = new Intent(MainActivity.this, SettingsActivity.class);
			    startActivity(intI);
			}
		});

		final Button btnDonate = (Button) findViewById(R.id.btnDonate);
		btnDonate.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
			    Intent intI = new Intent(MainActivity.this, DonateActivity.class);
			    startActivity(intI);
			}
		});

		final Button btnShare = (Button) findViewById(R.id.btnShare);
		btnShare.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
	   			Intent intI = new Intent(Intent.ACTION_SEND);  
	   			intI.setType("text/plain");
	   			intI.putExtra(Intent.EXTRA_SUBJECT, "Flipulator Premium");
	   			String sAux = "\nLet me recommend you this application\n\n";
	   			sAux = sAux + "https://play.google.com/store/apps/details?id=" + strPackName + "\n\n";
	   			intI.putExtra(Intent.EXTRA_TEXT, sAux);  
	   			startActivity(Intent.createChooser(intI, "choose one"));
			}
		});

/*		
		locL = (Location) intI.getSerializableExtra("Location");
		smSM = (SalesMortgage) intI.getSerializableExtra("SalesMortgage");
		rR = (Rehab) intI.getSerializableExtra("Rehab");
		rsR = (Reserves) intI.getSerializableExtra("Reserves");
		cemC = (ClosExpPropMktInfo) intI.getSerializableExtra("ClosExpPropMktInfo");

		final Button btnLoc = (Button) findViewById(R.id.btnLocation);
		btnLoc.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (locL != null) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cntC);
					 
					// set title
					alertDialogBuilder.setTitle("Location Exists");
			 
					// set dialog message
					alertDialogBuilder.setMessage("You already have Location info. Overwrite?")
									  .setCancelable(false)
									  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
										  public void onClick(DialogInterface dialog, int id) {
											  Intent intI = new Intent(MainActivity.this, LocationActivity.class);
											  startActivity(intI);
										  }
									  })
									  .setNegativeButton("No", new DialogInterface.OnClickListener() {
										  public void onClick(DialogInterface dialog, int id) {
											  dialog.cancel();
										  }
									  });
			 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
			 
					// show it
					alertDialog.show();
				} else {
					Intent intI = new Intent(MainActivity.this, LocationActivity.class);
					startActivity(intI);
				}
			}
		});
		
		final Button btnSM = (Button) findViewById(R.id.btnSalesMortgage);
		btnSM.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (smSM != null) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cntC);
					 
					// set title
					alertDialogBuilder.setTitle("Sales/Mortgage Exists");
			 
					// set dialog message
					alertDialogBuilder.setMessage("You already have Sales/Mortgage info. Overwrite?")
									  .setCancelable(false)
									  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
										  public void onClick(DialogInterface dialog, int id) {
											  Intent intI = new Intent(MainActivity.this, SalesMortgageActivity.class);
											  intI.putExtra("Location", locL);
											  startActivity(intI);
										  }
									  })
									  .setNegativeButton("No", new DialogInterface.OnClickListener() {
										  public void onClick(DialogInterface dialog, int id) {
											  dialog.cancel();
										  }
									  });
			 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
			 
					// show it
					alertDialog.show();			
				} else {
					Intent intI = new Intent(MainActivity.this, SalesMortgageActivity.class);
					intI.putExtra("Location", locL);
					startActivity(intI);
				}
			}
		});
		
		final Button btnRehab = (Button) findViewById(R.id.btnRehab);
		btnRehab.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (rR != null) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cntC);
					 
					// set title
					alertDialogBuilder.setTitle("Rehab Exists");
			 
					// set dialog message
					alertDialogBuilder.setMessage("You already have Rehab info. Overwrite?")
									  .setCancelable(false)
									  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
										  public void onClick(DialogInterface dialog, int id) {
											  Intent intI = new Intent(MainActivity.this, RehabActivity.class);
											  intI.putExtra("Location", locL);
											  intI.putExtra("SalesMortgage", smSM);
											  startActivity(intI);
										  }
									  })
									  .setNegativeButton("No", new DialogInterface.OnClickListener() {
										  public void onClick(DialogInterface dialog, int id) {
											  dialog.cancel();
										  }
									  });
			 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
			 
					// show it
					alertDialog.show();			
					
				} else {
					Intent intI = new Intent(MainActivity.this, RehabActivity.class);
					intI.putExtra("Location", locL);
					intI.putExtra("SalesMortgage", smSM);
					startActivity(intI);
				}
			}
		});

		final Button btnRes = (Button) findViewById(R.id.btnReserves);
		btnRes.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (rsR != null) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cntC);
					 
					// set title
					alertDialogBuilder.setTitle("Reserves Exists");
			 
					// set dialog message
					alertDialogBuilder.setMessage("You already have Reserves info. Overwrite?")
									  .setCancelable(false)
									  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
										  public void onClick(DialogInterface dialog, int id) {
											  Intent intI = new Intent(MainActivity.this, ReservesActivity.class);
											  intI.putExtra("Location", locL);
											  intI.putExtra("SalesMortgage", smSM);
											  intI.putExtra("Rehab", rR);
											  startActivity(intI);
										  }
									  })
									  .setNegativeButton("No", new DialogInterface.OnClickListener() {
										  public void onClick(DialogInterface dialog, int id) {
											  dialog.cancel();
										  }
									  });
			 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
			 
					// show it
					alertDialog.show();			
					
				} else {
					Intent intI = new Intent(MainActivity.this, ReservesActivity.class);
					intI.putExtra("Location", locL);
					intI.putExtra("SalesMortgage", smSM);
					intI.putExtra("Rehab", rR);
					startActivity(intI);
				}
			}
		});

		final Button btnCEPMI = (Button) findViewById(R.id.btnClosExpPMI);
		btnCEPMI.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (cemC != null) {
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cntC);
					 
					// set title
					alertDialogBuilder.setTitle("Closing Expenses/Prop Mkt Exists");
			 
					// set dialog message
					alertDialogBuilder.setMessage("You already have Closing Expenses/Prop Mkt info. Overwrite?")
									  .setCancelable(false)
									  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
										  public void onClick(DialogInterface dialog, int id) {
											  Intent intI = new Intent(MainActivity.this, ClosExpPropMktInfoActivity.class);
											  intI.putExtra("Location", locL);
											  intI.putExtra("SalesMortgage", smSM);
											  intI.putExtra("Rehab", rR);
											  intI.putExtra("Reserves", rsR);
											  startActivity(intI);
										  }
									  })
									  .setNegativeButton("No", new DialogInterface.OnClickListener() {
										  public void onClick(DialogInterface dialog, int id) {
											  dialog.cancel();
										  }
									  });
			 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
			 
					// show it
					alertDialog.show();			
					
				} else {
					Intent intI = new Intent(MainActivity.this, ClosExpPropMktInfoActivity.class);
					intI.putExtra("Location", locL);
					intI.putExtra("SalesMortgage", smSM);
					intI.putExtra("Rehab", rR);
					intI.putExtra("Reserves", rsR);
					startActivity(intI);
				}
			}
		});

		final Button btnFinRes = (Button) findViewById(R.id.btnFinalResult);
		btnFinRes.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (locL == null) {
					Toast.makeText(getApplicationContext(), "No Location info", Toast.LENGTH_SHORT).show();
				} else if (smSM == null) {
					Toast.makeText(getApplicationContext(), "No Sales/Mortgage info", Toast.LENGTH_SHORT).show();
				} else if (rR == null) {
					Toast.makeText(getApplicationContext(), "No Rehab info", Toast.LENGTH_SHORT).show();
				} else if (rsR == null) {
					Toast.makeText(getApplicationContext(), "No Reserves info", Toast.LENGTH_SHORT).show();
				} else if (cemC == null) {
					Toast.makeText(getApplicationContext(), "No Closing Exp/Prop Mkt info", Toast.LENGTH_SHORT).show();
				} else {
					Intent intI = new Intent(MainActivity.this, FinalResultActivity.class);
					intI.putExtra("Location", locL);
					intI.putExtra("SalesMortgage", smSM);
					intI.putExtra("Rehab", rR);
					intI.putExtra("Reserves", rsR);
					intI.putExtra("ClosExpPropMktInfo", cemC);
					startActivity(intI);
				}
			}
		});

		final Button btnFlip = (Button) findViewById(R.id.btnWhatIsFlip);
		btnFlip.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
			    Intent intI = new Intent(MainActivity.this, WhatIsFlipulator.class);
			    startActivity(intI);
			}
		});
*/
	}
	
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitByBackKey();
		    return true;
		}
		return super.onKeyDown(keyCode, event);
    }

	 protected void exitByBackKey() {
		AlertDialog adAlertBox = new AlertDialog.Builder(this)
		    .setMessage("Do you want to exit application?")
		    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		        // do something when the button is clicked
		        public void onClick(DialogInterface arg0, int arg1) {
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

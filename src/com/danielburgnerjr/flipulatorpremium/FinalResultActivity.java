package com.danielburgnerjr.flipulatorpremium;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.content.DialogInterface;
import android.content.Intent;

public class FinalResultActivity extends Activity {

	private Settings setS;
	private Location locL;
	private SalesMortgage smSM;
	private Rehab rR;
	private Reserves rsR;
	private ClosExpPropMktInfo cemC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.finalresult);

		TextView tvFRAddress = (TextView) findViewById(R.id.txtFRAddress);
		TextView tvFRCity = (TextView) findViewById(R.id.txtFRCity);
		TextView tvFRStZip = (TextView) findViewById(R.id.txtFRStZip);
		TextView tvBed = (TextView) findViewById(R.id.txtFRBed);
		TextView tvBath = (TextView) findViewById(R.id.txtFRBath);
		TextView tvSF = (TextView) findViewById(R.id.txtFRSF);
		
		TextView tvSP = (TextView) findViewById(R.id.txtFRSP);
		TextView tvDP = (TextView) findViewById(R.id.txtFRDP);
		TextView tvPD = (TextView) findViewById(R.id.txtFRPD);
		TextView tvLA = (TextView) findViewById(R.id.txtFRLA);
		TextView tvOB = (TextView) findViewById(R.id.txtFROB);
		TextView tvIR = (TextView) findViewById(R.id.txtFRIR);
		TextView tvRB = (TextView) findViewById(R.id.txtFRRehabBudget);
		TextView tvTM = (TextView) findViewById(R.id.txtFRTermMos);
		TextView tvMP = (TextView) findViewById(R.id.txtFRMonthPmt);
		
		TextView tvResMort = (TextView) findViewById(R.id.txtFRMortgageRes);
		TextView tvResTaxes = (TextView) findViewById(R.id.txtFRTaxesRes);
		TextView tvResIns = (TextView) findViewById(R.id.txtFRInsuranceRes);
		TextView tvResElect = (TextView) findViewById(R.id.txtFRElectricRes);
		TextView tvResWater = (TextView) findViewById(R.id.txtFRWaterRes);
		TextView tvResGas = (TextView) findViewById(R.id.txtFRGasRes);
		TextView tvTotRes = (TextView) findViewById(R.id.txtFRTotResAmt);
		
		TextView tvRealEstComm = (TextView)  findViewById(R.id.txtREC);
		TextView tvRECPerc = (TextView)  findViewById(R.id.txtRECP);
		TextView tvBuyerClosCost = (TextView)  findViewById(R.id.txtBCC);
		TextView tvBCCPerc = (TextView)  findViewById(R.id.txtBCCP);
		TextView tvSellClosCost = (TextView)  findViewById(R.id.txtSCC);
		TextView tvSCCPerc = (TextView)  findViewById(R.id.txtSCCP);
		TextView tvTotalCost = (TextView)  findViewById(R.id.txtTC);
		TextView tvOOPExp = (TextView)  findViewById(R.id.txtOOPE);
		
		TextView tvFMVARV = (TextView)  findViewById(R.id.txtFMAR);
		TextView tvComparables = (TextView)  findViewById(R.id.txtCompValue);
		TextView tvSellPrice = (TextView)  findViewById(R.id.txtSPValue);
		TextView tvBuyCosts = (TextView)  findViewById(R.id.txtBCValue);
		TextView tvGrossProfit = (TextView)  findViewById(R.id.txtGPValue);
		TextView tvCapGains = (TextView)  findViewById(R.id.txtCPValue);
		TextView tvNetProfit = (TextView)  findViewById(R.id.txtNPValue);
		
		TextView tvMoneyOut = (TextView)  findViewById(R.id.txtMOValue);
		TextView tvMoneyIn = (TextView)  findViewById(R.id.txtMIValue);
		TextView tvPercRet = (TextView)  findViewById(R.id.txtPRValue);
		TextView tvCashCashRet = (TextView)  findViewById(R.id.txtCCRValue);
			
		Intent intI = getIntent();
		
		locL = (Location) intI.getSerializableExtra("Location");
		smSM = (SalesMortgage) intI.getSerializableExtra("SalesMortgage");
		rR = (Rehab) intI.getSerializableExtra("Rehab");
		rsR = (Reserves) intI.getSerializableExtra("Reserves");
		cemC = (ClosExpPropMktInfo) intI.getSerializableExtra("ClosExpPropMktInfo");
		
		FinalResult frF = new FinalResult();
		frF.setRECost(cemC.getSellingPrice(), cemC.getRealEstComm());
		frF.setBCCost(smSM.getSalesPrice(), cemC.getBuyClosCost());
		frF.setSCCost(smSM.getSalesPrice(), cemC.getSellClosCost());
		frF.setTotalCost(smSM.getOfferBid(), rR.getBudget(), rsR.getTotalExpenses());
		frF.setOOPExp(smSM.getDownPayment(), rsR.getTotalExpenses(), rR.getBudget());
		frF.setGrossProfit(cemC.getSellingPrice());
		frF.setCapGains();
		frF.setNetProfit();
		frF.setROI(cemC.getSellingPrice());
		frF.setCashOnCash();
		
		tvFRAddress.setText(locL.getAddress());
		tvFRCity.setText(locL.getCity());
		tvFRStZip.setText(locL.getState() + " " + locL.getZIPCode());
		tvBed.setText(locL.getBedrooms() + "");
		tvBath.setText(locL.getBathrooms() + "");
		tvSF.setText(locL.getSquareFootage() + "");
		
		tvSP.setText("$" + String.format("%.0f", smSM.getSalesPrice()));
		tvDP.setText("$" + String.format("%.0f", smSM.getDownPayment()));
		tvPD.setText(String.format("%.0f", smSM.getPercentDown()) + "%");
		tvLA.setText("$" + String.format("%.0f", smSM.getLoanAmount()));
		tvOB.setText("$" + String.format("%.0f", smSM.getOfferBid()));
		tvIR.setText(String.format("%.2f", smSM.getInterestRate()) + "%");
		tvRB.setText("$" + String.format("%.0f", rR.getBudget()));
		tvTM.setText(smSM.getTerm() + "");
		tvMP.setText("$" + String.format("%.0f", smSM.getMonthlyPmt()));
		
		tvResMort.setText("$" + String.format("%.0f", (rsR.getMortgage()/6)) + "\t\t\t$" + String.format("%.0f", rsR.getMortgage()) + "\t\t\t$" + String.format("%.0f", (rsR.getMortgage()*2)));
		tvResTaxes.setText("$" + String.format("%.0f", (rsR.getTaxes()/6)) + "\t\t\t$" + String.format("%.0f", rsR.getTaxes()) + "\t\t\t$" + String.format("%.0f", (rsR.getTaxes()*2)));
		tvResIns.setText("$" + String.format("%.0f", (rsR.getInsurance()/6)) + "\t\t\t\t$" + String.format("%.0f", rsR.getInsurance()) + "\t\t\t\t$" + String.format("%.0f", (rsR.getInsurance()*2)));
		tvResElect.setText("$" + String.format("%.0f", (rsR.getElectric()/6)) + "\t\t\t$" + String.format("%.0f", rsR.getElectric()) + "\t\t\t$" + String.format("%.0f", (rsR.getElectric()*2)));
		tvResWater.setText("$" + String.format("%.0f", (rsR.getWater()/6)) + "\t\t\t\t$" + String.format("%.0f", rsR.getWater()) + "\t\t\t\t$" + String.format("%.0f", (rsR.getWater()*2)));
		tvResGas.setText("$" + String.format("%.0f", (rsR.getGas()/6)) + "\t\t\t\t$" + String.format("%.0f", rsR.getGas()) + "\t\t\t\t$" + String.format("%.0f", (rsR.getGas()*2)));
		tvTotRes.setText("$" + String.format("%.0f", (rsR.getTotalExpenses())));
		
		tvRealEstComm.setText("$" + String.format("%.0f", frF.getRECost()));
		tvRECPerc.setText(String.format("%.0f", cemC.getRealEstComm()) + "%");
		tvBuyerClosCost.setText("$" + String.format("%.0f", frF.getBCCost()));
		tvBCCPerc.setText(String.format("%.0f", cemC.getBuyClosCost()) + "%");
		tvSellClosCost.setText("$" + String.format("%.0f", frF.getSCCost()));
		tvSCCPerc.setText(String.format("%.0f", cemC.getSellClosCost()) + "%");
		tvTotalCost.setText("$" + String.format("%.0f", frF.getTotalCost()));
		tvOOPExp.setText("$" + String.format("%.0f", frF.getOOPExp()));
		
		tvFMVARV.setText("$" + String.format("%.0f", cemC.getFMVARV()));
		tvComparables.setText("$" + String.format("%.0f", cemC.getComparables()));
		tvSellPrice.setText("$" + String.format("%.0f", cemC.getSellingPrice()));
		tvBuyCosts.setText("$" + String.format("%.0f", frF.getTotalCost()));
		tvGrossProfit.setText("$" + String.format("%.0f", frF.getGrossProfit()));
		tvCapGains.setText("$" + String.format("%.0f", frF.getCapGains()));
		tvNetProfit.setText("$" + String.format("%.0f", frF.getNetProfit()));

		tvMoneyOut.setText("$" + String.format("%.0f", frF.getOOPExp()));
		tvMoneyIn.setText("$" + String.format("%.0f", frF.getNetProfit()));
		tvPercRet.setText(String.format("%.1f", frF.getROI()) + "%");
		tvCashCashRet.setText(String.format("%.1f", frF.getCashOnCash()) + "%");
	}

	// returns to main menu
	public void mainMenu(View view) {
	    Intent intI = new Intent(FinalResultActivity.this, MainActivity.class);
	    startActivity(intI);
	    finish();
	};

	public void nextPage(View view) {
		// email results of calculate to those parties concerned
/*		String strMessage = "Address:                " + locL.getAddress() + "\n";
		strMessage += "City, State ZIP:        " + calC.getCityStZip() + "\n";
		strMessage += "Square Footage:         " + calC.getSquareFootage() + "\n";
		strMessage += "Bedrooms/Bathrooms:     " + calC.getBedrooms() + " BR " + calC.getBathrooms() + " BA\n";
		strMessage += "After Repair Value:    $" + String.format("%.0f", calC.getFMVARV()) + "\n";
		strMessage += "Sales Price:           $" + String.format("%.0f", calC.getSalesPrice()) + "\n";
		strMessage += "Estimated Budget:      $" + String.format("%.0f", calC.getBudget()) + "\n";
		strMessage += "Closing/Holding Costs: $" + String.format("%.0f", resR.getClosHoldCosts()) + "\n";
		strMessage += "Profit:                $" + String.format("%.0f", resR.getProfit()) + "\n";
		strMessage += "ROI:                    " + String.format("%.1f", resR.getROI()) + "%\n";
		strMessage += "Cash on Cash Return:    " + String.format("%.1f", resR.getCashOnCash()) + "%\n";
		Intent intEmailActivity = new Intent(Intent.ACTION_SEND);
		intEmailActivity.putExtra(Intent.EXTRA_EMAIL, new String[]{});
		intEmailActivity.putExtra(Intent.EXTRA_SUBJECT, "Flipulator results for: " + calC.getAddress() + " " + calC.getCityStZip());
		intEmailActivity.putExtra(Intent.EXTRA_TEXT, strMessage);
		intEmailActivity.setType("plain/text");
   		startActivity(intEmailActivity);
*/	}
	
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
		        	Intent intB = new Intent(FinalResultActivity.this, MainActivity.class);
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

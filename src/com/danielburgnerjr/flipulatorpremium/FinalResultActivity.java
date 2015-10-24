package com.danielburgnerjr.flipulatorpremium;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
 
public class FinalResultActivity extends ActionBarActivity implements
	android.support.v7.app.ActionBar.TabListener {
 
    private ViewPager tabsviewPager;
    private ActionBar mActionBar;
    private TabsAdapter mTabsAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalresult);
 
        tabsviewPager = (ViewPager) findViewById(R.id.tabspager);
        
        mTabsAdapter = new TabsAdapter(getSupportFragmentManager());
        
        tabsviewPager.setAdapter(mTabsAdapter);
        
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        Tab tabLocation = getSupportActionBar().newTab().setText("Location").setTabListener(this);
        Tab tabSales = getSupportActionBar().newTab().setText("Sales").setTabListener(this);
        Tab tabMortgage = getSupportActionBar().newTab().setText("Mortgage").setTabListener(this);
        Tab tabReserves = getSupportActionBar().newTab().setText("Reserves").setTabListener(this);
        Tab tabClosingExp = getSupportActionBar().newTab().setText("Closing Expenses").setTabListener(this);
        Tab tabMarketInfo = getSupportActionBar().newTab().setText("Market Info").setTabListener(this);
        Tab tabROI = getSupportActionBar().newTab().setText("ROI").setTabListener(this);
        
        getSupportActionBar().addTab(tabLocation);
        getSupportActionBar().addTab(tabSales);
        getSupportActionBar().addTab(tabMortgage);
        getSupportActionBar().addTab(tabReserves);
        getSupportActionBar().addTab(tabClosingExp);
        getSupportActionBar().addTab(tabMarketInfo);
        getSupportActionBar().addTab(tabROI);
        
        
        //This helps in providing swiping effect for v7 compat library 
        tabsviewPager.setOnPageChangeListener(new OnPageChangeListener() {
            
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                  getSupportActionBar().setSelectedNavigationItem(position);
            }
            
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
                
            }
        });
    }
 
    @Override
   public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
       // TODO Auto-generated method stub
       
   }

    @Override
   public void onTabSelected(Tab selectedtab, FragmentTransaction arg1) {
       // TODO Auto-generated method stub
       tabsviewPager.setCurrentItem(selectedtab.getPosition()); //update tab position on tap
   }

    @Override
   public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
       // TODO Auto-generated method stub
       
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

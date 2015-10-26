package com.danielburgnerjr.flipulatorpremium;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {

     private int TOTAL_TABS = 7;
    
    public TabsAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public Fragment getItem(int index) {
        // TODO Auto-generated method stub
         switch (index) {
	            case 0:
	                return new LocationFragment();
	                
	            case 1:
	                return new SalesFragment();
	                
	            case 2:
	                return new MortgageFragment();

	            case 3:
	                return new ReservesFragment();

	            case 4:
	                return new ClosingExpensesFragment();

	            case 5:
	                return new MarketInfoFragment();

	            case 6:
	                return new ROIFragment();
         }
     
         return null;
    }

     @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return TOTAL_TABS;
    }

}
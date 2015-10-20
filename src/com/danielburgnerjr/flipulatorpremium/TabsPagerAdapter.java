package com.danielburgnerjr.flipulatorpremium;

import com.danielburgnerjr.flipulatorpremium.LocationFragment;
import com.danielburgnerjr.flipulatorpremium.SalesFragment;
import com.danielburgnerjr.flipulatorpremium.MortgageFragment;
import com.danielburgnerjr.flipulatorpremium.ReservesFragment;
import com.danielburgnerjr.flipulatorpremium.ClosingExpensesFragment;
import com.danielburgnerjr.flipulatorpremium.PropertyMarketInfoFragment;
import com.danielburgnerjr.flipulatorpremium.ReturnOnInvestmentFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
 
public class TabsPagerAdapter extends FragmentStatePagerAdapter {
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
	        case 0:
	            // Location fragment activity
	            return new LocationFragment();
	        case 1:
	            // Sales fragment activity
	            return new SalesFragment();
	        case 2:
	            // Mortgage fragment activity
	            return new MortgageFragment();
	        case 3:
	            // Reserves fragment activity
	            return new ReservesFragment();
	        case 4:
	            // Closing Expenses fragment activity
	            return new ClosingExpensesFragment();
	        case 5:
	            // Property Market Info fragment activity
	            return new PropertyMarketInfoFragment();
	        case 6:
	            // Return on Investment fragment activity
	            return new ReturnOnInvestmentFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 7;
    }
 
}
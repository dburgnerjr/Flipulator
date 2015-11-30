package com.danielburgnerjr.flipulatorpremium;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OpenFilesActivity extends Activity {
	
	ListView lvView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.openfiles);

		// get the files and place it in a file array
		File myDir = new File(getApplicationContext().getExternalFilesDir(null) + "/FlipulatorPremium");
		String strPath = myDir.getPath();
		File fFileList = new File(strPath);
		File fFileArray[] = fFileList.listFiles();
		List<String> arrayList = new ArrayList<String>();
		
		// Get ListView object from xml
        lvView = (ListView) findViewById(R.id.filesList);
        
        // Defined Array values to show in ListView
        for (int nI = 0; nI < fFileArray.length; nI++) {
        	arrayList.add(fFileArray[nI].getName().substring(0, fFileArray[nI].getName().length()-4));
        }
        
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);


        // Assign adapter to ListView
        lvView.setAdapter(adapter); 
        
        // ListView Item Click Listener
        lvView.setOnItemClickListener(new OnItemClickListener() {

              @Override
              public void onItemClick(AdapterView<?> parent, View view,
                 int position, long id) {
                
               // ListView Clicked item index
               int itemPosition = position;
               
               // ListView Clicked item value
               String itemValue = (String) lvView.getItemAtPosition(position);
                  
                Intent newActivity;
                switch( position ) {
                            
                }
            }

         }); 

	}

}

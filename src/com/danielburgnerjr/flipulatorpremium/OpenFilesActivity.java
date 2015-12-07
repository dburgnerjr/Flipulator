package com.danielburgnerjr.flipulatorpremium;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import android.widget.Toast;

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
        // Fourth - the Array of data

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
               String itemValue = (String) lvView.getItemAtPosition(itemPosition);

               // Get file and open it
               //Toast.makeText(getApplicationContext(), itemValue, Toast.LENGTH_SHORT).show();
               Intent newActivity;
               Settings setS = null;
               Location locL = null;
               try {
            	   File fPath = new File(getApplicationContext().getExternalFilesDir(null) + "/FlipulatorPremium");
            	   String strFilePath = fPath.getPath() + "/" + itemValue + ".txt";
            	   BufferedReader br = new BufferedReader(new FileReader(strFilePath));
	        	   String strContents = "";	        	   
	               try {
	                   StringBuilder sb = new StringBuilder();
	                   String strLine = br.readLine();
	
	                   while (strLine != null) {
	                       sb.append(strLine);
	                       sb.append(System.lineSeparator());
	                       strLine = br.readLine();
	                   }
	                   strContents = sb.toString();
	               } catch (FileNotFoundException e) {
	            	   Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_SHORT).show();
	               } finally {
	            	   br.close();
	            	   // split up the contents and store values into objects
	            	   String[] strValues = strContents.split(":");
	            	   setS = new Settings(Integer.parseInt(strValues[1]), Integer.parseInt(strValues[3]));
	            	   locL = new Location(strValues[5], strValues[7], strValues[9], strValues[11],
	            			   			   Integer.parseInt(strValues[13]), Integer.parseInt(strValues[15]),
	            			   			   Integer.parseInt(strValues[17]));
	            	   //Toast.makeText(getApplicationContext(), setS.toString(), Toast.LENGTH_SHORT).show();
	            	   //Toast.makeText(getApplicationContext(), locL.toString(), Toast.LENGTH_SHORT).show();
	               }
               } catch (IOException e) {
            	   Toast.makeText(getApplicationContext(), "IO Exception", Toast.LENGTH_SHORT).show();
               } finally {
            	   newActivity = new Intent(OpenFilesActivity.this, SettingsActivity.class);
            	   newActivity.putExtra("Settings", setS);
            	   newActivity.putExtra("Location", locL);
            	   startActivity(newActivity);
            	   finish();
               }               
            }

         }); 

	}

}

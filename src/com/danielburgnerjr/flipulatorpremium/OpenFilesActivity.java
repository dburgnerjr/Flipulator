package com.danielburgnerjr.flipulatorpremium;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
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
               SalesMortgage smSM = null;
               Rehab rR = null;
               Reserves resR = null;
               ClosExpPropMktInfo cemC = null;
               try {
            	   File fPath = new File(getApplicationContext().getExternalFilesDir(null) + "/FlipulatorPremium");
            	   String strFilePathXLS = fPath.getPath() + "/" + itemValue + ".xls";
            	   String strFilePathTXT = fPath.getPath() + "/" + itemValue + ".txt";
            	   BufferedReader br = new BufferedReader(new FileReader(strFilePathTXT));
	        	   String strContents = "";	        	   
	               try {
	            	   Workbook wbExcelFile = Workbook.getWorkbook(new File(strFilePathXLS));
	            	   Sheet shWorkSheet = wbExcelFile.getSheet(0);
	            	   
	            	   // Settings
	            	   Cell celRehabType = shWorkSheet.getCell(1, 64);
	            	   Cell celFinanceType = shWorkSheet.getCell(3, 64);
	            	   
	            	   String strRehabType = celRehabType.getContents();
	            	   String strFinanceType = celFinanceType.getContents();
	            	   setS = new Settings(Integer.parseInt(strRehabType), Integer.parseInt(strFinanceType));
	            	   
	            	   // Location
	            	   Cell celAddress = shWorkSheet.getCell(1, 1);
	            	   Cell celCity = shWorkSheet.getCell(1, 2);
	            	   Cell celState = shWorkSheet.getCell(1, 3);
	            	   Cell celZipCode = shWorkSheet.getCell(1, 4);
	            	   Cell celSqFootage = shWorkSheet.getCell(3, 2);
	            	   Cell celBedrooms = shWorkSheet.getCell(3, 3);
	            	   Cell celBathrooms = shWorkSheet.getCell(3, 4);
	            	   
	            	   String strAddress = celAddress.getContents();
	            	   String strCity = celCity.getContents();
	            	   String strState = celState.getContents();
	            	   String strZipCode = celZipCode.getContents();
	            	   int nSqFootage = Integer.parseInt(celSqFootage.getContents());
	            	   int nBedrooms = Integer.parseInt(celBedrooms.getContents());
	            	   double dBathrooms = Double.parseDouble(celBathrooms.getContents());
	            	   locL = new Location(strAddress, strCity, strState, strZipCode, nSqFootage, nBedrooms, dBathrooms);

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
				   } catch (BiffException e) {
					   Toast.makeText(getApplicationContext(), "Biff Exception", Toast.LENGTH_SHORT).show();
	               } finally {
	            	   br.close();
	            	   // split up the contents and store values into objects
	            	   String[] strValues = strContents.split(":");
/*	            	   setS = new Settings(Integer.parseInt(strValues[1]), Integer.parseInt(strValues[3]));
	            	   locL = new Location(strValues[5], strValues[7], strValues[9], strValues[11],
	            			   			   Integer.parseInt(strValues[13]), Integer.parseInt(strValues[15]),
	            			   			   Double.parseDouble(strValues[17]));
	            	   smSM = new SalesMortgage(Double.parseDouble(strValues[19]), Double.parseDouble(strValues[21]),
	            			   					Double.parseDouble(strValues[23]), Double.parseDouble(strValues[29]),
	            			   					Double.parseDouble(strValues[31]), Double.parseDouble(strValues[33]),
	            			   					Integer.parseInt(strValues[35]), Double.parseDouble(strValues[37]));
	            	   if (setS.getRehab() == 0) {
	            		   rR = new RehabFlatRate(Double.parseDouble(strValues[25]), strValues[27]);
	            	   }
	            	   if (setS.getRehab() == 1) {
		       				int nCostSF = (int)(Double.parseDouble(strValues[25])/locL.getSquareFootage());
		       				String strType = "";
		    	      	  	switch (nCostSF) {
		    	      	  		case 15:
		    	      	  					strType = "Low";
		    	      	  					break;
		    	      	  		case 20:
		    	      	  		case 25:
		    	      	  					strType = "Medium";
		    		    	  				break;
		    	      	  		case 30:
		    	      	  					strType = "High";
		    		    	  				break;
		    	      	  		case 40:
		    	      	  					strType = "Super-High";
		    		    	  				break;
		    	      	  		case 125:
		    	      	  					strType = "Bulldozer";
		    		    	  				break;
		    	      	    }

	            		   rR = new RehabType(locL.getSquareFootage(), strType, strValues[27]);
	            	   }
	            	   resR = new Reserves(Double.parseDouble(strValues[39]), Double.parseDouble(strValues[41]),
			   							   Double.parseDouble(strValues[43]), Double.parseDouble(strValues[45]),
			   							   Double.parseDouble(strValues[47]), Double.parseDouble(strValues[49]),
			   							   Double.parseDouble(strValues[51]));
	            	   cemC = new ClosExpPropMktInfo(Double.parseDouble(strValues[55]), Double.parseDouble(strValues[59]),
	            			   						 Double.parseDouble(strValues[63]), Double.parseDouble(strValues[69]),
	            			   						 Double.parseDouble(strValues[71]), Double.parseDouble(strValues[73]));
*/	            	   //Toast.makeText(getApplicationContext(), setS.toString(), Toast.LENGTH_SHORT).show();
	            	   //Toast.makeText(getApplicationContext(), locL.toString(), Toast.LENGTH_SHORT).show();
	            	   //Toast.makeText(getApplicationContext(), smSM.toString(), Toast.LENGTH_SHORT).show();
	            	   //Toast.makeText(getApplicationContext(), rR.toString(), Toast.LENGTH_SHORT).show();
	            	   //Toast.makeText(getApplicationContext(), resR.toString(), Toast.LENGTH_SHORT).show();
	            	   //Toast.makeText(getApplicationContext(), cemC.toString(), Toast.LENGTH_SHORT).show();
	               }
               } catch (IOException e) {
            	   Toast.makeText(getApplicationContext(), "IO Exception", Toast.LENGTH_SHORT).show();
               } finally {
            	   newActivity = new Intent(OpenFilesActivity.this, SettingsActivity.class);
            	   newActivity.putExtra("Settings", setS);
            	   newActivity.putExtra("Location", locL);
            	   //newActivity.putExtra("SalesMortgage", smSM);
            	   //newActivity.putExtra("Rehab", rR);
            	   //newActivity.putExtra("Reserves", resR);
            	   //newActivity.putExtra("ClosExpPropMktInfo", cemC);
            	   startActivity(newActivity);
            	   finish();
               }               
            }

         }); 

	}

	 public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intI = new Intent(OpenFilesActivity.this, MainActivity.class);
		    startActivity(intI);
		    finish();
		    return true;
		}
		return super.onKeyDown(keyCode, event);
   }
}

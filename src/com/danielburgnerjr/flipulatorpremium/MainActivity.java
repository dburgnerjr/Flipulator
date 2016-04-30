package com.danielburgnerjr.flipulatorpremium;

import java.io.File;
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
	private Button btnOpenFiles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		strPackName = getApplicationContext().getPackageName();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		File myDir = new File(getApplicationContext().getExternalFilesDir(null) + "/FlipulatorPremium");
		String strPath = myDir.getPath();
		RateThisApp.onLaunch(this);
		//Toast.makeText(getApplicationContext(), strPath, Toast.LENGTH_SHORT).show();
		        
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
				Intent intL = new Intent(MainActivity.this, SettingsActivity.class);
			    startActivity(intL);
			    finish();
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
		
		btnOpenFiles = (Button) findViewById(R.id.btnOpenFiles);
		File fFileList = new File(strPath);
		File fFileArray[] = fFileList.listFiles();
		if (fFileArray == null) {
			btnOpenFiles.setVisibility(View.INVISIBLE);
		}
		btnOpenFiles.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intI = new Intent(MainActivity.this, OpenFilesActivity.class);
			    startActivity(intI);
			    finish();
			}
		});


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

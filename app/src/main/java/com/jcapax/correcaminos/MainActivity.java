package com.jcapax.correcaminos;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	String _idDevice;
	private Double _latitude, _longitude;

	private static final String LOGTAG = "LogsAndroid";
	
	TextView txt_latitude;
	TextView txt_longitude;
	TextView txt_idDevide;
	Button btn_autenticate;
	Button btnUpdateAPK;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);		
		_idDevice =  telephonyManager.getDeviceId();
		
		btnUpdateAPK = (Button)findViewById(R.id.btnUpdateAPK);
		btnUpdateAPK.setVisibility(View.INVISIBLE);
		//btnUpdateAPK.setText(versionAPK());
		
		// VERISION WEB
		//*********************************************************************************************
		HttpHandler ver = new HttpHandler();
		String _ver = ver.version("versionAPK.php", "1");
		
		Log.e(LOGTAG, _ver + " --- "+ versionAPK());
		
		if(!_ver.equals(versionAPK())){		
			
			//*********************************************************************************************
			//*********************************************************************************************
			//*********************************************************************************************
			btnUpdateAPK.setVisibility(View.INVISIBLE);
			//*********************************************************************************************
			//*********************************************************************************************
			//*********************************************************************************************
			
		}
		
		btnUpdateAPK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sidssucre.tk/downloads/Sureguila.apk"));
				startActivity(browserIntent);
				
			}
		});
		
		
		//*********************************************************************************************
		
		
		GPSConfig();
		
		txt_latitude = (TextView)findViewById(R.id.txt_latitude);
		txt_longitude = (TextView)findViewById(R.id.txt_longitude);
		txt_idDevide = (TextView)findViewById(R.id.txt_idDevice);		
		txt_idDevide.setText(_idDevice);
		txt_idDevide.setVisibility(View.INVISIBLE);
		
		btn_autenticate = (Button)findViewById(R.id.btn_autenticate);
		//********************************************************
		//********************************************************
		//********************************************************

		btn_autenticate.setVisibility(View.INVISIBLE);
				
		btn_autenticate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				HttpHandler http = new HttpHandler();
				
				String _service = "autenticate.php";
				String _resp = null;
				String _local = null;
				EditText editPatron = (EditText)findViewById(R.id.edit_patron);	
				
				Log.e(LOGTAG,editPatron.getText().toString()+" PATRON");
				Log.e(LOGTAG,_idDevice+" IDDEVICE");
				
				_resp = http.autenticate(_service, _idDevice, editPatron.getText().toString());
				
				_local = editPatron.getText().toString()+_idDevice;
				
				//Toast.makeText(getApplicationContext(), _resp ,Toast.LENGTH_SHORT).show();
				
				Log.e(LOGTAG,_resp+" RESPUESTA...................");
				Log.e(LOGTAG,_local+" LOCAL.....................");
				
				 if (_resp.trim().equals(_local.trim())){
					 Log.e(LOGTAG,_resp);
					 Intent i = new Intent(getApplicationContext(),VisitActivity.class);				 
					//********************************************************
					//********************************************************
					//********************************************************
					

					i.putExtra("latitude",  _latitude.toString());
					i.putExtra("longitude", _longitude.toString());

					/*
					i.putExtra("latitude",  "1");
					i.putExtra("longitude", "1");
					*/
					i.putExtra("idDevice",  _idDevice);								
					startActivity(i);
					finish();
				
				 }
				 else{
					 Toast.makeText(getApplicationContext(), "Error en el Patron.",Toast.LENGTH_SHORT).show();					 
				 }				
			}
		});
	}

	public void GPSConfig(){
		LocationManager  _locationManager;
		LocationListener _locationListener;
		
		_locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		_locationListener = new MyLocationListener();
		
		_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 15, _locationListener);	
	
	}
	
	public class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			
			
			_latitude = location.getLatitude();
			_longitude = location.getLongitude();
			
			txt_latitude.setText(_latitude.toString());
			txt_longitude.setText(_longitude.toString());			
			
			if (_latitude.toString() != ""){
				btn_autenticate.setVisibility(View.VISIBLE);
			}
			else{
				btn_autenticate.setVisibility(View.INVISIBLE);
			}						
		}	
	
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	
	private String versionAPK() {
		// TODO Auto-generated method stub
		String version = "";
		PackageInfo pInfo;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			version = pInfo.versionName;
			
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version.trim();
	}


}

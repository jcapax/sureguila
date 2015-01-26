package com.jcapax.correcaminos;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class VisitActivity extends Activity {
	String _escado, _idCliente, _nombreCliente, _idDevice;
	String _tipoVenta = "0";
	String _latitude, _longitude;
	
	Button btn_visit, btn_buscarClient, btnListClient;
	
	
	TextView txt_datosCliente;
	EditText edit_idCliente;
	EditText edit_zonaCliente;
	EditText edit_rutaCliente;
	
	Spinner spn_escado, spn_visita;
	
	HttpHandler http = new HttpHandler();
	
	private static final String LOGTAG = "LogsAndroid";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visit);
		
		spn_visita = (Spinner)findViewById(R.id.spn_visita);
		spn_escado = (Spinner)findViewById(R.id.spn_escado);
				
		spn_visita.setVisibility(View.INVISIBLE);
		spn_escado.setVisibility(View.INVISIBLE);
		
		Bundle extras = getIntent().getExtras();
		
		_latitude  = extras.getString("latitude");
		_longitude = extras.getString("longitude");
		_idDevice  = extras.getString("idDevice");		
		
		Log.e(LOGTAG, _latitude+ ' '+ _longitude+' '+_idDevice);
		
		txt_datosCliente = (TextView)findViewById(R.id.txt_datosCliente);		
						
		btn_buscarClient = (Button)findViewById(R.id.btn_buscarClient);
		btn_buscarClient.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String _service = "getDataClient.php";
				
				updateValues();
				
				_nombreCliente = http.postIdcliente(_service, _idCliente.trim());	
				
				txt_datosCliente.setText(_nombreCliente);
				
				InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

		        inputMethodManager.hideSoftInputFromWindow(edit_idCliente.getWindowToken(), 0);
		        
		        // si no encuentra ningun cliente registrado
		        
		        String _aux = "0";
		        
		        if(_nombreCliente.trim().equals(_aux)){
		        	spn_escado.setVisibility(View.INVISIBLE);
		        	spn_visita.setVisibility(View.INVISIBLE);
		        	btn_visit.setVisibility(View.INVISIBLE);
		        	_tipoVenta = "0";
		        	_escado = "0";
		        }
		        else{
		        	spn_visita.setVisibility(View.VISIBLE);
		        	
		        }
			}
		});
		
		Button btn_newClient = (Button)findViewById(R.id.btn_newClient);
		btn_newClient.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(getApplicationContext(),ClientRegister.class);
				startActivity(i);
				//finish();				
			}
		});
		
		Button btnListClient = (Button)findViewById(R.id.btn_listaGeneral);
		btnListClient.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),ClientList.class);
				startActivity(i);
			}
		});
		
		btn_visit = (Button)findViewById(R.id.btn_generarVisita);
		btn_visit.setVisibility(View.INVISIBLE);
		
		btn_visit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				edit_rutaCliente = (EditText)findViewById(R.id.edit_rutaCliente);
				edit_zonaCliente = (EditText)findViewById(R.id.edit_zonaCliente);
				String _zonaCliente = edit_zonaCliente.getText().toString();;
				String _rutaCliente = edit_rutaCliente.getText().toString();;
				
				String _res; 
	
				String _service = "updateCliente.php";
				updateValues();
				
				http.UpdateClient(_service, _idCliente, _rutaCliente, _zonaCliente);
				
				_service = "postVisit.php";
								
				_res = http.postVisit(_service, _idCliente, 
										_latitude.toString(), _longitude.toString(), 
										_idDevice, _escado, _tipoVenta,
										_rutaCliente, _zonaCliente);
				
				//Log.e(LOGTAG, _res);
				
				String _aux = "0"; 
				
				if(!_res.trim().equals(_aux)){				
					String _preventa = "Preventa";
					String _venta = "Directa";

					if ((_tipoVenta.equals(_preventa))||(_tipoVenta.equals(_venta))){
						
						Toast toast = Toast.makeText(getApplicationContext(),
			                    "Visita "+ _res.toString()+" registrada con éxito", Toast.LENGTH_LONG);
			 
						toast.show();
						
						Intent intent = new Intent(getApplicationContext(),SaleDetail.class);						 
						
						intent.putExtra("idVisit", _res);
						intent.putExtra("detailClient", _nombreCliente.toString());						
						
						startActivity(intent);
																		
						finish();
					}
					else{					
						Intent i = new Intent(getApplicationContext(),MainActivity.class);
						startActivity(i);
						Toast toast1 = Toast.makeText(getApplicationContext(),
			                    "Visita "+ _res.toString()+" registrada con éxito", Toast.LENGTH_LONG);
			 
						toast1.show();
					}					
			
					finish();
				}
				else{
					Toast toast1 = Toast.makeText(getApplicationContext(),
		                    "Error en el Registro Verificar los datos", Toast.LENGTH_LONG);		 
					toast1.show();
				}
			}
		});		

// SPINNER TIPO VENTA		
		
		ArrayAdapter<?> arrAdapter = ArrayAdapter.createFromResource(this, 
				R.array.tipoVisita, android.R.layout.simple_spinner_dropdown_item);
					
		arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spn_visita.setAdapter(arrAdapter);	
	
		spn_visita.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> vistaActual, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				_tipoVenta = vistaActual.getItemAtPosition(position).toString();
				
				String _cadena1 = "TIPO VENTA"; 
//				Log.e(LOGTAG, _escado);
				if (_tipoVenta.equals(_cadena1)){

					btn_visit.setVisibility(View.INVISIBLE);					
				}
				else{
					btn_visit.setVisibility(View.VISIBLE);					
				}	

				String _cadena2 = "Rechazo"; 
//				Log.e(LOGTAG, _escado);
				if (_tipoVenta.equals(_cadena2)){

					spn_escado.setVisibility(View.VISIBLE);
					btn_visit.setVisibility(View.INVISIBLE);
				}
				else{
					spn_escado.setVisibility(View.INVISIBLE);		
					_escado = "0";
					btn_visit.setVisibility(View.VISIBLE);
				}	
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
//		CARGAMOS DATOS AL SPINNER				
		
			ArrayAdapter<?> arrayAdapter = ArrayAdapter.createFromResource(this, 
					R.array.escado, android.R.layout.simple_spinner_dropdown_item);
						
			arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			spn_escado.setAdapter(arrayAdapter);	
		
			spn_escado.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> vistaActual, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					
					_escado = vistaActual.getItemAtPosition(position).toString();
					String _cadena = "MOTIVO RECHAZO"; 					
					
//					Log.e(LOGTAG, _escado);
					if (_escado.equals(_cadena)){

						btn_visit.setVisibility(View.INVISIBLE);
						_escado = "0";
					}
					else{
						btn_visit.setVisibility(View.VISIBLE);					
					}				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});	
		
		
	}
	
	void updateValues(){		
		
		edit_idCliente = (EditText)findViewById(R.id.edit_idClient);
		_idCliente = edit_idCliente.getText().toString();
		
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visit, menu);
		return true;
	}

}

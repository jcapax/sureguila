package com.jcapax.correcaminos;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ClientRegister extends Activity {
	
	@SuppressWarnings("unused")
	private static final String LOGTAG = "LogsAndroid";
	
	EditText edit_clientName, edit_direccion, edit_nro, edit_zona,
		telefono, _fechaNacimineto;	
	
	String _canal, _tipoLocal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_register);
		
		Spinner spn_tipoLocal = (Spinner)findViewById(R.id.spn_tipoLocal);
		ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, 
				R.array.tipoLocal, android.R.layout.simple_spinner_dropdown_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spn_tipoLocal.setAdapter(adapter);	
		
		spn_tipoLocal.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parView, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				_tipoLocal = parView.getItemAtPosition(position).toString();
			//	Toast.makeText(parView.getContext(), parView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	
		Spinner spn_canal = (Spinner)findViewById(R.id.spn_canal);
		ArrayAdapter<?> arrayAdapter = ArrayAdapter.createFromResource(this, 
				R.array.canal, android.R.layout.simple_spinner_dropdown_item);
		
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spn_canal.setAdapter(arrayAdapter);	
		
		spn_canal.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parView, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				_canal = parView.getItemAtPosition(position).toString();
				
			//	Toast.makeText(parView.getContext(), parView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		Button btn_saveClient = (Button)findViewById(R.id.btn_saveClient);
		
		btn_saveClient.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				HttpHandler httpCliSave = new HttpHandler();
								
				String _service = "saveclient.php";

				edit_clientName = (EditText)findViewById(R.id.edit_clientName);
				EditText edit_direccion = (EditText)findViewById(R.id.edit_direcction);
				EditText edit_nro = (EditText)findViewById(R.id.edit_nro);
				EditText edit_zona = (EditText)findViewById(R.id.edit_zone);		
				EditText edit_telefono = (EditText)findViewById(R.id.edit_phones);
				EditText edit_fechaNacimiento = (EditText)findViewById(R.id.edit_dateBorn);
				EditText edit_ruta = (EditText)findViewById(R.id.edit_ruta);
				
				String _idCliente = "no registros";
				_idCliente = httpCliSave.postSaveClient(_service, edit_clientName.getText().toString(),
									edit_direccion.getText().toString(), 
									edit_nro.getText().toString(), 
									edit_ruta.getText().toString(),
									edit_zona.getText().toString(), 
									_canal, 
									_tipoLocal,
									edit_telefono.getText().toString(), 
									edit_fechaNacimiento.getText().toString());
				
				
//				Intent i = new Intent(getApplicationContext(),MainActivity.class);
				
				Intent i = new Intent(getApplicationContext(),ConfirmClient.class);
				i.putExtra("idCliente", _idCliente);
				i.putExtra("nombreCliente", edit_clientName.getText().toString());
				
//				i.putExtra("idCliente", idCliente);
//				i.putExtra("nombreCliente", edit_clientName.getText().toString());
				
				startActivity(i);
			
				//Toast toast1 = Toast.makeText(getApplicationContext(),
			      //              "Cliente "+_idCliente+ " registrado con éxito", Toast.LENGTH_LONG);
			 
			        //toast1.show();
			    //Toast toast2 = Toast.makeText(getApplicationContext(),
		          //          "Cliente "+_idCliente+ " registrado con éxito", Toast.LENGTH_LONG);
		 
		        //toast2.show();
			    finish();
			}
		});
		
	}


}

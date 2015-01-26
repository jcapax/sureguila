package com.jcapax.correcaminos;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmClient extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_client);
		
		Bundle extras = getIntent().getExtras();
		String _idCliente =	extras.getString("idCliente");
		String _nombreCliente =	extras.getString("nombreCliente");
		
		TextView txtIdClienteRegistrado = (TextView)findViewById(R.id.txtIdClienteRegistrado);
		TextView txtNombreRegistrado = (TextView)findViewById(R.id.txtNombreClienteRegistrado);
		
		txtIdClienteRegistrado.setText(_idCliente);
		txtNombreRegistrado.setText(_nombreCliente);
		
		Button btnAceptar = (Button)findViewById(R.id.btnAceptar);
		btnAceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(i);
				Toast toast1 = Toast.makeText(getApplicationContext(),
	                    "Cliente registrado con éxito", Toast.LENGTH_LONG);
	 
				toast1.show();
				finish();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirm_client, menu);
		return true;
	}

}

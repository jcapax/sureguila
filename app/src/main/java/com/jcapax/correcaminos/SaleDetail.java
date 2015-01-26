package com.jcapax.correcaminos;

import java.util.ArrayList;
import java.util.Iterator;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class SaleDetail extends Activity {
	String _idVisit;
	TextView detailClient;
	int contadorProductos = 0;
	
	Button btnSendText;
	Button btnGetProducts;
	Spinner spinProducto;	
	EditText editCantidad;
	ListView listProducts;
	String _producto = "";
	TextView editTextFechaEntrega;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_detail);
		
		detailClient = (TextView)findViewById(R.id.detailClient);
		Bundle extras = getIntent().getExtras();
		_idVisit = extras.getString("idVisit");
		detailClient.setText(extras.getString("detailClient"));
		 
		spinProducto = (Spinner)findViewById(R.id.spinProducto);
		editCantidad = (EditText)findViewById(R.id.editCantidadPreVenta);
		btnSendText = (Button)findViewById(R.id.btnSendText);		
		btnGetProducts = (Button)findViewById(R.id.btnGetProducts);
		btnGetProducts.setVisibility(View.INVISIBLE);
		listProducts = (ListView)findViewById(R.id.listProducts);
		
		

		
		
		
		// spiner producto
		//************************
		final ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, 
				R.array.producto, android.R.layout.simple_spinner_dropdown_item);		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spinProducto.setAdapter(adapter);
		
		spinProducto.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				_producto = arg0.getItemAtPosition(arg2).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		//************************

		final ArrayList<String> arrayList = new ArrayList<String>();
		final ArrayAdapter<String> arrayAdpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
		
		listProducts.setAdapter(arrayAdpater);		
		
		btnSendText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				
				String _compar = "PRODUCTO";
				
				if(!_compar.equals(_producto)){
					
					Boolean aux = false;
									
					Iterator<String> i = arrayList.iterator();
					while (i.hasNext()){
						String elemento = i.next();
						String soloProducto[] = elemento.split("-");
						if(soloProducto[0].trim().equals(_producto)){
							aux = true;
						}						
					}
					
					if (!aux){
						arrayList.add(_producto+ '-'+ editCantidad.getText().toString());
					}
					else{
						Toast.makeText(getApplicationContext(), "El producto ya ha sido seleccionado",Toast.LENGTH_SHORT).show();
					}
										
					editCantidad.setText("");

					//arrayList.add(_producto+ '-'+ editCantidad.getText().toString());
					//editCantidad.setText("");
				}				
				
				arrayAdpater.notifyDataSetChanged();
				
				spinProducto.setAdapter(adapter);
				
				InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

		        inputMethodManager.hideSoftInputFromWindow(editCantidad.getWindowToken(), 0);
		        
		        contadorProductos = contadorProductos + 1;
		        btnGetProducts.setVisibility(View.VISIBLE);
		        
			}
		});
		
		listProducts.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int posicion, long arg3) {
				// TODO Auto-generated method stub
				
				contadorProductos =  contadorProductos - 1;
				
				if (contadorProductos == 0){
					btnGetProducts.setVisibility(View.INVISIBLE);
				}
					
				
				arrayList.remove(posicion);
				arrayAdpater.notifyDataSetChanged();				
				return false;	
				
			}
		});
		
		btnGetProducts.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				for (int j = 0; j < arrayList.size(); j++) {
					HttpHandler httpHandler = new HttpHandler();
					 httpHandler.SaleDetail("postPreSale.php", _idVisit, arrayList.get(j).toString());	
					
				}					
				arrayList.clear();
				arrayAdpater.notifyDataSetChanged();
				
				Intent i = new Intent(getApplicationContext(),MainActivity.class);
				startActivity(i);
				
				finish();
			}
			
		});
		
	
		
		 
	}
	
	@Override
	public void onBackPressed(){
		Toast toast1 =
				Toast.makeText(getApplicationContext(),
						"Presione Enviar Para Finalizar el Proceso", Toast.LENGTH_SHORT);
			toast1.show();
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sale_detail, menu);
		return true;
	}

}

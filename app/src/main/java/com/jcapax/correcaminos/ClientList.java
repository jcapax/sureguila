package com.jcapax.correcaminos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jcapax.listas.Clientes;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ClientList extends Activity {
	
	private static final String LOGTAG = "LogsAndroid";
	
	ListView listView;
	Button btnSearch;
	EditText editSearch;
	
	static ProgressDialog dialog;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_list);
		
		listView  = (ListView)findViewById(R.id.listViewClientes);
		btnSearch = (Button)findViewById(R.id.btnSearch);
		editSearch= (EditText)findViewById(R.id.editSearch);
		
		
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				dialog = new ProgressDialog(ClientList.this);
				dialog.setMessage("Un momento porfavor");
				dialog.show();				
				
				Editable criterio = editSearch.getText();
				
				Tarea1 tarea1 = new Tarea1();
				tarea1.cargarContenido(getApplicationContext(), criterio.toString().trim());
				tarea1.execute(listView);
				
				InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

		        inputMethodManager.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);
								
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),VisitList.class);
				String cliente = listView.getItemAtPosition(position).toString();
				i.putExtra("cliente",  cliente);
				startActivity(i);
				
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	static class Tarea1 extends AsyncTask<ListView, Void, ArrayAdapter<Clientes>>{
		Context contexto;
		ListView list;
		InputStream is;
		ArrayList<Clientes> listaClientes = new ArrayList<Clientes>();
		
		String criterio;
		
		public void cargarContenido(Context contexto, String criterio){
			this.contexto = contexto;
			this.criterio = criterio.replaceFirst(" ", "%20");
		}
		
		@Override
		protected void onPostExecute(android.widget.ArrayAdapter<Clientes> result) {
			dialog.dismiss();
			list.setAdapter(result);
		};
		
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@SuppressLint("NewApi")
		@Override
		protected ArrayAdapter<Clientes> doInBackground(ListView... params) {
			// TODO Auto-generated method stub
			
			HttpHandler httpHandler = new HttpHandler();			
			
			list = params[0];
			String resultado = "fallo";
			Clientes cli;
						
			String url = null;
			url = httpHandler._global;	
			
			Log.e(LOGTAG, criterio);
						
			String param = "json_clientes.php?criterio="+criterio;
				
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url+param);
			
			try{
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity contenido = response.getEntity();
				is = contenido.getContent();
			}catch(ClientProtocolException e){
				e.printStackTrace();
			}catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			BufferedReader buferLector = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String linea = null;
			try{
				while((linea = buferLector.readLine()) != null){
					sb.append(linea);
				}
			}catch (IOException e){
				e.printStackTrace();
			}
			
			try{
				is.close();
			}catch (IOException e){
				e.printStackTrace();
			}
			
			resultado = sb.toString();
									
			try{
				JSONArray arrayJson = new JSONArray(resultado);
				for(int i = 0; i < arrayJson.length(); i++){
					JSONObject objetoJson = arrayJson.getJSONObject(i);
					cli = new Clientes(objetoJson.getInt("id"), objetoJson.getString("nombreCliente"), 
										objetoJson.getString("direccion"), objetoJson.getString("nro"), 
										objetoJson.getString("ruta"),      objetoJson.getString("zona"), 
										objetoJson.getString("canal"),     objetoJson.getString("tipoLocal"));
					
					listaClientes.add(cli);
					
					//Log.e(LOGTAG, objetoJson.getString("nombreCliente"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayAdapter<Clientes> adaptador = new ArrayAdapter<Clientes>(contexto, android.R.layout.simple_list_item_1, listaClientes);
			
			return adaptador;
		}
		
	}
	
	

}

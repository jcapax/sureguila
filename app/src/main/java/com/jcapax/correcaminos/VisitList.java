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

import com.jcapax.listas.Visitas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class VisitList extends Activity {
	
	@SuppressWarnings("unused")
	private static final String LOGTAG = "LogsAndroid";
	
	TextView textViewNombreClienteVisita;
	ListView listViewVisitaCliente;
	
	static ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visit_list);
		
		Bundle extras = getIntent().getExtras();
		
		String cliente[] = extras.getString("cliente").split("-");
		String idCliente = cliente[0].trim();
		String nombreCliente = cliente[1];
		textViewNombreClienteVisita =  (TextView)findViewById(R.id.textViewNombreClienteVisita);
		listViewVisitaCliente = (ListView)findViewById(R.id.listViewVisits);
		
		textViewNombreClienteVisita.setText(nombreCliente);
		
		dialog = new ProgressDialog(VisitList.this);
		dialog.setMessage("Cargando Visitas");
		dialog.show();
		
		Tarea1 tarea1 = new Tarea1();
		tarea1.cargarContenido(getApplicationContext(), idCliente.toString());
		tarea1.execute(listViewVisitaCliente);		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visit_list, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */

	
	static class Tarea1 extends AsyncTask<ListView, Void, ArrayAdapter<Visitas>>{
		Context contexto;
		ListView list;
		InputStream is;
		ArrayList<Visitas> listaVisitas = new ArrayList<Visitas>();
		
		String criterio;
		
		public void cargarContenido(Context contexto, String criterio){
			this.contexto = contexto;
			this.criterio = criterio;
		}
		
		@Override
		protected void onPostExecute(android.widget.ArrayAdapter<Visitas> result) {
			dialog.dismiss();
			list.setAdapter(result);
		};
		
		@Override
		protected ArrayAdapter<Visitas> doInBackground(ListView... params) {
			// TODO Auto-generated method stub
			
			HttpHandler httpHandler = new HttpHandler();			
			
			list = params[0];
			String resultado = "fallo";
			Visitas vi;
						
			String url = null;
			url = httpHandler._global;		
						
			String param = "json_visitas.php?criterio="+criterio;
			
			//Log.e(LOGTAG, param);
			//String param = "json_visita.php";			
			
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
					vi = new Visitas(objetoJson.getString("tipoVenta"), 
									objetoJson.getString("tipoVisita"), 
									objetoJson.getString("fechaHoraVisita").substring(0, 10),
                                    objetoJson.getString("producto"),
                                    objetoJson.getString("cantidad"));
					
					listaVisitas.add(vi);
					
					//Log.e(LOGTAG, objetoJson.getString("tipoVenta"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayAdapter<Visitas> adaptador = new ArrayAdapter<Visitas>(contexto, android.R.layout.simple_list_item_1, listaVisitas);
			
			return adaptador;
		}
		
	}

}

package com.jcapax.correcaminos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

public class HttpHandler {
	 
	private static final String LOGTAG = "LogsAndroid";
	
	//String _global = "http://192.168.123.205/sureguila/";
	String _global = "http://www.sids.com.bo/sureguila/";
	
	@SuppressLint("NewApi")
	public String autenticate(String _service, String _idDevice, String _patron){
		String _res = null;
		
		try{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        String _urlglobal = _global + _service;
	        
	        HttpClient httpClient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(_urlglobal);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idDevice", _idDevice));
            params.add(new BasicNameValuePair("patron", _patron));
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resp = httpClient.execute(httppost);
			
			HttpEntity ent = resp.getEntity();
			
			_res = EntityUtils.toString(ent);			
			
			return _res;
		}
		catch(Exception e){
			return "no ingresa";
		}
	} 
	
	@SuppressLint("NewApi")
	public String version(String _service, String _idAplicacion){
		String _res = null;
		
		try{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        String _urlglobal = _global + _service;
	        
	        HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(_urlglobal);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();		
			
            params.add(new BasicNameValuePair("idAplicacion", _idAplicacion));
            
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resp = httpclient.execute(httppost);			
			
			HttpEntity ent = resp.getEntity();
			
			_res = EntityUtils.toString(ent);	
		
			return _res.trim();
		}
		catch(Exception e){
			return "error";
		}
	} 
	
	
	@SuppressLint("NewApi")
	public String postIdcliente(String _service, String _idCliente){
		String nombreCliente = null;
		
		try{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        String  _urlglobal = _global + _service;
	        
	        HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(_urlglobal);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idCliente", _idCliente));
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resp = httpclient.execute(httppost);			
			
			HttpEntity ent = resp.getEntity();
			
			nombreCliente = EntityUtils.toString(ent);			
			
			return nombreCliente;
		}
		catch(Exception e){
			return "no ingresa";
		}
	} 
	
	@SuppressLint("NewApi")
	public String postSaveClient(String _service, String _nombreCliente, String _direccion, 
								String _nro, String _ruta, String _zona, String _canal, String _tipoLocal,
								String _telefono, String _fechaNacimiento) {
		
		try{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        String _urlglobal = _global + _service;
	        
	        HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(_urlglobal);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nombreCliente", _nombreCliente));
            params.add(new BasicNameValuePair("direccion", _direccion));            
            params.add(new BasicNameValuePair("nro", _nro));
            params.add(new BasicNameValuePair("ruta", _ruta));
            params.add(new BasicNameValuePair("zona", _zona));
            params.add(new BasicNameValuePair("canal", _canal));
            params.add(new BasicNameValuePair("tipoLocal", _tipoLocal));
            params.add(new BasicNameValuePair("telefono", _telefono));
            params.add(new BasicNameValuePair("fechaNacimiento", _fechaNacimiento));
            Log.e(LOGTAG, _ruta);	        
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resp = httpclient.execute(httppost);			
			
			HttpEntity ent = resp.getEntity();
			
			String _cliente = EntityUtils.toString(ent);
			
			Log.e(LOGTAG, _cliente);
			return _cliente;			
			
		}
		catch(Exception e){
			return "no ingresa";
		}
		
		
	}
	
	@SuppressLint("NewApi")
	public String SaleDetail(String _service, String _idVisita, String _detalle){
		String _res = null;
		
		try{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        String _urlglobal = _global + _service;
	        
	        HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(_urlglobal);
			
			String[] _separated = _detalle.split("-");
			String _producto = _separated[0];
			String _cantidad = _separated[1];	
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idVisita", _idVisita));
            params.add(new BasicNameValuePair("producto", _producto));
            params.add(new BasicNameValuePair("cantidad", _cantidad));
            
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resp = httpclient.execute(httppost);			
			
			HttpEntity ent = resp.getEntity();
			
			_res = EntityUtils.toString(ent);			
			
			return _res;
		}
		catch(Exception e){
			return "no ingresa";
		}
	} 

	
	@SuppressLint("NewApi")
	public String postVisit(String _service, String _idCliente, 
							String _longitude, String _latitude, 
							String _idDevice, String _tipoVisita, String _tipoVenta,
							String _ruta, String _zonaCliente){
		try {
			
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        String _urlglobal = _global + _service;
	        
			HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(_urlglobal);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idDevice", _idDevice));
            params.add(new BasicNameValuePair("idCliente", _idCliente));
            params.add(new BasicNameValuePair("longitud", _longitude));
            params.add(new BasicNameValuePair("latitud", _latitude));
            params.add(new BasicNameValuePair("tipoVisita", _tipoVisita));
            params.add(new BasicNameValuePair("tipoVenta", _tipoVenta));
            
            params.add(new BasicNameValuePair("ruta", _ruta));
            params.add(new BasicNameValuePair("zonaCliente", _zonaCliente));
            
//            Log.e(LOGTAG, _idCliente);
            
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resp = httpclient.execute(httppost);
			
			HttpEntity ent = resp.getEntity();
			
			String text = EntityUtils.toString(ent);

			return text;

		}
		catch(Exception e){
			return "error se pasa de largo....";
		}	
		
		
	}
	
	@SuppressLint("NewApi")
	public String postGPS(String _service, String _longitude, String _latitude, String _idDevice, String _mensaje){
		try {
			
			String _punto = _latitude + ", "  +_longitude;
			
			String _urlglobal = _global + _service;
			
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
			HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(_urlglobal);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idDevice", _idDevice));
            params.add(new BasicNameValuePair("punto", _punto));
            params.add(new BasicNameValuePair("longitude", _longitude));
            params.add(new BasicNameValuePair("latitude", _latitude));
            params.add(new BasicNameValuePair("mensaje", _mensaje));
            
            Log.e(LOGTAG, _idDevice);
            Log.e(LOGTAG, _punto);
	        	        
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));



			HttpResponse resp = httpclient.execute(httppost);
			
			HttpEntity ent = resp.getEntity();
			
			String text = EntityUtils.toString(ent);

			return text;

		}
		catch(Exception e){
			return "error se pasa de largo....";
		}
	}
	
	@SuppressLint("NewApi")
	public String UpdateClient(String _service, String _idCliente,							 
							String _rutaCliente, String _zonaCliente){
		try {
			
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        String _urlglobal = _global + _service;
	        
			HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(_urlglobal);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("idCliente", _idCliente));
            params.add(new BasicNameValuePair("rutaCliente", _rutaCliente));
            params.add(new BasicNameValuePair("zonaCliente", _zonaCliente));
            
//            Log.e(LOGTAG, _idCliente);
            
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resp = httpclient.execute(httppost);
			
			HttpEntity ent = resp.getEntity();
			
			String text = EntityUtils.toString(ent);

			return text;

		}
		catch(Exception e){
			return "error se pasa de largo....";
		}	
		
		
	}
	


}

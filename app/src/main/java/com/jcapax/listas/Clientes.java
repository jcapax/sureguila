package com.jcapax.listas;

public class Clientes {
	private int id;
	private String nombreCliente;
	private String direccion;
	private String nro;
	private String ruta;
	private String zona;
	private String canal;
	private String tipoLocal;

	
	
	public Clientes(int id, String nombreCliente, String direccion, String nro, String ruta, String zona,
			String canal, String tipoLocal) {
		// TODO Auto-generated constructor stub
		super();
		this.id = id;
		this.nombreCliente = nombreCliente;
		this.direccion = direccion;
		this.nro = nro;
		this.ruta = ruta;
		this.zona = zona;
		this.canal = canal;
		this.tipoLocal = tipoLocal;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getNro() {
		return nro;
	}
	public void setNro(String nro) {
		this.nro = nro;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getCanal() {
		return canal;
	}
	public void setCanal(String canal) {
		this.canal = canal;
	}
	public String getTipoLocal() {
		return tipoLocal;
	}
	public void setTipoLocal(String tipoLocal) {
		this.tipoLocal = tipoLocal;
	}

	@Override
	public String toString(){
		return this.id + " - " +this.nombreCliente + " - " + this.direccion;
	}
}

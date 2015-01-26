package com.jcapax.listas;

public class Visitas {
	private String tipoVenta;
	private String tipoVisita;
	private String fechaHoraVisita;
    private String producto;
    private String cantidad;
	
	public Visitas(String tipoVenta, String tipoVisita, String fechaHoraVisita, String producto, String cantidad){
		super();
		this.tipoVenta = tipoVenta;
		this.tipoVisita = tipoVisita;
		this.fechaHoraVisita = fechaHoraVisita;
        this.producto = producto;
        this.cantidad = cantidad;
	}

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoVenta() {
		return tipoVenta;
	}

	public void setTipoVenta(String tipoVenta) {
		this.tipoVenta = tipoVenta;
	}

	public String getTipoVisita() {
		return tipoVisita;
	}

	public void setTipoVisita(String tipoVisita) {
		this.tipoVisita = tipoVisita;
	}

	public String getFechaHoraVisita() {
		return fechaHoraVisita;
	}

	public void setFechaHoraVisita(String fechaHoraVisita) {
		this.fechaHoraVisita = fechaHoraVisita;
	}
		
	
	@Override
	public String toString(){
		return this.fechaHoraVisita + "   "+ this.tipoVenta + " - " +this.tipoVisita + " - " +this.producto + " - " +this.cantidad;
	}	
}

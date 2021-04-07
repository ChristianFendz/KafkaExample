package com.kafkaCurso.kafka.ejercicio;

import java.math.BigDecimal;
import java.util.Date;

public class Usuario {

	private String usuario;
	private String accion;
	private BigDecimal monto;
	private Date timestamp;

	
	public Usuario() {
		super();
	}

	public Usuario(String usuario, String accion, BigDecimal monto, Date timestamp) {
		super();
		this.usuario = usuario;
		this.accion = accion;
		this.monto = monto;
		this.timestamp = timestamp;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", accion=" + accion + ", monto=" + monto + ", timestamp=" + timestamp
				+ "]";
	}

}

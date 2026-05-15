package modelo;

import java.util.Date;

public class Proyecto {
	private int idProyecto;
    private String nombreProyecto;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    
    public Proyecto(int idProyecto, String nombreProyecto, String descripcion, 
    		        Date fechaInicio, Date fechaFin, String estado) {
        this.idProyecto = idProyecto;
        this.nombreProyecto = nombreProyecto;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }
    
    public int getIdProyecto() { return idProyecto; }
    public String getNombreProyecto() { return nombreProyecto; }
    public String getDescripcion() { return descripcion; }
    public Date getFechaInicio() { return fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public String getEstado() { return estado; }
    
    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
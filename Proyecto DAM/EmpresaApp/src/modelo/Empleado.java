package modelo;

import java.util.Date;

public class Empleado {
	private int idEmpleado;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private String telefono;
    private Date fechaNacimiento;
    private Date fechaContratacion;
    private double salario;
    private int idDepartamento;
    private int idPuesto;
    private int idSede;
    private boolean activo;
    private Departamento departamento;
    private Puesto puesto;
    private Sede sede;
    
    public Empleado(int idEmpleado, String nombre, String apellido, String dni, String email, String telefono,
			        Date fechaNacimiento, Date fechaContratacion, double salario, int idDepartamento, int idPuesto,
			        int idSede, boolean activo) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.email = email;
		this.telefono = telefono;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaContratacion = fechaContratacion;
		this.salario = salario;
		this.idDepartamento = idDepartamento;
		this.idPuesto = idPuesto;
		this.idSede = idSede;
		this.activo = activo;
	}

	public int getIdEmpleado() { return idEmpleado; }
	public String getNombre() { return nombre; }
	public String getApellido() { return apellido; }
	public String getDni() { return dni; }
	public String getEmail() { return email; }
	public String getTelefono() { return telefono; }
	public Date getFechaNacimiento() { return fechaNacimiento; }
	public Date getFechaContratacion() { return fechaContratacion; }
	public double getSalario() { return salario; }
	public int getIdDepartamento() { return idDepartamento; }
	public int getIdPuesto() { return idPuesto; }
	public int getIdSede() { return idSede; }
	public Departamento getDepartamento() { return departamento; }
	public Puesto getPuesto() { return puesto; }
	public Sede getSede() { return sede; }
	
	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public void setFechaContratacion(Date fechaContratacion) {
		this.fechaContratacion = fechaContratacion;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	public void setIdDepartamento(int idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	public void setIdPuesto(int idPuesto) {
		this.idPuesto = idPuesto;
	}
	public void setIdSede(int idSede) {
		this.idSede = idSede;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public void setDepartamento(Departamento departamento) {
	    this.departamento = departamento;
	}
	public void setPuesto(Puesto puesto) {
	    this.puesto = puesto;
	}
	public void setSede(Sede sede) {
	    this.sede = sede;
	}
	
	public boolean isActivo() {
		return activo;
	}
}
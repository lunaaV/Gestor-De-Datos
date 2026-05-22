package modelo;

public class EmpleadoProyecto {
	private int idEmpleado;
    private int idProyecto;
    private String rolProyecto;
    private int horasAsignadas;
    private String nombre;
    private String apellido;
    private String nombreProyecto;
    
    public EmpleadoProyecto(int idEmpleado, int idProyecto, String rolProyecto, int horasAsignadas) {
        this.idEmpleado = idEmpleado;
        this.idProyecto = idProyecto;
        this.rolProyecto = rolProyecto;
        this.horasAsignadas = horasAsignadas;
    }
    
    public EmpleadoProyecto(int idEmpleado, int idProyecto, String rolProyecto, int horasAsignadas, String nombre, String apellido, String nombreProyecto) {
		this.idEmpleado = idEmpleado;
		this.idProyecto = idProyecto;
		this.rolProyecto = rolProyecto;
		this.horasAsignadas = horasAsignadas;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nombreProyecto = nombreProyecto;
	}
    
    public int    getIdEmpleado()     { return idEmpleado; }
    public int    getIdProyecto()     { return idProyecto; }
    public String getRolProyecto()    { return rolProyecto; }
    public int    getHorasAsignadas() { return horasAsignadas; }
    public String getNombre()         { return nombre; }
    public String getApellido()       { return apellido; }
    public String getNombreProyecto() { return nombreProyecto; }
    
    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }
    public void setRolProyecto(String rolProyecto) {
        this.rolProyecto = rolProyecto;
    }
    public void setHorasAsignadas(int horasAsignadas) {
        this.horasAsignadas = horasAsignadas;
    }
}
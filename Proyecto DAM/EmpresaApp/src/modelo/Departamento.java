package modelo;

public class Departamento {
	private int idDepartamento;
    private String nombreDepartamento;
    private String descripcion;
    
	public Departamento(int idDepartamento, String nombreDepartamento, String descripcion) {
		super();
		this.idDepartamento = idDepartamento;
		this.nombreDepartamento = nombreDepartamento;
		this.descripcion = descripcion;
	}

	public int getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(int idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
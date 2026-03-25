package modelo;

public class Sede {
	private int idSede;
    private String nombreSede;
    private String ciudad;
    
	public Sede(int idSede, String nombreSede, String ciudad) {
		super();
		this.idSede = idSede;
		this.nombreSede = nombreSede;
		this.ciudad = ciudad;
	}

	public int getIdSede() {
		return idSede;
	}
	public String getNombreSede() {
		return nombreSede;
	}
	public String getCiudad() {
		return ciudad;
	}

	public void setIdSede(int idSede) {
		this.idSede = idSede;
	}
	public void setNombreSede(String nombreSede) {
		this.nombreSede = nombreSede;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
}
package model.vak;

public class Vak {
	private String code;
	private String naam;
	
	public Vak(String code, String naam){
		this.code = code;
		this.naam = naam;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}
}

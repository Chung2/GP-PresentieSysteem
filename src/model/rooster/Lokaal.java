package model.rooster;

import model.persoon.Status;

public class Lokaal {
	private String naam;
	private int capaciteit;

	public Lokaal(String nm, int cp){
		naam = nm;
		capaciteit = cp;
	}
	
	public String getNaam() {
		return naam;
	}
	
	public Integer getCapaciteit() {
		return capaciteit;
	}
	
	public boolean equals(Object obj){
		
		if(obj instanceof Lokaal){
			Lokaal andereLokaal = (Lokaal)obj;
			
			if(naam.equals(andereLokaal.getNaam()) &&
				 capaciteit == andereLokaal.getCapaciteit()) return true;
		}
		
		return false;
	}
}

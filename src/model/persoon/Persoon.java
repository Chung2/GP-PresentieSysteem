package model.persoon;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Persoon {

	private String voornaam;
	private String tussenvoegsel;
	private String achternaam;
	private transient String wachtwoord;
	private String gebruikersnaam;
	private ArrayList<Status> statussen;
	private boolean ziek;

	public Persoon(String voornaam, String tussenvoegsel, String achternaam, String wachtwoord, String gebruikersnaam) {
		this.voornaam = voornaam;
		this.tussenvoegsel = tussenvoegsel;
		this.achternaam = achternaam;
		this.wachtwoord = wachtwoord;
		this.gebruikersnaam = gebruikersnaam;
		this.statussen = new ArrayList<Status>();
		this.ziek=false;
	}

	public String getVoornaam() {
		return this.voornaam;
	}

	private String getAchternaam() {
		return this.achternaam;
	}

	protected String getWachtwoord() {
		return this.wachtwoord;
	}

	public String getGebruikersnaam() {
		return this.gebruikersnaam;
	}
	
	public void ziekMelden(){
		ziek=true;
	}
	
	public void beterMelden(){
		ziek=false;
	}
	
	public void nieuweStatus(String naam, LocalDate datum, String dagdeel){
		Status status = new Status(naam, datum, dagdeel);
		
		for (Status s : statussen) {
  		if(s.equals(status)) return;
		}
		
		statussen.add(status);
	}
	
	public void verwijderStatus(LocalDate datum, String dagdeel){
		Status status = new Status("Afwezig", datum, dagdeel);
		
		for (Status s : statussen) {
  		if(s.equals(status)) {
  			statussen.remove(s);
  			break;
  		}
		}
	}

	public String getVolledigeAchternaam() {
		String lVolledigeAchternaam="";
		if (this.tussenvoegsel != null && this.tussenvoegsel != "" && this.tussenvoegsel.length() > 0) {
			lVolledigeAchternaam += this.tussenvoegsel + " ";
		}
		lVolledigeAchternaam += this.getAchternaam();
		return lVolledigeAchternaam;
	}

	public boolean komtWachtwoordOvereen(String pWachtwoord) {
		boolean lStatus = false;
		if (this.getWachtwoord().equals(pWachtwoord)) {
			lStatus = true;
		}
		return lStatus;
	}
}

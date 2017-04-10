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
	
	//methode meld ziek
	public void ziekMelden(){
		ziek=true;
	}
	
	//methode meld beter
	public void beterMelden(){
		ziek=false;
	}
	
	//methode zet nieuwe status
	public void nieuweStatus(String naam, LocalDate datum, String dagdeel){
		Status status = new Status(naam, datum, dagdeel);
		
		for (Status s : statussen) {
  		if(s.equals(status)) return;
		}
		
		statussen.add(status);
	}
	
	//methode verwijder de status van de student
	public void verwijderStatus(LocalDate datum, String dagdeel){
		Status status = new Status("Afwezig", datum, dagdeel);
		
		for (Status s : statussen) {
  		if(s.equals(status)) {
  			statussen.remove(s);
  			break;
  		}
		}
	}

	//methode geeeft volledige achternaam terug
	public String getVolledigeAchternaam() {
		String lVolledigeAchternaam="";
		if (this.tussenvoegsel != null && this.tussenvoegsel != "" && this.tussenvoegsel.length() > 0) {
			lVolledigeAchternaam += this.tussenvoegsel + " ";
		}
		lVolledigeAchternaam += this.getAchternaam();
		return lVolledigeAchternaam;
	}

	//methode kijkt of wachtwoord overeenkom
	public boolean komtWachtwoordOvereen(String pWachtwoord) {
		boolean lStatus = false;
		if (this.getWachtwoord().equals(pWachtwoord)) {
			lStatus = true;
		}
		return lStatus;
	}
}
package model.rooster;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Locale;

import model.klas.Klas;
import model.persoon.Docent;
import model.vak.Vak;

public class Les {
	private Vak huidigeVak;
	private transient Klas huidigeKlas;
	private String klasCode;
	private Docent huidigeDocent;
	private Lokaal huidigeLokaal;
	private LocalDateTime beginTijd;
	private LocalDateTime eindTijd;
	private String dag;
	private int week;
	
	public Les(Vak hV, Klas hK, Docent hD, Lokaal hL, LocalDateTime bT, LocalDateTime eT){
		huidigeVak = hV;
		huidigeKlas = hK;
		klasCode = hK.getKlasCode();
		huidigeDocent = hD;
		huidigeLokaal = hL;
		beginTijd = bT;
		eindTijd = eT;
		
		//Dag ophalen
		dag = beginTijd.getDayOfWeek().toString();
		
		//Weeknummer ophalen
		TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		week = beginTijd.get(woy);
	}

	public Vak getHuidigeVak() {
		return huidigeVak;
	}

	public String klasCode(){
		return klasCode;
	}
	
	public void setHuidigeVak(Vak huidigeVak) {
		this.huidigeVak = huidigeVak;
	}

	public Klas getHuidigeKlas() {
		return huidigeKlas;
	}
	
	public void setHuidigeKlas(Klas huidigeKlas) {
		this.huidigeKlas = huidigeKlas;
	}

	public Docent getHuidigeDocent() {
		return huidigeDocent;
	}

	public void setHuidigeDocent(Docent huidigeDocent) {
		this.huidigeDocent = huidigeDocent;
	}

	public Lokaal getHuidigeLokaal() {
		return huidigeLokaal;
	}

	public void setHuidigeLokaal(Lokaal huidigeLokaal) {
		this.huidigeLokaal = huidigeLokaal;
	}

	public LocalDateTime getBeginTijd() {
		return beginTijd;
	}

	public void setBeginTijd(LocalDateTime beginTijd) {
		this.beginTijd = beginTijd;
	}

	public LocalDateTime getEindTijd() {
		return eindTijd;
	}

	public void setEindTijd(LocalDateTime eindTijd) {
		this.eindTijd = eindTijd;
	}
}

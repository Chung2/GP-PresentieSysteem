package model.rooster;
import java.util.Calendar;

import model.klas.Klas;
import model.persoon.Docent;
import model.vak.Vak;

public class Les {
	private Vak huidigeVak;
	private transient Klas huidigeKlas;
	private String klasCode;
	private Docent huidigeDocent;
	private Lokaal huidigeLokaal;
	private Calendar beginTijd;
	private Calendar eindTijd;
	
	public Les(Vak hV, Klas hK, Docent hD, Lokaal hL, Calendar bT, Calendar eT){
		huidigeVak = hV;
		huidigeKlas = hK;
		klasCode = hK.getKlasCode();
		huidigeDocent = hD;
		huidigeLokaal = hL;
		beginTijd = bT;
		eindTijd = eT;
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

	public Calendar getBeginTijd() {
		return beginTijd;
	}

	public void setBeginTijd(Calendar beginTijd) {
		this.beginTijd = beginTijd;
	}

	public Calendar getEindTijd() {
		return eindTijd;
	}

	public void setEindTijd(Calendar eindTijd) {
		this.eindTijd = eindTijd;
	}
}

package model.rooster;

import java.util.ArrayList;

public class Rooster {
	private ArrayList<Les> alleLessen = new ArrayList<Les>();
	
	public Rooster(ArrayList<Les> alleLessen){
		this.alleLessen = alleLessen;
	}

	public ArrayList<Les> getAlleLessen() {
		return alleLessen;
	}

	public void setAlleLessen(ArrayList<Les> alleLessen) {
		this.alleLessen = alleLessen;
	}
}

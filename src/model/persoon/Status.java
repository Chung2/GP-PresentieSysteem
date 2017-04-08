package model.persoon;

import java.time.LocalDate;

public class Status {
	private String status;
	private LocalDate datum;
	private String dagdeel;
	
	public Status(String status){
		this.status = status;
		this.dagdeel = "";
	}
	
	public Status (String status, LocalDate datum, String dagdeel){
		this.status = status;
		this.datum = datum;
		this.dagdeel = dagdeel;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public LocalDate getDatum(){
		return this.datum;
	}
	
	public String getDagdeel(){
		return this.dagdeel;
	}
	
	public boolean equals(Object obj){
		
		if(obj instanceof Status){
			Status andereStatus = (Status)obj;
			
			if(status.equals(andereStatus.getStatus()) &&
				 datum.equals(andereStatus.getDatum()) &&
				 dagdeel.equals(andereStatus.getDagdeel())) return true;
		}
		
		return false;
	}
}

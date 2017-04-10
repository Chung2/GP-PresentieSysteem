package controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.PrIS;
import model.klas.Klas;
import model.persoon.Docent;
import model.persoon.Student;
import server.Conversation;
import server.Handler;

public class PersoonController implements Handler{
	private PrIS informatieSysteem;
	
	public PersoonController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	//methode linkt json file aan een url
	public void handle(Conversation conversation) {
	  if (conversation.getRequestedURI().startsWith("/persoon/zetstatus")) {
	  	zetStatus(conversation);
		}
	  
		if (conversation.getRequestedURI().startsWith("/persoon/info")) {
			persoonInfo(conversation);
		}
		
		if (conversation.getRequestedURI().startsWith("/studenten/info")) {
			alleStudenten(conversation);
		}
		
		if (conversation.getRequestedURI().startsWith("/docenten/info")) {
			alleDocenten(conversation);
		}
	}
	
	//methode verandert de status van de persoon
	private void zetStatus(Conversation conversation) {
		//JsonParser maken om een json string naar een object om te zetten
		JsonParser parser = new JsonParser();
		//Gson object maken voor het parsen van json
		Gson gson = new Gson();
		
		//Kijk of er een json string is mee gegeven
		if (conversation.getRequestBodyAsString() == null){
			//Error terug sturen naar browser dat er geen data mee is gegeven
			conversation.sendJSONMessage("{\"error\":\"Geen json data mee gegeven\"}");
			
			return;
		}
		
		try {
			JsonObject request = parser.parse(conversation.getRequestBodyAsString()).getAsJsonObject();
			
			if (request.get("gebruikersnaam") != null && request.get("status") != null){
				String status = request.get("status").getAsString();
				
				//Kijken wat voor een status er mee is gegeven
        switch (status) {
        	//Persoon beter melden
          case "Beter":
          	//Kijk of 
    				if (informatieSysteem.getStudent(request.get("gebruikersnaam").getAsString()) != null){
    					Student student = informatieSysteem.getStudent(request.get("gebruikersnaam").getAsString());
    					
    					student.beterMelden();
    					
    					conversation.sendJSONMessage("{\"succes\":\"Status aangepast\"}");
    					
    					return;
    				}
    				
    				if (informatieSysteem.getDocent(request.get("gebruikersnaam").getAsString()) != null){
    					Docent docent = informatieSysteem.getDocent(request.get("gebruikersnaam").getAsString());
    					
    					docent.beterMelden();
    					
    					conversation.sendJSONMessage("{\"succes\":\"Status aangepast\"}");
    					
    					return;
    				}
          	break;
          	
          //Persoon ziek melden
          case "Ziek":  
    				if (informatieSysteem.getStudent(request.get("gebruikersnaam").getAsString()) != null){
    					Student student = informatieSysteem.getStudent(request.get("gebruikersnaam").getAsString());
    					
    					student.ziekMelden();
    					
    					conversation.sendJSONMessage("{\"succes\":\"Status aangepast\"}");
    					
    					return;
    				}
    				
    				if (informatieSysteem.getDocent(request.get("gebruikersnaam").getAsString()) != null){
    					Docent docent = informatieSysteem.getDocent(request.get("gebruikersnaam").getAsString());
    					
    					docent.ziekMelden();
    					
    					conversation.sendJSONMessage("{\"succes\":\"Status aangepast\"}");
    					
    					return;
    				}
          	break;
          //Persoon op afwezig zetten
          case "Afwezig": 
          	if (request.get("datum") != null && request.get("dagdeel") != null){
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      				formatter = formatter.withLocale( Locale.GERMANY );
      				LocalDate date = LocalDate.parse(request.get("datum").getAsString(), formatter);
            	
      				if (informatieSysteem.getStudent(request.get("gebruikersnaam").getAsString()) != null){
      					Student student = informatieSysteem.getStudent(request.get("gebruikersnaam").getAsString());
      					
      					student.nieuweStatus(status, date, request.get("dagdeel").getAsString());
      					
      					conversation.sendJSONMessage("{\"succes\":\"Status aangepast\"}");
      					
      					return;
      				}
      				
      				if (informatieSysteem.getDocent(request.get("gebruikersnaam").getAsString()) != null){
      					Docent docent = informatieSysteem.getDocent(request.get("gebruikersnaam").getAsString());
      					
      					docent.nieuweStatus(status, date, request.get("dagdeel").getAsString());
      					
      					conversation.sendJSONMessage("{\"succes\":\"Status aangepast\"}");
      					
      					return;
      				}
          	}else{
          		//Error terug sturen naar browser dat er geen datum of dagdeel is mee gegeven
          		conversation.sendJSONMessage("{\"error\":\"Geen datum of dagdeel gevonden\"}");
          	}
          	break;
          //Persoon op aanwezig zetten
          case "Aanwezig": 
          	if (request.get("datum") != null && request.get("dagdeel") != null){
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      				formatter = formatter.withLocale( Locale.GERMANY );
      				LocalDate date = LocalDate.parse(request.get("datum").getAsString(), formatter);
            	
      				if (informatieSysteem.getStudent(request.get("gebruikersnaam").getAsString()) != null){
      					Student student = informatieSysteem.getStudent(request.get("gebruikersnaam").getAsString());
      					
      					student.verwijderStatus(date, request.get("dagdeel").getAsString());
      					
      					conversation.sendJSONMessage("{\"succes\":\"Status aangepast\"}");
      					
      					return;
      				}
      				
      				if (informatieSysteem.getDocent(request.get("gebruikersnaam").getAsString()) != null){
      					Docent docent = informatieSysteem.getDocent(request.get("gebruikersnaam").getAsString());
      					
      					docent.verwijderStatus(date, request.get("dagdeel").getAsString());
      					
      					conversation.sendJSONMessage("{\"succes\":\"Status aangepast\"}");
      					
      					return;
      				}
          	}else{
          		conversation.sendJSONMessage("{\"error\":\"Geen datum of dagdeel gevonden\"}");
          	}
          	break;
          	
    			default: 
    				conversation.sendJSONMessage("{\"error\":\"Status bestaat niet\"}");

          	break;
        }
			}else{
				conversation.sendJSONMessage("{\"error\":\"Geen status of gebruikersnaam gevonden\"}");
			}
    } catch (Exception ex) {
    	ex.printStackTrace();
    }
	}
	
	//methode geeft json file van persoon info terug
	private void persoonInfo(Conversation conversation){
		//JsonParser maken om een json string naar een object om te zetten
		JsonParser parser = new JsonParser();
		//Gson object maken voor het parsen van json
		Gson gson = new Gson();
		//check of de string niet leeg is
		if (conversation.getRequestBodyAsString() == null){
			conversation.sendJSONMessage("{\"error\":\"Geen json data mee gegeven\"}");
			
			return;
		}
		
		try {
			//maakt er een json Object van de meegeven data
			JsonObject request = parser.parse(conversation.getRequestBodyAsString()).getAsJsonObject();
			
			//check of de data niet null is
			if (request.get("gebruikersnaam") != null){
				//check of de json kan worden aangemaakt
				if (informatieSysteem.getStudent(request.get("gebruikersnaam").getAsString()) != null){
					//maakt een nieuw student object aan
					Student student = informatieSysteem.getStudent(request.get("gebruikersnaam").getAsString());
					
					//maak json file aan en stuurt naar polymer
					conversation.sendJSONMessage(gson.toJson(student));
					
					return;
				}
				
				//check of de object niet leeg is
				if (informatieSysteem.getDocent(request.get("gebruikersnaam").getAsString()) != null){
					//maakt docent object aan
					Docent docent = informatieSysteem.getDocent(request.get("gebruikersnaam").getAsString());
					
					//maak json file aan en stuurt naar polymer
					conversation.sendJSONMessage(gson.toJson(docent));
					
					return;
				}
				
				
			}
			//check of de object niet leeg is
			if (request.get("studentNummer") != null){
				
				//check of het object niet leeg is
				if (informatieSysteem.getStudent(request.get("naam").getAsInt()) != null){
					//maak student object aan
					Student student = informatieSysteem.getStudent(request.get("naam").getAsInt());
					
					//maak van object een json file
					conversation.sendJSONMessage(gson.toJson(student));
				
					return;
				}
			}
    } catch (Exception ex) {
    	ex.printStackTrace();
    }
		//stuurt error code
		conversation.sendJSONMessage("{\"error\":\"Geen persoon gevonden\"}");
	}
	
	//methode void maakt json file van alle studenten
	private void alleStudenten(Conversation conversation){
		//Gson object maken voor het parsen van json
		Gson gson = new Gson();
		String jsonOut = "";
		
		//Json string maken van een java object
		jsonOut = gson.toJson(informatieSysteem.getStudenten()); 
		
		//Json terug sturen naar de browser
		conversation.sendJSONMessage(jsonOut);
	}
	
	//methode maakt json file van alle docenten
	private void alleDocenten(Conversation conversation){
		//Gson object maken voor het parsen van json
		Gson gson = new Gson();
		String jsonOut = "";
		
		//Json string maken van een java object
		jsonOut = gson.toJson(informatieSysteem.getDocenten()); 
		
		//Json terug sturen naar de browser
		conversation.sendJSONMessage(jsonOut);
	}
}

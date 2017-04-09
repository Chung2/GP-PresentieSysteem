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
	
	private void persoonInfo(Conversation conversation){
		//JsonParser maken om een json string naar een object om te zetten
		JsonParser parser = new JsonParser();
		//Gson object maken voor het parsen van json
		Gson gson = new Gson();
		
		if (conversation.getRequestBodyAsString() == null){
			conversation.sendJSONMessage("{\"error\":\"Geen json data mee gegeven\"}");
			
			return;
		}
		
		try {
			JsonObject request = parser.parse(conversation.getRequestBodyAsString()).getAsJsonObject();
			
			if (request.get("gebruikersnaam") != null){
				
				if (informatieSysteem.getStudent(request.get("gebruikersnaam").getAsString()) != null){
					Student student = informatieSysteem.getStudent(request.get("gebruikersnaam").getAsString());
					
					conversation.sendJSONMessage(gson.toJson(student));
					
					return;
				}
				
				if (informatieSysteem.getDocent(request.get("gebruikersnaam").getAsString()) != null){
					Docent docent = informatieSysteem.getDocent(request.get("gebruikersnaam").getAsString());
					
					conversation.sendJSONMessage(gson.toJson(docent));
					
					return;
				}
				
				
			}
			
			if (request.get("studentNummer") != null){
				if (informatieSysteem.getStudent(request.get("naam").getAsInt()) != null){
					Student student = informatieSysteem.getStudent(request.get("naam").getAsInt());
					
					conversation.sendJSONMessage(gson.toJson(student));
					
					return;
				}
			}
    } catch (Exception ex) {
    	ex.printStackTrace();
    }
		
		conversation.sendJSONMessage("{\"error\":\"Geen persoon gevonden\"}");
	}
	
	private void alleStudenten(Conversation conversation){
		//Gson object maken voor het parsen van json
		Gson gson = new Gson();
		String jsonOut = "";
		
		//Json string maken van een java object
		jsonOut = gson.toJson(informatieSysteem.getStudenten()); 
		
		//Json terug sturen naar de browser
		conversation.sendJSONMessage(jsonOut);
	}
	
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

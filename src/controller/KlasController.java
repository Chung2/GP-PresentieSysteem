package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.PrIS;
import server.Conversation;
import server.Handler;

public class KlasController implements Handler{
	private PrIS informatieSysteem;
	
	public KlasController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	
	public void handle(Conversation conversation) {
		 // data in backend moet naar frontend
		// klas/info geeft data weer van een klas
		// 
		//TODO: postRequest
		if (conversation.getRequestedURI().startsWith("/klas/info")) {
			ophalenKlas(conversation);
		}
		
		if (conversation.getRequestedURI().startsWith("/klassen/info")) {
			alleKlassenOphalen(conversation);
		}
	}
	
	private void alleKlassenOphalen(Conversation conversation) {
		//Gson object maken voor het parsen van json
		Gson gson = new Gson();
		String jsonOut = "";
		
		//Java object naar json string omzetten
		jsonOut = gson.toJson(informatieSysteem.getKlassen());
		
		//Json terug sturen naar browser
		conversation.sendJSONMessage(jsonOut);
	}
	
	private void ophalenKlas(Conversation conversation){
		//Gson object maken voor het parsen van json
		Gson gson = new Gson();
		
		//JsonParser maken om een json string naar een object om te zetten
		JsonParser parser = new JsonParser();
		
		//Check of er data mee is gegeven
		if (conversation.getRequestBodyAsString() == null){
			//Geen data mee gegeven error bericht terug sturen via json naar browser
			conversation.sendJSONMessage("{\"error\":\"Geen json data mee gegeven\"}");
			
			return;
		}
		
		//Json object maken van de mee gegeven data
	  JsonObject request = parser.parse(conversation.getRequestBodyAsString()).getAsJsonObject();
		String jsonOut = "";

		//Kijken of er een klas mee is gegeven en of deze bestaat
		if(request.get("klascode") != null && informatieSysteem.getKlas(request.get("klascode").getAsString()) != null){
			jsonOut = gson.toJson(informatieSysteem.getKlas(request.get("klascode").getAsString())); 
		}else{
			//Error terug sturen naar browser als de klas niet bestaat of leeg is
			jsonOut = "{\"error\":\"Klas niet gevonden\"}";
		}
		
		//Json terug sturen naar browser
		conversation.sendJSONMessage(jsonOut);	
	}
}

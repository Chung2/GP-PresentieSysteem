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
		
//		if (conversation.getRequestedURI().startsWith("/klas/slb")) {
//			ophalenSlb(conversation);
//		}
		
		if (conversation.getRequestedURI().startsWith("/klassen/info")) {
			alleKlassenOphalen(conversation);
		}
	}
	
	private void alleKlassenOphalen(Conversation conversation) {
		//TODO:getAlleKlasCodes: informatieSysteem.getKlas(klascode);
		Gson gson = new Gson();
		String jsonOut = "";
		
		jsonOut = gson.toJson(informatieSysteem.getKlassen()); //hardcoded een klas, als voorbeeld
		
		conversation.sendJSONMessage(jsonOut);

	}
	
	private void ophalenKlas(Conversation conversation){
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		
		if (conversation.getRequestBodyAsString() == null){
			conversation.sendJSONMessage("{\"error\":\"Geen json data mee gegeven\"}");
			
			return;
		}
		
		System.out.println(conversation.getRequestBodyAsString());
		
	  JsonObject request = parser.parse(conversation.getRequestBodyAsString()).getAsJsonObject();
		String jsonOut = "";

		if(request.get("klascode") != null && informatieSysteem.getKlas(request.get("klascode").getAsString()) != null){
			jsonOut = gson.toJson(informatieSysteem.getKlas(request.get("klascode").getAsString())); 
		}else{
			jsonOut = "{\"error\":\"Klas niet gevonden\"}";
		}
		
		if(informatieSysteem.getKlas(request.get("klascode").getAsString()) != null){
			jsonOut = gson.toJson(informatieSysteem.getKlas(request.get("klascode").getAsString()));
		} else {
			jsonOut = gson.toJson("{\"error\":\"klas bestaat niet\"}");
		}
		
		conversation.sendJSONMessage(jsonOut);	
	}
}

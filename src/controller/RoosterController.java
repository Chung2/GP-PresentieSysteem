package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.PrIS;
import model.persoon.Student;
import model.rooster.Rooster;
import server.Conversation;
import server.Handler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RoosterController implements Handler {
	private PrIS informatieSysteem;

	public RoosterController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}

	public void handle(Conversation conversation) {
		//data in backend naar frontend sturen
		//json files wordt gelinkt aan de volgende url. 
		if (conversation.getRequestedURI().startsWith("/rooster/docent")) {
			ophalenRoosterDocent(conversation);
		}

		if (conversation.getRequestedURI().startsWith("/rooster/les")) {
			ophalenLes(conversation);
		}

		if (conversation.getRequestedURI().startsWith("/rooster/student")) {
			ophalenRoosterKlas(conversation);
		}
		if (conversation.getRequestedURI().startsWith("/roosters/overzicht")) {
			ophalenAlleRoosters(conversation);
		}

	}
	
	//method ophalen rooster
	private void ophalenRoosterKlas(Conversation conversation) {
		//JsonParser maken om een json string naar een object om te zetten
		JsonParser parser = new JsonParser();
		//Gson object maken voor het parsen van json

		Gson gson = new Gson();
		//Kijk of er een json string is mee gegeven
		if (conversation.getRequestBodyAsString() == null){
			conversation.sendJSONMessage("{\"error\":\"Geen json data mee gegeven\"}");
			
			return;
		}
		//Json object maken van de mee gegeven data
		JsonObject request = parser.parse(conversation.getRequestBodyAsString()).getAsJsonObject();

		//System.out.println(request.get("klascode").getAsString());
		
		String jsonOut = "";
		// System.out.println(conversation.getRequestBodyAsString());

		//check of de het json object een klascode heeft
		if (request.get("klascode") != null) {
			// check of de string niet null is
			if (informatieSysteem.getRoosterKlas(request.get("klascode").getAsString()) != null) {
				// maakt een json object van de rooster door de klascode
				jsonOut = gson.toJson(informatieSysteem.getRoosterKlas(request.get("klascode").getAsString()));
				
				//stuurt json file naar polymer
				conversation.sendJSONMessage(jsonOut);

				return;
			}
		}
	}
	
	//methode ophalen alle roosters
	private void ophalenAlleRoosters(Conversation conversation) {
		//jsonparser object maken om json string naar object om te zetten
		JsonParser parser = new JsonParser();
		// gson object maken voor het parsen van json
		Gson gson = new Gson();

		String jsonOut = "";

		//json object maken van alle roosters
		jsonOut = gson.toJson(informatieSysteem.getRooster());

		//json sturen naar polymer
		conversation.sendJSONMessage(jsonOut);
	}

	// methode ophalen rooster voor docent
	private void ophalenRoosterDocent(Conversation conversation) {

		//jsonparser object maken om jsn string naar object om te zetten
		JsonParser parser = new JsonParser();
		//gson object maken voor het parsen van json
		Gson gson = new Gson();
		//checkt de string niet null is
		if (conversation.getRequestBodyAsString() == null){
			//geeft error terug
			conversation.sendJSONMessage("{\"error\":\"Geen json data mee gegeven\"}");
			
			return;
		}

		//maakt een object aanmaken van de mee gekregen data
		JsonObject request = parser.parse(conversation.getRequestBodyAsString()).getAsJsonObject();
		//System.out.println(request.get("gebruikersnaam").getAsString());
		String jsonOut = "";
		
		//check of de json object een gebruikersnaam bevat
		if (request.get("gebruikersnaam") != null) {
			//check of de json file bestaat na het zoeken van de gebruikersnaam
			if (informatieSysteem.getRoosterDocent(request.get("gebruikersnaam").getAsString()) != null) {
				//maakt de json object met de gebruikersnaam
				jsonOut = gson.toJson(informatieSysteem.getRoosterDocent(request.get("gebruikersnaam").getAsString()));
				//stuurt het naar polymer
				conversation.sendJSONMessage(jsonOut);

				return;
			}
		}
	}
	// methode individual les ophalen
	private void ophalenLes(Conversation conversation) {
		//JsonParser maken om een json string naar een object om te zetten
		JsonParser parser = new JsonParser();
		//Gson object maken voor het parsen van json
		Gson gson = new Gson();
		
		//Kijk of er een json string is mee gegeven
		if (conversation.getRequestBodyAsString() == null){
			conversation.sendJSONMessage("{\"error\":\"Geen json data mee gegeven\"}");
			
			return;
		}
		//Json object maken van de mee gegeven data
		JsonObject request = parser.parse(conversation.getRequestBodyAsString()).getAsJsonObject();
		
		
		String datum = request.get("datum").getAsString();
		String beginTijd = request.get("beginTijd").getAsString();
		String eindTijd = request.get("eindTijd").getAsString();
		String vakCode = request.get("vakCode").getAsString();
		String klasCode = request.get("klasCode").getAsString();

		String jsonOut = "";
		
		//Kijken of er een klas mee is gegeven en of deze bestaat
		if (datum != null || beginTijd != null || eindTijd != null || vakCode != null || klasCode != null) {
			//maakt een json file met de meegegeven data
			jsonOut = gson.toJson(informatieSysteem.getLes(datum, beginTijd, eindTijd, klasCode, vakCode));
		}else{
			//geeft error terug
			jsonOut = "{\"error\":\"Data niet goed mee gegeven\"}";
		}
		//stuurt naar polymer
		conversation.sendJSONMessage(jsonOut);
	}
}

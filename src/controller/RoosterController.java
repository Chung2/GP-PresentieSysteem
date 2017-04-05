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
		if (conversation.getRequestedURI().startsWith("/rooster")) {
			// ophalenRoosterKlas(conversation);
		}

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
			ophalenAlleRoostersKlassen(conversation);
		}

	}

	private void ophalenRoosterKlas(Conversation conversation) {
		JsonParser parser = new JsonParser();
		Gson gson = new Gson();
		
		if (conversation.getRequestBodyAsString() == null){
			conversation.sendJSONMessage("{\"error\":\"Geen json data mee gegeven\"}");
			
			return;
		}

		JsonObject request = parser.parse(conversation.getRequestBodyAsString()).getAsJsonObject();

		System.out.println(request.get("klascode").getAsString());

		String jsonOut = "";
		// System.out.println(conversation.getRequestBodyAsString());

		if (request.get("klascode") != null) {
			if (informatieSysteem.getRoosterKlas(request.get("klascode").getAsString()) != null) {
				jsonOut = gson.toJson(informatieSysteem.getRoosterKlas(request.get("klascode").getAsString()));

				conversation.sendJSONMessage(jsonOut);

				return;
			}
		}
	}

	private void ophalenAlleRoostersKlassen(Conversation conversation) {
		JsonParser parser = new JsonParser();
		Gson gson = new Gson();

		String jsonOut = "";

		jsonOut = gson.toJson(informatieSysteem.getRooster());

		conversation.sendJSONMessage(jsonOut);
	}

	private void ophalenRoosterDocent(Conversation conversation) {
		JsonParser parser = new JsonParser();
		Gson gson = new Gson();
		
		if (conversation.getRequestBodyAsString() == null){
			conversation.sendJSONMessage("{\"error\":\"Geen json data mee gegeven\"}");
			
			return;
		}

		JsonObject request = parser.parse(conversation.getRequestBodyAsString()).getAsJsonObject();
		System.out.println(request.get("gebruikersnaamDocent").getAsString());
		String jsonOut = "";

		if (request.get("gebruikersnaamDocent") != null) {
			if (informatieSysteem.getRoosterDocent(request.get("gebruikersnaamDocent").getAsString()) != null) {
				jsonOut = gson.toJson(informatieSysteem.getRoosterDocent(request.get("gebruikersnaamDocent").getAsString()));

				conversation.sendJSONMessage(jsonOut);

				return;
			}
		}
	}

	private void ophalenAlleRoostersDocenten(Conversation conversation) {
		Gson gson = new Gson();
		String jsonOut = "";
		
		if (conversation.getRequestBodyAsString() == null){
			conversation.sendJSONMessage("{\"error\":\"Geen json data mee gegeven\"}");
			
			return;
		}

		jsonOut = gson.toJson(informatieSysteem.getRooster());

		conversation.sendJSONMessage(jsonOut);
	}

	private void ophalenLes(Conversation conversation) {
		JsonParser parser = new JsonParser();
		Gson gson = new Gson();
		
		if (conversation.getRequestBodyAsString() == null){
			conversation.sendJSONMessage("{\"error\":\"Geen json data mee gegeven\"}");
			
			return;
		}
		
		JsonObject request = parser.parse(conversation.getRequestBodyAsString()).getAsJsonObject();
		String datum = request.get("datum").getAsString();
		String beginTijd = request.get("beginTijd").getAsString();
		String eindTijd = request.get("eindTijd").getAsString();
		String vakCode = request.get("vakCode").getAsString();
		String klasCode = request.get("klasCode").getAsString();

		String jsonOut = "";
		
		if (datum != null || beginTijd != null || eindTijd != null || vakCode != null || klasCode != null) {
			jsonOut = gson.toJson(informatieSysteem.getLes(datum, beginTijd, eindTijd, klasCode, vakCode));
		}
		conversation.sendJSONMessage(jsonOut);
	}
}

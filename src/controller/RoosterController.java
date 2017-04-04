package controller;

import com.google.gson.Gson;

import model.PrIS;
import server.Conversation;
import server.Handler;
import java.util.*;

public class RoosterController implements Handler{
	private PrIS informatieSysteem;
	
	public RoosterController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}
	
	public void handle(Conversation conversation) {
	  if (conversation.getRequestedURI().startsWith("/rooster")) {
	  	//ophalenRoosterKlas(conversation);
		}
	  
	  if(conversation.getRequestedURI().startsWith("/rooster/docent")){
	  	ophalenRoosterDocent(conversation);
	  }
	  
	  if (conversation.getRequestedURI().startsWith("/rooster/les")){
	  	ophalenLes(conversation);
	  }
	  
	  if(conversation.getRequestedURI().startsWith("/rooster/student"))
	  {
	  	ophalenRoosterKlas(conversation);
	  }
	}
	
	private void ophalenRoosterKlas(Conversation conversation){
		Gson gson = new Gson();
		String jsonOut = "";
		
		jsonOut = gson.toJson(informatieSysteem.getRoosterKlas("TICT-SIE-V1D")); 
		
		conversation.sendJSONMessage(jsonOut);
	}
	
	
	private void ophalenAlleRoostersKlassen(Conversation conversation){
		Gson gson = new Gson();
		String jsonOut = "";
		
		jsonOut = gson.toJson(informatieSysteem.getRooster()); 
		
		conversation.sendJSONMessage(jsonOut);
	}
	
	private void ophalenRoosterDocent(Conversation conversation) {
		Gson gson = new Gson();
		String jsonOut = "";
		
		jsonOut = gson.toJson(informatieSysteem.getRoosterDocent("peter.schuler@hu.nl")); 
		
		conversation.sendJSONMessage(jsonOut);
	}
	
	private void ophalenAlleRoostersDocenten(Conversation conversation){
		Gson gson = new Gson();
		String jsonOut = "";
		
		jsonOut = gson.toJson(informatieSysteem.getRooster()); 
		
		conversation.sendJSONMessage(jsonOut);
	}
	
	private void ophalenLes(Conversation conversation){
		// de calendar moet gefixt worden 
		
		/*Gson gson = new Gson();
		String jsonOut = "";
		
		jsonOut = gson.toJson(informatieSysteem.getLes(10:00,12:30,"TICT-SIE-V1D","TICT-V1OODC-15_2016)); 
		
		conversation.sendJSONMessage(jsonOut);*/
	}
}

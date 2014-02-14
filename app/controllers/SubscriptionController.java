package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class SubscriptionController extends Controller{
	
	/**
	 * 
	 * @return
	 */
	public static Result UpdateSubscriptionStatus(){
		
		// Get json information sent in
		JsonNode json = request().body().asJson();
		
		String entityType = json.get("entityType").asText();
		String entityId = json.get("entityId").asText();
		String username = json.get("username").asText();
		
		User user = User.findByName(username);

		// create return object
		ObjectNode result = Json.newObject();

		// swap subscription status
		if( User.doesUserSubscribeToEntity(user, entityId, entityType )){
			System.out.println("User already subscribes to entity, unsubscribe!");
			User.setUserEntitySubscriptionStatus(false, user, entityId, entityType);
			result.put("newState",false);
		}
		else{
			System.out.println("User does not already subscribe, SUBSCRIBE AWAY!!!");
			User.setUserEntitySubscriptionStatus(true, user, entityId, entityType);
			result.put("newState",true);
		}
		
		return ok(result);
	}

}

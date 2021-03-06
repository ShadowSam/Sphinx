package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.Request;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class SubscriptionController extends Controller{
	private static final ArrayList<String> ALL_EVENT_TYPES = new ArrayList(Arrays.asList("REPORT", "TIMESPENT", "CREATE", "UPDATE", "DELETE"));

	@Security.Authenticated(Secured.class)
	public static Result DeleteEntitySubscription(){

		// Get json information sent in
		JsonNode json = request().body().asJson();

		String entityType = json.get("entityType").asText();
		String entityId = json.get("entityId").asText();
		String username = request().username();
		
		ObjectNode result = Json.newObject();

		User user = User.findByName(username);
		user.setUserEntitySubscriptionStatus(false, user, entityId, entityType);

		return ok(result);
	}

	@Security.Authenticated(Secured.class)
	public static Result UpdateSubscriptionsEvents(){
		
		// Get json information sent in
		JsonNode json = request().body().asJson();
		
		String entityType = json.get("entityType").asText();
		String entityId = json.get("entityId").asText();
		String username = request().username();
		List<String> eventTypes = Arrays.asList( json.get("eventSubscriptions").asText().split(",") );

		ObjectNode result = Json.newObject();

		User user = User.findByName(username);
		user.updateEventsTiedToEntitySubscription(eventTypes,user,entityId,entityType);		

		return ok(result);
	}

	@Security.Authenticated(Secured.class)
	public static Result GetEntityEventSubscriptions(){

		// Get json information sent in
		JsonNode json = request().body().asJson();
		
		String entityType = json.get("entityType").asText();
		String entityId = json.get("entityId").asText();
		String username = request().username();  // user is showing up as null

		ObjectNode result = Json.newObject();

		User user = User.findByName(username);
		List<String> eventSubscriptions;

		eventSubscriptions = user.getEventsTiedToEntitySubscription(user, entityId, entityType);

		// Set output booleans for whether subscription is for x or y type of event
		for(int i=0; i<ALL_EVENT_TYPES.size(); i++){
			String currEventType = ALL_EVENT_TYPES.get(i);
			if( eventSubscriptions.contains(currEventType) ){
				result.put(currEventType,true);
			}else{
				result.put(currEventType,false);
			}
		}

		return ok(result);
	}
}
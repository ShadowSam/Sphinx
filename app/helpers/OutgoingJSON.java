package helpers;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

public class OutgoingJSON {
	
	protected String url;
	protected String json;
	
	public OutgoingJSON(){}
	
	public OutgoingJSON(String url, String json){
		this.url = url;
		this.json = json;
	}
	
	public Response sendJson(){
		Client c = ClientBuilder.newClient();
        WebTarget wt = c.target(url);

        Response response = wt.request().post(Entity.json(json));
        
        return response;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	

}
package controllers;

import java.util.List;

import models.EntityCollection;
import models.GetEntitiesForm;
import models.Initiative;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.adminTools;

public class AdminController extends Controller{
	public static Form<GetEntitiesForm> entitForm = Form.form(GetEntitiesForm.class);
	
	public static Result forceGetEntities(){
		Form<GetEntitiesForm> ff = entitForm.bindFromRequest();
		Ingester ingester = new Ingester();
		EntityCollection ec = ingester.getEntitiesSince(ff.get().date);
		List<Initiative> li = ec.getInitiatives();
		for(Initiative i : li){
			i.upsert();
		}
		return ok(adminTools.render("Migration Started",entitForm ));
	}

}

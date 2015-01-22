package edu.carleton.COMP4601.a1.Main;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import edu.carleton.COMP4601.a1.Model.Action;
import edu.carleton.COMP4601.a1.Model.Document;

public class DocumentAction extends Action {

	public DocumentAction(UriInfo uriInfo, Request request, String id) {
		super(uriInfo, request, id);
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Document getDocumentXML() {
		Document a = DatabaseManager.getInstance().findDocument(Integer.parseInt(id));
		if (a == null) {
			throw new RuntimeException("No such Document: " + id);
		}
		return a;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Document getDocumentHTML() {
		Document a = DatabaseManager.getInstance().findDocument(Integer.parseInt(id));
		if (a == null) {
			throw new RuntimeException("No such Document: " + id);
		}
		return a;
	}

	@DELETE
	private Response deleteAccount() {
		Response res;
		if (DatabaseManager.getInstance().removeDocument(Integer.parseInt(id)) == null) {
			res = Response.noContent().build();
		}
		else {
			res = Response.ok().build();
		}
		
		return res;
	}
}

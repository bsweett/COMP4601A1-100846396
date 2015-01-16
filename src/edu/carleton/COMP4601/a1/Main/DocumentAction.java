package edu.carleton.COMP4601.a1.Main;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import edu.carleton.COMP4601.a1.Model.Action;
import edu.carleton.COMP4601.a1.Model.Document;

public class DocumentAction extends Action {

	public DocumentAction(UriInfo uriInfo, Request request, String id) {
		super(uriInfo, request, id);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Document getDocumentXML() {		
		Document d = DatabaseManager.getInstance().findDocument(new Integer(id));
		if (d == null) {
			throw new RuntimeException("No such document: " + id);
		}
		return d;
	}
	
}

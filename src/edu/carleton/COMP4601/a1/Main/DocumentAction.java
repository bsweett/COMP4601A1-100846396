package edu.carleton.COMP4601.a1.Main;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

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
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response postDocument(JAXBElement<Document> doc) {
		Document d = doc.getValue();
		return postAndGetResponse(d);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateDocument(JAXBElement<Document> doc) {
		Response res;
		Document oldDocument = DatabaseManager.getInstance().findDocument(Integer.parseInt(id));
		Document newDocument = doc.getValue();
		
		if(oldDocument == null) {
			res = Response.noContent().build();
		}
		else {
			if(DatabaseManager.getInstance().updateDocument(newDocument, oldDocument)) {
				res = Response.ok().build();
			}
			else {
				res = Response.noContent().build();
			}
		}
		return res;
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
	
	private Response postAndGetResponse(Document document) {
		Response res;
			Document oldDocument = DatabaseManager.getInstance().findDocument(Integer.parseInt(id));
			if(oldDocument != null) {
				if(DatabaseManager.getInstance().updateDocument(document, oldDocument)) {
					res = Response.ok().build();
				}
				else {
					res = Response.noContent().build();
				}
			} else {
				if(DatabaseManager.getInstance().addNewDocument(document)) {
					res = Response.ok().build();
				}
				else {
					res = Response.noContent().build();
				}
			}		
		return res;
	}
}

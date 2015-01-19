package edu.carleton.COMP4601.a1.Model;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import edu.carleton.COMP4601.a1.Main.DatabaseManager;

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
	/*
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Document getDocumentHTML() {
		Document a = DatabaseManager.getInstance().findDocument(Integer.parseInt(id));
		if (a == null) {
			throw new RuntimeException("No such Document: " + id);
		}
		return a;
	}
*/
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putAccount(JAXBElement<Document> doc) {
		Document d = doc.getValue();
		return putAndGetResponse(d);
	}

	@DELETE
	public void deleteAccount() {
		if (DatabaseManager.getInstance().removeDocument(Integer.parseInt(id)) == null)
			throw new RuntimeException("Document " + id + " not found");
	}
	
	private Response putAndGetResponse(Document document) {
		Response res;
			Document oldDocument = DatabaseManager.getInstance().findDocument(Integer.parseInt(id));
			if(oldDocument != null) {
				DatabaseManager.getInstance().updateDocument(document, oldDocument);
				res = Response.ok().build();
			} else {
				DatabaseManager.getInstance().addNewDocument(document);
				res = Response.created(uriInfo.getAbsolutePath()).build();
			}		
		return res;
	}
}

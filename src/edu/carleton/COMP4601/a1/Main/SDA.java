package edu.carleton.COMP4601.a1.Main;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import edu.carleton.COMP4601.a1.Model.Document;

@Path("/sda")
public class SDA {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	private String name;

	public SDA() {
		name = "COMP4601 Searchable Document Archive";
	}

	@GET
	public String printName() {
		return name;
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String sayXML() {
		return "<?xml version=\"1.0\"?>" + "<sda> " + name + " </sda>";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtml() {
		return "<html> " + "<title>" + name + "</title>" + "<body><h1>" + name
				+ "</body></h1>" + "</html> ";
	}
	
	@GET
	@Path("documents")
	@Produces(MediaType.APPLICATION_XML)
	public ArrayList<Document> getDocumentsXML() {
		return null;
	}
	
	@GET
	@Path("documents")
	@Produces(MediaType.TEXT_HTML)
	public ArrayList<Document> getDocumentsHTML() {
		return null;
	}
	
	@GET
	@Path("{id}")
	public DocumentAction getDocument(@PathParam("id") String id) {
		return new DocumentAction(uriInfo, request, id);
	}
	
	@DELETE
	@Path("{id}")
	public DocumentAction deleteDocument(@PathParam("id") String id) {
		return new DocumentAction(uriInfo, request, id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createDocument(JAXBElement<Document> doc) {
		Response res;
		Document document = doc.getValue();
		document.setId(DatabaseManager.getInstance().getNextIndex());
		//System.out.println("Links: " + d.getLinks().size() + "tags: " + d.getTags().size());
		
		if(DatabaseManager.getInstance().addNewDocument(document)) {
			res = Response.ok().build();
		}
		else {
			res = Response.noContent().build();
		}
		
		return res;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateDocument(JAXBElement<Document> doc) {
		Response res;
		Document newDocument = doc.getValue();
		Document oldDocument = DatabaseManager.getInstance().findDocument(newDocument.getId());
		
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
}

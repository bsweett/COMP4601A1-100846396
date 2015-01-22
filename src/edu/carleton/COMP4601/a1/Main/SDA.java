package edu.carleton.COMP4601.a1.Main;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
	@Consumes(MediaType.APPLICATION_XML)
	public ArrayList<Document> getDocumentsXML() {
		ArrayList<Document> documents = DatabaseManager.getInstance().getDocuments();
		
		if(documents == null) {
			throw new RuntimeException("No documents exist");
		}
		
		return documents;
	}
	
	@GET
	@Path("documents")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_XML)
	public String getDocumentsHTML() {
		ArrayList<Document> documents = DatabaseManager.getInstance().getDocuments();
		
		if(documents == null) {
			throw new RuntimeException("No documents exist");
		}
		
		return documentsToHTML(documents);
	}
	/*
	@GET
	@Path("{id}")
	public DocumentAction getDocument(@PathParam("id") String id) {
		return new DocumentAction(uriInfo, request, id);
	}
	*/
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Document getDocumentXML(@PathParam("id") String id) {
		Document a = DatabaseManager.getInstance().findDocument(Integer.parseInt(id));
		if (a == null) {
			throw new RuntimeException("No such Document: " + id);
		}
		return a;
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_XML)
	public String getDocumentHTML(@PathParam("id") String id) {
		Document d = DatabaseManager.getInstance().findDocument(Integer.parseInt(id));
		if (d == null) {
			throw new RuntimeException("No such Document: " + id);
		}
		
		return documentToHTML(d);
	}
	
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_XML)
	private Response deleteAccount(@PathParam("id") String id) {
		Response res;
		if (DatabaseManager.getInstance().removeDocument(Integer.parseInt(id)) == null) {
			res = Response.noContent().build();
		}
		else {
			res = Response.ok().build();
		}
		
		return res;
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
	
	public String documentToHTML(Document d) {
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("<html>");
		htmlBuilder.append("<head><title>" + d.getName() + "</title></head>");
		htmlBuilder.append("<body><h1>" + d.getName() + "</h1>");
		htmlBuilder.append("<p>" + d.getText() + "</p>");
		htmlBuilder.append("<h1> Links </h1>");
		htmlBuilder.append("<ul>");
		for (String s : d.getLinks())
		{
			htmlBuilder.append("<li>");
			htmlBuilder.append(s);
			htmlBuilder.append("</li>");
		}
		htmlBuilder.append("</ul>");
		htmlBuilder.append("<h1> Tags </h1>");
		htmlBuilder.append("<ul>");
		for (String s : d.getTags())
		{
			htmlBuilder.append("<li>");
			htmlBuilder.append(s);
			htmlBuilder.append("</li>");
		}
		htmlBuilder.append("</ul></body>");
		htmlBuilder.append("</html>");
		
		return htmlBuilder.toString();
	}
	
	public String documentsToHTML(ArrayList<Document> documents) {
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("<html>");
		htmlBuilder.append("<head><title> All Documents </title></head>");
		htmlBuilder.append("<body>");
		for(Document d : documents) {
			htmlBuilder.append("<h1>" + d.getName() + "</h1>");
			htmlBuilder.append("<p>" + d.getText() + "</p>");
			htmlBuilder.append("<h1> Links </h1>");
			htmlBuilder.append("<ul>");
			for (String s : d.getLinks())
			{
				htmlBuilder.append("<li>");
				htmlBuilder.append(s);
				htmlBuilder.append("</li>");
			}
			htmlBuilder.append("</ul>");
			htmlBuilder.append("<h1> Tags </h1>");
			htmlBuilder.append("<ul>");
			for (String s : d.getTags())
			{
				htmlBuilder.append("<li>");
				htmlBuilder.append(s);
				htmlBuilder.append("</li>");
			}
			htmlBuilder.append("</ul>");
		}
		htmlBuilder.append("</body>");
		htmlBuilder.append("</html>");
		
		return htmlBuilder.toString();
	}
}

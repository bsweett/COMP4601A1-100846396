package edu.carleton.COMP4601.a1.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
	@Produces(MediaType.TEXT_XML)
	public String sayXML() {
		return "<?xml version=\"1.0\"?>" + "<sda> " + name + " </sda>";
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtml() {
		return "<html> " + "<title>" + name + "</title>" + "<body><h1>" + name
				+ "</body></h1>" + "</html> ";
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newAccount(@FormParam("name") String id,
			@FormParam("text") String text,
			@FormParam("tags") List<String> tags,
			@FormParam("links") List<String> links,
			@Context HttpServletResponse servletResponse) throws IOException {

		if(id == null || text == null || tags == null) {
			return Response.status(400).build();
		}
		
		
		
		return Response.ok().build();
		
		/*
		String newDescription = description;
		if (newDescription == null)
			newDescription = "";

		int newId = new Integer(id).intValue();
		int newBalance = new Integer(balance).intValue();
		
		if(newBalance > 0) {
			Accounts.getInstance().open(newId, newBalance, newDescription);
			servletResponse.sendRedirect("../create_account.html");
		} else {
			servletResponse.sendRedirect("../error.html");
		}*/
		
		
	}
}

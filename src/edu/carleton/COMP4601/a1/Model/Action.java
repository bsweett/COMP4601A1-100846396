package edu.carleton.COMP4601.a1.Model;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

public class Action {
	@Context
	protected UriInfo uriInfo;
	@Context
	protected Request request;

	protected String id;

	public Action(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

}

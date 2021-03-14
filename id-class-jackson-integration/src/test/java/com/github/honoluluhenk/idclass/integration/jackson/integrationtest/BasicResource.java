package com.github.honoluluhenk.idclass.integration.jackson.integrationtest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.honoluluhenk.idclass.ID;
import com.github.honoluluhenk.idclass.integration.jackson.integrationtest.fixtures.SomeDTO;
import com.github.honoluluhenk.idclass.integration.jackson.integrationtest.fixtures.SomeEntity;

@Path("/basic")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BasicResource {

	@POST
	@Path("id-in-body")
	public String idInBody(
			SomeDTO value
	) {
		return normalizeForTest(value.getId());
	}

	@POST
	@Path("id-in-path-param/{id}")
	public String idInPathParam(
			@PathParam("id") ID<SomeEntity> id
	) {
		return normalizeForTest(id);
	}

	@POST
	@Path("id-in-response/{id}")
	public ID<SomeEntity> idInTextResponse(
			@PathParam("id") String id
	) {
		return ID.of(id, SomeEntity.class);
	}

	@POST
	@Path("id-in-response-body/{id}")
	public SomeDTO idInResponseBody(
			@PathParam("id") String id
	) {
		return new SomeDTO(ID.of(id, SomeEntity.class), "ignored");
	}

	private String normalizeForTest(ID<?> id) {
		return id.getId() + ":" + id.getEntityClass().getSimpleName();
	}
}

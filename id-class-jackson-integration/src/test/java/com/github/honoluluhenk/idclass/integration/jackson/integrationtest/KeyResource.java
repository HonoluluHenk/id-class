package com.github.honoluluhenk.idclass.integration.jackson.integrationtest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.honoluluhenk.idclass.ID;
import com.github.honoluluhenk.idclass.integration.jackson.integrationtest.fixtures.SomeEntity;
import com.github.honoluluhenk.idclass.integration.jackson.integrationtest.fixtures.SomeMap;

@Path("/key")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KeyResource {

	@POST
	@Path("id-as-map-key-in-request-body")
	public String idAsMapKeyInRequestBody(
			SomeMap someMap
	) {
		String result = someMap.getIdStringMap()
				.entrySet()
				.stream()
				.map(Object::toString)
				.collect(Collectors.joining("|"));

		return result;
	}

	@POST
	@Path("id-as-map-key-in-response-body")
	public SomeMap idAsMapKeyInResponseBody() {
		Map<ID<SomeEntity>, String> map = new HashMap<>();
		map.put(ID.of("8e76ffba-78b1-4fc0-b975-33ff73394b41", SomeEntity.class), "Hello World");
		map.put(ID.of("9819a179-e30d-4b86-91f2-784b99749612", SomeEntity.class), "This is a test");

		SomeMap response = new SomeMap()
				.setIdStringMap(map);

		return response;
	}

	private String normalizeForTest(ID<?> id) {
		return id.getId() + ":" + id.getEntityClass().getSimpleName();
	}
}

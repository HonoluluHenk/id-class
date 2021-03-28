package com.github.honoluluhenk.idclass.integration.jackson.integrationtest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class KeySerializationAndDeserializationTest {

	@Test
	void id_works_as_map_key_in_request_body() {
		given()
				.when()
				.contentType(ContentType.JSON)
				.body("{\"idStringMap\": {"
						+ "\"8e76ffba-78b1-4fc0-b975-33ff73394b41\": \"Hello World\","
						+ "\"9819a179-e30d-4b86-91f2-784b99749612\": \"This is a test\""
						+ "}}")
				.post("/key/id-as-map-key-in-request-body")
				.then()
				.log().ifValidationFails()
				.statusCode(200)
				.body(is("ID[8e76ffba-78b1-4fc0-b975-33ff73394b41,SomeEntity]=Hello World"
						+ "|"
						+ "ID[9819a179-e30d-4b86-91f2-784b99749612,SomeEntity]=This is a test"))
		;
	}

	@Test
	void id_works_as_map_key_in_response_body() {
		given()
				.when()
				.contentType(ContentType.JSON)
				//				.body("")
				.post("/key/id-as-map-key-in-response-body")
				.then()
				.log().ifValidationFails()
				.statusCode(200)
				.body("idStringMap[\"8e76ffba-78b1-4fc0-b975-33ff73394b41\"]", equalTo("Hello World"))
				.body("idStringMap[\"9819a179-e30d-4b86-91f2-784b99749612\"]", equalTo("This is a test"))
		;
	}

}

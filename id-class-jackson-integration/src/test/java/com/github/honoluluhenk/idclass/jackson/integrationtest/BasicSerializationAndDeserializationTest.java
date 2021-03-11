package com.github.honoluluhenk.idclass.jackson.integrationtest;

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
class BasicSerializationAndDeserializationTest {

	@Test
	void id_in_body() {
		given()
				.when()
				.contentType(ContentType.JSON)
				.body("{\"id\":\"8e76ffba-78b1-4fc0-b975-33ff73394b41\"}")
				.post("/basic/id-in-body")
				.then()
				.log().ifValidationFails()
				.statusCode(200)
				// why Jackson does not encase this in double-quotes '"' as required by JSON standard is beyond me
				//				.body(is("\"8e76ffba-78b1-4fc0-b975-33ff73394b41:SomeEntity\""));
				.body(is("8e76ffba-78b1-4fc0-b975-33ff73394b41:SomeEntity"))
		;
	}

	@Test
	void id_in_path_param() {
		given()
				.when()
				.contentType(ContentType.JSON)
				.post("/basic/id-in-path-param/8e76ffba-78b1-4fc0-b975-33ff73394b41")
				.then()
				.log().ifValidationFails()
				.statusCode(200)
				.body(is("8e76ffba-78b1-4fc0-b975-33ff73394b41:SomeEntity"))
		;
	}

	@Test
	void id_in_response() {
		given()
				.when()
				.contentType(ContentType.JSON)
				.post("/basic/id-in-response/8e76ffba-78b1-4fc0-b975-33ff73394b41")
				.then()
				.log().ifValidationFails()
				.statusCode(200)
				// reminder: a string-as-JSON response gets quoted in JSON, this is specified behavior!
				.body(is("\"8e76ffba-78b1-4fc0-b975-33ff73394b41\""))
		;
	}

	@Test
	void id_in_response_body() {
		given()
				.when()
				.contentType(ContentType.JSON)
				.post("/basic/id-in-response-body/8e76ffba-78b1-4fc0-b975-33ff73394b41")
				.then()
				.log().ifValidationFails()
				.statusCode(200)
				.body("id", equalTo("8e76ffba-78b1-4fc0-b975-33ff73394b41"))
		;
	}
}

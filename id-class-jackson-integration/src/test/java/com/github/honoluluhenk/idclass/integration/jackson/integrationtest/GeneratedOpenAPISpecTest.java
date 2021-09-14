package com.github.honoluluhenk.idclass.integration.jackson.integrationtest;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GeneratedOpenAPISpecTest {
	@Test
	void specifies_ID_as_UUID() {
		given()
				.when()
				.get("/q/openapi?format=json")
				.then()
				.log().ifValidationFails()
				.statusCode(200)
				// @formatter:off
				.body("paths.'/basic/id-in-path-param/{id}'.post.parameters[0].name", is("id"))
				.body("paths.'/basic/id-in-path-param/{id}'.post.parameters[0].schema.$ref", is("#/components/schemas/IDSomeEntity"))
				.body("components.schemas.IDSomeEntity.properties.id.$ref", is("#/components/schemas/UUID"))
				.body("components.schemas.UUID.type", is("string"))
				.body("components.schemas.UUID.format", is("uuid"))
				// @formatter:on
		;
	}

	@Test
	void understands_ID_Key_in_object() {
		given()
				.when()
				.get("/q/openapi?format=json")
				.then()
				.log().ifValidationFails()
				.statusCode(200)
				.body("components.schemas.SomeMap.properties.idStringMap.type", is("object"))
		;
	}

}

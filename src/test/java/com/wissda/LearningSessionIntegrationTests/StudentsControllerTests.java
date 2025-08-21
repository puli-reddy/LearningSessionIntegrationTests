package com.wissda.LearningSessionIntegrationTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;
//import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentsControllerTests {
	private static String studentId;

	@BeforeAll
	static void setup() {
		// Use system property 'service.url' if provided, else fall back to environment variable 'QA_URL', else default to QA port 8082
        RestAssured.baseURI = System.getProperty("serviceUrl");
	}

	@Test
	@Order(1)
	void testCreateStudent() {
		StudentDTO studentDTO = StudentDTO.builder()
				.name("Test Name")
				.email("testEmail@gmail.com")
				.build();

		Address address = Address.builder()
				.city("Bengaluru")
				.district("Bengaluru")
				.state("Karnataka")
				.street("Marathahalli")
				.pincode(560037)
				.build();

		studentDTO.setAddress(address);

		ValidatableResponse response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(studentDTO)
				.when()
				.post("/students/create")
				.then()
				.statusCode(200)
				.body("id", notNullValue())
				.body("name", equalTo("Test Name"))
				.body("email", equalTo("testEmail@gmail.com"));

		studentId = response.extract().path("id");
		log.info("studentId : " + studentId);
		Assertions.assertNotNull(studentId, "Student ID should not be null after creation");
	}

	@Test
	@Order(2)
	void testGetStudent() {
		StudentDTO studentDTO = StudentDTO.builder()
				.studentId(studentId)
				.build();

		log.info("studentId : " + studentId);

		ValidatableResponse response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(studentDTO)
				.when()
				.post("/students/byId")
				.then()
				.statusCode(200)
				.body("id", notNullValue())
				.body("name", equalTo("Test Name"))
				.body("email", equalTo("testEmail@gmail.com"));
	}

	@Test
	@Order(3)
	void testDeleteStudent() {
		StudentDTO studentDTO = StudentDTO.builder()
				.studentId(studentId)
				.build();

		ValidatableResponse response = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(studentDTO)
				.when()
				.post("/students/delete")
				.then()
				.statusCode(400);
	}

}

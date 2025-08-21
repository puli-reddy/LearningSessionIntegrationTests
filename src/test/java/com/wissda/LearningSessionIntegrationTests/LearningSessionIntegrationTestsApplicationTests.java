package com.wissda.LearningSessionIntegrationTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Value;

import static org.hamcrest.Matchers.*;
//import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LearningSessionIntegrationTestsApplicationTests {

	@Value("spring.admin.domain")
	private static String domain;

	private String studentId;

	@BeforeAll
    static void setup() {
		RestAssured.baseURI = domain;
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
		Assertions.assertNotNull(studentId, "Student ID should not be null after creation");
	}

}

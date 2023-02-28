package com.app.main;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class MainApplicationTests {

    private static WireMockServer wikiMockServer;

    @BeforeAll
    static void beforeAll() {
        wikiMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
                .port(8081));
        wikiMockServer.start();
        wikiMockServer.stubFor(WireMock
                .get(urlEqualTo("/wiki/List_of_country_calling_codes"))
                .willReturn(aResponse()
                        .withBodyFile("wikiResponse.html")
                        .withStatus(200)
                )
        );
    }

    @AfterAll
    static void afterAll() {
        wikiMockServer.stop();
    }

    @Test
    @DisplayName("Given phone number '1' - When http get request - Then should return error")
    public void testShortPhoneNumber() {
        // Given
        String phoneNumber = "1";

        // When
        // Then
        io.restassured.RestAssured.get("api/v1/countryPhoneCode?phoneNumber=" + phoneNumber)
                .then()
                .assertThat()
                .body("message", equalTo("Invalid phone number. " +
                        "It has to start with '+' and have digits only, " +
                        "but at least 2 and no more than 15"))
                .body("status", equalTo(500));
    }

    @Test
    @DisplayName("Given phone number '123456789101112131415' - When http get request - Then should return error")
    public void testLongPhoneNumber() {
        // Given
        String phoneNumber = "123456789101112131415";

        // When
        // Then
        io.restassured.RestAssured.get("api/v1/countryPhoneCode?phoneNumber=" + phoneNumber)
                .then()
                .assertThat()
                .body("message", equalTo("Invalid phone number. " +
                        "It has to start with '+' and have digits only, " +
                        "but at least 2 and no more than 15"))
                .body("status", equalTo(500));
    }

    @Test
    @DisplayName("Given phone number '12?34567' - When http get request - Then should return error")
    public void testPhoneNumberWithInvalidCharacters() {
        // Given
        String phoneNumber = "12?34567";

        // When
        // Then
        io.restassured.RestAssured.get("api/v1/countryPhoneCode?phoneNumber=" + phoneNumber)
                .then()
                .assertThat()
                .body("message", equalTo("Invalid phone number. " +
                        "It has to start with '+' and have digits only, " +
                        "but at least 2 and no more than 15"))
                .body("status", equalTo(500));
    }

    @Test
    @DisplayName("Given phone number '1234567a' - When http get request - Then should return error")
    public void testPhoneNumberWithLetters() {
        // Given
        String phoneNumber = "1234567a";

        // When
        // Then
        io.restassured.RestAssured.get("api/v1/countryPhoneCode?phoneNumber=" + phoneNumber)
                .then()
                .assertThat()
                .body("message", equalTo("Invalid phone number. " +
                        "It has to start with '+' and have digits only, " +
                        "but at least 2 and no more than 15"))
                .body("status", equalTo(500));
    }

    @Test
    @DisplayName("Given empty phone number - When http get request - Then should return error")
    public void testEmptyPhoneNumber() {
        // Given
        String phoneNumber = "";

        // When
        // Then
        io.restassured.RestAssured.get("api/v1/countryPhoneCode?phoneNumber=" + phoneNumber)
                .then()
                .assertThat()
                .body("message", equalTo("Invalid phone number. " +
                        "It has to start with '+' and have digits only, " +
                        "but at least 2 and no more than 15"))
                .body("status", equalTo(500));
    }

    @Test
    @DisplayName("Given unassigned phone number '999913' - When http get request - Then should return error")
    public void testUnassignedPhoneNumber() {
        // Given
        String phoneNumber = "999913";

        // When
        // Then
        io.restassured.RestAssured.get("api/v1/countryPhoneCode?phoneNumber=" + phoneNumber)
                .then()
                .assertThat()
                .body("message", equalTo("Can't identify country for this phone number"))
                .body("status", equalTo(500));
    }

    @Test
    @DisplayName("Given phone number '123456789101112' - When http get request - Then should return Canada and United States")
    public void testPhoneNumberWithExactly15Digits() {
        // Given
        String phoneNumber = "123456789101112";

        // When
        // Then
        io.restassured.RestAssured.get("api/v1/countryPhoneCode?phoneNumber=" + phoneNumber)
                .then()
                .assertThat()
                .body("$", hasItems("Canada", "United States"))
                .body("$", hasSize(2));
    }

    @Test
    @DisplayName("Given phone number '92' - When http get request - Then should return Pakistan")
    public void testPhoneNumberWithExactly2Digits() {
        // Given
        String phoneNumber = "92";

        // When
        // Then
        io.restassured.RestAssured.get("api/v1/countryPhoneCode?phoneNumber=" + phoneNumber)
                .then()
                .assertThat()
                .body("$", hasItems("Pakistan"))
                .body("$", hasSize(1));
    }

    @Test
    @DisplayName("Given phone number '134011' - When http get request - Then should return US Virgin Islands")
    public void testCorrectPhoneNumber() {
        // Given
        String phoneNumber = "134011";

        // When
        // Then
        io.restassured.RestAssured.get("api/v1/countryPhoneCode?phoneNumber=" + phoneNumber)
                .then()
                .assertThat()
                .body("$", hasItems("US Virgin Islands"))
                .body("$", hasSize(1));
    }

    @Test
    @DisplayName("Given phone number '7919' - When http get request - Then should return Kazakhstan and Russia")
    public void testPhoneNumberWith2Countries() {
        // Given
        String phoneNumber = "7919";

        // When
        // Then
        io.restassured.RestAssured.get("api/v1/countryPhoneCode?phoneNumber=" + phoneNumber)
                .then()
                .assertThat()
                .body("$", hasItems("Russia", "Kazakhstan"))
                .body("$", hasSize(2));
    }

    @Test
    @DisplayName("Given phone number '3556' - When http get request - Then should return Albania")
    public void testPhoneNumberWithOnlyOneCountry() {
        // Given
        String phoneNumber = "3556";

        // When
        // Then
        io.restassured.RestAssured.get("api/v1/countryPhoneCode?phoneNumber=" + phoneNumber)
                .then()
                .assertThat()
                .body("$", hasItems("Albania"))
                .body("$", hasSize(1));
    }
}

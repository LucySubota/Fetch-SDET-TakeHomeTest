package com.fetch.test;

import com.fetch.TestConfiguration;
import com.fetch.domains.LocationNameResponse;
import com.fetch.domains.ZipPostCodeResponse;
import com.fetch.utils.Helper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ContextConfiguration(classes = TestConfiguration.class)
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
public class GeoCodingTest {

    @Autowired
    private Helper helper;

    @Value("${geocoding.url}")
    private String geoCodingUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCodingTest.class);
    private static final String COORDINATES_BY_LOCATION_NAME_ENDPOINT = "/direct";
    private static final String COORDINATES_BY_ZIP_OR_POST_CODE_ENDPOINT = "/zip";
    private static final String TOKEN = "f897a99d971b5eef57be6fafa0d83239";

    @Test
    public void processLocations() {
        String args = System.getProperty("locations");

        if (args == null) {
            throw new IllegalArgumentException("Please provide command line arguments using -Dlocations");
        }

        String[] locationArgs = helper.customSplit(args);

        List<Object> results =  Arrays.stream(locationArgs)
                     .map(this::getLocationResponse)
                     .collect(Collectors.toList());

        LOGGER.info("Location Results:");
        results.forEach(
            location -> {
                if (location instanceof LocationNameResponse loc) {
                    LOGGER.info("Place: {}, Country: {}, State: {}, Latitude: {}, Longitude: {}",
                                loc.getName(), loc.getCountry(), loc.getState(), loc.getLat(), loc.getLon());
                } else if (location instanceof ZipPostCodeResponse zip) {
                    LOGGER.info("ZIP: {}, Name: {}, Country: {}, Latitude: {}, Longitude: {}",
                                zip.getZip(), zip.getName(), zip.getCountry(), zip.getLat(), zip.getLon());
                }
        });
    }

    public Object getLocationResponse(String location) {
        if (helper.isZipOrPostCode(location)) {
            return getZipResponse(location);
        } else {
            return getLocationNameResponse(location);
        }
    }

    public ZipPostCodeResponse getZipResponse(String zipCode) {
        try {
            Response response = RestAssured.given().baseUri(geoCodingUrl)
                                      .queryParam("zip", zipCode)
                                      .queryParam("appid", TOKEN)
                                      .queryParam("limit", 5)
                    .when()
                    .get(COORDINATES_BY_ZIP_OR_POST_CODE_ENDPOINT);
            if (response.getStatusCode() == 404) {
                LOGGER.info("Location with zip/post code " + zipCode + " was not found." );
                return null;
            }
            return response.jsonPath().getObject("", ZipPostCodeResponse.class);
        }catch (Exception e){
            LOGGER.info("Unable to get response for " + zipCode + " zip/post code: " + e.getMessage());
            return null;
        }
    }

    public LocationNameResponse getLocationNameResponse(String location) {
        try {
            Response response = RestAssured.given().baseUri(geoCodingUrl)
                                                   .queryParam("q", location.trim())
                                                   .queryParam("appid", TOKEN)
                                                   .queryParam("limit", 5)
                    .when()
                    .get(COORDINATES_BY_LOCATION_NAME_ENDPOINT)
                    .then().statusCode(200).extract().response();

            LocationNameResponse locationNameResponse = new LocationNameResponse();
            if(response.jsonPath().get("[0].name") == null){
                LOGGER.info("Location " + location + " was not found.");
                return null;
            }
            locationNameResponse.setName(response.jsonPath().get("[0].name"));
            locationNameResponse.setLat(response.jsonPath().get("[0].lat"));
            locationNameResponse.setLon(response.jsonPath().get("[0].lon"));
            locationNameResponse.setCountry(response.jsonPath().get("[0].country"));
            locationNameResponse.setState(response.jsonPath().get("[0].state"));
            return locationNameResponse;
        }catch (Exception e){
            LOGGER.info("Unable to get response for " + location + " city/state code: " + e.getMessage());
            return null;
        }
    }

}

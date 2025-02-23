package com.fetch.test;

import com.fetch.TestConfiguration;
import com.fetch.domains.LocationNameResponse;
import com.fetch.domains.ZipPostCodeResponse;
import com.fetch.utils.Helper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@ContextConfiguration(classes = TestConfiguration.class)
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
public class GeoCodingIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoCodingTest.class);

    @Autowired
    private Helper helper;

    @Autowired
    private GeoCodingTest geoCodingTest;

    @Test
    public void testProcessLocationsWithMixedInputsAndDifferentQuotations() {
        LOGGER.info("Test 1: processLocations() with a mix of valid inputs (city/state and zip/post codes) and different quotation marks");
        String locationsArg = "“New York, NY” '60642' \"Chicago, IL\" “90210-1234”";
        System.setProperty("locations", locationsArg);
        try {
            geoCodingTest.processLocations();
        }catch (Exception e){
            LOGGER.info("Test 1 failed with an exception - " + e.getMessage());
        }
    }

    @Test
    public void testGetLocationResponse() {
        LOGGER.info("Test 2: test getLocations() method");

        Object locationResult = geoCodingTest.getLocationResponse("'29 Palms, CA'");
        assertTrue(locationResult instanceof LocationNameResponse);
        LocationNameResponse locationNameResponse = (LocationNameResponse) locationResult;
        assertNotNull(locationNameResponse.getName());
        assertNotNull(locationNameResponse.getLat());
        assertNotNull(locationNameResponse.getLon());
        assertEquals("US", locationNameResponse.getCountry());
        assertEquals("California", locationNameResponse.getState());

        Object zipResult = geoCodingTest.getLocationResponse("00801");
        assertTrue(zipResult instanceof ZipPostCodeResponse);
        ZipPostCodeResponse zipResponse = (ZipPostCodeResponse) zipResult;
        assertNotNull(zipResponse.getZip());
        assertNotNull(zipResponse.getLat());
        assertNotNull(zipResponse.getLon());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcessLocationsWithNullArguments() {
        LOGGER.info("Test 3: catching IlligalArgumentException if no argument for location for passed through the command line");

        System.clearProperty("locations");
        geoCodingTest.processLocations();
    }

    @Test
    public void testGetZipResponseReturnsNullForInvalidZip() {
        LOGGER.info("Test 4: getZipResponseReturns() for /zip endpoint returns null for invalid zip code");
        ZipPostCodeResponse response = geoCodingTest.getZipResponse("0");
        assertNull(response);
    }

    @Test
    public void testGetLocationNameResponseReturnsNullForInvalidCityAndState() {
        LOGGER.info("Test 5: getLocationNameResponse() for /direct endpoint returns null for invalid city/state code");
        LocationNameResponse response = geoCodingTest.getLocationNameResponse("abc");
        assertNull(response);
    }

    @Test
    public void testCustomSplitMethod() {
        LOGGER.info("Test of customSplit() method from Helper class");
        String input = "“Washington, DC” '67545' \"Long Beach-Lakewood, CA\" “96520” '02108'";
        String[] result = helper.customSplit(input);
        LOGGER.info("Results of splitting: " + Arrays.toString(result));

        assertEquals(5, result.length);
        assertEquals("“Washington, DC”", result[0]);
        assertEquals("'67545'", result[1]);
        assertEquals("\"Long Beach-Lakewood, CA\"", result[2]);
        assertEquals("“96520”", result[3]);
        assertEquals("'02108'", result[4]);
    }

    @Test
    public void testIsZipOrPostCode() {
        LOGGER.info("Test of isZipOrPostCode() method from Helper class");

        assertTrue(helper.isZipOrPostCode("12345"));
        assertTrue(helper.isZipOrPostCode("00801-00851"));
        assertFalse(helper.isZipOrPostCode("29 Palms, CA"));
        assertFalse(helper.isZipOrPostCode("Chicago, IL"));
    }

}

package com.radical3d.turismapp.TurismApp.utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class AppUtilsTest {

    @Test
    void testRetrievePropertiesFromFile() {
        String propertyName = "regions";

        Map<String, String> properties = AppUtils.retrievePropertiesFromFile(propertyName);
        LoggerHelper.info(properties.toString());
        assertAll(
            () -> assertNotNull(properties),
            () -> assertTrue(properties.size()>0)
        );
    }
}

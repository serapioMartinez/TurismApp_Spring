package com.radical3d.turismapp.TurismApp.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class AppUtils {

    private static final String APP_DATA_FILENAME = "app_data.json";

    public static String getRequestCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null)
            return null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }

        return null;
    }

    public static Authentication getContextAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Map<String, String> retrievePropertiesFromFile(String prop_name){
        JsonParser parser = new JacksonJsonParser();

        Resource resource = new ClassPathResource(APP_DATA_FILENAME);
        
        try {
            Map<String, Object> json_data = parser
                                                .parseMap(Files.readString(resource.getFile().toPath()));
            Map<String, String> properties = (Map<String, String>)json_data.get(prop_name);
            return properties;
        } catch (IOException e) {
            e.printStackTrace(LoggerHelper.printWriter);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There is an error with the APP DATA. Please contact the administrator to solve the problem");
        }
    }
}

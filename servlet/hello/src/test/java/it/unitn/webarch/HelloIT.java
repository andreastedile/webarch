package it.unitn.webarch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelloIT {
    static String url;

    @BeforeAll
    static void setUp() {
        String port = System.getProperty("http.port");
        String war = System.getProperty("war.name");
        url = "http://localhost:" + port + "/" + war;
    }

    @Test
    void testAuthorizationFilter() throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url + "/restricted/user"))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(302, response.statusCode());

        Optional<String> location = response.headers().firstValue("location");
        assertTrue(location.isPresent());
        assertEquals(url, location.get());
    }

    @Test
    void testLoginAndLogout() throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();

        // Test login

        String requestBody = "username=Andrea";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url + "/login"))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(302, response.statusCode());

        Optional<String> location = response.headers().firstValue("location");
        assertTrue(location.isPresent());
        assertEquals(url + "/restricted/user", location.get());

        // Test logout

        request = HttpRequest.newBuilder()
                .uri(new URI(url + "/restricted/logout"))
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(302, response.statusCode());

        location = response.headers().firstValue("location");
        assertTrue(location.isPresent());
        assertEquals(url, location.get());

        // Try to access the user page again

        request = HttpRequest.newBuilder()
                .uri(new URI(url + "/restricted/user"))
                .build();

        response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(302, response.statusCode());

        location = response.headers().firstValue("location");
        assertTrue(location.isPresent());
        assertEquals(url, location.get());
    }
}

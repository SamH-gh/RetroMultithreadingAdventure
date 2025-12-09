package main;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JokeAPI {
    
    public static void main(String[] args) {
        // Create an HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Define the URL of the API endpoint
        String apiUrl = "https://v2.jokeapi.dev/joke/Programming,Miscellaneous,Pun?blacklistFlags=nsfw,religious,political,racist,sexist,explicit&type=single";

        // Create an HttpRequest to the API endpoint
        HttpRequest request = HttpRequest.newBuilder()
                .uri(java.net.URI.create(apiUrl))
                .GET()
                .build();

        try {
            // send the request and get the response (typed)
            HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString());

            // check if response status code is successful (200s)
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                String responseBody = response.body();

                // Try to determine response "type" (single or twopart)
                Pattern typePattern = Pattern.compile("\"type\"\s*:\s*\"(single|twopart)\"", Pattern.CASE_INSENSITIVE);
                Matcher typeMatcher = typePattern.matcher(responseBody);
                String type = null;
                if (typeMatcher.find()) {
                    type = typeMatcher.group(1);
                }

                if ("twopart".equalsIgnoreCase(type)) {
                    // extract setup and delivery
                    Pattern p = Pattern.compile("\"setup\"\s*:\s*\"((?:\\\\.|[^\"])*?)\".*?\"delivery\"\s*:\s*\"((?:\\\\.|[^\"])*?)\"", Pattern.DOTALL);
                    Matcher m = p.matcher(responseBody);
                    if (m.find()) {
                        String setupRaw = m.group(1);
                        String deliveryRaw = m.group(2);
                        System.out.println(unescapeJsonString(setupRaw));
                        System.out.println(unescapeJsonString(deliveryRaw));
                    } else {
                        // fallback
                        System.out.println(responseBody);
                    }
                } else {
                    // assume single -> extract "joke"
                    Pattern p = Pattern.compile("\"joke\"\s*:\s*\"((?:\\\\.|[^\"])*?)\"", Pattern.DOTALL);
                    Matcher m = p.matcher(responseBody);
                    if (m.find()) {
                        String jokeRaw = m.group(1);
                        System.out.println(unescapeJsonString(jokeRaw));
                    } else {
                        // fallback: print full response for debugging
                        System.out.println(responseBody);
                    }
                }
            } else {
                System.out.println("Error: Received status code " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
        }
    }

    // Minimal JSON string unescape for common sequences (\n, \r, \t, \\", \\\\)
    private static String unescapeJsonString(String raw) {
        if (raw == null) return null;
        String s = raw.replaceAll("\\\\n", "\n")
                      .replaceAll("\\\\r", "\r")
                      .replaceAll("\\\\t", "\t")
                      .replaceAll("\\\\\"", "\"")
                      .replaceAll("\\\\\\\\", "\\");
        return s;
    }
}

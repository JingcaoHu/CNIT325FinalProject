package main;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class AIConnection {
    
    public String runAIConnection(String AIInput){
    try {
            // Set up the endpoint URL
            URL url = new URL("http://localhost:1234/v1/chat/completions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set request method and headers
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // JSON payload
            String jsonInputString = """
            {
              "model": "deepseek-r1-distill-qwen-7b",
              "messages": [
                { "role": "system", "content": "Always answer in rhymes. Today is Thursday" },
                { "role": "user", "content": "What day is it today?" }
              ],
              "temperature": 0.7,
              "max_tokens": -1,
              "stream": false
            }
            """;

            // Send the JSON input
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            int status = conn.getResponseCode();
            System.out.println("HTTP Status Code: " + status);

            try (var reader = new java.util.Scanner(conn.getInputStream(), StandardCharsets.UTF_8)) {
                String response = reader.useDelimiter("\\A").next();
                System.out.println("Response: " + response);
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

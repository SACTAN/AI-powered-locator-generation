package org.apicodes;

import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import org.apache.http.client.config.RequestConfig;

public class OllamaClient {
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private static final int TIMEOUT = 150000;
    private static final Gson gson = new Gson();

    public String generateText(String model, String prompt) throws Exception {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(TIMEOUT) // Time to establish connection
                .setSocketTimeout(TIMEOUT)  // Time to wait for data
                .build();

        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build()) {
            HttpPost httpPost = new HttpPost(OLLAMA_URL);

            // Define the request payload
            JsonObject payload = new JsonObject();
            payload.addProperty("model", model);
            payload.addProperty("prompt", prompt);
            payload.addProperty("stream", false);

            String jsonPayload = gson.toJson(payload);

            httpPost.setEntity(new StringEntity(jsonPayload));
            httpPost.setHeader("Content-Type", "application/json");
                   Thread.sleep(2000);
            System.out.println("waiting before hitting ollama client : 2 seconds");
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("Actual Response from Model "+ responseBody);
                OllamaResponse ollamaResponse = gson.fromJson(responseBody, OllamaResponse.class);
                return ollamaResponse.getResponse();
            }
        }
    }

    // Helper class to parse Ollama's JSON response
    private static class OllamaResponse {
        private String response;
        public String getResponse() { return response; }
    }
}

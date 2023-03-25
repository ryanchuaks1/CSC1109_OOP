package com.rjdxbanking.rjdxbank.Services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rjdxbanking.rjdxbank.Helpers.SecretKeyStore;

public class FXService {
    // Foreign exchange API that return Json object but filter based of string
    // currencyCode
    public static double foreignXchange(String currencyCode) throws IOException {
        try {
            // Setting URL
            String url_str = "https://v6.exchangerate-api.com/v6/" + SecretKeyStore.getKey("FXAPI_Key") + "/latest/SGD";

            // Making Request
            URL url = new URL(url_str);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            // Convert to JSON
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonobj = root.getAsJsonObject();

            // Accessing object
            var req_result = jsonobj.get("conversion_rates").getAsJsonObject();
            double conversionRate = req_result.get(currencyCode).getAsDouble();
            return conversionRate;
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
        // return 0

        return 0;
    }
}

package com.rjdxbanking.rjdxbank.Helpers;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class SecretKeyStore {
    private static final HashMap<String, String> apiKeys = new HashMap<>();
    static {
        apiKeys.put("FXAPI_Key", "f08fdd94f7ed788acb29d9f2");
        apiKeys.put("ACCOUNT_SID", "AC4bbf9e389cf4ba5acad1406737de3d43");
        apiKeys.put("AUTH_TOKEN", "b94685cbd4b8c2cf6d2802017496c434");
    }

    public static String getKey(String label) {
        if (apiKeys.containsKey(label)) {
            return apiKeys.get(label);
        } else {
            String excMsg = "No key for label \"" + label + "\"";
            throw new NoSuchElementException(excMsg);
        }
    }
}

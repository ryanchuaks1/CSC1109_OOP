package Helpers;

import java.util.HashMap;
import java.util.NoSuchElementException;
public class SecretKeyStore {
    private static final HashMap<String, String> apiKeys = new HashMap<>();
    static {
        apiKeys.put("mysqlUser", "EXAMPLE0000-0001");
        apiKeys.put("mysqlPass", "EXAMPLE0000-0002");
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

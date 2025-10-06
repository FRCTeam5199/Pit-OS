package org.robotdolphins.pitsystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Config {
    public static final String
            BASE_URL = "https://www.thebluealliance.com/api/v3/event/",
            EVENT_CODE = "2025caav",
            TEAMNUMBER = "5199";

    public static String getToken() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("./assets/AuthKey"));
            return br.readLine();
        } catch (IOException e) {
            System.err.println("Error reading Auth Key, check permissions and the file \"./assets/AuthKey\" : " + e.getMessage());
        }
        System.exit(1);
        return "This should never EVER go anywhere, the java compiler just requires i provide a default response.";
    }
}

package org.robotdolphins.pitsystem;

import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class ConfigService {
    private Config configuration = new Config();

    public Config getConfiguration() {
        return configuration;
    }
    public void setConfiguration(Config newConfig) {
        configuration = newConfig;
    }

    public void resetConfiguration() {
        configuration = new Config();
    }

    public static String getAPIToken() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("./assets/AuthKey"));
            return br.readLine();
        } catch (IOException e) {
            System.err.println("Error reading Auth Key, check permissions and the file \"./assets/AuthKey\" : " + e.getMessage());
        }
        System.exit(403);
        return "This should never EVER go anywhere, the java compiler just requires i provide a default response.";
    }
}

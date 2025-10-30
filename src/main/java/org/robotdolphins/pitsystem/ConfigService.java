package org.robotdolphins.pitsystem;

import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class ConfigService {
    private static final Config defaultConfiguration = new Config(
            "https://www.thebluealliance.com/api/v3/",
            "2025caav",
            getToken(),
            5199,
            new Config.PageFormat(
                    new Config.PageFormat.Colors(
                            //Page colors
                            new Color(0x003366),
                            //Table colors
                            new Color(0xFFFFFF),
                            new Color(0xCCCCCC),
                            new Color(0xFF0000),
                            new Color(0xCF0000),
                            new Color(0x0032FF),
                            new Color(0x0000DF),
                            //Text colors
                            new Color(0xFFFFFF),
                            new Color(0xFFFFFF),
                            new Color(0xDDDDDD),
                            new Color(0xFF0000),
                            new Color(0xAA2222),
                            new Color(0x0000FF),
                            new Color(0x2222AA)
                    ),
                    "5199-fonts/Eurostile-Extended-2-Regular.otf",
                    "5199-fonts/Eurostile-Extended-2-Bold.otf",
                    14
            ));
    public static Config configuration = defaultConfiguration;

    public static void resetConfiguration() {
        configuration = defaultConfiguration;
    }

    private static String getToken() {
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

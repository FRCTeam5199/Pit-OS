package org.robotdolphins.pitsystem.Configuration;


import java.awt.Color;

public record Config(
        String baseUrl,
        String matchLocation,
        String eventCode,
        String token,
        int teamNumber,
        PageFormat formatting
) {
    private static final Config defaultConfiguration = new Config(
            "https://www.thebluealliance.com/api/v3/",
            "event/%s/matches",
            "2025caav",
            ConfigService.getAPIToken(),
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
    public Config() {
        this(defaultConfiguration);
    }
    public Config(Config Configuration) {
        this(
                Configuration.baseUrl(),
                Configuration.matchLocation(),
                Configuration.eventCode(),
                Configuration.token(),
                Configuration.teamNumber(),
                Configuration.formatting()
        );
    }
    public record PageFormat(
            Colors pageColors,
            String normalFontLocation,
            String boldFontLocation,
            int fontSize
    ) {
        public record Colors(
                Color bg,
                Color tableWin,
                Color tableLose,
                Color tableRedWin,
                Color tableRedLose,
                Color tableBlueWin,
                Color tableBlueLose,
                Color textColor,
                Color textWin,
                Color textLose,
                Color textRedWin,
                Color textRedLose,
                Color textBlueWin,
                Color textBlueLose
        ) {
        }
    }
}

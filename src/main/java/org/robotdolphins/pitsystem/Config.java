package org.robotdolphins.pitsystem;


import java.awt.Color;

public record Config(
        String baseUrl,
        String eventCode,
        String Token,
        int teamNumber,
        PageFormat formatting
) {
    public record PageFormat(
            Colors pageColors,
            String normalFontLocation,
            String boldFontLocation,
            int fontSize
    ){
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
        ){}
    }
}

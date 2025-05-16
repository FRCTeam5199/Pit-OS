package org.robotdolphins.pitsystem;

public class StyledText {
    private String text, fontWeight, color;

    public StyledText(String text, String fontWeight, String color) {
        this.text = text;
        this.fontWeight = fontWeight;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(String fontWeight) {
        this.fontWeight = fontWeight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
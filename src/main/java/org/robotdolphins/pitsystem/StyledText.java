package org.robotdolphins.pitsystem;

public class StyledText {
    private String text, fontWeight, color, fontSize;
    public StyledText(String text, String fontWeight, String color, String fontSize) {
        this.text = text;
        this.fontWeight = fontWeight;
        this.color = color;
        this.fontSize = fontSize;
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

    public void setFontSize(String fontSize){
        this.fontSize = fontSize;
    }

    public String getFontSize(){
        return fontSize;
    }
}
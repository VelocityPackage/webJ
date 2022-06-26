package com.velocitypackage.webj.materials.components;

public class Color
{
    private final int r, g, b;
    
    /**
     *
     * @param r red 0 - 100
     * @param g green 0 - 100
     * @param b blue 0 - 100
     */
    public Color(int r, int g, int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    /**
     * Creates an interpretation as hex of this color
     * @return the color as hex
     */
    public String getHex() {
        return String.format("#%02X%02X%02X", r, g, b);
    }
}

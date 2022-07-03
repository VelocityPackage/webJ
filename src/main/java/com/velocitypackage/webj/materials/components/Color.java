package com.velocitypackage.webj.materials.components;

/**
 * @author maxmielchen
 */
public class Color
{
    private final int r, g, b;
    
    /**
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
     * @param hex color as hex
     */
    public Color(String hex)
    {
        this.r = Integer.valueOf(hex.substring(0, 2), 16);
        this.g = Integer.valueOf(hex.substring(2, 4), 16);
        this.b = Integer.valueOf(hex.substring(4, 6), 16);
    }
    
    /**
     * Creates an interpretation as hex of this color
     * @return the color as hex
     */
    public String getHex() {
        return String.format("#%02X%02X%02X", r, g, b);
    }
}

package dev.fire.dfextras.devutils;

import java.awt.*;

public class ColorUtils {
    public static int GRAY = 0xaaaaaa;
    public static int DARK_GRAY = 0x555555;
    public static int SUPPORT_COLOR = 0x557FD4;
    public static int setAlpha(int color, float alpha) {return (color+ ((int)(alpha*255)<<24));}

    public static int grayScale(int p) {

        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

        // calculate average
        int avg = (r + g + b) / 3;

        // replace RGB value with avg
        return (a << 24) | (avg << 16) | (avg << 8) | avg;
    }

    public static Color getColor(int color) {
        int b = (color)&0xFF;
        int g = (color>>8)&0xFF;
        int r = (color>>16)&0xFF;
        int a = (color>>24)&0xFF;
        return new Color(r, b, g, a);
    }
}

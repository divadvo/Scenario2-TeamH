package team.h.visualization;

import java.awt.*;

public class ColorRange {

    private double min, max;

    public ColorRange(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public Color generateColor(double value) {
        double red = ((value-min)/(max-min))*255; // 255
        int redInt = (int) red;
//        System.out.println(red + " RED " + redInt);
        Color color = new Color(redInt, 200, 50);
        return color;
    }
}

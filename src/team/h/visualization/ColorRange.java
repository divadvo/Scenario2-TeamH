package team.h.visualization;

import team.h.core.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        if(redInt < 0 || redInt > 255)
            return Color.PINK;

        Color color = new Color(redInt, 200, 50);

        if(value == max)
            return Color.MAGENTA;

        return color;
    }



    public static ColorRange generateColorRange(java.util.List<team.h.core.Shape> shapeList) {
        if (shapeList.isEmpty())
            return null;

        List<team.h.core.Shape> shapesSortedByCost = new ArrayList<>(shapeList);
        Collections.sort(shapesSortedByCost, Comparator.comparing(o -> o.getTotalCost()));

        team.h.core.Shape smallestCostShape = shapesSortedByCost.get(0);
        Shape highestCostShape = shapesSortedByCost.get(shapesSortedByCost.size() - 1);

        return new ColorRange(smallestCostShape.getTotalCost(), highestCostShape.getTotalCost());
    }
}

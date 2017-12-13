package team.h.visualization;

import team.h.core.Shape;

public class ShapeAndShape {
    private java.awt.Shape drawShape;
    private Shape ourShape;

    public ShapeAndShape(java.awt.Shape drawShape, Shape ourShape) {
        this.drawShape = drawShape;
        this.ourShape = ourShape;
    }

    public java.awt.Shape getDrawShape() {
        return drawShape;
    }

    public void setDrawShape(java.awt.Shape drawShape) {
        this.drawShape = drawShape;
    }

    public Shape getOurShape() {
        return ourShape;
    }

    public void setOurShape(Shape ourShape) {
        this.ourShape = ourShape;
    }
}

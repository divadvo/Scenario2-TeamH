package team.h.core;

public class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point translate(double dx, double dy) {
        return new Point(x + dx, y + dy);
//        this.x += dx;
//        this.y += dy;
    }
}

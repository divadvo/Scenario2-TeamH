package team.h.core;

import team.h.core.Point;

import java.util.List;

public class Room {

    private List<Point> points;

    public Room(List<Point> points) {
        this.points = points;
    }

    public List<Point> getPoints() {
        return points;
    }
}

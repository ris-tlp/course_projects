package com.company.Models;

public class Square extends Rectangle {
    private double side;

    public Square() {
        super("Square", 0, 0, 0, 0, new Color());
    }

    public Square(String name, double xPosition, double yPosition, double side, Color color) {
        super(name, xPosition, yPosition, side, side, color);
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    public double getArea(Object shape) {
        return Math.pow(this.getSide(), 2);
    }

    public double getPerimeter(Object shape) {
        return 4 * this.getSide();
    }

    @Override
    public String toString() {
        return super.toString() +
                "side=" + side +
                '}';
    }
}

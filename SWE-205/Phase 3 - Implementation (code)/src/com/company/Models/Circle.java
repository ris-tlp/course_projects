package com.company.Models;

public class Circle extends Ellipse {
    private double radius;

    public Circle() {
        super("Circle", 0, 0, 0, 0, new Color(), 0, 0, 0, 0);
        this.radius = 0;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                '}';
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}

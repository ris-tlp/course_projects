package com.company.Models;

public class Ellipse extends Shape {

    double centerX, centerY;
    double radiusX, radiusY;

    public Ellipse() {
        super("Ellipse", 0, 0, 0, 0, new Color());
        this.centerX = 0;
        this.centerY = 0;
        this.radiusX = 0;
        this.radiusY = 0;
    }

    public Ellipse(String name, double xPosition, double yPosition, double length, double width, Color color, double centerX, double centerY, double radiusX, double radiusY) {
        super(name, xPosition, yPosition, length, width, color);
        this.centerX = centerX;
        this.centerY = centerY;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(double radiusX) {
        this.radiusX = radiusX;
    }

    public double getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(double radiusY) {
        this.radiusY = radiusY;
    }

    @Override
    public boolean setPosition(double xPosition, double yPosition) {
        this.setXPosition(xPosition);
        this.setYPosition(yPosition);
        return true;
    }

    @Override
    public double getArea() {
        return 0;
    }

    @Override
    public double getPerimeter() {
        return 0;
    }


}

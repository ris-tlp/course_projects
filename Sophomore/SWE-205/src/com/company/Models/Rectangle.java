package com.company.Models;

public class Rectangle extends Shape {

    public Rectangle() {
        super("Rectangle", 0, 0, 0, 0, new Color());
    }

    public Rectangle(String name, double xPosition, double yPosition, double length, double width, Color color) {
        super(name, xPosition, yPosition, length, width, color);
    }

    @Override
    public boolean setPosition(double xPosition, double yPosition) {
        this.setXPosition(xPosition);
        this.setYPosition(yPosition);

        return true; // placeholder - check for exception
    }

    public double getArea() {
        return (this.getLength() * this.getWidth());
    }

    public double getPerimeter() {
        return ((this.getLength() + this.getWidth()) * 2);
    }
}

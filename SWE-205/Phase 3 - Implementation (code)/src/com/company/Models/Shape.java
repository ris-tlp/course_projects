package com.company.Models;

import java.io.Serializable;

// Parent to all shape models and implements serializable to allow the user to save the shapes to a file
public abstract class Shape implements Serializable {
    private String name;
    private double xPosition;
    private double yPosition;
    private double length;
    private double width;
    private Color color;

    public Shape() {
        this.name = "";
        this.xPosition = 0;
        this.yPosition = 0;
        this.length = 0;
        this.width = 0;
        this.color = null;
    }

    public Shape(String name, double xPosition, double yPosition, double length, double width, Color color) {
        this.name = name;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.length = length;
        this.width = width;
        this.color = color;
    }

    public abstract boolean setPosition(double xPosition, double yPosition);

    public abstract double getArea();

    public abstract double getPerimeter();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getXPosition() {
        return xPosition;
    }

    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public double getYPosition() {
        return yPosition;
    }

    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format(
                "%s,\n xPosition: %s,\n yPosition: %s,\n Length: %s,\n Width: %s\n",
                this.getName(),
                this.getXPosition(),
                this.getYPosition(),
                this.getLength(),
                this.getWidth()
        );
    }
}

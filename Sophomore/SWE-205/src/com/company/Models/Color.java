package com.company.Models;

import java.io.Serializable;

public class Color implements Serializable {
    private String hexCode;

    public Color() {
        this.hexCode = "#000000";
    }

    public Color(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    @Override
    public String toString() {
        return "Color{" +
                "hexCode='" + hexCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Color)) return false;
        Color color = (Color) other;
        return getHexCode().equals(color.getHexCode());
    }
}

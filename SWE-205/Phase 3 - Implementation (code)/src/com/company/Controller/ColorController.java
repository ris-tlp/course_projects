package com.company.Controller;

import com.company.Models.Color;
import com.company.Models.Shape;
import javafx.event.ActionEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;

public class ColorController {

    // To allow access throughout multiple controllers
    public static Color color = new Color();

    /**
     * Handler that assigns color using the color selected in the ColorPicker
     * @param click
     * @param colorPicker
     */

    public static void colorHandler(ActionEvent click, ColorPicker colorPicker) {
        if (click.getSource() == colorPicker) {
            color.setHexCode(colorPicker.getValue().toString());
        }
    }

    /**
     * Setter that sets Fill and Stroke colors to a GraphicsContext object
     * @param gc
     */
    public static void setColor(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.web(ColorController.color.getHexCode()));
        gc.setStroke(javafx.scene.paint.Color.web(ColorController.color.getHexCode()));
    }

    /**
     * @return the hex code of the color
     */
    public static String getColorHex() {
        return color.getHexCode();
    }

    /**
     * Uses the color stored in a shape to set the Stroke and Fill properties of a GraphicsContext object
     * @param gc
     * @param shape
     */
    public static void setColorFromShape(GraphicsContext gc, Shape shape) {
        gc.setFill(javafx.scene.paint.Color.web(shape.getColor().getHexCode()));
        gc.setStroke(javafx.scene.paint.Color.web(shape.getColor().getHexCode()));
    }

}

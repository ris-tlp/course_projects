package com.company.Controller;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class SelectShapeController {

    public static Shape shape;
    static Pane overlay = ShapeController.overlay;

    /**
     * This handler handles any operation related to selecting a shape. Once a shape is selected, it directs
     * control towards the PropertiesController to further handle operations in regards to editing a shape through the
     * properties bar
     *
     * @param canvas
     * @param deletePropertyButton
     * @param widthProperty
     * @param heightProperty
     * @param radius1Property
     * @param radius2Property
     * @param colorProperty
     * @param propertiesToolbar
     * @see PropertiesController
     */
    public static void selectShapeHandler(Canvas canvas, Button deletePropertyButton, TextField widthProperty, TextField heightProperty,
                                          TextField radius1Property, TextField radius2Property, ColorPicker colorProperty,
                                          ToolBar propertiesToolbar) {

        // Sets the overlay with properties similar to that of the canvas
        overlay.setOpacity(0);
        overlay.setLayoutX(canvas.getLayoutX());
        overlay.setLayoutY(canvas.getLayoutY());
        overlay.setMinSize(canvas.getWidth(), canvas.getHeight());


        // Check which shape has been selected and transfer control to PropertiesController
        for (int i = 0; i < overlay.getChildren().size(); i++) {
            // Only final variables allowed inside a lambda
            int finalI = i;
            overlay.getChildren().get(i).setOnMousePressed(e -> {
                shape = (Shape) overlay.getChildren().get(finalI);
                PropertiesController.propertiesHandler(deletePropertyButton, widthProperty, heightProperty, radius1Property, radius2Property, colorProperty, propertiesToolbar, canvas);
            });
        }

    }
}



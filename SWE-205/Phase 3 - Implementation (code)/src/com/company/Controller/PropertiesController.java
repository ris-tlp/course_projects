package com.company.Controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class PropertiesController {

    // Creating static fields to allow access from the redraw method
    final static double[] width = {0};
    final static double[] height = {0};
    final static double[] radius1 = {0};
    final static double[] radius2 = {0};
    final static Color[] color = new Color[1];
    final static Shape[] shapeToDelete = new Shape[1];
    static Pane pane = ShapeController.overlay;

    public static void propertiesHandler(Button deletePropertyButton, TextField widthProperty, TextField heightProperty,
                                         TextField radius1Property, TextField radius2Property, ColorPicker colorProperty,
                                         ToolBar propertiesToolbar, Canvas canvas) {

        Shape shape = SelectShapeController.shape;

        // GETS NAME OF CLASS WITHOUT javafx.scene.Shape.*
        String className = shape.getClass().getName().substring(shape.getClass().getName().lastIndexOf(".") + 1);

        // Enable toolbar fields according to the fields of the Shape
        if (className.equals("Rectangle")) {
            Rectangle squareOrRectangle = (Rectangle) shape;
            heightProperty.setDisable(false);
            widthProperty.setDisable(false);
            colorProperty.setDisable(false);
            deletePropertyButton.setDisable(false);
            heightProperty.setText(String.valueOf(squareOrRectangle.getHeight()));
            widthProperty.setText(String.valueOf(squareOrRectangle.getWidth()));
            colorProperty.setValue((Color) squareOrRectangle.getFill());
            height[0] = squareOrRectangle.getHeight();
            width[0] = squareOrRectangle.getWidth();
            color[0] = (Color) squareOrRectangle.getFill();


        } else if (className.equals("Ellipse")) {
            Ellipse ellipseOrCircle = (Ellipse) shape;
            radius1Property.setDisable(false);
            radius2Property.setDisable(false);
            colorProperty.setDisable(false);
            deletePropertyButton.setDisable(false);
            radius1Property.setText(String.valueOf(ellipseOrCircle.getRadiusX()));
            radius2Property.setText(String.valueOf(ellipseOrCircle.getRadiusY()));
            colorProperty.setValue((Color) ellipseOrCircle.getFill());


        } else if (className.equals("Line")) {
            Line line = (Line) shape;
            widthProperty.setDisable(false);
            colorProperty.setDisable(false);
            deletePropertyButton.setDisable(false);
            widthProperty.setText(String.valueOf(line.getStrokeWidth()));
            colorProperty.setValue((Color) line.getStroke());

        }


        // Handlers for different inputs of the properties bar. After every new input, control is trasnferred to the
        // redraw method and all shapes are immediately redrawn, effected shapes are transformed in accordance with
        // their new properties and redrawn
        widthProperty.setOnAction(e -> {
            width[0] = Double.parseDouble(widthProperty.getText());
            PropertiesController.redraw(PropertiesController.shapeToDelete[0], PropertiesController.height[0], PropertiesController.width[0],
                    PropertiesController.radius1[0], PropertiesController.radius2[0], PropertiesController.color[0], canvas);
        });

        heightProperty.setOnAction(e -> {
            height[0] = Double.parseDouble(heightProperty.getText());
            PropertiesController.redraw(PropertiesController.shapeToDelete[0], PropertiesController.height[0], PropertiesController.width[0],
                    PropertiesController.radius1[0], PropertiesController.radius2[0], PropertiesController.color[0], canvas);
        });

        colorProperty.setOnAction(e -> {
            color[0] = colorProperty.getValue();
            PropertiesController.redraw(PropertiesController.shapeToDelete[0], PropertiesController.height[0], PropertiesController.width[0],
                    PropertiesController.radius1[0], PropertiesController.radius2[0], PropertiesController.color[0], canvas);
        });

        radius1Property.setOnAction(e -> {
            radius1[0] = Double.parseDouble(radius1Property.getText());
            PropertiesController.redraw(PropertiesController.shapeToDelete[0], PropertiesController.height[0], PropertiesController.width[0],
                    PropertiesController.radius1[0], PropertiesController.radius2[0], PropertiesController.color[0], canvas);
        });

        radius2Property.setOnAction(e -> {
            radius2[0] = Double.parseDouble(radius2Property.getText());
            PropertiesController.redraw(PropertiesController.shapeToDelete[0], PropertiesController.height[0], PropertiesController.width[0],
                    PropertiesController.radius1[0], PropertiesController.radius2[0], PropertiesController.color[0], canvas);
        });

        deletePropertyButton.setOnAction(e -> {
            shapeToDelete[0] = shape;
            PropertiesController.redraw(PropertiesController.shapeToDelete[0], PropertiesController.height[0], PropertiesController.width[0],
                    PropertiesController.radius1[0], PropertiesController.radius2[0], PropertiesController.color[0], canvas);
        });

    }

    /**
     * Method that redraws the entire canvas once a property of a shape is changed in order to instantaneously update
     * any changes made to the shape
     * @param shapeToDelete
     * @param height
     * @param width
     * @param radius1
     * @param radius2
     * @param color
     * @param canvas
     */
    public static void redraw(Shape shapeToDelete, double height, double width, double radius1, double radius2, Color color, Canvas canvas) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int i = 0; i < pane.getChildren().size(); i++) {
            // Checks whether the shape is one that must be deleted, if true then it doesn't draw that shape
            if (!pane.getChildren().get(i).equals(shapeToDelete)) {

                // Gets the name of the class without the prefix of javafx.scene.Shape.
                String className = pane.getChildren().get(i).getClass().getName().substring(
                        pane.getChildren().get(i).getClass().getName().lastIndexOf(".") + 1
                );

                if (className.equals("Rectangle")) {
                    System.out.println("in here");
                    Rectangle rectangle = (Rectangle) pane.getChildren().get(i);
                    gc.setFill(color);
                    gc.setStroke(color);
                    gc.fillRect(rectangle.getX(), rectangle.getY(), width, height);
                    gc.strokeRect(rectangle.getX(), rectangle.getY(), width, height);
                }
//
//                // RESHAPING FOR ALL FIGURES WILL BE RELEASED IN Version 1.1
//                else if (className.equals("Ellipse")) {
//                    Ellipse ellipse = (Ellipse) pane.getChildren().get(i);
//                }

            }
        }

    }

}

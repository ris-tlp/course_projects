package com.company.Controller;

import com.company.Models.*;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ShapeController {

    // Static variables to allow access throughout multiple controllers
    public static Pane overlay = new Pane();

    // Creating arraylists specific to each shape because some shapes contain attributes that are not available in the
    // parent Shape class. This makes it difficult to downcast from Shape as attributes such as these are lost.
    public static ArrayList<Rectangle> rectangles = new ArrayList<>();
    public static ArrayList<Square> squares = new ArrayList<>();
    public static ArrayList<Ellipse> ellipses = new ArrayList<>();
    public static ArrayList<Circle> circles = new ArrayList<>();
    public static ArrayList<Line> lines = new ArrayList<>();

    /**
     * Handles all operations related to the drawing of a Shape
     *
     * @param root
     * @param click
     * @param canvas
     * @param rectangleButton
     * @param squareButton
     * @param ellipseButton
     * @param circleButton
     * @param triangleButton
     * @param lineButton
     */
    public static void shapeHandler(AnchorPane root, ActionEvent click, Canvas canvas, Button rectangleButton,
                                    Button squareButton, Button ellipseButton, Button circleButton, Button triangleButton,
                                    Button lineButton, Button selectButton) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Initially creating shapes as instantiation is not allowed in lambdas
        Rectangle rectangle = new Rectangle();
        Square square = new Square();
        Ellipse ellipse = new Ellipse();
        Circle circle = new Circle();
        Line line = new Line();
        final Canvas[] layer = {canvas};

        // Setting overlay properties similar to that of the canvas
        overlay.setOpacity(0);
        overlay.setLayoutX(canvas.getLayoutX());
        overlay.setLayoutY(canvas.getLayoutY());
        overlay.setMinSize(canvas.getWidth(), canvas.getHeight());

        // If the Select Button was clicked, no shapes are to be drawn and the overlay is added to
        // the root scene so as to carry out selecting/modification operations
        if (click.getSource() == selectButton) {
            root.getChildren().add(overlay);

        } else {

            // Removes the overlay so it doesn't block any inputs to the canvas
            root.getChildren().remove(overlay);

            // To allow real time rendering of a shape, a duplicate canvas is created which updates every time
            // the mouse is dragged. This duplicate canvas clears itself at the start of every drag event so
            // it appears that the shape is being rendered according position of the mouse is being changed.
            canvas.setOnMousePressed(e -> {
                Canvas newLayer = new Canvas(layer[0].getHeight(), layer[0].getWidth());
                GraphicsContext gc2 = newLayer.getGraphicsContext2D();
                double x = canvas.getLayoutX();
                double y = canvas.getLayoutY();

                newLayer.setLayoutX(x);
                newLayer.setLayoutY(y);
                root.getChildren().add(newLayer);
                layer[0] = newLayer;

                if (click.getSource() == lineButton) {
                    line.setStartX(e.getX());
                    line.setStartY(e.getY());
                }
                else if (click.getSource() == rectangleButton) {
                    gc.setLineWidth(MainController.sliderValue);
                    ColorController.setColor(gc);
                    rectangle.setColor(new Color(ColorController.getColorHex()));
                    rectangle.setXPosition(e.getX());
                    rectangle.setYPosition(e.getY());
                }
                else if (click.getSource() == squareButton) {
                    gc.setLineWidth(MainController.sliderValue);
                    ColorController.setColor(gc);
                    square.setColor(new Color(ColorController.getColorHex()));
                    square.setXPosition(e.getX());
                    square.setYPosition(e.getY());
                }
                else if (click.getSource() == ellipseButton) {
                    gc.setLineWidth(MainController.sliderValue);
                    ColorController.setColor(gc);
                    ellipse.setColor(new Color(ColorController.getColorHex()));
                    ellipse.setCenterX(e.getX());
                    ellipse.setCenterY(e.getY());
                }
                else if (click.getSource() == circleButton) {
                    gc.setLineWidth(MainController.sliderValue);
                    ColorController.setColor(gc);
                    circle.setColor(new Color(ColorController.getColorHex()));
                    circle.setCenterX(e.getX());
                    circle.setCenterY(e.getY());
                }
            });


            canvas.setOnMouseDragged(e -> {
                GraphicsContext gc2 = layer[0].getGraphicsContext2D();

                // Clearing the duplicate canvas every time the mouse is dragged so as to give the effect of
                // real time rendering of a shape
                gc2.clearRect(0, 0, layer[0].getWidth(), layer[0].getHeight());
                ColorController.setColor(gc2);

                if (click.getSource() == lineButton) {
                    gc2.setLineWidth(MainController.sliderValue);
                    gc2.strokeLine(line.getStartX(), line.getStartY(), e.getX(), e.getY());
                }
                else if (click.getSource() == rectangleButton) {
                    rectangle.setWidth(Math.abs(rectangle.getXPosition() - e.getX()));
                    rectangle.setLength(Math.abs(rectangle.getYPosition() - e.getY()));

                    gc2.fillRect((Math.min(rectangle.getXPosition(), e.getX())), Math.min(rectangle.getYPosition(), e.getY()), rectangle.getWidth(), rectangle.getLength());
                }
                else if (click.getSource() == squareButton) {
                    square.setSide(Math.abs(square.getYPosition() - e.getY()));

                    gc2.strokeRect((Math.min(square.getXPosition(), e.getX())), Math.min(square.getYPosition(), e.getY()), square.getSide(), square.getSide());
                    gc2.fillRect((Math.min(square.getXPosition(), e.getX())), Math.min(square.getYPosition(), e.getY()), square.getSide(), square.getSide());
                }
                else if (click.getSource() == ellipseButton) {
                    ellipse.setRadiusX(Math.abs(e.getX() - ellipse.getCenterX()));
                    ellipse.setRadiusY(Math.abs(e.getY() - ellipse.getCenterY()));

                    gc2.fillOval(Math.min(e.getX(), ellipse.getCenterX()), Math.min(e.getY(), ellipse.getCenterY()), ellipse.getRadiusX(), ellipse.getRadiusY());
                }
                else if (click.getSource() == circleButton) {
                    circle.setRadius(Math.abs(e.getX() - circle.getCenterX()));
                    gc2.fillOval(Math.min(e.getX(), circle.getCenterX()), Math.min(e.getY(), circle.getCenterY()), circle.getRadius(), circle.getRadius());
                }

            });

            // Once the shape has been finalized, that is, mouse click has been released, the duplicate canvas is
            // cleared and removed from the scene and the final rendition of the shape is drawn on the main canvas
            canvas.setOnMouseReleased(e -> {
                GraphicsContext gc2 = layer[0].getGraphicsContext2D();
                gc2.clearRect(0, 0, layer[0].getWidth(), layer[0].getHeight());
                root.getChildren().remove(layer[0]);

                if (click.getSource() == lineButton) {
                    gc.setLineWidth(MainController.sliderValue);
                    ColorController.setColor(gc);
                    gc.strokeLine(line.getStartX(), line.getStartY(), e.getX(), e.getY());
                    line.setEndX(e.getX());
                    line.setEndY(e.getY());

                    // Creating a node of the same shape to allow the user to select and edit said shape using the
                    // the properties bar
                    javafx.scene.shape.Line linePane = new javafx.scene.shape.Line(line.getStartX(), line.getStartY(), e.getX(), e.getY());
                    linePane.setStrokeWidth(MainController.sliderValue);
                    linePane.setStroke(javafx.scene.paint.Color.web(line.getColor().getHexCode()));

                    // Adding the node to an overlay that has same properties as the main canvas to allow the user
                    // to interact with the shape
                    overlay.getChildren().add(linePane);
                    // Adding the model to a specific array to record what shapes have been drawn to allow the user to
                    // save the shape their computer
                    lines.add(line);
                }
                else if (click.getSource() == rectangleButton) {
                    rectangle.setWidth(Math.abs(e.getX() - rectangle.getXPosition()));
                    rectangle.setLength(Math.abs(e.getY() - rectangle.getYPosition()));

                    if (rectangle.getXPosition() > e.getX()) {
                        rectangle.setXPosition(e.getX());
                    }

                    if (rectangle.getYPosition() > e.getY()) {
                        rectangle.setYPosition((e.getY()));
                    }

                    gc.fillRect(rectangle.getXPosition(), rectangle.getYPosition(), rectangle.getWidth(), rectangle.getLength());
                    gc.strokeRect(rectangle.getXPosition(), rectangle.getYPosition(), rectangle.getWidth(), rectangle.getLength());

                    // Creating a node of the same shape to allow the user to select and edit said shape using the
                    // the properties bar
                    javafx.scene.shape.Rectangle rectanglePane = new javafx.scene.shape.Rectangle(rectangle.getXPosition(), rectangle.getYPosition(), rectangle.getWidth(), rectangle.getLength());
                    rectanglePane.setStroke(javafx.scene.paint.Color.web(rectangle.getColor().getHexCode()));
                    rectanglePane.setFill(javafx.scene.paint.Color.web(rectangle.getColor().getHexCode()));

                    // Adding the node to an overlay that has same properties as the main canvas to allow the user
                    // to interact with the shape
                    overlay.getChildren().add(rectanglePane);
                    // Adding the model to a specific array to record what shapes have been drawn to allow the user to
                    // save the shape their computer
                    rectangles.add(rectangle);

                }
                else if (click.getSource() == squareButton) {
                    square.setSide(Math.abs(e.getY() - square.getYPosition()));

                    if (square.getXPosition() > e.getX()) {
                        square.setXPosition(e.getX());
                    }

                    if (square.getYPosition() > e.getY()) {
                        square.setYPosition(e.getY());
                    }

                    gc.fillRect(square.getXPosition(), square.getYPosition(), square.getSide(), square.getSide());
                    gc.strokeRect(square.getXPosition(), square.getYPosition(), square.getSide(), square.getSide());

                    // Creating a node of the same shape to allow the user to select and edit said shape using the
                    // the properties bar
                    javafx.scene.shape.Rectangle squarePane = new javafx.scene.shape.Rectangle(square.getXPosition(), square.getYPosition(), square.getSide(), square.getSide());
                    squarePane.setStroke(javafx.scene.paint.Color.web(square.getColor().getHexCode()));
                    squarePane.setFill(javafx.scene.paint.Color.web(square.getColor().getHexCode()));

                    // Adding the node to an overlay that has same properties as the main canvas to allow the user
                    // to interact with the shape
                    overlay.getChildren().add(squarePane);
                    // Adding the model to a specific array to record what shapes have been drawn to allow the user to
                    // save the shape their computer
                    squares.add(square);
                }
                else if (click.getSource() == ellipseButton) {
                    ellipse.setRadiusX(Math.abs(e.getX() - ellipse.getCenterX()));
                    ellipse.setRadiusY(Math.abs(e.getY() - ellipse.getCenterY()));
                    ellipse.setPosition(e.getX(), e.getY());

                    gc.fillOval(
                            Math.min(ellipse.getXPosition(), ellipse.getCenterX()), Math.min(ellipse.getYPosition(), ellipse.getCenterY()),
                            ellipse.getRadiusX(), ellipse.getRadiusY()
                    );

                    // Creating a node of the same shape to allow the user to select and edit said shape using the
                    // the properties bar
                    javafx.scene.shape.Ellipse ellipsePane = new javafx.scene.shape.Ellipse(Math.min(ellipse.getXPosition(), ellipse.getCenterX()),
                            Math.min(ellipse.getYPosition(), ellipse.getCenterY()), ellipse.getRadiusX(), ellipse.getRadiusY());
                    ellipsePane.setStroke(javafx.scene.paint.Color.web(ellipse.getColor().getHexCode()));
                    ellipsePane.setFill(javafx.scene.paint.Color.web(ellipse.getColor().getHexCode()));

                    // Adding the node to an overlay that has same properties as the main canvas to allow the user
                    // to interact with the shape
                    overlay.getChildren().add(ellipsePane);
                    // Adding the model to a specific array to record what shapes have been drawn to allow the user to
                    // save the shape their computer
                    ellipses.add(ellipse);
                }
                else if (click.getSource() == circleButton) {
                    circle.setRadius(Math.abs(e.getX() - circle.getCenterX()));
                    circle.setPosition(e.getX(), e.getY());

                    gc.fillOval(
                            Math.min(circle.getXPosition(), circle.getCenterX()), Math.min(circle.getYPosition(), circle.getCenterY()),
                            circle.getRadius(), circle.getRadius()
                    );


                    // Creating a node of the same shape to allow the user to select and edit said shape using the
                    // the properties bar
                    javafx.scene.shape.Ellipse circlePane = new javafx.scene.shape.Ellipse(Math.min(circle.getXPosition(), circle.getCenterX()),
                            Math.min(circle.getYPosition(), circle.getCenterY()), circle.getRadius(), circle.getRadius());
                    circlePane.setStroke(javafx.scene.paint.Color.web(circle.getColor().getHexCode()));
                    circlePane.setFill(javafx.scene.paint.Color.web(circle.getColor().getHexCode()));

                    // Adding the node to an overlay that has same properties as the main canvas to allow the user
                    // to interact with the shape
                    overlay.getChildren().add(circlePane);
                    // Adding the model to a specific array to record what shapes have been drawn to allow the user to
                    // save the shape their computer
                    circles.add(circle);
                }
            });
        }
    }
}

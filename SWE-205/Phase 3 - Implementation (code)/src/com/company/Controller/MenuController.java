package com.company.Controller;

import com.company.Models.*;
import com.company.View.MainDriver;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;

public class MenuController {

    /**
     * Handler that handles all operations regarding the file menu: Saving a file, Opening a file, Closing the window and
     * creating a new file
     * @param click
     * @param canvas
     * @param newFile
     * @param saveFile
     * @param openFile
     * @param closeFile
     */

    public static void menuHandler(ActionEvent click, Canvas canvas, MenuItem newFile, MenuItem saveFile, MenuItem openFile,
                                   MenuItem closeFile) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // NEWFILE HANDLER
        if (click.getSource() == newFile) {

            // Clears the canvas, clears the overlay and clears all records of shapes
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            ShapeController.circles.clear();
            ShapeController.ellipses.clear();
            ShapeController.rectangles.clear();
            ShapeController.squares.clear();
            ShapeController.lines.clear();
            ShapeController.overlay.getChildren().clear();
        }

        // SAVEFILE HANDLER
        else if (click.getSource() == saveFile) {

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Choose where you want to save your file");

            // Custom file extension specific to My Paint Shop files
            FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter(
                    "MyPaintShop (.mps) files", "*.mps"
            );
            chooser.getExtensionFilters().add(fileFilter);

            File file = chooser.showSaveDialog(MainDriver.stage);

            ShapeController s = new ShapeController();
            try {
                ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(file));
                write.writeObject(ShapeController.rectangles);
                write.writeObject(ShapeController.squares);
                write.writeObject(ShapeController.ellipses);
                write.writeObject(ShapeController.circles);
                write.writeObject(ShapeController.lines);
            } catch (IOException | NullPointerException e) {
                // Creates an alert box if there was an error or no file was selected
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error saving .mps file.");
                alert.show();
            }
        }

        // OPEN FILE HANDLER
        else if (click.getSource() == openFile) {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Choose what file you want to open");

            // Custom file extension specific to My Paint Shop files
            FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("MyPaintShop (.mps) files", "*.mps");
            chooser.getExtensionFilters().add(fileFilter);

            File file = chooser.showOpenDialog(MainDriver.stage);

            ArrayList<Rectangle> rectangles = new ArrayList<>();
            ArrayList<Square> squares = new ArrayList<>();
            ArrayList<Ellipse> ellipses = new ArrayList<>();
            ArrayList<Circle> circles = new ArrayList<>();
            ArrayList<Line> lines = new ArrayList<>();
            try {
                ObjectInputStream read = new ObjectInputStream(new FileInputStream(file));
                rectangles = (ArrayList<Rectangle>) read.readObject();
                squares = (ArrayList<Square>) read.readObject();
                ellipses = (ArrayList<Ellipse>) read.readObject();
                circles = (ArrayList<Circle>) read.readObject();
                lines = (ArrayList<Line>) read.readObject();

            } catch (ClassNotFoundException | IOException | NullPointerException e) {
                // Creates an alert box if there was an error or no file was selected
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error opening .mps file.");
                alert.show();
            }


            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

            for (Rectangle rectangle : rectangles) {
                // Initializes GraphicsContext with properties of the object as it was saved and draws it on the canvas
                ColorController.setColorFromShape(gc, rectangle);
                gc.setFill(javafx.scene.paint.Color.web(rectangle.getColor().getHexCode()));
                gc.setStroke(javafx.scene.paint.Color.web(rectangle.getColor().getHexCode()));
                gc.fillRect(rectangle.getXPosition(), rectangle.getYPosition(), rectangle.getWidth(), rectangle.getLength());
                gc.strokeRect(rectangle.getXPosition(), rectangle.getYPosition(), rectangle.getWidth(), rectangle.getLength());

                // Adding the shape back to the overlay pane to allowing editing it through the properties bar
                javafx.scene.shape.Rectangle rectPane = new javafx.scene.shape.Rectangle(rectangle.getXPosition(), rectangle.getYPosition(), rectangle.getWidth(), rectangle.getLength());
                rectPane.setFill(Color.web(rectangle.getColor().getHexCode()));
                ShapeController.overlay.getChildren().add(rectPane);
            }

            for (Square square : squares) {
                // Initializes GraphicsContext with properties of the object as it was saved and draws it on the canvas
                ColorController.setColorFromShape(gc, square);
                gc.setFill(javafx.scene.paint.Color.web(square.getColor().getHexCode()));
                gc.setStroke(javafx.scene.paint.Color.web(square.getColor().getHexCode()));
                gc.fillRect(square.getXPosition(), square.getYPosition(), square.getWidth(), square.getLength());
                gc.strokeRect(square.getXPosition(), square.getYPosition(), square.getWidth(), square.getLength());

                // Adding the shape back to the overlay pane to allowing editing it through the properties bar
                javafx.scene.shape.Rectangle squarePane = new javafx.scene.shape.Rectangle(square.getXPosition(), square.getYPosition(), square.getWidth(), square.getLength());
                squarePane.setFill(Color.web(square.getColor().getHexCode()));
                ShapeController.overlay.getChildren().add(squarePane);

            }

            for (Ellipse ellipse : ellipses) {
                // Initializes GraphicsContext with properties of the object as it was saved and draws it on the canvas
                ColorController.setColorFromShape(gc, ellipse);
                gc.setFill(javafx.scene.paint.Color.web(ellipse.getColor().getHexCode()));
                gc.setStroke(javafx.scene.paint.Color.web(ellipse.getColor().getHexCode()));
                gc.fillOval(
                        Math.min(ellipse.getXPosition(), ellipse.getCenterX()), Math.min(ellipse.getYPosition(), ellipse.getCenterY()),
                        ellipse.getRadiusX(), ellipse.getRadiusY()
                );

                // Adding the shape back to the overlay pane to allowing editing it through the properties bar
                javafx.scene.shape.Ellipse ellipsePane = new javafx.scene.shape.Ellipse(
                        Math.min(ellipse.getXPosition(), ellipse.getCenterX()), Math.min(ellipse.getYPosition(), ellipse.getCenterY()),
                        ellipse.getRadiusX(), ellipse.getRadiusY()
                );
                ellipsePane.setFill(Color.web(ellipse.getColor().getHexCode()));
                ShapeController.overlay.getChildren().add(ellipsePane);
            }

            for (Circle circle : circles) {
                // Initializes GraphicsContext with properties of the object as it was saved and draws it on the canvas
                ColorController.setColorFromShape(gc, circle);
                gc.setFill(javafx.scene.paint.Color.web(circle.getColor().getHexCode()));
                gc.setStroke(javafx.scene.paint.Color.web(circle.getColor().getHexCode()));
                gc.fillOval(
                        Math.min(circle.getXPosition(), circle.getCenterX()), Math.min(circle.getYPosition(), circle.getCenterY()),
                        circle.getRadius(), circle.getRadius()
                );

                // Adding the shape back to the overlay pane to allowing editing it through the properties bar
                javafx.scene.shape.Ellipse circlePane = new javafx.scene.shape.Ellipse(
                        Math.min(circle.getXPosition(), circle.getCenterX()), Math.min(circle.getYPosition(), circle.getCenterY()),
                        circle.getRadiusX(), circle.getRadiusY()
                );
                circlePane.setFill(Color.web(circle.getColor().getHexCode()));
                ShapeController.overlay.getChildren().add(circlePane);
            }

            for (Line line : lines) {
                // Initializes GraphicsContext with properties of the object as it was saved and draws it on the canvas
                ColorController.setColorFromShape(gc, line);
                gc.setLineWidth(line.getWidth());
                gc.setFill(javafx.scene.paint.Color.web(line.getColor().getHexCode()));
                gc.setStroke(javafx.scene.paint.Color.web(line.getColor().getHexCode()));
                gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());

                // Adding the shape back to the overlay pane to allowing editing it through the properties bar
                javafx.scene.shape.Line linePane = new javafx.scene.shape.Line(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                linePane.setStrokeWidth(line.getWidth());
                linePane.setStroke(Color.web(line.getColor().getHexCode()));
                ShapeController.overlay.getChildren().add(linePane);
            }

        } else if (click.getSource() == closeFile) {
            MainDriver.stage.close();
        }

    }

}

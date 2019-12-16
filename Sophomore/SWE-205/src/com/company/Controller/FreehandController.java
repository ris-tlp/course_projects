package com.company.Controller;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

public class FreehandController {

    /**
     * Handler that handles all operations related to free hand drawing and erasing
     *
     * @param click
     * @param canvas
     * @param freeDrawButton
     * @param eraserButton
     */
    public static void freeDrawHandler(ActionEvent click, Canvas canvas, Button freeDrawButton, Button eraserButton) {
        GraphicsContext gc = canvas.getGraphicsContext2D();


        canvas.setOnMousePressed(e -> {
            gc.setLineWidth(MainController.sliderValue);


            if (click.getSource() == freeDrawButton) {
                ColorController.setColor(gc);
                gc.beginPath();
                gc.lineTo(e.getX(), e.getY());
            } else if (click.getSource() == eraserButton) {
                gc.clearRect(e.getX() - gc.getLineWidth() / 2, e.getY() - gc.getLineWidth() / 2,
                        gc.getLineWidth(), gc.getLineWidth());
            }
        });

        canvas.setOnMouseDragged(e -> {
            if (click.getSource() == freeDrawButton) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            } else if (click.getSource() == eraserButton) {
                gc.clearRect(e.getX() - gc.getLineWidth() / 2, e.getY() - gc.getLineWidth() / 2,
                        gc.getLineWidth(), gc.getLineWidth());
            }
        });

        canvas.setOnMouseReleased(e -> {
            if (click.getSource() == freeDrawButton) {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
                gc.closePath();
            } else if (click.getSource() == eraserButton) {
                gc.clearRect(e.getX() - gc.getLineWidth() / 2, e.getY() - gc.getLineWidth() / 2,
                        gc.getLineWidth(), gc.getLineWidth());
            }
        });
    }

}

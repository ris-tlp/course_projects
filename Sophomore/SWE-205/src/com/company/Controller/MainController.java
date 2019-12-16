/**
 *
 * @author Omar Pervez Khan
 *
 */


package com.company.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class MainController {

    // Static slider value to enable access through multiple controllers
    public static double sliderValue;

    // FXML Variable references corresponding to Window.fxml
    @FXML
    AnchorPane root;
    @FXML
    MenuItem newFile, saveFile, openFile, closeFile;
    @FXML
    Button rectangleButton, squareButton, ellipseButton, circleButton, triangleButton, lineButton, freeDrawButton,
            eraserButton, deletePropertyButton, selectButton;
    @FXML
    ColorPicker colorPicker, colorProperty;
    @FXML
    Canvas canvas;
    @FXML
    Slider brushSlider;
    @FXML
    Label sliderLabel;
    @FXML
    TextField heightProperty, widthProperty, radius1Property, radius2Property;
    @FXML
    ToolBar propertiesToolbar;

    /**
     * Handles updating the slider label and slider value
     * @param event
     */
    public void setSliderValue(MouseEvent event) {
        sliderValue = ((Slider) event.getSource()).getValue();
        String labelValue = String.format("%.0f", MainController.sliderValue);
        sliderLabel.setText("Brush Size: " + labelValue);
    }

    /**
     * Handler that transfers control the ShapeController class
     * @param click
     * @see ShapeController
     */
    @FXML
    public void shapeHandler(ActionEvent click) {
        ShapeController.shapeHandler(root, click, canvas, rectangleButton, squareButton, ellipseButton, circleButton, triangleButton, lineButton, selectButton);
    }

    /**
     * Handler that transfers cotrol to the FreehandController class
     * @param click
     * @see FreehandController
     */
    @FXML
    public void freeDrawHandler(ActionEvent click) {
        FreehandController.freeDrawHandler(click, canvas, freeDrawButton, eraserButton);
    }

    /**
     * Handler that transfers control to the MenuController class
     * @param click
     * @see MenuController
     */
    @FXML
    public void menuHandler(ActionEvent click) {
        MenuController.menuHandler(click, canvas, newFile, saveFile, openFile, closeFile);
    }

    /**
     * Handler that transfers control to the ColorController class
     * @param click
     * @see ColorController
     */
    @FXML
    public void colorHandler(ActionEvent click) {
        ColorController.colorHandler(click, colorPicker);
    }

    /**
     * Handler that transfers control to the PropertiesController class
     * @param click
     * @see PropertiesController
     */
    @FXML
    public void propertiesHandler(ActionEvent click) {
            PropertiesController.propertiesHandler(deletePropertyButton, widthProperty, heightProperty, radius1Property, radius2Property, colorProperty, propertiesToolbar, canvas);
    }

    /**
     * Handler that transfers control to the SelectShapeController class and disables all property fields
     * in the properties bar
     * @param click
     * @see SelectShapeController
     */
    @FXML
    public void selectShapeHandler(ActionEvent click) {
        colorProperty.setDisable(true);
        deletePropertyButton.setDisable(true);
        heightProperty.setDisable(true);
        widthProperty.setDisable(true);
        radius2Property.setDisable(true);
        radius1Property.setDisable(true);
        ShapeController.shapeHandler(root, click, canvas, rectangleButton, squareButton, ellipseButton, circleButton, triangleButton, lineButton, selectButton);
        SelectShapeController.selectShapeHandler(canvas, deletePropertyButton, widthProperty, heightProperty, radius1Property, radius2Property, colorProperty, propertiesToolbar);


    }
}

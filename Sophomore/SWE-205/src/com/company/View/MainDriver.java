package com.company.View;


import com.company.Controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainDriver extends Application {

    // Static variables to allow access through multiple controllers
    private static MainController mainController = new MainController();
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Window.fxml"));
        Parent root = loader.load();
        loader.setController(mainController);

        primaryStage.setResizable(false);
        primaryStage.setTitle("My Paint Shop");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

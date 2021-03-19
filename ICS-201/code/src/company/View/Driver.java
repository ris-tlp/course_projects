package company.View;

//import company.Controller.ContactTableController;
import company.Controller.MainWindowController;
import company.Controller.ContactsTableController;
//import company.Controller.MainController;
import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Driver extends Application {

    // Static variables to allow access throughout multiple controllers
    public static Stage stage;

    public static MainWindowController mainWindowController = new MainWindowController();
    public static ContactsTableController contactsTableController = new ContactsTableController();


    // Returns the Root component of the ContactsTable.fxml to allow changing the stage through the MainWindowController
    public static AnchorPane loadTableRoot() throws IOException {
        FXMLLoader tableLoader = new FXMLLoader(Driver.class.getResource("ContactsTable.fxml"));
        return tableLoader.load();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Mail Merger");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}



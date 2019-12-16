package company.Controller;

import company.Models.Contact;
import company.View.Driver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

public class MainWindowController {

    // Static to enable referencing from ContactsTableController
    static final File[] templateFile = new File[1];
    public static final ArrayList<Contact> contacts = new ArrayList<>();
    public static ArrayList<String> tagList = new ArrayList<>();
    public static ChoiceBox tagDropDownReference;
    public static TextArea textAreaReference;
    public static TextField subjectTextReference;
    public static Stage tableStage;
    public static File contactsFile;



    // References to fx::id's set in MainWindow.fxml
    @FXML
    Menu fileMenu;
    @FXML
    MenuItem loadTemplate, saveTemplate, saveTemplateAs, aboutUs;
    @FXML
    TextArea textArea;
    @FXML
    Button clearTextButton, loadContactsButton, startMergeProcessButton;
    @FXML
    Label contactListLabel;
    @FXML
    ChoiceBox tagDropdown;
    @FXML
    AnchorPane root;
    @FXML
    TextField subjectText;

    public static void reloadContacts() throws FileNotFoundException {

        // Clearing contacts so it doesn't cause a duplicate contact error
        contacts.clear();

        Scanner scan = null;
        scan = new Scanner(new FileInputStream(contactsFile));

        // Reading the entire first line as tags
        List<String> tags = Arrays.asList(scan.nextLine().split(","));
        tagList = new ArrayList<>(tags);
        while (scan.hasNextLine()) {
            List<String> contact = Arrays.asList(scan.nextLine().split(","));

            // Check if any fields are empty, throw an error if so
            if (tags.size() != contact.size()) {
                // error
            } else {
                Iterator contactField = contact.iterator();
                Iterator tagField = tags.iterator();

                Contact newContact = new Contact();

                // Based on the assumption that each row of information is ordered
                while (contactField.hasNext() && tagField.hasNext()) {
                    newContact.getContactInformation().put((String) tagField.next(), (String) contactField.next());
                }

                // Check whether a contact is duplicated as stated by requirements.
                for (Contact c : contacts) {
                    if (c.equals(newContact)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Duplicate contact, please try again.");
                        alert.show();
                        break;
                    }
                }
                contacts.add(newContact);
            }
        }
    }

    /**
     * Initializes references to variables that are required in ContactsTableController
     * @see ContactsTableController
     */

    public void initialize() {
        tagDropDownReference = tagDropdown;
        textAreaReference = textArea;
        subjectTextReference = subjectText;
    }

    /**
     * Handles the inserting of a tag from the tag dropdown
     * @param click
     */
    public void dropDownHandler(ActionEvent click) {
        if (click.getSource() == tagDropdown) {
            String selectedTag = (String) tagDropdown.getValue();
            if (selectedTag != null) {
                textArea.insertText(textArea.getCaretPosition(), selectedTag);
                tagDropdown.getSelectionModel().clearSelection();
            }
        }
    }

    /**
     * Creates a new stage and shows the Contacts TableView once the Initiate MailMerge button is pressed
     * @throws IOException
     */
    public void initiateMailMerge() throws IOException {
        if (tableStage == null) {
            AnchorPane tableRoot = Driver.loadTableRoot();
            tableStage = new Stage();
            tableStage.setScene(new Scene(tableRoot));
            tableStage.show();
        } else if (tableStage.isShowing()) {
            tableStage.toFront();
        } else {
            tableStage.show();
        }

    }

    /**
     * Handles all actions in regards to loading contacts and templates and saving templates
     * @param click
     */
    public void functionHandler(ActionEvent click) {

        // Clears template region
        if (click.getSource() == clearTextButton) {
            textArea.clear();
        }

        else if (click.getSource() == loadContactsButton) {
            // Clearing contacts to avoid duplicate contact error on retries
            contacts.clear();
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select your contacts file");

            FileChooser.ExtensionFilter templateFilter =
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv");
            chooser.getExtensionFilters().add(templateFilter);

            File file = chooser.showOpenDialog(Driver.stage);
            contactsFile = file;


            Scanner scan = null;
            try {
                scan = new Scanner(new FileInputStream(file));

                // Format of template contacts a line of all the tags possible in the first line
                List<String> tags = Arrays.asList(scan.nextLine().split(","));
                tagList = new ArrayList<>(tags);

                // Format of template contains a new contact every line. Converting all
                // fields of contact to a list and creating a new HashMap that maps each tag
                // to its respective field and instantiates a new Contact object
                while (scan.hasNextLine()) {
                    List<String> contact = Arrays.asList(scan.nextLine().split(","));

                    // Check if any fields are empty, throw an error if so
                    if (tags.size() != contact.size()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Field empty.");
                        alert.show();
                    } else {
                        Iterator contactField = contact.iterator();
                        Iterator tagField = tags.iterator();

                        Contact newContact = new Contact();

                        // Based on the assumption that each row of information is ordered
                        while (contactField.hasNext() && tagField.hasNext()) {
                            newContact.getContactInformation().put((String) tagField.next(), (String) contactField.next());
                        }

                        // Check whether a contact is duplicated as stated by requirements.
                        for (Contact c : contacts) {
                            if (c.equals(newContact)) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setHeaderText("Duplicate contact, please try again.");
                                alert.show();
                                break;
                            }
                        }
                        contacts.add(newContact);
                    }
                }

                // Enable text area once contacts have been loaded as stated by requirements
                textArea.setDisable(false);
                startMergeProcessButton.setDisable(false);
                contactListLabel.setText(file.getName() + " has been loaded");

                // Setting Tags in the dropdown with default values
                ObservableList<String> options = FXCollections.observableList(tags);
                tagDropdown.setItems(options);

                scan.close();
            } catch (FileNotFoundException | NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error loading file, please try again.");
                alert.show();
            }
        }
    }

    public void menuHandler(ActionEvent click) {

        if (click.getSource() == loadTemplate) {
            try {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Select your template file");

                FileChooser.ExtensionFilter templateFilter =
                        new FileChooser.ExtensionFilter("Text Files", "*.txt");
                chooser.getExtensionFilters().add(templateFilter);

                // Storing reference to allow saving to same file through Save Template menu option
                templateFile[0] = chooser.showOpenDialog(Driver.stage);

                String fileText = new Scanner(templateFile[0]).useDelimiter("\\A").next();
                textArea.setText(fileText);
                saveTemplate.setDisable(false);
                saveTemplateAs.setDisable(false);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Template loaded successfully.");
                alert.show();
            } catch (FileNotFoundException | NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error opening template. Please try again.");
                alert.show();
            } catch (NoSuchElementException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error, your template is empty.");
                alert.show();
            }
        }
        else if (click.getSource() == saveTemplate) {
            String text = textArea.getText();

            PrintWriter writer = null;
            try {
                writer = new PrintWriter(new FileOutputStream(templateFile[0]));
                writer.print(text);
            } catch (FileNotFoundException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
            writer.close();
        }
        else if (click.getSource() == saveTemplateAs) {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Save your template file");

            FileChooser.ExtensionFilter templateFilter =
                    new FileChooser.ExtensionFilter("TXT Files", "*.txt");
            chooser.getExtensionFilters().add(templateFilter);

            File file = chooser.showSaveDialog(Driver.stage);
            String text = textArea.getText();

            PrintWriter writer = null;
            try {
                writer = new PrintWriter(new FileOutputStream(file));
                writer.print(text);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            writer.close();
        }
        else if (click.getSource() == aboutUs) {
            final Stage dialog = new Stage();
            dialog.initOwner(Driver.stage);
            VBox dialogVbox = new VBox(20);
            dialogVbox.setPadding(new Insets(5, 0, 0, 10));
            dialogVbox.setAlignment(Pos.TOP_LEFT);

            Label headerSoftwareText = new Label();
            headerSoftwareText.setText("About the Software");
            headerSoftwareText.setFont(new Font(17));
            headerSoftwareText.setWrapText(true);

            Label bodySoftwareText = new Label();
            bodySoftwareText.setText(
                    "Mail Merge is a process of automation which extracts information" +
                            "from a data source to generate large amount of mail documents." +
                            " These mail documents could be regular post mail or electronic mail documents. "
            );
            bodySoftwareText.setFont(new Font(15));
            bodySoftwareText.setWrapText(true);

            Label headerDevText = new Label();
            headerDevText.setText("About the Developers");
            headerDevText.setFont(new Font(17));
            headerDevText.setWrapText(true);

            Label bodyDevText = new Label();
            bodyDevText.setText("This software was developed by 2 students from the King Fahd University " +
                    "of Petroleum and Minerals: Omar Pervez Khan and Farhan Mohammed Abdul Qadir");
            bodyDevText.setFont(new Font(15));
            bodyDevText.setWrapText(true);

            dialogVbox.getChildren().add(headerSoftwareText);
            dialogVbox.getChildren().add(bodySoftwareText);
            dialogVbox.getChildren().add(headerDevText);
            dialogVbox.getChildren().add(bodyDevText);

            Scene dialogScene = new Scene(dialogVbox, 400, 400);
            dialog.setScene(dialogScene);
            dialog.show();
        }


    }
}

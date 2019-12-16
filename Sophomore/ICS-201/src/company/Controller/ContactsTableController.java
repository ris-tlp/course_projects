package company.Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.tools.javac.Main;
import company.Libraries.PasswordDialog;
import company.Libraries.SendEmail;
import company.Models.Contact;
import company.View.Driver;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.List;

import static company.Controller.MainWindowController.reloadContacts;

public class ContactsTableController {

    public static ArrayList<Contact> selectedContacts = new ArrayList<>();


    // References to fx::id's made in ContactsTable.fxml
    @FXML
    TableView contactsTable;

    @FXML
    Button emailButton, pdfButton, createContact;

    @FXML
    AnchorPane tableviewRoot;

    /**
     * Helper method to create templates tailored to data of a specific user
     *
     * @param i
     * @param dropdownTags
     * @param textArea
     * @return
     */
    public static String templateEditor(int i, ChoiceBox dropdownTags, TextArea textArea) {
        //This function is responsible for generating a custom template for each contact.
        String template = textArea.getText();
        List tags = dropdownTags.getItems();
        Iterator tagSelector = tags.iterator();
        while (tagSelector.hasNext()) {
            //checks for each tag in the template and replaces it with the corresponding value specific to each contact.
            String currentTag = (String) tagSelector.next();
            template = template.replace(currentTag, selectedContacts.get(i).getContactInformation().get(currentTag));
        }
        return template;
    }

    /**
     * Initializes the table with information of Contacts as soon as the Stage is set and shown
     */
    @FXML
    public void initialize() throws FileNotFoundException {
        this.loadTableData();
    }

    public void loadTableData() throws FileNotFoundException {
        // Reloads contacts to see if any new contact has been added
        MainWindowController.reloadContacts();

        ArrayList<Contact> contacts = MainWindowController.contacts;
        ArrayList<String> tagList = MainWindowController.tagList;

        /**
         * Contact information is stored in the form of Hashmap<String, String> -> <Tag, Value>
         * @see Contact
         *
         * Creates a new column with the name of the current tag and sets values for that column using
         * the tag's corresponding value
         */
        for (String tag : tagList) {
            TableColumn<HashMap<String, String>, String> column = new TableColumn<>(tag);
            column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(tag)));
            contactsTable.getColumns().add(column);
        }

        // Adds the data to the table
        ObservableList<HashMap<String, String>> data = FXCollections.observableArrayList();
        for (Contact contact : contacts) {
            data.add(contact.getContactInformation());
        }

        contactsTable.setItems(data);


    }

    /**
     * Creates a dialog box to incorporate fields required to create a new contact
     * @param click
     */
    public void newContactHandler(ActionEvent click) {

        // Will be used to create labels for each TextField
        ArrayList<String> tagList = MainWindowController.tagList;

        if (click.getSource() == createContact) {

            final Stage dialog = new Stage();
            dialog.initOwner(MainWindowController.tableStage);
            VBox dialogVbox = new VBox(20);
            dialogVbox.setPadding(new Insets(5, 0, 0, 10));
            dialogVbox.setAlignment(Pos.CENTER);

            // Will be used to hold all the data acquired from TextFields
            ArrayList<TextField> fields = new ArrayList<>();

            for (int i = 0; i < tagList.size(); i++) {
                // Creates a new label and sets it as the tag
                Label tag = new Label();
                tag.setText(tagList.get(i) + ": ");

                // Adds Tag: TextField to the view for input
                HBox hbox = new HBox();
                hbox.setAlignment(Pos.CENTER);
                hbox.getChildren().add(tag);
                TextField dataField = new TextField();

                // Sets id to reference later
                dataField.setId(tagList.get(i));

                // Adds the new Textfield to an ArrayList to allow referencing later
                fields.add(dataField);

                hbox.getChildren().add(dataField);
                hbox.setSpacing(5);
                dialogVbox.getChildren().add(hbox);
            }

            Button submit = new Button();
            submit.setText("Add Contact");
            dialogVbox.getChildren().add(submit);

            // Used to store data entered by user
            ArrayList<String> data = new ArrayList<>();

            submit.setOnAction(event -> {
                // Stores data from each field
                for (int i = 0; i < fields.size(); i++) {
                    data.add(fields.get(i).getText());
                }

                // Writes new contact to Contact file that was opened previously
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(new FileOutputStream(MainWindowController.contactsFile, true), true);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }

                StringBuilder dataLine = new StringBuilder();
                for (String dataField : data) {
                    dataLine.append(dataField).append(",");
                }
                dataLine.append("\n");


                writer.write(dataLine.toString());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Contact created");
                alert.show();
                writer.close();

                // Reloads the tableview
                contactsTable.getColumns().clear();
                try {
                    this.loadTableData();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            });


            Scene dialogScene = new Scene(dialogVbox);
            dialog.setScene(dialogScene);
            dialog.show();
        }

    }


    /**
     * Handler that handles all operations related to creating a PDF and sending an email
     *
     * @param click
     */
    public void functionHandler(ActionEvent click) {

        if (click.getSource() == pdfButton) {

            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select your template file");

            FileChooser.ExtensionFilter templateFilter =
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf");
            chooser.getExtensionFilters().add(templateFilter);

            File file = chooser.showSaveDialog(Driver.stage);

            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            try {
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();


                if (!MainWindowController.subjectTextReference.getText().isEmpty()) {
                    Paragraph coverPage = new Paragraph(MainWindowController.subjectTextReference.getText(), new Font(Font.FontFamily.TIMES_ROMAN, 32, Font.BOLD));
                    coverPage.setAlignment(Element.ALIGN_CENTER);
                    document.add(coverPage);
                    document.newPage();
                }

                for (int i = 0; i < selectedContacts.size(); i++) {
                    //creates a new paragraph for each page and then uses the templateEditor function to generate a custom template and adds it to the paragraph.
                    String personalisedTemplate = templateEditor(i, MainWindowController.tagDropDownReference, MainWindowController.textAreaReference);
                    Paragraph eachPage = new Paragraph(personalisedTemplate, new Font(Font.FontFamily.HELVETICA, 12));
                    try {
                        document.add(eachPage);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    //goes to the next page every time a custom template is generated and added to the page.
                    document.newPage();
                }
                try {
                    document.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("PDF Created");
                    alert.show();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("There was a problem in creating your PDF, please try again.");
                    alert.setContentText("Make sure your template isn't empty and you've selected at least 1 contact.");
                    alert.show();
                }
            } catch (NullPointerException | DocumentException | IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error opening file. Please try again.");
                alert.show();
            }
        } else if (click.getSource() == emailButton) {

            PasswordDialog dialog = new PasswordDialog();
            dialog.start(new Stage());
            String userName = null, password = null;
            //gets username and password from the user through a dialog box.
            try {
                userName = PasswordDialog.login.getUserName();
                password = PasswordDialog.login.getPassword();
            } catch (NullPointerException e) {
            }

            if (userName != null && password != null) {

                String subject;
                if (MainWindowController.subjectTextReference.getText().isEmpty()) {
                    subject = "No title";
                } else {
                    subject = MainWindowController.subjectTextReference.getText();
                }


                for (int i = 0; i < selectedContacts.size(); i++) {
                    String personalisedTemplate = templateEditor(i, MainWindowController.tagDropDownReference, MainWindowController.textAreaReference);
                    String toEmail = selectedContacts.get(i).getContactInformation().get("[[EMAIL_ADDRESS]]");
                    SendEmail mailer = new SendEmail(userName, password, toEmail, subject, personalisedTemplate);
                    if (mailer.sendEmail())
                        System.out.println("Email was sent to: " + toEmail + "  Successfully");
                }
            }

        }
    }


    public void contactTableHandler(MouseEvent mouseEvent) {
        ArrayList<Contact> contacts = MainWindowController.contacts;
        ArrayList<String> tagList = MainWindowController.tagList;

//        System.out.println(mouseEvent.getSource() == contactsTable);
        if (mouseEvent.getSource() == contactsTable) {
//        contactsTable.setOnMouseClicked(mouseEvent -> {
            selectedContacts.clear();
            contactsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            ObservableList<HashMap<String, String>> selected = FXCollections.observableArrayList();
            selected = (contactsTable.getSelectionModel().getSelectedItems());


            for (int i = 0; i < selected.size(); i++) {
                Contact temp = new Contact();
                for (int j = 0; j < tagList.size(); j++)
                    temp.getContactInformation().put(tagList.get(j), selected.get(i).get(tagList.get(j)));
                selectedContacts.add(temp);
            }
        }
    }
}

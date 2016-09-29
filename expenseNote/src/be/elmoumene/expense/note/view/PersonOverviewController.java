package be.elmoumene.expense.note.view;

import java.io.IOException;

import be.elmoumene.expense.note.MainApp;
import be.elmoumene.expense.note.entity.UserRole;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.PersonDTO;
import be.elmoumene.expense.note.service.FactoryService;
import be.elmoumene.expense.note.service.PersonService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PersonOverviewController {
    @FXML
    private TableView<PersonDTO> personTable;
    @FXML
    private TableColumn<PersonDTO, String> firstNameColumn;
    @FXML
    private TableColumn<PersonDTO, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label mobileLabel;


    // SERVICES

    private PersonService personService =  FactoryService.getPersonService();
    private static PersonOverviewController uniqueInstance;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    	uniqueInstance = this;
    }


    public static PersonOverviewController getInstance() {
        return uniqueInstance;
    }

    // Reference to the main application.
    private MainApp mainApp;



    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	// Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(
        		cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(
        		cellData -> cellData.getValue().lastNameProperty());

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //multiple selection (CRTL+Mouse selection)
		personTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showPersonDetails(newValue));

		loadData();

    }




    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;


    }

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showPersonDetails(PersonDTO person) {
    	if (person != null) {
    		// Fill the labels with info from the person object.
    		firstNameLabel.setText(person.getFirstName());
    		lastNameLabel.setText(person.getLastName());
    		streetLabel.setText(person.getStreet());
    		postalCodeLabel.setText(new String(""+person.getPostalCode()));
    		mobileLabel.setText(person.getMobile());
    		cityLabel.setText(person.getCity());

            //SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
            //String formattedDate = sdf.format(person.getBirthday());

    		birthdayLabel.setText(person.getBirthday().toString());
    		titleLabel.setText(person.getTitle());
    		emailLabel.setText(person.getEmail());


    	} else {
    		// Person is null, remove all the text.
    		firstNameLabel.setText("");
    		lastNameLabel.setText("");
    		streetLabel.setText("");
    		postalCodeLabel.setText("");
    		cityLabel.setText("");
    		birthdayLabel.setText("");
    		emailLabel.setText("");
    		mobileLabel.setText("");
    		emailLabel.setText("");
    		titleLabel.setText("");

    	}
    }

    /**
     * Called when the user clicks on the delete button.
     * @throws ExpenseNoteException
     */
    @FXML
    private void handleDeletePerson() throws ExpenseNoteException {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();

        PersonDTO p = personTable.getSelectionModel().getSelectedItem();

        if (selectedIndex >= 0){

        	if(p.getUserRole().equals(UserRole.ADMIN)){
            	Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("Error");
                alert.setHeaderText("Bad user selected");
                alert.setContentText("This user cannot be deleted");
                alert.showAndWait();
            	return;
            }

        	personTable.getItems().remove(selectedIndex);
        	personService.delete(p);

        }else{
          //nothing selected
        	Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");
            alert.showAndWait();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        PersonDTO tempPerson = new PersonDTO();
        showPersonNewDialog(tempPerson);
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
    	//PersonDTO tempPerson = new PersonDTO();
       // boolean okClicked = showPersonEditDialog(tempPerson);


        PersonDTO selectedPerson = personTable.getSelectionModel().getSelectedItem();
        	if (selectedPerson != null) {
            boolean okClicked = showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

	public boolean showPersonNewDialog(PersonDTO person) {
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/PersonNewDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Person");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the person into the controller.
        PersonNewDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setPerson(person);


        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}

	public boolean showPersonEditDialog(PersonDTO person) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/PersonNewDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edit Person");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        PersonNewDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setPerson(person);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public void loadData() {
        // Add observable list data to the table
        personTable.setItems(FXCollections.observableArrayList(personService.getPersons()));

	}
}
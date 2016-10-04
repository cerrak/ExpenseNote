package be.elmoumene.expense.note.view;

import be.elmoumene.expense.note.controller.FactoryController;
import be.elmoumene.expense.note.entity.UserRole;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.PersonDTO;
import be.elmoumene.expense.note.service.FactoryService;
import be.elmoumene.expense.note.service.PersonService;
import be.elmoumene.expense.note.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a person.
 *
 * @author Mouaad El Moumène
 */
public class PersonNewDialogController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField mobileField;
    @FXML
    private TextField titleField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private TextField passwordField;
    @FXML
    private Label passwordLabel;
    @FXML
    private CheckBox isActiv;
    @FXML
    private ButtonBar buttonBarNew;
    @FXML
    private ButtonBar buttonBarEdit;



    // SERVICES

    private PersonService personService =  FactoryService.getPersonService();

    private static PersonNewDialogController uniqueInstance;

    private Stage dialogStage;
    private PersonDTO person;
    private boolean okClicked = false;


    public static PersonNewDialogController getInstance() {
        return uniqueInstance;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    public PersonNewDialogController(){
    	uniqueInstance = this;
    }

    @FXML
    private void initialize() {
    	roleComboBox.getItems().addAll(UserRole.literalsCode());
    	roleComboBox.getSelectionModel().select(UserRole.USER.toString());

    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param person
     */
	public void setPerson(PersonDTO person) {
		this.person = person;

		firstNameField.setText(person.getFirstName());
		lastNameField.setText(person.getLastName());
		streetField.setText(person.getStreet());
		postalCodeField.setText("" + person.getPostalCode());
		mobileField.setText(person.getMobile());
		cityField.setText(person.getCity());

		if (person.getBirthday() != null)
			birthdayField.setText(DateUtil.format(person.getBirthday()));

		titleField.setText(person.getTitle());
		emailField.setText(person.getEmail());
		passwordField.setText(person.getPasswordField());

		if (person.getUserRole() != null)
			roleComboBox.setValue(person.getUserRole().toString());

		if (person.getIsActive() != null)
			isActiv.setSelected(person.getIsActive());

		if(person.getId() != null)
			passwordField.setVisible(false); // set invisible for editing
		if(person.getId() != null)
			passwordLabel.setVisible(false); // set invisible for editing


		if(person.getId() != null)
			buttonBarNew.setVisible(false);

		if(person.getId() == null ){
			buttonBarEdit.setVisible(false);

		}
	}


    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleNew() {
        if (isInputValid()) {


            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));
            person.setEmail(emailField.getText());
            person.setMobile(mobileField.getText());
            person.setTitle(titleField.getText());
            person.setUserRole(UserRole.fromString(roleComboBox.getSelectionModel().getSelectedItem()));
            person.setPasswordField(passwordField.getText());
            person.setIsActive(isActiv.isSelected());



            try {
				person = personService.create(person);

	            okClicked = true;
	            FactoryController.getPersonOverviewController().loadData();
	            dialogStage.close();

			} catch (ExpenseNoteException e) {
				// TODO fill the exception on screen
				e.printStackTrace();

			}


        }
    }

    @FXML
    private void handleEdit() {
        if (isInputValid()) {


            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));
            person.setEmail(emailField.getText());
            person.setMobile(mobileField.getText());
            person.setTitle(titleField.getText());
            person.setUserRole(UserRole.fromString(roleComboBox.getSelectionModel().getSelectedItem()));
            person.setPasswordField(passwordField.getText());
            person.setIsActive(isActiv.isSelected());



            try {
				person = personService.update(person);

	            okClicked = true;
	            FactoryController.getPersonOverviewController().loadData();
	            dialogStage.close();

			} catch (ExpenseNoteException e) {
				// TODO fill the exception on screen
				e.printStackTrace();

			}


        }
    }


    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "No valid street!\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }
        if(roleComboBox.getItems().equals(null)){
        	errorMessage += "No valid role!\n";
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}

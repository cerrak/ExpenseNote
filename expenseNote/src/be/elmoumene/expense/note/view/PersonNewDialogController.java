package be.elmoumene.expense.note.view;

import java.time.LocalDate;
import java.util.List;

import be.elmoumene.expense.note.controller.FactoryController;
import be.elmoumene.expense.note.entity.UserRole;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.ControllerDTO;
import be.elmoumene.expense.note.model.DepartmentDTO;
import be.elmoumene.expense.note.model.EntityDTO;
import be.elmoumene.expense.note.model.PersonDTO;
import be.elmoumene.expense.note.service.ControllerService;
import be.elmoumene.expense.note.service.DepartmentService;
import be.elmoumene.expense.note.service.EntityService;
import be.elmoumene.expense.note.service.FactoryService;
import be.elmoumene.expense.note.service.PersonService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    @FXML
    private ComboBox<EntityDTO> entityComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
	private ComboBox<DepartmentDTO> departmentComboBox;

    
    // SERVICES

    private PersonService<PersonDTO> personService =  FactoryService.getPersonService();
    
    private ControllerService<ControllerDTO> controllerService =  FactoryService.getControllerService();
     
    private EntityService entityService = FactoryService.getEntityService();
    	
    private DepartmentService departmentService =  FactoryService.getDepartmentService();
    
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
    private void initialize() throws ExpenseNoteException {
    	roleComboBox.getItems().addAll(UserRole.literalsCode());
    	roleComboBox.getSelectionModel().select(UserRole.USER.toString());
    	
    	List<EntityDTO> entities = entityService.getEntities();
    	
    	if(entities!=null)
    		entityComboBox.getItems().addAll(entities);
    	
    	entityComboBox.getSelectionModel().select(0);
    	
    	List<DepartmentDTO> deps = departmentService.getDepartments();
    	
    	if(deps!=null)
    	departmentComboBox.getItems().addAll(deps);
    	departmentComboBox.getSelectionModel().select(0);
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
			datePicker.setValue(person.getBirthday());

		titleField.setText(person.getTitle());
		emailField.setText(person.getEmail());
		passwordField.setText(person.getPasswordField());

		if (person.getUserRole() != null)
			roleComboBox.setValue(person.getUserRole().toString());

		if (person.getIsActive() != null)
			isActiv.setSelected(person.getIsActive());

//		if(person.getId() != null)
//			passwordField.setVisible(false); // set invisible for editing
//		if(person.getId() != null)
//			passwordLabel.setVisible(false); // set invisible for editing

		departmentComboBox.getSelectionModel().select(person.getDepartment());

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
			try {

				UserRole role = UserRole.fromString(roleComboBox.getSelectionModel().getSelectedItem());

				switch (role) {
				case ADMIN:
					/// TODO implement admin
					break;
				case CONTROLLER:
					ControllerDTO controller = new ControllerDTO();
					fillPerson(controller);
					if(entityComboBox.getValue() == null)
						throw new ExpenseNoteException("choose an entity for the controller");
					controller.setEntity(entityComboBox.getValue());
					controllerService.create(controller);
					break;
				default:
					PersonDTO person = new PersonDTO();
					fillPerson(person);
					personService.create(person);
				}

				okClicked = true;
				FactoryController.getPersonOverviewController().loadData();
				dialogStage.close();

			} catch (ExpenseNoteException e) {
				// TODO fill the exception on screen
				e.printStackTrace();

			}

		}
	}

    private void fillPerson(PersonDTO person){

		person.setFirstName(firstNameField.getText());
		person.setLastName(lastNameField.getText());
		person.setStreet(streetField.getText());
		person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
		person.setCity(cityField.getText());
		person.setBirthday(datePicker.getValue());
		person.setEmail(emailField.getText());
		person.setMobile(mobileField.getText());
		person.setTitle(titleField.getText());
		person.setUserRole(UserRole.fromString(roleComboBox.getSelectionModel().getSelectedItem()));
		person.setPasswordField(passwordField.getText());
		person.setIsActive(isActiv.isSelected());
		person.setDepartment(departmentComboBox.getSelectionModel().getSelectedItem());

    }

    @FXML
    private void handleEdit() {

        if (isInputValid()) {

            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(datePicker.getValue());
            person.setEmail(emailField.getText());
            person.setMobile(mobileField.getText());
            person.setTitle(titleField.getText());
            person.setUserRole(UserRole.fromString(roleComboBox.getSelectionModel().getSelectedItem()));
            person.setPasswordField(passwordField.getText());
            person.setIsActive(isActiv.isSelected());
            person.setDepartment(departmentComboBox.getSelectionModel().getSelectedItem());

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

        if (datePicker.getValue() == null) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (datePicker.getValue().isAfter(LocalDate.of(1998, 01, 01))) {
                errorMessage += "The employee must be major !\n";
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

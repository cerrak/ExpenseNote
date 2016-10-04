package be.elmoumene.expense.note.view;

import be.elmoumene.expense.note.MainApp;
import be.elmoumene.expense.note.controller.FactoryController;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.CategoryDTO;
import be.elmoumene.expense.note.model.CountryDTO;
import be.elmoumene.expense.note.model.ExpenseDTO;
import be.elmoumene.expense.note.service.CategoryService;
import be.elmoumene.expense.note.service.CountryService;
import be.elmoumene.expense.note.service.ExpenseService;
import be.elmoumene.expense.note.service.FactoryService;
import be.elmoumene.expense.note.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

	/**
	 * Dialog to edit details of a person.
	 *
	 * @author Mouaad El Moumène
	 */
	public class ExpenseNewDialogController {

	@FXML
    private ComboBox<CategoryDTO> categoryComboBox;
	@FXML
    private ComboBox<CountryDTO> countryComboBox;
    @FXML
    private TextField amountField;
    @FXML
    private CheckBox receipt;
    @FXML
    private TextField commentField;
    @FXML
    private TextField supplierField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField dateField;
    @FXML
    private ButtonBar saveNextBtnBar;
    @FXML
    private ButtonBar saveBtnBar;


    // SERVICES

    private ExpenseService expenseService =  FactoryService.getExpenseService();
    private CountryService countryService =  FactoryService.getCountryService();
    private CategoryService categoryService =  FactoryService.getCategoryService();
    
    private static ExpenseNewDialogController uniqueInstance;
    private Stage dialogStage;
    private ExpenseDTO expense;
    private boolean okClicked = false;
    private MainApp mainApp;

    public static ExpenseNewDialogController getInstance() {
        return uniqueInstance;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    public ExpenseNewDialogController(){
    	uniqueInstance = this;
    }

    @FXML
    private void initialize() throws ExpenseNoteException {
    	categoryComboBox.getItems().addAll(categoryService.getCategories());
    	countryComboBox.getItems().addAll(countryService.getCountries());
    	countryComboBox.getSelectionModel().selectFirst();
    	categoryComboBox.getSelectionModel().selectFirst();

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
     * Sets the expense to be edited in the dialog.
     *
     * @param person
     */
	public void setExpense(ExpenseDTO expense) {
		this.expense = expense;

		if(expense.getId() != null)

		amountField.setText(expense.getAmount().toString());
		commentField.setText(expense.getComment());
		supplierField.setText(expense.getSupplier());
		cityField.setText(expense.getCity());
		supplierField.setText(expense.getSupplier());
		commentField.setText(expense.getComment());

		receipt.setSelected(expense.getReceipt() == null ? false : true);


		if (expense.getDateExpense() != null)
		dateField.setText(DateUtil.format(expense.getDateExpense()));

		if (expense.getCategory() != null)
			categoryComboBox.setValue(expense.getCategory());

		if (expense.getCountry() != null)
			countryComboBox.setValue(expense.getCountry());

		if (expense.getId() != null)
			saveNextBtnBar.setVisible(false);
		if (expense.getId() == null)
			saveBtnBar.setVisible(false);

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
     * Called when the user clicks Save.
     */
    @FXML
    private void handelSave() {
        if (isInputValid()) {

        	expense.setPerson(mainApp.getUser());
            expense.setAmount(Float.parseFloat(amountField.getText()));
            expense.setDateExpense(DateUtil.parse(dateField.getText()));
            expense.setCategory(categoryComboBox.getSelectionModel().getSelectedItem());
            expense.setCity(cityField.getText());
            expense.setCountry(countryComboBox.getSelectionModel().getSelectedItem());
            expense.setReceipt(receipt.isSelected());
            expense.setComment(commentField.getText());
            expense.setSupplier(supplierField.getText());

            try {
            	expense = expenseService.create(expense);

	            okClicked = true;
	            FactoryController.getExpenseOverviewController().loadData();
	            dialogStage.close();

            } catch (ExpenseNoteException e) {
				// TODO fill the exception on screen
				e.printStackTrace();

			}

        }
    }

    /**
     * Called when the user clicks Save and next.
     */
    @FXML
    private void handelSaveAndNext() {
        if (isInputValid()) {


        	expense.setPerson(mainApp.getUser());
            expense.setAmount(Float.parseFloat(amountField.getText()));
            expense.setDateExpense(DateUtil.parse(dateField.getText()));
            expense.setCategory(categoryComboBox.getSelectionModel().getSelectedItem());
            expense.setCity(cityField.getText());
            expense.setCountry(countryComboBox.getSelectionModel().getSelectedItem());
            expense.setReceipt(receipt.isSelected());
            expense.setComment(commentField.getText());
            expense.setSupplier(supplierField.getText());

            try {
            	expense = expenseService.create(expense);

	            okClicked = true;
	            FactoryController.getExpenseOverviewController().loadData();


            } catch (ExpenseNoteException e) {
				// TODO fill the exception on screen
				e.printStackTrace();

			}

            amountField.setText("");
            dateField.setText("");
            //categoryComboBox.setItems(null);
            cityField.setText("");
            //countryComboBox.setItems(null);
            receipt.setSelected(false);
            commentField.setText("");
            supplierField.setText("");

        }
    }

    @FXML
    private void handelEdit() {
        if (isInputValid()) {

        	expense.setPerson(mainApp.getUser());
            expense.setAmount(Float.parseFloat(amountField.getText()));
            expense.setDateExpense(DateUtil.parse(dateField.getText()));
            expense.setCategory(categoryComboBox.getSelectionModel().getSelectedItem());
            expense.setCity(cityField.getText());
            expense.setCountry(countryComboBox.getSelectionModel().getSelectedItem());
            expense.setReceipt(receipt.isSelected());
            expense.setComment(commentField.getText());
            expense.setSupplier(supplierField.getText());

            try {
            	expense = expenseService.update(expense);

	            okClicked = true;
	            FactoryController.getExpenseOverviewController().loadData();
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

        if (amountField.getText() == null || amountField.getText().length() == 0) {
            errorMessage += "No valid amount code!\n";
        } else {
            // try to parse the amount  into a float.
            try {
                Float.parseFloat(amountField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid amount (must be a numeric)!\n";
            }
        }

        if (categoryComboBox.getItems().equals(null)) {
            errorMessage += "Please enter a category!\n";
        }
        if (supplierField.getText() == null || supplierField.getText().length() == 0) {
            errorMessage += "No valid supplier!\n";
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }

        if (dateField.getText() == null || dateField.getText().length() == 0) {
            errorMessage += "No valid date!\n";
        } else {
            if (!DateUtil.validDate(dateField.getText())) {
                errorMessage += "No valid date. Use the format dd.mm.yyyy!\n";
            }
        }
        if(countryComboBox.getItems().equals(null)){
        	errorMessage += "No valid country!\n";
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


	public MainApp getMainApp() {
		return mainApp;
	}


	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}




}

package be.elmoumene.expense.note.view;

import java.util.Calendar;
import java.util.List;

import be.elmoumene.expense.note.MainApp;
import be.elmoumene.expense.note.entity.Status;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.ExpenseDTO;
import be.elmoumene.expense.note.model.ExpenseNoteDTO;
import be.elmoumene.expense.note.service.ExpenseNoteService;
import be.elmoumene.expense.note.service.ExpenseService;
import be.elmoumene.expense.note.service.FactoryService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ExpenseNoteEditController {


    @FXML
    private ListView<ExpenseDTO> availableExpenses;
    @FXML
    private ListView<ExpenseDTO> selectedExpenses;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField commentField;
    @FXML
    private Label amountLabel;
    @FXML
    private Stage dialogStage;

    private ExpenseService expenseService = FactoryService.getExpenseService();
    private ExpenseNoteService expenseNoteService = FactoryService.getExpenseNoteService();
    private MainApp mainApp;
    private ExpenseNoteDTO expenseNote;

    private boolean okClicked = false;

    private static ExpenseNoteEditController uniqueInstance;


    public static ExpenseNoteEditController getInstance() {
        return uniqueInstance;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    public ExpenseNoteEditController(){
    	uniqueInstance = this;
    }

    @FXML
    public void initialize(){
    	availableExpenses.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	selectedExpenses.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


    }

	public void addExpenses(){
    	List<ExpenseDTO> selectedItem = availableExpenses.getSelectionModel().getSelectedItems();
    	selectedExpenses.getItems().addAll(selectedItem);
    	availableExpenses.getItems().removeAll(selectedItem);
    	availableExpenses.getSelectionModel().clearSelection();

    }

    public void removeExpenses(){
    	List<ExpenseDTO> selectedItem = selectedExpenses.getSelectionModel().getSelectedItems();
    	availableExpenses.getItems().addAll(selectedItem);
    	selectedExpenses.getItems().removeAll(selectedItem);
    	selectedExpenses.getSelectionModel().clearSelection();
    }

    public void handelSaveExpenseNote() throws ExpenseNoteException{
    	if(isInputValid())
		 {
			expenseNote.setPerson(mainApp.getUser());
    		expenseNote.setName(nameField.getText());
    		expenseNote.setComment(commentField.getText());
    		expenseNote.setStatus((Status.DRAFT)); //set de la note de frais "DRAFT" lors de la création de la NdF
    		expenseNote.setCeationDate(Calendar.getInstance());

    		List<ExpenseDTO> selectedItem = selectedExpenses.getItems();
     		expenseNote.setExpenses(selectedItem);
        	expenseNote = expenseNoteService.create(expenseNote);

            okClicked = true;
            FactoryController.getExpenseNoteOverviewController().loadData();
            dialogStage.close();
		}
    }


    public void cancelExpenseNote(){
    	//business logic
    	//showExpenseNoteOverview();
    	dialogStage.close();

    }

	public ExpenseNoteDTO getExpenseNote() {
		return expenseNote;
	}

	public void setExpenseNote(ExpenseNoteDTO expenseNote) {
		this.expenseNote = expenseNote;

		if(expenseNote.getId() != null)
			commentField.setText(expenseNote.getComment());
			nameField.setText(expenseNote.getName());
			initialize();

			List<ExpenseDTO> selectedItem = availableExpenses.getItems();
     		expenseNote.setExpenses(selectedItem);



        	 okClicked = true;
             FactoryController.getExpenseNoteOverviewController().loadData();
             dialogStage.close();
	}

	public void setDialogStage(Stage dialogStage) {
	      this.dialogStage = dialogStage;
	 }

	public Boolean isInputValid(){
		String errorMessage = "";

		if(nameField.getText() == null || nameField.getText().length() == 0){
			errorMessage += "Please enter a report name";
		}

		 if (errorMessage.length() == 0) {
	         return true;

	    } else {
	         // Show the error message.
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.initOwner(dialogStage);
	        alert.setTitle("Invalid Field");
	        alert.setHeaderText("Please correct invalid fields");
	        alert.setContentText(errorMessage);

	        alert.showAndWait();

	        return false;
	      }
	}

	 public void load(){
	    	List<ExpenseDTO> expenses = expenseService.getAvailableExpenses(mainApp.getUser());
	    	availableExpenses.getItems().addAll(expenses);
	    }

	 public MainApp getMainApp() {
			return mainApp;
		}

	 public void setMainApp(MainApp mainApp) {
			this.mainApp = mainApp;
		}

	 public boolean isOkClicked() {
	        return okClicked;
	    }

}

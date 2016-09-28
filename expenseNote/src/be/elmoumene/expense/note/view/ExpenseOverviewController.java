package be.elmoumene.expense.note.view;

import java.io.IOException;
import java.time.LocalDate;

import be.elmoumene.expense.note.MainApp;
import be.elmoumene.expense.note.model.ExpenseDTO;
import be.elmoumene.expense.note.model.ExpenseNoteDTO;
import be.elmoumene.expense.note.service.ExpenseService;
import be.elmoumene.expense.note.service.FactoryService;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExpenseOverviewController {
	@FXML
    private TableView<ExpenseDTO> expenseTable;
    @FXML
    private TableColumn<ExpenseDTO, LocalDate> dateColumn;
    @FXML
    private TableColumn<ExpenseDTO, String> categoryColumn;
    @FXML
    private TableColumn<ExpenseDTO, String> countryColumn;
    @FXML
    private TableColumn<ExpenseDTO, String> currencyColumn;
    @FXML
    private TableColumn<ExpenseDTO, Float> amountColumn;
    @FXML
    private TableColumn<ExpenseDTO, Boolean> receiptColumn;

    @FXML
    private Label commentLabel;
    @FXML
    private Label supplierLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private TextField expenseNoteNameField;
    @FXML
    private TextField commentField;


 // SERVICES

    private ExpenseService expenseService =  FactoryService.getExpenseService();
    private MainApp mainApp;

    private static ExpenseOverviewController uniqueInstance;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ExpenseOverviewController() {
    	uniqueInstance = this;
    }

    public static ExpenseOverviewController getInstance() {
        return uniqueInstance;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the expense table with the columns.
    	dateColumn.setCellValueFactory(cellData ->
    		cellData.getValue().dateExpenseProperty());
    	countryColumn.setCellValueFactory(cellData ->
    		cellData.getValue().countryProperty().asString());
    	currencyColumn.setCellValueFactory(cellData ->
    		cellData.getValue().currencyProperty());
    	amountColumn.setCellValueFactory(cellData ->
    		cellData.getValue().amountProperty().asObject());
    	categoryColumn.setCellValueFactory(cellData ->
    		cellData.getValue().categoryProperty().asString());


    	receiptColumn.setCellValueFactory(cellData ->cellData.getValue().receiptProperty());

    	receiptColumn.setCellFactory(CheckBoxTableCell.forTableColumn(receiptColumn));

    	showExpenseDetails(null);
    	expenseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	expenseTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showExpenseDetails(newValue));



    }

    public void showExpense(){
    	loadData();
    }

	private void showExpenseDetails(ExpenseDTO dto) {
		if (dto != null) {
    		// Fill the labels with info from the person object.

    		supplierLabel.setText(dto.getSupplier());
    		cityLabel.setText(dto.getCity());
    		commentLabel.setText(dto.getComment());

		}
		else{
			supplierLabel.setText("");
			cityLabel.setText("");
			commentLabel.setText("");

		}

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	public void loadData() {
        // Add observable list data to the table
        expenseTable.setItems(FXCollections.observableArrayList(expenseService.getAvailableExpenses(mainApp.getUser())));

	}

	public boolean showExpenseNewDialog(ExpenseDTO expense) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ExpenseNewDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Add Expense");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        ExpenseNewDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setExpense(expense);
	        controller.setMainApp(mainApp);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean showExpenseEditDialog(ExpenseDTO expense){
		try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ExpenseNewDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edit Expense");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the expense into the controller.
	        ExpenseNewDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setExpense(expense);
	        controller.setMainApp(mainApp);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();


	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public void handleNewExpense(){
		ExpenseDTO tempExpense = new ExpenseDTO();
		boolean okClicked = showExpenseNewDialog(tempExpense);
	}

	public void handleEditExpense(){
		ExpenseDTO selectedExpense = expenseTable.getSelectionModel().getSelectedItem();
    	if (selectedExpense != null) {
        boolean okClicked = showExpenseEditDialog(selectedExpense);
        if (okClicked) {
            showExpenseDetails(selectedExpense);
        }

    } else {
        // Nothing selected.
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Expense Selected");
        alert.setContentText("Please select an expense in the table.");

        alert.showAndWait();
    	}
	}

	public void handleCreateExpenseNote(){
		ExpenseNoteDTO tempExpense = new ExpenseNoteDTO();
		//boolean okClicked = showExpenseNoteOverviewDialog(tempExpense);
	}

	public void handelShowExpenseNote(){

		mainApp.showExpenseNoteOverview();

	}


}

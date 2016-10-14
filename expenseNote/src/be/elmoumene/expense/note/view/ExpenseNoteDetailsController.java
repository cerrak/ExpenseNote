package be.elmoumene.expense.note.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import be.elmoumene.expense.note.MainApp;
import be.elmoumene.expense.note.model.ExpenseDTO;
import be.elmoumene.expense.note.model.ExpenseNoteDTO;
import be.elmoumene.expense.note.service.ExpenseService;
import be.elmoumene.expense.note.service.FactoryService;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExpenseNoteDetailsController {
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
    private Label creationDateLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label receiptLabel;
    @FXML
    private Label commentLabel;
    @FXML
    private Label supplierLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label userLabel;
    @FXML
    private Label statusLabel;

    private ExpenseNoteDTO expenseNote;


 // SERVICES

    private ExpenseService expenseService =  FactoryService.getExpenseService();
    private MainApp mainApp;

    private static ExpenseNoteDetailsController uniqueInstance;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ExpenseNoteDetailsController() {
    	uniqueInstance = this;
    }

    public static ExpenseNoteDetailsController getInstance() {
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
    	//currencyColumn.setCellValueFactory(cellData ->
    		//cellData.getValue().currencyProperty());
    	amountColumn.setCellValueFactory(cellData ->
    		cellData.getValue().amountProperty().asObject());
    	receiptColumn.setCellValueFactory(cellData ->
    		cellData.getValue().receiptProperty());
    	categoryColumn.setCellValueFactory(cellData ->
    		cellData.getValue().categoryProperty().asString());
    	receiptColumn.setCellFactory(CheckBoxTableCell.forTableColumn(receiptColumn));



    	//showExpenseDetails(null);
    	expenseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	expenseTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showExpenseDetails(newValue));

    	expenseTable.setOnMousePressed(new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent event) {
    	        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
    	            handleEditExpense();
    	        }
    	    }
    	});


    }


	private void showExpenseDetails(ExpenseDTO dto) {
		if (dto != null) {


			userLabel.setText(dto.getPerson().getFirstName() + " " + dto.getPerson().getLastName());
			creationDateLabel.setText(dto.getDateExpense().toString());
			categoryLabel.setText(dto.getCategory().toString());
			countryLabel.setText(dto.getCountry().toString());
			//receiptLabel.set(dto.getReceipt()));
			commentLabel.setText(dto.getComment());
			supplierLabel.setText(dto.getSupplier());
			cityLabel.setText(dto.getCity());

		}
		else{
			creationDateLabel.setText("");
			categoryLabel.setText("");
			countryLabel.setText("");
			commentLabel.setText("");
			supplierLabel.setText("");
			cityLabel.setText("");
			userLabel.setText("");
			}

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	public void loadData() {
        // Add observable list data to the table

		List<ExpenseDTO> expenseList = expenseService.getExpensesFromExpenseNoteId(expenseNote.getId());

        expenseTable.setItems(FXCollections.observableArrayList(expenseList));

        amountLabel.setText(ExpenseNoteOverviewController.totalAmountCalculation(expenseList).toString());
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

	public void handleNewExpense(){
		ExpenseDTO tempExpense = new ExpenseDTO();
		boolean okClicked = showExpenseNewDialog(tempExpense);
	}

	public void handleCreateExpenseNote(){
		ExpenseNoteDTO tempExpense = new ExpenseNoteDTO();
		//boolean okClicked = showExpenseNoteOverviewDialog(tempExpense);
	}

	/**s
	 * @return the expenseNote
	 */
	public ExpenseNoteDTO getExpenseNote() {
		return expenseNote;
	}

	/**
	 * @param expenseNote the expenseNote to set
	 */
	public void setExpenseNote(ExpenseNoteDTO expenseNote) {
		this.expenseNote = expenseNote;
	}

	public void handelShowExpense(){

		mainApp.showExpenseOverview();

	}

	public void handleEditExpense() {

		ExpenseDTO selectedExpense = expenseTable.getSelectionModel().getSelectedItem();
		ExpenseDTO dto = expenseService.getExpenseById(selectedExpense.getId());

		if (selectedExpense != null) {
			showExpenseNewDialog(dto);
			loadData();

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

	public void handelSendExpenseNote(){

	}

}

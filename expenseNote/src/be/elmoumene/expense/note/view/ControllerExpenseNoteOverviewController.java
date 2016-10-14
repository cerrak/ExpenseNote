package be.elmoumene.expense.note.view;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import be.elmoumene.expense.note.MainApp;
import be.elmoumene.expense.note.entity.Status;
import be.elmoumene.expense.note.model.ExpenseDTO;
import be.elmoumene.expense.note.model.ExpenseNoteDTO;
import be.elmoumene.expense.note.service.ExpenseNoteService;
import be.elmoumene.expense.note.service.ExpenseService;
import be.elmoumene.expense.note.service.FactoryService;
import be.elmoumene.expense.note.util.DateUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerExpenseNoteOverviewController {
	@FXML
	private TableView<ExpenseNoteDTO> expenseNoteTable;
	@FXML
	private TableColumn<ExpenseNoteDTO, LocalDate> dateColumn;
	@FXML
	private TableColumn<ExpenseNoteDTO, String> nameColumn;
	@FXML
	private TableColumn<ExpenseNoteDTO, Integer> nbrTransationsColumn;
	@FXML
	private TableColumn<ExpenseNoteDTO, Float> totalColumn;
	@FXML
	private TableColumn<ExpenseNoteDTO, Status> statusColumn;
	@FXML
	private TableColumn<ExpenseNoteDTO, Status> currencyColumn;
	@FXML
	private TextField nameField;
	@FXML
	private TextField commentField;
	@FXML
	private ListView<ExpenseDTO> availableExpenses;
	@FXML
	private ListView<ExpenseDTO> selectedExpenses;

	// SERVICES

	private ExpenseNoteService expenseNoteService = FactoryService.getExpenseNoteService();
	private ExpenseService expenseService = FactoryService.getExpenseService();
	private MainApp mainApp;

	private static ControllerExpenseNoteOverviewController uniqueInstance;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public ControllerExpenseNoteOverviewController() {
		uniqueInstance = this;
	}

	public static ControllerExpenseNoteOverviewController getInstance() {
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

		new SimpleObjectProperty<LocalDate>(DateUtil
				.parse(new SimpleDateFormat("dd.MM.yyyy").format(cellData.getValue().getCeationDate().getTime()))));
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		nbrTransationsColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<Integer>(
				totalTransactionByExpenseNote(cellData.getValue().getExpenses())));
		totalColumn.setCellValueFactory(
				cellData -> new SimpleObjectProperty<Float>(totalAmountCalculation(cellData.getValue().getExpenses())));
		statusColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<Status>(cellData.getValue().getStatus()));

		expenseNoteTable.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					ExpenseNoteDTO selectedItem = expenseNoteTable.getSelectionModel().getSelectedItem();
					mainApp.showFormViewExpenseNoteDetails(selectedItem);
				}
			}
		});

	}

	public static Float totalAmountCalculation(List<ExpenseDTO> expenses) {
		Float totalAmount = 0f;

		for (ExpenseDTO dto : expenses) {
			totalAmount += dto.getAmount();
		}

		return totalAmount;

	}

	public Integer totalTransactionByExpenseNote(List<ExpenseDTO> expenses) {
		Integer totalTransaction = 0;

		for (int i = 0; i < expenses.size(); i++) {
			totalTransaction += 1;
		}
		return totalTransaction;

	}

	public void showExpenseNote() {
		loadData();
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	public void loadData() {
		// Add observable list data to the table
		expenseNoteTable.setItems(
				FXCollections.observableArrayList(expenseNoteService.getExpenseNotesFromPerson(mainApp.getUser())));

	}

	public void showExpenseNoteDetail(ExpenseNoteDTO dto) {
		// if(dto != null){
		// fill the data's with the info from the expense note

	}

	public void showExpenseNoteNewDialog(ExpenseNoteDTO expenseNote) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ExpenseNoteEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add/Remove Expenses");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the person into the controller.
			ExpenseNoteEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setExpenseNote(expenseNote);
			controller.setMainApp(mainApp);

			controller.load();

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean showExpenseNoteEditDialog(ExpenseNoteDTO expenseNote) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ExpenseNoteEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add/Remove Expenses");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(mainApp.getPrimaryStage());
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			// Set the expenses into the controller.
			ExpenseNoteEditController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setExpenseNote(expenseNote);
			controller.setMainApp(mainApp);

			controller.load();

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void handleNewExpenseNote() {
		showExpenseNoteNewDialog(null);
	}

	public void handleEditExpenseNote() {
		ExpenseNoteDTO selectedExpenseNote = expenseNoteTable.getSelectionModel().getSelectedItem();
		if (selectedExpenseNote != null) {

			if (showExpenseNoteEditDialog(selectedExpenseNote)) {
				showExpenseNoteDetail(selectedExpenseNote);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Expense Note Selected");
			alert.setContentText("Please select an expense Note in the table.");

			alert.showAndWait();
		}

	}

	public void handelShowExpense() {

		mainApp.showExpenseOverview();

	}

	public void handelDelete() {

		ExpenseNoteDTO dto = expenseNoteTable.getSelectionModel().getSelectedItem();

		if (dto == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Expense Note Selected");
			alert.setContentText("Please select an Expense Note in the table.");
			alert.showAndWait();
		} else {
			expenseNoteService.delete(dto);
			loadData();
		}

	}

}

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExpenseNoteOverviewController {
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


 // SERVICES

    private ExpenseNoteService expenseNoteService =  FactoryService.getExpenseNoteService();
    private MainApp mainApp;

    private static ExpenseNoteOverviewController uniqueInstance;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ExpenseNoteOverviewController() {
    	uniqueInstance = this;
    }

    public static ExpenseNoteOverviewController getInstance() {
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

	    	new SimpleObjectProperty<LocalDate>(
	    			DateUtil.parse(
	    					new SimpleDateFormat("dd.MM.yyyy").format(
	    							cellData.getValue().getCeationDate().getTime()
	    							)
	    					)
	    	)


    	);


    	nameColumn.setCellValueFactory(row ->
    									row.getValue().nameProperty());
    	nbrTransationsColumn.setCellValueFactory(row ->
    												new SimpleObjectProperty<Integer>(totalTransactionByExpenseNote(row.getValue().getExpenses())));

    	totalColumn.setCellValueFactory(row ->
    										new SimpleObjectProperty<Float>(totalAmountCalculation(row.getValue().getExpenses())));

    	statusColumn.setCellValueFactory(cellData ->
    										new SimpleObjectProperty<Status>(cellData.getValue().getStatus()));

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

    public Float totalAmountCalculation(List<ExpenseDTO> expenses){
    	Float totalAmount = 0f;

    	for (ExpenseDTO dto : expenses) {
			totalAmount += dto.getAmount();
		}

    	return totalAmount;

    }

    public Integer totalTransactionByExpenseNote(List<ExpenseDTO> expenses){
    	Integer totalTransaction = 0;

    	for(int i=0; i<expenses.size(); i++){
    		totalTransaction += 1;
    	}
    	return totalTransaction;

    }

    public void showExpenseNote(){
    	loadData();
    }


	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	public void loadData() {
        // Add observable list data to the table
        expenseNoteTable.setItems(FXCollections.observableArrayList(expenseNoteService.getExpenseNotesFromPerson(mainApp.getUser())));

	}

	public void showExpenseNoteDetail(ExpenseNoteDTO expenseNote){
		try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ExpenseNoteOverview.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Expense Note Detail");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        ExpenseNoteOverviewController controller = loader.getController();
	        //controller.setDialogStage(dialogStage);
	        //controller.setExpenseNote(expenseNote);
	        controller.setMainApp(mainApp);

	        //controller.load();

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void showExpenseNewDialog(ExpenseNoteDTO expenseNote) {
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

	public void handleNewExpenseNote(){
		ExpenseNoteDTO tempExpense = new ExpenseNoteDTO();
		showExpenseNewDialog(tempExpense);

	}

	public void handleEditExpenseNote(){

		//boolean okClicked = showExpenseNoteEditDialog(tempExpense);
	}

	public void handelShowExpense(){

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

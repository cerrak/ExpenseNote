package be.elmoumene.expense.note.controller.department;

import java.io.IOException;

import be.elmoumene.expense.note.controller.JavaFXBaseController;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.DepartmentDTO;
import be.elmoumene.expense.note.service.DepartmentService;
import be.elmoumene.expense.note.service.FactoryService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DepartmentOverviewController extends JavaFXBaseController<DepartmentDTO> {

	@FXML
    private TableView<DepartmentDTO> departmentTable;
	@FXML
	private TableColumn<DepartmentDTO, Integer> id;
    @FXML
    private TableColumn<DepartmentDTO, String> name;

    private DepartmentService departmentService =  FactoryService.getDepartmentService();

    private static DepartmentOverviewController uniqueInstance;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public DepartmentOverviewController() {
    	uniqueInstance = this;
    }

    public static DepartmentOverviewController getInstance() {
        return uniqueInstance;
    }

    @Override
    public void initialize() {
    	name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    }

    @Override
	public void loadData() throws ExpenseNoteException {
    	departmentTable.setItems(FXCollections.observableArrayList(departmentService.getDepartments()));
	}

	public void showNewAndEditDialog(DepartmentDTO department) throws ExpenseNoteException {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("DepartmentNewDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        if(department == null){
	        	dialogStage.setTitle("add Department");
	        }else{
	        	dialogStage.setTitle("edit Department");
	        }
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(getDialogStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        DepartmentNewController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setModel(department);
	        controller.setMainApp(getMainApp());
	        controller.loadData();

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	public void handleNewDepartment() throws ExpenseNoteException{
		showNewAndEditDialog(null);
	}

	public void handleEditDepartment() throws ExpenseNoteException {
		DepartmentDTO dto = departmentTable.getSelectionModel().getSelectedItem();
		if (dto != null) {
			showNewAndEditDialog(dto);

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(getMainApp().getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Department Selected");
			alert.setContentText("Please select a Department in the table.");

			alert.showAndWait();
		}

	}

	public void handelDelete() throws ExpenseNoteException {

		DepartmentDTO dto = departmentTable.getSelectionModel().getSelectedItem();

		if (dto == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(getMainApp().getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Department Selected");
			alert.setContentText("Please select a Department in the table.");

			alert.showAndWait();
		} else {

        	Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);

            alert.initOwner(getDialogStage());
            alert.setTitle("Question???");
            alert.setHeaderText("Delete Department");
            alert.setContentText("Do you really want to delete?");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
            	// ref au create /update
            	//utiliser le try & catch pour afficher le message d'erreur a l'ecran
            	departmentService.delete(dto);
            }

			loadData();
		}

	}


}

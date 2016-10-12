package be.elmoumene.expense.note.controller.department;

import be.elmoumene.expense.note.controller.FactoryController;
import be.elmoumene.expense.note.controller.JavaFXBaseController;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.DepartmentDTO;
import be.elmoumene.expense.note.model.EntityDTO;
import be.elmoumene.expense.note.service.DepartmentService;
import be.elmoumene.expense.note.service.EntityService;
import be.elmoumene.expense.note.service.FactoryService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class DepartmentNewController extends JavaFXBaseController<DepartmentDTO>{

    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<EntityDTO> entityComboBox;
    //@FXML
    //private TableView<DepartmentDTO> departmentTable;

    private DepartmentService departmentService =  FactoryService.getDepartmentService();
    private EntityService entityService = FactoryService.getEntityService();

    private static DepartmentNewController uniqueInstance;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public DepartmentNewController() {
    	uniqueInstance = this;
    }

    public static DepartmentNewController getInstance() {
        return uniqueInstance;
    }


	@Override
	public void initialize() throws ExpenseNoteException {
		entityComboBox.getItems().addAll(entityService.getEntities());
	}

	@Override
	public void loadData() throws ExpenseNoteException {
		if(getModel() != null){
			nameField.setText(getModel().getName());
			entityComboBox.setValue(getModel().getEntity());
			//departmentTable.setItems(FXCollections.observableArrayList(departmentService.getDepartments()));
		}

	}

//	public void showNewAndEditDialog(DepartmentDTO department) throws ExpenseNoteException {
//	    try {
//	        // Load the fxml file and create a new stage for the popup dialog.
//	        FXMLLoader loader = new FXMLLoader();
//	        loader.setLocation(getClass().getResource("DepartmentNewDialog.fxml"));
//	        AnchorPane page = (AnchorPane) loader.load();
//
//	        // Create the dialog Stage.
//	        Stage dialogStage = new Stage();
//	        if(department == null){
//	        	dialogStage.setTitle("add department");
//	        }else{
//	        	dialogStage.setTitle("edit department");
//	        }
//	        dialogStage.initModality(Modality.WINDOW_MODAL);
//	        dialogStage.initOwner(getDialogStage());
//	        Scene scene = new Scene(page);
//	        dialogStage.setScene(scene);
//
//	        // Set the person into the controller.
//	        DepartmentNewController controller = loader.getController();
//	        controller.setDialogStage(dialogStage);
//	        controller.setModel(department);
//	        controller.setMainApp(getMainApp());
//	        controller.loadData();
//
//	        // Show the dialog and wait until the user closes it
//	        dialogStage.showAndWait();
//
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	}

	public void handleSave() throws ExpenseNoteException{
		DepartmentDTO dto = getModel();
		if(dto == null)
			dto = new DepartmentDTO();

		dto.setDepartmentName(nameField.getText());
		dto.setEntity(entityComboBox.getSelectionModel().getSelectedItem());

			try {
				if(dto.getId() == null){
					departmentService.create(dto);
				}else{
					departmentService.update(dto);
				}
			} catch (ExpenseNoteException e) {
	               Alert alert = new Alert(AlertType.ERROR);
	               alert.setContentText(e.getMessage());
	               alert.showAndWait();

			}
		FactoryController.getDepartmentOverviewController().loadData();
		getDialogStage().close();
	}

//	public void handleEdit() throws ExpenseNoteException {
//		DepartmentDTO dto = departmentTable.getSelectionModel().getSelectedItem();
//		if (dto != null) {
//			showNewAndEditDialog(dto);
//
//		} else {
//			// Nothing selected.
//			Alert alert = new Alert(AlertType.WARNING);
//			alert.initOwner(getMainApp().getPrimaryStage());
//			alert.setTitle("No Selection");
//			alert.setHeaderText("No Country Note Selected");
//			alert.setContentText("Please select a country in the table.");
//
//			alert.showAndWait();
//		}
//
//	}

	public void handleCancel(){
		getDialogStage().close();
	}

}

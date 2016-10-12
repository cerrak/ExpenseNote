package be.elmoumene.expense.note.controller.entity;

import java.io.IOException;

import be.elmoumene.expense.note.controller.JavaFXBaseController;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.EntityDTO;
import be.elmoumene.expense.note.service.EntityService;
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

public class EntityOverviewController extends JavaFXBaseController<EntityDTO> {

	@FXML
    private TableView<EntityDTO> entityTable;
	@FXML
	private TableColumn<EntityDTO, Integer> id;
    @FXML
    private TableColumn<EntityDTO, String> name;

    private EntityService EntityService =  FactoryService.getEntityService();

    private static EntityOverviewController uniqueInstance;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public EntityOverviewController() {
    	uniqueInstance = this;
    }

    public static EntityOverviewController getInstance() {
        return uniqueInstance;
    }

    @Override
    public void initialize() {
    	name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    }

    @Override
	public void loadData() throws ExpenseNoteException {
    	entityTable.setItems(FXCollections.observableArrayList(EntityService.getEntities()));
	}

	public void showNewAndEditDialog(EntityDTO entity) throws ExpenseNoteException {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("EntityNewDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        if(entity == null){
	        	dialogStage.setTitle("add entity");
	        }else{
	        	dialogStage.setTitle("edit entity");
	        }
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(getDialogStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        EntityNewController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setModel(entity);
	        controller.setMainApp(getMainApp());
	        controller.loadData();

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	public void handleNewEntity(){
		try {
			showNewAndEditDialog(null);
		} catch (ExpenseNoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void handleEditEntity() {
		EntityDTO dto = entityTable.getSelectionModel().getSelectedItem();
		if (dto != null) {
			try {
				showNewAndEditDialog(dto);
			} catch (ExpenseNoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(getMainApp().getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Entity Selected");
			alert.setContentText("Please select an entity in the table.");

			alert.showAndWait();
		}

	}

	public void handelDelete() throws ExpenseNoteException {

		EntityDTO dto = entityTable.getSelectionModel().getSelectedItem();

		if (dto == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(getMainApp().getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Entity Note Selected");
			alert.setContentText("Please select an entity in the table.");

			alert.showAndWait();
		} else {

        	Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);

            alert.initOwner(getDialogStage());
            alert.setTitle("Question???");
            alert.setHeaderText("Delete Entity");
            alert.setContentText("Do you really want to delete?");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
            	// ref au create /update
            	//utiliser le try & catch pour afficher le message d'erreur a l'ecran
            	EntityService.delete(dto);
            }

			loadData();
		}

	}


}

package be.elmoumene.expense.note.controller.category;

import java.io.IOException;

import be.elmoumene.expense.note.controller.JavaFXBaseController;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.CategoryDTO;
import be.elmoumene.expense.note.service.CategoryService;
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

public class CategoryOverviewController extends JavaFXBaseController<CategoryDTO> {
	
	@FXML
    private TableView<CategoryDTO> categoryTable;
	@FXML
	private TableColumn<CategoryDTO, Integer> id;
    @FXML
    private TableColumn<CategoryDTO, String> name;

    private CategoryService categoryService =  FactoryService.getCategoryService();
   
    private static CategoryOverviewController uniqueInstance;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public CategoryOverviewController() {
    	uniqueInstance = this;
    }

    public static CategoryOverviewController getInstance() {
        return uniqueInstance;
    }

    @Override
    public void initialize() {
    	name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    }

    @Override
	public void loadData() throws ExpenseNoteException {
		categoryTable.setItems(FXCollections.observableArrayList(categoryService.getCategories()));
	}

	public void showNewAndEditDialog(CategoryDTO category) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("CategoryNewDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        if(category == null){
	        	dialogStage.setTitle("add category");
	        }else{
	        	dialogStage.setTitle("edit category");
	        }
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(getDialogStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        CategoryNewController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setModel(category);
	        controller.setMainApp(getMainApp());
	        controller.loadData();

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	public void handleNewCategory(){
		showNewAndEditDialog(null);
	}

	public void handleEditCategory() {
		CategoryDTO dto = categoryTable.getSelectionModel().getSelectedItem();
		if (dto != null) {
			showNewAndEditDialog(dto);

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(getMainApp().getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Category Note Selected");
			alert.setContentText("Please select a category in the table.");

			alert.showAndWait();
		}

	}

	public void handelDelete() throws ExpenseNoteException {

		CategoryDTO dto = categoryTable.getSelectionModel().getSelectedItem();

		if (dto == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(getMainApp().getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Category Note Selected");
			alert.setContentText("Please select a category in the table.");
			
			alert.showAndWait();
		} else {

        	Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
        	
            alert.initOwner(getDialogStage());
            alert.setTitle("Question???");
            alert.setHeaderText("Delete Category");
            alert.setContentText("Do you really want to delete?");
            alert.showAndWait();
			
            if (alert.getResult() == ButtonType.YES) {
            	categoryService.delete(dto);
            }
			
			loadData();
		}

	}


}

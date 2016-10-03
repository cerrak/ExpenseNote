package be.elmoumene.expense.note.controller.country;

import java.io.IOException;

import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.CountryDTO;
import be.elmoumene.expense.note.service.CountryService;
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

public class CountryOverviewController extends JavaFXBaseController<CountryDTO> {
	
	@FXML
    private TableView<CountryDTO> countryTable;
	@FXML
	private TableColumn<CountryDTO, Integer> id;
    @FXML
    private TableColumn<CountryDTO, String> name;

    private CountryService countryService =  FactoryService.getCountryService();
   
    private static CountryOverviewController uniqueInstance;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public CountryOverviewController() {
    	uniqueInstance = this;
    }

    public static CountryOverviewController getInstance() {
        return uniqueInstance;
    }

    @Override
    public void initialize() {
    	name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
    }

    @Override
	public void loadData() throws ExpenseNoteException {
		countryTable.setItems(FXCollections.observableArrayList(countryService.getCounties()));
	}

	public void showNewAndEditDialog(CountryDTO country) {
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("CountryNewDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        if(country == null){
	        	dialogStage.setTitle("add country");
	        }else{
	        	dialogStage.setTitle("edit country");
	        }
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(getDialogStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        CountryNewController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setModel(country);
	        controller.setMainApp(getMainApp());
	        controller.loadData();

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	public void handleNewCountry(){
		showNewAndEditDialog(null);
	}

	public void handleEditCountry() {
		CountryDTO dto = countryTable.getSelectionModel().getSelectedItem();
		if (dto != null) {
			showNewAndEditDialog(dto);

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(getMainApp().getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Country Note Selected");
			alert.setContentText("Please select a country in the table.");

			alert.showAndWait();
		}

	}

	public void handelDelete() throws ExpenseNoteException {

		CountryDTO dto = countryTable.getSelectionModel().getSelectedItem();

		if (dto == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(getMainApp().getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Country Note Selected");
			alert.setContentText("Please select a country in the table.");
			
			alert.showAndWait();
		} else {

        	Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.CANCEL);
        	
            alert.initOwner(getDialogStage());
            alert.setTitle("Question???");
            alert.setHeaderText("Delete Country");
            alert.setContentText("Do you really want to delete?");
            alert.showAndWait();
			
            if (alert.getResult() == ButtonType.YES) {
            	countryService.delete(dto);
            }
			
			loadData();
		}

	}


}

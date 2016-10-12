package be.elmoumene.expense.note.view;

import java.io.IOException;

import be.elmoumene.expense.note.MainApp;
import be.elmoumene.expense.note.controller.category.CategoryOverviewController;
import be.elmoumene.expense.note.controller.country.CountryNewController;
import be.elmoumene.expense.note.controller.country.CountryOverviewController;
import be.elmoumene.expense.note.controller.department.DepartmentOverviewController;
import be.elmoumene.expense.note.controller.entity.EntityNewController;
import be.elmoumene.expense.note.controller.entity.EntityOverviewController;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 *
 * @author Mouaad El Moumène
 */
public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
//        mainApp.getPersonData().clear();
//        mainApp.setPersonFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {

    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {

    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {

    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("ExpenseNote");
        alert.setHeaderText("About");
        alert.setContentText("Author: Mouaad El Moumène");

        alert.showAndWait();
    }

    /**
     * Opens the birthday statistics.
     */
    @FXML
    private void handleShowBirthdayStatistics() {
//      mainApp.showBirthdayStatistics();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @FXML
    private void handleCountry() throws ExpenseNoteException {
    	showCountryOverview();
    }

    @FXML
    private void handleCategory() throws ExpenseNoteException {
    	showCategoryOverview();
    }

    @FXML
    private void handleDepartment() throws ExpenseNoteException {
    	showDepartmentOverview();
    }

    private void showDepartmentOverview() throws ExpenseNoteException {
    	try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(DepartmentOverviewController.class.getResource("DepartmentOverview.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        DepartmentOverviewController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setMainApp(mainApp);
	        controller.loadData();

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}

	@FXML
    private void handleEntity() throws ExpenseNoteException {
    	showEntityOverview();
    }

    private void showCategoryOverview() throws ExpenseNoteException{
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(CategoryOverviewController.class.getResource("CategoryOverview.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        CategoryOverviewController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setMainApp(mainApp);
	        controller.loadData();

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

    private void showCountryOverview() throws ExpenseNoteException{
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(CountryNewController.class.getResource("CountryOverview.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        CountryOverviewController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setMainApp(mainApp);
	        controller.loadData();

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

    private void showEntityOverview() throws ExpenseNoteException{
	    try {
	        // Load the fxml file and create a new stage for the popup dialog.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(EntityNewController.class.getResource("EntityOverview.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(mainApp.getPrimaryStage());
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        EntityOverviewController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setMainApp(mainApp);
	        controller.loadData();

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }



}
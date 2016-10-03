package be.elmoumene.expense.note;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.security.Permission;
import java.util.concurrent.FutureTask;

import be.elmoumene.expense.note.model.ExpenseNoteDTO;
import be.elmoumene.expense.note.model.PersonDTO;
import be.elmoumene.expense.note.view.ExpenseNoteDetailsController;
import be.elmoumene.expense.note.view.ExpenseNoteOverviewController;
import be.elmoumene.expense.note.view.ExpenseOverviewController;
import be.elmoumene.expense.note.view.LoginController;
import be.elmoumene.expense.note.view.PersonOverviewController;
import be.elmoumene.expense.note.view.RootLayoutController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private Pane loginLayout;
	private PersonDTO user;


	/**
     * Constructor
     */
    public MainApp() {

    }


	/**
	 * Returns the main stage.
	 *
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager(){
            @Override
            public void checkPermission(Permission perm) {}
        });
        Thread.setDefaultUncaughtExceptionHandler(ALERT_EXCEPTION_HANDLER);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Expense Note");

		//Set the application icon.
		this.primaryStage.getIcons().add(new Image("file:resources/images/addressBook.png"));
		initApplication();

	}

	 public static final UncaughtExceptionHandler ALERT_EXCEPTION_HANDLER = (thread, cause) -> {
	        try {
	            cause.printStackTrace();
	            final Runnable showDialog = () -> {
	               Alert alert = new Alert(AlertType.ERROR);
	               alert.setContentText("An unknown error occurred");
	               alert.showAndWait();
	            };
	            if (Platform.isFxApplicationThread()) {
	               showDialog.run();
	            } else {
	               FutureTask<Void> showDialogTask = new FutureTask<Void>(showDialog, null);
	               Platform.runLater(showDialogTask);
	               showDialogTask.get();
	            }
	        } catch (Throwable t) {
	            t.printStackTrace();
	        } finally {
	            //System.exit(-1);
	        }
	    };
	    
	private void initApplication() throws IOException {

        // Load root layout from fxml file.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class
                .getResource("view/Login.fxml"));
        loginLayout = (Pane) loader.load();

        // Show the scene containing the root layout.
        Scene scene = new Scene(loginLayout);
        primaryStage.setScene(scene);

        // Give the controller access to the main app.
        LoginController controller = loader.getController();
        controller.setMainApp(this);
        controller.setDialogStage(primaryStage);

        primaryStage.show();

}

	public void initAdminRootLayout() throws IOException {
		initRootLayout();
		showPersonOverview();

	}

	public void initUserLayout() throws IOException {
		initRootLayout();
		showExpenseOverview();

	}

	public void initSupervisorLayout() throws IOException {
		initRootLayout();
		showExpenseOverview();

	}

	public void initControllerLayout() throws IOException {
		initRootLayout();
		showExpenseOverview();

	}

	/**
	 * Initializes the root layout and tries to load the last opened
	 * person file.
	 * @throws IOException
	 */
	public void initRootLayout() throws IOException {

	        // Load root layout from fxml file.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class
	                .getResource("view/RootLayout.fxml"));
	        rootLayout = (BorderPane) loader.load();

	        // Show the scene containing the root layout.
	        Scene scene = new Scene(rootLayout);
	        primaryStage.setScene(scene);

	        // Give the controller access to the main app.
	        RootLayoutController controller = loader.getController();
	        controller.setMainApp(this);

	        primaryStage.show();

	}


	/**
	 * Shows the person overview inside the root layout.
	 */
	private void showPersonOverview() {
		 try {
		        // Load person overview.
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
		        AnchorPane personOverview = (AnchorPane) loader.load();

		        // Set person overview into the center of root layout.
		        rootLayout.setCenter(personOverview);

		        // Give the controller access to the main app.
		        PersonOverviewController controller = loader.getController();

		        controller.setMainApp(this);

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	}

	public void showExpenseOverview() {
		try {
	        // Load expense overview.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ExpenseOverView.fxml"));
	        AnchorPane expenseOverview = (AnchorPane) loader.load();

	        // Set person overview into the center of root layout.
	        rootLayout.setCenter(expenseOverview);

	        // Give the controller access to the main app.
	        ExpenseOverviewController controller = loader.getController();

	        controller.setMainApp(this);

	        controller.showExpense();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}

	public void showExpenseNoteOverview() {
		try {
	        // Load expense overview.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ExpenseNoteOverView.fxml"));
	        AnchorPane expenseNoteOverview = (AnchorPane) loader.load();

	        // Set person overview into the center of root layout.
	        rootLayout.setCenter(expenseNoteOverview);

	        // Give the controller access to the main app.
	        ExpenseNoteOverviewController controller = loader.getController();

	        controller.setMainApp(this);

	        controller.showExpenseNote();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}

	public void showFormViewExpenseNoteDetails(ExpenseNoteDTO dto){
		try {
	        // Load expense overview.
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ExpenseNoteDetails.fxml"));
	        AnchorPane expenseNoteDetails = (AnchorPane) loader.load();

	        // Set person overview into the center of root layout.
	        rootLayout.setCenter(expenseNoteDetails);

	        // Give the controller access to the main app.
	        ExpenseNoteDetailsController controller = loader.getController();
	        controller.setExpenseNote(dto);
	        controller.setMainApp(this);
	        controller.loadData();



	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public PersonDTO getUser() {
		return user;
	}


	public void setUser(PersonDTO user) {
		this.user = user;
	}



}
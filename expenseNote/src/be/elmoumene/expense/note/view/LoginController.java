package be.elmoumene.expense.note.view;

import java.io.IOException;

import be.elmoumene.expense.note.MainApp;
import be.elmoumene.expense.note.model.PersonDTO;
import be.elmoumene.expense.note.service.FactoryService;
import be.elmoumene.expense.note.service.PersonService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    private MainApp mainApp;
    private Stage dialogStage;

    // SERVICES

    private PersonService<PersonDTO> personService =  FactoryService.getPersonService();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void  handelLogin( ) throws IOException{
    	String email = emailField.getText();
    	String password = passwordField.getText();

    	PersonDTO userConnected = personService.getByLogin(email, password);

    	if(userConnected!=null && userConnected.getIsActive()==false){
    		Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Incorrect user");
            alert.setHeaderText("Your account is not valid");
            alert.setContentText("Please contact your administrator.");
            alert.showAndWait();

    	}


    	else if(userConnected!=null){
    		mainApp.setUser(userConnected);

    		switch(userConnected.getUserRole()){
    		case ADMIN : mainApp.initAdminRootLayout();
    			break;
    		case CONTROLLER: mainApp.initControllerLayout();;
    			break;
    		case SUPERVISOR: mainApp.initSupervisorLayout();
    			break;
    		case USER: mainApp.initUserLayout();
    			break;
    		}
    		System.out.println("Welcome " + userConnected.getUserRole());
    	}



    	else{
    		Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Bad credential");
            alert.setHeaderText("Your login or password is not correct");
            alert.setContentText("Please verify your login or password .");
            alert.showAndWait();
    		}

    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

	public void setDialogStage(Stage stage) {
		this.dialogStage = stage;

	}

}

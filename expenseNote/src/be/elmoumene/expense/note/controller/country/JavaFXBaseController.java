package be.elmoumene.expense.note.controller.country;

import be.elmoumene.expense.note.MainApp;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public abstract class JavaFXBaseController<T> {

    private MainApp mainApp;

    private Stage dialogStage;

    private T model;
    
    @FXML
    public abstract void initialize();
    
    public abstract void loadData() throws ExpenseNoteException;
    
	public MainApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public Stage getDialogStage() {
		return dialogStage;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}
    
}

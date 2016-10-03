package be.elmoumene.expense.note.controller.country;

import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.CountryDTO;
import be.elmoumene.expense.note.service.CountryService;
import be.elmoumene.expense.note.service.FactoryService;
import be.elmoumene.expense.note.view.FactoryController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class CountryNewController extends JavaFXBaseController<CountryDTO>{

    @FXML
    private TextField name;
    
    private CountryService countryService =  FactoryService.getCountryService();
    
    private static CountryNewController uniqueInstance;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public CountryNewController() {
    	uniqueInstance = this;
    }

    public static CountryNewController getInstance() {
        return uniqueInstance;
    }
    
    
	@Override
	public void initialize() {
	}

	@Override
	public void loadData() {
		if(getModel() != null){
			name.setText(getModel().getName());
		}
	}


	public void handleSave() throws ExpenseNoteException{
		CountryDTO dto = getModel();
		if(dto == null)
			dto = new CountryDTO();
		dto.setName(name.getText());
		
			try {
				if(dto.getId() == null){		
					countryService.create(dto);
				}else{
					countryService.update(dto);
				}
			} catch (ExpenseNoteException e) {
	               Alert alert = new Alert(AlertType.ERROR);
	               alert.setContentText(e.getMessage());
	               alert.showAndWait();
	               
			}
		FactoryController.getCountryOverviewController().loadData();
		getDialogStage().close();
	}
	
	public void handleCancel(){
		getDialogStage().close();
	}

}

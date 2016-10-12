package be.elmoumene.expense.note.controller.entity;

import be.elmoumene.expense.note.controller.FactoryController;
import be.elmoumene.expense.note.controller.JavaFXBaseController;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.CountryDTO;
import be.elmoumene.expense.note.model.EntityDTO;
import be.elmoumene.expense.note.service.CountryService;
import be.elmoumene.expense.note.service.EntityService;
import be.elmoumene.expense.note.service.FactoryService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EntityNewController extends JavaFXBaseController<EntityDTO>{

    @FXML
    private TextField name;
    @FXML
    private TextField city;
    @FXML
    private TextField locality;
    @FXML
    private ComboBox<CountryDTO> countryComboBox;

    private EntityService entityService =  FactoryService.getEntityService();
    private CountryService countryService = FactoryService.getCountryService();

    private static EntityNewController uniqueInstance;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public EntityNewController() {
    	uniqueInstance = this;
    }

    public static EntityNewController getInstance() {
        return uniqueInstance;
    }


	@FXML
	public void initialize() throws ExpenseNoteException {
		countryComboBox.getItems().addAll(countryService.getCountries());
    	countryComboBox.getSelectionModel().selectFirst();
	}

	@Override
	public void loadData() throws ExpenseNoteException {
		if(getModel() != null){
			name.setText(getModel().getName());
			city.setText(getModel().getCity());
			locality.setText(getModel().getLocality());
			countryComboBox.getItems().addAll(countryService.getCountries());
	    	countryComboBox.getSelectionModel().selectFirst();

		}
	}


	public void handleSave() throws ExpenseNoteException{
		EntityDTO dto = getModel();
		if(dto == null)
			dto = new EntityDTO();
		dto.setName(name.getText());
		dto.setCity(city.getText());
		dto.setLocality(locality.getText());
		dto.setCountry(countryComboBox.getSelectionModel().getSelectedItem());

			try {
				if(dto.getId() == null){
					entityService.create(dto);
				}else{
					entityService.update(dto);
				}
			} catch (ExpenseNoteException e) {
	               Alert alert = new Alert(AlertType.ERROR);
	               alert.setContentText(e.getMessage());
	               alert.showAndWait();

			}
		FactoryController.getEntityOverviewController().loadData();
		getDialogStage().close();
	}

	public void handleCancel(){
		getDialogStage().close();
	}

}

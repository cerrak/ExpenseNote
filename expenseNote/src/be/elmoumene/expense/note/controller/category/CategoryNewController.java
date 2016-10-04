package be.elmoumene.expense.note.controller.category;

import be.elmoumene.expense.note.controller.FactoryController;
import be.elmoumene.expense.note.controller.JavaFXBaseController;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.CategoryDTO;
import be.elmoumene.expense.note.service.CategoryService;
import be.elmoumene.expense.note.service.FactoryService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class CategoryNewController extends JavaFXBaseController<CategoryDTO>{

    @FXML
    private TextField name;
    
    private CategoryService categoryService =  FactoryService.getCategoryService();
    
    private static CategoryNewController uniqueInstance;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public CategoryNewController() {
    	uniqueInstance = this;
    }

    public static CategoryNewController getInstance() {
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
		CategoryDTO dto = getModel();
		if(dto == null)
			dto = new CategoryDTO();
		dto.setName(name.getText());
		
			try {
				if(dto.getId() == null){		
					categoryService.create(dto);
				}else{
					categoryService.update(dto);
				}
			} catch (ExpenseNoteException e) {
	               Alert alert = new Alert(AlertType.ERROR);
	               alert.setContentText(e.getMessage());
	               alert.showAndWait();
	               
			}
		FactoryController.getCategoryOverviewController().loadData();
		getDialogStage().close();
	}
	
	public void handleCancel(){
		getDialogStage().close();
	}

}

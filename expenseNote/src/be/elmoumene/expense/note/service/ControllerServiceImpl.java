package be.elmoumene.expense.note.service;

import java.util.ArrayList;
import java.util.List;

import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.ControllerDTO;
import be.elmoumene.expense.note.model.PersonDTO;


public class ControllerServiceImpl<T extends ControllerDTO> implements ControllerService<ControllerDTO> {

	private PersonService<PersonDTO> personService =  FactoryService.getPersonService();
	
	private static ControllerService<ControllerDTO> uniqueInstance = new ControllerServiceImpl<ControllerDTO>();

    public static ControllerService<ControllerDTO> getInstance() {
        return uniqueInstance;
    }

    
	@Override
	public ControllerDTO create(ControllerDTO s) throws ExpenseNoteException {
		
		ControllerDTO dto = new ControllerDTO(personService.create(s));
		
		
		return dto;
	}

	@Override
	public ControllerDTO update(ControllerDTO s) throws ExpenseNoteException {

		ControllerDTO dto = new ControllerDTO(personService.update(s));
		
		return dto;
	}

	@Override
	public void delete(ControllerDTO s) throws ExpenseNoteException {
		personService.delete(s);
	}

	@Override
	public ControllerDTO getById(Long id) {
		ControllerDTO dto = new ControllerDTO(personService.getById(id));
		
		return dto;
	}

	@Override
	public List<ControllerDTO> getList() {
		
		List<ControllerDTO> list = new ArrayList<ControllerDTO>();
		
		personService.getList().forEach(dto -> list.add(new ControllerDTO(dto)));;
		
		return list;
	}
	
}

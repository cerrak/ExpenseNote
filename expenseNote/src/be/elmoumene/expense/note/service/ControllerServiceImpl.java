package be.elmoumene.expense.note.service;

import java.util.ArrayList;
import java.util.List;

import be.elmoumene.expense.note.dao.FactoryDao;
import be.elmoumene.expense.note.dao.GenericDao;
import be.elmoumene.expense.note.entity.Controller;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.ControllerDTO;


public class ControllerServiceImpl implements ControllerService<ControllerDTO> {

	private GenericDao<Controller> controllerDao = FactoryDao.getControllerDao();
	
	private static ControllerService<ControllerDTO> uniqueInstance = new ControllerServiceImpl();

    public static ControllerService<ControllerDTO> getInstance() {
        return uniqueInstance;
    }

    
	@Override
	public ControllerDTO create(ControllerDTO s) throws Exception {
		
		ControllerDTO dto = controllerDao.create(s.toEntity()).toDto();
		
		return dto;
	}

	@Override
	public ControllerDTO update(ControllerDTO s) throws Exception {

		ControllerDTO dto = controllerDao.update(s.toEntity()).toDto();
		
		return dto;
	}

	@Override
	public void delete(ControllerDTO s) throws ExpenseNoteException {
		controllerDao.delete(s.toEntity());
	}

	@Override
	public ControllerDTO getById(Long id) {
		ControllerDTO dto = controllerDao.getById(id).toDto();
		
		return dto;
	}

	@Override
	public List<ControllerDTO> getList() {
		
		List<ControllerDTO> list = new ArrayList<ControllerDTO>();
		
		controllerDao.getList().forEach(entity -> list.add(entity.toDto()));;
		
		return list;
	}
	
}

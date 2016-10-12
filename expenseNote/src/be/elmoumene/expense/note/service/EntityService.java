package be.elmoumene.expense.note.service;

import java.util.List;

import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.EntityDTO;

public interface EntityService {

	public EntityDTO create(EntityDTO c) throws ExpenseNoteException;

	public EntityDTO update(EntityDTO c) throws ExpenseNoteException;

	public void delete(EntityDTO c) throws ExpenseNoteException;

	public EntityDTO getEntityById(Long id) throws ExpenseNoteException;

	public List<EntityDTO> getEntities() throws ExpenseNoteException;

}

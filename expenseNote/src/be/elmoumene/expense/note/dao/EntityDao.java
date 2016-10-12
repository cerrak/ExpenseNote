package be.elmoumene.expense.note.dao;

import java.util.List;

import be.elmoumene.expense.note.entity.Entity;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public interface EntityDao {

	public Entity create(Entity en) throws ExpenseNoteException;

	public Entity update(Entity en) throws ExpenseNoteException;

	public void delete(Entity en) throws ExpenseNoteException;

	public Entity getEntityById(Long id) throws ExpenseNoteException;

	public List<Entity> getCountries() throws ExpenseNoteException;

	public Entity getEntityByName(String name) throws ExpenseNoteException;

}

package be.elmoumene.expense.note.dao;

import java.util.List;

import be.elmoumene.expense.note.entity.Entity;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public interface EntityDao {

	public Entity create(Entity e) throws ExpenseNoteException;

	public Entity update(Entity e) throws ExpenseNoteException;

	public void delete(Entity e);

	public List<Entity> getEntities();

	public List<Entity> getEntities(Long entityId);

	public Entity getEntityById(Long id);

}

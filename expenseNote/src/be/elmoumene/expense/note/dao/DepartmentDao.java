package be.elmoumene.expense.note.dao;

import java.util.List;

import be.elmoumene.expense.note.entity.Department;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public interface DepartmentDao {

	public Department create(Department d) throws ExpenseNoteException;

	public Department update(Department d) throws ExpenseNoteException;

	public void delete(Department d);

	public List<Department> getDepartments();

	public List<Department> getDepartments(Long DepartmentId);

	public Department getDepartmentById(Long id);

}

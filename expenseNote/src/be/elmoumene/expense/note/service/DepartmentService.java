package be.elmoumene.expense.note.service;

import java.util.List;

import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.DepartmentDTO;


public interface DepartmentService {

	public DepartmentDTO create(DepartmentDTO c) throws ExpenseNoteException;

	public DepartmentDTO update(DepartmentDTO c) throws ExpenseNoteException;

	public void delete(DepartmentDTO c) throws ExpenseNoteException;

	public DepartmentDTO getDepartmentById(Long id) throws ExpenseNoteException;

	public List<DepartmentDTO> getDepartments() throws ExpenseNoteException;


}

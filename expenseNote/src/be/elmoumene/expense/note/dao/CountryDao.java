package be.elmoumene.expense.note.dao;

import java.util.List;

import be.elmoumene.expense.note.entity.Country;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public interface CountryDao {
	
	public Country create(Country c) throws ExpenseNoteException;

	public Country update(Country c) throws ExpenseNoteException;

	public void delete(Country c) throws ExpenseNoteException;

	public Country getCountryById(Long id) throws ExpenseNoteException;

	public List<Country> getCounties() throws ExpenseNoteException;

	public Country getCountryByName(String name) throws ExpenseNoteException;
	
}

package be.elmoumene.expense.note.service;

import java.util.List;

import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.CountryDTO;


public interface CountryService {

	public CountryDTO create(CountryDTO c) throws ExpenseNoteException;

	public CountryDTO update(CountryDTO c) throws ExpenseNoteException;

	public void delete(CountryDTO c) throws ExpenseNoteException;

	public CountryDTO getCountryById(Long id) throws ExpenseNoteException;

	public List<CountryDTO> getCounties() throws ExpenseNoteException;


}

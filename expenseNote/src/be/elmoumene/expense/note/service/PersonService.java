package be.elmoumene.expense.note.service;

import java.util.List;

import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.PersonDTO;


public interface PersonService {

	public PersonDTO create(PersonDTO p) throws ExpenseNoteException;

	public PersonDTO update(PersonDTO p) throws ExpenseNoteException;

	public void delete(PersonDTO p);

	public PersonDTO getPersonById(Long id);

	public PersonDTO getPersonByEmail(String email);

	public List<PersonDTO> getPersons();

	public PersonDTO getPerson(String email, String password);

}

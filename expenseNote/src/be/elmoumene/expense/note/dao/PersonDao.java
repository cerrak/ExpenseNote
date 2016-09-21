package be.elmoumene.expense.note.dao;

import java.util.List;

import be.elmoumene.expense.note.entity.Person;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public interface PersonDao {

	public Person create(Person p) throws ExpenseNoteException;

	public Person update(Person p)throws ExpenseNoteException;

	public void delete(Person p) throws ExpenseNoteException;

	public Person getPersonById(Long id);

	public Person getPersonByEmail(String email);

	public List<Person> getPersons();

}

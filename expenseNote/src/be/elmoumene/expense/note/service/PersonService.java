package be.elmoumene.expense.note.service;

import be.elmoumene.expense.note.dao.GenericDao;


public interface PersonService<PersonDTO> extends GenericDao<PersonDTO> {

	public PersonDTO getByEmail(String email);

	public PersonDTO getByLogin(String email, String password);

}

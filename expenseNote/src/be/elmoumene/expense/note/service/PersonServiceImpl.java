package be.elmoumene.expense.note.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import be.elmoumene.expense.note.dao.FactoryDao;
import be.elmoumene.expense.note.dao.PersonDao;
import be.elmoumene.expense.note.entity.Person;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.PersonDTO;


public class PersonServiceImpl  implements PersonService{

	private static PersonService uniqueInstance = new PersonServiceImpl();

	private PersonDao personDao = FactoryDao.getPersonDao();

    public static PersonService getInstance() {
        return uniqueInstance;
    }

	@Override
	public PersonDTO create(PersonDTO p) throws ExpenseNoteException {
		// Business logic

		p.setPasswordField(DigestUtils.sha1Hex(p.getPasswordField())); //encrypter

		Person entity = personDao.create(PersonDTO.personToEntity(p));

		return PersonDTO.personToModel(entity);
	}

	@Override
	public PersonDTO update(PersonDTO p) throws ExpenseNoteException{
		// Business Logic

		p.setPasswordField(DigestUtils.sha1Hex(p.getPasswordField())); //encrypter

		Person entity = personDao.update(PersonDTO.personToEntity(p));

		return PersonDTO.personToModel(entity);
	}

	@Override
	public void delete(PersonDTO p) throws ExpenseNoteException {
		personDao.delete(PersonDTO.personToEntity(p));
	}

	@Override
	public PersonDTO getPersonById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonDTO getPersonByEmail(String email) {
		Person entity = personDao.getPersonByEmail(email);

		return PersonDTO.personToModel(entity);
	}

	@Override
	public List<PersonDTO> getPersons() {

		List<PersonDTO> personsDto = new ArrayList<PersonDTO>();

		List<Person> entities = personDao.getPersons();

		entities.forEach( entity -> personsDto.add(PersonDTO.personToModel(entity)));

		return personsDto;
	}

	public PersonDTO getPerson(String email, String password) {
		// 1) utiliser l'email et password pour générer le code sha1 (String)

		String cryptedPassword = DigestUtils.sha1Hex(password);

		// 2) récupérer l'utilisateur en DB via l'email

		Person entity = personDao.getPersonByEmail(email);

		// 3) comparer le code sha1 généré avec le mdp de l'utilisateur récupéré en DB

		if (entity!=null && entity.getPassword().equals(cryptedPassword)){

				return PersonDTO.personToModel(entity);
		}

		return null;
	}

}

package be.elmoumene.expense.note.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import be.elmoumene.expense.note.dao.FactoryDao;
import be.elmoumene.expense.note.dao.PersonDao;
import be.elmoumene.expense.note.entity.Person;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.PersonDTO;


public class PersonServiceImpl<T extends PersonDTO>  implements PersonService<PersonDTO>{

	private static PersonService<PersonDTO> uniqueInstance = new PersonServiceImpl<PersonDTO>();

	private PersonDao<Person> personDao = FactoryDao.getPersonDao();

    public static PersonService<PersonDTO> getInstance() {
        return uniqueInstance;
    }

    @Override
	public PersonDTO create(PersonDTO p) throws ExpenseNoteException {
		// Business logic

		p.setPasswordField(DigestUtils.sha1Hex(p.getPasswordField())); //encrypter

		Person entity = personDao.create(PersonDTO.toEntity(p));

		return PersonDTO.toDto(entity);
	}

	@Override
	public PersonDTO update(PersonDTO p) throws ExpenseNoteException{
		// Business Logic

		p.setPasswordField(DigestUtils.sha1Hex(p.getPasswordField())); //encrypter

		Person entity = personDao.update(PersonDTO.toEntity(p));

		return PersonDTO.toDto(entity);
	}

	@Override
	public void delete(PersonDTO p) throws ExpenseNoteException {
		personDao.delete(PersonDTO.toEntity(p));
	}

	@Override
	public PersonDTO getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonDTO getByEmail(String email) {
		Person entity = personDao.getByEmail(email);

		return PersonDTO.toDto(entity);
	}

	@Override
	public List<PersonDTO> getList() {

		List<PersonDTO> personsDto = new ArrayList<PersonDTO>();

		List<Person> entities = personDao.getList();

		entities.forEach( entity -> personsDto.add(PersonDTO.toDto(entity)));

		return personsDto;
	}

	@Override
	public PersonDTO getByLogin(String email, String password) {
		// 1) utiliser l'email et password pour g�n�rer le code sha1 (String)

		String cryptedPassword = DigestUtils.sha1Hex(password);

		// 2) r�cup�rer l'utilisateur en DB via l'email

		Person entity = personDao.getByEmail(email);

		// 3) comparer le code sha1 g�n�r� avec le mdp de l'utilisateur r�cup�r� en DB

		if (entity!=null && entity.getPassword().equals(cryptedPassword)){

				return PersonDTO.toDto(entity);
		}

		return null;
	}

}

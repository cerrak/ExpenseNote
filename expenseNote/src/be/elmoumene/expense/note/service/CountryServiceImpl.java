package be.elmoumene.expense.note.service;

import java.util.ArrayList;
import java.util.List;

import be.elmoumene.expense.note.dao.CountryDao;
import be.elmoumene.expense.note.dao.FactoryDao;
import be.elmoumene.expense.note.entity.Country;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.CountryDTO;

public class CountryServiceImpl implements CountryService {

	
	private static CountryService uniqueInstance = new CountryServiceImpl();

	private CountryDao countryDao = FactoryDao.getCountryDao();

	public static CountryService getInstance() {
		return uniqueInstance;
	}
	
	@Override
	public CountryDTO create(CountryDTO dto) throws ExpenseNoteException {
		Country countryFound = countryDao.getCountryByName(dto.getName());
		
		if(countryFound!= null){
			throw new ExpenseNoteException("this Country already existe!!!");
		}else{
			Country entity = countryDao.create(CountryDTO.toEntity(dto));
			return CountryDTO.toDto(entity);
		}
	}

	@Override
	public CountryDTO update(CountryDTO dto) throws ExpenseNoteException {
		// in first check if the name is already used on database
		Country countryFound = countryDao.getCountryByName(dto.getName());
		
		if(countryFound != null && countryFound.getId() != dto.getId()){
			throw new ExpenseNoteException("this Country already existe!!!");
		}else{
			Country entity = countryDao.update(CountryDTO.toEntity(dto));
			return CountryDTO.toDto(entity);
		}
	}

	@Override
	public void delete(CountryDTO dto) throws ExpenseNoteException {
		countryDao.delete(CountryDTO.toEntity(dto));
	}

	@Override
	public CountryDTO getCountryById(Long id) throws ExpenseNoteException {
		Country entity = countryDao.getCountryById(id);
		return CountryDTO.toDto(entity);
	}

	@Override
	public List<CountryDTO> getCountries() throws ExpenseNoteException {

		List<CountryDTO> countriesDto = new ArrayList<CountryDTO>();

		List<Country> entities = countryDao.getCounties();

		if(entities!=null)
			entities.forEach(entity -> countriesDto.add(CountryDTO.toDto(entity)));
		
		return countriesDto;

	}


}

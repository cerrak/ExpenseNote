package be.elmoumene.expense.note.service;

import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import be.elmoumene.expense.note.dao.EntityDao;
import be.elmoumene.expense.note.dao.FactoryDao;
import be.elmoumene.expense.note.entity.Entity;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.EntityDTO;

public class EntityServiceImpl implements EntityService {

	private static EntityService uniqueInstance = new EntityServiceImpl();

	private EntityDao entityDao = FactoryDao.getEntityDao();

	public static EntityService getInstance() {
		return uniqueInstance;
	}

	@Override
	public EntityDTO create(EntityDTO dto) throws ExpenseNoteException {
		Entity entityFound = entityDao.getEntityByName(dto.getName());

		if(entityFound!= null){
			throw new ExpenseNoteException("this Entity already existe!!!");
		}else{
			Entity entity = entityDao.create(EntityDTO.toEntity(dto));
			return EntityDTO.toDto(entity);
		}
	}

	@Override
	public EntityDTO update(EntityDTO dto) throws ExpenseNoteException {
		// in first check if the name is already used on database
		Entity entityFound = entityDao.getEntityByName(dto.getName());

		if(entityFound != null && entityFound.getId() != dto.getId()){
			throw new ExpenseNoteException("this Entity already existe!!!");
		}else{
			Entity entity = entityDao.update(EntityDTO.toEntity(dto));
			return EntityDTO.toDto(entity);
		}
	}

	@Override
	public void delete(EntityDTO dto) throws ExpenseNoteException {

		try {
			entityDao.delete(EntityDTO.toEntity(dto));
		} catch (Throwable e) {
			if(e instanceof MySQLIntegrityConstraintViolationException)
				throw new ExpenseNoteException("");
		}
	}

	@Override
	public EntityDTO getEntityById(Long id) throws ExpenseNoteException {
		Entity entity = entityDao.getEntityById(id);
		return EntityDTO.toDto(entity);
	}

	@Override
	public List<EntityDTO> getEntities() throws ExpenseNoteException {

		List<EntityDTO> entitiesDto = new ArrayList<EntityDTO>();

		List<Entity> entities = entityDao.getCountries();

		if(entities!=null)
			entities.forEach(entity -> entitiesDto.add(EntityDTO.toDto(entity)));

		return entitiesDto;

	}

}

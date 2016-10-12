package be.elmoumene.expense.note.service;

import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import be.elmoumene.expense.note.dao.DepartmentDao;
import be.elmoumene.expense.note.dao.FactoryDao;
import be.elmoumene.expense.note.entity.Department;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.DepartmentDTO;

public class DepartmentServiceImpl implements DepartmentService {


	private static DepartmentService uniqueInstance = new DepartmentServiceImpl();

	private DepartmentDao departmentDao = FactoryDao.getDepartmentDao();

	public static DepartmentService getInstance() {
		return uniqueInstance;
	}

	@Override
	public DepartmentDTO create(DepartmentDTO dto) throws ExpenseNoteException {
		Department departmentFound = departmentDao.getDepartmentByName(dto.getName());

		if(departmentFound!= null){
			throw new ExpenseNoteException("this Department already existe!!!");
		}else{
			Department entity = departmentDao.create(DepartmentDTO.toEntity(dto));
			return DepartmentDTO.toDto(entity);
		}
	}

	@Override
	public DepartmentDTO update(DepartmentDTO dto) throws ExpenseNoteException {
		// in first check if the name is already used on database
		Department departmentFound = departmentDao.getDepartmentByName(dto.getName());

		if(departmentFound != null && departmentFound.getId() != dto.getId()){
			throw new ExpenseNoteException("this Department already existe!!!");
		}else{
			Department entity = departmentDao.update(DepartmentDTO.toEntity(dto));
			return DepartmentDTO.toDto(entity);
		}
	}

	@Override
	public void delete(DepartmentDTO dto) throws ExpenseNoteException {

		try {
			departmentDao.delete(DepartmentDTO.toEntity(dto));
		} catch (Throwable e) {
			if(e instanceof MySQLIntegrityConstraintViolationException)
				throw new ExpenseNoteException("");
		}
	}

	@Override
	public DepartmentDTO getDepartmentById(Long id) throws ExpenseNoteException {
		Department entity = departmentDao.getDepartmentById(id);
		return DepartmentDTO.toDto(entity);
	}

	@Override
	public List<DepartmentDTO> getDepartments() throws ExpenseNoteException {
		List<DepartmentDTO> departmentsDto = new ArrayList<DepartmentDTO>();

		List<Department> entities = departmentDao.getDepartments();

		if(entities!=null)
			entities.forEach(department -> departmentsDto.add(DepartmentDTO.toDto(department)));

		return departmentsDto;


	}


}

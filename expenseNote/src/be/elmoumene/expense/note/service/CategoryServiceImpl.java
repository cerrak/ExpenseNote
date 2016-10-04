package be.elmoumene.expense.note.service;

import java.util.ArrayList;
import java.util.List;

import be.elmoumene.expense.note.dao.CategoryDao;
import be.elmoumene.expense.note.dao.FactoryDao;
import be.elmoumene.expense.note.entity.Category;
import be.elmoumene.expense.note.exception.ExpenseNoteException;
import be.elmoumene.expense.note.model.CategoryDTO;

public class CategoryServiceImpl implements CategoryService {

	
	private static CategoryService uniqueInstance = new CategoryServiceImpl();

	private CategoryDao categoryDao = FactoryDao.getCategoryDao();

	public static CategoryService getInstance() {
		return uniqueInstance;
	}
	
	@Override
	public CategoryDTO create(CategoryDTO dto) throws ExpenseNoteException {
		Category categoryFound = categoryDao.getCategoryByName(dto.getName());
		
		if(categoryFound!= null){
			throw new ExpenseNoteException("this Category already existe!!!");
		}else{
			Category entity = categoryDao.create(CategoryDTO.toEntity(dto));
			return CategoryDTO.toDto(entity);
		}
	}

	@Override
	public CategoryDTO update(CategoryDTO dto) throws ExpenseNoteException {
		// in first check if the name is already used on database
		Category categoryFound = categoryDao.getCategoryByName(dto.getName());
		
		if(categoryFound != null && categoryFound.getId() != dto.getId()){
			throw new ExpenseNoteException("this Category already existe!!!");
		}else{
			Category entity = categoryDao.update(CategoryDTO.toEntity(dto));
			return CategoryDTO.toDto(entity);
		}
	}

	@Override
	public void delete(CategoryDTO dto) throws ExpenseNoteException {
		categoryDao.delete(CategoryDTO.toEntity(dto));
	}

	@Override
	public CategoryDTO getCategoryById(Long id) throws ExpenseNoteException {
		Category entity = categoryDao.getCategoryById(id);
		return CategoryDTO.toDto(entity);
	}

	@Override
	public List<CategoryDTO> getCategories() throws ExpenseNoteException {

		List<CategoryDTO> countriesDto = new ArrayList<CategoryDTO>();

		List<Category> entities = categoryDao.getCounties();

		if(entities!=null)
			entities.forEach(entity -> countriesDto.add(CategoryDTO.toDto(entity)));
		
		return countriesDto;

	}


}

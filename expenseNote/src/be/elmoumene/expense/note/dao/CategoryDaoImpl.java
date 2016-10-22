package be.elmoumene.expense.note.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import be.elmoumene.expense.note.database.ConnectionHSQL;
import be.elmoumene.expense.note.entity.Category;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public class CategoryDaoImpl implements CategoryDao {

	private final static String TABLE_NAME = "category";

	private final static String ID = "id";
	private final static String NAME = "name";

	private static Map<String, Integer> columns;
	private static Map<String, Integer> columnsWithId;

    private static CategoryDaoImpl uniqueInstance = new CategoryDaoImpl();
    private Connection con = ConnectionHSQL.getInstance().getConn();
    private PreparedStatement stm;

    public static CategoryDaoImpl getInstance() {
        return uniqueInstance;
    }

    public CategoryDaoImpl(){
    	columns = new LinkedHashMap<String, Integer>();
    	columns.put(NAME, columns.size()+1);

    	columnsWithId = new LinkedHashMap<String, Integer>(columns);
    	columnsWithId.put(ID, columns.size()+1);
    }

	private Category mapResult(ResultSet res) throws SQLException{
		Category p = new Category();
		p.setId(res.getLong(columnsWithId.get(ID)));
		p.setName(res.getString(columnsWithId.get(NAME)));
		return p;
	}

	@Override
	public Category create(Category c) throws ExpenseNoteException {
		String sqlColumns = columns.keySet().stream().collect(Collectors.joining(", "));
		String sqlValues = columns.keySet().stream().map(string -> "?").collect(Collectors.joining(", "));

		// INSERT
		StringBuilder sql = new StringBuilder();
		sql.append("Insert into "+TABLE_NAME+" ");
		sql.append("("+sqlColumns+") ");
		sql.append("values ("+sqlValues+") ");

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(columns.get(NAME), c.getName());

            stm.executeUpdate();
            ResultSet res = stm.getGeneratedKeys();

            res.next();
           return getCategoryById(res.getLong(1));

        } catch (Exception e) {
        	throw new ExpenseNoteException("Category creation in error", e);
        }
	}

	@Override
	public Category update(Category c) throws ExpenseNoteException {
		String sqlColumnsAndValues = columns.keySet().stream().map(string -> string + " = ?").collect(Collectors.joining(", "));

		StringBuilder sql = new StringBuilder();
		sql.append("Update "+TABLE_NAME+" ");
		sql.append("set " + sqlColumnsAndValues + " ");
		sql.append("where id = ?");

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(columns.get(NAME), c.getName());

            stm.setLong(columnsWithId.get(ID), c.getId());

            stm.executeUpdate();
           return getCategoryById(c.getId());

        } catch (Exception e) {
        	throw new ExpenseNoteException("Category update in error", e);
        }
	}

	@Override
	public void delete(Category c) throws ExpenseNoteException {
		StringBuilder sql = new StringBuilder();
		sql.append("delete ");
		sql.append("from "+TABLE_NAME+" ");
		sql.append("where id = ?");

        try {
            stm = con.prepareStatement(sql.toString());
            stm.setLong(1, c.getId());
            stm.executeUpdate();

        } catch (Exception e) {
        	throw new ExpenseNoteException("Category delete in error", e);
        }

	}

	@Override
	public Category getCategoryById(Long id) throws ExpenseNoteException {
		if(id==null)
			return null;

		String sqlColumns = columnsWithId.keySet().stream().collect(Collectors.joining(", "));
		StringBuilder sql = new StringBuilder();
		sql.append("select " + sqlColumns + " ");
		sql.append("from "+TABLE_NAME+" ");
		sql.append("where id = ?");

		try {
            stm = con.prepareStatement(sql.toString());
            stm.setLong(1, id);

            ResultSet res = stm.executeQuery();
            res.next();
            if(res.getRow() > 0){
            	return mapResult(res);
            }
            return null;

        } catch (SQLException e) {
            throw new ExpenseNoteException(e);
        }
	}

	@Override
	public Category getCategoryByName(String name) throws ExpenseNoteException {

		String sqlColumns = columnsWithId.keySet().stream().collect(Collectors.joining(", "));
		StringBuilder sql = new StringBuilder();
		sql.append("select " + sqlColumns + " ");
		sql.append("from "+TABLE_NAME+" ");
		sql.append("where name = ?");

		try {
            stm = con.prepareStatement(sql.toString());
            stm.setString(1, name.trim());

            ResultSet res = stm.executeQuery();
            res.next();
            if(res.getRow() > 0){
            	return mapResult(res);
            }
            return null;

        } catch (SQLException e) {
            throw new ExpenseNoteException(e);
        }
	}

	@Override
	public List<Category> getCounties() throws ExpenseNoteException {
		List<Category> list = new ArrayList<Category>();
		String sqlColumns = columnsWithId.keySet().stream().collect(Collectors.joining(", "));
		StringBuilder sql = new StringBuilder();
		sql.append("select " + sqlColumns + " ");
		sql.append("from "+TABLE_NAME+" ");

		try {
            stm = con.prepareStatement(sql.toString());

            ResultSet res = stm.executeQuery();

            while( res.next() ){
            	list.add(mapResult(res));
            }

            return list;
        } catch (SQLException e) {
           throw new ExpenseNoteException(e);
        }
	}



}

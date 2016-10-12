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
import be.elmoumene.expense.note.entity.Department;
import be.elmoumene.expense.note.entity.Entity;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public class DepartmentDaoImpl implements DepartmentDao {


private final static String TABLE_NAME = "department";

	private final static String ID = "id";
	private final static String NAME = "name";
	private final static String ENTITY_ID = "entity_id";

	private static Map<String, Integer> columns;
	private static Map<String, Integer> columnsWithId;

	private EntityDao entityDao = FactoryDao.getEntityDao();

	private static DepartmentDaoImpl uniqueInstance = new DepartmentDaoImpl();
	private Connection con = ConnectionHSQL.getInstance().getConn();
    private PreparedStatement stm;

    public static DepartmentDaoImpl getInstance() {
        return uniqueInstance;
    }

    public DepartmentDaoImpl(){
    	columns = new LinkedHashMap<String, Integer>();
    	columns.put(NAME, columns.size()+1);
    	columns.put(ENTITY_ID, columns.size()+1);

    	columnsWithId = new LinkedHashMap<String, Integer>(columns);
    	columnsWithId.put(ID, columns.size()+1);

    }

	@Override
	public Department create(Department d) throws ExpenseNoteException {

		// INSERT
		String sqlColumns = columns.keySet().stream().collect(Collectors.joining(", "));
		String sqlValues = columns.keySet().stream().map(string -> "?").collect(Collectors.joining(", "));

		// INSERT
		StringBuilder sql = new StringBuilder();
		sql.append("Insert into "+TABLE_NAME+" ");
		sql.append("("+sqlColumns+") ");
		sql.append("values ("+sqlValues+") ");

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(columns.get(NAME), d.getName());
            stm.setLong(columns.get(ENTITY_ID), d.getEntity().getId());

            stm.executeUpdate();
            ResultSet res = stm.getGeneratedKeys();

            res.next();
           return getDepartmentById(res.getLong(1));

        } catch (Exception e) {
        	throw new ExpenseNoteException("Department creation in error", e);
        }
	}

	@Override
	public List<Department> getDepartments(Long DepartmentId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Department update(Department d) throws ExpenseNoteException {
		// UPDATE
		String sqlColumnsAndValues = columns.keySet().stream().map(string -> string + " = ?").collect(Collectors.joining(", "));

		StringBuilder sql = new StringBuilder();
		sql.append("Update "+TABLE_NAME+" ");
		sql.append("set " + sqlColumnsAndValues + " ");
		sql.append("where id = ?");

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(columns.get(NAME), d.getName());
            stm.setLong(columns.get(ENTITY_ID), d.getEntity().getId());

            stm.setLong(columnsWithId.get(ID), d.getId());

            stm.executeUpdate();
           return getDepartmentById(d.getId());

        } catch (Exception e) {
        	throw new ExpenseNoteException("Department update in error", e);
        }
	}

	@Override
	public void delete(Department d) throws ExpenseNoteException {
		StringBuilder sql = new StringBuilder();
		sql.append("delete ");
		sql.append("from "+TABLE_NAME+" ");
		sql.append("where id = ?");

        try {
            stm = con.prepareStatement(sql.toString());
            stm.setLong(1, d.getId());
            stm.executeUpdate();

        } catch (Exception e) {
        	throw new ExpenseNoteException("Department delete in error", e);
        }

	}

	@Override
	public Department getDepartmentById(Long id) throws ExpenseNoteException {
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
	public List<Department> getDepartments() throws ExpenseNoteException {
		List<Department> list = new ArrayList<Department>();
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

	private Department mapResult(ResultSet res) throws SQLException, ExpenseNoteException{

		Department d = new Department();
		d.setId(res.getLong(columnsWithId.get(ID)));
		d.setName(res.getString(columnsWithId.get(NAME)));
		Entity entity = entityDao.getEntityById(res.getLong(columnsWithId.get(ENTITY_ID)));
		d.setEntity(entity);

		return d;

//		Department d = new Department();
//
//		try{
//			d.setName(res.getString(1));
//			Entity entity = entityDao.getEntityById(res.getLong(2));
//			d.setEntity(entity);
//
//			return d;
//		}
//		catch (Exception ex){
//			throw new SQLException(ex);
//		}


	}

	@Override
	public Department getDepartmentByName(String name) throws ExpenseNoteException {

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
}

package be.elmoumene.expense.note.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import be.elmoumene.expense.note.database.ConnectionHSQL;
import be.elmoumene.expense.note.entity.Controller;
import be.elmoumene.expense.note.entity.Entity;
import be.elmoumene.expense.note.entity.Person;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public class SupervisorDaoImpl implements GenericDao<Controller>{

	private final static String TABLE_NAME = "controller";
	private final static String PERSON = "person_id";
	private final static String ENTITY = "entity_id";

	private static Map<String, Integer> columns;
	private static Map<String, Integer> columnsWithId;

	private Connection con = ConnectionHSQL.getInstance().getConn();
	private PreparedStatement stm;

	private EntityDao entityDao = FactoryDao.getEntityDao();
	private PersonDao<Person> personDao = FactoryDao.getPersonDao();

	private static GenericDao<Controller> uniqueInstance = new SupervisorDaoImpl();


    public static GenericDao<Controller> getInstance() {
        return uniqueInstance;
    }

    public SupervisorDaoImpl(){
    	columns = new LinkedHashMap<String, Integer>();
    	columns.put(ENTITY, columns.size()+1);

    	columnsWithId = new LinkedHashMap<String, Integer>(columns);
    	columnsWithId.put(PERSON, columns.size()+1);
    }

	@Override
	public Controller create(Controller c) throws Exception  {
		Long controllerId = null;
		Exception error = null;

		try {
			con.setAutoCommit(false);

			controllerId =  personDao.create(c).getId();

			String sqlColumns = columnsWithId.keySet().stream().collect(Collectors.joining(", "));
			String sqlValues = columnsWithId.keySet().stream().map(string -> "?").collect(Collectors.joining(", "));

			// INSERT
			StringBuilder sql = new StringBuilder();
			sql.append("Insert into "+TABLE_NAME+" ");
			sql.append("("+sqlColumns+") ");
			sql.append("values ("+sqlValues+") ");

	        stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
	        stm.setLong(columnsWithId.get(ENTITY), c.getEntity().getId());
	        stm.setLong(columnsWithId.get(PERSON), controllerId);

	        stm.executeUpdate();

			con.commit();

		} catch (Exception ex) {
			error = ex;
			try {
				con.rollback();
			} catch (Exception e) {
				error = e;
			}
		}

		con.setAutoCommit(true);

		if (error == null){
			return getById(controllerId);
		}else{
			throw new ExpenseNoteException("Controller creation in error", error);
		}

	}

	@Override
	public Controller update(Controller c) throws Exception {
		Exception error = null;

		try {

			con.setAutoCommit(false);

			personDao.update(c).getId();

			String sqlColumnsAndValues = columns.keySet().stream().map(string -> string + " = ?").collect(Collectors.joining(", "));

			StringBuilder sql = new StringBuilder();
			sql.append("Update "+TABLE_NAME+" ");
			sql.append("set " + sqlColumnsAndValues + " ");
			sql.append("where "+PERSON+ " = ?");

            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setLong(columns.get(ENTITY), c.getEntity().getId());
            stm.setLong(columnsWithId.get(PERSON), c.getId());

            stm.executeUpdate();

			con.commit();

		} catch (Exception ex) {
			error = ex;
			try {
				con.rollback();
			} catch (Exception e) {
				error = e;
			}
		}

		con.setAutoCommit(true);

		if (error == null){
			return getById(c.getId());
		}else{
			throw new ExpenseNoteException("Controller creation in error", error);
		}

	}

	private Controller mapResult(ResultSet res) throws SQLException, ExpenseNoteException{

		Controller c = new Controller(personDao.getById(res.getLong(columnsWithId.get(PERSON))));

		Entity e = entityDao.getEntityById(res.getLong(columnsWithId.get(ENTITY)));
		c.setEntity(e);

		return c;
	}

	@Override
	public void delete(Controller c) throws ExpenseNoteException {
		StringBuilder sql = new StringBuilder();
		sql.append("Update "+TABLE_NAME+" ");
		sql.append("set deleted = ? ");
		sql.append("where id = "+ c.getId());

        try {
            stm = con.prepareStatement(sql.toString());
            stm.setBoolean(1, true);
            stm.executeUpdate();

        } catch (Exception e) {
        	throw new ExpenseNoteException("Controller delete in error", e);
        }

	}

	@Override
	public Controller getById(Long id) {
		if(id==null)
			return null;

		Controller c = new Controller(personDao.getById(id));

		String sqlColumns = columns.keySet().stream().collect(Collectors.joining(", "));
		StringBuilder sql = new StringBuilder();
		sql.append("select " + sqlColumns + " ");
		sql.append("from "+TABLE_NAME+" ");
		sql.append("where "+PERSON+" = ?");

		try {
            stm = con.prepareStatement(sql.toString());
            stm.setLong(1, id);

            ResultSet res = stm.executeQuery();
            res.next();

            Long entityID = res.getLong(columns.get(ENTITY));

            c.setEntity(entityDao.getEntityById(entityID));

            return c;

        } catch (SQLException | ExpenseNoteException e) {
            System.out.println(e.toString());
        }
        return null;
	}

	@Override
	public List<Controller> getList() {
		// TODO Auto-generated method stub
		return null;
	}


}

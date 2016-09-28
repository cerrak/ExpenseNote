package be.elmoumene.expense.note.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import be.elmoumene.expense.note.database.ConnectionHSQL;
import be.elmoumene.expense.note.entity.Department;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public class DepartmentDaoImpl implements DepartmentDao {


	private final static String COLUMNS = "name ";

	private final static String COLUMNS_WITH_ID = COLUMNS + ", id";

	private EntityDao EntityDao = FactoryDao.getEntityDao();


	private static DepartmentDaoImpl uniqueInstance = new DepartmentDaoImpl();
    private Connection con = ConnectionHSQL.getInstance().getConn();
    private PreparedStatement stm;

    public static DepartmentDaoImpl getInstance() {
        return uniqueInstance;
    }

	@Override
	public Department create(Department d) throws ExpenseNoteException {

		// INSERT
		StringBuilder sql = new StringBuilder();
		sql.append("Insert into department ");
		sql.append("("+COLUMNS+") ");
		sql.append("values (?) ");

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, d.getName());

            stm.executeUpdate();

            ResultSet res = stm.getGeneratedKeys();

           // SELECT CREATED ENTITY
            res.next();
           return getDepartmentById(res.getLong(1));

        } catch (Exception ex) {
        	throw new ExpenseNoteException("Department creation in error", ex);
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
		StringBuilder sql = new StringBuilder();
		sql.append("Update Department ");
		sql.append("set name=? ");
		sql.append("where id = "+ d.getId());

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, d.getName());

            stm.executeUpdate();
           return getDepartmentById(d.getId());

        } catch (Exception ex) {
        	throw new ExpenseNoteException("Entity update in error", ex);
        }
	}

	@Override
	public void delete(Department e) {
		// TODO Auto-generated method stub

	}

	@Override
	public Department getDepartmentById(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from department ");
		sql.append("where id = ?");

		try {
            stm = con.prepareStatement(sql.toString());
            stm.setLong(1, id);

            ResultSet res = stm.executeQuery();
            res.next();
            return mapResult(res);

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;

	}


	@Override
	public List<Department> getDepartments() {
		List<Department> list = new ArrayList<Department>();

		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from department ");

		try {
            stm = con.prepareStatement(sql.toString());

            ResultSet res = stm.executeQuery();

            while(res.next() ){
            	list.add(mapResult(res));
            }

            return list;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
	}

	private Department mapResult(ResultSet res) throws SQLException{
		Department d = new Department();

		d.setName(res.getString(1));

		return d;
	}

}

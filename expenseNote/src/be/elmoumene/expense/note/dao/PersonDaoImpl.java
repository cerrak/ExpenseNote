package be.elmoumene.expense.note.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import be.elmoumene.expense.note.database.ConnectionHSQL;
import be.elmoumene.expense.note.entity.Person;
import be.elmoumene.expense.note.entity.UserRole;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public class PersonDaoImpl implements PersonDao {

	private final static String COLUMNS = "firstname, lastname, street, postalcode, city, "
											+ "birthday, entity, mobile, email, department, "
											+ "supervisor, controller, title, isactive, userrole, "
											+ "passwordfield ";

	private final static String COLUMNS_WITH_ID = COLUMNS + ", id";

    private static PersonDaoImpl uniqueInstance = new PersonDaoImpl();
    private Connection con = ConnectionHSQL.getInstance().getConn();
    private PreparedStatement stm;

    public static PersonDaoImpl getInstance() {
        return uniqueInstance;
    }

	@Override
	public Person create(Person p) throws ExpenseNoteException {

		// INSERT
		StringBuilder sql = new StringBuilder();
		sql.append("Insert into person ");
		sql.append("("+COLUMNS+") ");
		sql.append("values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, p.getFirstName());
            stm.setString(2, p.getLastName());
            stm.setString(3, p.getStreet());
            stm.setInt(4, p.getPostalCode());
            stm.setString(5, p.getCity());
            stm.setTimestamp(6, new Timestamp(p.getBirthday().getTimeInMillis()));
            stm.setString(7, p.getEntity());
            stm.setString(8, p.getMobile());
            stm.setString(9, p.getEmail());
            stm.setString(10, p.getDepartment());
            stm.setString(11, p.getSupervisor());
            stm.setString(12, p.getController());
            stm.setString(13, p.getTitle());
            stm.setBoolean(14, p.getIsActive());
            stm.setString(15, p.getUserRole().toString());
            stm.setString(16,  p.getPassword().toString());

            stm.executeUpdate();
            ResultSet res = stm.getGeneratedKeys();

           // SELECT CREATED PERSON
            res.next();
           return getPersonById(res.getLong(1));

        } catch (Exception e) {
        	throw new ExpenseNoteException("User creation in error", e);
        }
	}

	@Override
	public Person update(Person p) throws ExpenseNoteException {
		// UPDATE
				StringBuilder sql = new StringBuilder();
				sql.append("Update person ");
				//sql.append("("+COLUMNS+") ");
				sql.append("set firstName = ?, lastName = ?, street = ?, postalcode = ?, city = ?, birthday = ?, entity = ?, "
						+ "mobile = ?, email = ?, department = ?, supervisor = ?, controller = ?, title = ?, "
						+ "isactive = ?, userrole = ? ");
				sql.append("where id = "+ p.getId());

		        try {
		            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
		            stm.setString(1, p.getFirstName());
		            stm.setString(2, p.getLastName());
		            stm.setString(3, p.getStreet());
		            stm.setInt(4, p.getPostalCode());
		            stm.setString(5, p.getCity());
		            stm.setTimestamp(6, new Timestamp(p.getBirthday().getTimeInMillis()));
		            stm.setString(7, p.getEntity());
		            stm.setString(8, p.getMobile());
		            stm.setString(9, p.getEmail());
		            stm.setString(10, p.getDepartment());
		            stm.setString(11, p.getSupervisor());
		            stm.setString(12, p.getController());
		            stm.setString(13, p.getTitle());
		            //stm.setString(16,  p.getPassword().toString());
		            stm.setBoolean(14, p.getIsActive());
		            stm.setString(15, p.getUserRole().toString());



		            stm.executeUpdate();
		           return getPersonById(p.getId());

		        } catch (Exception e) {
		        	throw new ExpenseNoteException("User update in error", e);
		        }

	}

	@Override
	public void delete(Person p) throws ExpenseNoteException {
		StringBuilder sql = new StringBuilder();
		sql.append("Update person ");
		sql.append("set deleted = ? ");
		sql.append("where id = "+ p.getId());

        try {
            stm = con.prepareStatement(sql.toString());
            stm.setBoolean(1, true);
            stm.executeUpdate();

        } catch (Exception e) {
        	throw new ExpenseNoteException("User delete in error", e);
        }

	}

	@Override
	public Person getPersonById(Long id) {
		if(id==null)
			return null;

		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from person ");
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


	private Person mapResult(ResultSet res) throws SQLException{
		Person p = new Person();
		p.setId(res.getLong(17));
		p.setFirstName(res.getString(1));
		p.setLastName(res.getString(2));
		p.setStreet(res.getString(3));
		p.setPostalCode(res.getInt(4));
		p.setCity(res.getString(5));

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(res.getTimestamp(6).getTime());

		p.setBirthday(cal);
		p.setEntity(res.getString(7));
		p.setMobile(res.getString(8));
		p.setEmail(res.getString(9));
		p.setDepartment(res.getString(10));
		p.setSupervisor(res.getString(11));
		p.setController(res.getString(12));
		p.setTitle(res.getString(13));
		p.setIsActive(res.getBoolean(14));
		p.setUserRole(UserRole.fromString(res.getString(15)));
		p.setPassword(res.getString(16));

		return p;
	}

	@Override
	public Person getPersonByEmail(String email) {
		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from person ");
		sql.append("where email = ?");

		try {
            stm = con.prepareStatement(sql.toString());
            stm.setString(1, email);

            ResultSet res = stm.executeQuery();
            res.next();
            return mapResult(res);

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
		return null;
	}

	@Override
	public List<Person> getPersons() {
		List<Person> list = new ArrayList<Person>();

		StringBuilder sql = new StringBuilder();
		sql.append("select " + COLUMNS_WITH_ID + " ");
		sql.append("from person ");
		sql.append("where deleted is false ");

		try {
            stm = con.prepareStatement(sql.toString());

            ResultSet res = stm.executeQuery();

            while( res.next() ){
            	list.add(mapResult(res));
            }

            return list;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
	}

}

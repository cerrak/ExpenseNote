package be.elmoumene.expense.note.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import be.elmoumene.expense.note.database.ConnectionHSQL;
import be.elmoumene.expense.note.entity.Person;
import be.elmoumene.expense.note.entity.UserRole;
import be.elmoumene.expense.note.exception.ExpenseNoteException;

public class PersonDaoImpl implements PersonDao {

	private final static String ID = "id";
	private final static String FIRSTNAME = "firstname"; 
	private final static String LASTNAME = "lastname"; 
	private final static String STREET = "street"; 
	private final static String POSTAL_CODE = "postalcode"; 
	private final static String CITY = "city"; 
	private final static String BIRTHDAY = "birthday"; 
	private final static String MOBILE = "mobile"; 
	private final static String EMAIL = "email"; 
	private final static String TITLE = "title"; 
	private final static String IS_ACTIVE = "isactive"; 
	private final static String USER_ROLE = "userrole"; 
	private final static String PASSWORD_FIELD = "passwordfield"; 

	private static Map<String, Integer> columns;
	private static Map<String, Integer> columnsWithId;
	
    private static PersonDaoImpl uniqueInstance = new PersonDaoImpl();
    private Connection con = ConnectionHSQL.getInstance().getConn();
    private PreparedStatement stm;

    public static PersonDaoImpl getInstance() {
        return uniqueInstance;
    }

    public PersonDaoImpl(){
    	columns = new LinkedHashMap<String, Integer>();
    	columns.put(FIRSTNAME, columns.size()+1);
    	columns.put(LASTNAME, columns.size()+1);
    	columns.put(STREET, columns.size()+1);
    	columns.put(POSTAL_CODE, columns.size()+1);
    	columns.put(CITY, columns.size()+1);
    	columns.put(BIRTHDAY, columns.size()+1);
    	columns.put(MOBILE, columns.size()+1);
    	columns.put(TITLE, columns.size()+1);
    	columns.put(IS_ACTIVE, columns.size()+1);
    	columns.put(USER_ROLE, columns.size()+1);
    	columns.put(PASSWORD_FIELD, columns.size()+1);
    	columns.put(EMAIL, columns.size()+1);
    	
    	columnsWithId = new LinkedHashMap<String, Integer>(columns);
    	columnsWithId.put(ID, columns.size()+1);

    	
    }
    
	@Override
	public Person create(Person p) throws ExpenseNoteException {
		String sqlColumns = columns.keySet().stream().collect(Collectors.joining(", "));
		String sqlValues = columns.keySet().stream().map(string -> "?").collect(Collectors.joining(", "));	
		
		// INSERT
		StringBuilder sql = new StringBuilder();
		sql.append("Insert into person ");
		sql.append("("+sqlColumns+") ");
		sql.append("values ("+sqlValues+") ");

        try {
            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            stm.setString(columns.get(FIRSTNAME), p.getFirstName());
            stm.setString(columns.get(LASTNAME), p.getLastName());
            stm.setString(columns.get(STREET), p.getStreet());
            stm.setInt(columns.get(POSTAL_CODE), p.getPostalCode());
            stm.setString(columns.get(CITY), p.getCity());
            stm.setTimestamp(columns.get(BIRTHDAY), new Timestamp(p.getBirthday().getTimeInMillis()));
            stm.setString(columns.get(MOBILE), p.getMobile());
            stm.setString(columns.get(EMAIL), p.getEmail());
            stm.setString(columns.get(TITLE), p.getTitle());
            stm.setBoolean(columns.get(IS_ACTIVE), p.getIsActive());
            stm.setString(columns.get(USER_ROLE), p.getUserRole().toString());
            stm.setString(columns.get(PASSWORD_FIELD),  p.getPassword().toString());

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
		String sqlColumnsAndValues = columns.keySet().stream().map(string -> string + " = ?").collect(Collectors.joining(", "));	
		
				StringBuilder sql = new StringBuilder();
				sql.append("Update person ");
				sql.append("set " + sqlColumnsAndValues + " ");
				sql.append("where id = ?");

		        try {
		            stm = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
		            stm.setString(columns.get(FIRSTNAME), p.getFirstName());
		            stm.setString(columns.get(LASTNAME), p.getLastName());
		            stm.setString(columns.get(STREET), p.getStreet());
		            stm.setInt(columns.get(POSTAL_CODE), p.getPostalCode());
		            stm.setString(columns.get(CITY), p.getCity());
		            stm.setTimestamp(columns.get(BIRTHDAY), new Timestamp(p.getBirthday().getTimeInMillis()));
		            stm.setString(columns.get(MOBILE), p.getMobile());
		            stm.setString(columns.get(EMAIL), p.getEmail());
		            stm.setString(columns.get(TITLE), p.getTitle());
		            stm.setBoolean(columns.get(IS_ACTIVE), p.getIsActive());
		            stm.setString(columns.get(USER_ROLE), p.getUserRole().toString());
		            stm.setString(columns.get(PASSWORD_FIELD),  p.getPassword().toString());
		            
		            stm.setLong(columnsWithId.get(ID), p.getId());
		            
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
		
		String sqlColumns = columnsWithId.keySet().stream().collect(Collectors.joining(", "));
		StringBuilder sql = new StringBuilder();
		sql.append("select " + sqlColumns + " ");
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
		p.setId(res.getLong(columnsWithId.get(ID)));
		p.setFirstName(res.getString(columnsWithId.get(FIRSTNAME)));
		p.setLastName(res.getString(columnsWithId.get(LASTNAME)));
		p.setStreet(res.getString(columnsWithId.get(STREET)));
		p.setPostalCode(res.getInt(columnsWithId.get(POSTAL_CODE)));
		p.setCity(res.getString(columnsWithId.get(CITY)));

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(res.getTimestamp(columnsWithId.get(BIRTHDAY)).getTime());

		p.setBirthday(cal);
		p.setMobile(res.getString(columnsWithId.get(MOBILE)));
		p.setEmail(res.getString(columnsWithId.get(EMAIL)));
		p.setTitle(res.getString(columnsWithId.get(TITLE)));
		p.setIsActive(res.getBoolean(columnsWithId.get(IS_ACTIVE)));
		p.setUserRole(UserRole.fromString(res.getString(columnsWithId.get(USER_ROLE))));
		p.setPassword(res.getString(columnsWithId.get(PASSWORD_FIELD)));

		return p;
	}

	@Override
	public Person getPersonByEmail(String email) {
		String sqlColumns = columnsWithId.keySet().stream().collect(Collectors.joining(", "));
		StringBuilder sql = new StringBuilder();
		sql.append("select " + sqlColumns + " ");
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
		String sqlColumns = columnsWithId.keySet().stream().collect(Collectors.joining(", "));
		StringBuilder sql = new StringBuilder();
		sql.append("select " + sqlColumns + " ");
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

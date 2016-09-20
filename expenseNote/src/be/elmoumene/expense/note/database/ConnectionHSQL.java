package be.elmoumene.expense.note.database;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;

import be.elmoumene.expense.note.util.PropertyUtils;

public class ConnectionHSQL implements be.elmoumene.expense.note.dao.Connection {

	private Connection conn;

	public Connection getConn() {
		return conn;
	}

	private PreparedStatement stat;

	private boolean connected;

	private static ConnectionHSQL uniqueInstance = new ConnectionHSQL();

	/*
	 * Constructeur : ouvre la connexion
	 */
	public void op() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException classe) {
			System.out.println(classe.toString());
		}
		connected = false;

		try {
			
			String url = PropertyUtils.get("database.url");
			
			java.util.Properties prop = new java.util.Properties();
			prop.put("charSet", "UTF-8");
			prop.put("user", PropertyUtils.get("database.user"));
			prop.put("password", PropertyUtils.get("database.password"));

			conn = DriverManager.getConnection(url, prop);
			conn.setAutoCommit(true);
			//String req = "SET WRITE_DELAY 500 MILLIS";
			//stat = conn.prepareStatement(req);
			//stat.executeUpdate();// faire r√©guli√®rement des sauvegardes)

			connected = true;
		} catch (Exception e) {
			System.out.println("No connection");
			e.printStackTrace();
		}
	}

	public ConnectionHSQL() {
		op();
	}

	/*
	 * ferme la connexion.
	 */
	@Override
	public void close() {
		try {
			stat = conn.prepareStatement("SHUTDOWN");
			stat.executeUpdate();
			conn.close();
			connected = false;

		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}

	/*
	 * Pattern Singleton
	 */
	public static ConnectionHSQL getInstance() {
		return uniqueInstance;
	}

	/*
	 * Cette fct retourne l'Ètat de l'objet: connectÈ connected
	 */
	@Override
	public boolean isConnected() {
		return connected;
	}

}
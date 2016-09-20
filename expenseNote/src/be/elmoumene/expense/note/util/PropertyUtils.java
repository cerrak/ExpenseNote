package be.elmoumene.expense.note.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import be.elmoumene.expense.note.exception.ExpenseNoteException;

public class PropertyUtils {

	private static final String PROPERTIES_FILE = "./application.properties";
	
	private static Properties prop = null;
	
	private static void loadProperties() throws ExpenseNoteException {
		InputStream input = null;
		
		try {
			input = PropertyUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);
			
			StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
			encryptor.setPassword("ex-no");
			
			prop = new EncryptableProperties(encryptor);
			// load a properties file
			prop.load(input);

		} catch (IOException ex) {
			throw new ExpenseNoteException("failed to open properties file" ,ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					throw new ExpenseNoteException("failed to close properties file" ,e);
				}
			}
		}
	}

	public static String get(String key) throws ExpenseNoteException{
		if(prop == null)
			loadProperties();
		
		return prop.getProperty(key);
	}

}

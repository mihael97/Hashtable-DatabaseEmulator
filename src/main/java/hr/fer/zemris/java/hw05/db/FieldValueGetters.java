package hr.fer.zemris.java.hw05.db;

/**
 * Razred koji ima metode za vraćanje vrijednosti pojedninih atributa studenta
 * 
 * @author Mihael
 *
 */
public class FieldValueGetters {

	/**
	 * Metoda koja vraća ime studenta
	 */
	public static IFieldValueGetter FIRST_NAME = (record) -> record.getFirstName();
	/**
	 * Metoda vraća prezime studenta
	 */
	public static IFieldValueGetter LAST_NAME = (record) -> record.getLastName();
	/**
	 * Metoda vraća JMBAG studenta
	 */
	public static IFieldValueGetter JMBAG = (record) -> record.getJmbag();
}

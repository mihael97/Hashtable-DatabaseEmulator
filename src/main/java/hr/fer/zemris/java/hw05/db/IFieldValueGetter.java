package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje koja propisuje metodu za dohvat određenog atributa
 * 
 * @author Mihael
 *
 */
public interface IFieldValueGetter {
	/**
	 * Vraća vrijednost određenog atrubuta
	 * 
	 * @param record
	 *            - zapis o studentu
	 * @return vrijednost koju tražimo u obliku Stringa
	 */
	public String get(StudentRecord record);
}

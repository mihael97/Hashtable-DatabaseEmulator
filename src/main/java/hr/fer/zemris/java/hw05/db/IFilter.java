package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje koje propisuje metodu za odlučivanje da li zapis zadovoljava sve
 * uvjete
 * 
 * @author Mihael
 *
 */
public interface IFilter {
	/**
	 * Metoda vraća vrijednost ovisno da li zapis zadovoljava sve uvjete
	 * 
	 * @param record
	 *            - zapis studenta
	 * @return true ako zapis zadovoljava sve uvjete inače null
	 */
	public boolean accepts(StudentRecord record);
}

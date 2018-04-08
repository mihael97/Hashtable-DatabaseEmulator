package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje koje propisuje metodu za usporedbu dva izraza
 * 
 * @author Mihael
 *
 */
public interface IComparisonOperator {
	/**
	 * Metoda koaj provjerava zadovoljavaju li argumenti određenu relaciju
	 * 
	 * @param value1
	 *            - prva vrijednost
	 * @param value2
	 *            - druga vrijednost
	 * @return true ako vrijednosti zadovoljavaju relaciju inače false
	 */
	public boolean satisfied(String value1, String value2);
}

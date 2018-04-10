package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Razred koji implementira bazu podataka o studentima
 * 
 * @author Mihael
 *
 */
public class StudentDatabase {

	/**
	 * Lista svih studenata opisanih s {@link StudentRecord}
	 */
	private List<StudentRecord> list;

	/**
	 * Mapa koja služi za brzo pretraživanje studenata po JMBAGu
	 */
	private SimpleHashtable<String, StudentRecord> index;

	/**
	 * Konstruktor koji prima Listu Stringova gdje svaki String predstavlja
	 * informacije o jednom studentu
	 * 
	 * @param list
	 *            - lista Stringova sa informacijama o studentima
	 * 
	 * @throws NullPointerException
	 *             - ako je lista null
	 */
	public StudentDatabase(List<String> list) {
		super();
		if (list != null) {
			add(list);
		} else {
			throw new NullPointerException("Predana lista je null!");
		}
	}

	/**
	 * Metoda koja stvara listu {@link StudentRecord}-a i mapu za brzo pretraživanje
	 * putem JMBAG-a
	 * 
	 * @param studentList
	 *            - lista Stringova koji sadrže informacije o studentima
	 * @throws IllegalArgumentException
	 *             - ako je broj argumenata različit o 7
	 */
	private void add(List<String> studentList) {
		index = new SimpleHashtable<>(2);
		list = new LinkedList<>();

		int indexInt = 1;
		for (String string : studentList) {
			//String[] array = string.split("\\s+");
			String[] array=string.split("\t");

			if (array.length == 1)
				continue;

			if (array.length != 4) {
				throw new IllegalArgumentException(
						"Broj argumenata u \'" + string + "\' na poziciji " + indexInt + " je " + array.length);
			}

			StudentRecord pom = new StudentRecord(array[0], array[1], array[2], array[3]);
			index.put(array[0], pom);
			list.add(pom);
			indexInt++;
		}
	}

	/**
	 * Metoda vraća {@link StudentRecord} ovisno o studentovom JMBAGu
	 * 
	 * @param jmbag
	 *            - JMBAG studenta čiji {@link StudentRecord} želimo
	 * @return null ako student ne postoji inače {@link StudentRecord}
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if (index.containsKey(jmbag)) {
			return index.get(jmbag);
		}

		return null;
	}

	/**
	 * Metoda koja za svakog studenta iz kolekcija provjerava zadovoljava li
	 * zahtjeve dane putem argumenta
	 * 
	 * @param filter
	 *            - {@link IFilter}
	 * @return Lista {@link StudentRecord} koji zadovoljavaju uvjete
	 */
	public List<StudentRecord> filter(IFilter filter) {
		LinkedList<StudentRecord> forReturn = new LinkedList<>();
		for (StudentRecord student : list) {
			if (filter.accepts(student)) {
				forReturn.add(student);
			}
		}

		return forReturn;
	}
}

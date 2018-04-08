package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Razred koji implementira korisnika koji komunicira s bazom podataka studenta
 * 
 * @author Mihael
 *
 */
public class StudentDB {

	/**
	 * Baza studenata
	 */
	private static StudentDatabase database;

	/**
	 * Glavni program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		database = new StudentDatabase(readFiles());

		try (Scanner sc = new Scanner(System.in)) {
			String line = new String();
			List<StudentRecord> forPrint = new ArrayList<>();

			while (true) {
				System.out.println(">");
				if ((line = sc.nextLine()).trim().toUpperCase().equals("EXIT"))
					break;

				if (line.substring(0, 5).equals("query")) {
					forPrint = analize(line.substring(4));
				} else {
					throw new IllegalArgumentException(
							"Naredba mora započeti s \'query\',ali je zadana naredba \'" + line + "\"!");
				}

				for (StudentRecord student : forPrint) {
					System.out.println(student);
				}

				System.out.println("Ispisano " + forPrint.size() + " zapisa!");
			}

			System.out.println("Doviđenja!");
		}
	}

	/**
	 * Metoda koja prima upit i prosljeđuje ga parseru koji joj vraća studente koji
	 * zadovoljavaju uvjete upite
	 * 
	 * @param query
	 *            - upit
	 * @return Lista studenata koji zadovoljavaju uvjete
	 */
	public static List<StudentRecord> analize(String query) {

		ArrayList<StudentRecord> forReturn = new ArrayList<>();

		QueryParser parser = new QueryParser(query);
		if (parser.isDirectQuery()) {
			forReturn.add(database.forJMBAG(parser.getQueriedJMBAG()));
		} else {
			forReturn.addAll(database.filter(new QueryFilter(parser.getQuery())));
		}

		return forReturn;
	}

	/**
	 * Metoda koja čita iz tekstualne datoteke sve zapise o studentima. Svaki red
	 * predstavlja zapis o jednom studentu
	 * 
	 * @return {@link List} informacija o studentima
	 */
	private static List<String> readFiles() {
		try {
			List<String> lines = Files.readAllLines(Paths.get("src/main/resources/Studenti.txt"),
					StandardCharsets.UTF_8);

			return lines;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Metoda koja postavlja vrijednost studentska baze
	 * 
	 * @param database
	 *            - baza studenata
	 */
	public static void setDatabase(StudentDatabase database) {
		StudentDB.database = database;
	}
}

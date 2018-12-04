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
					forPrint = analize(line.substring(5));
				} else {
					throw new IllegalArgumentException(
							"Naredba mora započeti s \'query\',ali je zadana naredba \'" + line + "\"!");
				}

				System.out.printf(makeTable(forPrint));

				System.out.println("Ispisano :" + forPrint.size());
			}

			System.out.println("Doviđenja!");
		}
	}

	/**
	 * Metoda koja generira grafičku tablicu sa zapisima studenta
	 * 
	 * @param forPrint
	 *            - lista studenata koji se moraju ispisati
	 * @return String za ispis
	 */
	private static String makeTable(List<StudentRecord> forPrint) {
		int[] array = { 0, 0, 0, 0 };

		if (forPrint.size() == 0) {
			return "";
		}

		for (StudentRecord student : forPrint) {
			if (student.getJmbag().length() > array[0])
				array[0] = student.getJmbag().length();
			if (student.getLastName().length() > array[1])
				array[1] = student.getLastName().length();
			if (student.getFirstName().length() > array[2])
				array[2] = student.getFirstName().length();
			if (student.getFinalGrade().length() > array[3])
				array[3] = student.getFinalGrade().length();
		}

		StringBuilder builder = new StringBuilder().append("+");
		for (int j = 0; j < 4; j++) {
			for (int i = 0, length = array[j] + 2; i < length; i++) {
				builder.append("=");
			}
			builder.append("+");
		}

		builder.append("\n");

		StringBuilder string = new StringBuilder();
		string.append(builder.toString());

		for (StudentRecord student : forPrint) {
			
			string.append("|");

			string.append(" ");
			string.append(student.getJmbag());
			
			for(int i=0;i<(array[0]+1-student.getJmbag().length());i++) string.append(" ");
			string.append("|");
			
			string.append(" ");
			string.append(student.getLastName());
			
			for(int i=0;i<(array[1]+1-student.getLastName().length());i++) string.append(" ");
			string.append("|");
			
			string.append(" ");
			string.append(student.getFirstName());
			
			for(int i=0;i<(array[2]+1-student.getFirstName().length());i++) string.append(" ");
			string.append("|");
			
			string.append(" ");
			string.append(student.getFinalGrade());
			
			for(int i=0;i<(array[3]+1-student.getFinalGrade().length());i++) string.append(" ");
			
			string.append("|\n");
		}

		string.append(builder.toString()).append("\n");

		return string.toString();
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
			System.out.println("Koristeći brzo pretraživanje po indexu!");
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
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Metoda koja postavlja vrijednost studentske baze
	 * 
	 * @param database
	 *            - baza studenata
	 */
	public static void setDatabase(StudentDatabase database) {
		StudentDB.database = database;
	}
}

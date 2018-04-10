package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.hw05.db.StudentDB;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

@SuppressWarnings("javadoc")
public class StudentDBTest {
	List<String> list = null;

	@Before
	public void before() {
		StudentDB.setDatabase(new StudentDatabase(readFromFile()));
	}

	@Test
	public void searchingByJMBAG() {
		List<StudentRecord> list = StudentDB.analize("jmbag=\"0000000021\"");

		assertEquals("Antonija", list.get(0).getFirstName());
		assertEquals("Jakobušić", list.get(0).getLastName());
	}

	@Test
	public void searchingByLastName() {
		List<StudentRecord> list = StudentDB.analize("lastName=\"Jurina\"");

		assertEquals("0000000022", list.get(0).getJmbag());
		assertEquals("Filip", list.get(0).getFirstName());
	}

	@Test
	public void searchingByFirstName() {
		List<StudentRecord> list = StudentDB.analize("firstName=\"Sanjin\"");

		assertEquals("0000000007", list.get(0).getJmbag());
		assertEquals("Čima", list.get(0).getLastName());
	}

	@Test
	public void searchingByJMBAGAndLastName() {
		List<StudentRecord> list = StudentDB.analize("jmbag>\"0000000003\" and jmbag<\"0000000006\" and ");
		assertEquals(2, list.size());

		assertEquals("0000000004", list.get(0).getJmbag());
		assertEquals("Božić", list.get(0).getLastName());

		assertEquals("0000000005", list.get(1).getJmbag());
		assertEquals("Brezović", list.get(1).getLastName());
	}

	private List<String> readFromFile() {
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
}

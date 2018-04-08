package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

@SuppressWarnings("javadoc")
public class StudentDatabaseTest {

	@Test
	public void existingJMBG() {
		StudentDatabase db = new StudentDatabase(readFromFile());

		StudentRecord record = db.forJMBAG("0000000019");

		assertEquals("0000000019", record.getJmbag());
		assertEquals("Prezime019", record.getLastName());
		assertEquals("Ime019", record.getFirstName());
		assertEquals("1", record.getFinalGrade());

		record = db.forJMBAG("0000000434");
		assertEquals("Prezime434", record.getLastName());
		assertEquals("Ime434", record.getFirstName());
		assertEquals("3", record.getFinalGrade());
	}
	
	@Test
	public void nonExistingJMBG() {
		StudentDatabase db = new StudentDatabase(readFromFile());

		StudentRecord record = db.forJMBAG("0000000510");
		assertNull(record);
	}
	
	@Test 
	public void trueTest() {
		StudentDatabase db=new StudentDatabase(readFromFile());
		
		assertEquals(500, db.filter((record)->true).size());
	}
	
	@Test
	public void falseTest() {
		StudentDatabase db=new StudentDatabase(readFromFile());
		
		assertEquals(0, db.filter((record)->false).size());
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

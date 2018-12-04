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

		StudentRecord record = db.forJMBAG("0000000063");

		assertEquals("0000000063", record.getJmbag());
		assertEquals("Žabčić", record.getLastName());
		assertEquals("Željko", record.getFirstName());
		assertEquals("4", record.getFinalGrade());

		record = db.forJMBAG("0000000044");
		assertEquals("Pilat", record.getLastName());
		assertEquals("Ivan", record.getFirstName());
		assertEquals("5", record.getFinalGrade());
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
		
		assertEquals(63, db.filter((record)->true).size());
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
			e.printStackTrace();
		}

		return null;
	}
}

package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

@SuppressWarnings("javadoc")
public class FieldValueGettersTest {
	
	@Test
	public void example1() {
		StudentDatabase db=new StudentDatabase(readFromFile());
		
		StudentRecord record=db.forJMBAG("0000000013");
		
		assertEquals("Gagić", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("Mateja", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("0000000013", FieldValueGetters.JMBAG.get(record));
	}

	@Test
	public void example2() {
		StudentDatabase db=new StudentDatabase(readFromFile());
		
		StudentRecord record=db.forJMBAG("0000000028");
		
		assertEquals("Kosanović", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("Nenad", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("0000000028", FieldValueGetters.JMBAG.get(record));
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

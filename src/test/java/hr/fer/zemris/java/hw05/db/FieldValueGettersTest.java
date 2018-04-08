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
		
		StudentRecord record=db.forJMBAG("0000000481");
		
		assertEquals("Prezime481", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("Ime481", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("0000000481", FieldValueGetters.JMBAG.get(record));
	}
	
	@Test
	public void example2() {
		StudentDatabase db=new StudentDatabase(readFromFile());
		
		StudentRecord record=db.forJMBAG("0000000369");
		
		assertEquals("Prezime369", FieldValueGetters.LAST_NAME.get(record));
		assertEquals("Ime369", FieldValueGetters.FIRST_NAME.get(record));
		assertEquals("0000000369", FieldValueGetters.JMBAG.get(record));
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

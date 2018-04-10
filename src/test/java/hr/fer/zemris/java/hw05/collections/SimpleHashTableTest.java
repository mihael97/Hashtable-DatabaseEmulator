package hr.fer.zemris.java.hw05.collections;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Test;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

@SuppressWarnings("javadoc")
public class SimpleHashTableTest {

	@Test
	public void putItems() {
		SimpleHashtable<String, String> hashtable = new SimpleHashtable<>();

		hashtable.put("Prvi", "Drugi");
		hashtable.put("Treci", "Cetvrti");
		hashtable.put("Peti", "Sesti");
		hashtable.put("Sedmi", "Osmi");

		assertEquals(4, hashtable.size());
	}

	@Test(expected = NullPointerException.class)
	public void putNull() {
		SimpleHashtable<String, String> hashtable = new SimpleHashtable<>();

		hashtable.put(null, "Drugi");
	}

	@Test
	public void putNullValue() {
		SimpleHashtable<String, String> hashtable = new SimpleHashtable<>();

		hashtable.put("Prvi", "Drugi");
		hashtable.put("Treci", null);
		hashtable.put("Peti", "Sesti");
		hashtable.put("Sedmi", "Osmi");

		assertEquals(4, hashtable.size());
	}

	@Test
	public void getTest() {
		SimpleHashtable<String, String> hashtable = new SimpleHashtable<>();

		hashtable.put("Prvi", "Drugi");
		hashtable.put("Treci", "Cetvrti");
		hashtable.put("Peti", "Sesti");
		hashtable.put("Sedmi", "Osmi");

		assertEquals("Drugi", hashtable.get("Prvi"));
		assertNull(hashtable.get("Deveti"));
	}

	@Test
	public void containsKey() {
		SimpleHashtable<String, String> hashtable = new SimpleHashtable<>();

		hashtable.put("Prvi", "Drugi");
		hashtable.put("Treci", "Cetvrti");
		hashtable.put("Peti", "Sesti");
		hashtable.put("Sedmi", "Osmi");

		assertEquals(false, hashtable.containsKey("Deveti"));
		assertEquals(true, hashtable.containsKey("Prvi"));
	}

	@Test
	public void containsValueTest() {
		SimpleHashtable<String, String> hashtable = new SimpleHashtable<>();

		hashtable.put("Prvi", "Drugi");
		hashtable.put("Treci", "Cetvrti");
		hashtable.put("Peti", "Sesti");
		hashtable.put("Sedmi", "Osmi");

		assertEquals(true, hashtable.containsValue("Cetvrti"));
		assertEquals(false, hashtable.containsValue("Peti"));
	}

	@Test
	public void containsNullTest() {
		SimpleHashtable<String, String> hashtable = new SimpleHashtable<>();

		hashtable.put("Prvi", "Drugi");
		hashtable.put("Treci", null);
		hashtable.put("Peti", "Sesti");
		hashtable.put("Sedmi", "Osmi");

		assertEquals(true, hashtable.containsValue(null));
	}

	@Test
	public void remove() {
		SimpleHashtable<String, String> hashtable = new SimpleHashtable<>();

		hashtable.put("Prvi", "Drugi");
		hashtable.put("Treci", "Cetvrti");
		hashtable.put("Peti", "Sesti");
		hashtable.put("Sedmi", "Osmi");

		assertEquals(4, hashtable.size());
		hashtable.remove("Treci");
		assertEquals(3, hashtable.size());
		assertEquals(false, hashtable.containsKey("Treci"));
	}

	@Test
	public void emptyTest() {
		SimpleHashtable<String, String> hashtable = new SimpleHashtable<>();

		hashtable.put("Prvi", "Drugi");
		hashtable.put("Treci", "Cetvrti");
		hashtable.put("Peti", "Sesti");
		hashtable.put("Sedmi", "Osmi");

		hashtable.clear();
		assertEquals(true, hashtable.isEmpty());
		assertEquals(0, hashtable.size());
	}

	@Test
	public void toStringTest() {
		SimpleHashtable<String, String> hashtable = new SimpleHashtable<>();

		hashtable.put("Prvi", "Drugi");
		hashtable.put("Treci", "Cetvrti");

		String str = "[Prvi=Drugi, Treci=Cetvrti]";

		assertEquals(str, hashtable.toString());
	}

	@Test
	public void firstIteratorTest() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(3);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Iterator<SimpleHashtable.TableEntry<String, Integer>> it = examMarks.iterator();

		while (it.hasNext()) {
			if (it.next().getKey().equals("Kristina")) {
				it.remove();
			}
		}

		assertEquals(3, examMarks.size());
		assertEquals(false, examMarks.containsKey("Kristina"));
	}

	@Test(expected = ConcurrentModificationException.class)
	public void secondIteratorTest() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(3);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);

		Iterator<SimpleHashtable.TableEntry<String, Integer>> it = examMarks.iterator();

		while (it.hasNext()) {
			if (it.next().getKey().equals("Kristina")) {
				examMarks.remove("Kristina");
				it.remove();
			}
		}

		assertEquals(3, examMarks.size());
		assertEquals(false, examMarks.containsKey("Kristina"));
	}

	@Test
	public void firstExample() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(3);

		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		assertEquals(Integer.valueOf(5), examMarks.get("Kristina"));
		assertEquals(4, examMarks.size());
	}
}

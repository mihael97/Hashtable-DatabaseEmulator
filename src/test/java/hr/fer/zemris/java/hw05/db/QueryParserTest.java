package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.QueryParser;

@SuppressWarnings("javadoc")
public class QueryParserTest {

	@Test
	public void example1() {
		QueryParser parser = new QueryParser("jmbag=\"0000000008\"");

		List<ConditionalExpression> filter = parser.getQuery();

		assertEquals(FieldValueGetters.JMBAG, filter.get(0).getFieldGetter());
		assertEquals(ComparisonOperators.EQUALS, filter.get(0).getComparisonOperator());
		assertEquals("0000000008", filter.get(0).getStringLiteral());
	}

	@Test
	public void example2() {
		QueryParser parser = new QueryParser("firstName LIKE \"*ja\"");

		ConditionalExpression first = parser.getQuery().get(0);

		assertEquals(FieldValueGetters.FIRST_NAME, first.getFieldGetter());
		assertEquals(ComparisonOperators.LIKE, first.getComparisonOperator());
		assertEquals("*ja", first.getStringLiteral());
	}
	
	@Test
	public void example3() {
		QueryParser parser = new QueryParser("firstName LIKE \"*ja\" and lastName=\"Boris\"");

		List<ConditionalExpression> list=parser.getQuery();
		assertEquals(2, list.size());

		assertEquals(FieldValueGetters.FIRST_NAME, list.get(0).getFieldGetter());
		assertEquals(ComparisonOperators.LIKE, list.get(0).getComparisonOperator());
		assertEquals("*ja", list.get(0).getStringLiteral());
		
		assertEquals(FieldValueGetters.LAST_NAME, list.get(1).getFieldGetter());
		assertEquals(ComparisonOperators.EQUALS, list.get(1).getComparisonOperator());
		assertEquals("Boris", list.get(1).getStringLiteral());
	}
	
	@Test
	public void example4() {
		QueryParser parser = new QueryParser("lastName != \"Mihael\"");

		ConditionalExpression value=parser.getQuery().get(0);
		
		assertEquals(FieldValueGetters.LAST_NAME, value.getFieldGetter());
		assertEquals(ComparisonOperators.NOT_EQUALS, value.getComparisonOperator());
		assertEquals("Mihael", value.getStringLiteral());
	}
	
	@Test
	public void example5() {
		QueryParser parser = new QueryParser("lastName <= \"Mihael\"");

		ConditionalExpression value=parser.getQuery().get(0);
		
		assertEquals(FieldValueGetters.LAST_NAME, value.getFieldGetter());
		assertEquals(ComparisonOperators.LESS_OR_EQUALS, value.getComparisonOperator());
		assertEquals("Mihael", value.getStringLiteral());
	}
	
	@Test
	public void example6() {
		QueryParser parser = new QueryParser("lastName !=\"Mihael\" aNd firstName LIKE \"*bin\"");

		List<ConditionalExpression> value=parser.getQuery();
		
		assertEquals(FieldValueGetters.LAST_NAME, value.get(0).getFieldGetter());
		assertEquals(ComparisonOperators.NOT_EQUALS, value.get(0).getComparisonOperator());
		assertEquals("Mihael", value.get(0).getStringLiteral());
		
		assertEquals(FieldValueGetters.FIRST_NAME, value.get(1).getFieldGetter());
		assertEquals(ComparisonOperators.LIKE, value.get(1).getComparisonOperator());
		assertEquals("*bin", value.get(1).getStringLiteral());
	}
	
	@SuppressWarnings("unused")
	@Test(expected=NullPointerException.class)
	public void nullTest() {
		QueryParser parser = new QueryParser(null);
	}
	
	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void invalidInput1() {
		QueryParser parser = new QueryParser("\"Ante\"=firstName");
		
		List<ConditionalExpression> value=parser.getQuery();
	}
	
	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void invalidInput2() {
		QueryParser parser = new QueryParser("firstName=lastName");
		
		List<ConditionalExpression> value=parser.getQuery();
	}
}

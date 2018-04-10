package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;

@SuppressWarnings("javadoc")
public class ComparisonOperatorTest {
	@Test
	public void lessTest() {
		IComparisonOperator operator = ComparisonOperators.LESS;

		assertEquals(true, operator.satisfied("Ante", "Štefica"));
		assertEquals(false, operator.satisfied("Slavko", "Mirko"));
		assertEquals(false, operator.satisfied("Božena", "Božena"));
	}

	@Test
	public void lessOrEqualsTest() {
		IComparisonOperator operator = ComparisonOperators.LESS_OR_EQUALS;

		assertEquals(true, operator.satisfied("Ante", "Štefica"));
		assertEquals(false, operator.satisfied("Slavko", "Mirko"));
		assertEquals(true, operator.satisfied("Slavko", "Slavko"));
	}

	@Test
	public void greaterTest() {
		IComparisonOperator operator = ComparisonOperators.GREATER;

		assertEquals(true, operator.satisfied("Štefica", "Ante"));
		assertEquals(false, operator.satisfied("Božena", "Jasna"));
		assertEquals(false, operator.satisfied("Božena", "Božena"));
	}

	@Test
	public void greaterOrEqualsTest() {
		IComparisonOperator operator = ComparisonOperators.GREATER_OR_EQUALS;

		assertEquals(true, operator.satisfied("Štefica", "Ante"));
		assertEquals(false, operator.satisfied("Božena", "Jasna"));
		assertEquals(true, operator.satisfied("Božena", "Božena"));
	}

	@Test
	public void equalsTest() {
		IComparisonOperator operator = ComparisonOperators.EQUALS;

		assertEquals(false, operator.satisfied("Štefica", "Ante"));
		assertEquals(false, operator.satisfied("Božena", "Jasna"));
		assertEquals(true, operator.satisfied("Božena", "Božena"));
	}

	@Test
	public void notEqualsTest() {
		IComparisonOperator operator = ComparisonOperators.NOT_EQUALS;

		assertEquals(true, operator.satisfied("Štefica", "Ante"));
		assertEquals(true, operator.satisfied("Božena", "Jasna"));
		assertEquals(false, operator.satisfied("Božena", "Božena"));
	}

	@Test
	public void likeTest() {
		IComparisonOperator operator = ComparisonOperators.LIKE;

		// prije znaka * mogu se,a i ne moraju nalaziti znakovi
		assertEquals(true, operator.satisfied("Jasna", "*na")); 
		assertEquals(false, operator.satisfied("Jasna", "*mo"));

		// započinjanje s određenim nizom
		assertEquals(true, operator.satisfied("Jasna", "Ja*")); 
		assertEquals(false, operator.satisfied("Jasna", "ja*"));

		// riječ započinje i završava s određenim znakom
		assertEquals(true, operator.satisfied("Jasna", "J*a")); 
		assertEquals(false, operator.satisfied("Jasna", "M*a"));

		assertEquals(false, operator.satisfied("Jasna", "Jasn*na"));

		//jednako
		assertEquals(false, operator.satisfied("Jasna", "Ja")); 
		assertEquals(true, operator.satisfied("Jasna", "Jasna"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void likeExceptionTest() {
		IComparisonOperator operator = ComparisonOperators.LIKE;
		assertEquals(true, operator.satisfied("Jasna", "Ja**na"));
	}

	@Test
	public void example1() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Zagreb", "Aba*")); // false
		assertFalse(oper.satisfied("AAA", "AA*AA")); // false
		assertTrue(oper.satisfied("AAAA", "AA*AA")); // true
	}
}

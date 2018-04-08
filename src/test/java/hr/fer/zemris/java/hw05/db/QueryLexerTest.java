package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw05.db.QueryLexer;

@SuppressWarnings("javadoc")
public class QueryLexerTest {
	
	@Test
	public void example1() {
		QueryLexer lexer=new QueryLexer("firstName=\"Mihael\"");
		
		assertEquals("firstName", lexer.getToken());
		assertEquals("=", lexer.getToken());
		assertEquals("\"Mihael\"", lexer.getToken());
		assertNull(lexer.getToken());
	}
	
	@Test
	public void example2() {
		QueryLexer lexer=new QueryLexer("firstName=\"Mihael\" AND jmbag=\"000000000\"");
		
		assertEquals("firstName", lexer.getToken());
		assertEquals("=", lexer.getToken());
		assertEquals("\"Mihael\"", lexer.getToken());
		assertEquals("AND", lexer.getToken());
		assertEquals("jmbag", lexer.getToken());
		assertEquals("=", lexer.getToken());
		assertEquals("\"000000000\"", lexer.getToken());
		assertNull(lexer.getToken());
	}
	
	@Test
	public void example3() {
		QueryLexer lexer=new QueryLexer("firstName = \"Mihael\"");
		
		assertEquals("firstName", lexer.getToken());
		assertEquals("=", lexer.getToken());
		assertEquals("\"Mihael\"", lexer.getToken());
		assertNull(lexer.getToken());
	}
	
	@Test
	public void example4() {
		QueryLexer lexer=new QueryLexer("firstName>\"A\" and lastName LIKE \"B*ć\"");
		
		assertEquals("firstName", lexer.getToken());
		assertEquals(">", lexer.getToken());
		assertEquals("\"A\"", lexer.getToken());
		assertEquals("and", lexer.getToken());
		assertEquals("lastName", lexer.getToken());
		assertEquals("LIKE", lexer.getToken());
		assertEquals("\"B*ć\"", lexer.getToken());
		assertNull(lexer.getToken());
	}
	
}

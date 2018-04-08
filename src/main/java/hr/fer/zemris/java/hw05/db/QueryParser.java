package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Razred koji implementira parser upita
 * 
 * @author Mihael
 *
 */
public class QueryParser {
	/**
	 * Upit
	 */
	private String query;
	/**
	 * Lista dijelova upita
	 */
	private ArrayList<String> context;

	/**
	 * Konstruktor koji poziva metodu za parsiranje
	 * 
	 * @param query
	 *            - upit
	 * @throws NullPointerException
	 *             - ako je upit null
	 */
	public QueryParser(String query) {
		Objects.requireNonNull(query);
		this.query = query;
		parse();
	}

	/**
	 * Metoda koja uzastupno poziva lekser i sprema tokene u listu
	 */
	private void parse() {
		context = new ArrayList<>();
		QueryLexer lexer = new QueryLexer(query);

		String string = new String();

		while ((string = lexer.getToken()) != null) {
			// System.out.println(string);
			context.add(string);
		}
	}

	/**
	 * Metoda koja provjerava da li je upit direktan. Upit je direktan ako sadrži
	 * naredbu kojoj je jedini uvjet ispis studenta sa jednim specifičnim JMBAGom.
	 * 
	 * @return true ako je direktam, inače false
	 */
	public boolean isDirectQuery() {
		return context.size() == 3 && context.get(0).equals("jmbag") && context.get(1).equals("=")
				&& context.get(2).startsWith("\"") && context.get(2).endsWith("\"");
	}

	/**
	 * Metoda koja vraća JMBAG ako je upit direktan
	 * 
	 * @return JMBAG studenta u obliku Stringa
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException("Naredba \"" + query + "\" nije direktna!");
		}

		return context.get(2).substring(1, context.get(2).length() - 1);
	}

	/**
	 * Metoda sastavlja listu uvjetnih izraza ako upit nije direktan
	 * 
	 * @return lista uvjetnih izraza
	 */
	public List<ConditionalExpression> getQuery() {

		List<ConditionalExpression> forReturn = new LinkedList<>();
		int index = 0;

		while (index < context.size()) {
			IFieldValueGetter first;
			IComparisonOperator second;

			switch (context.get(index++)) {
			case "firstName":
				first = FieldValueGetters.FIRST_NAME;
				break;
			case "lastName":
				first = FieldValueGetters.LAST_NAME;
				break;
			case "jmbag":
				first = FieldValueGetters.JMBAG;
				break;
			default:
				throw new IllegalArgumentException("Kao naziv polja zadan je " + context.get(index - 1));
			}

			switch (context.get(index++)) {
			case "=":
				second = ComparisonOperators.EQUALS;
				break;
			case ">":
				second = ComparisonOperators.GREATER;
				break;
			case ">=":
				second = ComparisonOperators.GREATER_OR_EQUALS;
				break;
			case "<":
				second = ComparisonOperators.LESS;
				break;
			case "<=":
				second = ComparisonOperators.LESS_OR_EQUALS;
				break;
			case "!=":
				second = ComparisonOperators.NOT_EQUALS;
				break;
			case "LIKE":
				second = ComparisonOperators.LIKE;
				break;
			default:
				throw new IllegalArgumentException("Kao operator zadan je " + context.get(index - 1));
			}

			if (context.get(index).startsWith("\"") && context.get(index).endsWith("\"")) {
				forReturn.add(new ConditionalExpression(first,
						context.get(index).substring(1, context.get(index++).length() - 1), second));
				index++;
			} else {
				throw new IllegalArgumentException(
						"Predan je literal \'" + context.get(index) + "\' koji to ne može biti!");
			}
		}

		return forReturn;
	};

}

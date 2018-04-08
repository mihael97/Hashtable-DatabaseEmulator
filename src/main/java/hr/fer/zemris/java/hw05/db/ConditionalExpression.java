package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Razred koji predstavlja uvjetni izraz
 * 
 * @author Mihael
 *
 */
public class ConditionalExpression {

	/**
	 * Naziv polja
	 */
	private IFieldValueGetter fieldValueGetter;
	/**
	 * Literal,desna strana izraza
	 */
	private String literal;
	/**
	 * Operator
	 */
	private IComparisonOperator operator;

	/**
	 * Konstruktor koji od argumenata stvara novi uvjetni izraz
	 * 
	 * @param fieldValueGetter
	 *            - naziv polja
	 * @param literal
	 *            - literal
	 * @param operator
	 *            - operator
	 * 
	 * @throws NullPointerException
	 *             - ako je neki od argumenata
	 */
	public ConditionalExpression(IFieldValueGetter fieldValueGetter, String literal, IComparisonOperator operator) {
		this.fieldValueGetter = Objects.requireNonNull(fieldValueGetter);
		this.literal = Objects.requireNonNull(literal);
		this.operator = Objects.requireNonNull(operator);
	}

	/**
	 * Metoda vraća naziv polja
	 * 
	 * @return naziv polja {@link FieldValueGetters}
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldValueGetter;
	}

	/**
	 * Metoda vraća literal
	 * 
	 * @return literal u obliku Stringa
	 */
	public String getStringLiteral() {
		return literal;
	}

	/**
	 * Metoda koja vraća operator
	 * 
	 * @return operator {@link ComparisonOperators}
	 */
	public IComparisonOperator getComparisonOperator() {
		return operator;
	}

}

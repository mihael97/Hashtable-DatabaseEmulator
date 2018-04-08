package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Razred koji implementira susutav provjere zadovoljavaju li određeni zapisi
 * prije postavljen uvjete
 * 
 * @author Mihael
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * List uvjetnih izraza
	 */
	List<ConditionalExpression> list;

	/**
	 * Konstruktor koji prima lisu uvjetnih izraza
	 * 
	 * @param list
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}

	/**
	 * Metoda koja provjerava zadovoljava li student dan preko argumenta sve
	 * zahtjeve(uvjete)
	 * 
	 * @param record
	 *            - student kojeg provjeravamo
	 * 
	 * @throws true
	 *             ako student zadovoljava sve uvjete, inače false
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression expression : list) {
			IComparisonOperator operator = expression.getComparisonOperator();
			IFieldValueGetter field = expression.getFieldGetter();
			String literal = expression.getStringLiteral();

			if (operator.satisfied(field.get(record), literal) == false)
				return false;
		}

		return true;
	}

}

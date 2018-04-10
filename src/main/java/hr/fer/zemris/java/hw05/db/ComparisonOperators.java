package hr.fer.zemris.java.hw05.db;

/**
 * Razred koji sadrži sve operatore i metode koje provjeravaju zadovoljava li
 * zapis jednakosti
 * 
 * @author Mihael
 *
 */
public class ComparisonOperators {
	/**
	 * Varijabla koja sadrži provjeru je li prvi argument manji od drugog
	 */
	public static IComparisonOperator LESS = (string1, string2) -> (string1.compareTo(string2) < 0);
	/**
	 * Varijabla koja sadrži provjeru je li prvi argument manji ili jednak drugom
	 */
	public static IComparisonOperator LESS_OR_EQUALS = (string1, string2) -> (string1.compareTo(string2) <= 0);
	/**
	 * Varijabla koja sadrži provjeru je li prvi argument veći od drugog
	 */
	public static IComparisonOperator GREATER = (string1, string2) -> (string1.compareTo(string2) > 0);
	/**
	 * Varijabla koja sadrži provjeru je li prvi argument veći ili jednak drugom
	 */
	public static IComparisonOperator GREATER_OR_EQUALS = (string1, string2) -> (string1.compareTo(string2) >= 0);
	/**
	 * Varijabla koji sadrži provjeru da li su dva argumenata jednaka
	 */
	public static IComparisonOperator EQUALS = (string1, string2) -> (string1.compareTo(string2) == 0);
	/**
	 * Varijabla koji sadrži provjeru da li su dva argumenata različita
	 */
	public static IComparisonOperator NOT_EQUALS = (string1, string2) -> (string1.compareTo(string2) != 0);
	/**
	 * Varijabla koji sadrži provjeru da li su dva argumenata slična
	 */
	public static IComparisonOperator LIKE = new IComparisonOperator() {

		@Override
		public boolean satisfied(String value1, String value2) {
			StringBuilder builder = new StringBuilder();

			int count = 0;
			for (Character c : value2.toCharArray()) {
				if (c == '*') {
					if (++count > 1) {
						throw new IllegalArgumentException("Znak '*' pojavljuje se previše puta!");
					}
				}

				builder.append(c);
			}

			if ((builder.toString().contains("*") ? builder.length() - 1 : builder.length()) > value1.length()) {
				return false;
			}

			if (!builder.toString().contains("*")) {
				return value1.equals(builder.toString());
			}

			String[] pomArray = split(builder.toString());

			if (pomArray[0] == null)
				return value1.endsWith(pomArray[1]);
			if (pomArray[1] == null)
				return value1.startsWith(pomArray[0]);

			return value1.startsWith(pomArray[0]) && value1.endsWith(pomArray[1]);
		}

		/**
		 * Metoda rastavlja niz na dvije polovice ovisno o znaku *
		 * 
		 * @param string
		 *            - niz
		 * @return polje gdje se na prvoj poziciji nalazi sadržaj prije znaka,a na
		 *         drugoj poslije
		 */
		private String[] split(String string) {
			String[] forReturn = new String[2];
			char[] array = string.toCharArray();
			StringBuilder builder = new StringBuilder();

			for (int i = 0, length = array.length; i < length; i++) {
				if (array[i] == '*') {
					if (builder.length() == 0) {
						forReturn[0] = null;
						forReturn[1] = string.substring(1);
					} else {
						forReturn[0] = builder.toString();

						if (i + 1 != length) {
							forReturn[1] = string.substring(i + 1);
						} else {
							forReturn[1] = null;
						}
					}

					break;
				}

				builder.append(array[i]);
			}

			return forReturn;
		}
	};
}

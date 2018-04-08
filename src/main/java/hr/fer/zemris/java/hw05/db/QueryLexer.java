package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Razred koji implementira lekser upita tako da ih rastavlja na smislene
 * cjeline i vraća pozivatelju
 * 
 * @author Mihael
 *
 */
public class QueryLexer {
	/**
	 * Reprezentacija upita u obliku polja znakova
	 */
	char[] query;
	/**
	 * Indeks koji je sljedeći za čitanje u polju
	 */
	int index;

	/**
	 * Konstruktor koji inicijalizira lekser i postavlja prvu poziciju na nulu
	 * 
	 * @param query
	 *            - upit
	 * @throws NullPointerException
	 *             - ako je upit null
	 */
	public QueryLexer(String query) {
		this.query = Objects.requireNonNull(query).toCharArray();
		index = 0;
	}

	/**
	 * Metoda koja vraća sljedeći token na poziv parsera
	 * 
	 * @return sljedeći token u obliku Stringa
	 */
	public String getToken() {
		StringBuilder builder = new StringBuilder();

		while (index < query.length) {
			char c = query[index];

			if (Character.isWhitespace(c)) {
				if (builder.length() != 0) {
					index++;
					break;
				}
				index++;
			}

			else if (c == '!' || c == '=' || c == '<' || c == '>') {
				if (builder.length() != 0) {
					break;
				}

				builder.append(c);

				if (c == '!' && query[index + 1] == '=' || query[index + 1] == '=' && (c == '<' || c == '>')) {
					builder.append(query[++index]);
				}

				index++;
				break;
			}

			else if (c == '\"') {
				builder.append(read());
				break;
			}

			else if (builder.toString().toUpperCase().equals("AND")
					|| builder.toString().toUpperCase().equals("LIKE")) { // AND operator
				break;
			} else {
				builder.append(c);
				index++;
			}

		}

		return builder.length() != 0 ? builder.toString() : null;
	}

	/**
	 * Metoda koja čita tekst unutar navodnika(literal)
	 * 
	 * @return pročitani tekst
	 * 
	 * @throws IllegalArgumentException
	 *             - ako su literal nezatvoren
	 */
	private String read() {
		StringBuilder builder = new StringBuilder().append(query[index]);

		index++;
		char c;

		while ((c = query[index]) != '\"') {
			builder.append(c);

			if (index + 1 == query.length)
				break;
			index++;
		}

		builder.append(c);
		
		if ((index + 1) <= query.length) {
			index++;
			return builder.toString();
		}

		throw new IllegalArgumentException("Niz nije završen s \"");
	}

}

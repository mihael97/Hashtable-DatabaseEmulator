package hr.fer.zemris.java.hw05.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.lang.Math.abs;

/**
 * Razred koji implementira tablicu raspršenog adresiranja
 * 
 * @author Mihael
 *
 * @param <K>
 *            - parametar po kojem je parametriziran ključ
 * @param <V>
 *            - parametar po kojem je parametrizirana vrijednost
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	/**
	 * Referenca koja predstavlja veličinu tablice
	 */
	private int capacity;

	/**
	 * Konstanta koja predstavlja početni kapacitet
	 */
	private static int BASIC_CAPACITY = 16;

	/**
	 * Polje {@link TableEntry} koje predstavlja referencu na glave,početke slotova
	 */
	private TableEntry<K, V>[] table;

	/**
	 * Količina zapisa koji su u kolekciji
	 */
	private int size;

	/**
	 * Varijabla koja pamti broj promjena koji se dogodio u programu
	 */
	private int modificationCount;

	/**
	 * Defaultni konstruktor koji postavlja veličinu tablice na 16
	 */
	public SimpleHashtable() {
		this.capacity = BASIC_CAPACITY;
		table = (TableEntry<K, V>[]) new TableEntry[capacity];
		size = 0;
		modificationCount = 0;
	}

	/**
	 * Konstruktor koji postavlja veličinu tablice na velicnu određenu s argumentom.
	 * Ako broj nije potencija broja 2,velčina će se postaviti na prvu potenciju
	 * broja dva vecu od argumenta
	 * 
	 * @param capacity
	 *            - željeni kapacitet
	 * 
	 * @throws IllegalArgumentException
	 *             - ako je kao argument predan broj manji od 1
	 * 
	 */
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException("Argument ne smije biti manji od 1!");
		} else {
			this.capacity = setCapacity(capacity);
			table = (TableEntry<K, V>[]) new TableEntry[this.capacity];
			size = 0;
			modificationCount = 0;
		}
	}

	/**
	 * Metoda koja vraća budući kapacitet tako da računa prvu sljedeću potenciju
	 * broja 2(od broja koja je zadan kao argument konstruktora)
	 * 
	 * @param capacity
	 *            - kapacitet
	 * @return budući kapacitet u obliku Integera
	 */
	private int setCapacity(int capacity) {
		int sum = 1;

		while (sum < capacity) {
			sum *= 2;
		}

		return sum;
	}

	/**
	 * Metoda koja stavlja zapis u hash tablicu. Ako zapis sa određenim ključem
	 * postoji,njegova se vrijednost postavlja na argument dan funkcijom
	 * 
	 * @param key
	 *            - ključ na kojeg postavljamo
	 * @param value
	 *            - vrijednost na koju zapis postavljamo
	 * @throws IllegalArgumentException
	 *             - ako je ključ zapisa null
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Ključ zapisa ne smije biti null!");
		} else if (checkSize() && !containsKey(key)) {

			TableEntry<K, V>[] pomTable = Arrays.copyOf(table, capacity);
			capacity *= 2;
			clear();
			table = Arrays.copyOf(table, capacity);

			for (TableEntry<K, V> entry : pomTable) {
				while (entry != null) {
					put(entry.key, entry.value);

					entry = entry.next;
				}
			}
		}

		boolean change = false;
		int hashCode = getHashCode(key);
		TableEntry<K, V> pomEntry = table[hashCode];

		if (pomEntry == null) {
			table[hashCode] = new TableEntry<>(key, value, null);
			size++;
		} else {
			while (true) {
				if (pomEntry.key.equals(key)) {
					change = true;
					pomEntry.setValue(value);
					break;
				}

				if (pomEntry.next == null)
					break;

				pomEntry = pomEntry.next;
			}

			if (!change) {
				pomEntry.next = new TableEntry<>(key, value, null);
				size++;
				modificationCount++;
			}
		}
	}

	/**
	 * Metoda koja provjerava pretjeranu popunjenost tablice. Pretjerana popunjenost
	 * je kada broj parova dostigne 75% ukupnog kapaciteta
	 * 
	 * @return true ako je pretjerana popunjenost prisutna,inače false
	 */
	private boolean checkSize() {
		return (size / capacity) >= 0.75;
	}

	/**
	 * Metoda koja vraća vrijednost zapisa kojem je ključ zadani argument. Ako
	 * element ne postoji,vraca null
	 * 
	 * @param key
	 *            - ključ zapisa kojeg tražimo
	 * @return vrijednost zapisa ako postoji,inace null
	 */
	public V get(Object key) {

		TableEntry<K, V> entry = table[getHashCode(key)];

		while (true) {
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}

			if (entry.next == null)
				break;
			entry = entry.next;
		}

		return null;
	}

	/**
	 * Metoda provjerava postoji li zapis sa danim ključem
	 * 
	 * @param key
	 *            - ključ kojeg trazimo
	 * @return true ako postoji,inace false
	 */
	public boolean containsKey(K key) {
		if (key != null) {

			TableEntry<K, V> entry = table[getHashCode(key)];

			while (entry != null) {
				if (entry.getKey().equals(key)) {
					return true;
				}

				entry = entry.next;
			}
		}

		return false;
	}

	/**
	 * Metoda koja provjerava je li određena vrijednost sadržana u tablici
	 * 
	 * @param value
	 *            - vrijednost koju tražimo
	 * @return true ako se nalazi,inače false
	 */
	public boolean containsValue(Object value) {
		for (int i = 0, length = table.length; i < length; i++) {

			TableEntry<K, V> entry = table[i];

			while (entry != null) {
				if (value == null && entry.getValue() == null || entry.getValue().equals(value)) {
					return true;
				}

				entry = entry.next;
			}
		}

		return false;
	}

	/**
	 * Metoda koja briše clan s određenim ključem iz tablice
	 * 
	 * @param key
	 *            - ključ zapisa koje želimo izbrisati
	 */
	public void remove(Object key) {
		if (key != null) {
			int hashCode = getHashCode(key);
			TableEntry<K, V> pomEntry = table[hashCode];
			TableEntry<K, V> before = null;

			while (pomEntry != null) {
				if (pomEntry.key.equals(key)) {
					if (before == null) {
						table[hashCode] = pomEntry.next;
					} else {
						before.next = pomEntry.next;
					}

					modificationCount++;
					size--;
					break;
				}

				if (pomEntry.next == null)
					break;
				before = pomEntry;
				pomEntry = pomEntry.next;
			}
		}
	}

	/**
	 * Metoda koja provjerava je li hash tablica prazna
	 * 
	 * @return true ako je,inace false
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Metoda koja prolazi kroz cijelu hash tablicu i ispisuje uredene parove
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder().append("[");

		for (int i = 0, length = table.length; i < length; i++) {

			TableEntry<K, V> entry = table[i];

			while (entry != null) {
				builder.append(entry.getKey() + "=").append(entry.getValue()).append(", ");

				entry = entry.next;
			}
		}

		builder = new StringBuilder(builder.toString().substring(0, builder.length() - 2));
		builder.append("]");

		return builder.toString();
	}

	/**
	 * Metoda koja briše sve zapisane parove tako da glave postavi na null
	 */
	public void clear() {
		for (int i = 0, length = table.length; i < length; i++) {
			table[i] = null;
		}

		size = modificationCount = 0;
	}

	/**
	 * Metoda koja vraća broj do sada spremljenih parova
	 * 
	 * @return broj parova
	 */
	public int size() {
		return size;
	}

	/**
	 * Metoda koja vraća index slota u tablici gdje se prišadajući ključ treba
	 * nalaziti ili se nalazi
	 * 
	 * @param key
	 *            - ključ
	 * @return index pretinca tablice
	 */
	private int getHashCode(Object key) {
		return abs(key.hashCode()) % capacity;
	}

	/**
	 * Javni staticki razred koji predstavlja jedan slot tablice
	 * 
	 * @author Mihael
	 *
	 * @param <K>
	 *            - parametar po kojem je parametriziran ključ
	 * @param <V>
	 *            - parametar po kojem je parametrizirana vrijednost
	 */
	public static class TableEntry<K, V> {
		/**
		 * Referenca na ključ zapisa
		 */
		private K key;
		/**
		 * Referenca na vrijednost zapisa
		 */
		private V value;
		/**
		 * Referenca na zapis koji se nalazi sljedeći u bloku
		 */
		private TableEntry<K, V> next;

		/**
		 * Javni konstruktor koji inicijalizira novi {@link TableEntry} zapis
		 * 
		 * @param key
		 *            - ključ novog zapisa
		 * @param value
		 *            - vrijednost novog zapisa
		 * @param next
		 *            - sljedeći član
		 * 
		 * @throws IllegalArgumentException
		 *             - ako je predani argument za ključ null
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if (key == null) {
				throw new IllegalArgumentException("Ključ ne smije biti null!");
			}
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Javni getter za vrijednost
		 * 
		 * @return vrijednost zapisa
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter za vrijednost zapisa koja mora biti tipa V
		 * 
		 * @param value
		 *            - vrijednost na koju postavljamo zapis
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Getter za ključ zapisa
		 * 
		 * @return ključ zapisa paramteriziran po K
		 */
		public K getKey() {
			return key;
		}
	}

	/**
	 * Metoda koja kao povratnu vrijednost vraća referencu na iterator pomoću kojeg
	 * možemo prolaziti kroz hash tablicu
	 * 
	 * @return referenca na {@link Iterator}
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Privatna klasa koja implementira iterator kojim prolazimo kroz hash tablicu.
	 * Sadrži metode next,hasNext i remove
	 * 
	 * @author Mihael
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * Index na "slot" hash tablice u kojem se nalazimo
		 */
		private int index = 0;
		/**
		 * Referenca na zadnji vracen element - par
		 */
		private TableEntry<K, V> current = table[0];
		/**
		 * Varijabla koja cuva broj izmjena koje su se dogodile do pocetka stvaranja
		 * iteratora
		 */
		private int modCount = modificationCount;

		/**
		 * Metoda koja provjerava sadrži li tablica sljedeci zapis
		 * 
		 * @return true ako sadrzi,inace false
		 * 
		 * @throws ConcurrentModificationException
		 *             - ako se usporedbom broja modifikacija zakljuci da je doslo
		 *             preinaka
		 */
		@Override
		public boolean hasNext() {
			if (modCount != modificationCount) {
				throw new ConcurrentModificationException(
						"Jedan modifikator je " + modCount + ",a drugi " + modificationCount);
			} else if (current == null || current.next == null) {
				if ((index + 1) == capacity) {
					return false;
				}
			}

			return true;
		}

		/**
		 * Metoda koja briše zadnje generirani član iz kolekcije
		 * 
		 * @throws ConcurrentModificationException
		 *             - ako se usporedbom broja modifikacija zakljuci da je doslo do
		 *             problema
		 */
		@Override
		public void remove() {
			if (modCount != modificationCount) {
				throw new ConcurrentModificationException(
						"Jedan modifikator je " + modCount + ",a drugi " + modificationCount);
			}
			SimpleHashtable.this.remove(current.getKey());
			modCount++;
		}

		/**
		 * Metoda vraća sljedeći zapis koji slijedi nakon zadnjeg generiranog
		 * 
		 * 
		 * @return {@link TableEntry} - sljedeći clan
		 * 
		 * @throws ConcurrentModificationException
		 *             - ako se usporedbom broja modifikacija zaključi da je doslo do
		 *             preinaka
		 * 
		 * @throws NoSuchElementException
		 *             - ako se dode do kraja liste
		 */
		@Override
		public TableEntry<K, V> next() {
			if (modCount != modificationCount) {
				throw new ConcurrentModificationException(
						"Jedan modifikator je " + modCount + ",a drugi " + modificationCount);
			} else if (current == null || current.next == null) {
				index++;
				for (int i = index, length = table.length; i < length; i++) {
					if (table[i] != null) {
						current = table[i];
						return current;
					}

					index++;
				}

				throw new NoSuchElementException("Kraj liste,zapisi više ne postoje!");
			} else {
				current = current.next;
				return current;
			}
		}

	}

}

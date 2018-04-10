package hr.fer.zemris.java.hw05.db;

/**
 * Razred koji predstavlja svakog studenta sa svojim atributima
 * ime,prezime,jmbag i finalna ocjena
 * 
 * @author Mihael
 *
 */
public class StudentRecord {
	/**
	 * Referenca na studentov jmbag - jedinstevni broj akademskog građanina
	 */
	private String jmbag;
	/**
	 * Referenca na prezime studenta
	 */
	private String lastName;
	/**
	 * Referenca na ime studenta
	 */
	private String firstName;
	/**
	 * Referenca na završnu ocjenu studenta
	 */
	private String finalGrade;

	/**
	 * Javni konstruktor koji inicijalizira studenta s danima argumentima
	 * 
	 * @param jmbag
	 *            - broj akademskog građanina
	 * @param lastName
	 *            - prezime
	 * @param firstName
	 *            - ime
	 * @param finalGrade
	 *            - završna ocjena
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		
		if(jmbag==null || lastName==null || firstName==null || finalGrade==null) {
			throw new NullPointerException("Jedan od atributa je null!");
		}
		
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Metoda koja vraća JMBAG studenta
	 * 
	 * @return JMBAG u obliku Stringa
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Metoda vraća prezime studenta
	 * 
	 * @return prezime studenta
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Metoda vraća ime studenta
	 * 
	 * @return ime studenta
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Metoda vraća konačnu ocjenu
	 * 
	 * @return konačna ocjena
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Metode vraća hashCode studenta
	 * 
	 * @return hashCode u obliku cijelog broja
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((finalGrade == null) ? 0 : finalGrade.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	/**
	 * Metoda koja uspoređuje dva objekta jesu li isti(imaju iste vrijednosti)
	 * 
	 * @return true ako su isti,inače false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StudentRecord)) {
			return false;
		}
		StudentRecord other = (StudentRecord) obj;
		if (finalGrade == null) {
			if (other.finalGrade != null) {
				return false;
			}
		} else if (!finalGrade.equals(other.finalGrade)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (jmbag == null) {
			if (other.jmbag != null) {
				return false;
			}
		} else if (!jmbag.equals(other.jmbag)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		return true;
	}

	/**
	 * Metoda koja vraća ispis informacija o studentu
	 * 
	 * @return String kao ispis informacija o studentu
	 */
	@Override
	public String toString() {
		return jmbag + " " + lastName + " " + firstName + " " + finalGrade;
	}

}

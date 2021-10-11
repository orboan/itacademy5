package com.video.domain;

import java.util.Date;

public class Usuari {
	private String name;
	private String surname;
	private final String username; //identificador
	private char[] password;
	private final Date registrationDate;

	public Usuari(String name, String surname, String username, Date registrationDate) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.registrationDate = registrationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (getClass() != o.getClass()) {
			return false;
		}

		//
		Usuari u = (Usuari) o;
		return (this.getUsername().equals(u.getUsername()));
	}

	@Override
	public int hashCode() {
		final int PRIME = 37;
		int result = 3;
		result = PRIME * result + this.username.hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return (new StringBuilder()).append("Usuari:\n")
				.append("Nom: " + this.name + "\n")
				.append("Cognoms: " + this.surname + "\n")
				.append("Nom d'usuari: " + this.username + "\n")
				.toString();
	}

}

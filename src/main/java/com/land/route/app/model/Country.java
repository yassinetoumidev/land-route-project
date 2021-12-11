package com.land.route.app.model;

import java.util.LinkedHashSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

	private String cca3;
	private LinkedHashSet<String> borders;

	public String getCca3() {
		return cca3;
	}

	public void setCca3(String cca3) {
		this.cca3 = cca3;
	}

	public LinkedHashSet<String> getBorders() {
		return borders;
	}

	public void setBorders(LinkedHashSet<String> borders) {
		this.borders = borders;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((borders == null) ? 0 : borders.hashCode());
		result = prime * result + ((cca3 == null) ? 0 : cca3.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (borders == null) {
			if (other.borders != null)
				return false;
		} else if (!borders.equals(other.borders))
			return false;
		if (cca3 == null) {
			if (other.cca3 != null)
				return false;
		} else if (!cca3.equals(other.cca3))
			return false;
		return true;
	}

	

}

package pl.kruzar.calc4runner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Walidacja {
	/**
	 * Metoda za pomocą której sprawdzamy poprawność formatu czasu.
	 * Jeśli format jest hh:mm:ss to wynikiem będzie true
	 * 
	 * @param String czas
	 * @return boolean
	 */
	public static boolean sprawdzPoprawnoscFormatuCzasu(String czas) {
		String reg = "^(\\d{2}):(\\d{2}):(\\d{2})$"; //odpowiada to: hh:mm:ss
		Pattern p = Pattern.compile(reg);
		Matcher matcher = p.matcher(czas);
		if(matcher.matches()){
			return true;
		} else {
			return false;
		}
	}
}

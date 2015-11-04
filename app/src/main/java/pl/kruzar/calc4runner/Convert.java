package pl.kruzar.calc4runner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Convert {
	/**
	 * Metoda za pomocą której zmieniamy czas w formacie hh:mm:ss
	 * na ilość dni
	 * 
	 * @param int ilosc_godzin
	 * @param int ilosc_minut
	 * @param int ilosc_sekund
	 * @return double ilosc_dni
	 */
	public static double czas2dzien(double ilosc_godzin, double ilosc_minut, double ilosc_sekund){
		double ilosc_dni = 0;
		
		ilosc_dni = ilosc_dni + (ilosc_godzin / 24); 
		ilosc_dni = ilosc_dni + ilosc_minut / (24 * 60);
		ilosc_dni = ilosc_dni + ilosc_sekund / (24 * 60 * 60);
		
		return ilosc_dni;
	}
	
	/**
	 * Metoda zamienia ilość dni na czas w formacie hh:mm:ss
	 * @param double ilosc_dni
	 * @return String
	 */
	public static String dzien2czas(double ilosc_dni){
		double dh = Math.floor(ilosc_dni * 24);
		double dm = Math.floor((ilosc_dni * 24 - dh) * 60);
		double ds = Math.floor(((ilosc_dni * 24 - dh) * 60 - dm ) * 60);
		
		int h = (int) (dh);
		int m = (int) (dm);
		int s = (int) (ds);
		
		if(s == 60){
			s = 0;
			m = m + 1;
		}
		
		if(m == 60){
			m = 0;
			h = h + 1;
		}
		
		String czas;
		
		if(h < 10){
			czas = "0" + Integer.toString(h);
		} else {
			czas = Integer.toString(h);
		}
		
		czas = czas + ":";
		
		if(m < 10){
			czas = czas + "0" + Integer.toString(m);
		} else {
			czas = czas + Integer.toString(m);
		}
		
		czas = czas + ":";
		
		if(s < 10){
			czas = czas + "0" + Integer.toString(s);
		} else {
			czas = czas + Integer.toString(s);
		}
		
		return czas;
	}
	
	/**
	 * Metoda zmienia ilość dni na tempo, które wyrażane jest w formacie mm:ss
	 * @param ilosc_dni
	 * @return
	 */
	public static String dzien2tempo(double ilosc_dni){
		double dh = Math.floor(ilosc_dni * 24);
		double dm = Math.floor((ilosc_dni * 24 - dh) * 60);
		double ds = Math.floor(((ilosc_dni * 24 - dh) * 60 - dm ) * 60);
		
		int h = (int) (dh);
		int m = (int) (dm);
		int s = (int) (ds);
		
		if(s == 60){
			s = 0;
			m = m + 1;
		}
		
		//dodajemy godziny do minut
		m = h * 60 + m;
		
		String czas;
		
		czas = Integer.toString(m);
		
		czas = czas + ":";
		
		if(s < 10){
			czas = czas + "0" + Integer.toString(s);
		} else {
			czas = czas + Integer.toString(s);
		}
		
		return czas;
	}
	
	public static List<String> pobierzGodzineMinuteSekundeZCzasu(String czas) throws Exception{
		String reg = "^(\\d{2}):(\\d{2}):(\\d{2})$"; //odpowiada to: hh:mm:ss
		Pattern p = Pattern.compile(reg);
		Matcher matcher = p.matcher(czas);
		if(matcher.matches()){
			//int i = Integer.parseInt(matcher.group(1));
			List<String> list_czas = new ArrayList<String>();
			list_czas.add(matcher.group(1)); //hh
			list_czas.add(matcher.group(2)); //mm
			list_czas.add(matcher.group(3)); //ss
			return list_czas;
		} else {
			throw new Exception();
		}
	}
}

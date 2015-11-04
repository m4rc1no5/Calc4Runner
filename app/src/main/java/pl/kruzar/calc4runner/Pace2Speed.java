package pl.kruzar.calc4runner;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class Pace2Speed {
	private int min = 0;
	private int sec = 0;
	private Context context;
	
	public Pace2Speed(Context c, EditText ilosc_minut, EditText ilosc_sekund) throws Exception {
		context = c;
		
		try {
			min = Integer.parseInt(ilosc_minut.getText().toString());
		} catch (NumberFormatException e) {
			//throw new NumberFormatException("Niepoprawnie wypełnione pola");
			min = 0;
		}
		
		try {
			sec = Integer.parseInt(ilosc_sekund.getText().toString());
		} catch (NumberFormatException e) {
			//throw new NumberFormatException("Niepoprawnie wypełnione pola");
			sec = 0;
		}
		
		if(sec > 60){
			String error_msg = context.getResources().getString(R.string.exception_za_duzo_sekund);
			throw new Exception(error_msg);
		}
	}
	
	public String getMinkm(){
		String str_wynik;
		String str_min;
		String str_sec;
		
		String str_jednostka = context.getResources().getString(R.string.minkm);
		
		if(min<10){
			str_min = "0" + min;
		} else {
			str_min = Integer.toString(min);
		}
		
		if(sec<10){
			str_sec = "0" + sec;
		} else {
			str_sec = Integer.toString(sec);
		}
		
		str_wynik = str_min + ":" + str_sec + " " + str_jednostka;
		
		return str_wynik;
	}
	
	private Double obliczKph() throws Exception {
		double czas_w_sekundach_na_km = min * 60 + sec;
		
		double wynik = 0;
		
		if(czas_w_sekundach_na_km > 0){
			wynik = 3600 / czas_w_sekundach_na_km;
		} else {
			String error_msg = context.getResources().getString(R.string.p2s_info);
			throw new Exception(error_msg);
		}
		
		return wynik;
	}
	
	public String getKph() throws Exception {
		double wynik = 0;
		
		try {
			wynik = obliczKph();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		//zaokrąglamy wynik do dwóch miejsc po przecinku
		double f = Math.pow(10, 2);
		wynik = ((int)(wynik*f))/f;
		
		String str_wynik = Double.toString(wynik);
		String str_jednostka = context.getResources().getString(R.string.kph);
		str_wynik = str_wynik + " " + str_jednostka;
		
		return str_wynik;
	}
	
	private Double obliczMph() throws Exception {
		double wynik = 0;
		
		try {
			wynik = obliczKph();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		wynik = 0.621371192 * wynik;
		
		return wynik;
	}
	
	public String getMph() throws Exception {
		double wynik = 0;
		
		try {
			wynik = obliczMph();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		//zaokrąglamy wynik do dwóch miejsc po przecinku
		double f = Math.pow(10, 2);
		wynik = ((int)(wynik*f))/f;
		
		String str_wynik = Double.toString(wynik);
		String str_jednostka = context.getResources().getString(R.string.mph);
		str_wynik = str_wynik + " " + str_jednostka;
		
		return str_wynik;
	}
	
	public void setTabelka(TableLayout t) throws Exception {
		//ustawiamy że tabelka ma być widoczna
		t.setVisibility(View.VISIBLE);
		
		//pola tabelki które uzupełnimy wartościami
		TextView pole_minkm = (TextView) t.findViewById(R.id.p2s_txt_minkm);
		TextView pole_kph = (TextView) t.findViewById(R.id.p2s_txt_kph);
		TextView pole_mph = (TextView) t.findViewById(R.id.p2s_txt_mph);
		
		String str_minkm = getMinkm(); 
		
		//obliczenia
		String str_kph = "";
		String str_mph = "";
		try {
			str_kph = getKph();
			str_mph = getMph();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		pole_minkm.setText(str_minkm);
		pole_kph.setText(str_kph);
		pole_mph.setText(str_mph);
	}
}

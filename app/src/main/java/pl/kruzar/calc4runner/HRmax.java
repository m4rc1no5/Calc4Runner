package pl.kruzar.calc4runner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TextView;

public class HRmax {
	private Context context;
	private boolean kobieta = true;
	private boolean mezczyzna = false;
	private double masa;
	private int wiek;
	
	private double hrmax;
	
	private double hrmax_metoda1_wynik;
	private double hrmax_metoda2_wynik;
	private double hrmax_metoda3_wynik;
	private double hrmax_metoda4_wynik;
	private double hrmax_metoda5_wynik;
	
	private double hrmax_min;
	private double hrmax_max;
	
	public HRmax(Context c, RadioButton rb_kobieta, RadioButton rb_mezczyzna, EditText et_masa, EditText et_wiek){
		context = c;
		
		//sprawdzamy czy mezczyzna jest zaznaczony - jesli tak to zmieniamy opcje
		if(rb_mezczyzna.isChecked() == true){
			kobieta = false;
			mezczyzna = true;
		}
		
		try {
			masa = Double.parseDouble(et_masa.getText().toString());
		} catch (NumberFormatException e) {
			masa = 0;
		}
		
		try {
			wiek = Integer.parseInt(et_wiek.getText().toString());
		} catch (NumberFormatException e) {
			wiek = 0;
		}
		
		//obliczamy HRmax
		hrmax_metoda1_wynik = obliczHRmaxMetoda1();
		hrmax_metoda2_wynik = obliczHRmaxMetoda2();
		hrmax_metoda3_wynik = obliczHRmaxMetoda3();
		hrmax_metoda4_wynik = obliczHRmaxMetoda4();
		hrmax_metoda5_wynik = obliczHRmaxMetoda5();
		
		//obliczamy minHRmax i maxHRmax
		setMinIMaxHRmax();
		
		//zapamiętujemy w pamięci hrmax obliczone najbardziej
		//tradycyjną metodą (metoda 2: 220 - wiek) 
		//tak aby program uzupełniał hrmax w kalkularorze
		//training zones
		SharedPreferences moja_pamiec = c.getSharedPreferences("moja_pamiec",0);
		SharedPreferences.Editor editor = moja_pamiec.edit();
		String str_hrmax = Double.toString(hrmax_metoda2_wynik);
		editor.putString("default_hrmax", str_hrmax);
		editor.commit();
	}
	
	private Double obliczHRmaxMetoda1(){
		double wynik = 0;
		
		if(kobieta){
			wynik = 210 - 0.5 * wiek - 0.022 * masa;
		} else if(mezczyzna) {
			wynik = 210 - 0.5 * wiek - 0.022 * masa + 4;
		} else {
			wynik = 210 - 0.5 * wiek - 0.022 * masa + 4;
		}
		
		//zaokrąglamy wynik do dwóch miejsc po przecinku
		double f = Math.pow(10, 1);
		wynik = ((int)(wynik*f))/f;
		
		return wynik;
	}
	
	private Double obliczHRmaxMetoda2(){
		double wynik = 0;
		
		wynik = 220 - wiek;
		
		//zaokrąglamy wynik do dwóch miejsc po przecinku
		double f = Math.pow(10, 1);
		wynik = ((int)(wynik*f))/f;
		
		return wynik;
	}
	
	private Double obliczHRmaxMetoda3(){
		double wynik = 0;
		
		wynik = 205.8 - (0.685 * wiek);
		
		//zaokrąglamy wynik do dwóch miejsc po przecinku
		double f = Math.pow(10, 1);
		wynik = ((int)(wynik*f))/f;
		
		return wynik;
	}
	
	private Double obliczHRmaxMetoda4(){
		double wynik = 0;
		
		wynik = 206.3 - (0.711 * wiek);
		
		//zaokrąglamy wynik do dwóch miejsc po przecinku
		double f = Math.pow(10, 1);
		wynik = ((int)(wynik*f))/f;
		
		return wynik;
	}
	
	private Double obliczHRmaxMetoda5(){
		double wynik = 0;
		
		wynik = 217 - (0.85 * wiek);
		
		//zaokrąglamy wynik do dwóch miejsc po przecinku
		double f = Math.pow(10, 1);
		wynik = ((int)(wynik*f))/f;
		
		return wynik;
	}
	
	private void setMinIMaxHRmax(){
		List<Double> ar_hrmax = new ArrayList<Double>();
		ar_hrmax.add(hrmax_metoda1_wynik);
		ar_hrmax.add(hrmax_metoda2_wynik);
		ar_hrmax.add(hrmax_metoda3_wynik);
		ar_hrmax.add(hrmax_metoda4_wynik);
		ar_hrmax.add(hrmax_metoda5_wynik);
		
		Collections.sort(ar_hrmax);
		
		hrmax_min = ar_hrmax.get(0);
		hrmax_max = ar_hrmax.get(ar_hrmax.size()-1);
	}
	
	public void setTabelka(TableLayout t){
		t.setVisibility(View.VISIBLE);
		
		List<String> ar_metoda = new ArrayList<String>();
		ar_metoda.add("metoda_1");
		ar_metoda.add("metoda_2");
		ar_metoda.add("metoda_3");
		ar_metoda.add("metoda_4");
		ar_metoda.add("metoda_5");
		
		for(String metoda : ar_metoda){
			//ustawiamy hrmax
			setHRmax(metoda);
			
			/* zakres BMI */
			//wyciągamy id
			int id_metoda = t.getResources().getIdentifier("hrmax_" + metoda + "_input", "id", context.getPackageName());
			//wyciągamy odpowiedni TextView
			TextView tv_hrmax = (TextView)t.findViewById(id_metoda);
			setHRmax(tv_hrmax);
		}
	}
	
	private void setHRmax(TextView tv){
		//zaokrąglamy wynik do jednego miejsca po przecinku
		double f = Math.pow(10, 1);
		hrmax = ((int)(hrmax*f))/f;
		
		tv.setText(Double.toString(hrmax));
	}
	
	private void setHRmax(String metoda){
		if(metoda.equals("metoda_1")){
			hrmax = hrmax_metoda1_wynik;
		} else if(metoda.equals("metoda_2")){
			hrmax = hrmax_metoda2_wynik;
		} else if(metoda.equals("metoda_3")){
			hrmax = hrmax_metoda3_wynik;
		} else if(metoda.equals("metoda_4")){
			hrmax = hrmax_metoda4_wynik;
		} else if(metoda.equals("metoda_5")){
			hrmax = hrmax_metoda5_wynik;
		}
	}
	
	public String getWynikPrzedzialHRmax(){
		String str_hrmax_min = Double.toString(hrmax_min);
		String str_hrmax_max = Double.toString(hrmax_max);
		
		String wynik = str_hrmax_min + " - " + str_hrmax_max;
		
		return wynik;
		
	}
}

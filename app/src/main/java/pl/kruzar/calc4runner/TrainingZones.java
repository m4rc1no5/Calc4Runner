package pl.kruzar.calc4runner;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Klasa dzięki której obliczymy strefy treningowe
 * 
 * Calculation of a zone value
 * The calculation of a zone value, X%, is performed in the following way:
 * 
 * Subtract your RHR from your MHR giving us your working heart rate (WHR)
 * Calculate the required X% on the WHR giving us "Z"
 * Add "Z" and your RHR together to give us the final value
 * Example: The athlete's MHR is 180 and their RHR is 60 - determine the 70% value
 * 
 * MHR - RHR = 180 - 60 = 120
 * 70% of 120 = 84
 * 84 + RHR = 84 + 60 = 144 bpm
 * 
 * @author marcin
 *
 */
public class TrainingZones {
	private Context context;
	private int rhr;
	private int hrmax;
	private int zakres_hr;
	
	/**
	 * W konstruktorze przypisujemy zmienne sprawdzając czy są ok
	 * @param c
	 * @param et_rhr
	 * @param et_hrmax
	 * @throws Exception
	 */
	public TrainingZones(Context c, EditText et_rhr, EditText et_hrmax) throws Exception {
		context = c;
		String str_rhr;
		String str_hrmax;
		
		try {
			str_rhr = et_rhr.getText().toString();
			rhr = Integer.parseInt(str_rhr);
		} catch (NumberFormatException e) {
			throw new Exception(context.getResources().getString(R.string.podaj_tetno_spoczynkowe));
		}
		
		try {
			str_hrmax = et_hrmax.getText().toString();
			hrmax = Integer.parseInt(str_hrmax);
		} catch (NumberFormatException e) {
			throw new Exception(context.getResources().getString(R.string.podaj_tetno_max));
		}
		
		//pamięć - zapiszemy do niej wartości wprowadzone
		SharedPreferences moja_pamiec = c.getSharedPreferences("moja_pamiec",0);
		SharedPreferences.Editor editor = moja_pamiec.edit();
		editor.putString("default_rhr", str_rhr);
		editor.putString("default_hrmax", str_hrmax);
		editor.commit();
		
		//sprawdzamy czy tętno spoczynkowe nie jest wyższe od tętna maksymalnego
		if(rhr >= hrmax){
			throw new Exception(context.getResources().getString(R.string.rhr_wieksze_od_hrmax));
		}
		
		zakres_hr = hrmax - rhr;
	}
	
	private int getHRPL(int hrmax_in_procent){
		double hr = hrmax * (hrmax_in_procent * 0.01);
		int wynik = (int) hr;
		return wynik;
	}
	
	private int getHR(int hrmax_in_procent){
		double hr = (zakres_hr * (hrmax_in_procent * 0.01)) + rhr;
		int wynik = (int) Math.round(hr);
		return wynik;
	}
	
	public void setTabelkaPolskaSzkolaTreningu(View v){
		//BC1
		setWierszTabelkiPolskiejSzkolyTreningu(v, 1, 65, 79);
		//BC2
		setWierszTabelkiPolskiejSzkolyTreningu(v, 2, 75, 85);
		//BC3
		setWierszTabelkiPolskiejSzkolyTreningu(v, 3, 85, 95);
	}
	
	private void setWierszTabelkiPolskiejSzkolyTreningu(View v, int nr, int dolny_zakres_tetna, int gorny_zakres_tetna){
		//strefa
		int bc_nazwa = v.getResources().getIdentifier("bc" + nr + "_nazwa", "id", context.getPackageName());
		TextView tv_bc_nazwa = (TextView) v.findViewById(bc_nazwa);
		tv_bc_nazwa.setText("BC" + nr);
		
		//HRmax (%)
		int bc_zakres_tetna_w_proc = v.getResources().getIdentifier("bc" + nr + "_zakres_tetna_w_procentach", "id", context.getPackageName());
		TextView tv_bc_zakres_tetna_w_proc = (TextView) v.findViewById(bc_zakres_tetna_w_proc);
		tv_bc_zakres_tetna_w_proc.setText(dolny_zakres_tetna + "\n" + gorny_zakres_tetna);
		
		//obliczamy tetno
		int bc_zakres_tetna = v.getResources().getIdentifier("bc" + nr + "_zakres_tetna", "id", context.getPackageName());
		TextView tv_bc_zakres_tetna = (TextView) v.findViewById(bc_zakres_tetna);
		tv_bc_zakres_tetna.setText(getHRPL(dolny_zakres_tetna) + "\n" + getHRPL(gorny_zakres_tetna));
	}
	
	public void setTabelkaKarvonen(View v){
		//Strefa 1
		setWierszTabelkiKarvonena(v, 1, 50, 60);
		//Strefa 2
		setWierszTabelkiKarvonena(v, 2, 60, 70);
		//Strefa 3
		setWierszTabelkiKarvonena(v, 3, 70, 80);
		//Strefa 4
		setWierszTabelkiKarvonena(v, 4, 80, 90);
		//Strefa 5
		setWierszTabelkiKarvonena(v, 5, 90, 100);
	}
	
	private void setWierszTabelkiKarvonena(View v, int nr, int dolny_zakres_tetna, int gorny_zakres_tetna){
		//strefa
		int strefa_nazwa = v.getResources().getIdentifier("strefa" + nr + "_nazwa", "id", context.getPackageName());
		TextView tv_strefa_nazwa = (TextView) v.findViewById(strefa_nazwa);
		tv_strefa_nazwa.setText("" + nr);
		
		//HRmax (%)
		int strefa_zakres_tetna_w_proc = v.getResources().getIdentifier("strefa" + nr + "_zakres_tetna_w_procentach", "id", context.getPackageName());
		TextView tv_strefa_zakres_tetna_w_proc = (TextView) v.findViewById(strefa_zakres_tetna_w_proc);
		tv_strefa_zakres_tetna_w_proc.setText(dolny_zakres_tetna + "\n" + gorny_zakres_tetna);
		
		//obliczamy tetno
		int strefa_zakres_tetna = v.getResources().getIdentifier("strefa" + nr + "_zakres_tetna", "id", context.getPackageName());
		TextView tv_strefa_zakres_tetna = (TextView) v.findViewById(strefa_zakres_tetna);
		tv_strefa_zakres_tetna.setText(getHR(dolny_zakres_tetna) + "\n" + getHR(gorny_zakres_tetna));
	}
}

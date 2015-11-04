package pl.kruzar.calc4runner;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TextView;

public class BMI {
	private Context context;
	private boolean kobieta = true;
	private boolean mezczyzna = false;
	private double masa;
	private int wzrost;
	
	private double wynik;
	
	//zakres bmi dla poszczególnych stanów
	private double wyniszczenie_bmi_min = 0;
	private double wyniszczenie_bmi_max = 14.99;
	private double wychudzenie_bmi_min = 15;
	private double wychudzenie_bmi_max = 17.49;
	private double niedowaga_bmi_min = 17.5;
	private double niedowaga_bmi_max = 19.49;
	private double waga_prawidlowa_bmi_min = 19.5;
	private double waga_prawidlowa_bmi_max = 24.99;
	private double nadwaga_bmi_min = 25;
	private double nadwaga_bmi_max = 29.99;
	private double pierwszy_st_otylosci_bmi_min = 30;
	private double pierwszy_st_otylosci_bmi_max = 34.99;
	private double drugi_st_otylosci_bmi_min = 35;
	private double drugi_st_otylosci_bmi_max = 39.99;
	private double otylosc_kliniczna_bmi_min = 40;
	private double otylosc_kliniczna_bmi_max = 100;
	
	private double bmi_min = 0;
	private double bmi_max = 0;
	
	public BMI(Context c, RadioButton rb_kobieta, RadioButton rb_mezczyzna, EditText et_masa, EditText et_wzrost){
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
			wzrost = Integer.parseInt(et_wzrost.getText().toString());
		} catch (NumberFormatException e) {
			wzrost = 0;
		}
		
		//obliczamy BMI
		obliczBMI();
	}
	
	private Double obliczBMI(){
		wynik = 0;
		
		//wzrost w metrach
		double wzrost_w_m = wzrost * 0.01;
		
		wynik = masa / (wzrost_w_m * wzrost_w_m);
		
		//zaokrąglamy wynik do dwóch miejsc po przecinku
		double f = Math.pow(10, 2);
		wynik = ((int)(wynik*f))/f;
		
		return wynik;
	}
	
	private Double obliczWage(double bmi){
		double waga = 0;
		
		//wzrost w metrach
		double wzrost_w_m = wzrost * 0.01;
		
		waga = bmi * (wzrost_w_m * wzrost_w_m);
		
		//zaokrąglamy wynik do jednego miejsca po przecinku
		double f = Math.pow(10, 1);
		waga = ((int)(waga*f))/f;
		
		return waga;
	}
	
	public String getBMI(){
		String twoje_bmi = context.getResources().getString(R.string.twoje_bmi);
		String bmi_wynik = Double.toString(wynik);
		String bmi = twoje_bmi + " " + bmi_wynik;
		
		return bmi;
	}
	
	
	public String getOcenaBMI(){
		String ocena;
		if(wynik < 15){
			ocena = context.getResources().getString(R.string.bmi_wyniszczenie);
		} else if (wynik < 17.5){
			ocena = context.getResources().getString(R.string.bmi_wychudzenie);
		} else if (wynik < 19.5){
			ocena = context.getResources().getString(R.string.bmi_niedowaga);
		} else if (wynik < 25){
			ocena = context.getResources().getString(R.string.bmi_waga_prawidlowa);
		} else if (wynik < 30){
			ocena = context.getResources().getString(R.string.bmi_nadwaga);
		} else if (wynik < 35){
			ocena = context.getResources().getString(R.string.bmi_pierwszy_st_otylosci);
		} else if (wynik < 40){
			ocena = context.getResources().getString(R.string.bmi_drugi_st_otylosci);
		} else {
			ocena = context.getResources().getString(R.string.bmi_otylosc_kliniczna);
		}
		
		return ocena;
	}
	
	public String getOptymalneBMIDlaPlci(){
		String str_optymalne_bmi;
		Plec p;
		
		if(kobieta){
			str_optymalne_bmi = context.getResources().getString(R.string.bmi_optymalne_bmi_dla_kobiet);
			p = new Kobieta();
		} else if(mezczyzna) {
			p = new Mezczyzna();
			str_optymalne_bmi = context.getResources().getString(R.string.bmi_optymalne_bmi_dla_mezczyzn);
		} else {
			p = new Mezczyzna();
			str_optymalne_bmi = context.getResources().getString(R.string.bmi_optymalne_bmi_dla_mezczyzn);
		}
		
		//optymalne BMI dla płci
		double optymalne_min = p.getOptymalneBMIMin();
		double optymalne_max = p.getOptymalneBMIMax();
		String str_zakres_optymalnego_bmi = Double.toString(optymalne_min) + " - " + Double.toString(optymalne_max); 
		
		//optymalna waga dla płci (uwzględniając dane wypisane w formularzu)
		double optymalna_waga_min = obliczWage(optymalne_min);
		double optymalna_waga_max = obliczWage(optymalne_max);
		String str_zakres_optymalnegj_wagi = Double.toString(optymalna_waga_min) + "kg - " + Double.toString(optymalna_waga_max) + "kg";
		
		return str_optymalne_bmi + ":\n " + str_zakres_optymalnego_bmi + " (" + str_zakres_optymalnegj_wagi + ")";
	}
	
	public void setTabelka(TableLayout t){
		t.setVisibility(View.VISIBLE);
		
		List<String> arStan = new ArrayList<String>();
		arStan.add("wyniszczenie");
		arStan.add("wychudzenie");
		arStan.add("niedowaga");
		arStan.add("waga_prawidlowa");
		arStan.add("nadwaga");
		arStan.add("pierwszy_st_otylosci");
		arStan.add("drugi_st_otylosci");
		arStan.add("otylosc_kliniczna");
		
		for(String stan : arStan){
			/* BMI */
			//ustawiamy min i max bmi dla określonego stanu
			setMinIMaxBMI(stan);
			
			/* zakres BMI */
			//wyciągamy id
			int id_bmi = t.getResources().getIdentifier(stan + "_zakres_bmi", "id", context.getPackageName());
			//wyciągamy odpowiedni TextView
			TextView zakres_bmi = (TextView)t.findViewById(id_bmi);
			setZakresBMI(zakres_bmi, bmi_min, bmi_max);
			
			/* zakres mas */
			//wyciągamy id
			int id_mas = t.getResources().getIdentifier(stan + "_zakres_mas", "id", context.getPackageName());
			//wyciągamy odpowiedni TextView
			TextView zakres_mas = (TextView)t.findViewById(id_mas);
			setZakresMas(zakres_mas, bmi_min, bmi_max);
		}
		
		
		//poprzednio zamiast pętli było coś takiego: (zostawiam to sobie na pamiątkę) ;)
		
		//zakresy bmi
		//TextView wyniszczenie_zakres_bmi = (TextView)t.findViewById(R.id.wyniszczenie_zakres_bmi);
		//TextView wychudzenie_zakres_bmi = (TextView)t.findViewById(R.id.wychudzenie_zakres_bmi);
		//TextView niedowaga_zakres_bmi = (TextView)t.findViewById(R.id.niedowaga_zakres_bmi);
		//TextView waga_prawidlowa_zakres_bmi = (TextView)t.findViewById(R.id.waga_prawidlowa_zakres_bmi);
		//TextView nadwaga_zakres_bmi = (TextView)t.findViewById(R.id.nadwaga_zakres_bmi);
		//TextView pierwszy_st_otylosci_zakres_bmi = (TextView)t.findViewById(R.id.pierwszy_st_otylosci_zakres_bmi);
		//TextView drugi_st_otylosci_zakres_bmi = (TextView)t.findViewById(R.id.drugi_st_otylosci_zakres_bmi);
		//TextView otylosc_kliniczna_zakres_bmi = (TextView)t.findViewById(R.id.otylosc_kliniczna_zakres_bmi);
		
		//setZakresBMI(wyniszczenie_zakres_bmi, wyniszczenie_bmi_min, wyniszczenie_bmi_max);
		//setZakresBMI(wychudzenie_zakres_bmi, wychudzenie_bmi_min, wychudzenie_bmi_max);
		//setZakresBMI(niedowaga_zakres_bmi, niedowaga_bmi_min, niedowaga_bmi_max);
		//setZakresBMI(waga_prawidlowa_zakres_bmi, waga_prawidlowa_bmi_min, waga_prawidlowa_bmi_max);
		//setZakresBMI(nadwaga_zakres_bmi, nadwaga_bmi_min, nadwaga_bmi_max);
		//setZakresBMI(pierwszy_st_otylosci_zakres_bmi, pierwszy_st_otylosci_bmi_min, pierwszy_st_otylosci_bmi_max);
		//setZakresBMI(drugi_st_otylosci_zakres_bmi, drugi_st_otylosci_bmi_min, drugi_st_otylosci_bmi_max);
		//setZakresBMI(otylosc_kliniczna_zakres_bmi, otylosc_kliniczna_bmi_min, otylosc_kliniczna_bmi_max);
		
		//zakresy mas
		//TextView wyniszczenie_zakres_mas = (TextView)t.findViewById(R.id.wyniszczenie_zakres_mas);
		//TextView wychudzenie_zakres_mas = (TextView)t.findViewById(R.id.wychudzenie_zakres_mas);
		//TextView niedowaga_zakres_mas = (TextView)t.findViewById(R.id.niedowaga_zakres_mas);
		//TextView waga_prawidlowa_zakres_mas = (TextView)t.findViewById(R.id.waga_prawidlowa_zakres_mas);
		//TextView nadwaga_zakres_mas = (TextView)t.findViewById(R.id.nadwaga_zakres_mas);
		//TextView pierwszy_st_otylosci_zakres_mas = (TextView)t.findViewById(R.id.pierwszy_st_otylosci_zakres_mas);
		//TextView drugi_st_otylosci_zakres_mas = (TextView)t.findViewById(R.id.drugi_st_otylosci_zakres_mas);
		//TextView otylosc_kliniczna_zakres_mas = (TextView)t.findViewById(R.id.otylosc_kliniczna_zakres_mas);		
		
		//setZakresMas(wyniszczenie_zakres_mas, wyniszczenie_bmi_min, wyniszczenie_bmi_max);
		//setZakresMas(wychudzenie_zakres_mas, wychudzenie_bmi_min, wychudzenie_bmi_max);
		//setZakresMas(niedowaga_zakres_mas, niedowaga_bmi_min, niedowaga_bmi_max);
		//setZakresMas(waga_prawidlowa_zakres_mas, waga_prawidlowa_bmi_min, waga_prawidlowa_bmi_max);
		//setZakresMas(nadwaga_zakres_mas, nadwaga_bmi_min, nadwaga_bmi_max);
		//setZakresMas(pierwszy_st_otylosci_zakres_mas, pierwszy_st_otylosci_bmi_min, pierwszy_st_otylosci_bmi_max);
		//setZakresMas(drugi_st_otylosci_zakres_mas, drugi_st_otylosci_bmi_min, drugi_st_otylosci_bmi_max);
		//setZakresMas(otylosc_kliniczna_zakres_mas, otylosc_kliniczna_bmi_min, otylosc_kliniczna_bmi_max);

	}
	
	private void setZakresBMI(TextView tv, double min, double max){
		//zaokrąglamy wynik do jednego miejsca po przecinku
		double f = Math.pow(10, 1);
		min = ((int)(min*f))/f;
		max = ((int)(max*f))/f;
		
		tv.setText(Double.toString(min) + " - " + Double.toString(max));
	}
	
	private void setZakresMas(TextView tv, double min, double max){
		double waga_min = obliczWage(min);
		double waga_max = obliczWage(max);
		tv.setText(Double.toString(waga_min) + " - " + Double.toString(waga_max));
	}
	
	private void setMinIMaxBMI(String stan){
		if(stan.equals("wyniszczenie")){
			bmi_min = wyniszczenie_bmi_min;
			bmi_max = wyniszczenie_bmi_max;
		} else if(stan.equals("wychudzenie")){
			bmi_min = wychudzenie_bmi_min;
			bmi_max = wychudzenie_bmi_max;
		} else if(stan.equals("niedowaga")){
			bmi_min = niedowaga_bmi_min;
			bmi_max = niedowaga_bmi_max;
		} else if(stan.equals("waga_prawidlowa")){
			bmi_min = waga_prawidlowa_bmi_min;
			bmi_max = waga_prawidlowa_bmi_max;
		} else if(stan.equals("nadwaga")){
			bmi_min = nadwaga_bmi_min;
			bmi_max = nadwaga_bmi_max;
		} else if(stan.equals("pierwszy_st_otylosci")){
			bmi_min = pierwszy_st_otylosci_bmi_min;
			bmi_max = pierwszy_st_otylosci_bmi_max;
		} else if(stan.equals("drugi_st_otylosci")){
			bmi_min = drugi_st_otylosci_bmi_min;
			bmi_max = drugi_st_otylosci_bmi_max;
		} else if(stan.equals("otylosc_kliniczna")){
			bmi_min = otylosc_kliniczna_bmi_min;
			bmi_max = otylosc_kliniczna_bmi_max;
		}
	}
}

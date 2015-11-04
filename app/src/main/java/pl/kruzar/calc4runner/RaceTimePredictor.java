package pl.kruzar.calc4runner;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class RaceTimePredictor {
	private Context context;
	private boolean mezczyzna = true;
	private double wspolczynnik;
	private double dystans;
	private String czas;
	private String[] ar_dystanse;
	
	public RaceTimePredictor(Context c, RadioButton rb_m, Spinner dystans_s, EditText czas_et) throws Exception {
		context = c;
		
		if(rb_m.isChecked() == false){
			mezczyzna = false;
		}
		
		try {
			dystans = Double.parseDouble(dystans_s.getSelectedItem().toString());
		} catch (NumberFormatException e) {
			throw new NumberFormatException(context.getResources().getString(R.string.niewlasciwy_format_dystansu));
		}
		czas = czas_et.getText().toString();
		
		//sprawdzamy na starcie czy czas ma dobry format
		if(Walidacja.sprawdzPoprawnoscFormatuCzasu(czas) == false){
			throw new Exception(context.getResources().getString(R.string.niewlasciwy_format_czasu));
		}
		
		//ustawiamy współczynnik
		if(mezczyzna){
			wspolczynnik = 1.0642;
		} else {
			wspolczynnik = 1.0459;
		}
		
		//przypisujemy dystanse
		ar_dystanse = context.getResources().getStringArray(R.array.ar_dystans);
	}
	
	public void setTabelka(TableLayout t) throws Exception{
		List<String> list_czas = new ArrayList<String>();
		try{
			list_czas = Convert.pobierzGodzineMinuteSekundeZCzasu(czas);
		} catch (Exception e) {
			throw new Exception(context.getResources().getString(R.string.niewlasciwy_format_czasu));
		}
		
		double h = Double.parseDouble(list_czas.get(0));
		double m = Double.parseDouble(list_czas.get(1));
		double s = Double.parseDouble(list_czas.get(2));
		
		double dzien = Convert.czas2dzien(h, m, s);
		
		for (String d : ar_dystanse) {
			double wybrany_dystans = Double.parseDouble(d);
			
			//wyciągamy pole dystans
			int id_dyst = t.getResources().getIdentifier("rtp_tabelka_dystans_" + d, "id", context.getPackageName());
			TextView tv_dyst = (TextView)t.findViewById(id_dyst);
			
			//czas
			String str_czas_na_wybranym_dystansie;
			double czas_na_wybranym_dystansie = dzien * Math.pow((wybrany_dystans / dystans), wspolczynnik);
			if(wybrany_dystans == dystans){
				str_czas_na_wybranym_dystansie = czas;
			} else {
				str_czas_na_wybranym_dystansie = Convert.dzien2czas(czas_na_wybranym_dystansie);
			}
			//wyciągamy pole czas
			int id_czas = t.getResources().getIdentifier("rtp_tabelka_czas_" + d, "id", context.getPackageName());
			TextView tv_czas = (TextView)t.findViewById(id_czas);
			//wstawiamy czas
			tv_czas.setText(str_czas_na_wybranym_dystansie);
			
			//tempo
			double tempo_na_wybranym_dystansie = (czas_na_wybranym_dystansie * 1000) / wybrany_dystans;
			String str_tempo_na_wybranym_dystansie = Convert.dzien2tempo(tempo_na_wybranym_dystansie);
			//wyciągamy pole tempo
			int id_tempo = t.getResources().getIdentifier("rtp_tabelka_tempo_" + d, "id", context.getPackageName());
			TextView tv_tempo = (TextView)t.findViewById(id_tempo);
			//wstawiamy tempo
			tv_tempo.setText(str_tempo_na_wybranym_dystansie);
			
			//wytłuszamy wybrany wiersz i kolorujemy na zielono
			if(wybrany_dystans == dystans){
				//usawiamy bold na wierszu tabeli
				tv_dyst.setTypeface(null, Typeface.BOLD);
				tv_czas.setTypeface(null, Typeface.BOLD);
				tv_tempo.setTypeface(null, Typeface.BOLD);
				
				//ustawiamy kolor
				tv_dyst.setTextColor(context.getResources().getColor(R.color.zielony));
				tv_czas.setTextColor(context.getResources().getColor(R.color.zielony));
				tv_tempo.setTextColor(context.getResources().getColor(R.color.zielony));
			} else {
				//usawiamy normal na wierszu tabeli
				tv_dyst.setTypeface(null, Typeface.NORMAL);
				tv_czas.setTypeface(null, Typeface.NORMAL);
				tv_tempo.setTypeface(null, Typeface.NORMAL);
				
				//ustawiamy kolor na normalny
				tv_dyst.setTextColor(Color.LTGRAY);
				tv_czas.setTextColor(Color.LTGRAY);
				tv_tempo.setTextColor(Color.LTGRAY);
			}
		}	
		
		//pokazujemy tabelkę
		t.setVisibility(View.VISIBLE);
	}
}

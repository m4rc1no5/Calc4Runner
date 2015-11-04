package pl.kruzar.calc4runner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

public class BMIActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//widok
		setContentView(R.layout.bmi);
		
		//wyciągamy tytuł
		String str_title = getResources().getString(R.string.bmi_calc_menu_name);
		//ustawiamy tytuł
		Tools.setTitle(this, str_title);
		
		//ustawiamy domyślne wartości
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		//płeć
		RadioButton rb_k = (RadioButton) findViewById(R.id.bmi_radio_plec_k);
		RadioButton rb_m = (RadioButton) findViewById(R.id.bmi_radio_plec_m);
		String plec = prefs.getString("default_plec", "man");
		//jesli plec == man to zaznaczamy faceta i odznaczamy kobietke
		if(plec.equals("man")){
			rb_k.setChecked(false);
			rb_m.setChecked(true);
		} else {
			rb_k.setChecked(true);
			rb_m.setChecked(false);
		}
		
		//maly alert:
		//Toast.makeText(this, plec, Toast.LENGTH_SHORT).show();
		
		//masa
		EditText m = (EditText) findViewById(R.id.bmi_editText_masa);
		String masa = prefs.getString("default_masa", "70");
		m.setText(masa);
		//wzrost
		EditText w = (EditText) findViewById(R.id.bmi_editText_wzrost);
		String wzrost = prefs.getString("default_wzrost", "175");
		w.setText(wzrost);
		
		//button obliczający bmi
		Button obliczBtn = (Button) findViewById(R.id.bmi_btn_oblicz);
		obliczBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//pobieramy dane z formularza
				RadioButton rb_kobieta = (RadioButton) findViewById(R.id.bmi_radio_plec_k);
				RadioButton rb_mezczyzna = (RadioButton) findViewById(R.id.bmi_radio_plec_m);
				EditText masa = (EditText) findViewById(R.id.bmi_editText_masa);
				EditText wzrost = (EditText) findViewById(R.id.bmi_editText_wzrost);
				Context context = getApplicationContext();
				
				BMI bmi = new BMI(context, rb_kobieta, rb_mezczyzna, masa, wzrost);
				
				//pobieramy BMI
				String strWynik = bmi.getBMI();
				
				//wyświetlamy BMI w formie aleru
				//Toast.makeText(context, strWynik, Toast.LENGTH_SHORT).show();
				
				//definiujemy widok, który zostanie wrzucony do głównego widoku
				LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View v_wynik = vi.inflate(R.layout.bmi_wynik, null);
				
				//ustalamy miejsce w których chcemy "wstrzyknąć" widok
				View insertPoint = findViewById(R.id.bmi_wynik);
				//kasujemy poprzednie wyniki - jeśli istnieją - bez tej opcji
				//po każdym kliknięciu pojawiały się nowe tabelki na dole - trzeba czyścić widok
				((ViewGroup) insertPoint).removeAllViewsInLayout();
				//wrzucamy widok do widoku :)
				((ViewGroup) insertPoint).addView(v_wynik, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
				
				//wyświetlamy wynik pod formularzem
				TextView pole_na_wynik = (TextView) findViewById(R.id.bmi_txt_wynik);
				pole_na_wynik.setText(strWynik);
				
				//wyświetlamy ocenę pod wynikiem
				String strOcena = bmi.getOcenaBMI();
				TextView pole_na_ocene = (TextView) findViewById(R.id.bmi_txt_ocena);
				pole_na_ocene.setText(strOcena);
				if(strOcena.equals(getResources().getString(R.string.bmi_waga_prawidlowa))){
					pole_na_ocene.setTextColor(getResources().getColor(R.color.zielony));
				} else {
					pole_na_ocene.setTextColor(getResources().getColor(R.color.czerwony));
				}
				
				//wyświetlamy optymalne BMI dla płci pod oceną
				String strOptymalneBMI = bmi.getOptymalneBMIDlaPlci();
				TextView pole_na_optymalne_bmi = (TextView) findViewById(R.id.bmi_optymalne_dla_plci);
				pole_na_optymalne_bmi.setText(strOptymalneBMI);
				
				//wyświetlenie napisu nad tabelką
				TextView pole_mozliwe_wyniki = (TextView) findViewById(R.id.bmi_mozliwe_wyniki);
				pole_mozliwe_wyniki.setText(R.string.bmi_mozliwe_wyniki);
				
				//wyświetlenie tabelki z wynikami
				TableLayout t = (TableLayout) findViewById(R.id.bmi_tabelka);
				bmi.setTabelka(t);

				
				//chowamy soft keyboard
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(pole_na_ocene.getWindowToken(), 0);
				
				//scrollujemy na dół
				ScrollView sv = (ScrollView)findViewById(R.id.scroll_bmi);
                sv.scrollTo(0, sv.getBottom());
				//sv.scrollTo(0, pole_na_wynik.getBottom());
			}
		});
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_wybranego_calc, menu);
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {        
        switch(item.getItemId()) {
            case R.id.menu_pomoc:
            	Intent i = new Intent(this, PomocWybranegoCalcActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("calc_rodzaj", "bmi");
            	i.putExtras(bundle);
            	startActivity(i);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
}

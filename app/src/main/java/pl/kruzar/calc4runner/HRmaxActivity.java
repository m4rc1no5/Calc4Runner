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

public class HRmaxActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//widok
		setContentView(R.layout.hrmax);
		
		//wyciągamy tytuł
		String str_title = getResources().getString(R.string.hrmax_calc_menu_name);
		//ustawiamy tytuł
		Tools.setTitle(this, str_title);
		
		//ustawiamy domyślne wartości
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		//płeć
		RadioButton rb_k = (RadioButton) findViewById(R.id.hrmax_radio_plec_k);
		RadioButton rb_m = (RadioButton) findViewById(R.id.hrmax_radio_plec_m);
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
		EditText m = (EditText) findViewById(R.id.hrmax_editText_masa);
		String masa = prefs.getString("default_masa", "70");
		m.setText(masa);
		//wiek
		EditText w = (EditText) findViewById(R.id.hrmax_editText_wiek);
		String wiek = prefs.getString("default_wiek", "30");
		w.setText(wiek);
		
		//button obliczający hrmax
		Button obliczBtn = (Button) findViewById(R.id.hrmax_btn_oblicz);
		obliczBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//pobieramy dane z formularza
				RadioButton rb_kobieta = (RadioButton) findViewById(R.id.hrmax_radio_plec_k);
				RadioButton rb_mezczyzna = (RadioButton) findViewById(R.id.hrmax_radio_plec_m);
				EditText masa = (EditText) findViewById(R.id.hrmax_editText_masa);
				EditText wiek = (EditText) findViewById(R.id.hrmax_editText_wiek);
				Context context = getApplicationContext();
				
				HRmax hrmax = new HRmax(context, rb_kobieta, rb_mezczyzna, masa, wiek);
				
				//definiujemy widok, który zostanie wrzucony do głównego widoku
				LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View v_wynik = vi.inflate(R.layout.hrmax_wynik, null);
				
				//ustalamy miejsce w których chcemy "wstrzyknąć" widok
				View insertPoint = findViewById(R.id.hrmax_wynik);
				//kasujemy poprzednie wyniki - jeśli istnieją - bez tej opcji
				//po każdym kliknięciu pojawiały się nowe tabelki na dole - trzeba czyścić widok
				((ViewGroup) insertPoint).removeAllViewsInLayout();
				//wrzucamy widok do widoku :)
				((ViewGroup) insertPoint).addView(v_wynik, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
				
				//wyświetlenie tekstu o przedziale wyników
				TextView tv_wynik = (TextView) findViewById(R.id.hrmax_tv_wynik);
				String str_wynik = (String) getString(R.string.hrmax_wynik);
				tv_wynik.setText(str_wynik);
				
				//wyświetlenie przedziału wyników
				TextView tv_wynik_przedzial = (TextView) findViewById(R.id.hrmax_tv_wynik_przedzial);
				tv_wynik_przedzial.setText(hrmax.getWynikPrzedzialHRmax());
				
				//wyświetlenie tekstu nad tabelką
				TextView pole_szczegolowe_wyniki = (TextView) findViewById(R.id.hrmax_szczegolowe_wyniki);
				pole_szczegolowe_wyniki.setText(R.string.hrmax_szczegolowe_wyniki);
				
				//wyświetlenie tabelki z wynikami
				TableLayout t = (TableLayout) findViewById(R.id.hrmax_tabelka);
				hrmax.setTabelka(t);
				
				//chowamy soft keyboard
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(t.getWindowToken(), 0);
				
				//scrollujemy na dół
				ScrollView sv = (ScrollView)findViewById(R.id.scroll_hrmax);
                sv.scrollTo(0, sv.getBottom());
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
            	bundle.putString("calc_rodzaj", "hrmax");
            	i.putExtras(bundle);
            	startActivity(i);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
}

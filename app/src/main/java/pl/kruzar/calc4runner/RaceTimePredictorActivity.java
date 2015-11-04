package pl.kruzar.calc4runner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RaceTimePredictorActivity extends Activity {
	String a;
	int keyDel;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//widok
		setContentView(R.layout.race_time_predictor);
		
		//wyciągamy tytuł
		String str_title = getResources().getString(R.string.rtp_calc_menu_name);
		//ustawiamy tytuł
		Tools.setTitle(this, str_title);
		
		//pamięć
		SharedPreferences moja_pamiec = getSharedPreferences("moja_pamiec",0);
		
		//domyślne wartości - z ustawień
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		//pole: Płeć
		final RadioButton rb_k = (RadioButton) findViewById(R.id.rtp_radio_plec_k);
		final RadioButton rb_m = (RadioButton) findViewById(R.id.rtp_radio_plec_m);
		String plec = prefs.getString("default_plec", "man");
		//jesli plec == man to zaznaczamy faceta i odznaczamy kobietke
		if(plec.equals("man")){
			rb_k.setChecked(false);
			rb_m.setChecked(true);
		} else {
			rb_k.setChecked(true);
			rb_m.setChecked(false);
		}
		
		//pole: Dynstans
		//wyciągamy ostatnią wybraną opcję z pamięci
		String rtp_dystans_last_value = moja_pamiec.getString("rtp_dystans_last_value", "10000");
		//wypełnianie selecta dystansami (spinnera)
		final Spinner dystans_s = (Spinner) findViewById(R.id.rtp_spinner_dystans);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ar_dystans, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    dystans_s.setAdapter(adapter);
	    //wyciągamy pozycję spinnera jaka jest przypisana do ostatniej wartości
	    int pozycja = adapter.getPosition(rtp_dystans_last_value);
		dystans_s.setSelection(pozycja);
	    
		//pole: Czas
		final EditText czas_et = (EditText) findViewById(R.id.rtp_editText_czas);
		
		//wypełniamy pole ostatnią wpisaną wartością - lub wartością defaultową - jeśli wcześniej nic nie było wpisane
		String rtp_czas_last_value = moja_pamiec.getString("rtp_czas_last_value", "00:45:00");
		czas_et.setText(rtp_czas_last_value);
		//ustawiamy kursos na końcu (z prawej strony wpisanego w edittext tekstu)
		czas_et.setSelection(rtp_czas_last_value.length());
		
		//formatowanie pola czas - tak żeby tworzyło się coś w stylu hh:mm:ss
		czas_et.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				boolean flag = true;
                String eachBlock[] = czas_et.getText().toString().split(":");
                for (int i = 0; i < eachBlock.length; i++) {
                    if (eachBlock[i].length() > 2) {
                        flag = false;
                    }
                }
                if (flag) {
                    czas_et.setOnKeyListener(new OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {

                            if (keyCode == KeyEvent.KEYCODE_DEL)
                                keyDel = 1;
                            return false;
                        }
                    });

                    if (keyDel == 0) {
                        if (((czas_et.getText().length() + 1) % 3) == 0) {

                            if (czas_et.getText().toString().split(":").length <= 2) {
                                czas_et.setText(czas_et.getText() + ":");
                                czas_et.setSelection(czas_et.getText().length());
                            }
                        }
                        a = czas_et.getText().toString();
                    } else {
                        a = czas_et.getText().toString();
                        keyDel = 0;
                    }
                } else {
                    czas_et.setText(a);
                }
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//pole: Button Oblicz
		Button oblicz_btn = (Button) findViewById(R.id.rtp_btn_oblicz);
		oblicz_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Context context = getApplicationContext();
				
				RaceTimePredictor rtp;
				//pobieramy dane z selecta
				//Toast.makeText(context, czas_et.getText(), Toast.LENGTH_SHORT).show();
				//Toast.makeText(context, dystans_s.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
				
				try{
					rtp = new RaceTimePredictor(context, rb_m, dystans_s, czas_et);
				} catch (Exception e) {
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
					return;
				}
				
				//definiujemy widok, który zostanie wrzucony do głównego widoku
				LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View v_wynik = vi.inflate(R.layout.race_time_predictor_wynik, null);
				
				//ustalamy miejsce w których chcemy "wstrzyknąć" widok
				View insertPoint = findViewById(R.id.race_time_predictor_wynik);
				//kasujemy poprzednie wyniki - jeśli istnieją - bez tej opcji
				//po każdym kliknięciu pojawiały się nowe tabelki na dole - trzeba czyścić widok
				((ViewGroup) insertPoint).removeAllViewsInLayout();
				//wrzucamy widok do widoku :)
				((ViewGroup) insertPoint).addView(v_wynik, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
				
				//wyświetlenie napisu nad tabelką
				TextView pole_prognozowane_czasy = (TextView) findViewById(R.id.rtp_tv_prognozowane_czasy);
				pole_prognozowane_czasy.setText(R.string.rtp_prognozowane_czasy);
				
				//wyświetlenie tabelki z prognozami
				TableLayout t = (TableLayout) findViewById(R.id.rtp_tabelka);
				try{
					rtp.setTabelka(t);
				} catch (Exception e) {
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
					return;
				}
				
				
				//zapisujemy dystans i czas do pamięci
				SharedPreferences moja_pamiec = context.getSharedPreferences("moja_pamiec",0);
				SharedPreferences.Editor editor = moja_pamiec.edit();
				editor.putString("rtp_dystans_last_value", dystans_s.getSelectedItem().toString());
				editor.putString("rtp_czas_last_value", czas_et.getText().toString());
				editor.commit();
				
				//chowamy soft keyboard
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(czas_et.getWindowToken(), 0);
				
				//scrollujemy na dół
				ScrollView sv = (ScrollView)findViewById(R.id.rtp_scroll);
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
            	bundle.putString("calc_rodzaj", "rtp");
            	i.putExtras(bundle);
            	startActivity(i);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
}

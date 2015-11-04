package pl.kruzar.calc4runner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.Toast;

public class Pace2SpeedActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//widok:
		setContentView(R.layout.pace2speed);
		
		//wyciągamy tytuł
		String str_title = getResources().getString(R.string.p2s_calc_menu_name);
		//ustawiamy tytuł
		Tools.setTitle(this, str_title);
		
		EditText ilosc_minut = (EditText) findViewById(R.id.p2s_input_minuty);
		EditText ilosc_sekund = (EditText) findViewById(R.id.p2s_input_sekundy);
		
		SharedPreferences moja_pamiec = getSharedPreferences("moja_pamiec",0);
		String p2s_min_last_value = moja_pamiec.getString("p2s_min_last_value", "5");
		String p2s_sec_last_value = moja_pamiec.getString("p2s_sec_last_value", "15");
		
		ilosc_minut.setText(p2s_min_last_value);
		ilosc_sekund.setText(p2s_sec_last_value);
		
		//Tworzymy button:
		Button obliczBtn = (Button) findViewById(R.id.p2s_btn_oblicz);
		obliczBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//pobieramy dane z inputów (minuty i sekundy)
				EditText ilosc_minut = (EditText) findViewById(R.id.p2s_input_minuty);
				EditText ilosc_sekund = (EditText) findViewById(R.id.p2s_input_sekundy);
				Context context = getApplicationContext();
				
				SharedPreferences moja_pamiec = context.getSharedPreferences("moja_pamiec",0);
				SharedPreferences.Editor editor = moja_pamiec.edit();

				editor.putString("p2s_min_last_value", ilosc_minut.getText().toString());
				editor.putString("p2s_sec_last_value", ilosc_sekund.getText().toString());
				editor.commit();
				
				//obliczamy km/h
				try {
					Pace2Speed p2s = new Pace2Speed(context, ilosc_minut, ilosc_sekund);
					TableLayout t = (TableLayout) findViewById(R.id.p2s_tabelka);
					p2s.setTabelka(t);
					
				} catch (Exception e) {
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
				}
				
				//chowamy soft keyboard
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(ilosc_minut.getWindowToken(), 0);
				
				//scrollujemy na dół
				ScrollView sv = (ScrollView)findViewById(R.id.scroll_p2s);
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
            	bundle.putString("calc_rodzaj", "p2s");
            	i.putExtras(bundle);
            	startActivity(i);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
}

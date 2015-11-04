package pl.kruzar.calc4runner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TrainingZonesActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//widok
		setContentView(R.layout.training_zones);
		
		//wyciągamy tytuł
		String str_title = getResources().getString(R.string.tz_calc_menu_name);
		//ustawiamy tytuł
		Tools.setTitle(this, str_title);
		
		//pamięć
		SharedPreferences moja_pamiec = getSharedPreferences("moja_pamiec",0);
		
		//resting hr i hrmax z formularza
		final EditText et_rhr = (EditText) findViewById(R.id.tz_editText_resting_hr);
		final EditText et_hrmax = (EditText) findViewById(R.id.tz_editText_hrmax);
		
		//pobieramy domyślne wartości dla hrh i hrmax
		String str_default_rhr = moja_pamiec.getString("default_rhr", "70");
		String str_default_hrmax = moja_pamiec.getString("default_hrmax", "192");
		
		//wstawiamy wartości do pól
		et_rhr.setText(str_default_rhr);
		et_hrmax.setText(str_default_hrmax);
		
		//button pokazujący strefy treningowe
		Button obliczBtn = (Button) findViewById(R.id.tz_btn_oblicz);
		obliczBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Context context = getApplicationContext();
				
				TrainingZones tz;
				
				try {
					tz = new TrainingZones(context, et_rhr, et_hrmax);
				} catch (Exception e) {
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
					return;
				}
				
				//definiujemy widok, który zostanie wrzucony do głównego widoku
				LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View v_wynik = vi.inflate(R.layout.training_zones_wynik, null);
				
				tz.setTabelkaPolskaSzkolaTreningu(v_wynik);
				
				tz.setTabelkaKarvonen(v_wynik);
				
				//modyfikujemy widok
				//TextView textView = (TextView) v_wynik.findViewById(R.id.test);
				//textView.setText(hrmax.getText().toString());
				
				//ustalamy miejsce w których chcemy "wstrzyknąć" widok
				View insertPoint = findViewById(R.id.tz_wynik);
				//kasujemy poprzednie wyniki - jeśli istnieją - bez tej opcji
				//po każdym kliknięciu pojawiały się nowe tabelki na dole - trzeba czyścić widok
				((ViewGroup) insertPoint).removeAllViewsInLayout();
				//wrzucamy widok do widoku :)
				((ViewGroup) insertPoint).addView(v_wynik, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
				
				//chowamy soft keyboard
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(insertPoint.getWindowToken(), 0);
				
				//scrollujemy na dół
				//ScrollView sv = (ScrollView)findViewById(R.id.scroll_tz);
                //sv.scrollTo(0, sv.getBottom());
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
            	bundle.putString("calc_rodzaj", "tz");
            	i.putExtras(bundle);
            	startActivity(i);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
}

package pl.kruzar.calc4runner;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class PomocWybranegoCalcActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//nie wiem za bardzo co to robi... niestety nie skomentowałem tego w odpowiednim momencie ;-)
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
	                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		
		setContentView(R.layout.pomoc_wybranego_calc);
		
		//pobieramy przesłane do activity dane
		Bundle recdData = getIntent().getExtras();
		String calc_rodzaj = recdData.getString("calc_rodzaj");
		
		//inicjalizujemy zmienne określające nazwę kalkulatora oraz treść pomocy
		String calc_title = "";
		String calc_pomoc = "";
		
		//wyciągamy pola widoku, w które wstawimy nazwę i treść pomocy
		TextView tv_calc_title = (TextView) findViewById(R.id.calc_title);
		TextView tv_calc_text = (TextView) findViewById(R.id.calc_text);
		
		if(calc_rodzaj.equals("bmi")){
			calc_title = getResources().getString(R.string.bmi);
			calc_pomoc = getResources().getString(R.string.bmi_pomoc);
		} else if(calc_rodzaj.equals("hrmax")){
			calc_title = getResources().getString(R.string.hrmax);
			calc_pomoc = getResources().getString(R.string.hrmax_pomoc);
		} else if(calc_rodzaj.equals("p2s")){
			calc_title = getResources().getString(R.string.p2s_calc_menu_name);
			calc_pomoc = getResources().getString(R.string.p2s_pomoc);
		} else if(calc_rodzaj.equals("rtp")){
			calc_title = getResources().getString(R.string.rtp_calc_menu_name);
			calc_pomoc = getResources().getString(R.string.rtp_pomoc);
		} else if(calc_rodzaj.equals("tz")){
			calc_title = getResources().getString(R.string.tz_calc_menu_name);
			calc_pomoc = getResources().getString(R.string.tz_pomoc);
		}
		
		
		//uzupełniamy dane
		tv_calc_title.setText(calc_title);
		tv_calc_text.setText(calc_pomoc);
		
		//wyciągamy tytuł
		String str_title = getResources().getString(R.string.pomoc);
		//ustawiamy tytuł
		Tools.setTitle(this, str_title);
	}
}

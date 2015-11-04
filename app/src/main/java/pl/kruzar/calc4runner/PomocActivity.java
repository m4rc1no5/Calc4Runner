package pl.kruzar.calc4runner;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class PomocActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//nie wiem za bardzo co to robi... niestety nie skomentowałem tego w odpowiednim momencie ;-)
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
	                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		
		
		setContentView(R.layout.pomoc);
		
		//wyciągamy tytuł
		String str_title = getResources().getString(R.string.pomoc);
		//ustawiamy tytuł
		Tools.setTitle(this, str_title);
	}
}

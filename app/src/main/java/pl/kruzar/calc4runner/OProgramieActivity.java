package pl.kruzar.calc4runner;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.WindowManager;
import android.widget.TextView;

public class OProgramieActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
	                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
		 
		setContentView(R.layout.o_programie);
		
		//wyciągamy tytuł
		String str_title = getResources().getString(R.string.o_programie);
		//ustawiamy tytuł
		Tools.setTitle(this, str_title);
		
		TextView email = (TextView) findViewById(R.id.textView7);
		
		String email_adres = getResources().getString(R.string.app_autor_email);
		
		email.setText(Html.fromHtml("<a href=\"mailto:" + email_adres + "\">" + email_adres + "</a>"));
		email.setMovementMethod(LinkMovementMethod.getInstance());
	}
}

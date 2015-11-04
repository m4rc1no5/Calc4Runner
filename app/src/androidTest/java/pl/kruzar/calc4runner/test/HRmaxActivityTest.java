package pl.kruzar.calc4runner.test;

import pl.kruzar.calc4runner.HRmaxActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class HRmaxActivityTest extends ActivityInstrumentationTestCase2<HRmaxActivity> {
	private HRmaxActivity mActivity;
	
	//pole tekstowe
	private TextView tv_plec;
	private TextView tv_masa;
	private TextView tv_masa_jednostka;
	private TextView tv_wiek;
	private TextView tv_wiek_jednostka;
	
	//input
	private EditText et_masa;
	private EditText et_wiek;
	
	//radio
	private RadioButton rb_k;
	private RadioButton rb_m;
	
	//button
	private Button b_oblicz;
	
	//stringi
	private String str_plec;
	private String str_k;
	private String str_m;
	private String str_masa;
	private String str_masa_jednostka;
	private String str_wiek;
	private String str_wiek_jednostka;
	private String str_oblicz;
	
	public HRmaxActivityTest(){
		super("pl.kruzar.calc4runner", HRmaxActivity.class);
	}
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = this.getActivity();
        
        tv_plec = (TextView) mActivity.findViewById(pl.kruzar.calc4runner.R.id.hrmax_plec_txt);
        tv_masa = (TextView) mActivity.findViewById(pl.kruzar.calc4runner.R.id.hrmax_masa_txt);
        tv_masa_jednostka = (TextView) mActivity.findViewById(pl.kruzar.calc4runner.R.id.hrmax_masa_jednostka_txt);
        tv_wiek = (TextView) mActivity.findViewById(pl.kruzar.calc4runner.R.id.hrmax_wiek_txt);
        tv_wiek_jednostka = (TextView) mActivity.findViewById(pl.kruzar.calc4runner.R.id.hrmax_wiek_jednostka_txt);
        
        et_masa = (EditText) mActivity.findViewById(pl.kruzar.calc4runner.R.id.hrmax_editText_masa);
        et_wiek = (EditText) mActivity.findViewById(pl.kruzar.calc4runner.R.id.hrmax_editText_wiek);
        
        rb_k = (RadioButton) mActivity.findViewById(pl.kruzar.calc4runner.R.id.hrmax_radio_plec_k);
        rb_m = (RadioButton) mActivity.findViewById(pl.kruzar.calc4runner.R.id.hrmax_radio_plec_m);
        
        b_oblicz = (Button) mActivity.findViewById(pl.kruzar.calc4runner.R.id.hrmax_btn_oblicz);
        
        str_plec = mActivity.getString(pl.kruzar.calc4runner.R.string.plec);
        str_k = mActivity.getString(pl.kruzar.calc4runner.R.string.kobieta);
        str_m = mActivity.getString(pl.kruzar.calc4runner.R.string.mezczyzna);
        str_masa = mActivity.getString(pl.kruzar.calc4runner.R.string.masa);
        str_masa_jednostka = mActivity.getString(pl.kruzar.calc4runner.R.string.kg_jednostka);
        str_wiek = mActivity.getString(pl.kruzar.calc4runner.R.string.wiek);
        str_wiek_jednostka = mActivity.getString(pl.kruzar.calc4runner.R.string.lat_jednostka);
        str_oblicz = mActivity.getString(pl.kruzar.calc4runner.R.string.hrmax_oblicz);
    }
	
	/**
	 * Sprawdzamy czy mamy takie pola
	 */
	public void testPreconditions() {
		//pole tekstowe
		assertNotNull(tv_plec);
		assertNotNull(tv_masa);
		assertNotNull(tv_masa_jednostka);
		assertNotNull(tv_wiek);
		assertNotNull(tv_wiek_jednostka);
		
		//inputy
		assertNotNull(et_masa);
		assertNotNull(et_wiek);
		
		//radio
		assertNotNull(rb_k);
		assertNotNull(rb_m);
		
		//button
		assertNotNull(b_oblicz);
	}
	
	/**
	 * Sprawdzamy czy do pól przypisane są odpowiednie stringi
	 */
	public void testText() {
		assertEquals(str_plec,(String) tv_plec.getText());
		assertEquals(str_k,(String) rb_k.getText());
		assertEquals(str_m,(String) rb_m.getText());
		assertEquals(str_masa,(String) tv_masa.getText());
		assertEquals(str_masa_jednostka,(String) tv_masa_jednostka.getText());
		assertEquals(str_wiek,(String) tv_wiek.getText());
		assertEquals(str_wiek_jednostka,(String) tv_wiek_jednostka.getText());
		assertEquals(str_oblicz,(String) b_oblicz.getText());
	}
}

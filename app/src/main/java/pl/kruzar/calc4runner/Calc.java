package pl.kruzar.calc4runner;

import java.util.ArrayList;
import java.util.List;

public class Calc {
	private List<String> arNazwa = new ArrayList<String>();
	private List<String> arOpis = new ArrayList<String>();
	private List<String> arNazwaKlasy = new ArrayList<String>();
	
	public void setCalc(String nNazwa, String nOpis){
		arNazwa.add(nNazwa);
		arOpis.add(nOpis);
		//arNazwaKlasy.add(nNazwaKlasy);
	}
	
	public String getNazwa(int i){
		return arNazwa.get(i);
	}
	
	public String getOpis(int i){
		return arOpis.get(i);
	}
	
	public String getNazwaKlasy(int i){
		return arNazwaKlasy.get(i);
	}
	
	public Integer getIloscKalkulatorow() {
		return arNazwa.size();
	}
}

package pl.kruzar.calc4runner;

abstract class Plec {
	protected double bmi_optymalne_min = 0;
	protected double bmi_optymalne_max = 0;
	
	protected double getOptymalneBMIMin(){
		return bmi_optymalne_min;
	}
	
	protected double getOptymalneBMIMax(){
		return bmi_optymalne_max;
	}
}

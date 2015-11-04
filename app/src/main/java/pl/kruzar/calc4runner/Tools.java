package pl.kruzar.calc4runner;

import android.app.Activity;

public class Tools {
	public static void setTitle(Activity activity, String str_wlasny_tytul){
		activity.setTitle(activity.getTitle() + " - " + str_wlasny_tytul);
	}
}

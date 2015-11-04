package pl.kruzar.calc4runner;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Calc4RunnerActivity extends ListActivity {
	//private static final int PREFERENCES_ID = Menu.FIRST;
	
    /** Called when the activity is first created */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setListAdapter(new CalcListAdapter(this));
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        if(id == 0){
        	Intent i = new Intent(this, BMIActivity.class);
        	startActivity(i);
        } else if (id == 1) {
        	Intent i = new Intent(this, HRmaxActivity.class);
        	startActivity(i);
        } else if (id == 2) {
        	Intent i = new Intent(this, RaceTimePredictorActivity.class);
        	startActivity(i);
        } else if (id == 3) {
        	Intent i = new Intent(this, TrainingZonesActivity.class);
        	startActivity(i);
        } else if (id == 4) {
        	Intent i = new Intent(this, Pace2SpeedActivity.class);
        	startActivity(i);
        } else {
        	String str = Long.toString(id);
            Toast.makeText(this, "id " + str + " nieobsługiwane", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //stary sposob na menu - takie zwykłe ascetyczne
    	//super.onCreateOptionsMenu(menu);
        //menu.add(0, PREFERENCES_ID, 0, "Opcje");
    	
    	//nowy sposob na menu - to będzie bardziej graficzne:
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    	
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {        
        switch(item.getItemId()) {
            case R.id.menu_ustawienia:
            	Intent p = new Intent(this, PreferencesActivity.class);
            	startActivity(p);
                return true;
            case R.id.menu_o_programie:
            	Intent i = new Intent(this, OProgramieActivity.class);
            	startActivity(i);
                return true;
            case R.id.menu_pomoc:
            	Intent h = new Intent(this, PomocActivity.class);
            	startActivity(h);
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }
}
package pl.kruzar.calc4runner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalcListAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Calc calc = new Calc();
	
	public CalcListAdapter(Context context){
		mInflater = LayoutInflater.from(context);
			
		//0 - kalkulator BMI
		String bmi_name = context.getResources().getString(R.string.bmi_calc_menu_name);
		String bmi_info = context.getResources().getString(R.string.bmi_calc_menu_info);
		calc.setCalc(bmi_name, bmi_info);
		
		//1 - kalkulator HRmax
		String hrmax_name = context.getResources().getString(R.string.hrmax_calc_menu_name);
		String hrmax_info = context.getResources().getString(R.string.hrmax_calc_menu_info);
		calc.setCalc(hrmax_name, hrmax_info);
		
		//2 - kalkulator RaceTimePredictor
		String rtp_name = context.getResources().getString(R.string.rtp_calc_menu_name);
		String rtp_info = context.getResources().getString(R.string.rtp_calc_menu_info);
		calc.setCalc(rtp_name, rtp_info);
		
		//3 - kalkulator TrainingZones
		String tz_name = context.getResources().getString(R.string.tz_calc_menu_name);
		String tz_info = context.getResources().getString(R.string.tz_calc_menu_info);
		calc.setCalc(tz_name, tz_info);
		
		//4 - kalkulator Pace 2 Speed
		String p2s_name = context.getResources().getString(R.string.p2s_calc_menu_name);
		String p2s_info = context.getResources().getString(R.string.p2s_calc_menu_info);
		calc.setCalc(p2s_name, p2s_info);
		
		//calc.setCalc("Kalorie", "Ilość spalonych kalorii");
	}
	
	public int getCount() {
		return calc.getIloscKalkulatorow();
	}
	
	public Object getItem(int position) {
		return position;
	}
	
	public long getItemId(int position) {
        return position;
    }
	
	public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder holder;
    	
        if (convertView == null) {
        	convertView = mInflater.inflate(R.layout.calc_list, null);
        	
            holder = new ViewHolder();
            holder.nazwa_kalkulatora = (TextView) convertView.findViewById(R.id.nazwa_kalkulatora);
            holder.opis_kalkulatora = (TextView) convertView.findViewById(R.id.opis_kalkulatora);

            convertView.setTag(holder);
        } else {
        	holder = (ViewHolder) convertView.getTag();
        }
        
        holder.nazwa_kalkulatora.setText(calc.getNazwa(position));
        holder.opis_kalkulatora.setText(calc.getOpis(position));
        return convertView;
    }

    class ViewHolder {
        TextView nazwa_kalkulatora;
        TextView opis_kalkulatora;
    }
}

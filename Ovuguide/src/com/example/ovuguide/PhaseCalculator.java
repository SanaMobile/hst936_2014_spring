/*Inputs:
		get date,mucus,sex,menses day value for today
		get date and  mucus for previous day*/	
/*Algo2:
		 1. if menses then infertile
		 2. else if creamy|| eggwhite || watery then fertile
		 3. else if prev_day reading not available display wait till beginning of the cycle ; set flag_indecisive
		 4. else if prev_day==watery && today!= watery
		 	 then flag=1; ctr=1; return fertile
		 5. else if ctr<4 && flag=1
		 	 then ctr++; return fertile;
		 6. else if flag=1 && ctr==4
		  		then flag=0 && ctr=0; return fertile;
		 7. else if sticky
		 		then fertile;
		 8. else if none
		 		then infertile; 
		 default value returned fertile 		
		 
*/
package com.example.ovuguide;

import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;

public class PhaseCalculator {
	
	public static final String PREFS_NAME="Flags";
	public static final String OVULATION_FLAG="Ovulation";				//Marks the ovulation phase,ie, peak+4 days
	public static final String PEAKDAYFOLLOW_COUNT="PeakDayFollow";		//Stores the number of days past the peak day
	public static final String INDECISIVE_FLAG="Indecisive";			//Marks indecisive phase till the next menses
	
	public int calculatePhase(Context context,DailyReading todayReading)
	{
		//Flags:
		SharedPreferences flags = context.getSharedPreferences(PREFS_NAME,0);
		SharedPreferences.Editor editor = flags.edit();
		
		DailyReading previousDayReading=null;
		int todayMucus = todayReading.getMucus();
		
		Calendar today = Calendar.getInstance();
		Calendar previousDay = Calendar.getInstance();
		previousDay.setTimeInMillis(today.getTimeInMillis() - 1000*60*60*24);
		
		ObservationsDAO observationsDAO = new ObservationsDAO(context);
		
		
		
		if(todayMucus==MucusTexture.MENSES)
		{
			//reset indecisive flag
			editor.putBoolean(INDECISIVE_FLAG, false);
			editor.commit();
			return Phase.INFERTILE;
		}
		else if(todayMucus==MucusTexture.CREAMY || todayMucus==MucusTexture.EGGWHITE || todayMucus==MucusTexture.WATERY)
			return Phase.FERTILE;
		try
		{
			previousDayReading = observationsDAO.getDailyReading(previousDay.get(Calendar.DAY_OF_MONTH), previousDay.get(Calendar.MONTH), previousDay.get(Calendar.YEAR));
		}
		catch(android.database.CursorIndexOutOfBoundsException ex)
		{
			previousDayReading=null;
		}
		finally
		{
			if(previousDayReading == null || flags.getBoolean(INDECISIVE_FLAG, true))
			{
				//set indecisiveFlag
				editor.putBoolean(INDECISIVE_FLAG, true);
				editor.commit();
				return Phase.INDECISIVE;
			}
			else if(previousDayReading.getMucus()==MucusTexture.WATERY && todayMucus!=MucusTexture.WATERY)
			{
				//set flagOvulation to true
				editor.putBoolean(OVULATION_FLAG, true);
				//initialize daysFollowingPeakDay =1
				editor.putInt(PEAKDAYFOLLOW_COUNT, 1);
				editor.commit();
				return Phase.FERTILE;
			}
			else if(flags.getInt(PEAKDAYFOLLOW_COUNT, 0)<4 && flags.getBoolean(OVULATION_FLAG, false)==true)
			{
				//daysFollowingPeakDay++
				int temp = flags.getInt(PEAKDAYFOLLOW_COUNT, 0)+1;
				editor.putInt(PEAKDAYFOLLOW_COUNT, temp);
				editor.commit();
				return Phase.FERTILE;
			}
			else if(flags.getInt(PEAKDAYFOLLOW_COUNT, 0)==4 && flags.getBoolean(OVULATION_FLAG, false)==true)
			{
				//flagOvulation=false;
				editor.putBoolean(OVULATION_FLAG, false);
				//ctr=0;
				editor.putInt(PEAKDAYFOLLOW_COUNT, 0);
				editor.commit();
				return Phase.FERTILE;
			}
			else if(todayMucus==MucusTexture.STICKY)
			{
				return Phase.FERTILE;
			}
			else if(todayMucus==MucusTexture.NONE)
			{
				return Phase.INFERTILE;
			}
			
		}
		
		
		return Phase.FERTILE;
		
	}

}

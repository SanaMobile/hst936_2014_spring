package com.example.ovuguide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class CalendarView extends Activity {

	public Calendar month;
	public CalendarAdapter adapter;
	public Handler handler;
	public ArrayList<String> items; // container to store some random calendar items
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.calendar);
	    
	    month = Calendar.getInstance();				//get today's date
	    //onNewIntent(getIntent());
	    Calendar previousDay = Calendar.getInstance();
		
	    items = new ArrayList<String>();
	    adapter = new CalendarAdapter(this, month);
	    
	    GridView gridview = (GridView) findViewById(R.id.gridview);			//attach adapter to the view
	    gridview.setAdapter(adapter);
	    
	    //handler = new Handler();
	    //handler.post(calendarUpdater);
	    
	    //INITIALISE THE TEXT VIEWS:
	    //1.Sets the title bar to display current year and month
	    TextView title  = (TextView) findViewById(R.id.title);					
	    title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	    
	    //2.On clicking previous button : Sets date's value to previous month but same date
	    TextView previous  = (TextView) findViewById(R.id.previous);
	    previous.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(month.get(Calendar.MONTH)== month.getActualMinimum(Calendar.MONTH)) {				
					month.set((month.get(Calendar.YEAR)-1),month.getActualMaximum(Calendar.MONTH),1);
				} else {
					month.set(Calendar.MONTH,month.get(Calendar.MONTH)-1);
				}
				refreshCalendar();
			}
		});
	    
	  //3.On clicking next button : Sets date's value to the same date but next month's value
	    TextView next  = (TextView) findViewById(R.id.next);
	    next.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(month.get(Calendar.MONTH)== month.getActualMaximum(Calendar.MONTH)) {				
					month.set((month.get(Calendar.YEAR)+1),month.getActualMinimum(Calendar.MONTH),1);
				} else {
					month.set(Calendar.MONTH,month.get(Calendar.MONTH)+1);
				}
				refreshCalendar();
				
			}
		});
	    
	    
	    //4. On clicking a date in the grid: Obtains the date clicked and calls onNewIntent() that sets month to selected date 
		gridview.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		    	TextView date = (TextView) v.findViewById(R.id.date);
		        if(date instanceof TextView && !date.getText().equals("")) {
		        	
		        	
		        	Intent intent = new Intent("com.example.ovuguide.Form");
		        	String day = date.getText().toString();
		        			        	
		        	//intent.putExtra("date", android.text.format.DateFormat.format("yyyy-MM", month)+"-"+day);
		        	// send the selected date as a (dayOfMonth,month,year) tuple 
		        	intent.putExtra("dayOfMonth", Integer.parseInt(day));
		        	intent.putExtra("month", month.get(Calendar.MONTH));
		        	intent.putExtra("year", month.get(Calendar.YEAR));
		        	startActivity(intent);
		        	
		        }
		        
		    }
		});
	}
	
	//
	public void refreshCalendar()
	{
		TextView title  = (TextView) findViewById(R.id.title);
		
		adapter.refreshDays();
		adapter.notifyDataSetChanged();				
		//handler.post(calendarUpdater); // generate some random calendar items				
		
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}
	
	public void onNewIntent(Intent intent) {
		String date = intent.getStringExtra("date");
		String[] dateArr = date.split("-"); // date format is yyyy-mm-dd
		month.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[2]));
	}
	
	/*public Runnable calendarUpdater = new Runnable() {
		
		public void run() {
			items.clear();
			// format random values. You can implement a dedicated class to provide real values
			for(int i=0;i<31;i++) {
				Random r = new Random();
				
				if(r.nextInt(10)>6)
				{
					items.add(Integer.toString(i));
				}
			}

			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};*/
}

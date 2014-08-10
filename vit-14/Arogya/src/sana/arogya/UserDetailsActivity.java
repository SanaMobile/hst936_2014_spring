package sana.arogya;

import java.util.Calendar;
import sana.helitavya.R;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RadioButton;
import android.widget.Toast;

public class UserDetailsActivity extends Activity {
	String gender;
	int age=0,sex=1,height;
	String[] nums;
	double[] h= new double[10];
	double[] w= new double[10];
	double weight;
	private int Year,month,day;
	private NumberPicker wp,hp;
	private Button date;
	static final int DATE_DIALOG_ID = 0;
	static final String DATABASE_TABLE="axisvalues";
	DBAdapter dba = new DBAdapter(this);
	String u="";
	

	// the callback received when the user "sets" the date in the dialog
    
	private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    Year = year;
                    month = monthOfYear;
                    day = dayOfMonth;
                    updateDisplay();
                }
            };
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_details);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if(!prefs.getBoolean("firstTime", false)) 
		{
		DataBase sqldb= new  DataBase();
		sqldb.create(this);
		Toast.makeText(this, "Insert Done", Toast.LENGTH_LONG).show();
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("firstTime", true);
		editor.commit();
		}
		
       
		
		//Get Current Date
		final Calendar c = Calendar.getInstance();
		Year= c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		
		//Setting up of values for weight picker
		nums = new String[220];
		int i;
		double j=1;
		for(i=0;i<220;i++)
		{
			nums[i]=""+j;
			j+=0.5;
		}
		
	//Radio Group Listener
	
		final OnClickListener radioListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				RadioButton rb = (RadioButton) v;
				gender=rb.getText().toString().trim();
				if(gender.equalsIgnoreCase("Male"))
					sex=1;
				else
					sex=2;
			}
		};
		
		final RadioButton choice1 = (RadioButton) findViewById(R.id.male);
		choice1.setOnClickListener(radioListener);

		final RadioButton choice2 = (RadioButton) findViewById(R.id.female);
		choice2.setOnClickListener(radioListener);
				
	// Add an onClick listener to the date button
		
		date = (Button) findViewById(R.id.date);
		date.setOnClickListener(new View.OnClickListener()
		{
			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});

	//Height Picker
        
        hp = (NumberPicker) findViewById(R.id.numberPicker1);
		hp.setMinValue(1);
        hp.setMaxValue(220);
        hp.setValue(100);
        hp.setWrapSelectorWheel(true); 
        hp.setOnValueChangedListener(new OnValueChangeListener() {
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				height=newVal;
		}
	});
        
    	
			//Weight picker
			wp = (NumberPicker) findViewById(R.id.numberPicker2);
			wp.setMaxValue(nums.length-1);
            wp.setMinValue(0);
            wp.setValue(100);
            wp.setDisplayedValues(nums);
            wp.setWrapSelectorWheel(true); 
            wp.setOnValueChangedListener(new OnValueChangeListener() {
           @Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				String wt=nums[newVal];
				weight=Double.parseDouble(wt);
			}
		});     
            
     	
  }
	private void updateDisplay() 
    {
       	//Get Current Date to calculate age
		final Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH);
			age=(y-Year)*12+(m-month);
    }
    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id) 
        {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,
                        mDateSetListener,
                        Year, month, day);
        }
        return null;
	}
    @SuppressWarnings("deprecation")
	public void proceedButtton(View v)
    {
    	String KEY_CONTENT="content",KEY_GENDER="sex", KEY_AGE="age", KEY_P3="p3", KEY_P25="p25", KEY_P50="p50", KEY_P75="p75", KEY_P97="p97";
    	 SQLiteDatabase db=dba.open();
        Cursor cm = db.query(true, DATABASE_TABLE, new String[] {
                KEY_CONTENT,KEY_GENDER,KEY_AGE,KEY_P3,KEY_P25,KEY_P50,KEY_P75,KEY_P97}, 
                KEY_AGE + " = " +age + " and "+KEY_GENDER +" = "+sex, 
                null,null,null,null,null);
        if(cm.moveToFirst())
        {	
        	do
        	{
        	if(cm.getString(0).equals("h"))
        	{
        	h[0]=Double.parseDouble(cm.getString(3));
        	h[1]=Double.parseDouble(cm.getString(4));
        	h[2]=Double.parseDouble(cm.getString(5));
        	h[3]=Double.parseDouble(cm.getString(6));
        	h[4]=Double.parseDouble(cm.getString(7));
        	
        	}
        	else
        	{
        	w[0]=Double.parseDouble(cm.getString(3));
        	w[1]=Double.parseDouble(cm.getString(4));
        	w[2]=Double.parseDouble(cm.getString(5));
        	w[3]=Double.parseDouble(cm.getString(6));
        	w[4]=Double.parseDouble(cm.getString(7));
        	}
        	
        	}while(cm.moveToNext());
        }
        dba.close();
        u="";
        if(height>h[1]&&height<h[3])
			u=u+"Your Height is normal and ";	
		else if(height<h[1])
			u=u+"Your Height is Lesser than the ideal height and ";	
		else if(height>h[3])
		 	u=u+"Your Height is greater than the ideal height and";
		if(weight>w[3])
			u=u+"your Weight is greater than the ideal weight.";	
		else if(weight<w[2])
			u=u+"your Weight is lesser than the ideal weight.";	
		else if(weight>h[1]&&weight<w[3])
			u=u+"your Weight is normal.";	
		
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Arogya Graph");
        alertDialog.setMessage(u);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(final DialogInterface dialog, final int which) {
        	nextGraph();
        }
        });
        alertDialog.show();
    	
    }
    public void nextGraph()
    {
    	LineGraph lg = new LineGraph();
    	Intent in = lg.getIntent(this,h,w,height,weight);
    	startActivity(in);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.dev:
	        Intent ini = new Intent(this,About.class);
	        startActivity(ini);
	        return true;
	        
	    }
		return true;
	}
}
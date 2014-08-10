package com.example.bmiapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;




public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
   
    
public void CalculateBmi(View view)
{
	if(view.getId()==R.id.button1)
	{
		EditText weighttxt=(EditText)findViewById(R.id.WEIGHTTXT);
		EditText heighttxt=(EditText)findViewById(R.id.HEIGHTTXT);
		TextView result=(TextView)findViewById(R.id.RESULTTXT);
		EditText age=(EditText)findViewById(R.id.AGETXT);
		
		float weight=Float.parseFloat(weighttxt.getText().toString());
		float height=Float.parseFloat(heighttxt.getText().toString());
		int ages=Integer.parseInt(age.getText().toString());
		
		float bmiValue=calculateBMI(weight,height);
		
		String bmiInterpretation=interpretBMI(bmiValue,ages);
		result.setText(bmiValue+"-\n"+bmiInterpretation);
	}
}

private float calculateBMI(float weight,float height)
{
	return(float)(weight*4.88/(height*height));
}

private String interpretBMI(float bmiValue,int ages)
{
	if(ages==2)
	{
	if(bmiValue<14.5)
	{
		return " underweight";
	}
	if(bmiValue<18)
	{
		return "normal";
	}
		else if(bmiValue<19)
			
		{
			return "overweight";
		}
		
		else
		{
			return "obese";
		}
		}
	else if(ages==3)
	{
		if(bmiValue<14)
		{
			return"underweight";
		}
		else if(bmiValue<17)
		{
			return"normal";
		}
		else if(bmiValue<19)
		{
			return"overweight";
		}
		else
		{
			return"obese";
		}
		}
	else if(ages==4)
	{
		if(bmiValue<13.5)
		{
			return"underweight";
		}
		else if(bmiValue<17)
		{
			return"normal";
		}
		else if(bmiValue<18)
		{
			return"overweight";
		}
		else
		{
			return"obese";
		}
	}
	else if(ages==5)
	{
		if(bmiValue<13)
		{
			return"underweight";
		}
		else if(bmiValue<16.5)
		{
			return"normal";
		}
		else if(bmiValue<18.2)
		{
			return"overweight";
		}
		else
		{
			return"obese";
		}
	}
	else if(ages==6)
	{
		if(bmiValue<13)
		{
			return"underweight";
		}
		else if(bmiValue<17)
		{
			return"normal";
		}
		else if(bmiValue<19){
			return"overweight";
		}
		else
		{
			return"obese";
		}
	}
	else if(ages==7)
	{
		if(bmiValue<13.4)
		{
			return"underweight";
		}
		else if(bmiValue<17.5)
		{
			return"normal";
		}
		else if(bmiValue<19.5)
		{
			return "overweight";
		}
		else
		{
			return"obese";
		}
	}
		
	else if(ages==8)
	{
		if(bmiValue<13.5)
		{
			return"underweight";
		}
		else if(bmiValue<18.3)
		{
			return"normal";
		}
		else if(bmiValue<20.6)
		{
			return"overweight";
		}
		else
		{
			return"obese";
		}
	}
	else if(ages==9)
	{
		if(bmiValue<13.7)
		{
			return"underweight";
		}
		else if(bmiValue<19.2)
		{
			return"normal";
		}
		else if(bmiValue<21.8)
		{
			return"overweight";
		}
		else
		{
			return"obese";
		}
	}
	else if(ages==10)
	{
		if(bmiValue<14)
		{
			return"underweight";
		}
		else if(bmiValue<20)
		{
			return"normal";
		}
		else if(bmiValue<22.5)
		{
			return"overweight";
		}
		else
		{
			return"obese";
		}
	}
	else if(ages==11)
	{
		if(bmiValue<14.5)
		{
			return"underweight";
		}
		else if(bmiValue<20.2)
		{
			return"normal";
		}
		else if(bmiValue<24)
		{
			return"overweight";
		}
		else
		{
			return"obese";
		}
	}
	else if(ages==12)
	{
		if(bmiValue<14.7)
		{
			return"underweight";
		}
		else if(bmiValue<21.6)
		{
			return"normal";
		}
		else if(bmiValue<25.4)
		{
			return"overweight";
		}
		else
		{
			return"obese";
		}
	}
	else if(ages==13)
	{
		if(bmiValue<15.2)
		{
			return"underweight";
		}
		else if(bmiValue<22.5)
		{
			return"normal";
		}
		else if(bmiValue<26.2)
		{
			return"overweight";
		}
		else
		{
			return"obese";
		}
	}
	else if(ages==14)
	{
		if(bmiValue<15.7)
		{
			return"underweight";
		}
		else if(bmiValue<23.2)
		{
			return"normal";
		}
		else if(bmiValue<27.1)
		{
			return"overweight";
		}
		else
		{
			return"obese";
		}
	}
	else if(ages==15)
	{
		if(bmiValue<16.2)
		{
			return"underweight";
		}
		else if(bmiValue<24){
			return"normal";
		}
		else if(bmiValue<27.5)
		{
			return"overweight";
		}
		else
		{
			return"obese";
		}
	}
	else if(ages==16)
	{
		if(bmiValue<16.7)
		{
			return"underweight";
		}
		else if(bmiValue<24.5)
		{
			return"normal";
		}
		else if(bmiValue<28.2)
				{
			return"overweigt";
				}
		else
		{
			return"obese";
		}
	}
	else if(ages==17)
	{
		if(bmiValue<17.2)
		{
			return"underweight";
		}
		else if(bmiValue<25.2)
		{
			return"normal";
		}
		else if(bmiValue<29.5)
		{
			return"overweight";
		}
		else
		{
			return"obese";
		}
	}
	else if(ages==18)
	{
		if(bmiValue<17.5)
		{
			return"underweight";
			}
		else if(bmiValue<25.7)
		{
			return"normaL";
		}
		else if(bmiValue<30.3)
		{
			return"overweight";
		}
		else
		{
			return"obese";
		}
	}
	else if(ages==19)
	{
		if(bmiValue<17.9)
		{
			return"underweight";
		}
		else if(bmiValue<26){
			return"normal";
		}
			else if(bmiValue<31)
			{
				return"overweight";
			}
			else
			{
				return"obese";
			}
		}
		else if(ages==20)
		{
			if(bmiValue<18)
			{
				return"underweight";
			}
			else if(bmiValue<26.5)
			{
				return"normal";
			}
			else if(bmiValue<31.6)
			{
				return"overweight";
			}
			else
			{
				return"obese";
			}
		}
		else
		{
			if(bmiValue<18)
			{
				return"underweight";
			}
			else if(bmiValue<25)
			{
				return"normal";
			}
			else if(bmiValue<32)
			{
				return"overweight";
			}
			else
			{
				return"obese";
			}
		}
	
			
			}
		
	
		
		
		
		
		
		
		
		
		
		
	


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}

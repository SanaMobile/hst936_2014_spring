package com.example.stwo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class Third extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.third);


		final RadioButton a=(RadioButton) findViewById(R.id.checkBox1);
		final RadioButton b=(RadioButton) findViewById(R.id.two);
		final RadioButton c=(RadioButton) findViewById(R.id.three);
		
		final RadioButton d=(RadioButton) findViewById(R.id.four);
		final RadioButton e=(RadioButton) findViewById(R.id.five);
		final RadioButton f=(RadioButton) findViewById(R.id.none);
		
		final RadioButton g=(RadioButton) findViewById(R.id.nthree);
		final RadioButton h=(RadioButton) findViewById(R.id.nfive);
		
		final RadioButton i=(RadioButton) findViewById(R.id.nseven);
		final RadioButton j=(RadioButton) findViewById(R.id.nnine);
		
		final RadioButton k=(RadioButton) findViewById(R.id.neleven);
		
		final RadioButton l=(RadioButton) findViewById(R.id.nthirteen);
		final RadioButton m=(RadioButton) findViewById(R.id.nfifteen);
		final RadioButton n=(RadioButton) findViewById(R.id.checkbox3);
		final RadioButton o=(RadioButton) findViewById(R.id.checkbox5);
		
		final RadioButton q1=(RadioButton) findViewById(R.id.checkBox2);
		final RadioButton q2=(RadioButton) findViewById(R.id.checkbox3);
		final RadioButton q3=(RadioButton) findViewById(R.id.six);
		final RadioButton q4=(RadioButton) findViewById(R.id.seven);
		final RadioButton q5=(RadioButton) findViewById(R.id.eight);
		final RadioButton q6=(RadioButton) findViewById(R.id.ntwo);
		final RadioButton q7=(RadioButton) findViewById(R.id.nsix);
		final RadioButton q8=(RadioButton) findViewById(R.id.neight);
		final RadioButton q9=(RadioButton) findViewById(R.id.nten);


		Button one=(Button) findViewById(R.id.next);
		
		one.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			if(a.isChecked()&&b.isChecked()&&c.isChecked()){
				Toast.makeText(getApplicationContext(), "Iron deficiency anaemia", Toast.LENGTH_LONG).show();
				finish();
			}else 	if(d.isChecked()&&e.isChecked()&&f.isChecked()){
				Toast.makeText(getApplicationContext(), "Vitamin A deficiency thats xeropthalmia", Toast.LENGTH_LONG).show();
				finish();
			}else 	if(g.isChecked()&&h.isChecked()){
				Toast.makeText(getApplicationContext(), "Iodine deficiency", Toast.LENGTH_LONG).show();
				finish();
			}else 	if(i.isChecked()&&j.isChecked()){
				Toast.makeText(getApplicationContext(), "Vitamin C deficiency (scurvy)", Toast.LENGTH_LONG).show();
				finish();
			
			}else if(k.isChecked()){
				Toast.makeText(getApplicationContext(), "Thiamin (BeriBeri)", Toast.LENGTH_LONG).show();
				finish();
			}else 	if(l.isChecked()&&m.isChecked()&&n.isChecked()&&o.isChecked()){
				Toast.makeText(getApplicationContext(), "Niacin (Pelagra)", Toast.LENGTH_LONG).show();
				finish();
				
			}
			else if(q1.isChecked()&&q2.isChecked()&&q3.isChecked()&&q4.isChecked()&&q5.isChecked()&&q6.isChecked()&&q7.isChecked()&&q8.isChecked()&&q9.isChecked())
			{
				Toast.makeText(getApplicationContext(), "You have nothing ", Toast.LENGTH_LONG).show();
				finish();
			
			
			}}
		});
}
}
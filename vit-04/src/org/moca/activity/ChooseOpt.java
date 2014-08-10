package org.moca.activity;
import org.moca.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


public class ChooseOpt extends Activity implements OnClickListener {
	String diseaseName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option);
		Intent intent = getIntent();
		diseaseName = intent.getExtras().getString("name");
		TextView tv=(TextView)findViewById(R.id.textView1);
		tv.setText(diseaseName);
		 View sym=findViewById(R.id.button1);
	      sym.setOnClickListener(this);
	      View cau=findViewById(R.id.button2);
	      cau.setOnClickListener(this);
	      View man=findViewById(R.id.button3);
	      man.setOnClickListener(this);
	      View pre=findViewById(R.id.button4);
	      pre.setOnClickListener(this);
	      View tre=findViewById(R.id.button5);
	      tre.setOnClickListener(this);
	      
	      
	      
	}

	public ChooseOpt(){
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		// buttons on the main screen
		case R.id.button1:
			Intent i=new Intent(ChooseOpt.this,Symptoms.class);
			i.putExtra("name", diseaseName);
			startActivity(i);
			break;
		case R.id.button2:
			Intent j=new Intent(ChooseOpt.this,Causes.class);
			j.putExtra("name", diseaseName);
			startActivity(j);
			break;
		case R.id.button3:
			Intent k=new Intent(ChooseOpt.this,Manifestations.class);
			k.putExtra("name", diseaseName);
			startActivity(k);
			break;
		case R.id.button4 : 
			Intent l=new Intent(ChooseOpt.this,Prevention.class);
			l.putExtra("name", diseaseName);
			startActivity(l);
		break;
		case R.id.button5 : 
			Intent m=new Intent(ChooseOpt.this,Treatment.class);
			m.putExtra("name", diseaseName);
			startActivity(m);
		break;
		}
		
	}

}

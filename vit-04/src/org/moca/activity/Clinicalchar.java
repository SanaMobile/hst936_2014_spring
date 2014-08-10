package org.moca.activity;

import org.moca.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Clinicalchar extends Activity{
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clinicalchar);
       TextView textViewEC = (TextView)findViewById(R.id.tvec);
       textViewEC.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Eczema(atopic dermatitis)");
				startActivity(i);
			}
            
      
	});
       TextView textViewTC = (TextView)findViewById(R.id.tvtc);
       textViewTC.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Tinea Corporis");
				startActivity(i);
			}
            
      
	});
       TextView textViewTF = (TextView)findViewById(R.id.tvtf);
       textViewTF.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Tinea Faciei");
				startActivity(i);
			}
            
      
	});
       TextView textViewTP = (TextView)findViewById(R.id.tvtp);
       textViewTP.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Tinea Pedis");
				startActivity(i);
			}
            
      
	});
       TextView textViewTCA = (TextView)findViewById(R.id.tvtca);
       textViewTCA.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Tinea Capitis(scalp ringworm)");
				startActivity(i);
			}
            
      
	});
       TextView textViewPS = (TextView)findViewById(R.id.tvps);
       textViewPS.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Psoriasis");
				startActivity(i);
			}
            
      
	});
       TextView textViewHS = (TextView)findViewById(R.id.tvhs);
       textViewHS.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Herpes Simplex");
				startActivity(i);
			}
            
      
	});
       TextView textViewHZ = (TextView)findViewById(R.id.tvhz);
       textViewHZ.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Herpes Zoster");
				startActivity(i);
			}
            
      
	});
       TextView textViewIM = (TextView)findViewById(R.id.tvim);
       textViewIM.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Impetigo");
				startActivity(i);
			}
            
      
	});
       TextView textViewFWA = (TextView)findViewById(R.id.tvfwa);
       textViewFWA.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Flat Warts");
				startActivity(i);
			}
            
      
	});
       TextView textViewWA = (TextView)findViewById(R.id.tvwa);
       textViewWA.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Warts");
				startActivity(i);
			}
            
      
	});
       TextView textViewPY = (TextView)findViewById(R.id.tvpy);
       textViewPY.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Pytiriasis alba");
				startActivity(i);
			}
            
      
	});
       TextView textViewVI = (TextView)findViewById(R.id.tvvi);
       textViewVI.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Vitiligo");
				startActivity(i);
			}
            
      
	});
       TextView textViewON = (TextView)findViewById(R.id.tvon);
       textViewON.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Onychomycosis");
				startActivity(i);
			}
            
      
	});
       
       TextView textViewIB = (TextView)findViewById(R.id.tvib);
       textViewIB.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Insect Bites");
				startActivity(i);
			}
            
      
	});
       TextView textViewSC = (TextView)findViewById(R.id.tvsc);
       textViewSC.setOnClickListener(new View.OnClickListener()
        {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Clinicalchar.this,ChooseOpt.class);
				i.putExtra("name", "Scabies");
				startActivity(i);
			}
            
      
	});
       
	}

	public Clinicalchar() {
		// TODO Auto-generated constructor stub
	}

}

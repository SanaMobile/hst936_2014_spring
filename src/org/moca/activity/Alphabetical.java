package org.moca.activity;

import org.moca.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Alphabetical extends Activity {
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alphabetical);
		
		 TextView textViewEC = (TextView)findViewById(R.id.textView2);
	       textViewEC.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Eczema(atopic dermatitis)");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewTC = (TextView)findViewById(R.id.textView13);
	       textViewTC.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Tinea Corporis");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewTF = (TextView)findViewById(R.id.textView11);
	       textViewTF.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Tinea Faciei");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewTP = (TextView)findViewById(R.id.textView10);
	       textViewTP.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Tinea Pedis");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewTCA = (TextView)findViewById(R.id.textView12);
	       textViewTCA.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Tinea Capitis(scalp ringworm)");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewPS = (TextView)findViewById(R.id.textView14);
	       textViewPS.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Psoriasis");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewHS = (TextView)findViewById(R.id.textView16);
	       textViewHS.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Herpes Simplex");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewHZ = (TextView)findViewById(R.id.textView4);
	       textViewHZ.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Herpes Zoster");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewIM = (TextView)findViewById(R.id.textView5);
	       textViewIM.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Impetigo");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewFWA = (TextView)findViewById(R.id.textView3);
	       textViewFWA.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Flat Warts");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewWA = (TextView)findViewById(R.id.textView18);
	       textViewWA.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Warts");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewPY = (TextView)findViewById(R.id.textView6);
	       textViewPY.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Pytiriasis alba");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewVI = (TextView)findViewById(R.id.textView9);
	       textViewVI.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Vitiligo");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewON = (TextView)findViewById(R.id.textView17);
	       textViewON.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Onychomycosis");
					startActivity(i);
				}
	            
	      
		});
	       
	       TextView textViewIB = (TextView)findViewById(R.id.textView15);
	       textViewIB.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Insect Bites");
					startActivity(i);
				}
	            
	      
		});
	       TextView textViewSC = (TextView)findViewById(R.id.textView7);
	       textViewSC.setOnClickListener(new View.OnClickListener()
	        {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i=new Intent(Alphabetical.this,ChooseOpt.class);
					i.putExtra("name", "Scabies");
					startActivity(i);
				}
	            
	      
		});
	       
		}
	

	public Alphabetical() {
		// TODO Auto-generated constructor stub
	}

}

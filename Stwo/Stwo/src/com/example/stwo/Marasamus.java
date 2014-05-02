package com.example.stwo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Marasamus extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.mara);
    	
    	
    	Button next=(Button) findViewById(R.id.button1);
    	next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent n=new Intent(Marasamus.this,None.class);
				startActivity(n);
			}
		});
    }
}

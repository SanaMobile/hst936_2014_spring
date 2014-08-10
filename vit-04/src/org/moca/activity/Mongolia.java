package org.moca.activity;

import org.moca.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public  class Mongolia extends Activity  {
	
	protected void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mongolia);

        TextView someTextView = (TextView)findViewById(R.id.textView1);
        someTextView.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final AlertDialog levelDialog;
				// Strings to Show In Dialog with Radio Buttons
				final CharSequence[] items = {" List in group by clinical characteristics","List in diseases alphabetically A->Z"};            
				                // Creating and Building the Dialog 
				                AlertDialog.Builder builder = new AlertDialog.Builder(Mongolia.this);
				                builder.setTitle("Choose your preference");
				                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
				                public void onClick(DialogInterface dialog, int item) {
				                   
				                    
				                 switch(item)
				                    {
				                        case 0:  Intent i = new Intent(Mongolia.this,Clinicalchar.class);
				        				          startActivity(i);
				                                // Your code when first option selected
				                                 break;
				                        case 1:  Intent j = new Intent(Mongolia.this,Alphabetical.class);
		        				          startActivity(j);
				                                // Your code when 2nd  option selected
				                                break;
				                    }
				                    }
				                });
				                levelDialog = builder.create();
				                levelDialog.dismiss();  
				                levelDialog.show();
			}
	});
	}
	
}


	
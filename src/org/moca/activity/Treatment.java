package org.moca.activity;
import org.moca.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class Treatment extends Activity{
	String diseaseName , x,y,z,a,b,c;
	  String[] cmd,sym1,sym2,sym3,sym4,tredia1,tredia2,tredia3;
	  Integer i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.treatment);
		
		
		cmd = getResources().getStringArray(R.array.Diseases);
		sym1 = getResources().getStringArray(R.array.Tre1);
		sym2 = getResources().getStringArray(R.array.Tre2);
		sym3 = getResources().getStringArray(R.array.Tre3);
		sym4 = getResources().getStringArray(R.array.Tre4);
		tredia1 = getResources().getStringArray(R.array.Tredia1);
		tredia2 = getResources().getStringArray(R.array.Tredia2);
		tredia3 = getResources().getStringArray(R.array.Tredia3);

		
		Intent intent = getIntent();
		diseaseName = intent.getExtras().getString("name");
		TextView tv1=(TextView)findViewById(R.id.tvtreatment);
		ImageView iv1=(ImageView)findViewById(R.id.ivtreatment);
		
		int[] images = new int[16];
		images[0] = R.drawable.m_ecz_tre;
		images[1] = R.drawable.m_fwa_tre;
		images[2] = R.drawable.m_hsi_tre;
		images[3] = R.drawable.m_hzo_tre;
		images[4] = R.drawable.m_imp_tre;
		images[5] = R.drawable.m_ibi_tre;
		images[6] = R.drawable.m_ony_tre;
		images[7] = R.drawable.m_pso_tre;
		images[8] = R.drawable.m_pal_tre;
		images[9] = R.drawable.m_sca_tre;
		images[10] = R.drawable.m_tca_tre;
		images[11] = R.drawable.m_tco_tre;
		images[12] = R.drawable.m_tfa_tre;
		images[13] = R.drawable.m_tpe_tre;
		images[14] = R.drawable.m_vit_tre;
		images[15] = R.drawable.m_war_tre;
		
		TextView tv3=(TextView)findViewById(R.id.tvtreatment2);
		TextView tv4=(TextView)findViewById(R.id.tvtreatment3);
		TextView tv5=(TextView)findViewById(R.id.tvtreatment4);
		TextView tv6=(TextView)findViewById(R.id.tvtreatment5);
		for(i=0;i<cmd.length;i++)
		{
		if(diseaseName.contentEquals(cmd[i]))
		{
			tv1.setText(cmd[i]);
			iv1.setImageResource(images[i]);
			tv3.setText(sym1[i]);
			a=sym1[i];
            x=tredia1[i];
		       tv3.setOnClickListener(new View.OnClickListener()
		        {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AlertDialog.Builder eula = new AlertDialog.Builder(Treatment.this)
			            .setTitle(a)
			            .setIcon(android.R.drawable.ic_dialog_info)
			            .setMessage(x)
			            .setCancelable(true);
						eula.show();
					}
		            
		      
			});
			tv4.setText(sym2[i]);
			b=sym2[i];
            y=tredia2[i];

			 tv4.setOnClickListener(new View.OnClickListener()
		        {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AlertDialog.Builder eula = new AlertDialog.Builder(Treatment.this)
			            .setTitle(b)
			            .setIcon(android.R.drawable.ic_dialog_info)
			            .setMessage(y)
			            .setCancelable(true);
						eula.show();
					}
		            
		      
			});
			tv5.setText(sym3[i]);
			c=sym3[i];
            z=tredia3[i];

			 tv5.setOnClickListener(new View.OnClickListener()
		        {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AlertDialog.Builder eula = new AlertDialog.Builder(Treatment.this)
			            .setTitle(c)
			            .setIcon(android.R.drawable.ic_dialog_info)
			            .setMessage(z)
			            .setCancelable(true);
						eula.show();
					}
		            
		      
			});
			
			tv6.setText(sym4[i]);
		}
		}
		
		
		
		
		
		
	}

	public Treatment() {
		// TODO Auto-generated constructor stub
	}

}

package org.moca.activity;
import org.moca.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Prevention extends Activity{
	String diseaseName;
	  String[] cmd,sym1,sym2,sym3,sym4;
	  Integer i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.symptoms);
		
		cmd = getResources().getStringArray(R.array.Diseases);
		sym1 = getResources().getStringArray(R.array.Pre1);
		sym2 = getResources().getStringArray(R.array.Pre2);
		sym3 = getResources().getStringArray(R.array.Pre3);
		sym4 = getResources().getStringArray(R.array.Pre4);
		Intent intent = getIntent();
		diseaseName = intent.getExtras().getString("name");
		TextView tv1=(TextView)findViewById(R.id.tvSymHead);
		ImageView iv1=(ImageView)findViewById(R.id.ivSym);
		
		int[] images = new int[16];
		images[0] = R.drawable.m_ecz_pre;
		images[1] = R.drawable.m_fwa_pre;
		images[2] = R.drawable.m_hsi_pre;
		images[3] = R.drawable.m_hzo_pre;
		images[4] = R.drawable.m_imp_pre;
		images[5] = R.drawable.m_ibi_pre;
		images[6] = R.drawable.m_ony_pre;
		images[7] = R.drawable.m_pso_pre;
		images[8] = R.drawable.m_pal_pre;
		images[9] = R.drawable.m_sca_pre;
		images[10] = R.drawable.m_tca_pre;
		images[11] = R.drawable.m_tco_pre;
		images[12] = R.drawable.m_tfa_pre;
		images[13] = R.drawable.m_tpe_pre;
		images[14] = R.drawable.m_vit_pre;
		images[15] = R.drawable.m_war_pre;
		
		TextView tv2=(TextView)findViewById(R.id.tvSymName);
		TextView tv3=(TextView)findViewById(R.id.tvSymData);
		TextView tv4=(TextView)findViewById(R.id.tvsym2);
		TextView tv5=(TextView)findViewById(R.id.tvsym3);
		TextView tv6=(TextView)findViewById(R.id.tvsym4);
		for(i=0;i<cmd.length;i++)
		{
		if(diseaseName.contentEquals(cmd[i]))
		{
			tv1.setText(cmd[i]);
			iv1.setImageResource(images[i]);
			tv2.setText("Prevention");
			tv3.setText(sym1[i]);
			tv4.setText(sym2[i]);
			tv5.setText(sym3[i]);
			tv6.setText(sym4[i]);
		}
		}
	}

	public Prevention() {
		// TODO Auto-generated constructor stub
	}

}

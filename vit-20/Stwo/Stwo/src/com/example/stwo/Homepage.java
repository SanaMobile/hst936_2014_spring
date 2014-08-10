package com.example.stwo;





import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class Homepage extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);

		final RadioButton a= (RadioButton) findViewById(R.id.checkBox1);
		final RadioButton b= (RadioButton) findViewById(R.id.two);
		final RadioButton c= (RadioButton) findViewById(R.id.three);
		final RadioButton d= (RadioButton) findViewById(R.id.four);

		final RadioButton e= (RadioButton) findViewById(R.id.extra);
		final RadioButton f= (RadioButton) findViewById(R.id.six);
		final RadioButton g= (RadioButton) findViewById(R.id.seven);
		final RadioButton h= (RadioButton) findViewById(R.id.eight);

		final RadioButton i= (RadioButton) findViewById(R.id.none);
		final RadioButton j= (RadioButton) findViewById(R.id.nthree);
		final RadioButton k= (RadioButton) findViewById(R.id.nfive);
		final RadioButton l= (RadioButton) findViewById(R.id.nseven);

		final RadioButton m= (RadioButton) findViewById(R.id.ntwo);
		final RadioButton n= (RadioButton) findViewById(R.id.nfour);
		final RadioButton o= (RadioButton) findViewById(R.id.nsix);
		final RadioButton p= (RadioButton) findViewById(R.id.neight);






        

		Button one=(Button) findViewById(R.id.next);

        one.setOnClickListener(new View.OnClickListener() {
        	int count=0;
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				
				
				if(a.isChecked()&& b.isChecked()&&c.isChecked()&&d.isChecked()== true){
					/*Intent next=new Intent(Homepage.this,Marasamus.class);
					startActivity(next);*/
					String mara="Marasmus";
					Toast.makeText(getApplicationContext(), mara,
							Toast.LENGTH_LONG).show();
					finish();
				}
				else if(i.isChecked()&& j.isChecked()&&k.isChecked()&&l.isChecked()== true){
					String kwa="You are Having Kwashiorkor diesease";
					Toast.makeText(getApplicationContext(), kwa,Toast.LENGTH_LONG).show();
					finish();
				}
				else if(a.isChecked()|| b.isChecked()|| c.isChecked()||d.isChecked()|| i.isChecked()|| j.isChecked()|| k.isChecked()|| l.isChecked())
				{


					count++;

					if(count >= 3){
						Toast.makeText(getApplicationContext(),"You have Marasmic kwashiorkor which combines symptoms of marasmus and kwashiorkor", Toast.LENGTH_LONG).show();
						finish();
					}
				}
				
				else if(e.isChecked() && f.isChecked()&& g.isChecked()&& h.isChecked()&& m.isChecked()&& n.isChecked()&& o.isChecked() && p.isChecked()){
					Toast.makeText(getApplicationContext(),"You are not having Marasmus or Kwashiorkor",
							Toast.LENGTH_LONG).show();
					finish();
				}
	
				
			}
		});		
		

			}
}

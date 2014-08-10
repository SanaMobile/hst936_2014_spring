/*
 Author: Rajalakshmi
 */
package sana.arogya;

import sana.helitavya.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;


public class About extends Activity {
	 @Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.about_activity);
			
			String r="Developed By:\n S. Rajalakshmi \n VIT University\n Disclaimer: \n The app or the Developer is not responsible for any discrepencies in the data provided.\n";
			TextView tv=(TextView) findViewById(R.id.ab);
			tv.setGravity(Gravity.CENTER);
			tv.setText(r);
	 }
}
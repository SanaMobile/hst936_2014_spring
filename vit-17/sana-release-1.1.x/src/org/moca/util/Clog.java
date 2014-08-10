package org.moca.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;

public class Clog {
	 Context c;
	 public Clog(String g,Context c){
		  // for activity cont
		 this.c=c;
	    	String t = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	    	String x;
	    	System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEElpppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
	    	try {
	    		System.out.println(g);
	    		if(c==null){
	    			System.out.println("context is null");
	    		}
	    	    FileOutputStream fos = c.openFileOutput("Mysanalog",Context.MODE_APPEND);
	    	   
	    	    x=t+" :: "+g;
	    	    fos.write(x.getBytes());	
	    	    fos.close();
	    	    
	    	    result();
	    	} catch (Exception e) {
	    		System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
	    	    e.printStackTrace();
	    	}
	    }
	    
	   
		public void result(){
	    	StringBuffer stringBuffer=null;

	    	try {
	    	    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
	    	            c.openFileInput("Mysanalog")));
	    	    String inputString;
	    	    stringBuffer = new StringBuffer();                
	    	    while ((inputString = inputReader.readLine()) != null) {
	    	        stringBuffer.append(inputString + "\n");
	    	    }
	    	    System.out.println(stringBuffer.toString());
	    	} catch (IOException e) {
	    	    e.printStackTrace();
	    	}
	    	
		File dir =c.getExternalFilesDir(null);
		File file =new File(dir,"sanaLogFile");
		
		
		try {
			
			
		    FileOutputStream fos = new FileOutputStream(file);
		   
		   
		    fos.write(stringBuffer.toString().getBytes());	
		    fos.close();
		    
		   
		} catch (Exception e) {
			System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
		    e.printStackTrace();
		}
		
		} 
		
	    

}
package org.moca.procedure;

import java.util.ArrayList;
import java.util.List;

import org.moca.util.MocaUtil;
import org.w3c.dom.Node;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class HeartElement extends ProcedureElement{
    private String gender;
	
	private int totalChol;
	private String smoker;
	private int totalHdlChol;
	private int bp;

	

	protected HeartElement(String id, String question, String answer,
			String concept, String figure, String audioPrompt) {
		super(id, question, answer, concept, figure, audioPrompt);
		// TODO Auto-generated constructor stub
		
		
	}

	@Override
	protected View createView(Context c) {
		// TODO Auto-generated method stub
		return encapsulateQuestion(c,null);
	}
	public static HeartElement fromXML(String id, String question, String answer,
	    	String concept, String figure, String audio, Node node)    
			throws ProcedureParseException 
	    {    int age=0;
		  int points=0;
		if(id.equals("1"))
		{
			if(answer.equals("Female"))
			{ 
				if(age>=20 && age<=34)
				{
					points=points-7;
				}
				else if(age>=35 && age<=39)
				{
					points=points-3;
				}
				else if(age>=40 && age<=44)
				{
					points=points;
				}
				else if(age>=45 && age<=49)
				{
					points=points+3;
				}
				else if(age>=50 && age<=54)
				{
					points=points+6;
				}
				else if(age>=55 && age<=59)
				{
					points=points+8;
				}
				else if(age>=60 && age<=64)
				{
					points=points+10;
				}
				else if(age>=65 && age<=69)
				{
					points=points+12;
				}
				else if(age>=70 && age<=74)
				{
					points=points+14;
				}
				else if(age>=75 && age<=79)
				{
					points=points+16;
				}
			}
		}
	        return new HeartElement(id, question, answer, concept, figure, audio);
	    }

	@Override
	public ElementType getType() {
		// TODO Auto-generated method stub
		return ElementType.HEART;
	}

}


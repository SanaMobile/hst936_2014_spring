package com.example.stwo.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.example.stwo.R;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;

public class PeoplePullParser {

	private static final String LOGTAG = "rahul";
	
    public static final String COLUMN_ID = "peopleId";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_IMAGE = "image";
	public static final String COLUMN_AGE = "age";
	
	
	private People currentPeople  = null;
	private String currentTag = null;
	List<People> peoples = new ArrayList<People>();

	public List<People> parseXML(Context context) {

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			
			InputStream stream = context.getResources().openRawResource(R.raw.persons);
			xpp.setInput(stream, null);

			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					handleStartTag(xpp.getName());
				} else if (eventType == XmlPullParser.END_TAG) {
					currentTag = null;
				} else if (eventType == XmlPullParser.TEXT) {
					handleText(xpp.getText());
				}
				eventType = xpp.next();
			}

		} catch (NotFoundException e) {
			Log.d(LOGTAG, e.getMessage());
		} catch (XmlPullParserException e) {
			Log.d(LOGTAG, e.getMessage());
		} catch (IOException e) {
			Log.d(LOGTAG, e.getMessage());
		}

		return peoples;
	}

	private void handleText(String text) {
		String xmlText = text;
		if (currentPeople != null && currentTag != null) {
			if (currentTag.equals(COLUMN_ID)) {
				int id = Integer.parseInt(xmlText);
				currentPeople.setId(id);
			} 
			else if (currentTag.equals(COLUMN_EMAIL)) {
				currentPeople.setEmail(xmlText);
			}
			else if (currentTag.equals(COLUMN_AGE)) {
				int a= Integer.parseInt(xmlText);
				currentPeople.setAge(a);
			}
			else if (currentTag.equals(COLUMN_ADDRESS)) {
				currentPeople.setAddress(xmlText);
			}
			
		}
	}

	private void handleStartTag(String name) {
		if (name.equals("people")) {
			currentPeople = new People();
			peoples.add(currentPeople);
		}
		else {
			currentTag = name;
		}
	}
}

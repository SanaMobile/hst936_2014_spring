package org.sana.android.access;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.sana.R;
import org.sana.android.activity.BaseActivity;
import org.sana.android.content.core.ObserverWrapper;
import org.sana.android.provider.Observers;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.View;

public class Access {
	
	static Context context;
	static String currentPackageName;
	
	private static String TAG="Access";
	
	
	public static void fromXML(InputSource xml) throws IOException, 
	ParserConfigurationException, SAXException, AccessParseException 
	{
	
		
		long processingTime = System.currentTimeMillis();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setValidating(false);
	    dbf.setIgnoringComments(true);
	    dbf.setIgnoringElementContentWhitespace(true);
	    dbf.setNamespaceAware(false);
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document d = db.parse(xml);
	    Log.d(TAG+":Document", d.toString());
	    NodeList children = d.getChildNodes();
	    Log.d(TAG+":Children", children.toString());
	    Node accessNode = null;
	    for(int i=0; i<children.getLength(); i++) {
	        Node child = d.getChildNodes().item(i);
	        Log.d(TAG+":Child Node", child.getNodeName());
	        if(child.getNodeName().equals("Acl")) {
	            accessNode = child;
	            break;
	        }
	    }
	    if(accessNode == null) {
	    	throw new AccessParseException("Can't get Acl");
	    }
	    fromXML(accessNode);
	    
	    processingTime = System.currentTimeMillis() - processingTime;
	    Log.i(TAG, "Parsing Acl XML took " + processingTime + " milliseconds.");
	    
	}
	
	
	private static void fromXML(Node node) throws AccessParseException {
        
        if(!node.getNodeName().equals("Acl")) {
        	throw new AccessParseException("Acl got NodeName" 
        		+ node.getNodeName());
        	}
        
        NodeList nl = node.getChildNodes();
        for(int i=0; i<nl.getLength(); i++) {
            Node child = nl.item(i);
            if(child.getNodeName().equals("Role")) {  // And Check here for current Observer Role
                NodeList viewNodes = child.getChildNodes();
            	for(int j=0; j<viewNodes.getLength();j++)
            	{
            		Node viewNode = viewNodes.item(j);
            		
            		if(viewNode.getNodeName().equals("View"))
            		{
            			Node idNode = viewNode.getAttributes().getNamedItem("id");
            			if(idNode != null) {
            	        	Log.i(TAG, "Loading View from XML: " + idNode.getNodeValue());
            	            hideView(idNode.getNodeValue());
            	        }
            		}
            		
            	}

            }
        }
        
	}
	
	public static void hideViews(Context c, String packageName) throws AccessParseException
	{
		Access.context = c;
		Access.currentPackageName = packageName;
		
		InputStream is = null;
		is = c.getResources().openRawResource(R.raw.access);
        try {
			Access.fromXML(new InputSource(is));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void hideView(String resourceId)
	{
		try{
			View child = ((Activity)context).findViewById(context.getResources().getIdentifier(resourceId, "id", currentPackageName));
			child.setVisibility(View.GONE);
			Log.i(TAG, child.toString());
		}
		catch(Exception e)
		{
			Log.e(TAG, e.toString());
		}
	}

}

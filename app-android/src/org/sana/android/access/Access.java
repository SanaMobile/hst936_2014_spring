package org.sana.android.access;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.sana.R;
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
	private String author;
	private String metadata;
	
	static Context context;
	static String currentPackageName;
	//private List<AccessRole> roles;
	
	private static String TAG="Access";
	
	
	public static Access fromXML(InputSource xml) throws IOException, 
	ParserConfigurationException, SAXException 
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
	    /*if(procedureNode == null) {
	        throw new ProcedureParseException("Can't get procedure");
	    }*/
	    Access result = fromXML(accessNode);
	    
	    //fromXML(procedureNode);
	    processingTime = System.currentTimeMillis() - processingTime;
	    Log.i(TAG, "Parsing procedure XML took " + processingTime + " milliseconds.");
	    
	    return result;
	}
	
	
	private static Access fromXML(Node node) {
        
        if(!node.getNodeName().equals("Acl")) {
        //throw new ProcedureParseException("Procedure got NodeName" 
        //		+ node.getNodeName());
        	}
        
        //List<AccessRole> roles = new ArrayList<AccessRole>();
        NodeList nl = node.getChildNodes();
        
        //AccessRole role;
        //HashMap<String, ProcedureElement> elts = new HashMap<String, ProcedureElement>();
        for(int i=0; i<nl.getLength(); i++) {
            Node child = nl.item(i);
            if(child.getNodeName().equals("Role")) {
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
            	
            	
            	//role = AccessRole.fromXML(child);
                //elts.putAll(page.getElementMap());
                //roles.add(role);
            }
        }
            
        return null;
	}
	
	public static void hideViews(Context c, String packageName)
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
			Log.d("TEST", child.toString());
		}
		catch(Exception e)
		{
			Log.e("TEST Error", e.toString());
		}
	}

}

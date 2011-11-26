package org.component.partyspam;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

public class LocationInfo {
	private static final String RADIUS = "radius";
	private static final String LONGITUDE = "longitude";
	private static final String LATITUDE = "latitude";
	private static final String ADDITIONAL_LOCATION_DATA = "additionalLocationData";
	private ArrayList<String> mPartyInfosName = new ArrayList<String>();
	private ArrayList<String> mPartyInfosValue = new ArrayList<String>();
	private DocumentBuilderFactory mDbfac = null;
    private DocumentBuilder mDocBuilder = null;
	
    private final int LATITUDE_POS = 0;
    private final int LONGITUDE_POS = LATITUDE_POS + 1;
    private final int RADIUS_POS = LONGITUDE_POS + 1;
    private final int ADDITIONAL_DATA_POS = RADIUS_POS + 1;
    
	public LocationInfo(Double iLatitude, Double iLongitude, int iRadius, String iAdditionalLocationData) {
		initialize();
				
		mPartyInfosName.add(LATITUDE_POS,LATITUDE);
		mPartyInfosValue.add(LATITUDE_POS,iLatitude.toString());
		
		mPartyInfosName.add(LONGITUDE_POS,LONGITUDE);
		mPartyInfosValue.add(LONGITUDE_POS,iLongitude.toString());
					
		mPartyInfosName.add(RADIUS_POS,RADIUS);
		mPartyInfosValue.add(RADIUS_POS,String.valueOf(iRadius));
		
		mPartyInfosName.add(ADDITIONAL_DATA_POS,ADDITIONAL_LOCATION_DATA);
		mPartyInfosValue.add(ADDITIONAL_DATA_POS,iAdditionalLocationData);
	}
	
	public LocationInfo(String iXmlContent) {
		initialize();
		
		try {
			InputSource lIs = new InputSource();
		    lIs.setCharacterStream(new StringReader(iXmlContent));

		    Document lXmlDoc = mDocBuilder.parse(lIs);
		        
		    Node lRoot = lXmlDoc.getFirstChild();
		    NodeList lChildNodes = lRoot.getChildNodes();
		        
		    int k = 0;
		    for (int i = 0; i < lChildNodes.getLength(); i++) {
		    	Node lNode = (Node) lChildNodes.item(i);
		        if (!lNode.getNodeName().equals("#text")) {
		        	++k;
		        	mPartyInfosName.add(i-k, lNode.getNodeName());
		        	mPartyInfosValue.add(i-k, lNode.getTextContent());
		        	System.out.println((i-k) + "." + "name=" + lNode.getNodeName());
		        	System.out.println((i-k) + "." + "value=" + lNode.getTextContent());
		        }
		                 
		    }
		 }
		 catch (Exception e) {
			 System.out.println("XML -> data error");
			 e.printStackTrace();
		 }
	}
	
	public void initialize() {
		mDbfac = DocumentBuilderFactory.newInstance();
	    try {
			mDocBuilder = mDbfac.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toXML() {
		String xmlString = "";
		try {
                       
            Document lXmlDoc = mDocBuilder.newDocument();

            Element lPartyNode = lXmlDoc.createElement("locationQuery");
            lXmlDoc.appendChild(lPartyNode);

            for (int i=0; i<mPartyInfosName.size(); ++i) {
            	Element lNode = lXmlDoc.createElement(mPartyInfosName.get(i));
                lPartyNode.appendChild(lNode);
            	
                //add text in this node
                Text lText = lXmlDoc.createTextNode(mPartyInfosValue.get(i));
                lNode.appendChild(lText);            	
            }
            
            TransformerFactory lTransfac = TransformerFactory.newInstance();
            Transformer lTrans = lTransfac.newTransformer();
            lTrans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            lTrans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            StringWriter lSw = new StringWriter();
            StreamResult result = new StreamResult(lSw);
            DOMSource source = new DOMSource(lXmlDoc);
            lTrans.transform(source, result);
            xmlString = lSw.toString();

		} catch (Exception e) {
            System.out.println("HERE = " + e);
        }
		return xmlString;
	}
	
	/* Getters & setters for class attributes */	
	public Double getLatitude() {
		try {
			return Double.parseDouble(mPartyInfosValue.get(LATITUDE_POS));
		}catch (IndexOutOfBoundsException e) {
			return Double.MIN_VALUE;
		}
	}
	
	public void setLatitude(Double iLatitude) {
		mPartyInfosName.add(LATITUDE_POS, LATITUDE);
		mPartyInfosValue.add(LATITUDE_POS, iLatitude.toString());
	}
	
	public Double getLongitude() {
		try {
			return Double.parseDouble(mPartyInfosValue.get(LONGITUDE_POS));
		}catch (IndexOutOfBoundsException e) {
			return Double.MIN_VALUE;
		}
	}
	
	public void setLongitude(Double iLongitude) {
		mPartyInfosName.add(LONGITUDE_POS, LONGITUDE);
		mPartyInfosValue.add(LONGITUDE_POS, iLongitude.toString());
	}
		
	public int getRadius() {
		try {
			return Integer.parseInt(mPartyInfosValue.get(RADIUS_POS));
		}catch (IndexOutOfBoundsException e) {
			return Integer.MIN_VALUE;
		}
	}
	
	public void setRadius(int iRadius) {
		mPartyInfosName.add(RADIUS_POS, RADIUS);
		mPartyInfosValue.add(RADIUS_POS, String.valueOf(iRadius));
	}

	public int getAdditionalLocationData() {
		try {
			return Integer.parseInt(mPartyInfosValue.get(ADDITIONAL_DATA_POS));
		}catch (IndexOutOfBoundsException e) {
			return Integer.MIN_VALUE;
		}
	}
	
	public void setAdditionalLocationData(String iAdditionalLocationData) {
		mPartyInfosName.add(ADDITIONAL_DATA_POS, ADDITIONAL_LOCATION_DATA);
		mPartyInfosValue.add(ADDITIONAL_DATA_POS, String.valueOf(iAdditionalLocationData));
	}
}

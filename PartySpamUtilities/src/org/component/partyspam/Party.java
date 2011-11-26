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

public class Party {
	private static final String LOCATION = "location";
	private static final String END_HOUR = "endHour";
	private static final String START_HOUR = "startHour";
	private static final String END_DATE = "endDate";
	private static final String START_DATE = "startDate";
	private static final String USER_ID = "userId";
	private static final String PHONE = "phone";
	private static final String DESCRIPTION = "description";
	private ArrayList<String> mPartyInfosName = new ArrayList<String>();
	private ArrayList<String> mPartyInfosValue = new ArrayList<String>();
	private DocumentBuilderFactory mDbfac = null;
    private DocumentBuilder mDocBuilder = null;
	
    
    
    	private final int POS_DESCRIPTION = 0;
    	private final int POS_PHONE = POS_DESCRIPTION +1;
    	private final int POS_USER_ID = POS_PHONE +1;
    	private final int POS_START_DATE = POS_USER_ID +1;
    	private final int POS_END_DATE = POS_START_DATE +1;
    	private final int POS_START_HOUR = POS_END_DATE +1;
    	private final int POS_END_HOUR = POS_START_HOUR +1;
    	private final int POS_LOCATION = POS_END_HOUR +1;
    
    
	public Party(String iDescription, 
			String iPhone, 
			String iUserId, 
			String iStartDate, 
			String iEndDate, 
			String iStartHour, 
			String iEndHour, 
			LocationInfo iLocation) {
		initialize();
				
		mPartyInfosName.add(POS_DESCRIPTION,DESCRIPTION);
		mPartyInfosValue.add(POS_DESCRIPTION,iDescription);
		
		mPartyInfosName.add(POS_PHONE,PHONE);
		mPartyInfosValue.add(POS_PHONE,iPhone);
		
		mPartyInfosName.add(POS_USER_ID,USER_ID);
		mPartyInfosValue.add(POS_USER_ID,iUserId);
		
		mPartyInfosName.add(POS_START_DATE,START_DATE);
		mPartyInfosValue.add(POS_START_DATE,iStartDate);
		
		mPartyInfosName.add(POS_END_DATE,END_DATE);
		mPartyInfosValue.add(POS_END_DATE,iEndDate);
		
		mPartyInfosName.add(POS_START_HOUR,START_HOUR);
		mPartyInfosValue.add(POS_START_HOUR,iStartHour);
		
		mPartyInfosName.add(POS_END_HOUR,END_HOUR);
		mPartyInfosValue.add(POS_END_HOUR,iEndHour);
		
		mPartyInfosName.add(POS_LOCATION,LOCATION);
		mPartyInfosValue.add(POS_LOCATION,iLocation.toXML());
				
	}
	
	public Party(String iXmlContent) {
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
			e.printStackTrace();
		}
	}
	
	public String toXML() {
		String xmlString = "";
		try {
                       
            Document lXmlDoc = mDocBuilder.newDocument();

            Element lPartyNode = lXmlDoc.createElement("party");
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
	public String getDescription() {
		try {
			return mPartyInfosValue.get(POS_DESCRIPTION);
		}catch (IndexOutOfBoundsException e) {
			return "noDescription";
		}
	}
	
	public void setDescription(String iDescription) {
		mPartyInfosName.add(POS_DESCRIPTION, DESCRIPTION);
		mPartyInfosValue.add(POS_DESCRIPTION, iDescription);
	}
	
	public String getPhone() {
		try {
			return mPartyInfosValue.get(POS_PHONE);
		}catch (IndexOutOfBoundsException e) {
			return "noPhone";
		}
	}
	
	public void setPhone(String iPhone) {
		mPartyInfosName.add(POS_PHONE, PHONE);
		mPartyInfosValue.add(POS_PHONE, iPhone);
	}
	
	public String getUserId() {
		try {
			return mPartyInfosValue.get(POS_USER_ID);
		}catch (IndexOutOfBoundsException e) {
			return "noUser";
		}
	}
	
	public void setUserId(String iUserId) {
		mPartyInfosName.add(POS_USER_ID, USER_ID);
		mPartyInfosValue.add(POS_USER_ID, iUserId);
	}
	
	public String getStartDate() {
		try {
			return mPartyInfosValue.get(POS_START_DATE);
		}catch (IndexOutOfBoundsException e) {
			return "noStartDate";
		}
	}
	
	public void setStartDate(String iStartDate) {
		mPartyInfosName.add(POS_START_DATE, START_DATE);
		mPartyInfosValue.add(POS_START_DATE, iStartDate);
	}
	
	public String getEndDate() {
		try {
			return mPartyInfosValue.get(POS_END_DATE);
		}catch (IndexOutOfBoundsException e) {
			return "noEndDate";
		}
	}
	
	public void setEndDate(String iEndDate) {
		mPartyInfosName.add(POS_END_DATE, END_DATE);
		mPartyInfosValue.add(POS_END_DATE, iEndDate);
	}
	
	public String getStartHour() {
		try {
			return mPartyInfosValue.get(POS_START_HOUR);
		}catch (IndexOutOfBoundsException e) {
			return "noStartHour";
		}
	}
	
	public void setStartHour(String iStartHour) {
		mPartyInfosName.add(POS_START_HOUR, START_HOUR);
		mPartyInfosValue.add(POS_START_HOUR, iStartHour);
	}
	
	public String getEndHour() {
		try {
			return mPartyInfosValue.get(POS_END_HOUR);
		}catch (IndexOutOfBoundsException e) {
			return "noEndHour";
		}
	}
	
	public void setEndHour(String iEndHour) {
		mPartyInfosName.add(POS_END_HOUR, END_HOUR);
		mPartyInfosValue.add(POS_END_HOUR, iEndHour);
	}
	
	public LocationInfo getLocation() {
		try {
			return new LocationInfo(mPartyInfosValue.get(POS_LOCATION));
		}catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public void setLocation(LocationInfo iLocation) {
		mPartyInfosName.add(POS_LOCATION, LOCATION);
		mPartyInfosValue.add(POS_LOCATION, iLocation.toXML());
	}
}

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

public class EventListStructure {
	private static final String PHONE_ID = "phone_id";
	private static final String PARTY_ID = "party_id";
	private ArrayList<String> mPartyInfosName = new ArrayList<String>();
	private ArrayList<String> mPartyInfosValue = new ArrayList<String>();
	private DocumentBuilderFactory mDbfac = null;
    private DocumentBuilder mDocBuilder = null;
	private final int PARTY_ID_POS = 0;
	private final int PHONE_ID_POS = PARTY_ID_POS + 1;
	
	public EventListStructure(String iPartyId, String iPhoneId) {
		initialize();
				
		mPartyInfosName.add(PARTY_ID_POS,PARTY_ID);
		mPartyInfosValue.add(PARTY_ID_POS, iPartyId);
		
		mPartyInfosName.add(PHONE_ID_POS,PHONE_ID);
		mPartyInfosValue.add(PHONE_ID_POS,iPhoneId);		
	}
	
	public EventListStructure(String iXmlContent) {
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

            Element lPartyNode = lXmlDoc.createElement("attendPartyStructure");
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
	public String getPartyId() {
		try {
			return mPartyInfosValue.get(PARTY_ID_POS);
		}catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public void setPartyId(String iPartyId) {
		mPartyInfosName.set(PARTY_ID_POS, PARTY_ID);
		mPartyInfosValue.set(PARTY_ID_POS, iPartyId);
	}
	
	public String getPhoneId() {
		try {
			return mPartyInfosValue.get(PHONE_ID_POS);
		}catch (IndexOutOfBoundsException e) {
			return "noPhone";
		}
	}
	
	public void setPhoneId(String iPhoneId) {
		mPartyInfosName.set(PHONE_ID_POS, PHONE_ID);
		mPartyInfosValue.set(PHONE_ID_POS, iPhoneId);
	}
}
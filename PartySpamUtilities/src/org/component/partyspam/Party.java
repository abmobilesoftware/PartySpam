package org.component.partyspam;

import java.io.IOException;
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
import org.xml.sax.SAXException;

public class Party {
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String LOCATION = "location";
	public static final String IMAGE = "image";
	public static final String ATTENDEES = "nr_of_attendees";
	public static final String START_HOUR = "startHour";
	public static final String END_HOUR = "endHour";
	public static final String END_DATE = "endDate";
	public static final String START_DATE = "startDate";
	public static final String USER_ID = "userId";
	public static final String CONTACT_DATA = "contactData";
	public static final String DESCRIPTION = "description";
	public static final String MODIFIED_DATE = "modified_date";
	private ArrayList<String> mPartyInfosName = new ArrayList<String>();
	private ArrayList<String> mPartyInfosValue = new ArrayList<String>();
	private DocumentBuilderFactory mDbfac = null;
	private DocumentBuilder mDocBuilder = null;

	public static final int POS_ID = 0;
	public static final int POS_TITLE = POS_ID + 1;
	public static final int POS_DESCRIPTION = POS_TITLE + 1;
	public static final int POS_CONTACT_DATA = POS_DESCRIPTION + 1;
	public static final int POS_USER_ID = POS_CONTACT_DATA + 1;
	public static final int POS_NR_OF_ATTENDEES = POS_USER_ID + 1;
	public static final int POS_START_DATE = POS_NR_OF_ATTENDEES + 1;
	public static final int POS_END_DATE = POS_START_DATE + 1;
	public static final int POS_START_HOUR = POS_END_DATE + 1;
	public static final int POS_END_HOUR = POS_START_HOUR + 1;
	public static final int POS_LOCATION = POS_END_HOUR + 1;
	public static final int POS_IMAGE = POS_LOCATION + 1;
	public static final int POS_MODIFIED_DATE = POS_IMAGE + 1;
	/**
	 * 
	 * @param iTitle
	 * @param iDescription
	 * @param iContactData
	 * @param iUserId
	 * @param iStartDate
	 * @param iEndDate
	 * @param iStartHour: the format is 1545 = 15:45            
	 */
	public Party(int iId,String iTitle, String iDescription, String iContactData,
			String iUserId, int iAttendees, String iStartDate, String iEndDate, int iStartHour,
			int iEndHour, LocationInfo iLocation, String iImage,double iModifiedDate) {
		initialize();

		mPartyInfosName.add(POS_ID, ID);
		mPartyInfosValue.add(POS_ID, String.valueOf(iId));
		
		mPartyInfosName.add(POS_TITLE, TITLE);
		mPartyInfosValue.add(POS_TITLE, iTitle);

		mPartyInfosName.add(POS_DESCRIPTION, DESCRIPTION);
		mPartyInfosValue.add(POS_DESCRIPTION, iDescription);

		mPartyInfosName.add(POS_CONTACT_DATA, CONTACT_DATA);
		mPartyInfosValue.add(POS_CONTACT_DATA, iContactData);

		mPartyInfosName.add(POS_USER_ID, USER_ID);
		mPartyInfosValue.add(POS_USER_ID, iUserId);

		mPartyInfosName.add(POS_NR_OF_ATTENDEES, ATTENDEES);
		mPartyInfosValue.add(POS_NR_OF_ATTENDEES, String.valueOf(iAttendees));
		
		mPartyInfosName.add(POS_START_DATE, START_DATE);
		mPartyInfosValue.add(POS_START_DATE, iStartDate);

		mPartyInfosName.add(POS_END_DATE, END_DATE);
		mPartyInfosValue.add(POS_END_DATE, iEndDate);

		mPartyInfosName.add(POS_START_HOUR, START_HOUR);
		mPartyInfosValue.add(POS_START_HOUR, String.valueOf(iStartHour));

		mPartyInfosName.add(POS_END_HOUR, END_HOUR);
		mPartyInfosValue.add(POS_END_HOUR, String.valueOf(iEndHour));

		mPartyInfosName.add(POS_LOCATION, LOCATION);
		mPartyInfosValue.add(POS_LOCATION, iLocation.toXML());

		mPartyInfosName.add(POS_IMAGE, IMAGE);
		mPartyInfosValue.add(POS_IMAGE, iImage);
		
		mPartyInfosName.add(POS_MODIFIED_DATE, MODIFIED_DATE);
		mPartyInfosValue.add(POS_MODIFIED_DATE, String.valueOf(iModifiedDate));
	}

	public Party(String iXmlContent) throws org.xml.sax.SAXParseException,SAXException,IOException {
		initialize();

		try {
			InputSource lIs = new InputSource();
			lIs.setCharacterStream(new StringReader(iXmlContent));

			//it the xml is not valid a org.xml.sax.SAXParseException will be thrown
			Document lXmlDoc = mDocBuilder.parse(lIs);

			Node lRoot = lXmlDoc.getFirstChild();
			NodeList lChildNodes = lRoot.getChildNodes();

			int k = 0;
			for (int i = 0; i < lChildNodes.getLength(); i++) {
				Node lNode = (Node) lChildNodes.item(i);
				if (!lNode.getNodeName().equals("#text")) {
					++k;
					mPartyInfosName.add(i - k, lNode.getNodeName());
					mPartyInfosValue.add(i - k, lNode.getTextContent());
					System.out.println((i - k) + "." + "name="
							+ lNode.getNodeName());
					System.out.println((i - k) + "." + "value="
							+ lNode.getTextContent());
				}

			}
		} catch (org.xml.sax.SAXParseException e) {
			System.out.println("XML -> data error");
			e.printStackTrace();
			throw e;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

			for (int i = 0; i < mPartyInfosName.size(); ++i) {
				Element lNode = lXmlDoc.createElement(mPartyInfosName.get(i));
				lPartyNode.appendChild(lNode);

				// add text in this node
				Text lText = lXmlDoc.createTextNode(mPartyInfosValue.get(i));
				lNode.appendChild(lText);
			}

			TransformerFactory lTransfac = TransformerFactory.newInstance();
			Transformer lTrans = lTransfac.newTransformer();
			lTrans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			lTrans.setOutputProperty(OutputKeys.INDENT, "yes");

			// create string from xml tree
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

	public int getId() {
		try {
			return Integer.parseInt(mPartyInfosValue.get(POS_ID));
		} catch (IndexOutOfBoundsException e) {
			return Integer.MIN_VALUE;
		}
	}

	public void setId(int iId) {
		mPartyInfosName.set(POS_ID, ID);
		mPartyInfosValue.set(POS_ID, String.valueOf(iId));
	}
	
	public String getTitle() {
		try {
			return mPartyInfosValue.get(POS_TITLE);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void setTitle(String iTitle) {
		mPartyInfosName.set(POS_TITLE, TITLE);
		mPartyInfosValue.set(POS_TITLE, iTitle);
	}

	public String getDescription() {
		try {
			return mPartyInfosValue.get(POS_DESCRIPTION);
		} catch (IndexOutOfBoundsException e) {
			return "noDescription";
		}
	}

	public void setDescription(String iDescription) {
		mPartyInfosName.set(POS_DESCRIPTION, DESCRIPTION);
		mPartyInfosValue.set(POS_DESCRIPTION, iDescription);
	}

	public String getContactData() {
		try {
			return mPartyInfosValue.get(POS_CONTACT_DATA);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void setContactData(String iContactData) {
		mPartyInfosName.set(POS_CONTACT_DATA, CONTACT_DATA);
		mPartyInfosValue.set(POS_CONTACT_DATA, iContactData);
	}

	public String getUserId() {
		try {
			return mPartyInfosValue.get(POS_USER_ID);
		} catch (IndexOutOfBoundsException e) {
			return "noUser";
		}
	}

	public void setUserId(String iUserId) {
		mPartyInfosName.set(POS_USER_ID, USER_ID);
		mPartyInfosValue.set(POS_USER_ID, iUserId);
	}

	public String getStartDate() {
		try {
			return mPartyInfosValue.get(POS_START_DATE);
		} catch (IndexOutOfBoundsException e) {
			return "noStartDate";
		}
	}

	public void setStartDate(String iStartDate) {
		mPartyInfosName.set(POS_START_DATE, START_DATE);
		mPartyInfosValue.set(POS_START_DATE, iStartDate);
	}

	public String getEndDate() {
		try {
			return mPartyInfosValue.get(POS_END_DATE);
		} catch (IndexOutOfBoundsException e) {
			return "noEndDate";
		}
	}

	public void setEndDate(String iEndDate) {
		mPartyInfosName.set(POS_END_DATE, END_DATE);
		mPartyInfosValue.set(POS_END_DATE, iEndDate);
	}

	public int getStartHour() {
		try {
			return Integer.parseInt(mPartyInfosValue.get(POS_START_HOUR));
		} catch (IndexOutOfBoundsException e) {
			return Integer.MIN_VALUE;
		}
	}

	public void setStartHour(int iStartHour) {
		mPartyInfosName.set(POS_START_HOUR, START_HOUR);
		mPartyInfosValue.set(POS_START_HOUR, String.valueOf(iStartHour));
	}

	public int getEndHour() {
		try {
			return Integer.parseInt(mPartyInfosValue.get(POS_END_HOUR));
		} catch (IndexOutOfBoundsException e) {
			return Integer.MIN_VALUE;
		}
	}

	public void setEndHour(int iEndHour) {
		mPartyInfosName.set(POS_END_HOUR, END_HOUR);
		mPartyInfosValue.set(POS_END_HOUR, String.valueOf(iEndHour));
	}

	public LocationInfo getLocation() {
		try {
			return new LocationInfo(mPartyInfosValue.get(POS_LOCATION));
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void setLocation(LocationInfo iLocation) {
		mPartyInfosName.set(POS_LOCATION, LOCATION);
		mPartyInfosValue.set(POS_LOCATION, iLocation.toXML());
	}

	public String getImage() {
		try {
			return mPartyInfosValue.get(POS_IMAGE);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void setImage(String iImage) {
		mPartyInfosName.set(POS_IMAGE, IMAGE);
		mPartyInfosValue.set(POS_IMAGE, iImage);
	}

	public int getNrOfAttendees() {
		try {
			return Integer.parseInt(mPartyInfosValue.get(POS_NR_OF_ATTENDEES));
		} catch (IndexOutOfBoundsException e) {
			return Integer.MIN_VALUE;
		}
	}

	public void setNrOfAttendess(int iNrOfAttendees) {
		mPartyInfosName.set(POS_NR_OF_ATTENDEES, ATTENDEES);
		mPartyInfosValue.set(POS_NR_OF_ATTENDEES,
				String.valueOf(iNrOfAttendees));
	}
	
	public Double getModifiedDate() {
		try {
			return Double.parseDouble(mPartyInfosValue.get(POS_MODIFIED_DATE));
		} catch (IndexOutOfBoundsException e) {
			return Double.MIN_VALUE;
		}
	}

	public void setModifiedDate(Double iModifiedDate) {
		mPartyInfosName.set(POS_MODIFIED_DATE, MODIFIED_DATE);
		mPartyInfosValue.set(POS_MODIFIED_DATE, String.valueOf(iModifiedDate));
	}
}

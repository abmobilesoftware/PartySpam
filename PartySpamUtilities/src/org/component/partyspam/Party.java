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
	private static final String TITLE = "title";
	private static final String LOCATION = "location";
	private static final String IMAGE = "image";
	private static final String ATTENDEES = "nr_of_attendees";
	private static final String START_HOUR = "startHour";
	private static final String END_HOUR = "endHour";
	private static final String END_DATE = "endDate";
	private static final String START_DATE = "startDate";
	private static final String USER_ID = "userId";
	private static final String CONTACT_DATA = "contactData";
	private static final String DESCRIPTION = "description";
	private ArrayList<String> mPartyInfosName = new ArrayList<String>();
	private ArrayList<String> mPartyInfosValue = new ArrayList<String>();
	private DocumentBuilderFactory mDbfac = null;
	private DocumentBuilder mDocBuilder = null;

	private final int POS_TITLE = 0;
	private final int POS_DESCRIPTION = POS_TITLE + 1;
	private final int POS_CONTACT_DATA = POS_DESCRIPTION + 1;
	private final int POS_USER_ID = POS_CONTACT_DATA + 1;
	private final int POS_NR_OF_ATTENDEES = POS_USER_ID + 1;
	private final int POS_START_DATE = POS_NR_OF_ATTENDEES + 1;
	private final int POS_END_DATE = POS_START_DATE + 1;
	private final int POS_START_HOUR = POS_END_DATE + 1;
	private final int POS_END_HOUR = POS_START_HOUR + 1;
	private final int POS_LOCATION = POS_END_HOUR + 1;
	private final int POS_IMAGE = POS_LOCATION + 1;
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
	public Party(String iTitle, String iDescription, String iContactData,
			String iUserId, int iAttendees, String iStartDate, String iEndDate, int iStartHour,
			int iEndHour, LocationInfo iLocation, String iImage) {
		initialize();

		mPartyInfosName.add(POS_TITLE, TITLE);
		mPartyInfosValue.add(POS_TITLE, iTitle);

		mPartyInfosName.add(POS_DESCRIPTION, DESCRIPTION);
		mPartyInfosValue.add(POS_DESCRIPTION, iDescription);

		mPartyInfosName.add(POS_CONTACT_DATA, CONTACT_DATA);
		mPartyInfosValue.add(POS_CONTACT_DATA, iContactData);

		mPartyInfosName.add(POS_USER_ID, USER_ID);
		mPartyInfosValue.add(POS_USER_ID, iUserId);

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

		mPartyInfosName.add(POS_NR_OF_ATTENDEES, ATTENDEES);
		mPartyInfosValue.add(POS_NR_OF_ATTENDEES, String.valueOf(iAttendees));

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
					mPartyInfosName.add(i - k, lNode.getNodeName());
					mPartyInfosValue.add(i - k, lNode.getTextContent());
					System.out.println((i - k) + "." + "name="
							+ lNode.getNodeName());
					System.out.println((i - k) + "." + "value="
							+ lNode.getTextContent());
				}

			}
		} catch (Exception e) {
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

	public String getTitle() {
		try {
			return mPartyInfosValue.get(POS_TITLE);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void setTitle(String iTitle) {
		mPartyInfosName.add(POS_TITLE, TITLE);
		mPartyInfosValue.add(POS_TITLE, iTitle);
	}

	public String getDescription() {
		try {
			return mPartyInfosValue.get(POS_DESCRIPTION);
		} catch (IndexOutOfBoundsException e) {
			return "noDescription";
		}
	}

	public void setDescription(String iDescription) {
		mPartyInfosName.add(POS_DESCRIPTION, DESCRIPTION);
		mPartyInfosValue.add(POS_DESCRIPTION, iDescription);
	}

	public String getContactData() {
		try {
			return mPartyInfosValue.get(POS_CONTACT_DATA);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void setContactData(String iContactData) {
		mPartyInfosName.add(POS_CONTACT_DATA, CONTACT_DATA);
		mPartyInfosValue.add(POS_CONTACT_DATA, iContactData);
	}

	public String getUserId() {
		try {
			return mPartyInfosValue.get(POS_USER_ID);
		} catch (IndexOutOfBoundsException e) {
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
		} catch (IndexOutOfBoundsException e) {
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
		} catch (IndexOutOfBoundsException e) {
			return "noEndDate";
		}
	}

	public void setEndDate(String iEndDate) {
		mPartyInfosName.add(POS_END_DATE, END_DATE);
		mPartyInfosValue.add(POS_END_DATE, iEndDate);
	}

	public int getStartHour() {
		try {
			return Integer.parseInt(mPartyInfosValue.get(POS_START_HOUR));
		} catch (IndexOutOfBoundsException e) {
			return Integer.MIN_VALUE;
		}
	}

	public void setStartHour(int iStartHour) {
		mPartyInfosName.add(POS_START_HOUR, START_HOUR);
		mPartyInfosValue.add(POS_START_HOUR, String.valueOf(iStartHour));
	}

	public int getEndHour() {
		try {
			return Integer.parseInt(mPartyInfosValue.get(POS_END_HOUR));
		} catch (IndexOutOfBoundsException e) {
			return Integer.MIN_VALUE;
		}
	}

	public void setEndHour(int iEndHour) {
		mPartyInfosName.add(POS_END_HOUR, END_HOUR);
		mPartyInfosValue.add(POS_END_HOUR, String.valueOf(iEndHour));
	}

	public LocationInfo getLocation() {
		try {
			return new LocationInfo(mPartyInfosValue.get(POS_LOCATION));
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void setLocation(LocationInfo iLocation) {
		mPartyInfosName.add(POS_LOCATION, LOCATION);
		mPartyInfosValue.add(POS_LOCATION, iLocation.toXML());
	}

	public String getImage() {
		try {
			return mPartyInfosValue.get(POS_IMAGE);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public void setImage(String iImage) {
		mPartyInfosName.add(POS_IMAGE, IMAGE);
		mPartyInfosValue.add(POS_IMAGE, iImage);
	}

	public int getNrOfAttendees() {
		try {
			return Integer.parseInt(mPartyInfosValue.get(POS_NR_OF_ATTENDEES));
		} catch (IndexOutOfBoundsException e) {
			return Integer.MIN_VALUE;
		}
	}

	public void setNrOfAttendess(int iNrOfAttendees) {
		mPartyInfosName.add(POS_NR_OF_ATTENDEES, ATTENDEES);
		mPartyInfosValue.add(POS_NR_OF_ATTENDEES,
				String.valueOf(iNrOfAttendees));
	}
}

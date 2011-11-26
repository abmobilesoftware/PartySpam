package classUnitTests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.component.partyspam.LocationInfo;
import org.component.partyspam.Party;
import org.junit.Test;

/**
 * 
 */

/**
 * @author Ando
 * 
 */
public class partyTest {

	/**
	 * Test method for
	 * {@link org.component.partyspam.Party#Party(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, int, int, org.component.partyspam.LocationInfo, java.lang.String)}
	 * .
	 */
	@Test
	public void constructorMultipleParameters_passingValidParameters_validObjectIsCreated() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		// what does "a valid" Party object really mean?
		// through reflection we check the state of the internal objects - in
		// this case the 2 string array lists
		checkInternalIntegrityOfPartyClass(lDemoLocationInfo, lEventTitle,
				lEventDescription, lContactDetails, lUserId, lNrOfAttendees,
				lStartDate, lEndDate, lStartHour, lEndHour, lEventImage, lParty);

	}

	private void checkInternalIntegrityOfPartyClass(
			LocationInfo lDemoLocationInfo, String lEventTitle,
			String lEventDescription, String lContactDetails, String lUserId,
			int lNrOfAttendees, String lStartDate, String lEndDate,
			int lStartHour, int lEndHour, String lEventImage, Party lParty) {
		Class lClassPartyObject = lParty.getClass();
		Field field;
		try {
			field = lClassPartyObject.getDeclaredField("mPartyInfosName");
			field.setAccessible(true);
			ArrayList<String> lPartyInfosName = (ArrayList<String>) field
					.get(lParty);

			// this is what we expect tin the Names string array list - we have
			// exposed the constants to avoid duplication & typeos
			ArrayList<String> lExpectedInfosName = new ArrayList<String>();
			lExpectedInfosName.add(Party.POS_TITLE, Party.TITLE);
			lExpectedInfosName.add(Party.POS_DESCRIPTION, Party.DESCRIPTION);
			lExpectedInfosName.add(Party.POS_CONTACT_DATA, Party.CONTACT_DATA);
			lExpectedInfosName.add(Party.POS_USER_ID, Party.USER_ID);
			lExpectedInfosName.add(Party.POS_NR_OF_ATTENDEES, Party.ATTENDEES);
			lExpectedInfosName.add(Party.POS_START_DATE, Party.START_DATE);
			lExpectedInfosName.add(Party.POS_END_DATE, Party.END_DATE);
			lExpectedInfosName.add(Party.POS_START_HOUR, Party.START_HOUR);
			lExpectedInfosName.add(Party.POS_END_HOUR, Party.END_HOUR);
			lExpectedInfosName.add(Party.POS_LOCATION, Party.LOCATION);
			lExpectedInfosName.add(Party.POS_IMAGE, Party.IMAGE);

			assertArrayEquals(lExpectedInfosName.toArray(),
					lPartyInfosName.toArray());

			field = lClassPartyObject.getDeclaredField("mPartyInfosValue");
			field.setAccessible(true);
			ArrayList<String> lPartyInfosValue = (ArrayList<String>) field
					.get(lParty);

			ArrayList<String> lExpectedInfoValue = new ArrayList<String>();
			lExpectedInfoValue.add(Party.POS_TITLE, lEventTitle);
			lExpectedInfoValue.add(Party.POS_DESCRIPTION, lEventDescription);
			lExpectedInfoValue.add(Party.POS_CONTACT_DATA, lContactDetails);
			lExpectedInfoValue.add(Party.POS_USER_ID, lUserId);
			lExpectedInfoValue.add(Party.POS_NR_OF_ATTENDEES,
					String.valueOf(lNrOfAttendees));
			lExpectedInfoValue.add(Party.POS_START_DATE, lStartDate);
			lExpectedInfoValue.add(Party.POS_END_DATE, lEndDate);
			lExpectedInfoValue.add(Party.POS_START_HOUR,
					String.valueOf(lStartHour));
			lExpectedInfoValue
					.add(Party.POS_END_HOUR, String.valueOf(lEndHour));
			lExpectedInfoValue.add(Party.POS_LOCATION,
					lDemoLocationInfo.toXML());
			lExpectedInfoValue.add(Party.POS_IMAGE, lEventImage);

			assertArrayEquals(lExpectedInfoValue.toArray(),
					lPartyInfosValue.toArray());

		} catch (SecurityException e) {
			fail("Unexpected exception" + e.getMessage());
		} catch (NoSuchFieldException e) {
			fail("Unexpected exception" + e.getMessage());
		} catch (IllegalArgumentException e) {
			fail("Unexpected exception" + e.getMessage());
		} catch (IllegalAccessException e) {
			fail("Unexpected exception" + e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link org.component.partyspam.Party#Party(java.lang.String)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void constructorFromXML_passingValidXML_validObjectIsCreated()
			throws Exception {
		// this is the xml generated from a valid party object
		String lStartXML = "<party>\r\n<title>DemoTitle</title>\r\n<description>DemoDescription</description>\r\n<contactData>DemoContactDetails</contactData>\r\n<userId>DemoUserID</userId>\r\n<nr_of_attendees>14</nr_of_attendees>\r\n<startDate>26-11-2011</startDate>\r\n<endDate>26-11-2011</endDate>\r\n<startHour>1040</startHour>\r\n<endHour>1045</endHour>\r\n<location>&lt;locationQuery&gt;&#13;\r\n&lt;latitude&gt;20.2&lt;/latitude&gt;&#13;\r\n&lt;longitude&gt;40.4&lt;/longitude&gt;&#13;\r\n&lt;radius&gt;100&lt;/radius&gt;&#13;\r\n&lt;additionalLocationData&gt;DemoAdditionalLocationData&lt;/additionalLocationData&gt;&#13;\r\n&lt;/locationQuery&gt;&#13;\r\n</location>\r\n<image>DemoImage</image>\r\n</party>\r\n";
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		Party lParty = new Party(lStartXML);
		checkInternalIntegrityOfPartyClass(lDemoLocationInfo, lEventTitle,
				lEventDescription, lContactDetails, lUserId, lNrOfAttendees,
				lStartDate, lEndDate, lStartHour, lEndHour, lEventImage, lParty);
	}

	/**
	 * Test method for
	 * {@link org.component.partyspam.Party#Party(java.lang.String)}.
	 * 
	 * @throws Exception
	 */
	@Test(expected = org.xml.sax.SAXParseException.class)
	public void constructorFromXML_passingInvalidXML_exceptionIsThrown()
			throws Exception {
		String lStartXML = "party>\r\n<title>DemoTitle</title>\r\n<description>DemoDescription</description>\r\n<contactData>DemoContactDetails</contactData>\r\n<userId>DemoUserID</userId>\r\n<nr_of_attendees>14</nr_of_attendees>\r\n<startDate>26-11-2011</startDate>\r\n<endDate>26-11-2011</endDate>\r\n<startHour>1040</startHour>\r\n<endHour>1045</endHour>\r\n<location>&lt;locationQuery&gt;&#13;\r\n&lt;latitude&gt;20.2&lt;/latitude&gt;&#13;\r\n&lt;longitude&gt;40.4&lt;/longitude&gt;&#13;\r\n&lt;radius&gt;100&lt;/radius&gt;&#13;\r\n&lt;additionalLocationData&gt;DemoAdditionalLocationData&lt;/additionalLocationData&gt;&#13;\r\n&lt;/locationQuery&gt;&#13;\r\n</location>\r\n<image>DemoImage</image>\r\n</party>\r\n";
		Party lParty = new Party(lStartXML);
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#toXML()}.
	 */
	@Test
	public void toXML_callOnValidPartyObject_resultingStringsAreEqual() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lResultXml = lParty.toXML();
		String lExpectedXML = "<party>\r\n<title>DemoTitle</title>\r\n<description>DemoDescription</description>\r\n<contactData>DemoContactDetails</contactData>\r\n<userId>DemoUserID</userId>\r\n<nr_of_attendees>14</nr_of_attendees>\r\n<startDate>26-11-2011</startDate>\r\n<endDate>26-11-2011</endDate>\r\n<startHour>1040</startHour>\r\n<endHour>1045</endHour>\r\n<location>&lt;locationQuery&gt;&#13;\r\n&lt;latitude&gt;20.2&lt;/latitude&gt;&#13;\r\n&lt;longitude&gt;40.4&lt;/longitude&gt;&#13;\r\n&lt;radius&gt;100&lt;/radius&gt;&#13;\r\n&lt;additionalLocationData&gt;DemoAdditionalLocationData&lt;/additionalLocationData&gt;&#13;\r\n&lt;/locationQuery&gt;&#13;\r\n</location>\r\n<image>DemoImage</image>\r\n</party>\r\n";
		assertArrayEquals(lExpectedXML.toCharArray(), lResultXml.toCharArray());
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#getTitle()}.
	 */
	@Test
	public void getTitle_validObject_returnsExpectedResult() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lResultedEventTitle = lParty.getTitle();
		assertArrayEquals(lEventTitle.toCharArray(),
				lResultedEventTitle.toCharArray());
	}

	/**
	 * Test method for
	 * {@link org.component.partyspam.Party#setTitle(java.lang.String)}.
	 */
	@Test
	public void setTitle_setValueToNewValue_internalFieldIsModified() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lExpectedTitle = "NewDemoTitle";
		lParty.setTitle(lExpectedTitle);
		int lTitleIndex = Party.POS_TITLE;
		testInternalArrayMemberValue(lParty, lExpectedTitle, lTitleIndex);
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#getDescription()}.
	 */
	@Test
	public void getDescription_validObject_returnsExpectedResult() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lResultedEventDescription = lParty.getDescription();
		assertArrayEquals(lEventDescription.toCharArray(),
				lResultedEventDescription.toCharArray());
	}

	/**
	 * Test method for
	 * {@link org.component.partyspam.Party#setDescription(java.lang.String)}.
	 */
	@Test
	public void setDescription_setToNewValue_internalFieldIsModified() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lExpectedDescription = "NewDemoDescription";
		lParty.setDescription(lExpectedDescription);
		int lDescriptionIndex = Party.POS_DESCRIPTION;
		testInternalArrayMemberValue(lParty, lExpectedDescription,
				lDescriptionIndex);
	}

	private void testInternalArrayMemberValue(Party lParty,
			String lExpectedString, int lExpectedStringIndex) {
		Class lClassPartyObject = lParty.getClass();
		Field field;
		try {
			field = lClassPartyObject.getDeclaredField("mPartyInfosValue");
			field.setAccessible(true);
			ArrayList<String> lPartyInfosValue = (ArrayList<String>) field
					.get(lParty);
			String lResultValue = lPartyInfosValue.get(lExpectedStringIndex);
			assertArrayEquals(lExpectedString.toCharArray(),
					lResultValue.toCharArray());

		} catch (SecurityException e) {
			fail("Unexpected exception" + e.getMessage());
		} catch (NoSuchFieldException e) {
			fail("Unexpected exception" + e.getMessage());
		} catch (IllegalArgumentException e) {
			fail("Unexpected exception" + e.getMessage());
		} catch (IllegalAccessException e) {
			fail("Unexpected exception" + e.getMessage());
		}
	}
	
	private void testInternalArrayMemberValue(Party lParty,
			int lExpectedInt, int lExpectedIntIndex) {
		Class lClassPartyObject = lParty.getClass();
		Field field;
		try {
			field = lClassPartyObject.getDeclaredField("mPartyInfosValue");
			field.setAccessible(true);
			ArrayList<String> lPartyInfosValue = (ArrayList<String>) field
					.get(lParty);
			int lResultValue =  Integer.parseInt(lPartyInfosValue.get(lExpectedIntIndex));
			assertEquals(lExpectedInt,lResultValue);

		} catch (SecurityException e) {
			fail("Unexpected exception" + e.getMessage());
		} catch (NoSuchFieldException e) {
			fail("Unexpected exception" + e.getMessage());
		} catch (IllegalArgumentException e) {
			fail("Unexpected exception" + e.getMessage());
		} catch (IllegalAccessException e) {
			fail("Unexpected exception" + e.getMessage());
		}
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#getContactData()}.
	 */
	@Test
	public void getContactData_validObject_returnsExpectedResult() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lResultedContact = lParty.getContactData();
		assertArrayEquals(lContactDetails.toCharArray(),
				lResultedContact.toCharArray());
	}

	/**
	 * Test method for
	 * {@link org.component.partyspam.Party#setContactData(java.lang.String)}.
	 */
	@Test
	public void setContactData_setToNewValue_internalFieldIsModified() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lExpectedContactData = "NewDemoContactDetails";
		lParty.setContactData(lExpectedContactData);
		int lContactDataIndex = Party.POS_CONTACT_DATA;
		testInternalArrayMemberValue(lParty, lExpectedContactData,
				lContactDataIndex);
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#getUserId()}.
	 */
	@Test
	public void getUserId_validObject_returnsExpectedResult() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lResultedUserId = lParty.getUserId();
		assertArrayEquals(lUserId.toCharArray(),
				lResultedUserId.toCharArray());
	}

	/**
	 * Test method for
	 * {@link org.component.partyspam.Party#setUserId(java.lang.String)}.
	 */
	@Test
	public void setUserId_setToNewValue_internaFieldIsModified() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lExpectedUserId = "NewDemoUserId";
		lParty.setUserId(lExpectedUserId);
		int lUserIdIndex = Party.POS_USER_ID;
		testInternalArrayMemberValue(lParty, lExpectedUserId,
				lUserIdIndex);;
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#getStartDate()}.
	 */
	@Test
	public void getStartDate_validObject_returnsExpectedResult() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lResultedStartDate = lParty.getStartDate();
		assertArrayEquals(lStartDate.toCharArray(),
				lResultedStartDate.toCharArray());
	}

	/**
	 * Test method for
	 * {@link org.component.partyspam.Party#setStartDate(java.lang.String)}.
	 */
	@Test
	public void setStartDate_setToNewValue_internaFieldIsModified() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lExpectedStartDate = "29-02-2012";
		lParty.setStartDate(lExpectedStartDate);
		int lStartDateIndex = Party.POS_START_DATE;
		testInternalArrayMemberValue(lParty, lExpectedStartDate,
				lStartDateIndex);
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#getEndDate()}.
	 */
	@Test
	public void getEndDate_validObject_returnsExpectedResult() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lResultedEndDate = lParty.getEndDate();
		assertArrayEquals(lEndDate.toCharArray(),
				lResultedEndDate.toCharArray());
	}

	/**
	 * Test method for
	 * {@link org.component.partyspam.Party#setEndDate(java.lang.String)}.
	 */
	@Test
	public void setEndDate_setToNewValue_internaFieldIsModified() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lExpectedEndDate = "28-02-2012";
		lParty.setEndDate(lExpectedEndDate);
		int lEndDateIndex = Party.POS_END_DATE;
		testInternalArrayMemberValue(lParty, lExpectedEndDate,
				lEndDateIndex);
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#getStartHour()}.
	 */
	@Test
	public void getStartHour_validObject_returnsExpectedResult() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		int lResultedStartHour = lParty.getStartHour();
		assertEquals(lStartHour,
				lResultedStartHour);
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#setStartHour(int)}.
	 */
	@Test
	public void setStartHour_setToNewValue_internaFieldIsModified() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		int lExpectedStartHour = 2333;
		lParty.setStartHour(lExpectedStartHour);
		int lStartHourIndex = Party.POS_START_HOUR;
		testInternalArrayMemberValue(lParty, lExpectedStartHour,
				lStartHourIndex);
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#getEndHour()}.
	 */
	@Test
	public void getEndHour_validObject_returnsExpectedResult() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		int lResultedEndHour = lParty.getEndHour();
		assertEquals(lEndHour,
				lResultedEndHour);
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#setEndHour(int)}.
	 */
	@Test
	public void setEndHour_setToNewValue_internaFieldIsModified() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		int lExpectedEndHour = 2234;
		lParty.setEndHour(lExpectedEndHour);
		int lEndHourIndex = Party.POS_END_HOUR;
		testInternalArrayMemberValue(lParty, lExpectedEndHour,
				lEndHourIndex);
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#getLocation()}.
	 */
	@Test
	public void getLocation_validObject_returnsExpectedResult() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		LocationInfo lResultedLocationInfo = lParty.getLocation();
		assertArrayEquals(lDemoLocationInfo.toXML().toCharArray(),
				lResultedLocationInfo.toXML().toCharArray());
	}

	/**
	 * Test method for
	 * {@link org.component.partyspam.Party#setLocation(org.component.partyspam.LocationInfo)}
	 * .
	 */
	@Test
	public void setLocation_setToNewValue_internaFieldIsModified() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);		
		LocationInfo lExpectedLocation = new LocationInfo(21.21,41.41,101,"NewDemoAdditionalLocationData");
		lParty.setLocation(lExpectedLocation);
		int lLocationIndex = Party.POS_LOCATION;
		testInternalArrayMemberValue(lParty, lExpectedLocation.toXML(),
				lLocationIndex);
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#getImage()}.
	 */
	@Test
	public void getImage_validObject_returnsExpectedResult() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lResultedImage = lParty.getImage();
		assertArrayEquals(lEventImage.toCharArray(),
				lResultedImage.toCharArray());
	}

	/**
	 * Test method for
	 * {@link org.component.partyspam.Party#setImage(java.lang.String)}.
	 */
	@Test
	public void setImage_setToNewValue_internaFieldIsModified() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		String lExpectedImage = "NewDemoImage";
		lParty.setImage(lExpectedImage);
		int lImageIndex = Party.POS_IMAGE;
		testInternalArrayMemberValue(lParty, lExpectedImage,
				lImageIndex);
	}

	/**
	 * Test method for {@link org.component.partyspam.Party#getNrOfAttendees()}.
	 */
	@Test
	public void getNrOfAttendees_validObject_returnsExpectedResult() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		int lResultedNrOfAttendees = lParty.getNrOfAttendees();
		assertEquals(lNrOfAttendees,
				lResultedNrOfAttendees);
	}

	/**
	 * Test method for
	 * {@link org.component.partyspam.Party#setNrOfAttendess(int)}.
	 */
	@Test
	public void setNrOfAttendess_setToNewValue_internaFieldIsModified() {
		LocationInfo lDemoLocationInfo = new LocationInfo(20.20, 40.40, 100,
				"DemoAdditionalLocationData");
		String lEventTitle = "DemoTitle";
		String lEventDescription = "DemoDescription";
		String lContactDetails = "DemoContactDetails";
		String lUserId = "DemoUserID";
		int lNrOfAttendees = 14;
		String lStartDate = "26-11-2011";
		String lEndDate = "26-11-2011";
		int lStartHour = 1040;
		int lEndHour = 1045;
		String lEventImage = "DemoImage";
		Party lParty = new Party(lEventTitle, lEventDescription,
				lContactDetails, lUserId, lNrOfAttendees, lStartDate, lEndDate,
				lStartHour, lEndHour, lDemoLocationInfo, lEventImage);
		int lExpectedNrOfAttendees = 143;
		lParty.setNrOfAttendess(lExpectedNrOfAttendees);
		int lNrOfAttendeesIndex = Party.POS_NR_OF_ATTENDEES;
		testInternalArrayMemberValue(lParty, lExpectedNrOfAttendees,
				lNrOfAttendeesIndex);
	}

}

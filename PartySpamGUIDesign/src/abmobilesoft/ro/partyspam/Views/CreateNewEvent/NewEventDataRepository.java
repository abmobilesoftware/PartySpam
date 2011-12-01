package abmobilesoft.ro.partyspam.Views.CreateNewEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.component.partyspam.LocationInfo;
import org.component.partyspam.Party;
import org.jivesoftware.smack.PacketListener;

import abmobilesoft.ro.partyspam.BusinessLogic;

public class NewEventDataRepository {
	private static final String DATE_FORMAT = "dd/MM/yyyy";

	private static NewEventDataRepository mEventData = null;

	private Party mPartyToCreate;	
	
	private NewEventDataRepository() {

	}

	public static NewEventDataRepository getInstance() {
		// add the new listener to my listeners
		if (mEventData == null) {
			mEventData = new NewEventDataRepository();
		}
		return mEventData;
	}

	public void clearEventData() {
		mPartyToCreate = null;
	}
	
	public Party getParty()
	{
		if (mPartyToCreate == null)
		{
			int lDefaultPartyID = 1;
			String lDefaultEventTitle = "";
			String lDefaultEventDescription = "";
			String lDefaultContactDetails = "";
			BusinessLogic lBl = BusinessLogic.getInstance((PacketListener)null);
			String lUserId = lBl.getInstallationUniqueID();
			int lInitialNrOfAttendees = 1;//since you create a party, this party has 1 attendee = you
			Date lCurrentDate = new Date();
			SimpleDateFormat lDateFormatter=  new SimpleDateFormat(DATE_FORMAT);
			String lInitialStartDate = lDateFormatter.format(lCurrentDate);
			String lInitialEndDate = getDateForTomorrow();
			//TODO handle the case when the event is created between 23:00 and 24:00
			int lInitialStartTime = 2300;
			int lInitialEndTime = 300;
			LocationInfo lInitialLocationInfo = lBl.getCurrentLocation();
			String lInitialPartyImage = "NoImage";
			double lDefaultModifiedDate = 100000.1234;
			mPartyToCreate = new Party( lDefaultPartyID,
										lDefaultEventTitle,
										lDefaultEventDescription,
										lDefaultContactDetails,
										lUserId,
										lInitialNrOfAttendees,
										lInitialStartDate,
										lInitialEndDate,
										lInitialStartTime,
										lInitialEndTime,
										lInitialLocationInfo,
										lInitialPartyImage,
										lDefaultModifiedDate);
		
		}
		return mPartyToCreate;
	}	
	
	private String getDateForTomorrow()
	{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat lDateFormatter=  new SimpleDateFormat(DATE_FORMAT);	
		  calendar.add(Calendar.DATE, 1);
		  return lDateFormatter.format(calendar.getTime());
	}
}

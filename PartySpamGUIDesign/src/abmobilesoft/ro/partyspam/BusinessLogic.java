package abmobilesoft.ro.partyspam;
import java.util.ArrayList;

import org.component.partyspam.LocationInfo;
import org.component.partyspam.Party;
import org.component.partyspam.PresenceData;
import org.component.partyspam.XMPPConnect;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

import abmobilesoft.ro.partyspam.support.Installation;
import android.content.Context;
import android.telephony.TelephonyManager;

public class BusinessLogic implements PacketListener {
	private static final String XMPP_HOST = "10.0.2.2";
	private static final String XMPP_RESOURCE = "PC";
	private static final String XMPP_COMPONENT = "partyspam.andospc"; 
	
	// The link with the xmpp brodcasting component.	
	private XMPPConnect mCon = null;
	
	//the context this application is running in
	private static Context mApplicationContext = null;
	
	// Array used to hold the incoming events.
	private ArrayList<String> mIncomingEvents = new ArrayList<String>();
	private static ArrayList<PacketListener> mListeners = new ArrayList<PacketListener>();
	private static BusinessLogic mBL = null;
	
	private BusinessLogic(Context iApplicationContext) {
		if (iApplicationContext == null)
		{
			//we have a coding error :(
		}
		mCon = XMPPConnect.getInstance(XMPP_HOST, XMPP_RESOURCE,
				XMPP_COMPONENT, this);
		mApplicationContext = iApplicationContext;		
		PresenceData lCurrentUserRegistrationData = new PresenceData(getCurrentLocation(), getInstallationUniqueID());
		mCon.sendPresence(Presence.Type.subscribe, lCurrentUserRegistrationData.toXML());        	    
	}
	
	public static BusinessLogic getInstance(PacketListener iNewPacketListener) {
		//add the new listener to my listeners 
		mListeners.add(iNewPacketListener);
		Context ibogusContext = null;
		return getInstance(ibogusContext);
	}
	
	public static BusinessLogic getInstance() {
		Context ibogusContext = null;
		return getInstance(ibogusContext);
	}
	
	//this is supposed to be called only once, from the starting activity
	public static BusinessLogic getInstance(Context iApplicationContext) {
		if (mBL == null) {
			mBL = new BusinessLogic(iApplicationContext);
		}		
		return mBL;
	}
	
	private String getInstallationUniqueID(){
		return Installation.id(mApplicationContext);
	}
	
	public void removeListener(PacketListener iRegisteredPacketListener)
	{
		mListeners.remove(iRegisteredPacketListener);
	}
	
	public void requestEvents() {	
		LocationInfo currentLocation = new LocationInfo(20.12, 41.42, 1000, "");
		mCon.sendMessage("Party", currentLocation.toXML(), Message.Type.getEvents);
	}
	
	public void createEvent(String iEvent) {
		//we create only "valid events"
		if (iEvent != null && !iEvent.isEmpty())
		{
			LocationInfo lQueryLocationInfo = new LocationInfo(20.12, 41.42, 1000, "");
			Party lNewParty = new Party(iEvent, "07524987",
					"mihai@mihai.com", "2011", "2012", "15", "20",
					lQueryLocationInfo);
			mCon.sendMessage("New Party", lNewParty.toXML(), Message.Type.createEvent);		
		}
	}
	
	private LocationInfo getCurrentLocation() {
 	   	/* Get the current location from the gps device */		
		return new LocationInfo(45.45241, 27.17424, 10000, "");
 	}
	
	public void addEvent(String iEvent) {
		mIncomingEvents.add(iEvent);
	}
	
	public ArrayList<String> getEvents() {
		return mIncomingEvents;
	}
	
	public void processPacket(Packet iPacket) {
		if (iPacket instanceof Message) {
			for(PacketListener iListener: mListeners)
			{
				iListener.processPacket(iPacket);
			}						
		}
		
	}		
}
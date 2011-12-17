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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class BusinessLogic implements PacketListener {
	// private static final String XMPP_HOST = "10.0.2.2";
	private static final String XMPP_HOST = "46.137.116.121";
	private static final String XMPP_RESOURCE = "PC";
	// private static final String XMPP_COMPONENT = "partyspam.andospc";
	private static final String XMPP_COMPONENT = "logic.partyspam";
	LocationManager mLocationManager;
	Location mLastKnownLocation;
	// The link with the xmpp brodcasting component.
	private XMPPConnect mCon = null;

	// the context this application is running in
	private static Context mApplicationContext = null;

	// Array used to hold the incoming events.
	private ArrayList<String> mIncomingEvents = new ArrayList<String>();
	private static ArrayList<PacketListener> mListeners = new ArrayList<PacketListener>();
	private static BusinessLogic mBL = null;

	private BusinessLogic(Context iApplicationContext) {
		if (iApplicationContext == null) {
			// we have a coding error :(
		}
		mApplicationContext = iApplicationContext;
		mLocationManager = (LocationManager) iApplicationContext
				.getSystemService(Context.LOCATION_SERVICE);
		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				updateUserLocation(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}
		};
		// Register the listener with the Location Manager to receive location
		// updates
		mLocationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		//
		mLastKnownLocation= mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		connectToXMPPServer();
	}

	private void connectToXMPPServer() {
		if (mCon == null) {
			mCon = XMPPConnect.getInstance(XMPP_HOST, XMPP_RESOURCE,
					XMPP_COMPONENT, this);
			if (mCon != null) {
				PresenceData lCurrentUserRegistrationData = new PresenceData(
						getCurrentLocation(), getInstallationUniqueID());
				mCon.sendPresence(Presence.Type.subscribe,
						lCurrentUserRegistrationData.toXML());
				mCon.sendPresence(Presence.Type.available,
						lCurrentUserRegistrationData.toXML());
			}
		} else {
			// TODO check the validity of the connection and if not valid
			// anymore
			// e.g. InvalidStateException
		}
	}

	public static BusinessLogic getInstance(PacketListener iNewPacketListener) {
		// add the new listener to my listeners
		if (iNewPacketListener != null) {
			mListeners.add(iNewPacketListener);
		}
		Context ibogusContext = null;
		return getInstance(ibogusContext);
	}

	public static BusinessLogic getInstance() {
		Context ibogusContext = null;
		return getInstance(ibogusContext);
	}

	// this is supposed to be called only once, from the starting activity
	public static BusinessLogic getInstance(Context iApplicationContext) {
		if (mBL == null) {
			mBL = new BusinessLogic(iApplicationContext);
		}
		return mBL;
	}

	public String getInstallationUniqueID() {
		return Installation.id(mApplicationContext);
	}

	public void removeListener(PacketListener iRegisteredPacketListener) {
		mListeners.remove(iRegisteredPacketListener);
	}

	public void requestEvents() {
		LocationInfo currentLocation = getCurrentLocation();
		connectToXMPPServer();
		if (mCon != null) {
			mCon.sendMessage("Party", currentLocation.toXML(),
					Message.Type.getEvents);
		}
	}

	public void createEvent(Party iNewParty) {
		// we create only "valid events"
		if (iNewParty != null) 
		{			
			connectToXMPPServer();
			if (mCon != null) {
				mCon.sendMessage("New Party", iNewParty.toXML(),
						Message.Type.createEvent);
			}

		}
	}
	private void updateUserLocation(Location iNewLocation) {
		mLastKnownLocation = iNewLocation;
	}
	public LocationInfo getCurrentLocation() {
		Double latitude = 45.45241;
		Double longitude = 27.17424;
		if (mLastKnownLocation!=null)
		{
			latitude = mLastKnownLocation.getLatitude();
			longitude = mLastKnownLocation.getLongitude(); 
		}
		/* Get the current location from the gps device */
		return new LocationInfo(latitude,longitude, 1000, "");
	}

	public void addEvent(String iEvent) {
		mIncomingEvents.add(iEvent);
	}

	public ArrayList<String> getEvents() {
		return mIncomingEvents;
	}

	public void processPacket(Packet iPacket) {
		if (iPacket instanceof Message) {
			for (PacketListener iListener : mListeners) {
				iListener.processPacket(iPacket);
			}
		}

	}
}

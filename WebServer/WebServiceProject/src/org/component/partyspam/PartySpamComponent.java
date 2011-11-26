package org.component.partyspam;

import java.rmi.RemoteException;

import org.jivesoftware.whack.ExternalComponentManager;
import org.xmpp.component.ComponentException;
import org.xmpp.component.ComponentManager;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Presence;

import com.partyspam.PartySpamServiceSoapProxy;

public class PartySpamComponent implements org.xmpp.component.Component {
	private ExternalComponentManager mMgr = null;
	
	private PartySpamServiceSoapProxy mPartySpamService;

	public String getName() {
		return ("PartySpam");
	}

	public String getDescription() {
		return ("Connecting party people.");
	}

	public void processPacket(Packet iPacket) {
		System.out.println("Received package:" + iPacket.toXML());
		if (iPacket instanceof Message) {
			Message lOriginalMessage = (Message) iPacket;
			if (lOriginalMessage.getType() == Message.Type.identity) {

			} else if (lOriginalMessage.getType() == Message.Type.createEvent) {
				createNewPartyOnWebServer(lOriginalMessage);

			} else if (lOriginalMessage.getType() == Message.Type.getEvents) {
				retrieveEvents(lOriginalMessage);

			} else if (lOriginalMessage.getType() == Message.Type.updatePosition) {
				try {
					mPartySpamService.updateUser(lOriginalMessage.getFrom()
							.toBareJID(), lOriginalMessage.getBody());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (lOriginalMessage.getType() == Message.Type.attendEvent) {
				EventListStructure lAttendEvent = new EventListStructure(lOriginalMessage.getBody());
				// TODO: Call webservice to add the user in party's attendes list.
				
			} else if (lOriginalMessage.getType() == Message.Type.refreshEvents) {
				EventsToUpdateStructure lEventsToAttend = new EventsToUpdateStructure(lOriginalMessage.getBody());
				// TODO Send the list to the webservice for further processing.
				// For an updated event send a message with type updatedEvent with body containing the party in XML.
				// For a deleted event send a message with type deletedEvent with body containing the id of the deleted party.
				// For an unchanged event don't send anything.
			}

		} else if (iPacket instanceof Presence) {
			// Checks if the user comes online or goes unavailable. -> manage
			// the user list.
			Presence lPresence = (Presence) iPacket;
			if (lPresence.getType() == Presence.Type.subscribe) {
				markUserAsAvailable(lPresence);

			} else if (lPresence.getType() == Presence.Type.unavailable) {
				markUserAsUnavailable(lPresence);
			}
		}

	}

	private void markUserAsUnavailable(Presence lPresence) {
		try {
			mPartySpamService.deleteUser(lPresence.getFrom()
					.toBareJID());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void markUserAsAvailable(Presence lPresence) {
		// User comes online -> I now add it in the active users table
		// to become eligible for receiving messages.
		// I must send phone number in this message too.
		try {
			PresenceData lUserPresenceData = new PresenceData(
					lPresence.getStatus());
			
			LocationInfo lUserLocation = lUserPresenceData.getLocation();
			StringBuilder lUserLocationSb = new StringBuilder();
			lUserLocationSb.append(lUserLocation.getLatitude().toString());
			lUserLocationSb.append(",");
			lUserLocationSb.append(lUserLocation.getLongitude());
			mPartySpamService.addUser(lPresence.getFrom().toBareJID(),
					lUserPresenceData.getPhoneId(),
					lUserLocationSb.toString());
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void retrieveEvents(Message lOriginalMessage) {
		Message lMessage = new Message();
		lMessage.setTo(lOriginalMessage.getFrom());
		lMessage.setFrom(lOriginalMessage.getTo());
		lMessage.setType(Message.Type.getEvents);

		try {
			LocationInfo lQueryPosition = new LocationInfo(lOriginalMessage.getBody());
			com.partyspam.Party lParties[] = mPartySpamService
					.selectPartiesFromRange(lQueryPosition.getLatitude(), lQueryPosition.getLongitude(), lQueryPosition.getRadius());
			for (int i = 0; i < lParties.length; ++i) {
				String[] lLatLng = lParties[i].getMLocation().split(",");
				// TODO : CHANGE THIS INSTRUCTION ONCE WE GET THE ADDITIONAL DATA
				Party lTempParty = new Party(
						lParties[i].getMTitle(),
						lParties[i].getMDescription(),
						lParties[i].getMPhone(),
						lParties[i].getMUserId(),
						Integer.parseInt(lParties[i].getMNrOfAttendes()),
						lParties[i].getMStartDate(),
						lParties[i].getMEndDate(),
						Integer.parseInt(lParties[i].getMStartHour()), 
						20,
						new LocationInfo(Double.parseDouble(lLatLng[0]), 
										Double.parseDouble(lLatLng[1]), 
										Integer.parseInt(lParties[i].getMRadius()), ""),
						lParties[i].getMImage()
						);
				
				
				lMessage.setBody(lTempParty.toXML());
				mMgr.sendPacket(this, lMessage);

				System.out.println("-==Party==-");
				System.out.println("Description = "
						+ lParties[i].getMDescription());
				System.out
						.println("Phone = " + lParties[i].getMPhone());
				System.out.println("User id = "
						+ lParties[i].getMUserId());
				System.out.println("Start date ="
						+ lParties[i].getMStartDate());
				System.out.println("End date = "
						+ lParties[i].getMEndDate());
				System.out.println("Location = "
						+ lParties[i].getMLocation());
				System.out.println("Radius ="
						+ lParties[i].getMRadius());

			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createNewPartyOnWebServer(Message lOriginalMessage) {
		Party lNewParty = new Party(lOriginalMessage.getBody());
		LocationInfo lLocation = lNewParty.getLocation();
		try {
			mPartySpamService.addParty(
					lNewParty.getTitle(), 
					lNewParty.getDescription(),
					lNewParty.getContactData(), 
					lOriginalMessage.getFrom().toBareJID(), 
					lNewParty.getNrOfAttendees(),
					lNewParty.getStartDate(),
					lNewParty.getEndDate(), 
					lNewParty.getStartHour(),
					lNewParty.getEndHour(),
					lLocation.getLatitude(),
					lLocation.getLongitude(),
					lLocation.getRadius(), 
					lLocation.getAdditionalLocationData(), 
					lNewParty.getImage());
			
			notifyUsersInRangeThatNewPartyWasCreated(lOriginalMessage,
					lLocation);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void notifyUsersInRangeThatNewPartyWasCreated(
			Message lOriginalMessage, LocationInfo lLocation)
			throws RemoteException {
		String lUsers[] = mPartySpamService.selectUsersFromRange(
				lLocation.getLatitude(), lLocation.getLongitude(),
				lLocation.getRadius());

		Message lResponseMessage = lOriginalMessage.createCopy();
		lResponseMessage.setFrom(lOriginalMessage.getTo());
		lResponseMessage.setType(Message.Type.createEvent);
		lResponseMessage.setBody(lOriginalMessage.getBody());
		for (String lUser : lUsers) {
			lResponseMessage.setTo(lUser);
			mMgr.sendPacket(this, lResponseMessage);
		}
	}

	public void initialize(JID iJid, ComponentManager iComponentManager)
			throws ComponentException {
		System.out.println("Initializing component.");
		mMgr = (ExternalComponentManager) iComponentManager;
		mPartySpamService = new PartySpamServiceSoapProxy();
	}

	public void start() {
		System.out.println("Component started.");
	}

	public void shutdown() {
		System.out.println("Component is shutting down.");
	}
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.component.partyspam.LocationInfo;
import org.component.partyspam.Party;
import org.component.partyspam.PresenceData;
import org.component.partyspam.XMPPConnect;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

public class XMPPClient implements PacketListener {
//	private static final String XMPP_HOST = "192.168.44.1";
	private static final String XMPP_HOST = "46.137.116.121";
	private static final String XMPP_RESOURCE = "PC";
//	private static final String XMPP_COMPONENT = "partyspam.andospc";
	private static final String XMPP_COMPONENT = "logic.partyspam";
	private static XMPPConnect mCon = null;

	public void login() throws XMPPException {
		mCon = XMPPConnect.getInstance(XMPP_HOST, XMPP_RESOURCE,
				XMPP_COMPONENT, this);
		PresenceData lCurrentUserRegistrationData = new PresenceData(
				getCurrentLocation(), "0751569435");
		mCon.sendPresence(Presence.Type.subscribe,
				lCurrentUserRegistrationData.toXML());
		mCon.sendMessage("Hello", "Hello World", Message.Type.normal);
	}

	public void sendMessage(String iSubject, String iBody, Message.Type iType)
			 throws XMPPException {
		mCon.sendMessage(iSubject, iBody, iType);
	}

	public void disconnect() {
		// TODO implement disconnect
		mCon.disconnect();
	}

	public static void main(String args[]) throws XMPPException, IOException {
		// declare variables
		XMPPClient lXmppClient = new XMPPClient();
		BufferedReader lBr = new BufferedReader(
				new InputStreamReader(System.in));
		String lMsg;

		// turn on the enhanced debugger
		XMPPConnection.DEBUG_ENABLED = true;

		// Login to the server.
		lXmppClient.login();

		System.out.println("Enter your message in the console:");
		System.out.println("-----\n");

		while (!(lMsg = lBr.readLine()).equals("bye")) {
			/*
			 * Send your message to partyspam component. The XMPP component will
			 * broadcast your message to the users in your area. When sending
			 * the message you must notify the xmpp component about your current
			 * location.
			 */
			LocationInfo lQueryLocationInfo = new LocationInfo(20.12, 41.42, 1000, "");
			if (lMsg.equals("?")) {
				lXmppClient.sendMessage("Party", lQueryLocationInfo.toXML(),  Message.Type.getEvents);
			} else {							
				Party lNewParty = new Party("Title: " + lMsg, lMsg, "07524987", 
						"mihai@mihai.com", 0, "2011", "2012", 15, 20,
						lQueryLocationInfo, "no image");				
				mCon.sendMessage("New Party", lNewParty.toXML(), Message.Type.createEvent);	
				lXmppClient.sendMessage("Party", lNewParty.toXML(),
						Message.Type.createEvent);
			}
		}

		lXmppClient.disconnect();
		System.exit(0);
	}

	private LocationInfo getCurrentLocation() {
		/* Get the current location from the gps device */
		return new LocationInfo(45.45241, 27.17424, Integer.MIN_VALUE, "");
	}

	@Override
	public void processPacket(Packet iPacket) {
		// TODO Auto-generated method stub
		System.out.println(iPacket.toXML());
		if (iPacket instanceof Message) {
			Message lMessage = (Message) iPacket;
			System.out.println(lMessage.getBody());

		}
	}

}
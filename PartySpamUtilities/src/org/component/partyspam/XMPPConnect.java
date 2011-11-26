package org.component.partyspam;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

public class XMPPConnect {

	private XMPPConnection mConnection;
	// according to
	// http://developer.android.com/guide/developing/devices/emulator.html
	// 10.0.2.2 is equivalent to 127.0.0.1
	private static final int XMPP_PORT = 5222;

	private String mComponentName;
	private static XMPPConnect mCon = null;

	private XMPPConnect(String iXMPP_host, String iXMPP_resource,
			String iXMPP_component, PacketListener iPacketListener)
			throws XMPPException {
		mComponentName = iXMPP_component;
		ConnectionConfiguration config = new ConnectionConfiguration(
				iXMPP_host, XMPP_PORT, iXMPP_resource);

		mConnection = new XMPPConnection(config);
		mConnection.connect();
		mConnection.addPacketListener(iPacketListener, null);

		// Login to XMPP Server Anonymously - XMPP Server must permit this type
		// of login */
		mConnection.loginAnonymously();
		// TODO - move to better place

	}

	public static synchronized XMPPConnect getInstance(String iXMPP_host,
			String iXMPP_resource, String iXMPP_component,
			PacketListener iPacketListener) {
		if (mCon == null) {
			try {
				mCon = new XMPPConnect(iXMPP_host,iXMPP_resource, iXMPP_component,iPacketListener);
			} catch (XMPPException e) {
				// failed creating a new instance
			}
		}
		return mCon;

	}

	public void disconnect()
	{
		mCon.disconnect();
	}
	
	public void sendMessage(String iSubject, String iBody, Message.Type iType) {
		// Send a message stanza. - more info here:
		// http://xmpp.org/rfcs/rfc3920.html (XML stanzas section)
		mConnection.sendPacket(this.createMessage(iSubject, iBody, iType,
				mComponentName));
	}

	public void sendPresence(Presence.Type iType, String iUserRegistrationData) {
		mConnection.sendPacket(this.createPresence(iType, iUserRegistrationData,
				mComponentName));
	}

	// Helper methods for creating message and presence packets.
	private Message createMessage(String iSubject, String iBody,
			Message.Type iType, String iReceiver) {
		/*
		 * Current issue: 1. Create the message in the manner showed below. 2.
		 * Send the message using sendPacket. 3. The component receives the
		 * message and replies. 4. The client receives the message but the
		 * processMessage method it's not triggered.
		 */
		Message lMessage = new Message();
		lMessage.addSubject(null, iSubject);
		lMessage.addBody(null, iBody);
		lMessage.setType(iType);
		lMessage.setTo(iReceiver);
		return lMessage;
	}

	private Presence createPresence(Presence.Type iType, String iStatus,
			String iReceiver) {
		Presence lPresence = new Presence(iType);
		lPresence.setStatus(iStatus);
		lPresence.setTo(iReceiver);
		return lPresence;
	}	
}

package org.component.partyspam;

import org.xmpp.packet.JID;

public class User {
	private JID mJID;
	private double mLat;
	private double mLng;
	
	public User() {
		// empty
	}
	
	public User(JID iJID, double iLat, double iLng) {
		this.mJID = iJID;
		this.mLat = iLat;
		this.mLng = iLng;
	}
	
	public JID getJID() {
		return mJID;
	}

	public void setJID(JID iJID) {
		mJID = iJID;
	}

	public double getLat() {
		return mLat;
	}

	public void setLat(double mLat) {
		this.mLat = mLat;
	}

	public double getLng() {
		return mLng;
	}

	public void setLng(double mLng) {
		this.mLng = mLng;
	}
	
}

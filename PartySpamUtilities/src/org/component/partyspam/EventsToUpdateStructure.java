package org.component.partyspam;

import java.util.ArrayList;

public class EventsToUpdateStructure {
	private String[] mEventIds;
	private String mEventIdsInString;
	private final static String SEPARATOR = ",";
	
	
	public EventsToUpdateStructure(String iEventsList) {
		mEventIds = iEventsList.split(SEPARATOR);
		mEventIdsInString = iEventsList;
	}
	
	public EventsToUpdateStructure(String[] iEventsList) {
		StringBuilder lSb = new StringBuilder();
		if (iEventsList.length > 0) {
			lSb.append(iEventsList[0]);
			if (iEventsList.length > 1)
				for (int i=1; i < iEventsList.length; ++i) {
					lSb.append(SEPARATOR);
					lSb.append(iEventsList[i]);
				}	
		}
		
		mEventIdsInString = lSb.toString();
		mEventIds = iEventsList;
		
	}

	/**
	 * @return the mEventIds
	 */
	public String[] getEventIds() {
		return mEventIds;
	}

	/**
	 * @param mEventIds the mEventIds to set
	 */
	public void setEventIds(String[] mEventIds) {
		this.mEventIds = mEventIds;
	}

	/**
	 * @return the mEventIdsInString
	 */
	public String getEventIdsInString() {
		return mEventIdsInString;
	}

	/**
	 * @param mEventIdsInString the mEventIdsInString to set
	 */
	public void setEventIdsInString(String mEventIdsInString) {
		this.mEventIdsInString = mEventIdsInString;
	}
		
}

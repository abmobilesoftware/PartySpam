package abmobilesoft.ro.partyspam.Views;

import org.component.partyspam.Party;

public class StringFormattingForParty {
	public static final String HOURS_MINUTES_SEPARATOR = ":";
	public static String extractDateAndHourStringFromInteger(int iTime, String iDate) {
		int lStartTimeExtended = iTime;
		int lHours = lStartTimeExtended / 100;
		int lMinutes = lStartTimeExtended % 100;
		String lStartHourAndDate = iDate + " "
				+ String.valueOf(lHours) + HOURS_MINUTES_SEPARATOR
				+ String.valueOf(lMinutes);
		return lStartHourAndDate;
	}
}

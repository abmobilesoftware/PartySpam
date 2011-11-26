import org.component.partyspam.LocationInfo;
import org.component.partyspam.Party;


public class PartyTest {
	public static String getXMLRepresetationOfParty()
	{
		LocationInfo lLocationForMyParty = new LocationInfo(20.20, 30.30, 456, "");
		Party lParty = new Party("Party 1", "0751569435","dragos si mihai", "ieri","maine","10:00","12:00",lLocationForMyParty);		
		return lParty.toXML();
	}
	
	public static Party buildPartyFromXMLRepresentation()
	{
		String lTestString = "<party><description>Party 1</description><phone>0751569435</phone><userId>dragos si mihai</userId><startDate>ieri</startDate><endDate>maine</endDate><startHour>10:00</startHour><endHour>12:00</endHour><location>&lt;locationQuery&gt;&#13;&lt;latitude&gt;20.2&lt;/latitude&gt;&#13;&lt;longitude&gt;30.3&lt;/longitude&gt;&#13;&lt;radius&gt;456&lt;/radius&gt;&#13;&lt;additionalLocationData/&gt;&#13;&lt;/locationQuery&gt;&#13;</location></party>"; 
		return new Party(lTestString);
	}
	
	
	public static String getXMLRepresentationOfLocation()
	{
		LocationInfo lLocation = new LocationInfo(20.20, 40.40, 1000, "");
		return lLocation.toXML();
	}
	
	public static LocationInfo buildLocationFromXMLRepresentation()
	{
		String lDemoString = getXMLRepresentationOfLocation();
		return new LocationInfo(lDemoString);
	}
}

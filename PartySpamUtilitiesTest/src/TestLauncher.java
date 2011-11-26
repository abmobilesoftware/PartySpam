import org.component.partyspam.LocationInfo;
import org.component.partyspam.Party;


public class TestLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println(PartyTest.getXMLRepresentationOfLocation());
		LocationInfo lLocation = PartyTest.buildLocationFromXMLRepresentation();
		if (lLocation == null)
		{
			System.out.println("Failure!");
		}
		else
		{
			System.out.println("Great success for location!");
			System.out.println(lLocation.toXML());	
			System.out.println(lLocation.getLatitude());
			System.out.println(lLocation.getLongitude());
			System.out.println(lLocation.getRadius());
		}
		
//		System.out.println("Party test");
//		System.out.println(PartyTest.getXMLRepresetationOfParty());	
//		Party lNewParty = PartyTest.buildPartyFromXMLRepresentation();
//		if (lNewParty == null)
//		{
//			System.out.println("Failure!");
//		}
//		else
//		{
//			System.out.println("Great success!");
//			System.out.println(lNewParty.toXML());	
//			System.out.println(lNewParty.getLocation().getLatitude());
//			System.out.println(lNewParty.getLocation().getLongitude());
//			System.out.println(lNewParty.getLocation().getRadius());
//		}
	}

}

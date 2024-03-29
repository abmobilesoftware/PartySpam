package abmobilesoft.ro.partyspam.Views;

import java.io.IOException;

import org.component.partyspam.LocationInfo;
import org.component.partyspam.MessageProcessing;
import org.component.partyspam.Party;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.MenuInflater;

public class EventsActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Do first time initialization -- add initial fragment.
		FragmentManager fm = getSupportFragmentManager();
		// Create the list fragment and add it as our sole content.
		if (fm.findFragmentById(android.R.id.content) == null) {
			EventsListFragment list = new EventsListFragment();
			fm.beginTransaction().add(android.R.id.content, list).commit();
		}
	}

	public static class EventsListFragment extends EventsFragmentBase {
		private static final String NO_EVENTS_FOUND_ON_REFRESTH_TEXT = "No parties in your area :(. Try later or extend the search area";
		private static final String NO_EVENTS_DEFAULT_BACKGROUNDTEXT = "No parties.  Select 'Refresh' to see what's around";
		private static final String NO_PARTIES_FOUND_MAGIC_STRING = "@!*&NO_PARTIES_FOUND8^%%";
		private static final int REFRESH_ID = Menu.FIRST;
		private static final int SCANOPTIONS_ID = REFRESH_ID + 1;
		private static final int SETTINGS_ID = SCANOPTIONS_ID + 1;
		private static final int DEMOREFRESH_ID = SETTINGS_ID + 1;
		@Override
		protected void customInitialize(Bundle savedInstanceState) {
			setEmptyText(NO_EVENTS_DEFAULT_BACKGROUNDTEXT);
		}

		@Override
		protected void processPacketImplementation(Packet iPacket) {
			if (iPacket instanceof Message) {
				final Message lMessage = (Message) iPacket;
				if (lMessage.getType() == Message.Type.getEvents) {
					// only handle results of "GetEvent"
					// this.getActivity().runOnUiThread(new Runnable() {
					// public void run() {
					try {
						handleNewReceivedParty(MessageProcessing
								.buildEventFromMessageBody(lMessage));						
					} catch (SAXParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// setListShown(true);
					// }
					// });
				}
			}
		}

		private void handleNewReceivedParty(Party iNewParty) {
			if (iNewParty.getTitle().equalsIgnoreCase(
					NO_PARTIES_FOUND_MAGIC_STRING)) {
				this.getActivity().runOnUiThread(new Runnable() {
					public void run() {			
						setEmptyText(NO_EVENTS_FOUND_ON_REFRESTH_TEXT);
					}
				});	
				
			} else {
				addPartyToList(iNewParty);
				setEmptyText(NO_EVENTS_DEFAULT_BACKGROUNDTEXT);
			}
		}
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			super.onCreateOptionsMenu(menu, inflater);
			menu.add(Menu.NONE, REFRESH_ID, 0, "Refresh").setShowAsAction(
					MenuItem.SHOW_AS_ACTION_IF_ROOM);
			menu.add(Menu.NONE, SCANOPTIONS_ID, 0, "Scan options")
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			menu.add(Menu.NONE, SETTINGS_ID, 0, "Settings").setShowAsAction(
					MenuItem.SHOW_AS_ACTION_IF_ROOM);
			menu.add(Menu.NONE, DEMOREFRESH_ID, 0, "DemoRefresh")
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
				case REFRESH_ID :
					clearData();
					setListShown(false);
					mBL.requestEvents();
					return true;
				case SCANOPTIONS_ID :
					return true;
				case SETTINGS_ID :
					return true;
				case DEMOREFRESH_ID :
					clearData();
					setListShown(false);
					populateListWithDummyData();
					return true;
				default :
					return super.onOptionsItemSelected(item);
			}
		}

		private void populateListWithDummyData() {
			int lNrOfPartiesToCreate = 5;
			for (int i = 0; i < lNrOfPartiesToCreate; ++i) {
				LocationInfo lDemoLocationInfo = new LocationInfo(
						20.20,
						40.40,
						100,
						"Observatorului 19 - bring whatever you would like to drink and high spirits - it's going to be a blast!");
				int lPartyId = 1;
				String lEventTitle = "Demo Title " + String.valueOf(i);
				String lEventDescription = "Super party tonight at my crib";
				String lContactDetails = "DemoContactDetails";
				String lUserId = "DemoUserID";
				int lNrOfAttendees = 14;
				String lStartDate = "26-11-2011";
				String lEndDate = "26-11-2011";
				int lStartHour = 1040;
				int lEndHour = 1045;
				String lEventImage = "DemoImage";
				double lModifiedDate = 100000.3128;
				Party lParty = new Party(lPartyId, lEventTitle,
						lEventDescription, lContactDetails, lUserId,
						lNrOfAttendees, lStartDate, lEndDate, lStartHour,
						lEndHour, lDemoLocationInfo, lEventImage, lModifiedDate);
				addPartyToList(lParty);
			}
			setListShown(true);
		}
	}
}

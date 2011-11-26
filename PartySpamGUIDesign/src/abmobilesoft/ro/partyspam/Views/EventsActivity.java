package abmobilesoft.ro.partyspam.Views;

import org.component.partyspam.MessageProcessing;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

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
	
	public static class EventsListFragment extends EventsFragmentBase
 {
		private static final int REFRESH_ID = Menu.FIRST;
		private static final int SCANOPTIONS_ID = REFRESH_ID + 1;
		private static final int SETTINGS_ID = SCANOPTIONS_ID + 1;
		@Override
		protected void customInitialize(Bundle savedInstanceState) {
			setEmptyText("No parties.  Select 'Refresh' to see what's around");
		}

		@Override
		protected void processPacketImplementation(Packet iPacket) {
			if (iPacket instanceof Message) {
				final Message lMessage = (Message) iPacket;
				if (lMessage.getType() == Message.Type.getEvents) {
					// only handle results of "GetEvent"
				
					this.getActivity().runOnUiThread(new Runnable() {
						public void run() {
							addPartyToList(MessageProcessing
									.buildEventFromMessageBody(lMessage));
							setListShown(true);
						}
						});
				
				}
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
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
				case REFRESH_ID :		
					clearData();
					mBL.requestEvents();
					setListShown(false);
					return true;
				case SCANOPTIONS_ID :
					return true;
				case SETTINGS_ID :
					return true;
				default :
					return super.onOptionsItemSelected(item);
			}
		}
	}
}

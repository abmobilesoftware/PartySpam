package abmobilesoft.ro.partyspam.Views;

import java.io.IOException;

import org.component.partyspam.MessageProcessing;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import abmobilesoft.ro.partyspam.Views.CreateNewEvent.CreateEventActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.MenuInflater;

public class MyEventsActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Do first time initialization -- add initial fragment.
		FragmentManager fm = getSupportFragmentManager();
		// Create the list fragment and add it as our sole content.
		if (fm.findFragmentById(android.R.id.content) == null) {
			MyEventsListFragment list = new MyEventsListFragment();
			fm.beginTransaction().add(android.R.id.content, list).commit();
		}
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
           
    }

	public static class MyEventsListFragment extends EventsFragmentBase {
		private static final int CREATEEVENT_ID = Menu.FIRST;
		 private static final int ACTIVITY_CREATE_EVENT =0;
		@Override
		protected void customInitialize(Bundle savedInstanceState) {
			setEmptyText("You're not attending any parties :(. Why don't you create one?");
		}

		@Override
		protected void processPacketImplementation(Packet iPacket) {
			if (iPacket instanceof Message) {
				Message lMessage = (Message) iPacket;
				if (lMessage.getType() == Message.Type.createEvent) {
					// TODO - fix this later as right now it is here just as
					// proof of concept
					try {
						addPartyToList(MessageProcessing
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
				}
			}
		}
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			super.onCreateOptionsMenu(menu,inflater);
			menu.add(Menu.NONE, CREATEEVENT_ID, 0, "Create event").setShowAsAction(
					MenuItem.SHOW_AS_ACTION_IF_ROOM);			
		}

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
				case CREATEEVENT_ID :
					createNewEvent();
					return true;
				default :
					return super.onOptionsItemSelected(item);
			}
		}
		
		private void createNewEvent() {
	        Intent i = new Intent(getActivity(), CreateEventActivity.class);
	        startActivityForResult(i, ACTIVITY_CREATE_EVENT);
	    }				
	}

}

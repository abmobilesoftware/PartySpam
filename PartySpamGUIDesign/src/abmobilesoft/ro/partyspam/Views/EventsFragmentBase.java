package abmobilesoft.ro.partyspam.Views;

import java.util.ArrayList;
import java.util.Arrays;

import org.component.partyspam.Party;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;

import abmobilesoft.ro.partyspam.BusinessLogic;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public abstract class EventsFragmentBase extends ListFragment
		implements
			PacketListener {
	private static final String EVENTS_SAVE_KEY = "loadedEvents";

	protected ArrayList<String> mEvents;
	protected BusinessLogic mBL = null;
	ArrayAdapter<String> mDataAdapter ;

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		// we have the list of displayed events
		outState.putStringArrayList(EVENTS_SAVE_KEY, mEvents);
	}

	private void baseInitialize(Bundle savedInstanceState) {
		setHasOptionsMenu(true); // explicitly indicate that we have a context
									// menu

		// we attempt to restore any already displayed events
		String[] defaultData = new String[]{};
		/*according to http://stackoverflow.com/questions/3200551/unable-to-modify-arrayadapter-in-listview it is important
		 * to set the data source not to a string array but to a string list array
		*/
		ArrayList<String> lst = new ArrayList<String>();
		if (savedInstanceState == null) {			 
			lst.addAll(Arrays.asList(defaultData));			
		} else {
			lst = savedInstanceState.getStringArrayList(EVENTS_SAVE_KEY);
			if (lst == null || lst.isEmpty())
			{
				lst.addAll(Arrays.asList(defaultData));									
			}						
		}	
		mEvents = new ArrayList<String>();
		mEvents.addAll(lst);

		mDataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1, lst);
		setListAdapter(mDataAdapter);

		try {
			// TODO if no XMPP server is available then this call will hang
			mBL = BusinessLogic.getInstance(this);
		} catch (NullPointerException e) {
			// TODO we should log the error
			System.exit(0);
		}
		//on start do not shown a loading icon
		setListShown(true);
	}

	// this should be overridden in the implementations
	protected abstract void customInitialize(Bundle savedInstanceState);

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		baseInitialize(savedInstanceState);
		customInitialize(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// in order to avoid memory leaks we have to make sure we are no
		// longer registered as a listener
		mBL.removeListener(this);
		super.onDestroy();
	}

	protected abstract void processPacketImplementation(Packet iPacket);

	@Override
	public void processPacket(Packet iPacket) {
		processPacketImplementation(iPacket);
	}
	
	protected void addPartyToList(Party iParty)
	{
		final Party lParty = iParty;		
		String lPartyDescription = lParty.getDescription();
		mDataAdapter.add(lPartyDescription);			
		mEvents.add(lPartyDescription);
	}
	
	protected void clearData()
	{
		  mDataAdapter.clear();
		  mEvents.clear();
	}
}

package abmobilesoft.ro.partyspam.Views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.component.partyspam.LocationInfo;
import org.component.partyspam.Party;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import abmobilesoft.ro.partyspam.BusinessLogic;
import abmobilesoft.ro.partyspam.R;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public abstract class EventsFragmentBase extends ListFragment
		implements
			PacketListener {
	private static final String EVENTS_SAVE_KEY = "loadedEvents";
	private static final int ACTIVITY_VIEW_EVENT_DETAILS = 1;

	protected BusinessLogic mBL = null;
	PartyAdapter mDataAdapter;

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// we have the list of displayed events
		ArrayList<String> lEventsSerializedAsString = new ArrayList<String>();
		for (Party lParty : mDataAdapter.getParties()) {
			lEventsSerializedAsString.add(lParty.toXML());
		}
		outState.putStringArrayList(EVENTS_SAVE_KEY, lEventsSerializedAsString);
	}

	private void baseInitialize(Bundle savedInstanceState) {
		setHasOptionsMenu(true); // explicitly indicate that we have a context
									// menu

		// we attempt to restore any already displayed events
		String[] defaultData = new String[]{};
		/*
		 * according to
		 * http://stackoverflow.com/questions/3200551/unable-to-modify
		 * -arrayadapter-in-listview it is important to set the data source not
		 * to a string array but to a string list array because if we set it to
		 * a string array then we won't be able to further add/remove elements
		 * from it
		 */
		ArrayList<String> lst = new ArrayList<String>();
		if (savedInstanceState == null) {
			lst.addAll(Arrays.asList(defaultData));
		} else {
			lst = savedInstanceState.getStringArrayList(EVENTS_SAVE_KEY);
			if (lst == null || lst.isEmpty()) {
				lst.addAll(Arrays.asList(defaultData));
			}
		}
		ArrayList<Party> lRestoredParties = new ArrayList<Party>();
		for (String iStringRepresentationOfParty : lst) {
			Party lPartyCreatedFromXML;
			try {
				lPartyCreatedFromXML = new Party(iStringRepresentationOfParty);
				lRestoredParties.add(lPartyCreatedFromXML);
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
		mDataAdapter = new PartyAdapter(getActivity(), lRestoredParties);
		setListAdapter(mDataAdapter);

		try {
			// TODO if no XMPP server is available then this call will hang
			mBL = BusinessLogic.getInstance(this);
		} catch (NullPointerException e) {
			// TODO we should log the error
			System.exit(0);
		}
		// on start do not shown a loading icon
		setListShown(true);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Party lSelectedParty = (Party) mDataAdapter.getItem(position);
		Intent i = new Intent(this.getActivity(), EventDetailsActivity.class);
		i.putExtra(EventDetailsActivity.PARTY_DESCRIPTION,
				lSelectedParty.toXML());
		startActivityForResult(i, ACTIVITY_VIEW_EVENT_DETAILS);
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

	protected void addPartyToList(Party iParty) {
		mDataAdapter.add(iParty);
	}

	protected void clearData() {
		mDataAdapter.clear();
	}

	public class PartyAdapter extends BaseAdapter {
		Context _context;
		ArrayList<Party> mParties;
		private final LayoutInflater mInflater;

		public PartyAdapter(Context context, ArrayList<Party> iParties) {
			_context = context;
			mParties = iParties;
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public ArrayList<Party> getParties() {
			return mParties;
		}

		public void add(Party iParty) {
			mParties.add(iParty);
			// the call to notifyDataSetChanged is important as it will lead to
			// update of the GUI
			notifyDataSetChanged();
		}
		public void clear() {
			mParties.clear();
			notifyDataSetChanged();
		}

		public int getCount() {
			if (mParties != null)
				return mParties.size();
			else
				return 0;
		}

		public Object getItem(int arg0) {
			return mParties.get(arg0);
		}

		public long getItemId(int arg0) {
			return mParties.get(arg0).getId();
		}

		
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView == null) {
				view = mInflater.inflate(R.layout.list_item_icon_text, parent,
						false);
			} else {
				view = convertView;
			}
			Party item = (Party) getItem(position);
			Resources res = getResources();
			Drawable drawable = res.getDrawable(R.drawable.ic_launcher);
			((ImageView) view.findViewById(R.id.evListViewIconEvent))
					.setImageDrawable(drawable);
			((TextView) view.findViewById(R.id.evListViewTxtEventTitle))
					.setText(item.getTitle());
			String lStartHourAndDate = StringFormattingForParty.extractDateAndHourStringFromInteger(item.getStartHour(),item.getStartDate());
			((TextView) view.findViewById(R.id.evListViewTxtStartDateAndHour))
					.setText(lStartHourAndDate);

			LocationInfo lPartyLocation = item.getLocation();
			((TextView) view.findViewById(R.id.evListViewTxtAdditionalInfo))
					.setText(lPartyLocation.getAdditionalLocationData());
			return view;
		}

		
	}
}

package abmobilesoft.ro.partyspam.Views.CreateNewEvent;

import abmobilesoft.ro.partyspam.R;
import abmobilesoft.ro.partyspam.Views.EventsActivity;
import abmobilesoft.ro.partyspam.Views.EventsActivity.EventsListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class CreateEventsStep1Activity extends FragmentActivity {

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
	
	public static class CreateEventStep1Fragment extends Fragment implements ICreateEventWizardStep
	{
		EditText mNewEventTitle;
		EditText mNewEventDescription;
		EditText mNewEventContact;
		NewEventDataRepository mNewEventRepository = NewEventDataRepository.getInstance();;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.createneweventstep1, container, false);
			
			//identify the graphical elements
			mNewEventTitle = (EditText) v.findViewById(R.id.newEventTitle);         
			mNewEventDescription = (EditText) v.findViewById(R.id.newEventDescription);
			mNewEventContact = (EditText) v.findViewById(R.id.newEventContact);
			
			return v; 
		}	
				
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			restoreEventData();
			super.onResume();
		}

		public void onPause() {
			// TODO Auto-generated method stub
			saveEventData();
			super.onPause();
		}

		public void restoreEventData()
		{
			mNewEventTitle.setText(mNewEventRepository.getEventTitle());
			mNewEventDescription.setText(mNewEventRepository.getEventDescription());
			mNewEventContact.setText(mNewEventRepository.getContact());
		}
		public void saveEventData()
		{			
			mNewEventRepository.setEventTitle(mNewEventTitle.getText().toString());			
			mNewEventRepository.setEventDescription(mNewEventDescription.getText().toString());
			mNewEventRepository.setContact(mNewEventContact.getText().toString());
		}
	}
}
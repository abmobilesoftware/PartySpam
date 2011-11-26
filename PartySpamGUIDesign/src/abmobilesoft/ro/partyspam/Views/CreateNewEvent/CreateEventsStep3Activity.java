package abmobilesoft.ro.partyspam.Views.CreateNewEvent;

import abmobilesoft.ro.partyspam.R;
import abmobilesoft.ro.partyspam.Views.EventsActivity;
import abmobilesoft.ro.partyspam.Views.EventsActivity.EventsListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateEventsStep3Activity extends FragmentActivity {

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
	
	public static class CreateEventStep3Fragment extends Fragment implements ICreateEventWizardStep
	{		
		NewEventDataRepository mNewEventRepository =NewEventDataRepository.getInstance();;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.createneweventstep3, container, false);			
            return v; 
		}	
		
		public void restoreEventData()
		{
			
		}
		public void saveEventData()
		{
			
		}
	}
}

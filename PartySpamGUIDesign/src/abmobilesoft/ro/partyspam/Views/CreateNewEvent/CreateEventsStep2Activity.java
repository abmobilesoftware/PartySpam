package abmobilesoft.ro.partyspam.Views.CreateNewEvent;

import org.component.partyspam.LocationInfo;
import org.component.partyspam.Party;

import abmobilesoft.ro.partyspam.R;
import abmobilesoft.ro.partyspam.Views.EventsActivity.EventsListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CreateEventsStep2Activity extends FragmentActivity {

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
	
	public static class CreateEventStep2Fragment extends Fragment implements ICreateEventWizardStep
	{		
		private static final String HOURS_MINUTES_SEPARATOR = ":";
		NewEventDataRepository mNewEventRepository =NewEventDataRepository.getInstance();;
		EditText txtAdditionalLocationData;
		Button mBtnSelectStartDate;
		Button mBtnSelectStartTime;
		Button mBtnSelectEndDate;
		Button mBtnSelectEndTime;
	
		String mStartDate;
		String mEndDate;
		int mStartHour;
		int mEndHour;
		LocationInfo mLocationInfo;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.createneweventstep2, container, false);
			txtAdditionalLocationData = (EditText) v.findViewById(R.id.txtAdditionalEventData);
			mBtnSelectStartDate = (Button) v.findViewById(R.id.btnSelectStartDate);
			mBtnSelectStartTime = (Button) v.findViewById(R.id.btnSelectStartTime);
			mBtnSelectEndDate = (Button) v.findViewById(R.id.btnSelectEndDate);
			mBtnSelectEndTime = (Button) v.findViewById(R.id.btnSelectEndTime);
			restoreEventData();
            return v; 
		}	
		
		public void restoreEventData()
		{
			Party lPartyToCreate = mNewEventRepository.getParty();
			String lAdditionalLocationData = lPartyToCreate.getLocation().getAdditionalLocationData();
			if (!lAdditionalLocationData.isEmpty())
			{
				txtAdditionalLocationData.setText(lAdditionalLocationData);	
			}
			
			mBtnSelectStartDate.setText(lPartyToCreate.getStartDate());
			int lStartTimeExtended = lPartyToCreate.getStartHour();
			int lHours = lStartTimeExtended / 100;
			int lMinutes = lStartTimeExtended % 100;
			mBtnSelectStartTime.setText(String.valueOf(lHours) + HOURS_MINUTES_SEPARATOR + String.valueOf(lMinutes));
			
			mBtnSelectEndDate.setText(lPartyToCreate.getEndDate());
			int lEndTimeExtended = lPartyToCreate.getEndHour();
			lHours = lEndTimeExtended / 100;
			lMinutes = lEndTimeExtended % 100;
			mBtnSelectEndTime.setText(String.valueOf(lHours) + HOURS_MINUTES_SEPARATOR + String.valueOf(lMinutes));
			
		}
		
		public void saveEventData()
		{
			Party lPartyToCreate = mNewEventRepository.getParty();
			
			String lAdditionalLocationData = txtAdditionalLocationData.getText().toString();
			if (! lAdditionalLocationData.isEmpty())
			{
				LocationInfo lNewPartyLocation = lPartyToCreate.getLocation();
				lNewPartyLocation.setAdditionalLocationData(lAdditionalLocationData);
				lPartyToCreate.setLocation(lNewPartyLocation);
			}
			lPartyToCreate.setStartDate(mBtnSelectStartDate.getText().toString());
					
			String lTimeAsString = mBtnSelectStartTime.getText().toString();
			String[] lHoursAndMinutes = lTimeAsString.split(HOURS_MINUTES_SEPARATOR);
			lPartyToCreate.setStartHour(Integer.parseInt(lHoursAndMinutes[0]) * 100 + Integer.parseInt(lHoursAndMinutes[1]));
			
			lPartyToCreate.setEndDate(mBtnSelectEndDate.getText().toString());
			lTimeAsString = mBtnSelectEndTime.getText().toString();
			lHoursAndMinutes = lTimeAsString.split(HOURS_MINUTES_SEPARATOR);
			lPartyToCreate.setEndHour(Integer.parseInt(lHoursAndMinutes[0]) * 100 + Integer.parseInt(lHoursAndMinutes[1]));			
		}				
	}
}

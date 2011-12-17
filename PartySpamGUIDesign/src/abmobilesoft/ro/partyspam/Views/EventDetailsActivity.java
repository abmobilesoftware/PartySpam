package abmobilesoft.ro.partyspam.Views;

import java.io.IOException;

import org.component.partyspam.LocationInfo;
import org.component.partyspam.Party;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import abmobilesoft.ro.partyspam.R;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetailsActivity extends Activity {
	public static final String PARTY_DESCRIPTION = "_partyDesc";
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_details);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String lPartyDescription = (String) extras
					.getString(PARTY_DESCRIPTION);
			try {
				Party lPartyToShow = new Party(lPartyDescription);
				ImageView lPartyImage = (ImageView) findViewById(R.id.evListViewIconEvent);
				TextView lPartyTitle = (TextView) findViewById(R.id.evListViewTxtEventTitle);
				Resources res = getResources();
				Drawable drawable = res.getDrawable(R.drawable.ic_launcher);
				lPartyImage.setImageDrawable(drawable);
				lPartyTitle.setText(lPartyToShow.getTitle());
				String lStartHourAndDate = StringFormattingForParty
						.extractDateAndHourStringFromInteger(
								lPartyToShow.getStartHour(),
								lPartyToShow.getStartDate());
				((TextView) findViewById(R.id.evListViewTxtStartDateAndHour))
						.setText(lStartHourAndDate);

				String lEndHourAndDate = StringFormattingForParty
						.extractDateAndHourStringFromInteger(
								lPartyToShow.getEndHour(),
								lPartyToShow.getEndDate());
				((TextView) findViewById(R.id.evListViewTxtEndDateAndHour))
						.setText(lEndHourAndDate);

				((TextView) findViewById(R.id.evListViewDetails))
						.setText(lPartyToShow.getDescription());
				LocationInfo lPartyLocation = lPartyToShow.getLocation();
				((TextView) findViewById(R.id.evListViewTxtAdditionalInfo))
						.setText(lPartyLocation.getAdditionalLocationData());
			} catch (SAXParseException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}

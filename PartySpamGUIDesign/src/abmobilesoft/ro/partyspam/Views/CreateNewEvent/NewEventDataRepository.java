package abmobilesoft.ro.partyspam.Views.CreateNewEvent;

import java.sql.Date;

import android.graphics.Bitmap;
import android.text.format.Time;

public class NewEventDataRepository {
	private static NewEventDataRepository mEventData = null;

	private String mEventName;
	private String mEventDescription;
	private Date mStartDate;
	private Date mEndDate;
	private Time mStartHour;
	private Time mEndHour;
	private String mContact;
	private Bitmap mImage;
	private int mRadius;
	private double mLongitude;
	private double mLatitude;
	private String mLocationAdditionalInformation;
	
	/**
	 * @return the mEventName
	 */
	public String getEventName() {
		return mEventName;
	}

	/**
	 * @param mEventName the mEventName to set
	 */
	public void setEventName(String mEventName) {
		this.mEventName = mEventName;
	}

	/**
	 * @return the mEventDescription
	 */
	public String getEventDescription() {
		return mEventDescription;
	}

	/**
	 * @param mEventDescription the mEventDescription to set
	 */
	public void setEventDescription(String mEventDescription) {
		this.mEventDescription = mEventDescription;
	}

	/**
	 * @return the mStartDate
	 */
	public Date getStartDate() {
		return mStartDate;
	}

	/**
	 * @param mStartDate the mStartDate to set
	 */
	public void setStartDate(Date mStartDate) {
		this.mStartDate = mStartDate;
	}

	/**
	 * @return the mEndDate
	 */
	public Date getEndDate() {
		return mEndDate;
	}

	/**
	 * @param mEndDate the mEndDate to set
	 */
	public void setEndDate(Date mEndDate) {
		this.mEndDate = mEndDate;
	}

	/**
	 * @return the mStartHour
	 */
	public Time getStartHour() {
		return mStartHour;
	}

	/**
	 * @param mStartHour the mStartHour to set
	 */
	public void setStartHour(Time mStartHour) {
		this.mStartHour = mStartHour;
	}

	/**
	 * @return the mEndHour
	 */
	public Time getEndHour() {
		return mEndHour;
	}

	/**
	 * @param mEndHour the mEndHour to set
	 */
	public void setEndHour(Time mEndHour) {
		this.mEndHour = mEndHour;
	}

	/**
	 * @return the mContact
	 */
	public String getContact() {
		return mContact;
	}

	/**
	 * @param mContact the mContact to set
	 */
	public void setContact(String mContact) {
		this.mContact = mContact;
	}

	/**
	 * @return the mImage
	 */
	public Bitmap getImage() {
		return mImage;
	}

	/**
	 * @param mImage the mImage to set
	 */
	public void setImage(Bitmap mImage) {
		this.mImage = mImage;
	}

	/**
	 * @return the mRadius
	 */
	public int getRadius() {
		return mRadius;
	}

	/**
	 * @param mRadius the mRadius to set
	 */
	public void setRadius(int mRadius) {
		this.mRadius = mRadius;
	}

	/**
	 * @return the mLongitude
	 */
	public double getLongitude() {
		return mLongitude;
	}

	/**
	 * @param mLongitude the mLongitude to set
	 */
	public void setLongitude(double mLongitude) {
		this.mLongitude = mLongitude;
	}

	/**
	 * @return the mLatitude
	 */
	public double getLatitude() {
		return mLatitude;
	}

	/**
	 * @param mLatitude the mLatitude to set
	 */
	public void setLatitude(double mLatitude) {
		this.mLatitude = mLatitude;
	}

	/**
	 * @return the mLocationAdditionalInformation
	 */
	public String getLocationAdditionalInformation() {
		return mLocationAdditionalInformation;
	}

	/**
	 * @param mLocationAdditionalInformation the mLocationAdditionalInformation to set
	 */
	public void setLocationAdditionalInformation(
			String mLocationAdditionalInformation) {
		this.mLocationAdditionalInformation = mLocationAdditionalInformation;
	}

	/**
	 * @return the eventName
	 */
	public String getEventTitle() {
		return mEventName;
	}

	/**
	 * @param eventName
	 *            the eventName to set
	 */
	public void setEventTitle(String eventName) {
		mEventName = eventName;
	}

	private NewEventDataRepository() {

	}

	public static NewEventDataRepository getInstance() {
		// add the new listener to my listeners
		if (mEventData == null) {
			mEventData = new NewEventDataRepository();
		}
		return mEventData;
	}

	public void clearEventData() {
		mEventName = null;
	}
}

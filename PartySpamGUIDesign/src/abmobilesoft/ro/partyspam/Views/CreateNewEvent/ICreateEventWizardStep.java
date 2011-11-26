package abmobilesoft.ro.partyspam.Views.CreateNewEvent;

public interface ICreateEventWizardStep {
	/**
	 * On entering the current step restore fields content with data from the repository
	 */
	public  void restoreEventData();
	/**
	 * Before leaving the current step this will be called to save the current entered data
	 */
	public void saveEventData();	
}

package com.partyspam;

public class PartySpamServiceSoapProxy implements com.partyspam.PartySpamServiceSoap {
  private String _endpoint = null;
  private com.partyspam.PartySpamServiceSoap partySpamServiceSoap = null;
  
  public PartySpamServiceSoapProxy() {
    _initPartySpamServiceSoapProxy();
  }
  
  public PartySpamServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initPartySpamServiceSoapProxy();
  }
  
  private void _initPartySpamServiceSoapProxy() {
    try {
      partySpamServiceSoap = (new com.partyspam.PartySpamServiceLocator()).getPartySpamServiceSoap();
      if (partySpamServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)partySpamServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)partySpamServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (partySpamServiceSoap != null)
      ((javax.xml.rpc.Stub)partySpamServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.partyspam.PartySpamServiceSoap getPartySpamServiceSoap() {
    if (partySpamServiceSoap == null)
      _initPartySpamServiceSoapProxy();
    return partySpamServiceSoap;
  }
  
  public java.lang.String addUser(java.lang.String iUserId, java.lang.String iTelephoneId, java.lang.String iLocation) throws java.rmi.RemoteException{
    if (partySpamServiceSoap == null)
      _initPartySpamServiceSoapProxy();
    return partySpamServiceSoap.addUser(iUserId, iTelephoneId, iLocation);
  }
  
  public void deleteUser(java.lang.String iUserId) throws java.rmi.RemoteException{
    if (partySpamServiceSoap == null)
      _initPartySpamServiceSoapProxy();
    partySpamServiceSoap.deleteUser(iUserId);
  }
  
  public void updateUser(java.lang.String iUserId, java.lang.String iLocation) throws java.rmi.RemoteException{
    if (partySpamServiceSoap == null)
      _initPartySpamServiceSoapProxy();
    partySpamServiceSoap.updateUser(iUserId, iLocation);
  }
  
  public java.lang.String[] selectUsersFromRange(double iLat, double iLng, int iRadius) throws java.rmi.RemoteException{
    if (partySpamServiceSoap == null)
      _initPartySpamServiceSoapProxy();
    return partySpamServiceSoap.selectUsersFromRange(iLat, iLng, iRadius);
  }
  
  public void addParty(java.lang.String iTitle, 
		  java.lang.String iDescription, 
		  java.lang.String iPhoneNumber, 
		  java.lang.String iUserId, 
		  int iNrOfAttendees, 
		  java.lang.String iStartDate, 
		  java.lang.String iEndDate, 
		  int iStartHour, 
		  int iEndHour, 
		  double iLat, 
		  double iLng, 
		  int iRadius, 
		  java.lang.String iAdditionalInfo, 
		  java.lang.String iImage) throws java.rmi.RemoteException{
    if (partySpamServiceSoap == null)
      _initPartySpamServiceSoapProxy();
    partySpamServiceSoap.addParty(iTitle, iDescription, iPhoneNumber, iUserId, iNrOfAttendees, iStartDate, iEndDate, iStartHour, iEndHour, iLat, iLng, iRadius, iAdditionalInfo, iImage);
  }
  
  public void deletePartiesByDate(java.lang.String iDate, int iHour) throws java.rmi.RemoteException{
    if (partySpamServiceSoap == null)
      _initPartySpamServiceSoapProxy();
    partySpamServiceSoap.deletePartiesByDate(iDate, iHour);
  }
  
  public void deleteExpiredParties() throws java.rmi.RemoteException{
    if (partySpamServiceSoap == null)
      _initPartySpamServiceSoapProxy();
    partySpamServiceSoap.deleteExpiredParties();
  }
  
  public com.partyspam.Party[] selectPartiesFromRange(double iLat, double iLng, int iRadius) throws java.rmi.RemoteException{
    if (partySpamServiceSoap == null)
      _initPartySpamServiceSoapProxy();
    return partySpamServiceSoap.selectPartiesFromRange(iLat, iLng, iRadius);
  }
  
  
}
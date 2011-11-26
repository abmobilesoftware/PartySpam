/**
 * PartySpamServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.partyspam;

public interface PartySpamServiceSoap extends java.rmi.Remote {
    public java.lang.String addUser(java.lang.String iUserId, java.lang.String iTelephoneId, java.lang.String iLocation) throws java.rmi.RemoteException;
    public void deleteUser(java.lang.String iUserId) throws java.rmi.RemoteException;
    public void updateUser(java.lang.String iUserId, java.lang.String iLocation) throws java.rmi.RemoteException;
    public java.lang.String[] selectUsersFromRange(double iLat, double iLng, int iRadius) throws java.rmi.RemoteException;
    public void addParty(java.lang.String iTitle, java.lang.String iDescription, java.lang.String iPhoneNumber, java.lang.String iUserId, int iNrOfAttendees, java.lang.String iStartDate, java.lang.String iEndDate, int iStartHour, int iEndHour, double iLat, double iLng, int iRadius, java.lang.String iAdditionalInfo, java.lang.String iImage) throws java.rmi.RemoteException;
    public void deletePartiesByDate(java.lang.String iDate, int iHour) throws java.rmi.RemoteException;
    public void deleteExpiredParties() throws java.rmi.RemoteException;
    public com.partyspam.Party[] selectPartiesFromRange(double iLat, double iLng, int iRadius) throws java.rmi.RemoteException;
}

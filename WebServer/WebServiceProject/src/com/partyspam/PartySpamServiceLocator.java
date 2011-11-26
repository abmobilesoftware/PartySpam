/**
 * PartySpamServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.partyspam;

public class PartySpamServiceLocator extends org.apache.axis.client.Service implements com.partyspam.PartySpamService {

    public PartySpamServiceLocator() {
    }


    public PartySpamServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PartySpamServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PartySpamServiceSoap
    private java.lang.String PartySpamServiceSoap_address = "http://cluj-info.com/partyspam/PartySpamService.asmx";

    public java.lang.String getPartySpamServiceSoapAddress() {
        return PartySpamServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PartySpamServiceSoapWSDDServiceName = "PartySpamServiceSoap";

    public java.lang.String getPartySpamServiceSoapWSDDServiceName() {
        return PartySpamServiceSoapWSDDServiceName;
    }

    public void setPartySpamServiceSoapWSDDServiceName(java.lang.String name) {
        PartySpamServiceSoapWSDDServiceName = name;
    }

    public com.partyspam.PartySpamServiceSoap getPartySpamServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PartySpamServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPartySpamServiceSoap(endpoint);
    }

    public com.partyspam.PartySpamServiceSoap getPartySpamServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.partyspam.PartySpamServiceSoapStub _stub = new com.partyspam.PartySpamServiceSoapStub(portAddress, this);
            _stub.setPortName(getPartySpamServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPartySpamServiceSoapEndpointAddress(java.lang.String address) {
        PartySpamServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.partyspam.PartySpamServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.partyspam.PartySpamServiceSoapStub _stub = new com.partyspam.PartySpamServiceSoapStub(new java.net.URL(PartySpamServiceSoap_address), this);
                _stub.setPortName(getPartySpamServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("PartySpamServiceSoap".equals(inputPortName)) {
            return getPartySpamServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://partyspam.com/", "PartySpamService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://partyspam.com/", "PartySpamServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PartySpamServiceSoap".equals(portName)) {
            setPartySpamServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

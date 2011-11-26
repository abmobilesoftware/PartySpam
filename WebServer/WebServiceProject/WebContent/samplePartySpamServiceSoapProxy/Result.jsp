<%@page contentType="text/html;charset=UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="samplePartySpamServiceSoapProxyid" scope="session" class="com.partyspam.PartySpamServiceSoapProxy" />
<%
if (request.getParameter("endpoint") != null && request.getParameter("endpoint").length() > 0)
samplePartySpamServiceSoapProxyid.setEndpoint(request.getParameter("endpoint"));
%>

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        java.lang.String getEndpoint2mtemp = samplePartySpamServiceSoapProxyid.getEndpoint();
if(getEndpoint2mtemp == null){
%>
<%=getEndpoint2mtemp %>
<%
}else{
        String tempResultreturnp3 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(getEndpoint2mtemp));
        %>
        <%= tempResultreturnp3 %>
        <%
}
break;
case 5:
        gotMethod = true;
        String endpoint_0id=  request.getParameter("endpoint8");
            java.lang.String endpoint_0idTemp = null;
        if(!endpoint_0id.equals("")){
         endpoint_0idTemp  = endpoint_0id;
        }
        samplePartySpamServiceSoapProxyid.setEndpoint(endpoint_0idTemp);
break;
case 10:
        gotMethod = true;
        com.partyspam.PartySpamServiceSoap getPartySpamServiceSoap10mtemp = samplePartySpamServiceSoapProxyid.getPartySpamServiceSoap();
if(getPartySpamServiceSoap10mtemp == null){
%>
<%=getPartySpamServiceSoap10mtemp %>
<%
}else{
        if(getPartySpamServiceSoap10mtemp!= null){
        String tempreturnp11 = getPartySpamServiceSoap10mtemp.toString();
        %>
        <%=tempreturnp11%>
        <%
        }}
break;
case 13:
        gotMethod = true;
        String iUserId_1id=  request.getParameter("iUserId16");
            java.lang.String iUserId_1idTemp = null;
        if(!iUserId_1id.equals("")){
         iUserId_1idTemp  = iUserId_1id;
        }
        String iTelephoneId_2id=  request.getParameter("iTelephoneId18");
            java.lang.String iTelephoneId_2idTemp = null;
        if(!iTelephoneId_2id.equals("")){
         iTelephoneId_2idTemp  = iTelephoneId_2id;
        }
        String iLocation_3id=  request.getParameter("iLocation20");
            java.lang.String iLocation_3idTemp = null;
        if(!iLocation_3id.equals("")){
         iLocation_3idTemp  = iLocation_3id;
        }
        java.lang.String addUser13mtemp = samplePartySpamServiceSoapProxyid.addUser(iUserId_1idTemp,iTelephoneId_2idTemp,iLocation_3idTemp);
if(addUser13mtemp == null){
%>
<%=addUser13mtemp %>
<%
}else{
        String tempResultreturnp14 = org.eclipse.jst.ws.util.JspUtils.markup(String.valueOf(addUser13mtemp));
        %>
        <%= tempResultreturnp14 %>
        <%
}
break;
case 22:
        gotMethod = true;
        String iUserId_4id=  request.getParameter("iUserId25");
            java.lang.String iUserId_4idTemp = null;
        if(!iUserId_4id.equals("")){
         iUserId_4idTemp  = iUserId_4id;
        }
        samplePartySpamServiceSoapProxyid.deleteUser(iUserId_4idTemp);
break;
case 27:
        gotMethod = true;
        String iUserId_5id=  request.getParameter("iUserId30");
            java.lang.String iUserId_5idTemp = null;
        if(!iUserId_5id.equals("")){
         iUserId_5idTemp  = iUserId_5id;
        }
        String iLocation_6id=  request.getParameter("iLocation32");
            java.lang.String iLocation_6idTemp = null;
        if(!iLocation_6id.equals("")){
         iLocation_6idTemp  = iLocation_6id;
        }
        samplePartySpamServiceSoapProxyid.updateUser(iUserId_5idTemp,iLocation_6idTemp);
break;
case 34:
        gotMethod = true;
        String iLat_7id=  request.getParameter("iLat37");
        double iLat_7idTemp  = Double.parseDouble(iLat_7id);
        String iLng_8id=  request.getParameter("iLng39");
        double iLng_8idTemp  = Double.parseDouble(iLng_8id);
        String iRadius_9id=  request.getParameter("iRadius41");
        int iRadius_9idTemp  = Integer.parseInt(iRadius_9id);
        java.lang.String[] selectUsersFromRange34mtemp = samplePartySpamServiceSoapProxyid.selectUsersFromRange(iLat_7idTemp,iLng_8idTemp,iRadius_9idTemp);
if(selectUsersFromRange34mtemp == null){
%>
<%=selectUsersFromRange34mtemp %>
<%
}else{
        String tempreturnp35 = null;
        if(selectUsersFromRange34mtemp != null){
        java.util.List listreturnp35= java.util.Arrays.asList(selectUsersFromRange34mtemp);
        tempreturnp35 = listreturnp35.toString();
        }
        %>
        <%=tempreturnp35%>
        <%
}
break;
case 43:
        gotMethod = true;
        String iTitle_10id=  request.getParameter("iTitle46");
            java.lang.String iTitle_10idTemp = null;
        if(!iTitle_10id.equals("")){
         iTitle_10idTemp  = iTitle_10id;
        }
        String iDescription_11id=  request.getParameter("iDescription48");
            java.lang.String iDescription_11idTemp = null;
        if(!iDescription_11id.equals("")){
         iDescription_11idTemp  = iDescription_11id;
        }
        String iPhoneNumber_12id=  request.getParameter("iPhoneNumber50");
            java.lang.String iPhoneNumber_12idTemp = null;
        if(!iPhoneNumber_12id.equals("")){
         iPhoneNumber_12idTemp  = iPhoneNumber_12id;
        }
        String iUserId_13id=  request.getParameter("iUserId52");
            java.lang.String iUserId_13idTemp = null;
        if(!iUserId_13id.equals("")){
         iUserId_13idTemp  = iUserId_13id;
        }
        String iNrOfAttendees_14id=  request.getParameter("iNrOfAttendees54");
        int iNrOfAttendees_14idTemp  = Integer.parseInt(iNrOfAttendees_14id);
        String iStartDate_15id=  request.getParameter("iStartDate56");
            java.lang.String iStartDate_15idTemp = null;
        if(!iStartDate_15id.equals("")){
         iStartDate_15idTemp  = iStartDate_15id;
        }
        String iEndDate_16id=  request.getParameter("iEndDate58");
            java.lang.String iEndDate_16idTemp = null;
        if(!iEndDate_16id.equals("")){
         iEndDate_16idTemp  = iEndDate_16id;
        }
        String iStartHour_17id=  request.getParameter("iStartHour60");
        int iStartHour_17idTemp  = Integer.parseInt(iStartHour_17id);
        String iEndHour_18id=  request.getParameter("iEndHour62");
        int iEndHour_18idTemp  = Integer.parseInt(iEndHour_18id);
        String iLat_19id=  request.getParameter("iLat64");
        double iLat_19idTemp  = Double.parseDouble(iLat_19id);
        String iLng_20id=  request.getParameter("iLng66");
        double iLng_20idTemp  = Double.parseDouble(iLng_20id);
        String iRadius_21id=  request.getParameter("iRadius68");
        int iRadius_21idTemp  = Integer.parseInt(iRadius_21id);
        String iAdditionalInfo_22id=  request.getParameter("iAdditionalInfo70");
            java.lang.String iAdditionalInfo_22idTemp = null;
        if(!iAdditionalInfo_22id.equals("")){
         iAdditionalInfo_22idTemp  = iAdditionalInfo_22id;
        }
        String iImage_23id=  request.getParameter("iImage72");
            java.lang.String iImage_23idTemp = null;
        if(!iImage_23id.equals("")){
         iImage_23idTemp  = iImage_23id;
        }
        samplePartySpamServiceSoapProxyid.addParty(iTitle_10idTemp,iDescription_11idTemp,iPhoneNumber_12idTemp,iUserId_13idTemp,iNrOfAttendees_14idTemp,iStartDate_15idTemp,iEndDate_16idTemp,iStartHour_17idTemp,iEndHour_18idTemp,iLat_19idTemp,iLng_20idTemp,iRadius_21idTemp,iAdditionalInfo_22idTemp,iImage_23idTemp);
break;
case 74:
        gotMethod = true;
        String iDate_24id=  request.getParameter("iDate77");
            java.lang.String iDate_24idTemp = null;
        if(!iDate_24id.equals("")){
         iDate_24idTemp  = iDate_24id;
        }
        String iHour_25id=  request.getParameter("iHour79");
        int iHour_25idTemp  = Integer.parseInt(iHour_25id);
        samplePartySpamServiceSoapProxyid.deletePartiesByDate(iDate_24idTemp,iHour_25idTemp);
break;
case 81:
        gotMethod = true;
        samplePartySpamServiceSoapProxyid.deleteExpiredParties();
break;
case 84:
        gotMethod = true;
        String iLat_26id=  request.getParameter("iLat87");
        double iLat_26idTemp  = Double.parseDouble(iLat_26id);
        String iLng_27id=  request.getParameter("iLng89");
        double iLng_27idTemp  = Double.parseDouble(iLng_27id);
        String iRadius_28id=  request.getParameter("iRadius91");
        int iRadius_28idTemp  = Integer.parseInt(iRadius_28id);
        com.partyspam.Party[] selectPartiesFromRange84mtemp = samplePartySpamServiceSoapProxyid.selectPartiesFromRange(iLat_26idTemp,iLng_27idTemp,iRadius_28idTemp);
if(selectPartiesFromRange84mtemp == null){
%>
<%=selectPartiesFromRange84mtemp %>
<%
}else{
        String tempreturnp85 = null;
        if(selectPartiesFromRange84mtemp != null){
        java.util.List listreturnp85= java.util.Arrays.asList(selectPartiesFromRange84mtemp);
        tempreturnp85 = listreturnp85.toString();
        }
        %>
        <%=tempreturnp85%>
        <%
}
break;
}
} catch (Exception e) { 
%>
exception: <%= e %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>
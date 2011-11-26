package org.component.partyspam;
import java.io.IOException;

import org.jivesoftware.smack.packet.Message;
import org.xml.sax.SAXException;

public class MessageProcessing {
	
	public static Party buildEventFromMessageBody(Message iMsgToBeProcessed) throws org.xml.sax.SAXParseException,SAXException,IOException
	{
		Party lEvent = new Party(iMsgToBeProcessed.getBody());				
		return lEvent;
	}
}

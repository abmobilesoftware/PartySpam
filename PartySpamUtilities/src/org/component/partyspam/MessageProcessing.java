package org.component.partyspam;
import org.jivesoftware.smack.packet.Message;

public class MessageProcessing {
	
	public static Party buildEventFromMessageBody(Message iMsgToBeProcessed)
	{
		Party lEvent = new Party(iMsgToBeProcessed.getBody());				
		return lEvent;
	}
}

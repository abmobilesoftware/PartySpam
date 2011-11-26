package org.component.partyspam;

import java.util.logging.*;
import org.jivesoftware.whack.*;
import org.xmpp.component.*;

public class Main {
   public static void main(String[] args) {
      ExternalComponentManager mgr = new ExternalComponentManager("andospc", 5275);
      mgr.setSecretKey("partyspam", "13071989");
      try {
         mgr.addComponent("partyspam", new PartySpamComponent());
      } catch (ComponentException e) {
         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "main", e);
         System.exit(-1);
      }
      //Keep it alive
      while (true)
         try {
            Thread.sleep(10000);
         } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "main", e);
         }
   }
}
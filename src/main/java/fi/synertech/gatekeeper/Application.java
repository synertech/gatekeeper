
package fi.synertech.gatekeeper;

import fi.synertech.gatekeeper.nfcreader.Gatekeeper;

/**
 *
 * @author Toni Oksanen
 */
public class Application {
  
  public static void main( String[] args ) {
    
    AccessManager accessManager = new AccessManager();
    
    Gatekeeper gatekeeper = new Gatekeeper();
    
    gatekeeper
      .getInReader()
      .onReading( event -> {
        if ( accessManager.hasAccess( event.rfid() ) ) {
          System.out.println( "Gate opening..." );
        } else {
          System.out.println( "You don't have a permission!" );
        }
      });
    
    gatekeeper
      .getOutReader()
      .onReading( event -> {
        System.out.println( "welcome again..." );
      });
    
  }
  
}

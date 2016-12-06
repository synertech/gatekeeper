
package fi.synertech.gatekeeper;

import fi.synertech.gatekeeper.nfcreader.Gatekeeper;

/**
 *
 * @author Toni Oksanen
 */
public class Application {
  
  public static void main( String[] args ) {
    
    AccessManager accessManager = new AccessManager();
    Gatekeeper gatekeeper = new Gatekeeper( accessManager );
  
    gatekeeper.start();
    
  }
  
}


package fi.synertech.gatekeeper;

import fi.synertech.gatekeeper.service.AccessManager;
import fi.synertech.gatekeeper.nfcreader.Gatekeeper;
import fi.synertech.gatekeeper.service.CheckpointService;

/**
 *
 * @author Toni Oksanen
 */
public class Application {
  
  public static void main( String[] args ) {
    
    AccessManager accessManager = new AccessManager();
    CheckpointService checkpointService = new CheckpointService( accessManager );
    
    Gatekeeper gatekeeper = new Gatekeeper( accessManager, checkpointService );
  
    gatekeeper.start();
    
  }
  
}

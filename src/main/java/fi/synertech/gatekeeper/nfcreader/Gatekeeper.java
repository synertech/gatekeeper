
package fi.synertech.gatekeeper.nfcreader;

import fi.synertech.gatekeeper.service.AccessManager;
import fi.synertech.gatekeeper.service.CheckpointService;

/**
 *
 * @author Toni Oksanen
 */

public class Gatekeeper {
  
  private final NfcReaderHub hub;
  private final NfcReader inReader;
  private final NfcReader outReader;
  private final AccessManager accessManager;
  
  /**
   * Konstruktori.
   * 
   * @param accessManager
   * @param checkpointService
   */
  
  public Gatekeeper( AccessManager accessManager, 
                     CheckpointService checkpointService ) {
    
    this.accessManager = accessManager;
    
    this.hub = NfcReaderHub.getInstance();
    this.inReader = new NfcReader( "IN_READER" );
    this.outReader = new NfcReader( "OUT_READER" );
  
  }
  
  /**
   * Käynnistää Gatekeeperin.
   * 
   * 
   */
  
  public void start() {
    
    hub.start();
    
    hub.connect( inReader );
    hub.connect( outReader );
    
    inReader.onReading( event -> {
      if ( accessManager.hasAccess( event.rfid() ) ) {
        System.out.println( "Gate opening..." );
      } else {
        System.out.println( "You don't have a permission!" );
      }
    });
    
    outReader.onReading( event -> {
      System.out.println( "welcome again..." );
    });
    
  }
  
}

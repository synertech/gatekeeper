
package fi.synertech.gatekeeper;

import fi.synertech.gatekeeper.nfcreader.NfcReader;
import fi.synertech.gatekeeper.nfcreader.NfcReaderHub;

/**
 *
 * @author Toni Oksanen
 */
public class Application {
  
  public static void main( String[] args ) {
    
    AccessManager accessManager = new AccessManager();
    
    //////// Luodaan lukijat ///////
    
    NfcReader inReader = new NfcReader( "IN_READER" );
    NfcReader outReader = new NfcReader( "OUT_READER" );
    
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
    
    /////////////// Luodaan lukijoille hubi ja liitetään lukijat /////////
    
    NfcReaderHub hub = NfcReaderHub.getInstance();
    
    hub.connect( inReader );
    hub.connect( outReader ); 
    
  }
  
}


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
    
    NfcReaderHub readerHub = NfcReaderHub.getInstance();
    readerHub.start();
    
    NfcReader inReader = new NfcReader( "IN_READER" );
    NfcReader outReader = new NfcReader( "OUT_READER" );
    
    readerHub.connect( inReader );
    readerHub.connect( outReader ); 
    
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

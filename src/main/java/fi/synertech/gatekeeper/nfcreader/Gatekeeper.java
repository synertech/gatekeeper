
package fi.synertech.gatekeeper.nfcreader;

/**
 *
 * @author Toni Oksanen
 */

public class Gatekeeper {
  
  private final NfcReaderHub hub;
  private final NfcReader inReader;
  private final NfcReader outReader;
  
  /**
   * Konstruktori.
   * 
   */
  
  public Gatekeeper() {
    
    this.hub = NfcReaderHub.getInstance();
    
    this.inReader = new NfcReader( "IN_READER" );
    this.outReader = new NfcReader( "OUT_READER" );
    
    hub.connect( inReader );
    hub.connect( outReader );
  
  }
  
  /**
   * Sisäänlukija.
   * 
   * @return 
   */
  
  public NfcReader getInReader() {
    return inReader;
  }
  
  /**
   * Uloslukija.
   * 
   * @return 
   */
  
  public NfcReader getOutReader() {
    return outReader;
  }
  
  
}

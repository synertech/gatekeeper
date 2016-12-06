
package fi.synertech.gatekeeper.nfcreader.event;

/**
 *
 * @author Toni Oksanen
 */
public class RfidReadEvent extends NfcReaderEvent {
  
  private final String rfid;
  
  /**
   * Konstruktori.
   * 
   * @param rfid
   * @param readerName 
   */
  
  public RfidReadEvent( String rfid, 
                        String readerName ) {
    
    super( readerName );
    this.rfid = rfid;
    
  }
  
  /**
   * Luettu RFID.
   * 
   * @return 
   */
  
  public String rfid() {
    return rfid;
  }
  
}

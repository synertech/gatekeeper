
package fi.synertech.gatekeeper.nfcreader.event;

import java.util.Date;

/**
 *
 * @author Toni Oksanen
 */
public abstract class NfcReaderEvent {
  
  private final Date timestamp;
  private final String readerName;
  
  /**
   * Konstruktori.
   * 
   * @param readerName 
   */
  
  public NfcReaderEvent( String readerName ) {
    
    this.timestamp = new Date();
    this.readerName = readerName;
  
  }
  
  /**
   * Ajankohta.
   * 
   * @return 
   */
  
  public Date timestamp() {
    return timestamp;
  }
  
  /**
   * Lukijan nimi.
   * 
   * @return 
   */
  
  public String readerName() {
    return readerName;
  }
  
}

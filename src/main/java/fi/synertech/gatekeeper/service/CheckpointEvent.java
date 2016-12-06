
package fi.synertech.gatekeeper.service;

import java.util.Date;

/**
 *
 * @author Toni Oksanen
 */
public abstract class CheckpointEvent {
  
  private final String rfid;
  private final String imei;
  private final Date timestamp;
  
  public CheckpointEvent( String rfid,
                          String imei, 
                          Date timestamp ) {
    
    this.rfid = rfid;
    this.imei = imei;
    this.timestamp = timestamp;
 
  }
  
  /**
   * RFID
   * 
   * @return 
   */
  
  public String rfid() {
    return rfid;
  }
  
  /**
   * Ajanhetki, kun 
   * tarkastuspisteestä on kuljettu.
   * 
   * @return 
   */
  
  public Date timestamp() {
    return timestamp;
  }
  
  /**
   * Kulunvalvontalaitteen IMEI-koodi.
   * 
   * @return 
   */
  
  public String imei() {
    return imei;
  }
  
}

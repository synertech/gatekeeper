
package fi.synertech.gatekeeper.service;

import java.util.Date;

/**
 *
 * @author Toni Oksanen
 */
public class CheckedOut extends CheckpointEvent {

  public CheckedOut(String rfid, String imei, Date timestamp) {
    super(rfid, imei, timestamp);
  }
  
}

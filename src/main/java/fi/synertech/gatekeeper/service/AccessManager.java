
package fi.synertech.gatekeeper.service;

/**
 *
 * @author Toni Oksanen
 */
public class AccessManager {
  
  public boolean hasAccess( String rfid ) {
    return rfid.equals( "04AE72B21F2780" );
  }

}

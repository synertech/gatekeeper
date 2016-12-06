
package fi.synertech.gatekeeper.nfcreader;

import fi.synertech.gatekeeper.nfcreader.event.EventListener;
import fi.synertech.gatekeeper.nfcreader.event.NfcReaderEvent;
import fi.synertech.gatekeeper.nfcreader.event.RfidReadEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import static javax.xml.bind.DatatypeConverter.parseHexBinary;
import static org.apache.commons.codec.binary.Hex.encodeHexString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Toni Oksanen
 */

public class NfcReader implements Runnable {
  
  private CardTerminal terminal;
  private boolean connected = false;
  
  private final String name;
  private final List<EventListener<NfcReaderEvent>> listeners;
  
  private static final CommandAPDU READ_RFID_APDU = 
    new CommandAPDU( parseHexBinary( "FFCA000000" ) );
  
  private static Logger logger;
  
 /**
   * Konstruktori.
   * 
   * @param name
   */
  
  public NfcReader( String name ) {
    
    this.name = name;
    this.listeners = new ArrayList<>();
    
    if ( logger == null ) {
      logger = LoggerFactory.getLogger( getClass() );
    }
    
  }
  
  /**
   * Rekisteröi kuuntelijan, jota
   * notifoidaan rfid:n lukemisen yhteydessä.
   * 
   * @param listener
   */
  
  public void onReading( Consumer<RfidReadEvent> listener ) {
    listeners.add( new EventListener( RfidReadEvent.class, listener ) );
  }
  
  /**
   * Lukijan nimi.
   * 
   * @return 
   */
  
  public String name() {
    return name;
  }
  
  /**
   * Monitoroi lukijan tilaa
   * ja lukee rfid:n, jos sellainen
   * on esitetty lukijan päällä.
   * 
   */
  
  @Override
  public void run() {
    
    while ( connected ) {
      
      try {
        
        if ( terminal.isCardPresent() ) { 
        
          Card card = terminal.connect( "*" );
          CardChannel channel = card.getBasicChannel();

          ResponseAPDU resp = channel.transmit( READ_RFID_APDU );
          String rfid = encodeHexString( resp.getData() ).toUpperCase();
          
          listeners.forEach( listener -> {
            listener.accept( new RfidReadEvent( rfid, name ) );
          });
          
        }
        
        Thread.sleep( 800 );
        
      } catch ( CardException | InterruptedException e ) {}
      
    }
    
  }
  
  /////////////////////////////////////////////////////////////////
  ////////////////////// Protected methods ////////////////////////
  /////////////////////////////////////////////////////////////////
  
  /**
   * Kertoo, onko lukija kytketty.
   * 
   * @return 
   */
  
  protected boolean isConnected() {
    return connected;
  }
  
  /**
   * Liittää lukijan.
   * 
   * @param terminal 
   */
  
  protected void connect( CardTerminal terminal ) {
    
    this.connected = true;
    this.terminal = terminal;
    
    logger.info( "NFC-Reader '" + name + "' has been connected." );
    
  }
  
  /**
   * Irrottaa lukijan. 
   * 
   */
  
  protected void disconnect() {
    
    this.connected = false; 
    this.terminal = null;
    
    logger.info( "NFC-Reader '" + name + "' has been disconnected." );
    
  }
  
  /**
   * Palauttaa viitteen käytössä 
   * olevan terminaaliin.
   * 
   * @return 
   */
  
  protected CardTerminal terminal() {
    return terminal;
  }
  

}

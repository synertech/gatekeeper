
package fi.synertech.gatekeeper.nfcreader;

import fi.synertech.gatekeeper.nfcreader.event.EventListener;
import fi.synertech.gatekeeper.nfcreader.event.NfcReaderEvent;
import fi.synertech.gatekeeper.nfcreader.event.RfidReadEvent;
import static java.lang.Thread.sleep;
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
  
 /**
   * Konstruktori.
   * 
   * @param name
   */
  
  public NfcReader( String name ) {
    
    this.name = name;
    this.listeners = new ArrayList<>();
    
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
   * Liittää lukijan.
   * 
   * @param terminal 
   * @return  
   */
  
  public NfcReader connect( CardTerminal terminal ) {
    
    this.connected = true;
    this.terminal = terminal;
    
    return this;
    
  }
  
  /**
   * Irrottaa lukijan. 
   * 
   */
  
  public void disconnect() {
    this.connected = false; 
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
   * Lukee rfid:een.
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
        
        sleep( 800 );
        
      } catch ( CardException | InterruptedException e ) {}
      
    }
    
  }
  
  
  
  
  
}

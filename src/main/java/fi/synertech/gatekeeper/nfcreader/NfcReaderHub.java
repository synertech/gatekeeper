
package fi.synertech.gatekeeper.nfcreader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

/**
 *
 * @author Toni Oksanen
 */

public class NfcReaderHub extends Thread {
  
  private static NfcReaderHub hub;
  
  private final List<NfcReader> readers;
  private final TerminalFactory factory;
  
  /**
   * Yksityinen konstruktori.
   * 
   */
  
  private NfcReaderHub() {
    
    this.readers = new ArrayList<>();
    this.factory = TerminalFactory.getDefault();
    
  }
  
  /**
   * Palauttaa NfcReaderHubin ilmentymän.
   * 
   * @return 
   */
  
  public static NfcReaderHub getInstance() {
    
    if ( hub == null ) {
      
      hub = new NfcReaderHub();
      hub.start();
      
    }
    
    return hub;
  
  }
  
  /**
   * Liittää nfc-lukija hubiin.
   * 
   * @param reader 
   */
  
  public void connect( NfcReader reader ) {
    readers.add( reader );
  }
  
  /**
   * Monitoroi liitettyjen lukijoiden tilaa. 
   * 
   * 
   */

  @Override
  public void run() {
   
    while ( true ) {
      
      try {
        
        readers.forEach( reader -> {
          if ( !reader.isConnected() ) {
            getTerminal().ifPresent( terminal -> {
              
              reader.connect( terminal );
              
              Thread thread = new Thread( reader );
              thread.start();
              
            });
          } else if ( !terminalExists( reader.terminal() ) ) {
            reader.disconnect();
          }
        });
        
        Thread.sleep( 1000L ); 
        
      } catch ( InterruptedException e ) {}
    
    }
 
  }
  
  /**
   * Palauttaa ensimmäisen vapaan terminaalin.
   * 
   * @return 
   */
  
  private Optional<CardTerminal> getTerminal() {
    
    return terminals().stream()
      .filter( terminal -> 
        !isReserved( terminal ) )
      .findFirst();
    
  }
  
  /**
   * Kertoo, löytyykö terminaalia.
   * 
   * @param terminal
   * @return 
   */
  
  private boolean terminalExists( CardTerminal terminal ) {
    return terminals().contains( terminal );
  }
  
  /**
   * Palauttaa terminaalit.
   * 
   * @return 
   */
  
  private List<CardTerminal> terminals() {
    
    try {
      return factory.terminals().list();
    } catch ( CardException e ) {
      return new ArrayList<>();
    }
  
  }
  
  /**
   * Kertoo, onko terminaali varattu.
   * 
   * @param terminal
   * @return 
   */
  
  private boolean isReserved( CardTerminal terminal ) {
    
    return readers.stream()
      .map( reader -> 
        reader.terminal() )
      .filter( term -> 
        term == terminal )
      .findAny()
      .isPresent();
    
  }
  
  
  
}
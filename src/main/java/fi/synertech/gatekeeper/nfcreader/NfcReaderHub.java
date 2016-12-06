
package fi.synertech.gatekeeper.nfcreader;

import java.util.ArrayList;
import java.util.List;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

/**
 *
 * @author Toni Oksanen
 */

public class NfcReaderHub {
  
  private static NfcReaderHub hub;
  private static int terminalId = 0;
  
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
    }
    
    return hub;
  
  }
  
  public void connect( NfcReader reader ) {
    
    readers.add( reader );
    
    Thread thread = new Thread( reader.connect( getTerminal() ) );
    thread.start();
    
  }
  
  private CardTerminal getTerminal() {
    
    try {
      
      List<CardTerminal> terminals = factory.terminals().list();
      
      if ( terminals.size() > terminalId ) {
        return terminals.get( terminalId++ );
      }
    
    } catch ( CardException e ) {}
    
    throw new RuntimeException( "" );
    
  }

  
  
  
  
  
  
  
  
  
}

































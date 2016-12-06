
package fi.synertech.gatekeeper.nfcreader.event;

import java.util.function.Consumer;

/**
 *
 * @author Toni Oksanen
 * @param <T>
 */

public class EventListener<T> {
  
  private final Class<T> eventType;
  private final Consumer<T> consumer;
  
  /**
   * Wrapper.
   * 
   * @param eventType
   * @param consumer 
   */
  
  public EventListener( Class<T> eventType, 
                        Consumer<T> consumer ) {
    
    this.eventType = eventType;
    this.consumer = consumer;
    
  }
  
  /**
   * Antaa tapahtuma-olion 
   * käsittejälle, joka suorittaa
   * sen jos tukee kyseiseen tyyppistä tapahtumaa.
   * 
   * @param event 
   */
  
  public void accept( T event ) {
    
    if ( event.getClass() == eventType ) {
      consumer.accept( event ); 
    }
    
  }
  
  
}

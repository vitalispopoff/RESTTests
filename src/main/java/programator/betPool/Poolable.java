package programator.betPool;

import programator.types.*;
import java.util.Map;

public interface Poolable {
    void addTicket(
            Ticket ticket,
            Bettor bettor,
            LotteryAgent agent);
    
    Map<Long, Ticket> getAllTickets();
    
    Ticket getTicket(Long id);
    
    void checkTicket(Long id);
    
    void removeTicket(Long ticketId);
}

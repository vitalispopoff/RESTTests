package programator.betPool;

import programator.types.Bettor;
import programator.types.LotteryAgent;
import programator.types.Ticket;

import java.util.List;
import java.util.Map;

public interface Poolable {

    void addTicket(Ticket ticket, Bettor bettor, LotteryAgent agent);

    Map<Long, Ticket> getAllTickets();

    Ticket getTicket(Long id);

    void checkTicket(Long id);


}

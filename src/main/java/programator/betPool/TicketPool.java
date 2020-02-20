package programator.betPool;

import programator.types.*;
import java.io.Serializable;
import java.util.*;

public class TicketPool implements Poolable, Serializable {

    public static Map<Long, Ticket> ticketMap = new HashMap<>();
    public static Map<String, Bettor> bettorMap = new HashMap<String, Bettor>();
    public static Map<Long, LotteryAgent> agentMap = new HashMap<>();

    @Override
    public Ticket getTicket(Long ticketId) {
        return ticketMap.get(ticketId);
    }       // TODO try/catch for empty call

    @Override
    public void addTicket(Ticket ticket, Bettor bettor, LotteryAgent agent) {
        ticketMap.put(ticket.getTicketId(), ticket);
        bettorMap.put(bettor.getBettorLogin(), bettor);
        agentMap.put(agent.getAgentId(), agent);
    }

    @Override
    public void checkTicket(Long id) {
        boolean cache = ticketMap.get(id).isStatus();
        ticketMap.get(id).setStatus(!cache);

    }       // TODO try/catch for empty call

    @Override
    public Map<Long, Ticket> getAllTickets() {
        return ticketMap;
    }

    @Override
    public void removeTicket(Long ticketId) {
        ticketMap.remove(ticketId);
    }
}

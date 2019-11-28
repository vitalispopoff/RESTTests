package programator.betPool;

import programator.types.Bettor;
import programator.types.LotteryAgent;
import programator.types.Ticket;

import java.util.HashMap;
import java.util.Map;

public class TicketPool implements Poolable {

    private static Map<Long, Ticket> ticketMap = new HashMap<>();
    private static Map<Long, Bettor> bettorMap = new HashMap<>();
    private static Map<Long, LotteryAgent> agentMap = new HashMap<>();


    @Override
    public Ticket getTicket(Long id) {
        return ticketMap.get(id);
    }       // TODO try/catch for empty call

    @Override
    public void addTicket(Ticket ticket, Bettor bettor, LotteryAgent agent) {
        ticketMap.put(ticket.getTicketId(), ticket);
        bettorMap.put(bettor.getBettorId(), bettor);
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
}
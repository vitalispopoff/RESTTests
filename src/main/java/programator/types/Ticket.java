package programator.types;

import java.io.Serializable;

public class Ticket implements Serializable {

    private long ticketId;
    private String numbers;
    private Bettor bettor;
    private LotteryAgent agent;
    private boolean status = false;


    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public Bettor getBettor() {
        return bettor;
    }

    public void setBettor(Bettor bettor) {
        this.bettor = bettor;
    }

    public LotteryAgent getAgent() {
        return agent;
    }

    public void setAgent(LotteryAgent agent) {
        this.agent = agent;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

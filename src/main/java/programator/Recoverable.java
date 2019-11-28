package programator;

import programator.betPool.TicketPool;
import programator.types.Bettor;
import programator.types.LotteryAgent;
import programator.types.Ticket;

import java.io.*;
import java.util.Map;

public interface Recoverable {

       public static void serializeIt(){
        try {
            FileOutputStream fos = new FileOutputStream("LotteryTicketPool.srl");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(ticketPool);
            oos.writeObject(TicketPool.ticketMap);
            oos.writeObject(TicketPool.bettorMap);
            oos.writeObject(TicketPool.agentMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void deserializeIt() {

        try {
            FileInputStream fis = new FileInputStream("LotteryTicketPool.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            TicketPool.ticketMap = (Map<Long, Ticket>) ois.readObject();
            TicketPool.bettorMap = (Map<String, Bettor>) ois.readObject();
            TicketPool.agentMap = (Map<Long, LotteryAgent>) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

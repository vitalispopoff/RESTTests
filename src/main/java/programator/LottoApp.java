package programator;

import fi.iki.elonen.NanoHTTPD;

import java.io.File;
import java.io.IOException;

import static programator.Recoverable.deserializeIt;
//import static programator.betPool.TicketPool.setTicketPool;

public class LottoApp extends NanoHTTPD {

    RequestUrlMapper requestUrlMapper = new RequestUrlMapper();

    public LottoApp(int port) throws IOException {
        super(port);
        start(1000, false);
        System.out.println("Server is on");
    }

    public static void main(String[] args) {

        if (new File("LotteryTicketPool.txt").exists()) {
            try {
                deserializeIt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            new LottoApp(8080);

        } catch (IOException e) {
            System.out.println("Server failed to start due to : \n");
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        return requestUrlMapper.delegateRequest(session);
    }
}

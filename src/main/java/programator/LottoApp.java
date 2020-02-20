package programator;

import static programator.Recoverable.deserializeIt;
import java.io.*;
import fi.iki.elonen.NanoHTTPD;

public class LottoApp extends NanoHTTPD {

    RequestUrlMapper requestUrlMapper = new RequestUrlMapper();

    public LottoApp(int port) throws IOException {
        super(port);
        start(1000, false);
        System.out.println("Server is on");
    }

    public static void main(String[] args) {
        if (new File("LotteryTicketPool.srl").exists()) {
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

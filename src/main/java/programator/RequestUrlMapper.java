package programator;

import fi.iki.elonen.NanoHTTPD;
import programator.control.Controller;

import static fi.iki.elonen.NanoHTTPD.*;
import static fi.iki.elonen.NanoHTTPD.Method.GET;
import static fi.iki.elonen.NanoHTTPD.Method.POST;
import static fi.iki.elonen.NanoHTTPD.Response.Status.NOT_FOUND;

public class RequestUrlMapper {

    private final static String ADD_TICKET = "/ticket/add";
    private final static String ALL_TICKETS = "/ticket/getAll";
    private final static String GET_TICKET = "/ticket/get";
    private final static String CHECK_TICKET = "/ticket/check";

    public NanoHTTPD.Response delegateRequest(IHTTPSession session) {

        Controller controller = new Controller();

        if (POST.equals(session.getMethod()) && ADD_TICKET.equals(session.getUri())) {
            return controller.serveAddTicketRequest(session);
        } else if (GET.equals(session.getMethod()) && ALL_TICKETS.equals(session.getUri())) {
            return controller.serveGetAllTicketsRequest(session);
        } else if (GET.equals(session.getMethod()) && GET_TICKET.equals(session.getUri())) {
            return controller.serveGetTicketRequest(session);
        } else if (POST.equals(session.getMethod()) && CHECK_TICKET.equals(session.getUri())) {
            return controller.serveCheckTicketRequest(session);
        }

        return NanoHTTPD.newFixedLengthResponse(NOT_FOUND, "text/plain", "Not Found");
    }
}

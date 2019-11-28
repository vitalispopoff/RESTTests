package programator.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import programator.betPool.TicketPool;
import programator.betPool.Poolable;
import programator.types.Ticket;

import static fi.iki.elonen.NanoHTTPD.*;
import static fi.iki.elonen.NanoHTTPD.Response.Status.INTERNAL_ERROR;
import static fi.iki.elonen.NanoHTTPD.Response.Status.OK;

public class Controller {

    private Poolable ticketPool = new TicketPool();

    public Response serveAddTicketRequest(IHTTPSession session) {

        ObjectMapper objectMapper = new ObjectMapper();
        long theTicketId = System.currentTimeMillis();

        String lengthHeader = session.getHeaders().get("content-length");
        int contentLength = Integer.parseInt(lengthHeader);
        byte[] buffer = new byte[contentLength];

        try {
            session.getInputStream().read(buffer, 0, contentLength);
            String requestBody = new String(buffer).trim();
            Ticket requestTicket = objectMapper.readValue(requestBody, Ticket.class);
            requestTicket.setTicketId(theTicketId);

            ticketPool.addTicket(requestTicket, requestTicket.getBettor(), requestTicket.getAgent());
        } catch (Exception e) {
            System.err.println("Error in the request process:\n" + e);
            return newFixedLengthResponse(
                    INTERNAL_ERROR,
                    "text/plain",
                    "Internal error, the ticket is NOT in the pool.");
        }

        return newFixedLengthResponse(
                OK,
                "text/plain",
                "Congrats, Your ticket is in the pool now. \nIt's id: " + theTicketId);

    }

    public Response serveGetAllTicketsRequest(IHTTPSession session) {

        ObjectMapper objectMapper = new ObjectMapper();
        String response = "";

        try {
            response = objectMapper.writeValueAsString(ticketPool.getAllTickets());
        } catch (JsonProcessingException e) {
            System.err.println("Error in the request process:\n" + e);
            return newFixedLengthResponse(
                    INTERNAL_ERROR,
                    "text/plain",
                    "Internal error, can't read all the books, not at once"
            );
        }

        return newFixedLengthResponse(
                OK,
                "application/json",
                response
        );
    }

    public Response serveGetTicketRequest(IHTTPSession session) {
        return null;
    }

    public Response serveCheckTicketRequest(IHTTPSession session) {
        return null;
    }
}

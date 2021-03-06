package programator.control;

import static fi.iki.elonen.*;
import static programator.Recoverable.serializeIt;
import java.io.Serializable;
import java.util.*;

import com.fasterxml.jackson.*;
import programator.betPool.TicketPool;
import programator.types.Ticket;

public class Controller implements Serializable {

    private final static String TICKET_ID_PARAMETER_NAME = "ticketId";
    private TicketPool ticketPool = new TicketPool();

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public void setTicketPool(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

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
            serializeIt();
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
                    "Internal error, can't read all the tickets, not at once");
        }
        return newFixedLengthResponse(
                OK,
                "application/json",
                response);
    }

    public Response serveGetTicketRequest(IHTTPSession session) {
        Map<String, List<String>> requestParameters = session.getParameters();

        if (requestParameters.containsKey(TICKET_ID_PARAMETER_NAME)) {
            List<String> ticketIdParameters = requestParameters.get(TICKET_ID_PARAMETER_NAME);
            String ticketIdParameter = ticketIdParameters.get(0);
            long ticketId = 0;

            try {
                ticketId = Long.parseLong(ticketIdParameter);
            } catch (NumberFormatException nfe) {
                return newFixedLengthResponse(
                        BAD_REQUEST,
                        "text/plain",
                        "Request parameter 'ticketId' gotta be a number");
            }
            Ticket ticket = ticketPool.getTicket(ticketId);
            if (ticket != null) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String response = objectMapper.writeValueAsString(ticket);
                    return newFixedLengthResponse(
                            OK,
                            "application/json",
                            response);
                } catch (JsonProcessingException e) {
                    System.err.println("Error in request process:\n" + e);
                    return newFixedLengthResponse(
                            INTERNAL_ERROR,
                            "text/plain",
                            "Internal error: can't get all the tickets");
                }
            }
            return newFixedLengthResponse(NOT_FOUND, "application/json", "¯\\_(ツ)_/¯");
        }
        return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Uncorrected request params");
    }

    public Response serveCheckTicketRequest(IHTTPSession session) {
        Map<String, List<String>> requestParameters = session.getParameters();

        if (requestParameters.containsKey(TICKET_ID_PARAMETER_NAME)) {
            List<String> ticketIdParameters = requestParameters.get(TICKET_ID_PARAMETER_NAME);          // ? wartość ticketId zapamiętana jako element listy będącej elementem mapy requestParameters (?)
            String ticketIdParameter = ticketIdParameters.get(0);
            long ticketId = 0;

            try {
                ticketId = Long.parseLong(ticketIdParameter);
            } catch (NumberFormatException nfe) {
                return newFixedLengthResponse(
                        BAD_REQUEST,
                        "text/plain",
                        "Request parameter 'ticketId' gotta be a number");
            }
            Ticket ticket = ticketPool.getTicket(ticketId);
            if (ticket != null) {
                try {
                    ticketPool.checkTicket(ticketId);
                    serializeIt();
                    return newFixedLengthResponse(
                            OK,
                            "application/json",
                            "Ticket " + ticketId + " has been checked. You have successfully failed.");
                } catch (Exception e) {
                    System.err.println("Error in request process:\n" + e);
                    return newFixedLengthResponse(
                            INTERNAL_ERROR,
                            "text/plain",
                            "I'm sorry Dave, I'm afraid i can't do that. \n¯\\_(ツ)_/¯");
                }
            }
            return newFixedLengthResponse(NOT_FOUND, "application/json", "Ticket " + ticketId + " not found. ¯\\_(ツ)_/¯");
        }
        return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Uncorrected request params");
    }

    public Response serveRemoveTicketRequest(IHTTPSession session) {
        long ticketId = 0;
        Map<String, List<String>> requestParameters = session.getParameters();

        if (requestParameters.containsKey(TICKET_ID_PARAMETER_NAME)) {
            List<String> ticketIdParameters = requestParameters.get(TICKET_ID_PARAMETER_NAME);
            String ticketIdParameter = ticketIdParameters.get(0);
            
            try {
                ticketId = Long.parseLong(ticketIdParameter);
            } catch (NumberFormatException nfe) {
                return newFixedLengthResponse(
                        BAD_REQUEST,
                        "text/plain",
                        "Request parameter 'ticketId' gotta be a number");
            }
            ticketPool.removeTicket(ticketId);
            serializeIt();
        }
        return newFixedLengthResponse(OK, "application/json", "Ticket " + ticketId + "has been removed.");
    }
}

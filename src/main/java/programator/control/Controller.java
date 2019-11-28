package programator.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import programator.betPool.TicketPool;
import programator.types.Ticket;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.*;
import static fi.iki.elonen.NanoHTTPD.Response.Status.*;
import static programator.Recoverable.serializeIt;

public class Controller implements Serializable {

    private final static String TICKET_ID_PARAMETER_NAME = "ticketId";
    private /*static*/ TicketPool ticketPool = new TicketPool();

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

            serializeIt();    //TODO

           /* try {
                FileOutputStream fos = new FileOutputStream("LotteryTicketPool.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(ticketPool.getAllTickets());
                oos.close();
                fos.close();

            } catch (Exception e) {
                System.out.println("Internal Error in serialization process. Please come back later.");
            }*/     // inner serialization - we no likes it

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
                    "Internal error, can't read all the tickets, not at once"
            );
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

//                    serializeIt();    //TODO

                    return newFixedLengthResponse(
                            OK,
                            "application/json",
                            response);

                } catch (JsonProcessingException e) {
                    System.err.println("Error in request process:\n" + e);
                    return newFixedLengthResponse(
                            INTERNAL_ERROR,
                            "text/plain",
                            "Internal error: can't get all the tickets"
                    );
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
                        "Request parameter 'ticketId' gotta be a number"
                );
            }

            Ticket ticket = ticketPool.getTicket(ticketId);
            if (ticket != null) {
                try {
                    ticketPool.checkTicket(ticketId);
                    return newFixedLengthResponse(
                            OK,
                            "application/json",
                            "Ticket " + ticketId + " has been checked. You have successfully failed.");

                } catch (Exception e) {
                    System.err.println("Error in request process:\n" + e);
                    return newFixedLengthResponse(
                            INTERNAL_ERROR,
                            "text/plain",
                            "I'm sorry Dave, I'm afraid i can't do that. \n¯\\_(ツ)_/¯"
                    );
                }
            }
            return newFixedLengthResponse(NOT_FOUND, "application/json", "Ticket " + ticketId + " not found. ¯\\_(ツ)_/¯");
        }
        return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Uncorrected request params");
    }

/*    public Response serveRemoveTicketRequest(IHTTPSession session) {

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

            TicketPool.removeTicket()
            try
        }

    }*/

/*    public static List<Ticket> deserializeLotteryTicketPool() {
        List<Ticket> deserializeLotteryTicketPool = null;
        System.out.println("Please wait, entering backup.");
        try {

            FileInputStream fis = new FileInputStream("LotteryTicketPool.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            deserializeLotteryTicketPool = (List<Ticket>) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error, backup not found: \n" + e);
        } catch (IOException e) {
            System.err.println("IO IO! It's da sound of da police! \n" + e);
        } catch (ClassNotFoundException e) {
            System.err.println("No idea what that even means... \n" + e);
        }

        return deserializeLotteryTicketPool;
    }*/     // deserialization we no know

}


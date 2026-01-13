package servlets;

import java.io.IOException;
import java.io.OutputStream;
import server.RequestParser.RequestInfo;

/**
 * Servlet defines a handler for a specific HTTP command + URI.
 * Implementations extract information from RequestInfo, trigger logic in the module layer,
 * and write the response to the client OutputStream.
 */


public interface Servlet {
    void handle(RequestInfo ri, OutputStream toClient) throws IOException;
    void close() throws IOException;
}

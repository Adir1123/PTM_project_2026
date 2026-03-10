package server;

import servlets.Servlet;

/**
 * HTTPServer is a generic HTTP server interface.
 * Allows registering/removing servlets per (httpCommand, uri) pair,
 * and runs its main accept-loop on a separate thread (Runnable).
 */
public interface HTTPServer extends Runnable {
    void addServlet(String httpCommanmd, String uri, Servlet s);
    void removeServlet(String httpCommanmd, String uri);
    void start();
    void close();
}

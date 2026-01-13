package server;

import servlets.Servlet;

/**
 * HTTPServer is a generic HTTP server interface.
 * It allows registering/removing servlets per (httpCommand, uri) pair,
 * and runs its main accept-loop on a separate thread (Runnable).
 */


public interface HTTPServer extends Runnable{
    public void addServlet(String httpCommanmd, String uri, Servlet s);
    public void removeServlet(String httpCommanmd, String uri);
    public void start();
    public void close();
}


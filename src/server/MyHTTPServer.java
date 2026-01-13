package server;

import server.RequestParser.RequestInfo;
import servlets.Servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * MyHTTPServer is a simple HTTP server implementation for the PTM project.
 * The server runs in its own thread and dispatches client requests to
 * registered Servlets using longest URI prefix matching.
 *
 * Client handling is performed using a fixed-size worker thread pool,
 * according to the project requirements.
 *
 * Developed with the assistance of AI tools and reviewed, tested,
 * and adapted by me.
 */



public class MyHTTPServer extends Thread implements HTTPServer {

    private final int port;
    private final int nThreads;

    // key: "GET /publish" , "GET /app/"
    private final Map<String, Servlet> servletMap = new HashMap<>();

    private volatile boolean stop = false;
    private ServerSocket serverSocket;
    private ExecutorService pool;

    public MyHTTPServer(int port, int nThreads) {
        this.port = port;
        this.nThreads = nThreads;
    }

    public void addServlet(String httpCommanmd, String uri, Servlet s) {
        if (httpCommanmd == null || uri == null || s == null) return;
        servletMap.put(makeKey(httpCommanmd, uri), s);
    }

    public void removeServlet(String httpCommanmd, String uri) {
        if (httpCommanmd == null || uri == null) return;
        servletMap.remove(makeKey(httpCommanmd, uri));
    }

    @Override
    public void run() {
        stop = false;
        pool = Executors.newFixedThreadPool(nThreads);

        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(1000);

            while (!stop) {
                try {
                    Socket client = serverSocket.accept(); // עד 1 שניה
                    pool.execute(() -> handleClient(client));
                } catch (SocketTimeoutException ignored) {
                }
            }
        } catch (IOException ignored) {

        }
    }

    private void handleClient(Socket client) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            OutputStream out = client.getOutputStream();

            RequestInfo ri = RequestParser.parseRequest(reader);

            String path = pathOnly(ri.getUri());
            Servlet s = findBestServlet(ri.getHttpCommand(), path);

            if (s != null) {
                s.handle(ri, out);
                out.flush();
            }

        } catch (Exception ignored) {
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
    }

   
    private Servlet findBestServlet(String httpCommand, String path) {
        if (httpCommand == null) return null;
        if (path == null) path = "";

        String cmd = httpCommand.trim().toUpperCase();

        Servlet best = null;
        int bestLen = -1;

        for (Map.Entry<String, Servlet> entry : servletMap.entrySet()) {
            String key = entry.getKey();

            int sp = key.indexOf(' ');
            if (sp < 0) continue;

            String keyCmd = key.substring(0, sp).trim();
            String prefix = key.substring(sp + 1).trim();

            if (!cmd.equals(keyCmd)) continue;

            if (path.startsWith(prefix) && prefix.length() > bestLen) {
                bestLen = prefix.length();
                best = entry.getValue();
            }
        }

        return best;
    }

    private String makeKey(String httpCommand, String uri) {
        return httpCommand.trim().toUpperCase() + " " + uri.trim();
    }

    private String pathOnly(String uri) {
        if (uri == null) return "";
        int q = uri.indexOf('?');
        if (q >= 0) return uri.substring(0, q);
        return uri;
    }

    public void close() {
        stop = true;

        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException ignored) {}

        if (pool != null) pool.shutdownNow();

        for (Servlet s : servletMap.values()) {
            try { s.close(); } catch (Exception ignored) {}
        }
    }
}

package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * RequestParser parses a simplified HTTP request from a BufferedReader
 * and produces a RequestInfo object according to the project specification.
 */
public class RequestParser {

    public static RequestInfo parseRequest(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line == null || line.trim().isEmpty()) {
            return new RequestInfo("", "", new String[0], new HashMap<>(), new byte[0]);
        }

        String[] first = line.trim().split("\\s+");
        String httpCommand = first.length > 0 ? first[0].trim() : "";
        String uri = first.length > 1 ? first[1].trim() : "";

        Map<String, String> params = new HashMap<>();

        String path = uri;
        int q = uri.indexOf('?');
        if (q >= 0) {
            path = uri.substring(0, q);
            addQueryParams(uri.substring(q + 1), params);
        }

        String[] uriSegments = splitSegmentsNormal(path);

        // Skip headers until empty line
        while (true) {
            line = reader.readLine();
            if (line == null || line.isEmpty()) break;
        }

        // Optional key=value lines until empty line
        while (reader.ready()) {
            line = reader.readLine();
            if (line == null || line.isEmpty()) break;

            int eq = line.indexOf('=');
            if (eq > 0) {
                String k = line.substring(0, eq).trim();
                String v = line.substring(eq + 1).trim();
                if (!k.isEmpty() && !params.containsKey(k)) {
                    params.put(k, v);
                }
            }
        }

        byte[] content = readContentLines(reader);

        return new RequestInfo(httpCommand, uri, uriSegments, params, content);
    }

    private static void addQueryParams(String query, Map<String, String> params) {
        if (query == null || query.isEmpty()) return;

        String[] pairs = query.split("&");
        for (String p : pairs) {
            if (p.isEmpty()) continue;

            int eq = p.indexOf('=');
            if (eq < 0) {
                params.put(p.trim(), "");
            } else {
                String k = p.substring(0, eq).trim();
                String v = p.substring(eq + 1).trim();
                if (!k.isEmpty()) params.put(k, v);
            }
        }
    }

    private static String[] splitSegmentsNormal(String path) {
        if (path == null) return new String[0];

        while (path.startsWith("/")) path = path.substring(1);
        if (path.isEmpty()) return new String[0];

        return Arrays.stream(path.split("/"))
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    private static byte[] readContentLines(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();

        while (reader.ready()) {
            String line = reader.readLine();
            if (line == null || line.isEmpty()) break;
            sb.append(line).append('\n');
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public static class RequestInfo {
        private final String httpCommand;
        private final String uri;
        private final String[] uriSegments;
        private final Map<String, String> parameters;
        private final byte[] content;

        public RequestInfo(String httpCommand, String uri, String[] uriSegments, Map<String, String> parameters, byte[] content) {
            this.httpCommand = httpCommand;
            this.uri = uri;
            this.uriSegments = uriSegments;
            this.parameters = parameters;
            this.content = content;
        }

        public String getHttpCommand() { return httpCommand; }
        public String getUri() { return uri; }
        public String[] getUriSegments() { return uriSegments; }
        public Map<String, String> getParameters() { return parameters; }
        public byte[] getContent() { return content; }
    }
}

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleWebServer {
    private static final AtomicLong rootRequests = new AtomicLong(0);
    private static final AtomicLong helloRequests = new AtomicLong(0);

    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("Server started on port " + port);
        
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                rootRequests.incrementAndGet();
                String response = "<html><body><h1>Hello from Java inside Docker!</h1><p>Time: " + new java.util.Date() + "</p></body></html>";
                exchange.getResponseHeaders().set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        server.createContext("/api/hello", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                helloRequests.incrementAndGet();
                // Add CORS headers
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, OPTIONS");
                exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type,Authorization");

                if ("OPTIONS".equals(exchange.getRequestMethod())) {
                    exchange.sendResponseHeaders(204, -1);
                    return;
                }

                String response = "{\"message\": \"Hello from Java API!\", \"time\": \"" + new java.util.Date() + "\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        });

        server.createContext("/metrics", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                StringBuilder response = new StringBuilder();
                response.append("# HELP api_requests_total Total number of API requests.\n");
                response.append("# TYPE api_requests_total counter\n");
                response.append("api_requests_total{endpoint=\"/\"} ").append(rootRequests.get()).append("\n");
                response.append("api_requests_total{endpoint=\"/api/hello\"} ").append(helloRequests.get()).append("\n");

                exchange.getResponseHeaders().set("Content-Type", "text/plain; version=0.0.4");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.toString().getBytes());
                os.close();
            }
        });
        
        server.setExecutor(null);
        server.start();
    }
}

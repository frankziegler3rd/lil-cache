/**
 * http handler (controller) for the cache
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package cache;

import cache.Cache;
import cache.InMemCache;
import cache.LRUPolicy;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class CacheController implements HttpHandler {

    private Cache cache;

    public CacheController() {
        cache = new InMemCache(new LRUPolicy());
    }

    public void handleGet(HttpExchange exchange) {}

    public void handlePut(HttpExchange exchange) {}

    public void handleDelete(HttpExchange exchange) {}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch(method) {
            case "GET":
                break;
            case "PUT":
                break;
            case "DELETE":
                break;
            default:
                break;
        }
    }
}

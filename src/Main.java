/**
 * cache Main / entry point
 *
 * @author Frank Ziegler
 * @version 1.0.0
 */

package cache;

import cache.CacheController;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class Main {
    
    /**
     * create an http server
     * http context maps controller to /cache endpoint
     * requests are sent to 0.0.0.0:8080/cache and handled by controller
     */
    public static void main(String[] args) {
        HttpServer server = HttpServer.create(
                new InetSocketAddress("0.0.0.0", 8080),
                0);
        server.createContext("/cache", new CacheController());
        server.start();
    }
}

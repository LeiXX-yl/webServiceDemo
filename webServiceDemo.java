

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class webServiceDemo {
    public static void main(String[] arg) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 10);
        server.createContext("/add",new addHandler());
        server.createContext("/mult", new multHandler());
        server.start();
    }

    static  class addHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
                exchange.sendResponseHeaders(200, 0);
                OutputStream os = exchange.getResponseBody();
            try {
                float[] results = getNum(exchange.getRequestURI());
                String response = ""+(results[0]+results[1]);
                os.write(response.getBytes());

            }catch (Exception e){
                String response = "URL有误";
                os.write(response.getBytes());
            }finally {
                os.close();
            }
        }
    }

    static  class multHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.sendResponseHeaders(200, 0);
            OutputStream os = exchange.getResponseBody();
            try {
                float[] results = getNum(exchange.getRequestURI());
                String response = ""+(results[0]*results[1]);
                os.write(response.getBytes());

            }catch (Exception e){
                String response = "URL有误";
                os.write(response.getBytes());
            }finally {
                os.close();
            }
        }
    }
    /**
     * 拆分URI
     * @param uri
     * @return float[]
     */
    public static float[] getNum(URI uri){
        float[] results = {0,0};
        String s = uri.getQuery();
        String[] array = s.split("&");
        String[] after1 = array[0].split("=");
        String[] after2 = array[1].split("=");
        String a = "";
        String b = "";
        for (int i = 1;i<after1.length;i++) {
            a = a+after1[i];
        }
        for (int i = 1;i<after2.length;i++) {
            b = b+after2[i];
        }
        results[0] = Float.parseFloat(a);
        results[1] = Float.parseFloat(b);
        return results;
    }
}

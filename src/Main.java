import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import db.DB;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class Main {
    public static void main(String[] args) throws IOException {



        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null);
        server.start();

    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {



            Connection conn = null;
            Statement st = null;
            ResultSet rs = null;
            try {
                conn = DB.getConnection();
                st = conn.createStatement();

                rs = st.executeQuery("select * from produto");

                while (rs.next()){
                    System.out.println(rs.getInt("Id") + ", " + rs.getString("nome"));

                    //String response = "This is the response";
                    t.sendResponseHeaders(200, Long.parseLong(rs.getString("nome")));
                    //t.sendResponseHeaders(200, response.length());
                    OutputStream os = t.getResponseBody();
                    os.write((int) Long.parseLong(rs.getString("nome")));
                    //os.write(response.getBytes());
                    os.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }


            /*String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();*/
        }
    }


    /*public static void executeQuery()  {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = DB.getConnection();
            st = conn.createStatement();

            rs = st.executeQuery("select * from produto");

            while (rs.next()){
                 System.out.println(rs.getInt("Id") + ", " + rs.getString("nome"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
            DB.closeConnection();
        }
*/
    //}

}

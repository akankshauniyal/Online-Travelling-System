package online.travelling.system;
import java.sql.*;

public class Conn implements AutoCloseable {

    Connection c;
    Statement s;

    Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinetravellingsystem", "root", "mayakanksha130502");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws SQLException {
        if (s != null) {
            s.close();
        }
        if (c != null) {
            c.close();
        }
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return c.prepareStatement(query);
    }
}

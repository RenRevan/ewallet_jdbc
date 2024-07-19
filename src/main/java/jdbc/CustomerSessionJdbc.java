package jdbc;

import entities.CustomerSession;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerSessionJdbc {
    private final static String INSERT = "INSERT INTO ewalletdb.customer_session (session_datetime, browser, customer_id) VALUES (?, ?, ?);";
    private final static String UPDATE = "UPDATE ewalletdb.customer_session set session_datetime=?, browser=?, customer_id=? where id=?";
    private final static String SELECT_BY_ID = "SELECT * FROM ewalletdb.customer_session where id=?";
    private final static String SELECT_ALL = "SELECT * FROM ewalletdb.customer_session";

    public static void insertCustomerSession(CustomerSession customerSession) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, customerSession.getSessionDatetime());
        args.put(2, customerSession.getBrowser());
        args.put(3, customerSession.getCustomer().getId());
        Integer id = ConnectionStatement.insert(connection, INSERT, args);
        System.out.println("New id CustomerSession " + id);
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static void updateCustomerSession(CustomerSession customerSession) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, customerSession.getSessionDatetime());
        args.put(2, customerSession.getBrowser());
        args.put(3, customerSession.getCustomer().getId());
        args.put(4, customerSession.getId());
        ConnectionStatement.update(connection, UPDATE, args);
        System.out.println("Updated CustomerSession with id=" + customerSession.getId());
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static CustomerSession selectCustomerSessionById(Integer id) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, id);
        CustomerSession customerSession = (CustomerSession) ConnectionStatement.select(connection, SELECT_BY_ID, args, CustomerSession.class).getFirst();
        System.out.println("Select CustomerSession with id=" + id);
        ConnectionStatement.closeConnection(connection);
        return customerSession;
    }

    public static List<CustomerSession> selectAllCustomerSession() {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        List<CustomerSession> customerSessions = (List<CustomerSession>) ConnectionStatement.select(connection, SELECT_ALL, args, CustomerSession.class);
        ConnectionStatement.closeConnection(connection);
        return customerSessions;
    }
}

package jdbc;

import entities.Currency;
import entities.Customer;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerJdbs {
    private final static String INSERT = "INSERT INTO ewalletdb.customer (mobile_number, email, first_name, last_name, password, customer_role_id) VALUES (?,?,?,?,?,?);";
    private final static String UPDATE = "UPDATE ewalletdb.customer set mobile_number=?, email=?, first_name=?, last_name=?, password=?, customer_role_id=? where id=?";
    private final static String SELECT_BY_ID = "SELECT * FROM ewalletdb.customer where id=?";
    private final static String SELECT_ALL = "SELECT * FROM ewalletdb.customer";

    public static void insertCustomer(Customer customer) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, customer.getMobileNumber());
        args.put(2, customer.getEmail());
        args.put(3, customer.getFirstName());
        args.put(4, customer.getLastName());
        args.put(5, customer.getPassword());
        args.put(6, customer.getCustomerRole().getId());
        Integer id = ConnectionStatement.insert(connection, INSERT, args);
        System.out.println("New id Customer " + id);
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static void updateCustomer(Customer customer) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, customer.getMobileNumber());
        args.put(2, customer.getEmail());
        args.put(3, customer.getFirstName());
        args.put(4, customer.getLastName());
        args.put(5, customer.getPassword());
        args.put(6, customer.getCustomerRole().getId());
        args.put(7, customer.getId());
        ConnectionStatement.update(connection, UPDATE, args);
        System.out.println("Updated customer with id=" + customer.getId());
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static Customer selectCustomerById(Integer id) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, id);
        Customer customer = (Customer) ConnectionStatement.select(connection, SELECT_BY_ID, args, Customer.class).getFirst();
        System.out.println("Select Customer with id=" + id);
        ConnectionStatement.closeConnection(connection);
        return customer;
    }

    public static List<Customer> selectAllCustomer() {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        List<Customer> customers = (List<Customer>) ConnectionStatement.select(connection, SELECT_ALL, args, Customer.class);
        ConnectionStatement.closeConnection(connection);
        return customers;
    }
}

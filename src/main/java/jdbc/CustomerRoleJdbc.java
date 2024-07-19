package jdbc;

import entities.Customer;
import entities.CustomerRole;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRoleJdbc {
    private final static String INSERT = "INSERT INTO ewalletdb.customer_role (role) VALUES (?);";
    private final static String UPDATE = "UPDATE ewalletdb.customer_role set role=? where id=?";
    private final static String SELECT_BY_ID = "SELECT * FROM ewalletdb.customer_role where id=?";
    private final static String SELECT_ALL = "SELECT * FROM ewalletdb.customer_role";

    public static void insertCustomerRole(CustomerRole customerRole) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, customerRole.getRole());
        Integer id = ConnectionStatement.insert(connection, INSERT, args);
        System.out.println("New id CustomerRole " + id);
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static void updateCustomerRole(CustomerRole customerRole) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, customerRole.getRole());
        args.put(2, customerRole.getId());
        ConnectionStatement.update(connection, UPDATE, args);
        System.out.println("Updated CustomerRole with id=" + customerRole.getId());
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static CustomerRole selectCustomerRoleById(Integer id) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, id);
        CustomerRole customerRole = (CustomerRole) ConnectionStatement.select(connection, SELECT_BY_ID, args, CustomerRole.class).getFirst();
        System.out.println("Select CustomerRole with id=" + id);
        ConnectionStatement.closeConnection(connection);
        return customerRole;
    }

    public static List<CustomerRole> selectAllCustomerRole() {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        List<CustomerRole> customerRoles = (List<CustomerRole>) ConnectionStatement.select(connection, SELECT_ALL, args, CustomerRole.class);
        ConnectionStatement.closeConnection(connection);
        return customerRoles;
    }
}

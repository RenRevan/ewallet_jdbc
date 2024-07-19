package jdbc;

import entities.CustomerSession;
import entities.OperationCode;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationCodeJdbc {
    private final static String INSERT = "INSERT INTO ewalletdb.operation_code (code) VALUES (?);";
    private final static String UPDATE = "UPDATE ewalletdb.operation_code set code=? where id=?";
    private final static String SELECT_BY_ID = "SELECT * FROM ewalletdb.operation_code where id=?";
    private final static String SELECT_ALL = "SELECT * FROM ewalletdb.operation_code";

    public static void insertOperationCode(OperationCode operationCode) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, operationCode.getCode());
        Integer id = ConnectionStatement.insert(connection, INSERT, args);
        System.out.println("New id OperationCode " + id);
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static void updateOperationCode(OperationCode operationCode) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, operationCode.getCode());
        args.put(2, operationCode.getId());
        ConnectionStatement.update(connection, UPDATE, args);
        System.out.println("Updated OperationCode with id=" + operationCode.getId());
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static OperationCode selectOperationCodeById(Integer id) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, id);
        OperationCode operationCode = (OperationCode) ConnectionStatement.select(connection, SELECT_BY_ID, args, OperationCode.class).getFirst();
        System.out.println("Select OperationCode with id=" + id);
        ConnectionStatement.closeConnection(connection);
        return operationCode;
    }

    public static List<OperationCode> selectAllOperationCode() {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        List<OperationCode> operationCodes = (List<OperationCode>) ConnectionStatement.select(connection, SELECT_ALL, args, OperationCode.class);
        ConnectionStatement.closeConnection(connection);
        return operationCodes;
    }
}

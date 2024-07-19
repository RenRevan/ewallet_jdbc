package jdbc;

import entities.OperationCode;
import entities.Transaction;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionJdbc {
    private final static String INSERT = "INSERT INTO ewalletdb.transaction (amount, date, description, iban_from, iban_to, account_id, operation_code_id) VALUES (?,?,?,?,?,?,?);";
    private final static String UPDATE = "UPDATE ewalletdb.transaction set amount=?, date=?, description=?, iban_from=?, iban_to=?, account_id=?, operation_code_id=? where id=?";
    private final static String SELECT_BY_ID = "SELECT * FROM ewalletdb.transaction where id=?";
    private final static String SELECT_ALL = "SELECT * FROM ewalletdb.transaction";

    public static void insertTransaction(Transaction transaction) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, transaction.getAmount());
        args.put(2, transaction.getDate());
        args.put(3, transaction.getDescription());
        args.put(4, transaction.getIbanFrom());
        args.put(5, transaction.getIbanTo());
        args.put(6, transaction.getAccount().getId());
        args.put(7, transaction.getOperationCode().getId());
        Integer id = ConnectionStatement.insert(connection, INSERT, args);
        System.out.println("New id Transaction " + id);
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static void updateTransaction(Transaction transaction) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, transaction.getAmount());
        args.put(2, transaction.getDate());
        args.put(3, transaction.getDescription());
        args.put(4, transaction.getIbanFrom());
        args.put(5, transaction.getIbanTo());
        args.put(6, transaction.getAccount().getId());
        args.put(7, transaction.getOperationCode().getId());
        args.put(8, transaction.getId());
        ConnectionStatement.update(connection, UPDATE, args);
        System.out.println("Updated Transaction with id=" + transaction.getId());
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static Transaction selectTransactionById(Integer id) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, id);
        Transaction transaction = (Transaction) ConnectionStatement.select(connection, SELECT_BY_ID, args, Transaction.class).getFirst();
        System.out.println("Select Transaction with id=" + id);
        ConnectionStatement.closeConnection(connection);
        return transaction;
    }

    public static List<Transaction> selectAllTransaction() {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        List<Transaction> transactions = (List<Transaction>) ConnectionStatement.select(connection, SELECT_ALL, args, Transaction.class);
        ConnectionStatement.closeConnection(connection);
        return transactions;
    }
}

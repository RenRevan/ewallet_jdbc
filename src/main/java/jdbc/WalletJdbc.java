package jdbc;

import entities.Transaction;
import entities.Wallet;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletJdbc {
    private final static String INSERT = "INSERT INTO ewalletdb.wallet (customer_id) VALUES (?);";
    private final static String UPDATE = "UPDATE ewalletdb.wallet set customer_id=? where id=?";
    private final static String SELECT_BY_ID = "SELECT * FROM ewalletdb.wallet where id=?";
    private final static String SELECT_ALL = "SELECT * FROM ewalletdb.wallet";

    public static void insertWallet(Wallet wallet) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, wallet.getCustomer().getId());
        Integer id = ConnectionStatement.insert(connection, INSERT, args);
        System.out.println("New id Wallet " + id);
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static void updateWallet(Wallet wallet) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, wallet.getCustomer().getId());
        args.put(2, wallet.getId());
        ConnectionStatement.update(connection, UPDATE, args);
        System.out.println("Updated Wallet with id=" + wallet.getId());
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static Wallet selectWalletById(Integer id) {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        args.put(1, id);
        Wallet wallet = (Wallet) ConnectionStatement.select(connection, SELECT_BY_ID, args, Wallet.class).getFirst();
        System.out.println("Select Transaction with id=" + id);
        ConnectionStatement.closeConnection(connection);
        return wallet;
    }

    public static List<Wallet> selectAllTransaction() {
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer, Object> args = new HashMap<>();
        List<Wallet> wallets = (List<Wallet>) ConnectionStatement.select(connection, SELECT_ALL, args, Wallet.class);
        ConnectionStatement.closeConnection(connection);
        return wallets;
    }
}

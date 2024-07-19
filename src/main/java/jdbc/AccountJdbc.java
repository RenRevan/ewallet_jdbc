package jdbc;

import entities.Account;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountJdbc {

    private final static String INSERT = "INSERT INTO ewalletdb.account (iban, balance, wallet_id, bank_id, currency_id) VALUES (?, ?, ?, ?, ?);";
    private final static String UPDATE = "UPDATE ewalletdb.account set iban=?, balance=?, wallet_id=?, bank_id=?, currency_id=? where id=?";
    private final static String SELECT_BY_ID = "SELECT * FROM ewalletdb.account where id=?";
    private final static String SELECT_ALL = "SELECT * FROM ewalletdb.account";

    public static Integer insertAccount(Account account){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        args.put(1,account.getIban());
        args.put(2,account.getBalance());
        args.put(3,account.getWallet().getId());
        args.put(4,account.getBank().getId());
        args.put(5,account.getCurrency().getId());
        Integer id = ConnectionStatement.insert(connection,INSERT, args);
        System.out.println("New id account " + id);
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
        return id;
    }

    public static void updateAccount(Account account){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        args.put(1,account.getIban());
        args.put(2,account.getBalance());
        args.put(3,account.getWallet().getId());
        args.put(4,account.getBank().getId());
        args.put(5,account.getCurrency().getId());
        args.put(6,account.getId());
        ConnectionStatement.update(connection,UPDATE, args);
        System.out.println("Updated account with id=" + account.getId());
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static Account selectAccountById(Integer id){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        args.put(1,id);
        Account account = (Account) ConnectionStatement.select(connection,SELECT_BY_ID, args, Account.class).getFirst();
        System.out.println("Select account with id=" + id);
        ConnectionStatement.closeConnection(connection);
        return account;
    }

    public static List<Account> selectAllAccounts(){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        List<Account> account = (List<Account>) ConnectionStatement.select(connection,SELECT_ALL, args, Account.class);
        ConnectionStatement.closeConnection(connection);
        return account;
    }

}

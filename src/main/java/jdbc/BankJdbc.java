package jdbc;

import entities.Account;
import entities.Bank;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankJdbc {
    private final static String INSERT = "INSERT INTO ewalletdb.bank (name, mfo, address) VALUES (?, ?, ?);";
    private final static String UPDATE = "UPDATE ewalletdb.bank set name=?, mfo=?, address=? where id=?";
    private final static String SELECT_BY_ID = "SELECT * FROM ewalletdb.bank where id=?";
    private final static String SELECT_ALL = "SELECT * FROM ewalletdb.bank";

    public static void insertBank(Bank bank){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        args.put(1,bank.getName());
        args.put(2,bank.getMfo());
        args.put(3,bank.getAddress());
        Integer id = ConnectionStatement.insert(connection,INSERT, args);
        System.out.println("New id bank " + id);
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static void updateBank(Bank bank){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        args.put(1,bank.getName());
        args.put(2,bank.getMfo());
        args.put(3,bank.getAddress());
        args.put(4,bank.getId());
        ConnectionStatement.update(connection,UPDATE, args);
        System.out.println("Updated account with id=" + bank.getId());
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static Bank selectBankById(Integer id){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        args.put(1,id);
        Bank bank = (Bank) ConnectionStatement.select(connection,SELECT_BY_ID, args, Bank.class).getFirst();
        System.out.println("Select account with id=" + id);
        ConnectionStatement.closeConnection(connection);
        return bank;
    }

    public static List<Bank> selectAllBank(){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        List<Bank> banks = (List<Bank>) ConnectionStatement.select(connection,SELECT_ALL, args, Bank.class);
        ConnectionStatement.closeConnection(connection);
        return banks;
    }
}

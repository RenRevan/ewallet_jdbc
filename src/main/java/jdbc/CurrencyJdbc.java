package jdbc;

import entities.Beneficiary;
import entities.Currency;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyJdbc {
    private final static String INSERT = "INSERT INTO ewalletdb.currency (code) VALUES (?);";
    private final static String UPDATE = "UPDATE ewalletdb.currency set code=? where id=?";
    private final static String SELECT_BY_ID = "SELECT * FROM ewalletdb.currency where id=?";
    private final static String SELECT_ALL = "SELECT * FROM ewalletdb.currency";

    public static void insertCurrency(Currency currency){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        args.put(1,currency.getCode());
        Integer id = ConnectionStatement.insert(connection,INSERT, args);
        System.out.println("New id Currency " + id);
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static void updateCurrency(Currency currency){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        args.put(1,currency.getCode());
        args.put(2,currency.getId());
        ConnectionStatement.update(connection,UPDATE, args);
        System.out.println("Updated currency with id=" + currency.getId());
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static Currency selectCurrencyById(Integer id){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        args.put(1,id);
        Currency currency = (Currency) ConnectionStatement.select(connection,SELECT_BY_ID, args, Currency.class).getFirst();
        System.out.println("Select Currency with id=" + id);
        ConnectionStatement.closeConnection(connection);
        return currency;
    }

    public static List<Currency> selectAllCurrency(){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        List<Currency> currencies = (List<Currency>) ConnectionStatement.select(connection,SELECT_ALL, args, Currency.class);
        ConnectionStatement.closeConnection(connection);
        return currencies;
    }
}

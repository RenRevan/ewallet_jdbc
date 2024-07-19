package jdbc;

import entities.Bank;
import entities.Beneficiary;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeneficiaryJdbc {
    private final static String INSERT = "INSERT INTO ewalletdb.beneficiary (iban, bank_name, mfo, full_name, wallet_id) VALUES (?, ?, ?, ?, ?);";
    private final static String UPDATE = "UPDATE ewalletdb.beneficiary set iban=?, bank_name=?, mfo=?, full_name=?, wallet_id=? where id=?";
    private final static String SELECT_BY_ID = "SELECT * FROM ewalletdb.beneficiary where id=?";
    private final static String SELECT_ALL = "SELECT * FROM ewalletdb.beneficiary";

    public static void insertBeneficiary(Beneficiary beneficiary){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        args.put(1,beneficiary.getIban());
        args.put(2,beneficiary.getBankName());
        args.put(3,beneficiary.getMfo());
        args.put(4,beneficiary.getFullName());
        args.put(5,beneficiary.getWallet().getId());
        Integer id = ConnectionStatement.insert(connection,INSERT, args);
        System.out.println("New id beneficiary " + id);
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static void updateBeneficiary(Beneficiary beneficiary){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        args.put(1,beneficiary.getIban());
        args.put(2,beneficiary.getBankName());
        args.put(3,beneficiary.getMfo());
        args.put(4,beneficiary.getFullName());
        args.put(5,beneficiary.getWallet().getId());
        args.put(6,beneficiary.getId());
        ConnectionStatement.update(connection,UPDATE, args);
        System.out.println("Updated beneficiary with id=" + beneficiary.getId());
        ConnectionStatement.commit(connection);
        ConnectionStatement.closeConnection(connection);
    }

    public static Beneficiary selectBeneficiaryById(Integer id){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        args.put(1,id);
        Beneficiary beneficiary = (Beneficiary) ConnectionStatement.select(connection,SELECT_BY_ID, args, Beneficiary.class).getFirst();
        System.out.println("Select Beneficiary with id=" + id);
        ConnectionStatement.closeConnection(connection);
        return beneficiary;
    }

    public static List<Beneficiary> selectAllBeneficiary(){
        Connection connection = ConnectionStatement.createConnect();
        Map<Integer,Object> args = new HashMap<>();
        List<Beneficiary> beneficiaries = (List<Beneficiary>) ConnectionStatement.select(connection,SELECT_ALL, args, Beneficiary.class);
        ConnectionStatement.closeConnection(connection);
        return beneficiaries;
    }
}

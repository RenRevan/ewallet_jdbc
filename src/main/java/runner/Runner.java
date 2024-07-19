package runner;

import entities.Account;
import entities.Bank;
import entities.Currency;
import entities.Wallet;
import jdbc.AccountJdbc;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Runner {

    private static final Logger LOGGER = Logger.getLogger(Runner.class.getName());

    public static void main(String[] args) {
        //Examples of usage JDBC methods

        LOGGER.info("Insert new account");
        Account account = Account.builder()
                .iban("UA00111111000000000" + new Random().nextLong(1000000000L, 9999999999L))
                .bank(Bank.builder().id(1).build())
                .balance(new Random().nextInt())
                .wallet(Wallet.builder().id(1).build())
                .currency(Currency.builder().id(1).build())
                .build();
        Integer newAccountId = AccountJdbc.insertAccount(account);

        //select one account
        LOGGER.info("select one new account with id=" + newAccountId);
        Account account1 = AccountJdbc.selectAccountById(newAccountId);
        LOGGER.info(account1.toString());

        //update account
        LOGGER.info("update account");
        account = account.toBuilder().id(2).balance(new Random().nextInt()).build();
        AccountJdbc.updateAccount(account);

        //select all accounts
        LOGGER.info("select all accounts");
        List<Account> accountsAll = AccountJdbc.selectAllAccounts();
        accountsAll.forEach(acc -> LOGGER.info(acc.toString()));

    }
}

import com.my.model.dao.AccountDao;
import com.my.model.dao.ConnectionPool;
import com.my.model.dao.DaoFactory;
import com.my.model.dao.UserDao;
import com.my.model.dao.exceptions.DBException;
import com.my.model.entities.Account;
import com.my.model.entities.User;
import com.my.model.entities.enums.Block;
import com.my.model.entities.enums.Role;
import com.my.model.services.AccountService;
import com.my.model.services.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountServiceTest {
    private Connection connection;
    private AccountService accountService;
    private AccountDao accountDao;


    @Before
    public void before() {
        connection = ConnectionPool.getConnection();
        accountService = new AccountService();
        accountDao = accountService.getAccountDao();
    }

    @After
    public void after() {
        try {
            accountDao.close();
            connection.close();
        } catch (SQLException e) {
            throw new DBException("Cannot close connection", e);
        }
    }
    @Test
    public void addAccount() {
        final Account account = Account.builder()
                .id(320)
                .number("test1")
                .accountName("email1")
                .IBAN("phoneNum")
                .dateOfRegistration(LocalDateTime.now())
                .balanceAmount(1000)
                .isBlocked(Block.ACTIVE)
                .userId(1)
                .build();
        accountService.addAccount(account.getNumber(), account.getAccountName(), account.getIBAN(), account.getUserId());

        Account actual = accountDao.findByNumber(account.getNumber());
        accountDao.delete(actual.getId());

        Assert.assertNotNull(actual);
    }

    @Test
    public void replenish() {
        final Account account = Account.builder()
                .id(320)
                .number("test1")
                .accountName("email1")
                .IBAN("phoneNum")
                .dateOfRegistration(LocalDateTime.now())
                .balanceAmount(0)
                .isBlocked(Block.ACTIVE)
                .userId(1)
                .build();
        accountService.addAccount(account.getNumber(), account.getAccountName(), account.getIBAN(), account.getUserId());

        Account actual = accountDao.findByNumber(account.getNumber());
        double amount = actual.getBalanceAmount();
        accountService.replenishAccount(actual.getId(), 1000);
        amount+=1000;
        actual = accountDao.findByNumber(account.getNumber());


        Assert.assertEquals(amount, actual.getBalanceAmount(), 0.01);

    }
    @Test
    public void blockTest1() {
        final Account account = Account.builder()
                .id(320)
                .number("test1")
                .accountName("email1")
                .IBAN("phoneNum")
                .dateOfRegistration(LocalDateTime.now())
                .balanceAmount(1000)
                .isBlocked(Block.ACTIVE)
                .userId(1)
                .build();
        accountService.addAccount(account.getNumber(), account.getAccountName(), account.getIBAN(), account.getUserId());

        Account actual = accountDao.findByNumber(account.getNumber());
        accountService.block(actual.getId());
        actual = accountDao.findByNumber(account.getNumber());
        accountDao.delete(actual.getId());
        Assert.assertEquals(Block.BLOCKED, actual.getIsBlocked());
    }
    @Test
    public void blockTest2() {
        final Account account = Account.builder()
                .id(320)
                .number("test1")
                .accountName("email1")
                .IBAN("phoneNum")
                .dateOfRegistration(LocalDateTime.now())
                .balanceAmount(1000)
                .isBlocked(Block.ACTIVE)
                .userId(1)
                .build();
        accountService.addAccount(account.getNumber(), account.getAccountName(), account.getIBAN(), account.getUserId());

        Account actual = accountDao.findByNumber(account.getNumber());
        accountService.block(actual.getId());
        accountService.block(actual.getId());
        actual = accountDao.findByNumber(account.getNumber());
        accountDao.delete(actual.getId());
        Assert.assertEquals(Block.APPROVAL, actual.getIsBlocked());
    }
    @Test
    public void blockAdminTest1() {
        final Account account = Account.builder()
                .id(320)
                .number("test1")
                .accountName("email1")
                .IBAN("phoneNum")
                .dateOfRegistration(LocalDateTime.now())
                .balanceAmount(1000)
                .isBlocked(Block.ACTIVE)
                .userId(1)
                .build();
        accountService.addAccount(account.getNumber(), account.getAccountName(), account.getIBAN(), account.getUserId());

        Account actual = accountDao.findByNumber(account.getNumber());
        accountService.blockAdmin(actual.getId());
        actual = accountDao.findByNumber(account.getNumber());
        accountDao.delete(actual.getId());
        Assert.assertEquals(Block.BLOCKED, actual.getIsBlocked());
    }
    @Test
    public void blockAdminTest2() {
        final Account account = Account.builder()
                .id(320)
                .number("test1")
                .accountName("email1")
                .IBAN("phoneNum")
                .dateOfRegistration(LocalDateTime.now())
                .balanceAmount(1000)
                .isBlocked(Block.ACTIVE)
                .userId(1)
                .build();
        accountService.addAccount(account.getNumber(), account.getAccountName(), account.getIBAN(), account.getUserId());

        Account actual = accountDao.findByNumber(account.getNumber());
        accountService.blockAdmin(actual.getId());
        accountService.blockAdmin(actual.getId());
        actual = accountDao.findByNumber(account.getNumber());
        accountDao.delete(actual.getId());
        Assert.assertEquals(Block.ACTIVE, actual.getIsBlocked());
    }
}

import com.my.model.dao.*;
import com.my.model.dao.exceptions.DBException;
import com.my.model.entities.Account;
import com.my.model.entities.CreditCard;
import com.my.model.entities.User;
import com.my.model.services.AccountService;
import com.my.model.services.CreditCardService;
import com.my.model.services.PaymentService;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

public class CreditCardTest {
    private static CreditCardService creditCardService;
    private static CreditCardDao creditCardDao;
    private static Account account;
    private static User user;
    private static UserDao userDao;
    private static AccountDao accountDao;
    @BeforeClass
    public static void init(){

        creditCardService = new CreditCardService();
        creditCardDao = creditCardService.getCreditCardDao();

        userDao = DaoFactory.getInstance().createUserDao();
        accountDao = DaoFactory.getInstance().createAccountDao();

        user = new User("testUser", "testPhone","testEmail",  "testPassword");
        userDao.add(user);
        user = userDao.findByEmail(user.getEmail());
        account = new Account("99999","sender", "99919", 1000, user.getId());
        accountDao.add(account);
        account = accountDao.findByNumber(account.getNumber());
    }
    @AfterClass
    public static void del(){
        userDao.delete(user.getId());
        accountDao.delete(account.getId());

        userDao.close();
        accountDao.close();
        creditCardDao.close();
    }

    @Test
    public void addCreditCard(){
        CreditCard creditCard = new CreditCard("9999", "222",
                "2002-12", account.getId());

        creditCardService.addCreditCard(
                creditCard.getNumber(), creditCard.getCvv(),
                creditCard.getExpireDate(), creditCard.getAccountId());
        CreditCard actual = creditCardDao.findByNumber(creditCard.getNumber());
        creditCardDao.delete(actual.getId());
        Assert.assertNotNull(actual);
    }
    @Test
    public void deleteCreditCard(){
        CreditCard creditCard = new CreditCard("9999", "222",
                "2002-12", account.getId());
        creditCardDao.add(creditCard);
        CreditCard actual = creditCardDao.findByNumber(creditCard.getNumber());
        creditCardService.deleteCreditCard(actual.getId());
        Assert.assertNotNull(actual);
    }
}

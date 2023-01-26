import com.my.model.dao.*;
import com.my.model.dao.exceptions.DBException;
import com.my.model.entities.Account;
import com.my.model.entities.Payment;
import com.my.model.entities.User;
import com.my.model.entities.enums.Block;
import com.my.model.entities.enums.PaymentStatus;
import com.my.model.entities.enums.Role;
import com.my.model.services.AccountService;
import com.my.model.services.PaymentService;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;

public class PaymentServiceTest {
    private Connection connection;
    private static PaymentService paymentService;
    private static PaymentDao paymentDao;

    private static Account sender;
    private static Account receiver;
    private static User user;
    private static UserDao userDao;
    private static AccountDao accountDao;
    @BeforeClass
    public static void init(){

        paymentService = new PaymentService();
        paymentDao = paymentService.getPaymentDao();

        userDao = DaoFactory.getInstance().createUserDao();
        accountDao = DaoFactory.getInstance().createAccountDao();

        user = new User("testUser", "testPhone","testEmail",  "testPassword");
        userDao.add(user);
        user = userDao.findByEmail(user.getEmail());
        sender = new Account("99999","sender", "99919", 1000, user.getId());
        receiver = new Account("99998","receiver", "99918", 1000, user.getId());
        accountDao.add(sender);
        accountDao.add(receiver);
        sender = accountDao.findByNumber(sender.getNumber());
        receiver = accountDao.findByNumber(receiver.getNumber());
    }
    @AfterClass
    public static void del(){
        userDao.delete(user.getId());
        accountDao.delete(sender.getId());
        accountDao.delete(receiver.getId());

        userDao.close();
        accountDao.close();
        paymentDao.close();
    }
    @Test
    public void addPayment(){
        double amount = 100;
        final Payment payment = new Payment(amount, "", sender.getId(), receiver.getId());
        Assert.assertEquals(0, paymentDao.findByAccountId(sender.getId()).size());
        paymentService.addPayment(amount, "", sender.getNumber(), receiver.getNumber(), user);

        Payment actual = paymentDao.findByAccountId(sender.getId()).get(0);
        paymentDao.delete(actual.getId());
        Assert.assertNotNull(actual);
    }
    @Test
    public void checkStatus(){
        double amount = 100;
        final Payment payment = new Payment(amount, "", sender.getId(), receiver.getId());
        Assert.assertEquals(0, paymentDao.findByAccountId(sender.getId()).size());
        paymentService.addPayment(amount, "", sender.getNumber(), receiver.getNumber(), user);

        Payment actual = paymentDao.findByAccountId(sender.getId()).get(0);
        paymentDao.delete(actual.getId());
        Assert.assertNotNull(actual);
        Assert.assertEquals(PaymentStatus.PREPARED, actual.getStatus());
    }
    @Test
    public void cancelPayment(){
        double amount = 100;
        final Payment payment = new Payment(amount, "", sender.getId(), receiver.getId());
        Assert.assertEquals(0, paymentDao.findByAccountId(sender.getId()).size());
        paymentService.addPayment(amount, "", sender.getNumber(), receiver.getNumber(), user);

        Payment actual = paymentDao.findByAccountId(sender.getId()).get(0);
        paymentService.cancelPayment(actual.getId());
        //paymentDao.delete(actual.getId());

        Assert.assertEquals(0, paymentDao.findByAccountId(sender.getId()).size());
    }
    @Test
    public void sendPayment(){
        double amount = 100;
        final Payment payment = new Payment(amount, "", sender.getId(), receiver.getId());
        Assert.assertEquals(0, paymentDao.findByAccountId(sender.getId()).size());
        paymentService.addPayment(amount, "", sender.getNumber(), receiver.getNumber(), user);

        Payment actual = paymentDao.findByAccountId(sender.getId()).get(0);
        paymentService.sendPayment(actual.getId());
        actual = paymentDao.findByAccountId(sender.getId()).get(0);
        paymentDao.delete(actual.getId());
        Assert.assertEquals(PaymentStatus.SENT, actual.getStatus());
    }
}

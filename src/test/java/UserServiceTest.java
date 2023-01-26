import com.my.model.dao.ConnectionPool;
import com.my.model.dao.DaoFactory;
import com.my.model.dao.UserDao;
import com.my.model.dao.exceptions.DBException;
import com.my.model.entities.User;
import com.my.model.entities.enums.Block;
import com.my.model.entities.enums.Role;
import com.my.model.services.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {
    private Connection connection;
    private UserService userService;
    private UserDao userDao;


    @Before
    public void before() {
        connection = ConnectionPool.getConnection();
        userService = new UserService();
        userDao = userService.getUserDao();
    }

    @After
    public void after() {
        try {
            userDao.close();
            connection.close();
        } catch (SQLException e) {
            throw new DBException("Cannot close connection", e);
        }
    }
    @Test
    public void findByEmailAndPasswordCorrect() {
        final User user = User.builder()
                .id(320)
                .name("test1")
                .email("email1")
                .phoneNumber("phoneNumber")
                .password("password1")
                .role(Role.USER)
                .isBlocked(Block.ACTIVE)
                .build();

        userService.addUser(user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword());

        Optional<User> actual = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        actual.ifPresent(value -> userDao.delete(value.getId()));

        Assert.assertNotEquals(Optional.empty(), actual);

    }

    @Test
    public void findByEmailAndPasswordIncorrect() {
        final User user = User.builder()
                .id(320)
                .name("test2")
                .email("email1")
                .phoneNumber("phoneNumber")
                .password("password1")
                .role(Role.USER)
                .isBlocked(Block.ACTIVE)
                .build();

        userService.addUser(user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword());

        Optional<User> actual = userService.findByEmailAndPassword(user.getEmail(), "IncorrectPassword");
        User actualUser = userDao.findByEmail(user.getEmail());
        userDao.delete(actualUser.getId());
        Assert.assertEquals(Optional.empty(), actual);

    }
    @Test
    public void addUser() {
        final User user = User.builder()
                .id(320)
                .name("addUser2")
                .email("email2")
                .phoneNumber("phoneNumber")
                .password("password2")
                .role(Role.USER)
                .isBlocked(Block.ACTIVE)
                .build();

        userService.addUser(user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword());

        User actual = userDao.findByEmail(user.getEmail());

        userDao.delete(actual.getId());

        Assert.assertNotNull(actual);
    }
    @Test
    public void setBlockedTest1() {
        final User user = User.builder()
                .id(320)
                .name("name3")
                .email("email3")
                .phoneNumber("phoneNumber")
                .password("password3")
                .role(Role.USER)
                .isBlocked(Block.ACTIVE)
                .build();

        userService.addUser(user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword());

        User actual = userDao.findByEmail(user.getEmail());
        userService.setBlocked(actual.getId());
        actual = userDao.findByEmail(user.getEmail());

        userDao.delete(actual.getId());


        Assert.assertEquals(Block.BLOCKED, actual.getIsBlocked());
    }
    @Test
    public void setBlockedTest2() {
        final User user = User.builder()
                .id(320)
                .name("name4")
                .email("email3")
                .phoneNumber("phoneNumber")
                .password("password3")
                .role(Role.USER)
                .isBlocked(Block.ACTIVE)
                .build();

        userService.addUser(user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword());

        User actual = userDao.findByEmail(user.getEmail());
        userService.setBlocked(actual.getId());
        userService.setBlocked(actual.getId());
        actual = userDao.findByEmail(user.getEmail());

        userDao.delete(actual.getId());
        Assert.assertEquals(Block.ACTIVE, actual.getIsBlocked());

    }


    @Test
    public void promote() {
        final User user = User.builder()
                .id(320)
                .name("name5")
                .email("email4")
                .phoneNumber("phoneNumber")
                .password("password4")
                .role(Role.USER)
                .isBlocked(Block.ACTIVE)
                .build();

        userService.addUser(user.getName(), user.getPhoneNumber(), user.getEmail(), user.getPassword());

        User actual = userDao.findByEmail(user.getEmail());
        userService.promoteUser(actual.getId());
        actual = userDao.findByEmail(user.getEmail());

        userDao.delete(actual.getId());
        Assert.assertEquals(Role.ADMIN, actual.getRole());

    }
}

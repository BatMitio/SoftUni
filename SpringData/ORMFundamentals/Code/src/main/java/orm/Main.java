package orm;

import entities.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        Connector.createConnection("root", "root", "fsd");
        Connection connection = Connector.getConnection();
        EntityManager<User> entityManager = new EntityManager<>(connection);

        User user = new User();
        user.setId(2);
        user.setUsername("dsgasdg");
        user.setPassword("Goshinko");
        user.setAge(18);
        user.setRegistrationDate(LocalDate.of(2004,11,1));

        entityManager.persist(user);

    }
}

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;


public class DBTest {
    @BeforeEach
    @SneakyThrows
    void setUp() {
        Faker faker = new Faker();
        QueryRunner runner = new QueryRunner();
        String dataSQL = "INSERT INTO users(login, password) VALUES (?, ?);";

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/db", "kylon", "kylon0"
                );
        ) {
            runner.update(conn, dataSQL, faker.name().username(), "pass");
            runner.update(conn, dataSQL, faker.name().username(), "pass");
        }
    }

    @Test
    @SneakyThrows
    void Test() {
        String countSQL = "SELECT COUNT(*) FROM users;";
        String usersSQL = "SELECT * FROM users;";
        QueryRunner runner = new QueryRunner();

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/db", "kylon", "kylon0"
                );
        ) {
            Long count = runner.query(conn, countSQL, new ScalarHandler<>());
            System.out.println(count);
            User first = runner.query(conn, usersSQL, new BeanHandler<>(User.class));
            System.out.println(first);
            List<User> all = runner.query(conn, usersSQL, new BeanListHandler<>(User.class));
            System.out.println(all);
        }
    }
}

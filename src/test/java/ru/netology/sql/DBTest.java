package ru.netology.sql;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBTest {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db", "kylon", "kylon0");
    }

    @SneakyThrows
    public static void deleteTables() {
        val authCodes = "DELETE FROM auth_codes";
        val cardTransactions = "DELETE FROM card_transactions";
        val cards = "DELETE FROM cards";
        val users = "DELETE FROM users";
        val runner = new QueryRunner();
        try (val conn = getConnection();
        ) {
            runner.update(conn, authCodes);
            runner.update(conn, cardTransactions);
            runner.update(conn, cards);
            runner.update(conn, users);
        }
    }

    @SneakyThrows
    public static String getCode() {
        try (val conn = getConnection();
             val countStmt = conn.createStatement()) {
            val sql = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
            val resultSet = countStmt.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getString("code");
            }
        }
        return null;
    }
}

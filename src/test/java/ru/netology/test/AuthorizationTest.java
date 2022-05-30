package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.AuthorizationPage;
import ru.netology.sql.DBTest;

import static com.codeborne.selenide.Selenide.open;

public class AuthorizationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void cleanDb() {
        DBTest.deleteTables();
    }

    @Test
    void shouldAuthorization() {
        AuthorizationPage loginPage = new  AuthorizationPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        verificationPage.validVerify(DBTest.getCode());
    }

    @Test
    void shouldGetInvalidLoginMessage() {
        val  authorizationPage = new  AuthorizationPage();
        val authInfo = DataHelper.getAuthInfoInvalid();
        authorizationPage.login(authInfo);
        authorizationPage.getInvalidLogin();
        authorizationPage.cleaning();
        authorizationPage.login(authInfo);
        authorizationPage.getInvalidLogin();
    }
}
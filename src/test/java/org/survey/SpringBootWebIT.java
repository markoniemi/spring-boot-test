package org.survey;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.survey.config.IntegrationTestConfig;
import org.survey.model.user.Role;
import org.survey.model.user.User;
import org.survey.selenium.LoginPage;
import org.survey.selenium.UserPage;
import org.survey.selenium.UsersPage;
import org.survey.service.user.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApp.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = IntegrationTestConfig.class))
public class SpringBootWebIT {
    @Resource
    protected UserService userService;
    @Resource(name = "loginUrl")
    private String loginUrl;
    @Resource
    protected WebDriver webDriver;
    private LoginPage loginPage;
    private UsersPage usersPage;
    private UserPage userPage;

    @Before
    public void setUp() {
        loginPage = new LoginPage(webDriver);
        usersPage = new UsersPage(webDriver);
        userPage = new UserPage(webDriver);
    }

    @After
    public void tearDown() {
        deleteUserFromRepository("admin_user");
        deleteUserFromRepository("user_user");
        webDriver.close();
        webDriver.quit();
    }

    @Test
    public void integrationTest() throws InterruptedException {
        webDriver.get(loginUrl);
        loginPage.login("admin1", "admin");
        usersPage.clickAddUser();
        userPage.addUser("admin_user", "admin_user@test.com", "another", Role.ROLE_ADMIN);
        usersPage.assertUserRole("admin_user", "Admin");
        usersPage.clickAddUser();
        userPage.addUser("user_user", "user_user@test.com", "another", Role.ROLE_USER);
        usersPage.assertUserRole("user_user", "User");
        usersPage.deleteUser("user_user");
        usersPage.logout();
        loginPage.login("admin_user", "another");
        usersPage.editUser("admin_user");
        userPage.editUser("admin_user", "newpassword");
        usersPage.logout();
        loginPage.login("admin_user", "newpassword");
        usersPage.deleteUser("admin_user");
        usersPage.logout();
    }

    private void deleteUserFromRepository(String username) {
        User user = userService.findOne(username);
        if (user != null) {
            userService.delete(username);
        }
    }
}
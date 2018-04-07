package org.survey;

import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.selenium.SeleniumTestRule;
import org.selenium.annotation.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.survey.config.ApplicationConfig;
import org.survey.model.user.Role;
import org.survey.service.user.UserService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApp.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = ApplicationConfig.class))
@Slf4j
public class AppIT {
    private static final int SLEEP_TIME = 100;
    @ChromeDriver(headless=true)
    public WebDriver webDriver;
    @Rule
    public SeleniumTestRule seleniumTestRule = new SeleniumTestRule();
    private String httpPort;
    private String httpProtocol;
    private String serverURL;
    private String appName = "";
    @Resource
    protected UserService userService;

    @Before
    public void setUp() {
        httpPort = System.getProperty("http.port", "8082");
        httpProtocol = System.getProperty("http.protocol", "http");
        serverURL = httpProtocol + "://localhost:" + httpPort+"/survey-spring-web";
    }

    @After
    public void tearDown() {
        deleteUserFromRepository("registered_user");
        deleteUserFromRepository("admin_user");
        deleteUserFromRepository("user_user");
//        deletePollFromRepository("poll");
    }
    private void deleteUserFromRepository(String username) {
        userService.delete(username);
    }
    @Test
    public void integrationTest() throws InterruptedException {
        openBrowser();

//        loginError();
//        registerNewUser("registered_user", "registered_user@test.com", "test");
//        login("registered_user", "test");
//        assertUserRole("registered_user", "User");
//        assertNoDeleteOrAdd();
//        logout();
//        Thread.sleep(60000);
        login("admin", "admin");
//        deleteUser("registered_user");
        addUser("admin_user", "admin_user@test.com", "another", Role.ROLE_ADMIN);
        assertUserRole("admin_user", "Admin");
        addUser("user_user", "user_user@test.com", "another", Role.ROLE_USER);
        assertUserRole("user_user", "User");
        deleteUser("user_user");
        logout();
        login("admin_user", "another");
        editUser("admin_user", "newpassword");
        logout();
        login("admin_user", "newpassword");
        deleteUser("admin_user");
////        checkVersion();
//        addPoll("poll");
//        editPoll("poll");
//        addQuestion("question1");
//        deletePoll("poll");
        logout();
    }

    protected void openBrowser() throws InterruptedException {
        webDriver.get(serverURL + "/" + appName);
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Login",
                webDriver.getTitle());
    }

    protected void assertUserRole(String username, String localizedRole) {
        WebElement element = webDriver.findElement(By.xpath("//tr[td='"
                + username + "']//td[contains(@id,'role')]"));
        Assert.assertNotNull(element);
        Assert.assertEquals(localizedRole, element.getText());
    }

    protected void editUser(String username, String password)
            throws InterruptedException {
        WebElement button = webDriver.findElement(By
                .xpath("//tr[contains(td/text(),'" + username
                        + "')]//a[contains(@id,'edit')]"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "User",
                webDriver.getTitle());
        WebElement passwordElement = webDriver.findElement(By.id("password"));
        passwordElement.clear();
        passwordElement.sendKeys(password);
//        WebElement repeatPasswordElement = webDriver.findElement(By.id("repeatPassword"));
//        repeatPasswordElement.clear();
//        repeatPasswordElement.sendKeys(password);
        selectItemInList("role", Role.ROLE_USER.name());
        webDriver.findElement(By.id("submit")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(/*webDriver.getPageSource(),*/ "Users",
                webDriver.getTitle());
    }

    protected void selectItemInList(String elementId, String value) {
        WebElement roleSelect = webDriver.findElement(By.id(elementId));
        List<WebElement> options = roleSelect
                .findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getAttribute("value").equals(value)) {
                option.click();
            }
        }
    }

    protected void addUser(String username, String email, String password,
                           Role role) throws InterruptedException {
        webDriver.findElement(By.id("addUser")).click();
//        webDriver.get(serverURL + "/" + appName + "/#/users/user");
//        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "User",
                webDriver.getTitle());
        webDriver.findElement(By.id("username")).sendKeys(
                username);
        webDriver.findElement(By.id("password")).sendKeys(
                password);
//        webDriver.findElement(By.id("repeatPassword")).sendKeys(
//                password);
        webDriver.findElement(By.id("email")).sendKeys(email);
        selectItemInList("role", role.name());
        webDriver.findElement(By.id("submit")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(/*webDriver.getPageSource(),*/ "Users",
                webDriver.getTitle());
    }

    protected void deleteUser(String username) throws InterruptedException {
        WebElement button = webDriver.findElement(By.xpath("//tr[td='" + username
                + "']//button[@id='delete']"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Users",
                webDriver.getTitle());
    }

    protected void assertNoDeleteOrAdd() {
        try {
            webDriver.findElement(By.xpath("//tr//button[@id='delete']"));
            Assert.fail();
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    protected void registerNewUser(String username, String email,
                                   String password) {
        webDriver.findElement(By.id("register:register")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Register",
                webDriver.getTitle());
        webDriver.findElement(By.id("register:editUser:username")).sendKeys(
                username);
        webDriver.findElement(By.id("register:editUser:password")).sendKeys(
                password);
        webDriver.findElement(By.id("register:editUser:repeatPassword"))
                .sendKeys(password);
        webDriver.findElement(By.id("register:editUser:email")).sendKeys(email);
        webDriver.findElement(By.id("register:registerButton")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Login",
                webDriver.getTitle());
    }

    protected void checkVersion() {
        Assert.assertTrue(webDriver.getPageSource(), webDriver.getPageSource().contains("1.4-SNAPSHOT"));
    }

    private void addPoll(String pollName) throws InterruptedException {
        webDriver.findElement(By.id("menu-polls")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Polls",
                webDriver.getTitle());
        // TODO find a fix for click not working with maven build
        webDriver.findElement(By.id("addPoll")).click();
//        webDriver.get(serverURL + "/" + appName + "/#/polls/poll");
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Poll",
                webDriver.getTitle());
        webDriver.findElement(By.id("pollName")).sendKeys(pollName);
        webDriver.findElement(By.id("savePoll")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Polls",
                webDriver.getTitle());
    }

    private void editPoll(String pollName) {
        WebElement button = webDriver.findElement(By.xpath("//tr[td='" + pollName
                + "']//a[@id='edit']"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Assert.assertEquals(webDriver.getPageSource(), "Poll",
                webDriver.getTitle());
        // TODO remove when addQuestion works
//        webDriver.findElement(By.id("savePoll")).click();
//        Assert.assertEquals(webDriver.getPageSource(), "Polls",
//                webDriver.getTitle());
    }

    private void addQuestion(String questionText) {
        webDriver.findElement(By.id("addQuestion")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Poll",
                webDriver.getTitle());
        webDriver.findElement(By.id("questionText"))
                .sendKeys(questionText);
        webDriver.findElement(By.id("savePoll")).click();
        Assert.assertEquals(webDriver.getPageSource(), "Polls",
                webDriver.getTitle());
    }

    protected void deletePoll(String pollName) throws InterruptedException {
        WebElement button = webDriver.findElement(By.xpath("//tr[td='" + pollName
                + "']//button[@id='delete']"));
        Assert.assertNotNull(webDriver.getPageSource(), button);
        button.click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Polls",
                webDriver.getTitle());
    }

    protected void loginError() throws InterruptedException {
        webDriver.findElement(By.id("j_username")).clear();
        webDriver.findElement(By.id("j_username")).sendKeys("");
        webDriver.findElement(By.id("j_password")).clear();
        webDriver.findElement(By.id("j_password")).sendKeys("");
        webDriver.findElement(By.id("loginButton")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals("Login", webDriver.getTitle());
        Assert.assertTrue(webDriver.getPageSource().contains("Login error"));
    }

    protected void login(String username, String password) throws InterruptedException {
        webDriver.findElement(By.id("j_username")).clear();
        webDriver.findElement(By.id("j_username")).sendKeys(username);
        webDriver.findElement(By.id("j_password")).clear();
        webDriver.findElement(By.id("j_password")).sendKeys(password);
        webDriver.findElement(By.id("loginButton")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Users",
                webDriver.getTitle());
    }

    public void logout() throws InterruptedException {
        webDriver.findElement(By.id("logout")).click();
        Thread.sleep(SLEEP_TIME);
        Assert.assertEquals(webDriver.getPageSource(), "Login",
                webDriver.getTitle());
    }
}

package org.jwt;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

@SuppressWarnings("squid:S2925")
public class JwtAuthenticationIT {
    private String httpPort;
    private String httpProtocol;
    private String serverURL;
    private String appName = "jwt-authentication";
    private WebDriver browser;

    @Before
    public void setUp() {
        httpPort = System.getProperty("http.port", "8082");
        httpProtocol = System.getProperty("http.protocol", "http");
        serverURL = httpProtocol + "://localhost:" + httpPort;
        String phantomJsPath = System.getProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "target/phantomjs-maven-plugin/phantomjs-1.9.7-windows/phantomjs.exe");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomJsPath);
        browser = new PhantomJSDriver(capabilities);
    }

    @After
    public void tearDown() {
        browser.close();
        browser.quit();
    }

    @Ignore
    @Test
    public void test() throws InterruptedException {
        openBrowser();
        loginError();
        login();
        echo();
    }

    private void echo() throws InterruptedException {
        browser.findElement(By.id("echoButton")).click();
        Thread.sleep(500);
        String message = browser.findElement(By.id("messageFromServer")).getText();
        Assert.assertEquals("Message", message);
    }

    private void loginError() throws InterruptedException {
        browser.findElement(By.id("j_username")).clear();
        browser.findElement(By.id("j_username")).sendKeys("");
        browser.findElement(By.id("j_password")).clear();
        browser.findElement(By.id("j_password")).sendKeys("");
        browser.findElement(By.id("loginButton")).click();
        Thread.sleep(500);
        Assert.assertEquals(browser.getPageSource(), "Login",
                browser.getTitle());
    }

    private void login() throws InterruptedException {
        browser.findElement(By.id("j_username")).clear();
        browser.findElement(By.id("j_username")).sendKeys("admin");
        browser.findElement(By.id("j_password")).clear();
        browser.findElement(By.id("j_password")).sendKeys("admin");
        browser.findElement(By.id("loginButton")).click();
        Thread.sleep(500);
        Assert.assertEquals(browser.getPageSource(), "Echo",
                browser.getTitle());
    }

    protected void openBrowser() throws InterruptedException {
        browser.get(serverURL + "/" + appName + "/");
        Thread.sleep(500);
        Assert.assertEquals(browser.getPageSource(), "Login",
                browser.getTitle());
    }
}

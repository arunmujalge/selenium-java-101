package lambdatestjava;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LambdaTestJava {
	String username = "arunlambdatest";
	String accesskey = "DigzlyuMuhHtl8RJmZT4DtdIKRxogHPDtsZ92zluzhPKFy0I1S";
	public static RemoteWebDriver driver = null;
	public String gridURL = "@hub.lambdatest.com/wd/hub";
	boolean status = false;

	@BeforeClass
	@Parameters(value = { "browser", "version", "platform" })
	public void setUp(String browser, String version, String platform) throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("version", version);
		capabilities.setCapability("platform", platform); // If this cap isn't specified, it will just get the any
															// available one
		capabilities.setCapability("build", "Test Scenario 1");
		capabilities.setCapability("name", "Simple Form Demo");
		capabilities.setCapability("network", true); // To enable network logs
		capabilities.setCapability("visual", true); // To enable step by step screenshot
		capabilities.setCapability("video", true); // To enable video recording
		capabilities.setCapability("console", true); // To capture console logs

		try {
			driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
			driver.get("https://www.lambdatest.com/selenium-playground");
		} catch (MalformedURLException e) {
			System.out.println("Invalid grid URL");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test(timeOut = 20000)
	public void testScenario1() throws Exception {
		try {// Change it to production page
			driver.findElement(By.linkText("Simple Form Demo")).click();
			Assert.assertEquals(driver.getCurrentUrl(),
					"https://www.lambdatest.com/selenium-playground/simple-form-demo");
			String inputText = "Welcome to LambdaTest";
			WebElement messageBox = driver.findElement(By.id("user-message"));
			messageBox.clear();
			messageBox.sendKeys(inputText);
			driver.findElement(By.id("showInput")).click();
			String result = driver.findElement(By.xpath("//*[@id='message']")).getText();
			Assert.assertEquals(inputText, result);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@AfterClass
	public void tearDown() throws Exception {
		if (driver != null) {
			((JavascriptExecutor) driver).executeScript("lambda-status=passed");
			driver.quit();
		}
	}
}

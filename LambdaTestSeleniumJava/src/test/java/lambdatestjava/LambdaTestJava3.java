package lambdatestjava;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LambdaTestJava3 {
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
		capabilities.setCapability("build", "Test Scenario 3");
		capabilities.setCapability("name", "Input Submit Form");
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
	public void testScenario3() throws Exception {
		try {
			driver.findElement(By.linkText("Input Form Submit")).click();
			Assert.assertEquals(driver.getCurrentUrl(),
					"https://www.lambdatest.com/selenium-playground/input-form-demo");
			WebElement submitBtn = driver.findElement(By.xpath("//*[@id='seleniumform']/div[6]/button"));
			submitBtn.click();
			WebElement name = driver.findElement(By.name("name"));
			String errorMsg = name.getAttribute("validationMessage");
			Assert.assertEquals(errorMsg, "Please fill out this field.");
			name.sendKeys("Naveen");
			driver.findElement(By.id("inputEmail4")).sendKeys("arun@gmail.com");
			driver.findElement(By.name("password")).sendKeys("Arun@123");
			driver.findElement(By.name("company")).sendKeys("Cognizant");
			driver.findElement(By.name("website")).sendKeys("arun.com");
			Select country = new Select(driver.findElement(By.name("country")));
			country.selectByValue("US");
			driver.findElement(By.name("city")).sendKeys("Chennai");
			driver.findElement(By.name("address_line1")).sendKeys("North Street");
			driver.findElement(By.name("address_line2")).sendKeys("Guindy");
			driver.findElement(By.id("inputState")).sendKeys("TamilNadu");
			driver.findElement(By.name("zip")).sendKeys("600025");
			submitBtn.click();
			String successMsg = driver.findElement(By.xpath("//*[@id='__next']/div/section[3]/div/div/div[2]/div/p"))
					.getText();
			Assert.assertEquals(successMsg, "Thanks for contacting us, we will get back to you shortly.");

		} catch (Exception e) {
			// TODO: handle exception
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

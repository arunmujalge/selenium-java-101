package lambdatestjava;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LambdaTestJava2 {
	String username = "arunlambdatest";
	String accesskey = "DigzlyuMuhHtl8RJmZT4DtdIKRxogHPDtsZ92zluzhPKFy0I1S";
	public static RemoteWebDriver driver = null;
	public String gridURL = "@hub.lambdatest.com/wd/hub";
	public String browserName;
	boolean status = false;

	@BeforeClass
	@Parameters(value = { "browser", "version", "platform" })
	public void setUp(String browser, String version, String platform) throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("version", version);
		capabilities.setCapability("platform", platform); // If this cap isn't specified, it will just get the any
															// available one
		capabilities.setCapability("build", "Test Scenario 2");
		capabilities.setCapability("name", "Drag and Drop Sliders");
		capabilities.setCapability("network", true); // To enable network logs
		capabilities.setCapability("visual", true); // To enable step by step screenshot
		capabilities.setCapability("video", true); // To enable video recording
		capabilities.setCapability("console", true); // To capture console logs

		try {
			driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
			driver.get("https://www.lambdatest.com/selenium-playground");
			browserName = browser;
		} catch (MalformedURLException e) {
			System.out.println("Invalid grid URL");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test(timeOut = 20000)
	public void testScenario2() throws Exception {
		try {
			driver.findElement(By.linkText("Drag & Drop Sliders")).click();
			Assert.assertEquals(driver.getCurrentUrl(),
					"https://www.lambdatest.com/selenium-playground/drag-drop-range-sliders-demo");
			WebElement slider = driver.findElement(By.xpath("//input[@value='15']"));
			Actions act = new Actions(driver);
			if (browserName.equalsIgnoreCase("chrome")) {
				act.dragAndDropBy(slider, 120, 0).build().perform();
			}
			if (browserName.equalsIgnoreCase("firefox")) {
				act.dragAndDropBy(slider, 115, 0).build().perform();
			}
			if (browserName.equalsIgnoreCase("edge")) {
				act.dragAndDropBy(slider, 119, 0).build().perform();
			}
			if (browserName.equalsIgnoreCase("ie")) {
				act.dragAndDropBy(slider,85, 0).build().perform();
			}
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("window.scrollBy(0,300)");
			String out = driver.findElement(By.id("rangeSuccess")).getText();
			Assert.assertEquals(out, "95");
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

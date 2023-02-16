package org.tutorialninja.test;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

	WebDriver driver;

	// This @BeforeMethod will execute first before every method
	@Parameters({"URL"})  //to prevent hard coding if url change in future -- refer to testng.xml file
	@BeforeMethod
	public void setup(String url) {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.findElement(By.xpath("//span[contains(text(),'My Account')]")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Login")));
		driver.findElement(By.linkText("Login")).click();
	}
 
	// @AfterMethod -- will be executed after every test method
	@AfterMethod
	public void closer() {
		driver.quit();
	}

	@Test(priority = 1, timeOut = 5000) // we can set priority for execution -- otherwise execution will happen in
						// alphabetical order
	public void loginFunctionalityWithValidCredential() {

		driver.findElement(By.id("input-email")).sendKeys("asdfghjk@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Boolean breadCrumb = driver.findElement(By.xpath("//ul[@class='breadcrumb']//a[text()='Account']"))
				.isDisplayed();
		Assert.assertTrue(breadCrumb); // this will pass
	}

	@Test(priority = 2)
	public void loginFunctionalityWithInalidCredential() {

		driver.findElement(By.id("input-email")).sendKeys(invalidEmailGenerator());
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		String warningMessage = driver
				.findElement(
						By.xpath("//div[contains(text(),' Warning: No match for E-Mail Address and/or Password.')]"))
				.getText();
		AssertJUnit.assertEquals(warningMessage, "Warning: No match for E-Mail Address and/or Password."); // this will fail as
																								// warning message is
																								// wrong
	}

	// This method is to generate different invalid email
	public String invalidEmailGenerator() {
		Date date = new Date();
		return date.toString().replace(" ", "_").replace(":", "_") + "@gmail.com";
	}
}

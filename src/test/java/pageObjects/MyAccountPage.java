package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage {

	    WebDriver driver;

	    By logoutLink = By.xpath("//a[@class='list-group-item'][normalize-space()='Logout']");

	    public MyAccountPage(WebDriver driver) {
	        this.driver = driver;
	    }

	    public boolean isLogoutLinkPresent() {
	        try {
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	            wait.until(ExpectedConditions.presenceOfElementLocated(logoutLink));
	            return driver.findElement(logoutLink).isDisplayed();
	        } catch (Exception e) {
	            return false;
	        }
	    }

	    public void clickLogout() {
	        try {
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	            wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
	            driver.findElement(logoutLink).click();
	        } catch (Exception e) {
	            System.out.println("Logout link not clickable: " + e.getMessage());
	        }
	    }
	}


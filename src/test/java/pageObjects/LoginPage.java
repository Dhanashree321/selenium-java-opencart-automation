package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage
{

	public LoginPage(WebDriver driver)
	{
		super(driver);
		
	}
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtemailAddress;
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPasword;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement btnLogin;
	
	public void setEmail(String email)
	{
		txtemailAddress.sendKeys(email);
	}
	
	public void setPassword(String pwd)
	{
		txtPasword.sendKeys(pwd);
	}
	
	public void clickLogin()
	{
		btnLogin.click();
	}
	
	public boolean isLoginWarningDisplayed()
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

            WebElement warn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(@class,'alert-danger')]")
                )
            );

            return warn.isDisplayed();
        }
        catch(Exception e)
        {
            return false;
        }
    }
	

}

package testCases;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass
{
	
	
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registration()
	{
		//logger.info("""""""""""Starting TC001_AccountRegistrationTest """""""""""""""");"
		logger.info(" *******Starting TC001_AccountRegistrationTest****** ");
		
		try
		{
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on MyAccount");
		
		hp.clickRegister();
		logger.info("Clicked on Register Link");
		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		
		logger.info("Providing customer details");
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString()+"@gmail.com"); //generate random data
		regpage.setTelephone(randomNumber());
		
		String password=randomAlphaNumeric();
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		
		regpage.setPrivatePolicy();
		regpage.clickContinue();
		
		logger.info("Validating exected message");
		String msg=regpage.getConfirmation();
		if(msg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test failed");
			logger.debug("Debug logs");
			Assert.assertTrue(false);
		}
		
		//Assert.assertEquals(msg,"Your Account Has Been Created!!");
		}
		catch(Exception e)
		{
			
			Assert.fail();
		}
		
		logger.info(" *******Finished TC001_AccountRegistrationTest****** ");

	}

	

}

package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class,groups="Datadriven")
    public void testLoginDDT(String email, String password, String expectedResult) {
        try {
        	
        	//driver.get(rb.getString("appURL"));
        	
        	//HomePage
			HomePage hp=new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
        	
            LoginPage lp = new LoginPage(driver);
            lp.setEmail(email);
            lp.setPassword(password);
            lp.clickLogin();

            MyAccountPage myAcc = new MyAccountPage(driver);

            // Check if login was successful
            boolean isLoginSuccessful = myAcc.isLogoutLinkPresent();

            if (expectedResult.equalsIgnoreCase("Valid")) 
            {
                if (isLoginSuccessful) 
                {
                    Assert.assertTrue(true, "Login successful as expected");
                    myAcc.clickLogout(); // Logout only if login succeeded
                } 
                else 
                {
                    Assert.fail("Login failed with valid credentials");
                }
            }
            else if (expectedResult.equalsIgnoreCase("Invalid")) 
            {
                if (!isLoginSuccessful)
                {
                    Assert.assertTrue(true, "Login failed as expected for invalid credentials");
                }
                else 
                {
                    Assert.fail("Login succeeded with invalid credentials");
                    myAcc.clickLogout(); // Optional: logout to reset state
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception during login test: " + e.getMessage());
        }
    }
}

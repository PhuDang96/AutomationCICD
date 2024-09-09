package phudn.test;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import phudn.TestComponents.BaseTest;
import phudn.pageobjects.CartPage;
import phudn.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups = {"ErrorHandling"}, retryAnalyzer= phudn.TestComponents.Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException {

		
		
		
		
		String productName = "ZARA COAT 3";
		landingPage.loginApplication("dangphudn96@gmail.com", "Admin@");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());

	}

	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException {

		String productName = "ZARA COAT 3";

		ProductCatalogue productCatalogue = landingPage.loginApplication("dangphudn96@gmail.com", "Admin@123");
		// ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		// CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
	}
	
	
}

package phudn.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import phudn.TestComponents.BaseTest;
import phudn.pageobjects.CartPage;
import phudn.pageobjects.CheckoutPage;
import phudn.pageobjects.ConfirmationPage;
import phudn.pageobjects.OrderPage;
import phudn.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {

	String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = { "Purchase" })
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		// ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();
		// CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);

		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("Viet");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();

		String confirmMessage = confirmationPage.getConfirmationMessage();
		AssertJUnit.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}

	@Test(dependsOnMethods = { "submitOrder" })
	public void OrderHistoryTest() {
		// "ZARA COAT 3"
		ProductCatalogue productCatalogue = landingPage.loginApplication("dangphudn96@gmail.com", "Admin@123");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));

	}

	// Extent Reports

	@DataProvider
	public Object[][] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "//src//test//java//phudn//data//PurchaseOrder.json");

		return new Object[][] { { data.get(0) }, { data.get(1) } };

	}

	// HashMap<String, String> map = new HashMap<String, String>();
	// map.put("email", "dangphudn96@gmail.com");
	// map.put("password", "Admin@123");
	// map.put("product", "ZARA COAT 3");

	// HashMap<String, String> map1 = new HashMap<String, String>();
	// map.put("email", "phudn@gmail.com");
	// map.put("password", "Admin@123");
	// map.put("product", "ADDIDAS ORIGINAL");
}

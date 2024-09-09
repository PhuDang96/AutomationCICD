package phudn.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.AssertJUnit;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import phudn.TestComponents.BaseTest;
import phudn.pageobjects.CartPage;
import phudn.pageobjects.CheckoutPage;
import phudn.pageobjects.ConfirmationPage;
import phudn.pageobjects.LandingPage;
import phudn.pageobjects.ProductCatalogue;

public class StepDefinitionIml extends BaseTest {

	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;

	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecomerce_Page() throws IOException {

		landingPage = launchApplication();
	}

	// @Given ("Logged in with username dangphudn96@gmail.com and password
	// Admin@123")

	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_username_and_password(String username, String password) {

		productCatalogue = landingPage.loginApplication(username, password);

	}

	@When("^I add product (.+) from Cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException {

		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}

	@When("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName) {

		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);

		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("Viet");
		confirmationPage = checkoutPage.submitOrder();
	}

	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed_confirmationPage(String string) {

		String confirmMessage = confirmationPage.getConfirmationMessage();
		AssertJUnit.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close();
	}

	@Then("^\"([^\"]*)\" message is displayed$")
	public void something_message_is_displayed(String stgArg1) throws Throwable {

		Assert.assertEquals(stgArg1, landingPage.getErrorMessage());
		driver.close();
	}

}

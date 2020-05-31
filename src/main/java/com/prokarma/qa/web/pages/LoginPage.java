package com.prokarma.qa.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.prokarma.qa.base.CommonFunctions;
import com.prokarma.qa.web.helpers.Log;

public class LoginPage extends CommonFunctions{
	
	private WebDriver driver;

	public LoginPage(WebDriver driver) {
    	this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
		
	@FindBy(id="txtUsername")
	private WebElement userName_txtBox;
	
	@FindBy(id="txtPassword")
	private WebElement txtPassword_txtBox;
	
	@FindBy(xpath="//*[@id='btnLogin']/button")
	private WebElement login_btn;
	
//	public HomePage doLogin(String username, String password) {
//		
//				username=(String) setup.get(3);
//				password=(String) setup.get(4);
	
	public HomePage doLogin() {
		
				userName_txtBox.clear();
				userName_txtBox.sendKeys((String) setup.get(3));
				txtPassword_txtBox.clear();
				txtPassword_txtBox.sendKeys((String) setup.get(4));
				login_btn.click();
//				
//				Log.info("Login Successfull");
//				write("PASS", "Login Successfull");
				
				return new HomePage(driver);
	
	}


	
}

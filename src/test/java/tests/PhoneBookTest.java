package tests;

import config.BaseTest;
import helpers.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import model.Contact;
import model.User;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AddPage;
import pages.ContactsPage;
import pages.LoginPage;
import pages.MainPage;

import java.io.IOException;

public class PhoneBookTest extends BaseTest {

    @Test(description = "The test checks the empty field warning declaration.")
    @Parameters("browser")
    public void registrationWithoutPassword(@Optional("chrome") String browser) throws InterruptedException {
        Allure.description("User already exist. Login and add contact.!");

        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Click by Login button");
        LoginPage loginPage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Click by Reg button");
        String expectedString = "Wrong";

        Alert alert= loginPage.fillEmailField("myemail@mail.com").clickByRegistartionBUtton();
        boolean isAlertHandled = AlertHandler.handleAlert(alert, expectedString);
        Assert.assertTrue(isAlertHandled);

    }
    @Test
    @Description("User already exist. Login and add contact.")
    public void loginOfAnExistingUserAddContact() throws InterruptedException {
        Allure.description("User already exist. Login and add contact.!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");
        lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                .clickByLoginButton();
        Allure.step("Step 3");
        MainPage.openTopMenu(TopMenuItem.ADD.toString());
        AddPage addPage = new AddPage(getDriver());
        Contact newContact = new Contact(NameAndLastNameGenerator.generateName(),
                NameAndLastNameGenerator.generateLastName(),
                PhoneNumberGenerator.generatePhoneNumber(),
                EmailGenerator.generateEmail(10,5,3),
                AddressGenerator.generateAddress(),
                "new description");
        newContact.toString();
        addPage.fillFormAndSave(newContact);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.getDataFromContactList(newContact));
        TakeScreen.takeScreenshot("screen");
        Thread.sleep(3000);

    }

    @Test
    @Description("Registration successful")

    public void testRegistrationSuccess() throws Exception {

        Allure.description("New Registration, user is not registered ");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");
        lpage.fillEmailField(EmailGenerator.generateEmail(5,1,3))
                .fillPasswordField(PasswordStringGenerator.generateString())
                .clickByRegistartionBUtton();
        Allure.step("Step 3");
        Assert.assertTrue(new ContactsPage().isElementPresent(By.xpath("//button")));
        TakeScreen.takeScreenshot("screenReg");
    }


    @Test
    public void removeOneContactPositive() throws InterruptedException {
        Allure.description("User already exist. Login and delete contact.!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1 Login");
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2 Fill Login Form");
        lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                .clickByLoginButton();
        Allure.step("Step 3 Remove Contact");
        int res = ContactsPage.removeOneContact();
        Assert.assertEquals(-1, res);
    }

      @Test
    public void deleteContactApproachTwo() throws IOException, InterruptedException {
        String filename = "contactDataFile.ser";
        MainPage mainPage = new MainPage(getDriver());
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
          lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                  .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                  .clickByLoginButton();
        MainPage.openTopMenu(TopMenuItem.ADD.toString());
        AddPage addPage = new AddPage(getDriver());
        Contact newContact = new Contact(NameAndLastNameGenerator.generateName()
        , NameAndLastNameGenerator.generateLastName(),PhoneNumberGenerator.generatePhoneNumber()
        , EmailGenerator.generateEmail(5,1,3), AddressGenerator.generateAddress(), "description"
        );
        addPage.fillFormAndSave(newContact);
        Contact.serializeContact(newContact,filename);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Contact deserContact = Contact.deserializeContact(filename);
          int res = ContactsPage.removeOneContact();
          Assert.assertEquals(-1, res);


      }

    @Test
    public void deleteContact() throws InterruptedException {
        Allure.description("User already exist. Delete contact by phone number!");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Step 1");
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
        Allure.step("Step 2");
        lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
                .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
                .clickByLoginButton();
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertNotEquals(contactsPage.deleteContactByPhoneNumberOrName("0123456789843"),
                contactsPage.getContactsListSize(),"Contact lists are different");

    }
   @Test
   @Description("User already exist. Registration")
   public void registrationOfAnAlreadyRegisteredUser(){
       Allure.description("Step 1");
       MainPage mainPage = new MainPage(getDriver());
       Allure.step("Open registrationPage");
       LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());
       Allure.step("Fill registration form");
       String expectedString = "User already exist";
       Alert alert = lpage.fillEmailField(PropertiesReader.getProperty("existingUserEmail"))
               .fillPasswordField(PropertiesReader.getProperty("existingUserPassword"))
               .clickByRegistartionBUtton();
       boolean isAlertHandled = AlertHandler.handleAlert(alert, expectedString);
       Assert.assertTrue(isAlertHandled);
   }
    @Test
    @Description("Registration attempt test.")
    public void reRegistrationAttempt() {
        boolean res = false;
        Allure.description("Registration attempt test.");
        MainPage mainPage = new MainPage(getDriver());
        Allure.step("Open LOGIN menu");
        LoginPage lpage = mainPage.openTopMenu(TopMenuItem.LOGIN.toString());

        User user = new User(EmailGenerator
                .generateEmail(7, 7, 3), PasswordStringGenerator.generateString());

        lpage.fillEmailField(user.getUserEmail())
                .fillPasswordField(user.getUserPassword());
        Alert alert = lpage.clickByRegistartionBUtton();
        if (alert == null) {
            ContactsPage contactsPage = new ContactsPage(getDriver());
            lpage = contactsPage.clickBySignOutButton();
            Alert alert1 = lpage.fillEmailField(user.getUserEmail())
                    .fillPasswordField(user.getUserPassword()).clickByRegistartionBUtton();
            if (alert1 != null) {
                res = AlertHandler.handleAlert(alert1, "exist");
            }
        } else {
            System.out.println("reRegistrationAttempt");
        }
        Assert.assertTrue(res, "Registration attempt failed");
    }



}
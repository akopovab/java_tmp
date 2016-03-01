package ru.stqa.pft.addressbook;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.*;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation()
    {
       /* wd.get("http://localhost/addressbook/"); */

        initContactCreation();
        fillContactForm(new ContactData("Olga", "Petrova", "45 Green Street", "2156106755", "olga.petrova@hotmail.com"));
        submitContactCreation();
        gotoHomePage();
    }

}

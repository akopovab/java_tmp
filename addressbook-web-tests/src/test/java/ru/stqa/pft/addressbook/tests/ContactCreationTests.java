package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation()
    {


        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactForm(new ContactData("Olga", "Petrova", "45 Green Street", "2156106755", "olga.petrova@hotmail.com"));
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().gotoHomePage();
    }

}

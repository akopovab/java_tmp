package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {


    @Test
    public void testContactDeletion()  {

        if(! app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("Olga", "Petrova",
                    "45 Green Street", "2156106755", "olga.petrova@hotmail.com", "test2"));
        }
        app.getContactHelper().choiceContact();
        app.getContactHelper().deleteContact();
    }







}

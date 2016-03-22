package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {


    @Test(enabled=false)
    public void testContactDeletion()  {

      /*  if(! app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("Olga", "Petrova",
                    "45 Green Street", "2156106755", "olga.petrova@hotmail.com", "test0"));
        }
        List<ContactData> before= app.getContactHelper().getContactList();
        app.getContactHelper().choiceContact(before.size()-1);
        app.getContactHelper().deleteContact();
        app.getContactHelper().returnContactPage();
        List<ContactData> after= app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(),before.size()-1);
        before.remove(before.size()-1);
        Assert.assertEquals(before, after);   */

    }







}

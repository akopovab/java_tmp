package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().gotoHomePage();
        if (app.contact().all().size() == 0)
            //  if (app.group().list().size()==0)                     //(!app.group().isThereAGroup()) {
            app.contact().createContact(new ContactData().withFirstname("Ola").withLastname("Ivanova"));
    }



    @Test
    public void testContactDeletion()  {


        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        assertEquals(app.contact().count(), before.size() - 1);
        Contacts after = app.contact().all();
        assertThat(after, equalTo( before.withOut(deletedContact)));
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

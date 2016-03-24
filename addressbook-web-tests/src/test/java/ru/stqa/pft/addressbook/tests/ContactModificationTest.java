package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTest extends TestBase {


    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().gotoHomePage();
        if (app.contact().all().size() == 0)
            //  if (app.group().list().size()==0)                     //(!app.group().isThereAGroup()) {
            app.contact().createContact(new ContactData().withFirstname("Ola").withLastname("Ivanova"));
    }


    @Test
    public void testModification() {

        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Natasha").withLastname("Petrova").withAddress("45 Green Street");
        app.contact().modify(contact);
        assertEquals(app.contact().count(), before.size());
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.withOut(modifiedContact).
                withAdded(contact)));

 /*       if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Olga", "Petrova",
                    "45 Green Street", "2156106755", "olga.petrova@hotmail.com", "test2","","",""));
        }
        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().initContactModification(before.size() - 1);
        ContactData contact = new ContactData(before.get(before.size() - 1).getId(), "Ola", "Konina", "Moscow, " +
                "Russia", "Manja@mail.ru", "123456", null);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().updateContact();
        app.getContactHelper().returnContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size()-1);
        before.add(contact);
        Comparator<? super ContactData> byId=(g1, g2)->Integer.compare(g1.getId(),g2.getId());
        before.sort(byId);
        after.sort(byId);
       //  Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object> (after));
        Assert.assertEquals(before,after);
       /* System.out.println( "before" +before);
        System.out.println("after"+after); */


    }


}

package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactModificationTest extends TestBase {


    @Test
    public void testModification() {

        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("Olga", "Petrova",
                    "45 Green Street", "2156106755", "olga.petrova@hotmail.com", "test2"));
        }
        List<ContactData> before = app.getContactHelper().getContactList();

       // System.out.println( "before 1111" +before);

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

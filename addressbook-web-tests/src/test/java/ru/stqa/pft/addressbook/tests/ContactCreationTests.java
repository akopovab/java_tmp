package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() {

    app.getContactHelper().returnContactPage();
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("Olga", "Petrova",
            "45 Green Street", "2156106755", "olga.petrova@hotmail.com", "test0");
    app.getContactHelper().createContact(contact);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);

    // contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(),o2.getId())).get().getId());
    contact.setId(after.get(after.size() - 1).getId());
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);

    Assert.assertEquals(before, after); // сравнение нами отсортированных объектов(списков)
    // Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object> (after)); сравнение множеств, без сортировки

    // app.getContactHelper().submitContactCreation();
    //app.getNavigationHelper().gotoHomePage();

  }

}

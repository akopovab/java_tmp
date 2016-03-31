package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test(dataProvider = "validContacsFromCsv")
  public void testContactCreation(ContactData contact) {

    app.goTo().gotoHomePage();
    Contacts before=app.contact().all();
    File photo=new File("src/main/resources/Tulips.jpg");
   // ContactData contact=new ContactData().withFirstname("Ira").withLastname("Goup").
    //       withPhoto(photo);
     contact=contact.withPhoto(photo);
    app.contact().createContact(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().
                    mapToInt((g) -> g.getId()).max().getAsInt()))));

 /*  app.getContactHelper().returnContactPage();
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("Olga", "Petrova",
            "45 Green Street", "2156106755", null, null);
    app.getContactHelper().createContact(contact);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);

   // contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(),o2.getId())).get().getId());

      before.add(contact);
   /* Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId()); - сортировка по id
     before.sort(byId);
     after.sort(byId); */

   /* Comparator<? super ContactData> byLastName = (g1, g2) -> String.CASE_INSENSITIVE_ORDER.compare(g1.getLastName(), g2.getLastName()); //- сортировка по LastName
    before.sort(byLastName);
    after.sort(byLastName);

    Assert.assertEquals(before, after); // сравнение нами отсортированных объектов(списков)
    // Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object> (after)); сравнение множеств, без сортировки

    // app.getContactHelper().submitContactCreation();
    //app.getNavigationHelper().gotoHomePage();      */



  }

  @Test(enabled = true)
  public void currentDir(){

    File currentDir=new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo=new File("src/main/resources/Tulips.jpg");
       System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }
}

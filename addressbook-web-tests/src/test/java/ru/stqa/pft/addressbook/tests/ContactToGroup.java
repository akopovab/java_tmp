package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by eee on 02.04.2016.
 */
public class ContactToGroup  extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) { // проверка при загрузке данных из database
      app.goTo().gotoHomePage();
      app.contact().createContact(new ContactData().withFirstname("Masha").withLastname("Durova"));
    }

    if (app.db().groups().size() == 0) { // проверка при загрузке данных из database
      app.goTo().groupPage();
      // if (app.group().all().size() == 0) - проверка при загрузке данных из web
      //if( app.group().list().size()==0 )                         //     (!app.group().isThereAGroup()) {
      app.group().create(new GroupData().withName("testik"));
    }
  }

  @Test
  public void testContactToGroup() {

    app.goTo().groupPage();
    Groups groups = app.group().all();
    app.goTo().gotoHomePage();
    Contacts before = app.db().contacts();
    //ContactData modifiedContact = before.iterator().next();
    // GroupData group =groups.iterator().next();
    ContactData modifiedContact = null;
    GroupData group = null;
    boolean isPossibleToAdd = false;

    Iterator<ContactData> itrContacts = before.iterator();
    while (itrContacts.hasNext()) {
      Iterator<GroupData> itrGroups = groups.iterator();
      modifiedContact = itrContacts.next();

      while (itrGroups.hasNext()) {
        group = itrGroups.next();
        if (modifiedContact.getGroups().contains(group)) continue;
        else {
          isPossibleToAdd = true;
          break;
        }
      }
      if (isPossibleToAdd) break;
    }

    if (!isPossibleToAdd) {
      //System.out.println(" Все контакты добавлены в группы");
      Assert.assertTrue(itrContacts.hasNext(), " Хотели добавить контакт, но.. уже все контакты добавлены" +
              "во все группы");
    }

    app.contact().toGroup(modifiedContact, group);
    app.goTo().gotoHomePage();
    Contacts after = app.contact().all();
    app.contact().formContactsPlusGroupsfromUI(after);
     assertion();

  }


}
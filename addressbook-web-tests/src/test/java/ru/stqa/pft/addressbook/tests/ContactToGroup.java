package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by eee on 02.04.2016.
 */
public class ContactToGroup  extends TestBase{
  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) { // проверка при загрузке данных из database
      app.goTo().gotoHomePage();
      // if (app.group().all().size() == 0) - проверка при загрузке данных из web
      //if( app.group().list().size()==0 )                         //     (!app.group().isThereAGroup()) {
      app.contact().createContact(new ContactData().withFirstname("Masha").withLastname("Durova"));
    }
  }

  @Test
  public void testContactToGroup() {

    app.goTo().groupPage();
    Groups groups = app.group().all();
    app.goTo().gotoHomePage();
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();


    app.contact().toGroup(modifiedContact,groups.iterator().next());

    app.goTo().gotoHomePage();
    Contacts after =app.contact().all();
    System.out.println(" ui "+ after);

    for ( ContactData contact : after ) {

      String[] namegroup = verifyGroup(contact);
      if(namegroup !=null ) {
         for (int i = 0; i < namegroup.length; i++) {
          GroupData group = new GroupData().withName(namegroup[i].trim());
          contact = contact.inGroup(group);
        }
      }
     // System.out.println(contact);
     // System.out.println(contact.getGroups());

      }

      assertion();

    }



  private String[] verifyGroup(ContactData contact) {
    app.goTo().gotoHomePage();
    String details=app.getContactHelper().infoFromDetailForm( contact );
    String det[]=details.split("\n");
    for(int i=0;i<det.length;i++) {
      if (det[i].contains("Member of:")) {
        int ind = det[i].lastIndexOf(":");
        det[i] = det[i].substring(ind + 2);
        String[] groups = det[i].split(",");
        return groups;
      }
    }
    return null;
  }


}

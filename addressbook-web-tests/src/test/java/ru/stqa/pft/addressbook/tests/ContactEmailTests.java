package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
/**
 * Created by eee on 22.03.2016.
 */
public class ContactEmailTests extends TestBase {

  @Test
  public void testContactEmails(){

    app.goTo().gotoHomePage();
    ContactData contact=app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm=app.contact().infoFromEditForm(contact);

    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
  }

  private String mergeEmails(ContactData contact) {

    return Arrays.asList(contact.getEmail1(),contact.getEmail2()
            ,contact.getEmail3()).stream().filter((s) ->! s.equals(""))
            //.map(ContactPhoneTests::cleaned)
            .collect(Collectors.joining("\n"));


  }

  public static String cleaned(String phone){
    return phone.replaceAll("\\s","").replaceAll("[-()]","");
  }
}




package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by eee on 22.03.2016.
 */
public class ContactDetailTest extends TestBase {

    @Test
    public void testContactDetails(){

      app.goTo().gotoHomePage();
      ContactData contact=app.contact().all().iterator().next();
      String contactInfoFromDetailForm=app.contact().infoFromDetailForm(contact);
        app.goTo().gotoHomePage();
      ContactData contactInfoFromEditForm=app.contact().infoFromEditForm(contact);
      //ContactData contactInfoFromEditForm=app.contact().infoFromEditForm(contact);
       /* assertThat(contact.getHomePhone(), equalTo(cleaned(contactInfoFromEditForm.getHomePhone())));
        assertThat(contact.getMobilePhone(), equalTo(cleaned(contactInfoFromEditForm.getMobilePhone())));
        assertThat(contact.getWorkPhone(), equalTo(cleaned(contactInfoFromEditForm.getWorkPhone())));  */

       assertThat(cleaned(contactInfoFromDetailForm), equalTo(mergeContact(contactInfoFromEditForm)));

    }

    private String mergeContact(ContactData contact) {
        /*String result="";
        if(contact.getHomePhone() !=null) result=result+contact.getHomePhone();
        return result; */


      return Arrays.asList(contact.getFio(),contact.getAddress(),
              contact.getHomePhone(),contact.getMobilePhone(),contact.getWorkPhone(),contact.getEmail1(),contact.getEmail2(),contact.getEmail3())
              .stream().filter((s) ->! s.equals(""))
              // .map(ContactDetailTest::cleanForm)
              .collect(Collectors.joining(""));


    }


    public static String cleaned(String details){

        details= details.replaceAll("\\(www(.*).\\)|H:|M:|W:|","");
        String det[]=details.split("\n");


        for(int i=0;i<det.length;i++)
                      det[i]=det[i].trim();


        details="";
        for(int i=0;i<det.length;i++) {
             details = details + det[i];


        }
        return details;
    }



}
package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by 3 on 21.03.2016.
 */
public class ContactPhoneTests extends TestBase {

    @Test
    public void testContactPhones(){

        app.goTo().gotoHomePage();
        ContactData contact=app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm=app.contact().infoFromEditForm(contact);
       /* assertThat(contact.getHomePhone(), equalTo(cleaned(contactInfoFromEditForm.getHomePhone())));
        assertThat(contact.getMobilePhone(), equalTo(cleaned(contactInfoFromEditForm.getMobilePhone())));
        assertThat(contact.getWorkPhone(), equalTo(cleaned(contactInfoFromEditForm.getWorkPhone())));  */

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    }

    private String mergePhones(ContactData contact) {
        /*String result="";
        if(contact.getHomePhone() !=null) result=result+contact.getHomePhone();
        return result; */

       return Arrays.asList(contact.getHomePhone(),contact.getMobilePhone(),contact.getWorkPhone()).stream().filter((s) ->! s.equals(""))
                 .map(ContactPhoneTests::cleaned)
                .collect(Collectors.joining("\n"));


    }

    public static String cleaned(String phone){
        return phone.replaceAll("\\s","").replaceAll("[-()]","");
    }
}

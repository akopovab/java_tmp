package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase {

    
    @Test
    public void testModification() {

      app.getContactHelper().initContactModification();
      app.getContactHelper().fillContactForm(new ContactData("Korona", "Manja", "Moscow, " +
              "Russia", "Manja@mail.ru", "123456", null),false);
      app.getContactHelper().updateContact();
    }




}

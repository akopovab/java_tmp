package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;



/**
 * Created by 3 on 05.04.2016.
 */
public class ContactRemoveFromGroup extends TestBase{

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
    public void testContactFromGroup() {

        ContactData contact= new ContactData();
        boolean isFoundContactforRemoval=false;
        app.goTo().groupPage();
        Groups groups = app.group().all();
        app.goTo().gotoHomePage();
        for(GroupData group: groups) {
            app.contact().findContactintoGroup(group);
            Contacts contacts=app.contact().all();
            if (contacts.size()==0)
                continue;
            else {

                contact=contacts.iterator().next();
                isFoundContactforRemoval=true;
                break;
            }
        }
        if (! isFoundContactforRemoval) {
            System.out.println("Добавляем контакт в какую-нибудь группу  для дальнейшего удаления");
            app.contact().findContactintoGroup(new GroupData().withName("[all]"));
            Contacts contacts = app.contact().all();
            contact = contacts.iterator().next();
            app.contact().toGroup(contact, groups.iterator().next());
            app.goTo().ContactsIntoGroupPage();

        }
        app.contact().remove(contact);
        Contacts after= app.contact().all();
        app.contact().formContactsPlusGroupsfromUI(after);
        assertion();


}



}

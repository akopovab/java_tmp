package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }


  public void submitContactCreation() {
    click(By.xpath("//div[@id='content']/form/input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getPhoneNumber());
    type(By.name("email"), contactData.getEmail1());
    attach(By.name("photo"), contactData.getPhoto());
    if (creation) {
      // if (contactData.getGroup()!=null)  //- вариант без вытаскивания групп из БД.
      // new Select(wd.findElement(By.name("new_group"))).selectByVisibleText
      //         (contactData.getGroup());
      if (contactData.getGroups().size() > 0)
        Assert.assertTrue(contactData.getGroups().size() == 1);
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText
              (contactData.getGroups().iterator().next().getName());
    } else Assert.assertFalse(isElementPresent(By.name("new_group")));

  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }


  public void deleteContact() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    if (isAlertPresent())
      wd.switchTo().alert().accept();


  }


  public void choiceContact(int index) {

    wd.findElements(By.name("selected[]")).get(index).click();
    //click(By.name("selected[]"));


  }

  public void selectContactById(int id) {

    wd.findElement(By.cssSelector(String.format("input[id='%s']", id))).click();

  }

  public boolean isThereAContact()

  {
    return isElementPresent(By.name("selected[]"));
  }

  public void createContact(ContactData contact) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    contactCache = null;
    returnContactPage();
  }

  public void modify(ContactData contact) {
    initContactModificationById(contact.getId());
    fillContactForm(contact, false);
    updateContact();
    contactCache = null;
    returnContactPage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteContact();
    contactCache = null;
    returnContactPage();
  }


  public void returnContactPage() {

    if (isElementPresent(By.id("maintable"))) return;
    click(By.linkText("home"));
  }

  public void initContactModification(int index) {
    //driver.findElement(By.cssSelector("input[type=’submit’][value=’Subscribe’]"));
    wd.findElements(By.cssSelector("img[alt='Edit']")).get(index).click();
    //click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
  }

  public void updateContact() {
    click(By.name("update"));
    //click(By.xpath("//div[@id='content']/form[1]/input[22]"));
    contactCache = null;
  }


  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      String firstname = element.findElements(By.tagName("td")).get(2).getText();
      String lastname = element.findElements(By.tagName("td")).get(1).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
      ContactData contact = new ContactData().withId(id).withFirstname(firstname).withLastname(lastname); //null,null,null,null);
      contacts.add(contact);

    }
    return contacts;

  }

  private Contacts contactCache = null;

  public Contacts all() {   //Set<GroupData> all() {
    if (contactCache != null) return new Contacts(contactCache);
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      String firstname = element.findElements(By.tagName("td")).get(2).getText();
      String lastname = element.findElements(By.tagName("td")).get(1).getText();
      String allPhones = element.findElements(By.tagName("td")).get(5).getText();
      String allEmails = element.findElements(By.tagName("td")).get(4).getText();
      //System.out.println("allEmails"+ allEmails);
      String address = element.findElements(By.tagName("td")).get(3).getText();
      // String[] phones = allPhones.split("\n");
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
    /*  ContactData contact = new ContactData().withId(id).withFirstname(firstname).withLastname(lastname).
      withHomePhone(phones[0]).withMobilePhone(phones[1]).withWorkPhone(phones[2]);   // firstname, lastname, null,null,null,null); */
      ContactData contact = new ContactData().withId(id).withFirstname(firstname).withLastname(lastname).
              withAllPhones(allPhones).withAllEmails(allEmails).withAddress(address);
      contactCache.add(contact);

    }
    return new Contacts(contactCache);
  }


  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String middlename = wd.findElement(By.name("middlename")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email1 = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getText();
    return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname).withMiddlename(middlename).
            withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).
            withAddress(address).withEmail1(email1).withEmail2(email2).withEmail3(email3);
  }

  public String infoFromDetailForm(ContactData contact) {
    initContactDetailsById(contact.getId());
    String allDetails = wd.findElement(By.id("content")).getText();
    return allDetails;
  }

  private void initContactModificationById(int id) {
    /*WebElement checkbox= wd.findElement(By.cssSelector(String.format("input[value='%s']",id)));
    WebElement row= checkbox.findElement(By.xpath("./../.."));
    List <WebElement> cells= row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click(); */
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }


  public void initContactDetailsById(int id) {
    /*WebElement checkbox= wd.findElement(By.cssSelector(String.format("input[value='%s']",id)));
    WebElement row= checkbox.findElement(By.xpath("./../.."));
    List <WebElement> cells= row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click(); */
    wd.findElement(By.cssSelector(String.format("a[href='view.php?id=%s']", id))).click();

  }


  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }


  public void toGroup(ContactData contact, GroupData group) {
    selectContactById(contact.getId());
    new Select(wd.findElement(By.name("to_group"))).selectByVisibleText
            (group.getName());

    click(By.name("add"));

  }

  public void findContactintoGroup(GroupData group) {

    new Select(wd.findElement(By.name("group"))).selectByVisibleText
            (group.getName());

    contactCache=null;

    }



  public void remove(ContactData contact) {

    selectContactById(contact.getId());
    wd.findElement (By.cssSelector("input[name='remove']")).click();
    returnContactPage();
    findContactintoGroup(new GroupData().withName("[all]"));

  }


  public void formContactsPlusGroupsfromUI(Contacts after) {
    for (ContactData contact : after) {
      String[] namegroup = verifyGroup(contact);
      if (namegroup != null) {
        for (int i = 0; i < namegroup.length; i++) {
         GroupData group = new GroupData().withName(namegroup[i].trim());
          contact = contact.inGroup(group);
        }
      }


    }
  }
    public String[] verifyGroup (ContactData contact) {
    returnContactPage();
    String details=infoFromDetailForm( contact );
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

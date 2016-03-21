package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.List;


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
    type(By.name("email"), contactData.getEmail());
    if (creation) {
      if (contactData.getGroup()!=null)
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText
              (contactData.getGroup());
    }
    else Assert.assertFalse(isElementPresent(By.name("new_group")));

  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }


  public void deleteContact() {
    click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));
    if (isAlertPresent())
      wd.switchTo().alert().accept();
    contactCache=null;

  }

  public void choiceContact(int index) {

    wd.findElements(By.name("selected[]")).get(index).click();
    //click(By.name("selected[]"));


  }
  public boolean isThereAContact()

  {
    return isElementPresent(By.name("selected[]"));
  }

  public void createContact(ContactData contact) {
    initContactCreation();
    fillContactForm(contact,true);
    submitContactCreation();
    contactCache=null;
    returnContactPage();
  }

  public void returnContactPage() {

    if(isElementPresent(By.id("maintable"))) return;
    click(By.linkText("home"));
  }

  public void initContactModification(int index){
    //driver.findElement(By.cssSelector("input[type=’submit’][value=’Subscribe’]"));
    wd.findElements (By.cssSelector("img[alt='Edit']")).get(index).click();
    //click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
  }

  public void updateContact() {
    click(By.name("update"));
    //click(By.xpath("//div[@id='content']/form[1]/input[22]"));
    contactCache=null;
  }


  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      String firstname = element.findElements(By.tagName("td")).get(2).getText();
      String lastname = element.findElements(By.tagName("td")).get(1).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
      ContactData contact = new ContactData(id, firstname, lastname, null,null,null,null);
      contacts.add(contact);

    }
    return contacts;

  }

  private Contacts contactCache=null;

  public  Contacts all() {   //Set<GroupData> all() {
    if (contactCache !=null) return new Contacts(contactCache);
    contactCache=new Contacts();
    //Groups groups = new Groups();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      String firstname = element.findElements(By.tagName("td")).get(2).getText();
      String lastname = element.findElements(By.tagName("td")).get(1).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
      ContactData contact = new ContactData(id, firstname, lastname, null,null,null,null);
      contactCache.add(contact);

    }
    return new Contacts(contactCache);
  }

  public ContactData infoFromEditForm(ContactData contact ) {
      initContactModificationById(contact.getId());
      String firstname= wd.findElement(By.name("firstname")).getAttribute("value");
      String lastname= wd.findElement(By.name("lastname")).getAttribute("value");
      String home=wd.findElement(By.name("home")).getAttribute("value");
      String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
      String work = wd.findElement(By.name("work")).getAttribute("value");
       return new ContactData().withId(contact.getId()).withFirstame(firstname).withLastname(lastname).
               withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
  }

  private void initContactModificationById(int id){
    /*WebElement checkbox= wd.findElement(By.cssSelector(String.format("input[value='%s']",id)));
    WebElement row= checkbox.findElement(By.xpath("./../.."));
    List <WebElement> cells= row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click(); */
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']",id))).click();
  }
}

package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

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
    if (creation)
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText
              (contactData.getGroup());
    else Assert.assertFalse(isElementPresent(By.name("new_group")));

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
  public boolean isThereAContact()

  {
    return isElementPresent(By.name("selected[]"));
  }

  public void createContact(ContactData contact) {
    initContactCreation();
    fillContactForm(contact,true);
    submitContactCreation();
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
    click(By.xpath("//div[@id='content']/form[1]/input[22]"));
  }


  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      String firstname = element.findElements(By.tagName("td")).get(1).getText();
      String lastname = element.findElements(By.tagName("td")).get(2).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
      ContactData contact = new ContactData(id, firstname, lastname, null,null,null,null);
      contacts.add(contact);

    }
    return contacts;

  }

}

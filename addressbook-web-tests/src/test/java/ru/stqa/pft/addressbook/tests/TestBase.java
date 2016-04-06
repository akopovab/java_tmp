package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created by eee on 28.02.2016.
 */
public class TestBase  {

    Logger logger= LoggerFactory.getLogger(TestBase.class);
    // protected final ApplicationManager app = new ApplicationManager(BrowserType.FIREFOX);
  protected final static ApplicationManager app = new ApplicationManager
         (System.getProperty("browser",BrowserType.CHROME));

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }


  @AfterSuite
  public void tearDown() {
    app.stop();
  }



@BeforeMethod(alwaysRun = true)
public void logTestStart(Method m, Object[]p){

    logger.info ("Start test" + m.getName()+ " with parameters " + Arrays.asList(p));

}

 @AfterMethod(alwaysRun = true)
 public void logTestStop(Method m){

     logger.info ("Stop test"+ m.getName());

 }

  public void verifyGroupListInUI() {
    if (Boolean.getBoolean("verifyUI")) { // проверка системного параметра,
      // заданного в Edit Configuration теста GroupModification
      // если ДА- сравнение выполнять, НЕТ - не выполнять
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().all();
      // Ниже - преобразуем инфо, полученное из БД, чтобы совпадало с инфо из web :
      // убираем все, кроме id и name, т.е. того, что есть на странице в web
      assertThat(uiGroups, equalTo(dbGroups.stream().map((g) -> new GroupData().
              withId(g.getId()).withName(g.getName())).
              collect(Collectors.toSet())));
    }


  }



  public void assertion() {

    Contacts dbContacts = app.db().contacts();
    Contacts uiContacts = app.contact().all();


 //   assertThat(uiContacts,equalTo(dbContacts));

    assertThat(uiContacts,equalTo(dbContacts.stream().map((g) -> new ContactData().
            withId(g.getId()).withFirstname(g.getFirstName()).
            withLastname(g.getLastName()).withGroups(g.getGroups())).
            collect(Collectors.toSet())));



  }



  @DataProvider
  public Iterator<Object[]> validGroupsFromCsv() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>(); // все закомментированное для csv формата урок 6, видео 6
    // list.add(new Object[] {new GroupData().withName("test1").withHeader("header1").withFooter("footer1")} );
    // list.add(new Object[] {new GroupData().withName("test2").withHeader("header2").withFooter("footer2")} );
    //list.add(new Object[] {new GroupData().withName("test3").withHeader("header3").withFooter("footer3")} );
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.csv")));
    String line = reader.readLine();
    while (line != null) {
      String[] split = line.split(";");
      list.add(new Object[]{new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[2])});
      line = reader.readLine();
    }
    return list.iterator();


  }



  @DataProvider
  public Iterator<Object[]> validGroupsFromXml() throws IOException {

    try( BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")))){
      String xml="";
      String line = reader.readLine();
      while (line != null) {
        xml+=line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(GroupData.class);
      List<GroupData> groups=(List<GroupData>)xstream.fromXML(xml);
      return groups.stream().map((q)-> new Object[]{q}).collect(Collectors.toList()).iterator();
    }

     }

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.json")));
    String json="";
    String line = reader.readLine();
    while (line != null) {
      json+=line;
      line = reader.readLine();
    }
    Gson gson= new Gson();
    List<GroupData> groups= gson.fromJson(json, new TypeToken<List<GroupData>>()
    {}.getType());//List<GroupData>.classj
    return groups.stream().map((q)-> new Object[]{q}).collect(Collectors.toList()).iterator();

  }



  @DataProvider
  public Iterator<Object[]> validContacsFromCsv() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>(); //  для csv формата урок 6, видео 6
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
    String line = reader.readLine();
    while (line != null) {
      String[] split = line.split(";");
      list.add(new Object[]{new ContactData().withFirstname(split[0]).withLastname(split[1]).withAddress(split[2])});
      line = reader.readLine();
    }
    return list.iterator();


  }



}
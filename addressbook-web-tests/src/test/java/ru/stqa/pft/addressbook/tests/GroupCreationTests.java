package ru.stqa.pft.addressbook.tests;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

 // Logger logger= LoggerFactory.getLogger(GroupCreationTests.class); перенесли в TestBase

/*  @DataProvider
  public Iterator<Object[]> validGroupsFromCsv() throws IOException {
     List<Object[]> list = new ArrayList<Object[]>(); // все закомментированное  для csv формата урок 6, видео 6
     перенесено в TestBase
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
  */

  @Test(dataProvider = "validGroupsFromJson")
  public void testGroupCreation(GroupData group) {

    //  logger.info ("Start test testGroupCreation");

      /*String[] names = new String[]{"test1", "test2", "test3"};
      GroupData group = new GroupData().withName(name).withHeader(header).
              withFooter(footer); */
    app.goTo().groupPage();
    // List<GroupData> before= app.group().list(); работа со списками
    // Set<GroupData> before = app.group().all();
    Groups before = app.group().all();
    // int before =app.getGroupHelper().count();
    app.group().create(group);
    // int after =app.getGroupHelper().count();
    //Set<GroupData> after = app.group().all();

    assertThat(app.group().count(), equalTo(before.size() + 1));
    Groups after = app.group().all();
    /*    int max=0;
        for (GroupData g:after){
            if (g.getId()>max) max=g.getId();
        } */
       /* Comparator<? super GroupData> byId=new Comparator<GroupData>() {
            @Override
            public int compare(GroupData o1, GroupData o2) {
                return Integer.compare(o1.getId(),o2.getId());
            }
        }; */
    // Comparator<? super GroupData> byId= (o1, o2) -> Integer.compare(o1.getId(),o2.getId()); -сравнение множеств
    //int max1= after.stream().max(byId).get().getId();
        /*int max1= after.stream().max((o1, o2) -> Integer.compare(o1.getId(),o2.getId())).get().getId();
        group.setId(max1); */
    // group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(),o2.getId())).get().getId());
    // group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    // before.add(group);
       /* Comparator<? super GroupData> byId=(g1,g2)->Integer.compare(g1.getId(),g2.getId()); // упорядочение нами объектов(списков)
        before.sort(byId);
        after.sort(byId); */
    // Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object> (after)); -сравнение множеств
    //assertEquals(before, after); // сравнение нами упорядоченных объектов(списков)
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().
                    mapToInt((g) -> g.getId()).max().getAsInt()))));

      //logger.info ("Stop test testGroupCreation");


  }

  @Test(enabled = true)
  public void testBadGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("aaa2'");
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }

}




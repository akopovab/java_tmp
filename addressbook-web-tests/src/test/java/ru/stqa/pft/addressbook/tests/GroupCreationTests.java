package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.goTo().groupPage();
    // List<GroupData> before= app.group().list(); работа со списками
    // Set<GroupData> before = app.group().all();
    Groups before = app.group().all();
    // int before =app.getGroupHelper().count();
    GroupData group = new GroupData().withName("aaa2");
    app.group().create(group);
    // int after =app.getGroupHelper().count();
    //Set<GroupData> after = app.group().all();

    assertThat(app.group().count(), equalToObject(before.size() + 1));
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
  }


  @Test
  public void testBadGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group = new GroupData().withName("aaa2'");
    assertThat(app.group().count(), equalToObject(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
  }

}




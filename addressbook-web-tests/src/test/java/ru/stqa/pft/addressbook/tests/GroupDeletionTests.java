package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.*;


public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size() == 0)
      //  if (app.group().list().size()==0)                     //(!app.group().isThereAGroup()) {
      app.group().create(new GroupData().withName("test1"));
  }


  @Test
  public void testGroupDeletion() {

    //Set<GroupData> before= app.group().all();
    Groups before = app.group().all();
    GroupData deletedGroup = before.iterator().next();
    //int index= before.size()-1;
    app.group().delete(deletedGroup);
    //Set<GroupData> after= app.group().all();
    assertEquals(app.group().count(), before.size() - 1);
    Groups after = app.group().all();
    //before.remove(deletedGroup);
    assertThat(after, equalTo( before.withOut(deletedGroup)));

    //Assert.assertEquals(before, after);

  }


}

package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getNavigationHelper().gotoGroupPage();
        List<GroupData> before= app.getGroupHelper().getGroupList();
       // int before =app.getGroupHelper().getGroupCount();
        GroupData group =new GroupData("test125", "test13", "test4");
        app.getGroupHelper().createGroup(group);
       // int after =app.getGroupHelper().getGroupCount();
        List<GroupData> after= app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(),before.size()+1);
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
        before.add(group);
        Comparator<? super GroupData> byId=(g1,g2)->Integer.compare(g1.getId(),g2.getId()); // упорядочение нами объектов(списков)
        before.sort(byId);
        after.sort(byId);
       // Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object> (after)); -сравнение множеств
        Assert.assertEquals(before, after); // сравнение нами упорядоченных объектов(списков)
    }

}

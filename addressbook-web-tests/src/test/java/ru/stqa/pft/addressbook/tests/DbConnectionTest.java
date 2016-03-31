package ru.stqa.pft.addressbook.tests;

import org.junit.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.*;

/**
 * Created by 3 on 31.03.2016.
 */
public class DbConnectionTest {

    @Test
    public void testDbConnection(){

        Connection conn = null;

        try {
            conn =  DriverManager.getConnection("jdbc:mysql://localhost/addressbook?user=root&password=");
            Statement st=conn.createStatement();
            ResultSet rs = st.executeQuery("select group_id, group_name, group_header,group_footer from group_list");
            Groups groups= new Groups();
            while(rs.next()){
                groups.add(new GroupData().withId(rs.getInt("group_id")).withName(rs.getString("group_name")).withHeader(rs.getString("group_header")).
                        withFooter(rs.getString("group_footer"))); //извлекаем данные из столбцов таблицы
            }

            rs.close();
            st.close();
            conn.close();
            System.out.println(groups);




        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }
}

package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dynamicList {


    String dbUrl = "jdbc:oracle:thin:@100.27.15.76:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void metaData2() throws SQLException {
        //create the connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
       /* ResultSet resultSet = statement.executeQuery("select first_name, last_name, salary, job_id\n" +
                "from employees\n" +
                "where rownum<6");
        */
        ResultSet resultSet = statement.executeQuery("select * from employees");
        //get the resultset object metadata
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        //main List
        List<Map<String, Object>> queryData = new ArrayList<>();

        //loop through each row
        while(resultSet.next()){

            Map<String, Object> row= new HashMap<>();

            //some code to get values dynamically

            for(int i=1; i<=rsMetaData.getColumnCount(); i++){
                row.put(rsMetaData.getColumnName(i), resultSet.getString(i));
            }
            queryData.add(row);
        }

        //print list one by one, print result
        for (Map<String, Object> row: queryData) {
            System.out.println(row.toString());

        }

        //closing all connections
        resultSet.close();
        statement.close();
        connection.close();
    }
}

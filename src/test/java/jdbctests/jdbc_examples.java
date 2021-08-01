package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;

public class jdbc_examples {

    String dbUrl = "jdbc:oracle:thin:@100.27.15.76:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void test1() throws SQLException {

        //create the connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        Statement statement = connection.createStatement();
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from regions");

        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }


        resultSet = statement.executeQuery("select * from departments");

        while (resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        //closing all connections
        resultSet.close();
        statement.close();
        connection.close();

    }

    @Test
    public void CountAndNavigate() throws SQLException {
        //create the connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from regions");

        //how to find how many rows we have for the query
        //go to last row
        resultSet.last();
        //get the row count
        int rowCount = resultSet.getRow();
        System.out.println("rowCount = " +rowCount);

        resultSet = statement.executeQuery("select * from departments");
        resultSet.last();
        rowCount = resultSet.getRow();
        System.out.println("rowCount = " + rowCount);

        //we need to move before first row to get full list since we are at the last row right now
        resultSet.beforeFirst();
        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }


        //closing all connections
        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void metaData() throws SQLException {
        //create the connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from departments");

        //get the database related data inside the dbMetaData object
        DatabaseMetaData dbMetaData = connection.getMetaData();

        System.out.println("User : "+ dbMetaData.getUserName());
        System.out.println("Database Product Name = " + dbMetaData.getDatabaseProductName());
        System.out.println("Database Product Version = " + dbMetaData.getDatabaseProductVersion());
        System.out.println("Driver Name = " + dbMetaData.getDriverName());
        System.out.println("Driver Version = " + dbMetaData.getDriverVersion());

        //get the resultset object metadata
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        //how many columns we have?
        System.out.println("Column Count = " + rsMetaData.getColumnCount());

        //column names
        System.out.println("Column Name(2) = " + rsMetaData.getColumnName(2));

        //print all the column names dynamically
        for (int i = 1; i<=rsMetaData.getColumnCount();i++){
            System.out.println("Column " + i + " Name is = " + rsMetaData.getColumnName(i));
        }



        //closing all connections
        resultSet.close();
        statement.close();
        connection.close();
    }

}

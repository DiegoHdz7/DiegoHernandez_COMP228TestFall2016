import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnection  {
    static final String DRIVER = "oracle.jdbc.OracleDriver";
    static final String DATABASE_URL = "jdbc:oracle:thin:@199.212.26.208:1521:SQLD";
    static final String USERNAME = "COMP228_F21_015_7";
    static final String PASSWORD = "password";

    static private ResultSet resultSet;
    static private Connection connection;
    static private Statement statement;
    static private PreparedStatement preparedStatement;

    public DbConnection(){
        open();
    }



    public  void open(){
        try{
            // load the driver class
            Class.forName( DRIVER );
            // establish connection to database
            connection = DriverManager.getConnection( DATABASE_URL, USERNAME, PASSWORD );

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("----------------- TRY TO OPEN EXCEPTION---------------");
            e.printStackTrace();
            System.out.println( e.getMessage());
        }

    }

    public  void close(){
        try{
            resultSet.close();
            preparedStatement.close();
        }
        catch( SQLException e) {
            System.out.println("Database close failed");
            System.out.println(e.toString());
        }


    }


    public ArrayList<String> getRows(String city){
        String query = "select * from Students where city=?";
        ArrayList<String> rows = new ArrayList<>();
        String row;
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,city);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                row =   resultSet.getString("studentID")+"\t"+
                        resultSet.getString("firstName")+" "+
                        resultSet.getString("lastName")+"\t\t"+
                        resultSet.getString("address")+"\t"+
                        resultSet.getString("city")+"\t"+
                        resultSet.getString("province")+"\t"+
                        resultSet.getString("postalCode");
                rows.add(row);

            }

            close();

        }
        catch (SQLException e){
            System.out.println("Select statement has failed");
            System.out.println(e.toString());
        }

        return rows;

    }

    public ArrayList<String> getCities(){
        ArrayList<String> cities = new ArrayList<>();
        String row;
        String query = "select distinct city from students";
        try{
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

                row =   resultSet.getString("city");
                cities.add(row);

            }

            close();

        }
        catch (SQLException e){
            System.out.println("Select statement has failed");
            System.out.println(e.toString());
        }



        return cities;
    }
}

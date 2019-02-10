package database.models;

import java.sql.*;

public class Driver {

    private static final String JDBC_MYSQL_LOCALHOST_3306_FAOS = "jdbc:mysql://127.0.0.1:3306/chat";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String TIMEZONE = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private String foo="";

    private Connection myConnection;
    private Statement statement;
    private ResultSet results;


//    public void getMealList() {
//        try {
//
//            myConnection = DriverManager.getConnection(JDBC_MYSQL_LOCALHOST_3306_FAOS + TIMEZONE, USER, PASSWORD);
//            statement = myConnection.createStatement();
//            results = statement.executeQuery(" SELECT * FROM produkt");
//            while (results.next()) {
//
//                foo = foo +
//                        results.getString("id_kat_prod") + "-#-" +
//                        results.getString("id_produktu") + "-#-" +
//                        results.getString("nazwa") + "-#-" +
//                        results.getString("cena_szt") + "-#-" +
//                        results.getString("waga") + "-#-" +
//                        results.getString("file_path") + "--";
//
//            }
//            System.out.println(foo);
//
//            myConnection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        }
////        System.out.println(foo);
//    }

    public void saveMessage(String splited){
        String[] splitedArray = null;

        splitedArray = splited.split(", ");

        try {
            myConnection = DriverManager.getConnection(JDBC_MYSQL_LOCALHOST_3306_FAOS + TIMEZONE, USER, PASSWORD);

            statement = myConnection.createStatement();
            String query = " insert into wiadomosci (uzytkownik, message)"
                    + " values (?, ?)";

            PreparedStatement preparedStmt = myConnection.prepareStatement(query);
            preparedStmt.setString (1, splitedArray[0]);
            preparedStmt.setString (2, splitedArray[1]);

            preparedStmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Zapytanie nie wykonane! " + e.getMessage() + ": " + e.getErrorCode());
        }


    }



    public String getFoo() {
        return foo;
    }

}




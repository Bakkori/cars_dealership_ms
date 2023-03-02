package db_interface_cars;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class DB_interface_cars {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // set driver
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Scanner input = new Scanner(System.in);
        // crate connection
        try {
            // get DB name & pass from environment variables
            String username = System.getenv("oracle_name");
            String pass = System.getenv("oracle_pass");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", username, pass);
            // create statement object
            Statement statement = connection.createStatement();
                       
            System.out.println("*** WELCOME TO X Car Dealerships MANAGMENT SYSTEM ***");
            System.out.println("-------------------------------------------------------");
            System.out.println("Select Table To SHOW/EDIT");
            System.out.println("[1] - Cars");
            System.out.print("\nPlease enter your choice: ");
            int userInput = input.nextInt();
            System.out.println("");
            if (userInput == 1) {
                System.out.println("[1] - Show Record");
                System.out.println("[2] - Update Record");
                System.out.println("[3] - Add Record");
                System.out.println("[4] - Delete Record");
                System.out.print("Please enter your choice: ");
                userInput = input.nextInt();
                switch (userInput) {
                    case 1:
                        showCars(statement);
                        break;

                    case 2:
                        showCars(statement);
                        updateCars(input, statement);
                        showCars(statement);
                        break;
                    case 3:
                        showCars(statement);
                        addCars(input, connection, statement);
                        showCars(statement);
                        break;
                    case  4:
                        showCars(statement);
                        deleteCars(input, connection, statement);
                        showCars(statement);
                    default:
                        break;
                }
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    public static void showCars(Statement statement) throws SQLException{
    ResultSet rs = statement.executeQuery("select * from cars");
                    System.out.println("----------------------------");
                    int i = 0; // for counting
                    while (rs.next()) {
                    // set column
                    int db_car_id = rs.getInt("car_id");
                    int db_car_price = rs.getInt("price");
                    int db_car_model = rs.getInt("model_id");
                    System.out.println("CAR_ID: " + db_car_id);
                    System.out.println("CAR PRICE: " + db_car_price+"SAR");
                    System.out.println("CAR MODEL ID: " + db_car_model);
                    System.out.println("----------------------------");

                }
    }
    
    public static void updateCars(Scanner input, Statement statement) throws SQLException{
        System.out.println("Write The car ID to update:");
        int input_car_id = input.nextInt();
        System.out.println("Write The new value for the price:");
        int input_new_value = input.nextInt();
        statement.executeUpdate("UPDATE cars set price = "+input_new_value+" where car_id = "+input_car_id);
        System.out.println("Record has been updated");
    }
    
    public static void addCars(Scanner input, Connection connection, Statement statement) throws SQLException{
        System.out.println("Write new car ID to INSERT:");
        int input_car_id = input.nextInt();
        System.out.println("Write car PRICE:");
        int input_car_price = input.nextInt();
        System.out.println("----------------------------");
        showmodels(statement);
        System.out.print("Write model_id: ");
        
        int input_model_id = input.nextInt();
        connection.prepareStatement("insert into cars values("+input_car_id+", "+input_car_price+","+input_model_id+")").executeUpdate();
        System.out.println("Record has been added");
        
    }
    
    public static void deleteCars(Scanner input, Connection connection, Statement statement) throws SQLException{
        System.out.println("Write car ID to DELETE:");
        int input_car_id = input.nextInt();
        connection.prepareStatement("DELETE from cars where car_id = "+input_car_id).executeUpdate();
        System.out.println("Record has been deleted");
    }
    
        public static void showmodels(Statement statement) throws SQLException{
    ResultSet rs = statement.executeQuery("select * from models");
                    System.out.println("----------------------------");
                    while (rs.next()) {
                    // set column
                    int db_model_id = rs.getInt("model_id");
                    String db_model_name = rs.getString("model_name");
                    System.out.print("MODEL_ID: " + db_model_id+" ");
                    System.out.print("MODEL NAME: " + db_model_name+" ");
                    System.out.println("");

                }
    }

}

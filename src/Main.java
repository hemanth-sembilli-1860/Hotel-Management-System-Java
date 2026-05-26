import java.sql.*;
import java.util.*;

class HotelManagementSystem {
    private static final String username = "your_name";
    private static final String password = "your_password";
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";

    public static void main(String[] args) throws SQLException,ClassNotFoundException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException c){
            System.out.println(c.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.println();
                System.out.println("Hotel Management System");
                System.out.println("1.Reserve a Room");
                System.out.println("2.View Reservations");
                System.out.println("3.Get Room Number");
                System.out.println("4.Update Reservation Details");
                System.out.println("5.Delete Reservations");
                System.out.println("0:Exit");
                System.out.println("Choose an option::");
                int choice  = scanner.nextInt();
                switch (choice){
                    case 1:
                        reserveRoom(connection,scanner);
                        break;
                    case 2:
                        viewReservation(connection);
                        break;
                    case 3:
                        GetRoomNumber(connection,scanner);
                        break;
                    case 4:
                        UpdateReservationDetails(connection,scanner);
                        break;
                    case 5:
                        RemoveReservation(connection,scanner);
                        break;
                    case 0:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid Operation.Try Again and Choose Appropriate Option.");
                }
            }
        }
        catch (SQLException s){
            System.out.println(s.getMessage());
        }
        catch (InterruptedException i){
            throw new RuntimeException(i);
        }
    }
    public static void reserveRoom(Connection connection,Scanner scanner){
        try {
            System.out.println("Enter Guest Name::");
            String guest_name = scanner.next();
            System.out.println("Enter Room Number::");
            int roomNumber = scanner.nextInt();
            System.out.println("Enter Contact Number::");
            String contactNumber = scanner.next();

            String query = "INSERT INTO reservations (guest_name, room_number, contact_number) " +
                    "VALUES ('" + guest_name + "', " + roomNumber + ", '" + contactNumber + "')";

            try (Statement statement = connection.createStatement()) {
                int affected_rows = statement.executeUpdate(query);
                if (affected_rows > 0) {
                    System.out.println(affected_rows + "row(s) affected");
                    System.out.println("Registration Successful");
                } else {
                    System.out.println("Registration failed.");
                }
            }
        }
        catch (SQLException s){
            s.printStackTrace();
        }
    }
    public static void UpdateReservationDetails(Connection connection,Scanner scanner){
        try {
            System.out.println("Enter Reservation ID to Update Reservation Details::");
            int reservation_id = scanner.nextInt();
            scanner.nextLine();
            if (!ReservationExists(connection,reservation_id)){
                System.out.println("Reservation Details with reservation_id "+ reservation_id + " doesn't exists");
                return;
            }
            System.out.println("Enter New Guest Name::");
            String guest_name = scanner.next();
            scanner.next();
            System.out.println("Enter New Room Number::");
            int room_number = scanner.nextInt();
            System.out.println("Enter Contact Number::");
            String contact_number = scanner.next();

            String query = "UPDATE reservations SET guest_name = '" + guest_name +
                    "', room_number = " + room_number +
                    ", contact_number = '" + contact_number +
                    "' WHERE reservation_id = " + reservation_id;
             try (Statement statement = connection.createStatement()){
                 int affected_rows = statement.executeUpdate(query);
                 if (affected_rows > 0) {
                     System.out.println(affected_rows + "row(s) affected");
                     System.out.println("Updation Successfull");
                 } else {
                     System.out.println("Updation failed.");
                 }
             }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void viewReservation(Connection connection){
        try {
            String query = "SELECT reservation_id,guest_name,room_number,contact_number,reservation_date FROM reservations";

            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                System.out.println("Current Reservations::");
                System.out.println("+----------------+-----------------+-------------+------------------+---------------------+");
                System.out.println("| Reservation ID | Guest Name      | Room Number | Contact Number   | Reservation Date    |");
                System.out.println("+----------------+-----------------+-------------+------------------+---------------------+");
                while (rs.next()){
                    int reservation_id = rs.getInt("reservation_id");
                    String guest_name = rs.getString("guest_name");
                    int room_number = rs.getInt("room_number");
                    String contact_number = rs.getString("contact_number");
                    String reservation_date = rs.getTimestamp("reservation_date").toString();

                    System.out.printf("| %-14d | %-15s | %-11d | %-16s | %-19s |\n",
                            reservation_id,
                            guest_name,
                            room_number,
                            contact_number,
                            reservation_date);
                    System.out.println();
                }
                System.out.println("+----------------+-----------------+-------------+------------------+---------------------+");
            }
        }
        catch (SQLException s){
            s.printStackTrace();
        }
    }
    public static void GetRoomNumber(Connection connection,Scanner scanner){
        try {
            System.out.println("Enter Reservation_id to Get room Number::");
            int reservation_id  = scanner.nextInt();
            System.out.println("Enter Guest Name::");
            String guest_name = scanner.next();
            String query = "SELECT room_number FROM reservations WHERE reservation_id = "
                    + reservation_id +
                    " AND guest_name = '" + guest_name + "'";
            try (Statement statement = connection.createStatement()){
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()){
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.println("Room Number for the Guest " + guest_name + " with reservation id " + reservation_id + " is " + roomNumber);
                }
                else {
                    System.out.println("No Results found for Given Reservation ID and Guest Name.");
                }
            }
        }
        catch (SQLException q){
            q.printStackTrace();
        }
    }
    public static boolean ReservationExists(Connection connection,int reservation_id){
        try {
            String query = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservation_id;
            try(Statement statement = connection.createStatement()){
                ResultSet resultSet = statement.executeQuery(query);
                return resultSet.next();
            }
        }
        catch (SQLException s){
            s.printStackTrace();
            return false;
        }
    }
    public static void RemoveReservation(Connection connection,Scanner scanner){
        try {
            System.out.println("Enter Reservation ID to Delete::");
            int reservation_id = scanner.nextInt();
            scanner.nextLine();
            if (!ReservationExists(connection,reservation_id)){
                System.out.println("Reservation Details with reservation_id "+ reservation_id + " doesnt exists");
                return;
            }

            String query = "DELETE FROM reservations WHERE reservation_id = " + reservation_id;
            try (Statement statement = connection.createStatement()){
                int affected_rows = statement.executeUpdate(query);
                if (affected_rows > 0) {
                    System.out.println(affected_rows + "row(s) affected");
                    System.out.println("Deletion Successfull");
                } else {
                    System.out.println("Deletion failed.");
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void exit() throws  InterruptedException{
        System.out.println("Exiting the System");
        int i = 5;
        while (i!=0){
            System.out.println(" . ");
            Thread.sleep(450);
            i--;
        }
        System.out.println();
        System.out.println("Thank You for using Hotel Management System.");
    }
}
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class HotelDataBase {

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();
    private ArrayList<Payment> payments = new ArrayList<>();

    //This method connects the program to the database of the hotel.
    private static Connection connect(){

        Connection con = null;

        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:HotelDataBase.db");
        }catch (ClassNotFoundException | SQLException e){
            System.out.println("Connection to database failed!");
            System.out.println(e +" ");
        }

        return con;
    }

    //This method reads all the data from the tables in base.
    public void readAllData(){
        readTable("User");
        readTable("Customer");
        readTable("Room");
        readTable("Booking");
        readTable("Payment");
    }

    //This method reads the data by a specific table in base, given the table name.
    private void readTable(String tableName){

        Connection con = connect();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            //SQL Query.
            String sqlQuery = "SELECT * FROM " + tableName;
            ps = con.prepareStatement(sqlQuery);
            rs = ps.executeQuery();

            while(rs.next()){

                if (tableName.equals("User")){

                    User user = new User();

                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setUserRole(rs.getInt("userRole"));

                    users.add(user);

                }else if(tableName.equals("Customer")){

                    Customer customer = new Customer();

                    customer.setUid(rs.getInt("uid"));
                    customer.setName(rs.getString("name"));
                    customer.setEmail(rs.getString("email"));

                    customers.add(customer);

                }else if (tableName.equals("Room")){

                    Room room = new Room();

                    room.setId(rs.getInt("id"));
                    room.setRoomType(rs.getString("roomType"));
                    room.setRoomPrice(rs.getInt("roomPrice"));

                    rooms.add(room);

                }else if (tableName.equals("Booking")){

                    Booking booking = new Booking();

                    booking.setId(rs.getInt("id"));
                    booking.setCustomerID(rs.getInt("customerID"));
                    booking.setRoomID(rs.getInt("roomID"));
                    booking.setStartDay(rs.getInt("startDay"));
                    booking.setEndDay(rs.getInt("endDay"));
                    booking.setStartMonth(rs.getInt("startMonth"));
                    booking.setEndMonth(rs.getInt("endMonth"));
                    booking.setBookingSeason(rs.getInt("bookingSeason"));
                    booking.setBookingYear(rs.getInt("bookingYear"));

                    bookings.add(booking);

                }else if (tableName.equals("Payment")){

                    Payment payment = new Payment();

                    payment.setId(rs.getInt("id"));
                    payment.setBookingID(rs.getInt("bookingID"));
                    payment.setMethod(rs.getInt("method"));
                    payment.setFinalAmount(rs.getInt("finalAmount"));
                    payment.setPaymentDay(rs.getInt("paymentDay"));
                    payment.setPaymentMonth(rs.getInt("paymentMonth"));
                    payment.setPaymentYear(rs.getInt("paymentYear"));

                    payments.add(payment);
                }
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }finally {
            try{
                rs.close();
                ps.close();
                con.close();
            }catch (SQLException e){
                System.out.println(e.toString());
            }
        }
    }

    //This method inserts a new customers record to the hotel base.
    private void insertNewCustomer(HotelDataBase db){

        Connection con = connect();
        PreparedStatement ps = null;

        try{
            //SQL Query.
            String sqlQuery = "INSERT INTO Customer (uid, name, email) VALUES (?,?,?)";
            ps = con.prepareStatement(sqlQuery);

            //Create the new customer record.
            Customer customer = new Customer();
            Scanner input = new Scanner(System.in);
            System.out.print("First name: ");
            String firstName = input.nextLine();
            System.out.print("Last name: ");
            String lastName = input.nextLine();

            //UID of new customer record.
            int newUID = customers.get(customers.size()-1).getUid() + 1;
            customer.setUid(newUID);

            //Name of new customer record.
            String newName = firstName + " " + lastName;
            customer.setName(newName);

            //Email of new customer record.
            int validEmail = 0;
            String newEmail = "";
            while(true){

                if(validEmail == 1){
                    break;
                }
                System.out.print("Email: ");
                newEmail = input.nextLine();

                if(isEmailValid(newEmail) && !isEmailExists(newEmail)){
                    validEmail = 1;
                }else{
                    validEmail = 0;
                    System.out.println("Email is not valid or already exists, please try again!");
                }
            }
            customer.setEmail(newEmail);

            //Create & insert the new customer record to the base.
            ps.setInt(1, newUID);
            ps.setString(2, newName);
            ps.setString(3, newEmail);
            ps.execute();

            //Add the new customer to the list.
            customers.add(customer);

            System.out.println("New customer created successfully!");
            System.out.println();
            db.userLogin(db);

        }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
            try{
                ps.close();
                con.close();
            }catch (SQLException e){
                System.out.println(e.toString());
            }
        }
    }

    //This method inserts a new booking record in Booking SQL table.
    private void insertNewBooking(HotelDataBase db, int uid){

        Connection con = connect();
        PreparedStatement ps = null;

        try{
            //SQL Query.
            String sqlQuery = "INSERT INTO Booking (id, customerID, roomID, startDay, endDay, startMonth, endMonth, bookingYear, bookingSeason) VALUES (?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sqlQuery);

            //Create the new booking record.
            Booking booking = new Booking();

            //ID of the new booking record.
            int newID = bookings.get(bookings.size()-1).getId() + 1;
            booking.setId(newID);

            //Set the customer ID of this new booking.
            booking.setCustomerID(uid);

            //Get the start, end date and room's type for this new booking from the customer.
            Scanner input = new Scanner(System.in);
            System.out.print("Start day: ");
            int startDay = input.nextInt();
            System.out.print("End day: ");
            int endDay = input.nextInt();
            System.out.print("Month: ");
            int month = input.nextInt();
            System.out.print("Room Type (Single, Double or Suite): ");
            String type = input.next();

            //Set the room ID for this new booking record, first check for available rooms in this period.
            int roomID = showAvailableRooms(startDay, endDay, month, type);
            booking.setRoomID(roomID);

            //Set the start-end date & year for this new booking record.
            booking.setStartDay(startDay);
            booking.setEndDay(endDay);
            booking.setStartMonth(month);
            booking.setEndMonth(month);
            booking.setBookingYear(2021);

            //Set the booking season value for this new booking record.
            int bookingSeason = 0;
            if(month >= 10 && month <= 12){
                bookingSeason = 0;
            }else if(month >= 1 && month <= 4){
                bookingSeason = 1;
            }else if(month >= 5 && month <= 9){
                bookingSeason = 2;
            }
            booking.setBookingSeason(bookingSeason);

            //Create & insert the new booking record to the base.
            ps.setInt(1, newID);
            ps.setInt(2, uid);
            ps.setInt(3, roomID);
            ps.setInt(4, startDay);
            ps.setInt(5, endDay);
            ps.setInt(6, month);
            ps.setInt(7, month);
            ps.setInt(8, 2021);
            ps.setInt(9, bookingSeason);
            ps.execute();

            //Add the new booking record to the list.
            bookings.add(booking);

            System.out.println("New booking record created successfully!");
            System.out.println();
            db.userLogin(db);

        }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
            try{
                ps.close();
                con.close();
            }catch (SQLException e){
                System.out.println(e.toString());
            }
        }
    }

    //This method inserts the paid booking by a customer in Payment SQL table.
    private void insertNewPayment(HotelDataBase db, int bookPos, int bookID, int payMethod, int finalAmount){

        Connection con = connect();
        PreparedStatement ps = null;

        try{
            //SQL Query.
            String sqlQuery = "INSERT INTO Payment (id, bookingID, method, finalAmount, paymentDay, paymentMonth, paymentYear) VALUES (?,?,?,?,?,?,?)";
            ps = con.prepareStatement(sqlQuery);

            //Create the new payment record.
            Payment payment = new Payment();

            //Set ID of the new payment record.
            int newID = payments.get(payments.size()-1).getId() + 1;
            payment.setId(newID);

            //Set the booking ID of the new payment record.
            payment.setBookingID(bookID);

            //Set the method of this payment record.
            payment.setMethod(payMethod);

            //Set the final paid amount of this payment record.
            if (payMethod == 1){
                payment.setFinalAmount(finalAmount - 20);
            }else{
                payment.setFinalAmount(finalAmount);
            }

            //Set the payment day of this record.
            payment.setPaymentDay(bookings.get(bookPos).getEndDay());

            //Set the payment month of this record.
            payment.setPaymentMonth(bookings.get(bookPos).getEndMonth());

            //Set the payment year of this record.
            payment.setPaymentYear(bookings.get(bookPos).getBookingYear());

            //Create & insert this payment in the base.
            ps.setInt(1, newID);
            ps.setInt(2, bookID);
            ps.setInt(3, payMethod);
            if(payMethod == 1){
                ps.setInt(4, finalAmount-20);
            }else{
                ps.setInt(4, finalAmount);
            }
            ps.setInt(5, bookings.get(bookPos).getEndDay());
            ps.setInt(6, bookings.get(bookPos).getEndMonth());
            ps.setInt(7, bookings.get(bookPos).getBookingYear());
            ps.execute();

            //Add the new payment record to the list.
            payments.add(payment);

            //Delete this current booking from base cause it's paid.
            deletePaidBooking(bookID, bookPos);

            System.out.println();
            System.out.println("Thank you so much! See you soon!");
            System.out.println();
            db.userLogin(db);

        }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
            try{
                ps.close();
                con.close();
            }catch (SQLException e){
                System.out.println(e.toString());
            }
        }
    }

    //This method inserts a new receptionist user in User SQL table, by manager only.
    private void insertNewRecept(HotelDataBase db, String name){

        Connection con = connect();
        PreparedStatement ps = null;
        int number = 0;

        try{
            //SQL Query.
            String sqlQuery = "INSERT INTO User (id, name, email, username, password, userRole) VALUES (?,?,?,?,?,?)";
            ps = con.prepareStatement(sqlQuery);

            //Create the new user record.
            User user = new User();

            //Find the number of the last receptionist user.
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(users.get(users.size() -1).getUsername());
            while(m.find()) {
                number = Integer.parseInt(m.group());
            }

            //Set the id of the new user.
            user.setId(users.get(users.size()-1).getId() + 1);

            //Set the full name of the new user.
            user.setName(name);

            //Set the email of the new user.
            user.setEmail("recept" + (number+1) + "@hotel.com");

            //Set the username & password of the new user.
            user.setUsername("recept" + (number+1));
            user.setPassword("recept" + (number+1));

            //Set the user role.
            user.setUserRole(0);

            //Create & insert this new user in the base.
            ps.setInt(1,users.get(users.size()-1).getId() + 1);
            ps.setString(2, name);
            ps.setString(3, "recept" + (number+1) + "@hotel.com");
            ps.setString(4, "recept" + (number+1));
            ps.setString(5, "recept" + (number+1));
            ps.setInt(6,0);
            ps.execute();

            //Add the new user to the list.
            users.add(user);

            System.out.println();
            System.out.println("New receptionist user created successfully!");
            System.out.println();
            db.userLogin(db);

        }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
            try{
                ps.close();
                con.close();
            }catch (SQLException e){
                System.out.println(e.toString());
            }
        }
    }

    //This method deletes the paid booking in Booking SQL table.
    private void deletePaidBooking(int bookingID, int bookPos){

        Connection con = connect();
        PreparedStatement ps = null;

        try{
            //SQL Query.
            String sqlQuery = "DELETE FROM Booking WHERE id = ?";
            ps = con.prepareStatement(sqlQuery);

            String bookID = String.valueOf(bookingID);
            ps.setString(1, bookID);
            ps.execute();

            //Remove this booking record from the list.
            bookings.remove(bookings.get(bookPos));

        }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
            try{
                ps.close();
                con.close();
            }catch (SQLException e){
                System.out.println(e.toString());
            }
        }

    }

    //This method updates the email of a customer.
    private void updateEmail(HotelDataBase db, int uid){

        Connection con = connect();
        PreparedStatement ps = null;

        try{
            //SQL Query.
            String sqlQuery = "UPDATE Customer SET email = ? WHERE uid = ?";
            ps = con.prepareStatement(sqlQuery);

            //The customer set the new email.
            Scanner input = new Scanner(System.in);
            int validEmail = 0;
            String newEmail = "";
            while(true){

                if(validEmail == 1){
                    break;
                }
                System.out.println();
                System.out.print("Give me your new email: ");
                newEmail = input.nextLine();

                if(isEmailValid(newEmail) && !isEmailExists(newEmail)){
                    validEmail = 1;
                }else{
                    validEmail = 0;
                    System.out.println("Your email is not valid, please try again!");
                }
            }

            //Update & change only the email for this customer's uid.
            ps.setString(1, newEmail);
            ps.setInt(2, uid);
            ps.execute();

            System.out.println("Customer's email updated successfully!");
            System.out.println();
            db.userLogin(db);

        }catch(SQLException e){
            System.out.println(e.toString());
        }finally{
            try{
                ps.close();
                con.close();
            }catch (SQLException e){
                System.out.println(e.toString());
            }
        }
    }

    //This method checks the login credentials of a user.
    public void userLogin(HotelDataBase db){

        Menu menu = new Menu();
        menu.userLoginMenuLabel();

        int userFound = 0;

        while (true){

            if (userFound == 1){
                break;
            }

            Scanner userUsername = new Scanner(System.in);
            Scanner userPassword = new Scanner(System.in);
            System.out.print("Username: ");
            String username = userUsername.nextLine();
            System.out.print("Password: ");
            String password = userPassword.nextLine();

            for (int i = 0 ; i < users.size() ; i++){

                if (users.get(i).getUsername().equals(username)
                        && users.get(i).getPassword().equals(password)){
                    System.out.println("Success user log in!");

                    userFound = 1;

                    if (username.startsWith("m")){
                        menu.managerMenu(db);
                    }else if(username.startsWith("r")){
                        menu.receptionistMenu(db);
                    }
                }
            }

            if (userFound == 0){
                System.out.println("Error! Please try again!");
            }
        }
    }

    //This method checks if customer's UID is valid and accept it.
    public void customerLogin(HotelDataBase db){

        int customerFound = 0;

        Menu menu = new Menu();

        while (true){

            if (customerFound == 1){
                break;
            }

            Scanner input = new Scanner(System.in);
            System.out.print("Please give me your unique ID: ");
            int uid = input.nextInt();

            for (int i = 0 ; i < customers.size() ; i++){
                if (customers.get(i).getUid() == uid){
                    System.out.println("Success customer log in!");
                    customerFound = 1;
                    menu.customerMenu(db,uid);
                }
            }

            if (customerFound == 0){
                System.out.println("Error! Please try again!");
            }
        }
    }

    //This method checks if the email that the new customer gave is in valid format.
    private boolean isEmailValid(String email){

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    //This method checks if the email that the new customer gave is already exists in our db.
    private boolean isEmailExists(String email){

        int emailExists = 0;

        for(int i = 0 ; i < customers.size() ; i++){
            if (customers.get(i).getEmail().equals(email)){
                emailExists = 1;
            }
        }

        if(emailExists == 1){
            return true;
        }else{
            return false;
        }
    }

    //This method checks for available rooms in hotel for a specific date, that customer gave us, show this rooms.
    private int showAvailableRooms(int startDay, int endDay, int month, String type){

        ArrayList<Integer> temp = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        int roomID;

        Menu menu = new Menu();
        menu.availableRoomsLabel();

        for (int i = 0 ; i < rooms.size(); i++){
            temp.add(rooms.get(i).getId());
        }

        for (int i = 0; i < bookings.size(); i++){

            if ((bookings.get(i).getStartDay() >= startDay && bookings.get(i).getEndDay() <= endDay
                    && bookings.get(i).getStartMonth() == month)
                    || (bookings.get(i).getStartDay() <= endDay && bookings.get(i).getEndDay() >= endDay
                    && bookings.get(i).getStartMonth() == month)
                    || (bookings.get(i).getStartDay() <= startDay && bookings.get(i).getEndDay() >= startDay)
                    && bookings.get(i).getStartMonth() == month){
                int thisId = bookings.get(i).getRoomID();
                temp.removeIf(id -> (id == thisId));
            }
        }

        for (int i = 0 ; i < temp.size(); i++){
            for (Room room: rooms){
                if (temp.get(i) == room.getId() && room.getRoomType().equalsIgnoreCase(type)){
                    System.out.println("-----------------");
                    System.out.println("Room # " + (i+1));
                    System.out.println("-----------------");
                    System.out.println("ID: " + room.getId());
                    System.out.println("Price: " + room.getRoomPrice()+" €/day");
                    System.out.println();
                }
            }
        }

        System.out.print("Give me the room ID you want to book: ");
        roomID = input.nextInt();

        return roomID;
    }

    public void createNewCustomer(HotelDataBase db){
        insertNewCustomer(db);
    }

    public void createNewBooking(HotelDataBase db, int uid){
        insertNewBooking(db,uid);
    }

    public void createNewPayment(HotelDataBase db, int bookPos, int bookID ,int payMethod ,int finalAmount){
        insertNewPayment(db,bookPos,bookID,payMethod,finalAmount);
    }

    public void createNewRecept(HotelDataBase db){

        Scanner input = new Scanner(System.in);
        System.out.print("Give the full name of the new receptionist: ");
        String name = input.nextLine();

        insertNewRecept(db, name);
    }

    public void updateCustomerEmail(HotelDataBase db, int uid){
        updateEmail(db,uid);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }
}
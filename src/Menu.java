import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    public void userLoginMenuLabel(){
        System.out.println("-----------------------------------------");
        System.out.println("|          USER LOGIN TO BASE           |");
        System.out.println("-----------------------------------------");
        System.out.println();
    }

    public void newCustomerLabel(){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|             NEW CUSTOMER              |");
        System.out.println("-----------------------------------------");
        System.out.println();
        System.out.println("Please give me your personal information.");
    }

    public void newBookingLabel(){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|              NEW BOOKING              |");
        System.out.println("-----------------------------------------");
        System.out.println();
    }

    public void availableRoomsLabel(){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|             AVAILABLE ROOMS           |");
        System.out.println("-----------------------------------------");
        System.out.println();
    }

    public void managerMenu(HotelDataBase db){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|            MANAGER MENU               |");
        System.out.println("-----------------------------------------");
        System.out.println();
        System.out.println("Select one operation (type the ID number).");
        System.out.println("ID: 1 ---> Welcome a customer to the hotel.");
        System.out.println("ID: 2 ---> Show customers list.");
        System.out.println("ID: 3 ---> Show bookings list.");
        System.out.println("ID: 4 ---> Show rooms list.");
        System.out.println("ID: 5 ---> Show receptionists list.");
        System.out.println("ID: 6 ---> Show data about hotel.");
        System.out.println("ID: 7 ---> Create new receptionist user.");
        System.out.println("ID: -1 ---> Exit program.");

        User user = new User();
        user.managerMenuOperations(db);
    }

    public void managerMenuData(HotelDataBase db){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|               HOTEL DATA              |");
        System.out.println("-----------------------------------------");
        System.out.println();
        System.out.println("Select an option (type the ID number).");
        System.out.println("ID: 1 ---> Show financial data about year 2020.");
        System.out.println("ID: 2 ---> Show financial data about year 2021 (so far).");
        System.out.println("ID: 3 ---> Show statistical data about year 2020.");
        System.out.println("ID: 4 ---> Show statistical data about year 2021 (so far).");
        System.out.println("ID: 5 ---> Back to manager's basic menu.");

        User user = new User();
        user.managerMenuDataOperations(db);
    }

    public void receptionistMenu(HotelDataBase db){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|           RECEPTIONIST MENU           |");
        System.out.println("-----------------------------------------");
        System.out.println();
        System.out.println("Select one operation (type the ID number).");
        System.out.println("ID: 1 ---> Welcome a customer to the hotel.");
        System.out.println("ID: 2 ---> Show customers list.");
        System.out.println("ID: 3 ---> Show bookings list.");
        System.out.println("ID: 4 ---> Show rooms list.");
        System.out.println("ID: -1 ---> Exit program.");

        User user = new User();
        user.receptMenuOperations(db);
    }

    public void customerMenu(HotelDataBase db, int uid){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|             CUSTOMER MENU             |");
        System.out.println("-----------------------------------------");
        System.out.println();
        System.out.println("Select one operation (type the ID number).");
        System.out.println("ID: 1 ---> Create a new booking.");
        System.out.println("ID: 2 ---> Show information about your booking(s).");
        System.out.println("ID: 3 ---> Pay off your booking account.");
        System.out.println("ID: 4 ---> Show your personal information.");
        System.out.println("ID: 5 ---> Update/change your email address.");
        System.out.println("ID: -1 ---> Exit program.");

        Customer customer = new Customer();
        customer.customerMenuOperations(db,uid);
    }

    public void welcomeCustomer(HotelDataBase db){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|          WELCOME TO OUR HOTEL         |");
        System.out.println("-----------------------------------------");
        System.out.println();

        Scanner input = new Scanner(System.in);
        System.out.print("Do you have an account? (Yes or No): ");
        String answer = input.nextLine();

        if(answer.equals("Yes") || answer.equals("yes") || answer.equals("YES")){
            db.customerLogin(db);
        }else if (answer.equals("No") || answer.equals("no") || answer.equals("NO")) {
            newCustomerLabel();
            db.createNewCustomer(db);
        }
    }

    public void showReceptionistsList(HotelDataBase db){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|          RECEPTIONISTS LIST           |");
        System.out.println("-----------------------------------------");
        System.out.println();

        for (int i = 0 ; i < db.getUsers().size() ; i++){

            if (db.getUsers().get(i).getUserRole() == 0){
                System.out.println("--------------------");
                System.out.println("Receptionist # " + (i-1));
                System.out.println("--------------------");
                System.out.println("ID: " + db.getUsers().get(i).getId());
                System.out.println("Name: " + db.getUsers().get(i).getName());
                System.out.println("Email: " + db.getUsers().get(i).getEmail());
                System.out.println("Username: " + db.getUsers().get(i).getUsername());
                System.out.println("Password: " + db.getUsers().get(i).getPassword());
                System.out.println();
            }
        }
    }

    public void showCustomersList(HotelDataBase db){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|            CUSTOMERS LIST             |");
        System.out.println("-----------------------------------------");
        System.out.println();

        for (int i = 0 ; i < db.getCustomers().size() ; i++){
            System.out.println("------------------");
            System.out.println("Customer # " + (i+1));
            System.out.println("------------------");
            System.out.println("UID: " + db.getCustomers().get(i).getUid());
            System.out.println("Name: " + db.getCustomers().get(i).getName());
            System.out.println("Email: " + db.getCustomers().get(i).getEmail());
            System.out.println();
        }
    }

    public void showRoomsList(HotelDataBase db){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|              ROOMS LIST               |");
        System.out.println("-----------------------------------------");
        System.out.println();

        for (int i = 0 ; i < db.getRooms().size() ; i++){
            System.out.println("-----------------");
            System.out.println("Room # " + (i+1));
            System.out.println("-----------------");
            System.out.println("ID: " + db.getRooms().get(i).getId());
            System.out.println("Type: " + db.getRooms().get(i).getRoomType());
            System.out.println("Price: " + db.getRooms().get(i).getRoomPrice()+" €/day");
            System.out.println();
        }
    }

    public void showBookingsList(HotelDataBase db){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|            BOOKINGS LIST              |");
        System.out.println("-----------------------------------------");
        System.out.println();

        for (int i = 0 ; i < db.getBookings().size() ; i++){
            System.out.println("-----------------");
            System.out.println("Booking # " + (i+1));
            System.out.println("-----------------");
            System.out.println("ID: " + db.getBookings().get(i).getId());
            System.out.println("Customer's UID: " + db.getBookings().get(i).getCustomerID());
            System.out.println("Room's ID: " + db.getBookings().get(i).getRoomID());
            System.out.println("Booking's period: From " + db.getBookings().get(i).getStartDay()+"."+db.getBookings().get(i).getStartMonth()
                    + "."+ db.getBookings().get(i).getBookingYear() +" to " + db.getBookings().get(i).getEndDay()+"."+ db.getBookings().get(i).getEndMonth() + "." + db.getBookings().get(i).getBookingYear());

            if (db.getBookings().get(i).getBookingSeason() == 0){
                System.out.println("Season: Low");
            }else if (db.getBookings().get(i).getBookingSeason() == 1){
                System.out.println("Season: Medium");
            }else if(db.getBookings().get(i).getBookingSeason() == 2){
                System.out.println("Season: High");
            }
            System.out.println();
        }
    }

    public void showBookingCustomerInfo(HotelDataBase db, int uid){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|             BOOKING INFO              |");
        System.out.println("-----------------------------------------");
        System.out.println();

        int bookingFound = 0, bookingNumber = 1;

        for (int i = 0 ; i < db.getBookings().size() ; i++){
            if (db.getBookings().get(i).getCustomerID() == uid){
                System.out.println("------------------");
                System.out.println("Your booking # " + bookingNumber);
                System.out.println("------------------");
                System.out.println("Booking's ID: " + db.getBookings().get(i).getId());
                System.out.println("Room's ID: " + db.getBookings().get(i).getRoomID());
                System.out.println("Booking's period: From " + db.getBookings().get(i).getStartDay()+"."+db.getBookings().get(i).getStartMonth()
                        + "."+ db.getBookings().get(i).getBookingYear() +" to " + db.getBookings().get(i).getEndDay()+"."+ db.getBookings().get(i).getEndMonth() + "." + db.getBookings().get(i).getBookingYear());
                if (db.getBookings().get(i).getBookingSeason() == 0){
                    System.out.println("Season: Low");
                }else if (db.getBookings().get(i).getBookingSeason() == 1){
                    System.out.println("Season: Medium");
                }else if(db.getBookings().get(i).getBookingSeason() == 2){
                    System.out.println("Season: High");
                }
                bookingFound = 1;
                bookingNumber++;
            }
        }

        if (bookingFound == 0){
            System.out.println("No booking with your UID.");
        }
    }

    public void showCustomerPersonalInfo(HotelDataBase db, int uid){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|             CUSTOMER INFO             |");
        System.out.println("-----------------------------------------");
        System.out.println();

        for(int i = 0 ; i < db.getCustomers().size() ; i++){
            if(db.getCustomers().get(i).getUid() == uid){
                System.out.println("UID: " + db.getCustomers().get(i).getUid());
                System.out.println("Name: " + db.getCustomers().get(i).getName());
                System.out.println("Email: " + db.getCustomers().get(i).getEmail());
            }
        }
    }

    public void showPaymentInfo(HotelDataBase db, int uid){
        System.out.println();
        System.out.println("-----------------------------------------");
        System.out.println("|              PAYMENT INFO             |");
        System.out.println("-----------------------------------------");
        System.out.println();

        int customerBookings = 0, bookingNumber = 1;
        int roomID = 0, bookID = 0;
        int bookPos = 0;
        int days = 0, finalAmount = 0;
        ArrayList<Integer> roomIDs = new ArrayList<>();
        Booking booking = new Booking();
        Payment payment = new Payment();

        //First, count the bookings that this customer have right now in our hotel base.
        for(int i = 0 ; i < db.getBookings().size() ; i++){
            if(db.getBookings().get(i).getCustomerID() == uid){
                roomIDs.add(db.getBookings().get(i).getRoomID());
                customerBookings++;
            }
        }

        //If this customer doesn't have bookings in our hotel base right now.
        if(customerBookings == 0){
            System.out.println("No booking found with your UID.");
        }

        //If customer has exactly one booking...
        else if(customerBookings == 1){
            for(int i = 0 ; i < db.getBookings().size() ; i++){
                if(db.getBookings().get(i).getCustomerID() == uid){
                    roomID = db.getBookings().get(i).getRoomID();
                    bookID = db.getBookings().get(i).getId();
                    bookPos = i;
                    System.out.println("Booking's ID: " + bookID);
                    System.out.println("Booking's period: From " + db.getBookings().get(i).getStartDay()+"."+db.getBookings().get(i).getStartMonth()
                            + "."+ db.getBookings().get(i).getBookingYear() +" to " + db.getBookings().get(i).getEndDay()+"."+ db.getBookings().get(i).getEndMonth() + "." + db.getBookings().get(i).getBookingYear());
                    System.out.println("Room's ID: " + roomID);
                }
            }
            for(int i = 0 ; i < db.getRooms().size() ; i++){
                if(db.getRooms().get(i).getId() == roomID){
                    System.out.println("Room's type: " + db.getRooms().get(i).getRoomType());
                }
            }
            days = booking.computeBookingDays(db,bookPos);
            finalAmount = payment.computeFinalAmount(db,days,roomID);
            System.out.println("Total amount: " + finalAmount + " €");

            Scanner input = new Scanner(System.in);
            System.out.print("Are you sure you want to pay your booking? (Yes or No): ");
            String answer = input.nextLine();

            if(answer.equals("Yes") || answer.equals("yes") || answer.equals("YES")){
                Scanner input1 = new Scanner(System.in);
                System.out.println();
                System.out.println("-----------------");
                System.out.println("Payment method");
                System.out.println("-----------------");
                System.out.println("1. Credit card (-20 € discount).");
                System.out.println("2. Cash.");
                System.out.print("Customer's choice: ");
                int payMethod = input1.nextInt();
                System.out.println();
                System.out.println("-----------------");
                db.createNewPayment(db,bookPos,bookID,payMethod,finalAmount);
            }else {
                customerMenu(db, uid);
            }
        }
        //If customer has more than one bookings...
        else if(customerBookings > 1){
            for(int i = 0 ; i < db.getBookings().size() ; i++) {
                if (db.getBookings().get(i).getCustomerID() == uid) {
                    System.out.println("-----------------");
                    System.out.println("Your booking # " + bookingNumber);
                    System.out.println("-----------------");
                    roomID = db.getBookings().get(i).getRoomID();
                    bookID = db.getBookings().get(i).getId();
                    System.out.println("Booking's ID: " + bookID);
                    System.out.println("Booking's period: From " + db.getBookings().get(i).getStartDay()+"."+db.getBookings().get(i).getStartMonth()
                            + "."+ db.getBookings().get(i).getBookingYear() +" to " + db.getBookings().get(i).getEndDay()+"."+ db.getBookings().get(i).getEndMonth() + "." + db.getBookings().get(i).getBookingYear());
                    System.out.println("Room's ID: " + roomID);
                    days = booking.computeBookingDays(db,i);
                    finalAmount = payment.computeFinalAmount(db,days,roomID);
                    System.out.println("Total amount: " + finalAmount + " €");
                    bookingNumber++;
                }
            }
            Scanner input = new Scanner(System.in);
            System.out.println();
            System.out.println("You have more than one booking active in our hotel.");
            System.out.print("Are you sure you want to pay one of your bookings? (Yes or No): ");
            String answer = input.nextLine();

            if(answer.equals("Yes") || answer.equals("yes") || answer.equals("YES")){
                Scanner input1 = new Scanner(System.in);
                System.out.println();
                System.out.print("Please choose the booking ID you want to pay now: ");
                int currentPayBookingID = input1.nextInt();
                for(int i = 0 ; i < db.getBookings().size() ; i++) {
                    if(db.getBookings().get(i).getId() == currentPayBookingID){
                        bookPos = i;
                    }
                }
                System.out.print("Fill the amount of current booking: ");
                int currentBookingAmount = input1.nextInt();
                System.out.println();
                System.out.println("-----------------");
                System.out.println("Payment method");
                System.out.println("-----------------");
                System.out.println("1. Credit card (-20 € discount).");
                System.out.println("2. Cash.");
                System.out.print("Customer's choice: ");
                int payMethod = input1.nextInt();
                System.out.println();
                System.out.println("-----------------");
                db.createNewPayment(db,bookPos,currentPayBookingID,payMethod,currentBookingAmount);
            }else{
                customerMenu(db,uid);
            }
        }
    }
}
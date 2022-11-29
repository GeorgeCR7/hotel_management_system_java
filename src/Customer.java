import java.util.Scanner;

public class Customer {

    private int uid; // Unique ID.
    private String name;
    private String email;


    public void customerMenuOperations(HotelDataBase db, int uid){

        Menu menu = new Menu();

        while(true){

            Scanner customerInput = new Scanner(System.in);
            System.out.print("Enter the menu ID number (ID: 0 ---> MENU): ");
            int customerMenuChoice = customerInput.nextInt();

            if(customerMenuChoice == 1){
                //create a new booking, in phase 5.
                menu.newBookingLabel();
                db.createNewBooking(db,uid);
            }else if(customerMenuChoice == 2){
                //show info booking.
                menu.showBookingCustomerInfo(db,uid);
            }else if(customerMenuChoice == 3){
                //pay off your booking.
                menu.showPaymentInfo(db,uid);
            }else if(customerMenuChoice == 4){
                //personal info.
                menu.showCustomerPersonalInfo(db,uid);
            }else if(customerMenuChoice == 5){
                //update-change email.
                db.updateCustomerEmail(db,uid);
            }else if(customerMenuChoice == 0){
                menu.customerMenu(db,uid);
            }else if(customerMenuChoice == -1){
                System.exit(0);
            }else{
                System.out.println("Please give a valid ID menu number!");
            }
        }
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
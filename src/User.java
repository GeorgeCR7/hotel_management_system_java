import java.util.Scanner;

public class User {

    private int id;
    private String name;
    private String email;
    private String username;
    private String password;
    private int userRole; // 0 --> Receptionist, 1 --> Manager.

    public void managerMenuOperations(HotelDataBase db){

        Menu menu = new Menu();

        while (true){

            Scanner managerInput = new Scanner(System.in);
            System.out.print("Enter the menu ID number (ID: 0 ---> MENU): ");
            int managerMenuChoice = managerInput.nextInt();

            if (managerMenuChoice == 1){
                menu.welcomeCustomer(db);
                break;
            }else if(managerMenuChoice == 2){
                menu.showCustomersList(db);
            }else if (managerMenuChoice == 3){
                menu.showBookingsList(db);
            }else if(managerMenuChoice == 4){
                menu.showRoomsList(db);
            }else if(managerMenuChoice == 5){
                menu.showReceptionistsList(db);
            }else if (managerMenuChoice == 6) {
                menu.managerMenuData(db);
            }else if (managerMenuChoice == 7){
                //create new receptionist user.
                db.createNewRecept(db);
            }else if(managerMenuChoice == 0){
                menu.managerMenu(db);
            }else if (managerMenuChoice == -1){
                System.exit(0);
            }else{
                System.out.println("Please give a valid ID menu number!");
            }
        }
    }

    public void managerMenuDataOperations(HotelDataBase db){

        Payment payment = new Payment();
        Menu menu = new Menu();

        while(true){

            Scanner managerInput = new Scanner(System.in);
            System.out.print("Enter the data menu ID number (ID: 0 --> DATA MENU): ");
            int managerMenuChoice = managerInput.nextInt();

            if(managerMenuChoice == 1){
                payment.showFinancialData(db, 2020);
            }else if(managerMenuChoice == 2){
                payment.showFinancialData(db, 2021);
            }else if(managerMenuChoice == 3){
                //statistical data about 2020, in phase 5.
                payment.showStatsData(db, 2020);
            }else if(managerMenuChoice == 4){
                //statistical data about 2021, in phase 5.
                payment.showStatsData(db, 2021);
            }else if(managerMenuChoice == 5){
                menu.managerMenu(db);
            }else if(managerMenuChoice == 0){
                menu.managerMenuData(db);
            }else{
                System.out.println("Please give a valid ID data menu number!");
            }
        }
    }

    public void receptMenuOperations(HotelDataBase db){

        Menu menu = new Menu();

        while (true){

            Scanner receptInput = new Scanner(System.in);
            System.out.print("Enter the menu ID number (ID: 0 ---> MENU): ");
            int receptMenuChoice = receptInput.nextInt();

            if(receptMenuChoice == 1){
                menu.welcomeCustomer(db);
                break;
            }else if(receptMenuChoice == 2){
                menu.showCustomersList(db);
            }else if(receptMenuChoice == 3){
                menu.showBookingsList(db);
            }else if(receptMenuChoice == 4){
                menu.showRoomsList(db);
            }else if(receptMenuChoice == 0){
                menu.receptionistMenu(db);
            }else if(receptMenuChoice == -1){
                System.exit(0);
            }else{
                System.out.println("Please give a valid ID menu number!");
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }
}

public class Main {

    public static void main(String [] args) {

        HotelDataBase db = new HotelDataBase();

        db.readAllData();
        db.userLogin(db);
    }
}

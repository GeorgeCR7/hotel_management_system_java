import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {

    private int id;
    private int customerID;
    private int roomID;
    private int startDay;
    private int endDay;
    private int startMonth;
    private int endMonth;
    private int bookingYear;
    private int bookingSeason;

    public int computeBookingDays(HotelDataBase db, int pos){

        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");

        int bookingDays=0;
        int startDay = db.getBookings().get(pos).getStartDay();
        int endDay = db.getBookings().get(pos).getEndDay();
        int startMonth = db.getBookings().get(pos).getStartMonth();
        int endMonth = db.getBookings().get(pos).getEndMonth();

        String startDate = String.valueOf(startDay) + " " + String.valueOf(startMonth) + " 2021";
        String endDate = String.valueOf(endDay) + " " + String.valueOf(endMonth) + " 2021";

        try{
            Date dateBefore = myFormat.parse(startDate);
            Date dateAfter = myFormat.parse(endDate);

            long difference = dateAfter.getTime() - dateBefore.getTime();
            long days = (difference/(1000*60*60*24));

            bookingDays = (int)days;

        }catch (Exception e){
            e.printStackTrace();
        }

        return bookingDays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getBookingYear() {
        return bookingYear;
    }

    public void setBookingYear(int bookingYear) {
        this.bookingYear = bookingYear;
    }

    public int getBookingSeason() {
        return bookingSeason;
    }

    public void setBookingSeason(int bookingSeason) {
        this.bookingSeason = bookingSeason;
    }
}